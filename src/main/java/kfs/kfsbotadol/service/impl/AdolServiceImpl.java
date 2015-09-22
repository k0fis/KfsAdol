package kfs.kfsbotadol.service.impl;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import kfs.kfsbotadol.dao.*;
import kfs.kfsbotadol.domain.Adol;
import kfs.kfsbotadol.domain.AdolType;
import kfs.kfsbotadol.service.AdolService;
import kfs.kfsbotadol.service.impl.xs.adolDocumentConv;
import kfs.kfsbotadol.service.impl.xs.adolListConv;
import kfs.kfscrm.service.CrmService;
import kfs.kfslog.service.KfsLogService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author pavedrim
 */
@Service
public class AdolServiceImpl implements AdolService {

    private final Logger log = Logger.getLogger(getClass());

    @Value("${adol.urlLogin}")
    private String urlLogin;
    @Value("${adol.urlPrefix}")
    private String urlPrefix;
    @Value("${adol.username}")
    private String username;
    @Value("${adol.password}")
    private String password;
    @Autowired
    private AdolDao adolDao;
    @Autowired
    private AdolTypeDao adolTypeDao;
    @Autowired
    private KfsLogService logService;
    @Autowired
    private CrmService crmService;
    
    private String userLabel;

    private final XStream xs;

    public AdolServiceImpl() {
        xs = new XStream(new DomDriver());

        adolDocumentConv c1 = new adolDocumentConv();
        xs.registerConverter(c1);
        xs.alias(c1.getAlias(), c1.getDecodeClass());

        adolListConv c2 = new adolListConv(this);
        xs.registerConverter(c2);
        xs.alias(c2.getAlias(), c2.getDecodeClass());
    }

    @Override
    public void adolSave(Adol adol) {
        Adol find = adolDao.findById(adol.getName());
        if (find != null) {
            return;
        }
        adol.setImported(new Timestamp(new Date().getTime()));
        String phone = adol.getTelefon();
        if ((phone == null) || (phone.length() == 0)) {
            phone = adol.getKtelefon();
        }
        adol.setContact(crmService.contactCreate(phone, adol.getMail(), userLabel));
        if (adol.getContact() == null) {
            //adol.setNoContact();
        }
        adolDao.insert(adol);
    }

    @Override
    public Object adolLoad(String url) {
        DefaultHttpClient httpclient = null;
        try {
            httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(urlLogin);

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            //log.log(Level.INFO, "Login form get: {0}", response.getStatusLine());
            EntityUtils.consume(entity);

            //log.info("Initial set of cookies:");
            List<Cookie> cookies = httpclient.getCookieStore().getCookies();

            HttpPost httpost = new HttpPost(urlLogin);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("username", username));
            nvps.add(new BasicNameValuePair("password", password));
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

            response = httpclient.execute(httpost);
            entity = response.getEntity();

            //log.log(Level.INFO, "Login form get: {0}", response.getStatusLine());
            EntityUtils.consume(entity);

            //log.info("Post logon cookies:");
            cookies = httpclient.getCookieStore().getCookies();

            httpget = new HttpGet(url);
            response = httpclient.execute(httpget);
            entity = response.getEntity();
            return xs.fromXML(entity.getContent());
        } catch (IOException | IllegalStateException ex) {
            throw new AdolException("Cannot login into ADOL service", ex);
        } finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.urlLogin = url;
    }

    @Override
    public void adolImport(boolean doLastHour) {
        log.info("Adol start - last " + (doLastHour ? "Hour" : "5 min"));
        try {
            String lastHour;

            Calendar c = Calendar.getInstance();
            c.add(Calendar.HOUR, -1);
            lastHour = String.format("%1$td%1$tm%1$tY%1$tH", c.getTime());

            for (AdolType lt : adolTypeDao.loadActive()) {
                String url = urlPrefix //
                        + lt.getName() + "&url=1" + //
                        (doLastHour ? ("&timemark=" + lastHour) : "&fmin=true");
                log.info(lt.getNote());
                Object o = adolLoad(url);
                if (o != null) {
                    logService.log("adol", o.toString());
                }
            }

        } catch (Exception ex) {
            logService.log("adol", "Cannot impoert data from ADOL: " + ex.getMessage());
            throw new AdolException("Cannot impoert data from ADOL", ex);
        }
        log.info("Adol done");
    }

}
