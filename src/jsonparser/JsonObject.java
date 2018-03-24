package jsonparser;

public abstract class JsonObject implements TypedObject {

    public enum JsonType {
        String,
        Object,
        Numeric,
        DateTime,
        ArrayType,
        ObjectType
    }
    
}
