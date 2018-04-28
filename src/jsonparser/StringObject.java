package jsonparser;

public class StringObject extends JsonObject {
    
    String value;

    public StringObject(String value) {
        this.value = value;
    }
    
    String get() {
        return value;
    }
    
    void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return encodeString(value).toString();
    }
    
    public static StringBuffer encodeString(String value) {
        StringBuffer sbuf = new StringBuffer("\"");
        for (int i = 0; i < value.length(); i++) {
            if(value.charAt(i) == '\"')
                sbuf.append("\\\\");
            sbuf.append(value.charAt(i));
        }
        return sbuf.append('\"');
    }

    @Override
    public JsonType getType() {
        return JsonType.String;
    }

    @Override
    public Object getValue() {
        return get();
    }

}
