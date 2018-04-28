package jsonparser;

import java.util.ArrayList;

public class JsonArray extends JsonObject {
    
    ArrayList<JsonObject> elements;

    ArrayList<JsonObject> getElements() {
        return (ArrayList<JsonObject>) elements.clone();
    }
    
    public JsonArray() {
        elements = new ArrayList<>();
    }
    
    public void add(JsonObject obj) {
        elements.add(obj);
    }
    
    public void remove(int index) {
        elements.remove(index);
    }
    
    public int size() {
        return elements.size();
    }
    
    public JsonObject get(int index) {
        return elements.get(index);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < elements.size(); i++) {
            if(i != 0)
                sb.append(",");
            sb.append(elements.get(i).toString());
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public JsonType getType() {
        return JsonType.ArrayType;
    }

    @Override
    public Object getValue() {
        return this;
    }
}
