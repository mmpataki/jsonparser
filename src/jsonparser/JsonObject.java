package jsonparser;

public abstract class JsonObject implements TypedObject {

    public enum JsonType {
        String,
        Object,
        Integer,
        Long,
        Double,
        DateTime,
        ArrayType,
        ObjectType
    }
    
}
