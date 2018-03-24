package jsonparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.util.Stack;
import static jsonparser.JsonTokenizer.TokenType.*;

public class JsonTokenizer {

    private final PushbackReader pbrdr;
    private int lineNumber = 1;
    private StringBuffer stok;
    private Double numVal;
    private final Stack<TokenType> stk;
    
    public enum TokenType {
        LBRACE,
        RBRACE,
        LSBRACKET,
        RSBRACKET,
        COMMA,
        COLON,
        QSTRING,
        NUMBER,
        STRING,
        ERROR
    };

    int getCurrentLine() {
        return lineNumber;
    }

    void pushBack(TokenType tok) {
        stk.push(tok);
    }
    
    double getNumVal() {
        return numVal;
    }
    
    public String getStok() {
        return stok.toString();
    }

    public JsonTokenizer(InputStream iStream) {
        this.stk = new Stack<>();
        this.pbrdr = new PushbackReader(new InputStreamReader(iStream));
    }

    public TokenType nextToken() throws IOException {

        if(!stk.isEmpty())
            return stk.pop();
        
        char ch = (char) pbrdr.read();
        stok = new StringBuffer();
        switch (ch) {
            case '{':
                return LBRACE;
            case '[':
                return LSBRACKET;
            case '\"':
                while ((ch = (char) pbrdr.read()) != '\"') {
                    if (ch == '\\') {
                        stok.append((char)pbrdr.read());
                        continue;
                    }
                    if(ch == '\n' || ch == '\r')
                        return ERROR;
                    stok.append(ch);
                }
                return QSTRING;
            case ']':
                return RSBRACKET;
            case '}':
                return RBRACE;
            case ':':
                return COLON;
            case ',':
                return COMMA;
            case '\n':
                lineNumber++;
            case ' ':
            case '\t':
            case '\r':
                while (true) {
                    ch = (char) pbrdr.read();
                    if(ch == '\n')
                        lineNumber++;
                    if (ch != ' ' && ch != '\t' && ch != '\r' && ch != '\n') {
                        pbrdr.unread(ch);
                        break;
                    }
                }
                return nextToken();
            case '0': case '1': case '2':
            case '3': case '4': case '5':
            case '6': case '7': case '8':
            case '.': case '9':
                boolean dotSeen = false;
                do {
                    stok.append(ch);
                    ch = (char) pbrdr.read();
                    if(ch == '.' && dotSeen)
                        return ERROR;
                    dotSeen = (ch == '.');
                } while(ch >= '0' && ch <= '9' || ch == '.');
                pbrdr.unread(ch);
                numVal = Double.parseDouble(stok.toString());
                return NUMBER;
        }
        return ERROR;
    }
}
