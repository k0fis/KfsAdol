package kfs.kfsbotadol.service.impl.xs;

/**
 *
 * @author pavedrim
 */
public class newfeed {
    int cnt;
    int cntSms;
    int cntEmail;

    public newfeed() {
        this.cnt = 0;
        this.cntSms = 0;
        this.cntEmail = 0;
    }

    @Override
    public String toString() {
        return 
        (new StringBuilder()) 
        .append("load: ").append(cnt).append(" items, ") 
        .append(cntSms).append(" SMS, ") 
        .append(cntEmail).append(" e-mail") 
        .toString();
    }

}
