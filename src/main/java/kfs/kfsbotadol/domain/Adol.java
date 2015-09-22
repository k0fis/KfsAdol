package kfs.kfsbotadol.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import kfs.kfsbot.domain.BotNoContact;
import kfs.kfscrm.domain.KfsContact;

/**
 *
 * @author pavedrim
 */
@Entity
public class Adol {

    @Id
    private String name;

    @OneToOne
    private KfsContact contact;
    
    @OneToOne
    private BotNoContact noContact;

    @Column(length = 20)
    private String cena;

    @Column(length = 256)
    private String mail;

    @Column(length = 20)
    private String telefon;

    @Column(length = 20)
    private String ktelefon;

    @Column(length = 64)
    private String title;

    @Lob
    private byte[] text;

    @Column(length = 20)
    private String npName;

    @Column(length = 20)
    private String npKraj;

    @Column(length = 20)
    private String typeName;

    @Column(length = 20)
    private String status;

    @Column(length = 256)
    private String link;

    @Column(updatable = false)
    private Timestamp imported;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KfsContact getContact() {
        return contact;
    }

    public void setContact(KfsContact contact) {
        this.contact = contact;
    }

    public BotNoContact getNoContact() {
        return noContact;
    }

    public void setNoContact(BotNoContact noContact) {
        this.noContact = noContact;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getKtelefon() {
        return ktelefon;
    }

    public void setKtelefon(String ktelefon) {
        this.ktelefon = ktelefon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getText() {
        return text;
    }

    public void setText(byte[] text) {
        this.text = text;
    }

    public String getNpName() {
        return npName;
    }

    public void setNpName(String npName) {
        this.npName = npName;
    }

    public String getNpKraj() {
        return npKraj;
    }

    public void setNpKraj(String npKraj) {
        this.npKraj = npKraj;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Timestamp getImported() {
        return imported;
    }

    public void setImported(Timestamp imported) {
        this.imported = imported;
    }

}
