package jsonparser;

import java.io.FileInputStream;
import java.io.InputStream;

public class Jsonparser {

    public static void main(String[] args) throws Exception {
        
        InputStream is = new FileInputStream("C:\\Users\\mpataki\\Desktop\\sample.json");
        JsonObject jobj = (JsonObject) Json.parse(is);
        
        System.out.println(jobj);
        
    }
    
}
