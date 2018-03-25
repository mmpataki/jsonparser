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
    
    JsonObject get(String key) {
        return dict.get(key);
    }
    
    void set(String key, JsonObject obj) {
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

