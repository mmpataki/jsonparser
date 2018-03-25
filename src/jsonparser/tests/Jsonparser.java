package jsonparser.tests;

import java.io.FileInputStream;
import java.io.InputStream;
import jsonparser.Json;

public class Jsonparser {

    public static void main(String[] args) throws Exception {
        
        InputStream is = new FileInputStream("C:\\Users\\mpataki\\Desktop\\sample.json");
        
        Person p = (Person) Json.parse(is, Person.class);

        System.out.println(Json.dump(p));
        
        System.out.println(p);
        
    }
    
}
