package kfs.kfsbotadol.service.impl.xs;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.lang.reflect.Field;
import kfs.field.KfsField;
import kfs.kfsbotadol.domain.Adol;

/**
 *
 * @author pavedrim
 */
public class adolDocumentConv implements Converter {

    private static final String name = "name";
    private static final String np = "np";
    private static final String kraj = "kraj";
    private static final String typ = "typ";
    private static final String id = "id";

    public Class getDecodeClass() {
        return Adol.class;
    }

    public String getAlias() {
        return "document";
    }

    public void kfsSet(String aName, String value, Adol rd) {
        if (value == null) {
            value = "";
        }
        for (Field f : rd.getClass().getDeclaredFields()) {
            if (f.getName().equalsIgnoreCase(aName)) {
                KfsField kf = new KfsField(rd.getClass(), f);
                kf.setVal(rd, value);
                return;
            }
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc) {
        Adol doc = new Adol();
        doc.setName(reader.getAttribute(name));

        while (reader.hasMoreChildren()) { // pole
            reader.moveDown();

            String s = reader.getAttribute(name);
            if (np.equalsIgnoreCase(s)) {
                setInner(np, doc, reader);
            } else if (kraj.equalsIgnoreCase(s)) {
                setInner(kraj, doc, reader);
            } else if (typ.equalsIgnoreCase(s)) {
                setInner(typ, doc, reader);
            } else {
                kfsSet(s, reader.getValue(), doc);
            }
            reader.moveUp();
        }

        return doc;
    }

    void setInner(String prefix, Adol doc, HierarchicalStreamReader reader) {
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String s = reader.getNodeName();
            s = s == null ? "" : s;
            if (id.equalsIgnoreCase(s)) {
                kfsSet(prefix + id, reader.getValue(), doc);
            } else if (name.equalsIgnoreCase(s)) {
                kfsSet(prefix + name, reader.getValue(), doc);
            } else {
                System.err.println("sub pole attr: " + prefix + ":" + s);
            }
            reader.moveUp();
        }

    }

    @Override
    public boolean canConvert(Class type) {
        return getDecodeClass().equals(type);
    }

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext mc) {
    }
}
