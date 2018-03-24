package jsonparser;

import java.io.IOException;
import java.io.InputStream;
import jsonparser.JsonTokenizer.TokenType;
import static jsonparser.JsonTokenizer.TokenType.*;

public class Json {

    public static JsonObject parse(InputStream iStream) throws IOException, JsonException {
        JsonTokenizer jtok = new JsonTokenizer(iStream);
        return getObject(jtok);
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
                jobj = new NumberObject(jtok.getNumVal());
                break;
            case ERROR:
                throw new JsonException(jtok, "Error occured while parsing");
        }
        return jobj;
    }

}
