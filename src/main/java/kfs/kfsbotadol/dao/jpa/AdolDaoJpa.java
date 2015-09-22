package kfs.kfsbotadol.dao.jpa;

import kfs.kfsbotadol.dao.AdolDao;
import kfs.kfsbotadol.domain.Adol;
import kfs.springutils.BaseDaoJpa;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pavedrim
 */
@Repository
public class AdolDaoJpa extends BaseDaoJpa<Adol, String> implements AdolDao{

    @Override
    protected Class<Adol> getDataClass() {
        return Adol.class;
    }

    @Override
    protected String getId(Adol data) {
        return data.getName();
    }

}
