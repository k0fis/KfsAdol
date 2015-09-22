package kfs.kfsbotadol.dao.jpa;

import java.util.List;
import kfs.kfsbotadol.dao.AdolTypeDao;
import kfs.kfsbotadol.domain.AdolType;
import kfs.springutils.BaseDaoJpa;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pavedrim
 */
@Repository
public class AdolTypeDaoJpa extends BaseDaoJpa<AdolType, Long> implements AdolTypeDao{

    @Override
    protected Class<AdolType> getDataClass() {
        return AdolType.class;
    }

    @Override
    protected Long getId(AdolType data) {
        return data.getId();
    }

    @Override
    public List<AdolType> loadActive() {
        return em.createQuery("SELECT a FROM BotAdolType a WHERE a.active = :active")
                .setParameter("active", Boolean.TRUE)
                .getResultList();
    }

}
