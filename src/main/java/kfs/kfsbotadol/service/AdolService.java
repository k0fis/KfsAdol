package kfs.kfsbotadol.service;

import kfs.kfsbotadol.domain.Adol;


/**
 *
 * @author pavedrim
 */
public interface AdolService {

    void adolSave(Adol adol);
    Object adolLoad(String url);
    
    void adolImport(boolean doLastHour);
    
}
