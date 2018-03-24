package jsonparser;

public class NumberObject extends JsonObject {

    double numVal;
    public NumberObject(double numVal) {
        this.numVal = numVal;
    }
    
    public double getValue() {
        return numVal;
    }

    @Override
    public String toString() {
        return numVal + "";
    }

    @Override
    public JsonType getType() {
        return JsonType.Numeric;
    }
    
}
