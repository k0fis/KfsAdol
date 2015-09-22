package kfs.kfsbotadol.service.impl;

/**
 *
 * @author pavedrim
 */
public class AdolException extends RuntimeException {
    public AdolException(String msg) {
        super(msg);
    }
    public AdolException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
