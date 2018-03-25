package jsonparser;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jsonparser.JsonObject.JsonType.ObjectType;
import jsonparser.JsonTokenizer.TokenType;
import static jsonparser.JsonTokenizer.TokenType.*;

public class Json {

    public static JsonObject parse(InputStream iStream) throws IOException, JsonException {
        JsonTokenizer jtok = new JsonTokenizer(iStream);
        return getObject(jtok);
    }

    public static JsonObject parse(String jsonString) throws IOException, JsonException {
        return parse(new CharInputStream(jsonString));
    }

    public static Object parse(String jsonString, Class c) throws JsonException, IOException {
        return parse(new CharInputStream(jsonString), c);
    }

    public static Object parse(InputStream iStream, Class c)
            throws IOException, JsonException {

        try {
            Object obj = c.newInstance();
            JsonObject jobj = parse(iStream);
            assign(obj, jobj);
            return obj;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | IllegalArgumentException ex) {
            throw new JsonException("Field mismatch or AccessException");
        }
    }

    private static JsonObject getObject(JsonTokenizer jtok) throws IOException, JsonException {

        TokenType tok;
        JsonObject jobj = null;
        tok = jtok.nextToken();

        switch (tok) {
            case LBRACE:
                jobj = new DictObject();
                do {
                    if (jtok.nextToken() != QSTRING) {
                        throw new JsonException(jtok, "Expected a Key here");
                    }
                    String key = jtok.getStok();
                    if (jtok.nextToken() != COLON) {
                        throw new JsonException(jtok, "Expected a colon here");
                    }
                    ((DictObject) jobj).set(key, getObject(jtok));
                } while ((tok = jtok.nextToken()) == COMMA);
                if (tok != RBRACE) {
                    throw new JsonException(jtok, "Expected a '}' or ','");
                }
                break;
            case LSBRACKET:
                jobj = new JsonArray();
                do {
                    JsonObject child = getObject(jtok);
                    JsonArray jarr = ((JsonArray) jobj);
                    jarr.add(child);
                    if (jarr.size() != 0 && jarr.get(0).getType() != child.getType()) {
                        throw new JsonException(jtok, "All elements of array must be of same type");
                    }
                } while ((tok = jtok.nextToken()) == COMMA);

                if (tok != RSBRACKET) {
                    throw new JsonException(jtok, "Expected a ']'");
                }
                break;
            case QSTRING:
                jobj = new StringObject(jtok.getStok());
                break;
            case NUMBER:
                jobj = jtok.getStok().contains(".") ? new DoubleObject(jtok.getStok())
                        : new IntObject(jtok.getStok());
                break;
            case ERROR:
                throw new JsonException(jtok, "Error occured while parsing");
        }
        return jobj;
    }

    public static String dump(Object obj) throws JsonException {
        return sbdump(obj).toString();
    }

    public static StringBuffer sbdump(Object obj) throws JsonException {

        Class<?> ctype = obj.getClass();
        if (isPrimitive(ctype)) {
            if (ctype == String.class) {
                return StringObject.encodeString((String) obj);
            } else {
                return new StringBuffer().append(obj);
            }
        }

        StringBuffer sbuf = new StringBuffer();

        if (obj.getClass().isArray()) {
            sbuf.append('[');
            for (int i = 0; i < Array.getLength(obj); i++) {
                if (i != 0) {
                    sbuf.append(',');
                }
                sbuf.append(dump(Array.get(obj, i)));
            }
            sbuf.append(']');
        } else {
            int i = 0;
            Field[] fields = obj.getClass().getFields();
            sbuf.append('{');
            for (Field field : fields) {

                try {
                    if (field.get(obj) == null) {
                        continue;
                    }

                    if (i++ != 0) {
                        sbuf.append(',');
                    }
                    sbuf.append(StringObject.encodeString(field.getName())).append(':');
                    sbuf.append(dump(field.get(obj)));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(Json.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            sbuf.append('}');
        }
        return sbuf;
    }

    private static void assign(Object obj, JsonObject jobj) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InstantiationException {

        if (jobj.getType() == ObjectType) {
            DictObject dobj = (DictObject) jobj;
            for (String key : dobj.keySet()) {
                Field field = obj.getClass().getField(key);
                JsonObject val = dobj.get(key);
                Class<?> co = field.getType();
                if (isPrimitive(co)) {
                    field.set(obj, val.getValue());
                } else {
                    if (co.isArray()) {
                        field.set(obj, Array.newInstance(co.getComponentType(), ((JsonArray) val).size()));
                    } else {
                        field.set(obj, co.newInstance());
                    }
                    assign(field.get(obj), val);
                }
            }
        } else {
            int i = 0;
            for (JsonObject ele : ((JsonArray) jobj).getElements()) {
                Array.set(obj, i++, ele.getValue());
            }
        }
    }

    public static boolean isPrimitive(Class<?> type) {
        return (type.isPrimitive() && type != void.class)
                || type == Double.class || type == Float.class || type == Long.class
                || type == Integer.class || type == Short.class || type == Character.class
                || type == Byte.class || type == Boolean.class || type == String.class;
    }

}
