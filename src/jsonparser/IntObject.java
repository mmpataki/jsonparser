package jsonparser;

public class IntObject extends JsonObject {

    private final int value;
    
    public IntObject(String stok) {
        value = Integer.parseInt(stok);
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
