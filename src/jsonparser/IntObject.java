package jsonparser;

public class IntObject extends JsonObject {

    private final int value;
    
    public IntObject(String stok) {
        value = Integer.parseInt(stok);
    }

    IntObject(Integer value) {
        this.value = value;
    }

    @Override
    public JsonType getType() {
        return JsonType.Integer;
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
