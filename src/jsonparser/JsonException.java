package jsonparser;

public class JsonException extends Exception {

    public JsonException(String msg) {
        super(msg);
    }

    JsonException(JsonTokenizer jtok) {
        super("Invalid json at line: " + jtok.getCurrentLine());
    }

    JsonException(JsonTokenizer jtok, String msg) {
        super(msg + " line: " + jtok.getCurrentLine());
    }
    
}
