package jsonparser;

public abstract class JsonObject implements TypedObject {

    public enum JsonType {
        String,
        Object,
        Integer,
        Double,
        DateTime,
        ArrayType,
        ObjectType
    }
    
}
