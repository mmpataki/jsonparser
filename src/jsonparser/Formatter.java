package jsonparser;

import java.io.IOException;
import static jsonparser.JsonTokenizer.TokenType.COLON;
import static jsonparser.JsonTokenizer.TokenType.COMMA;
import static jsonparser.JsonTokenizer.TokenType.QSTRING;
import static jsonparser.JsonTokenizer.TokenType.RBRACE;
import static jsonparser.JsonTokenizer.TokenType.RSBRACKET;

public class Formatter {

    private static StringBuilder getTABS() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append('\t');
        }
        return sb;
    }

    public static String format(String json) throws IOException, JsonException {
        depth = 0;
        JsonTokenizer tok = new JsonTokenizer(new CharInputStream(json));
        StringBuilder sbuf = new StringBuilder();
        getObject(tok, sbuf);
        return sbuf.toString();
    }

    private static int depth = -1;

    private static void getObject(JsonTokenizer jtok, StringBuilder sbuf) throws IOException, JsonException {

        depth++;
        JsonTokenizer.TokenType tok;
        tok = jtok.nextToken();

        switch (tok) {
            case LBRACE:
                sbuf.append('{');
                if (jtok.peekToken() == RBRACE) {
                    sbuf.append('}');
                    depth--;
                    return;
                }
                do {
                    JsonTokenizer.TokenType ntok = jtok.nextToken();
                    if (ntok != QSTRING) {
                        throw new JsonException(jtok, "Expected a Key here");
                    }
                    String key = jtok.getStok();
                    if (jtok.nextToken() != COLON) {
                        throw new JsonException(jtok, "Expected a colon here");
                    }
                    sbuf.append('\n').append(getTABS()).append(StringObject.encodeString(key)).append(" : ");
                    getObject(jtok, sbuf);
                    if (jtok.peekToken() == COMMA) {
                        sbuf.append(',');
                    }
                } while ((tok = jtok.nextToken()) == COMMA);
                if (tok != RBRACE) {
                    throw new JsonException(jtok, "Expected a '}' or ','");
                }
                depth--;
                sbuf.append('\n').append(getTABS()).append('}');
                break;
            case LSBRACKET:
                sbuf.append('[');
                if (jtok.peekToken() == RBRACE) {
                    sbuf.append(']');
                    return;
                }
                sbuf.append('\n');
                do {
                    if ((tok = jtok.nextToken()) == RSBRACKET) {
                        break;
                    } else {
                        jtok.pushBack(tok);
                    }
                    sbuf.append(getTABS());
                    getObject(jtok, sbuf);
                    if (jtok.peekToken() == COMMA) {
                        sbuf.append(',');
                    }
                    sbuf.append('\n');
                } while ((tok = jtok.nextToken()) == COMMA);

                if (tok != RSBRACKET) {
                    throw new JsonException(jtok, "Expected a ']'");
                }
                depth--; //quick hack for beautiful array display.
                sbuf.append(getTABS()).append("]");
                depth++; //epilog
                break;
            case QSTRING:
                sbuf.append(StringObject.encodeString(jtok.getStok()));
                break;
            case NUMBER:
                sbuf.append(jtok.getStok());
                break;
            case ERROR:
                throw new JsonException(jtok, "Error occured while parsing");
        }
        depth--;
    }

}
