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
        return get();
    }

    @Override
    public JsonType getType() {
        return JsonType.String;
    }

}
