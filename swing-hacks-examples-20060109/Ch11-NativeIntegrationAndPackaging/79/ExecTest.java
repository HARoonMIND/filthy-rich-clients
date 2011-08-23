import java.io.IOException;
public class ExecTest {
    public static void main(String[] args) throws IOException {
    
        String cmd = "cmd.exe /c start ";
        //String file = "c:\\version.txt";
        //String file = "http://www.google.com";
        //String file = "c:\\";
        //String file = "mailto:author@mybook.com";
        String file = "mailto:";
        Runtime.getRuntime().exec(cmd + file);
    }

}
