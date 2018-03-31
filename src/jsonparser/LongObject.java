package jsonparser;

public class LongObject extends JsonObject {

    private final long value;
    
    public LongObject(String stok) {
        value = Long.parseLong(stok);
    }

    LongObject(Long value) {
        this.value = value;
    }

    @Override
    public JsonObject.JsonType getType() {
        return JsonObject.JsonType.Long;
    }

    @Override
    public Object getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
}
