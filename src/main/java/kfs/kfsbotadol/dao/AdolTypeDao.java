package kfs.kfsbotadol.dao;

import java.util.List;
import kfs.kfsbotadol.domain.AdolType;
import kfs.springutils.BaseDao;

/**
 *
 * @author pavedrim
 */
public interface AdolTypeDao extends BaseDao<AdolType, Long> {

    List<AdolType>loadActive();
}
