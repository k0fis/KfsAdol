package kfs.kfsScrDb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import kfs.kfsDbi.*;

/**
 *
 * @author pavedrim
 */
public class kfsAdolDoc extends kfsDbObject {

    public static final String tableName = "INP_ADOL";
    private final kfsInt cid;
    private final kfsInt ncId;
    private final kfsInt name;
    //private final kfsString datum; //datum = new kfsString("datum", "Datum", 30, pos++);
    private final kfsString cena;
    private final kfsMail email;
    private final kfsPhone telefon;
    private final kfsString ktelefon;
    private final kfsString title;
    private final kfsString text;
    //private final kfsString npId = new kfsString("npid", "id np", 2);
    private final kfsString npName;
    //private final kfsString krajId = new kfsString("krajid", "Kraj id", 2);
    private final kfsString krajName;
    //private final kfsString typId = new kfsString("typid", "Typ id", 2);
    private final kfsString typName;
    private final kfsString status;
    private final kfsString link;
    private final kfsDate imported;

    kfsAdolDoc(kfsDbServerType st) {
        super(st, tableName, "Adol");
        int pos = 0;
        cid = new kfsInt("contact_id", "cId", 19, pos++, true);
        ncId = new kfsInt("no_contact_id", "ncId", 19, pos++, true);
        name = new kfsInt("name", "Id", 10, pos++, true);

        cena = new kfsString("cena", "Cena", 20, pos++);
        email = new kfsMail("email", "E-Mail", pos++);
        telefon = new kfsPhone("telefon", "Telefon", pos++);
        ktelefon = new kfsPhone("ktelefon", "k. Telefon", pos++);
        title = new kfsString("title", "Popis", 64, pos++);
        text = new kfsString("text", "Text", 4096, pos++);
        npName = new kfsString("npname", "Jméno np", 10, pos++);
        krajName = new kfsString("krajname", "Kraj", 20, pos++);
        typName = new kfsString("typname", "Typ", 32, pos++);
        status = new kfsString("state", "Stav", 32, pos++);
        link = new kfsString("url", "URL", 250, pos++);
        imported = new kfsDate("imported", "Datum importu", pos++);

        super.setColumns(new kfsDbiColumn[]{
                    cid,
                    ncId,
                    name,
                    //datum,
                    cena,
                    email,
                    telefon,
                    ktelefon,
                    title,
                    text,
                    //npId,
                    npName,
                    //krajId,
                    krajName,
                    //typId,
                    typName,
                    status,
                    link,
                    imported});
        super.setIdsColumns(new kfsDbiColumn[]{ncId});
        super.setUpdateColumns(new kfsDbiColumn[]{cid});
        super.setFullTextColumns(
                new kfsDbiColumn[]{
                    cid
                }, new kfsDbiColumn[]{
                    email,
                    telefon,
                    ktelefon,
                    title,
                    text,
                    typName
                });
    }

    public String sqlSelectByCid() {
        return getSelect(getName(), getColumns(), new kfsDbiColumn[]{cid});
    }

    public void psSelectByCid(PreparedStatement ps, int cid) throws SQLException {
        ps.setInt(1, cid);
    }

    public String sqlSelectByNcid() {
        return getSelect(getName(), getColumns(), new kfsDbiColumn[]{ncId});
    }

    public void psSelectByNcid(PreparedStatement ps, int ncid) throws SQLException {
        ps.setInt(1, ncid);
    }

    public String sqlSetCidByNcid() {
        return getUpdate(getName(), new kfsDbiColumn[]{cid}, new kfsDbiColumn[]{ncId});
    }

    public void psSetCidByNcid(PreparedStatement ps, int cid, int ncid) throws SQLException {
        ps.setInt(1, cid);
        ps.setInt(2, ncid);
    }

    public String sqlExistSelect() {
        return getSelect(getName(), getColumns(), new kfsDbiColumn[]{name});
    }

    public void psExistSelect(PreparedStatement ps, pojo pj) throws SQLException {
        ps.setInt(1, pj.getName());
    }

    public void kfsSet(String aName, String value, kfsRowData rd) {
        if (value == null) {
            value = "";
        }
        for (kfsDbiColumn ksd : getColumns()) {
            if (ksd.getColumnName().equalsIgnoreCase(aName)) {
                if (ksd instanceof kfsString) {
                    ksd.setObject(value, rd);
                    return;
                } else if (ksd instanceof kfsLong) {
                    ksd.setObject(Long.parseLong(value), rd);
                    return;
                } else if (ksd instanceof kfsInt) {
                    ksd.setObject(Integer.parseInt(value), rd);
                    return;
                } else if (ksd instanceof kfsDouble) {
                    ksd.setObject(Double.parseDouble(value), rd);
                    return;
                }
                //System.err.println("adolDocument.set 2: Cannot set name:" + aName + ", val:" + value + " : " + ksd.getColumnName() + " : " + ksd.getClass().getName());
                return;
            }
        }
        //System.err.println("adolDocument.set : Cannot set name:" + aName + ", val:" + value);
    }

    @Override
    public pojo getPojo(kfsRowData row) {
        return new pojo( row);
    }

    public pojo createPojo() {
        return new pojo( new kfsRowData(this));
    }

    public class pojo extends kfsContactDetailDoc.pojo<kfsAdolDoc> {

        public pojo( final kfsRowData rd) {
            super(kfsAdolDoc.this, rd);
        }

        public Integer getId() {
            return inx.cid.getInt(rd);
        }

        @Override
        public String getTextShort() {
            return inx.text.getStringHead(rd, 32);
        }

        public Integer getCid() {
            return inx.cid.getData(rd);
        }

        public Integer getNcId() {
            return inx.ncId.getData(rd);
        }

        public void setNcId(Integer ncId) {
            inx.ncId.setData(ncId, rd);
        }

        public Integer getName() {
            return inx.name.getData(rd);
        }

        public String getCena() {
            return inx.cena.getData(rd);
        }

        public String getEmail() {
            return inx.email.getData(rd);
        }

        public String getTelefon() {
            return inx.telefon.getData(rd);
        }

        public String getKtelefon() {
            return inx.ktelefon.getData(rd);
        }

        public String getTitle() {
            return inx.title.getData(rd);
        }

        public String getText() {
            return inx.text.getData(rd);
        }

        public String getNpName() {
            return inx.npName.getData(rd);
        }

        public String getKrajName() {
            return inx.krajName.getData(rd);
        }

        public String getTypName() {
            return inx.typName.getData(rd);
        }

        public String getStatus() {
            return inx.status.getData(rd);
        }

        @Override
        public Date getImported() {
            return inx.imported.getData(rd);
        }

        public void setImportedNow() {
            inx.imported.setData(new Date(), rd);
        }

        public void setCid(int cid) {
            inx.cid.setData(cid, rd);
        }

        public String getLink() {
            return inx.link.getData(rd);
        }

        @Override
        public String getDetailType() {
            return "A Inzerát";
        }

        public void kfsSet(String aName, String value) {
            inx.kfsSet(aName, value, rd);
        }
    }
}
