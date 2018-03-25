package jsonparser;

public class DoubleObject extends JsonObject {

    private final double value;
    
    public DoubleObject(String stok) {
        value = Double.parseDouble(stok);
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
