package jsonparser;

import java.io.IOException;
import java.io.InputStream;

public class CharInputStream extends InputStream {
    
    private String string;
    private int curpos;

    public CharInputStream(String string) {
        this.string = string;
    }

    @Override
    public int read() throws IOException {
        try {
            return string.charAt(curpos++);
        } catch(StringIndexOutOfBoundsException ex) {
            return -1;
        }
    }    
    
}
