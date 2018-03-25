package jsonparser;

import java.util.HashMap;
import java.util.Set;

public class DictObject extends JsonObject {
    
    HashMap<String, JsonObject> dict;

    public DictObject() {
        dict = new HashMap<>();
    }
    
    public Set<String> keySet() {
        return dict.keySet();
    }
    
    public JsonObject get(String key) {
        return dict.get(key);
    }
    
    public void set(String key, Integer value) {
        dict.put(key, new IntObject(value));
    }
    
    public void set(String key, Double obj) {
        dict.put(key, new DoubleObject(obj));
    }
    
    public void set(String key, String obj) {
        dict.put(key, new StringObject(obj));
    }
    
    public void set(String key, JsonObject obj) {
        dict.put(key, obj);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        int i = 0;
        for (String key : dict.keySet()) {
            if(i++ != 0)
                sb.append(",");
            sb.append("\"").append(key).append("\":").append(dict.get(key));
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public JsonType getType() {
        return JsonType.ObjectType;
    }

    @Override
    public Object getValue() {
        return this;
    }
}

