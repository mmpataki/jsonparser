package jsonparser.tests;

import java.util.Arrays;

public class Person {
    public String name;
    public int age;
    public String hobbies[];
    public int negage;
    public Person spouce;
    
    public Person() {
    }
    
    @Override
    public String toString() {
        return "Name : " + name + ", age : " + age + ", hobbies : " + Arrays.toString(hobbies) + ", negage: " + negage + ((spouce == null) ? "": ", spouce: " + spouce);
    }
    
}
