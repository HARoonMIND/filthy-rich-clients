package swinghacks.ch11.NativeIntegrationAndPackaging.hack80;
import java.io.*;

public class OSXOpenHack {

    public static void main(String[] args) throws Exception {
        Runtime rt = Runtime.getRuntime();
        //rt.exec("open notes.txt");
        //rt.exec("open .");
        //rt.exec("open http://www.yahoo.com/");
        
        //String cmd = "open -a Microsoft Word notes.txt";
        /*
        String[] cmd = {
            "open", 
            "-a", 
            "Microsoft Word",
            "notes.txt"
            };
            
        for(int i=0; i<cmd.length; i++) {
            System.out.println(cmd[i]);
        }
        rt.exec(cmd);
        */
        
        
    }

}
