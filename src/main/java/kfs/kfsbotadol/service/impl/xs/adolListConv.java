package kfs.kfsbotadol.service.impl.xs;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import kfs.kfsbotadol.domain.Adol;
import kfs.kfsbotadol.service.AdolService;

/**
 *
 * @author pavedrim
 */
public class adolListConv implements Converter {

    private final AdolService adolService;

    public adolListConv(AdolService adolService) {
        this.adolService = adolService;
    }

    public String getAlias() {
        return "newsfeed";
    }


    public Class getDecodeClass() {
        return newfeed.class;
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc) {
        newfeed ret = new newfeed();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            if ("documents".equals(reader.getNodeName()) || "query".equals(reader.getNodeName())) {
                while (reader.hasMoreChildren()) {
                    reader.moveDown();
                    if ("document".equals(reader.getNodeName())) {
                        Object o = uc.convertAnother(ret, Adol.class);
                        if (o != null) {
                            adolService.adolSave((Adol) o);
                        }
                    }
                    reader.moveUp();
                }
            }
            reader.moveUp();
        }
        return ret;
    }

    @Override
    public boolean canConvert(Class type) {
        return getDecodeClass().equals(type);
    }

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext mc) {
    }
}
