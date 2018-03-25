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
        return JsonType.Double;
    }

    @Override
    public Object getValue() {
        return value;
    }
    
}
