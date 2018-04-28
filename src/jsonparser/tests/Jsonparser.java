package jsonparser.tests;

import java.io.FileInputStream;
import java.io.InputStream;
import jsonparser.Formatter;
import jsonparser.Json;

public class Jsonparser {

    public static void main(String[] args) throws Exception {
        
        String json = "{'http-root': 'foo@bar.com','hello-world':'foor-bar','hobbies':['a','b','c'],'age':23,'school':{'name':'ABCD', 'year-est':1992, 'classes':['1',2,'3']}}";
        
        System.out.println(Formatter.format(json));
        
    }
    
}
