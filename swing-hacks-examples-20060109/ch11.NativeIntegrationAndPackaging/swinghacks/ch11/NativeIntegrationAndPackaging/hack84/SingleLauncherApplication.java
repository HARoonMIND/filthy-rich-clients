package swinghacks.ch11.NativeIntegrationAndPackaging.hack84;
import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class SingleLauncherApplication implements Runnable {
    public static final int PORT = 38629;
    public JLabel label;
    public ServerSocket server;
    
    public void launch(String[] args) {
        try {
            server = new ServerSocket(PORT);
            new Thread(this).start();
            firstMain(args);
        } catch (IOException ioex) {
            System.out.println("already running!");
            relaunch(args);
        }
    }
    
    public void run() {
        System.out.println("waiting for a connection");
        while(true) {
            try {
                // wait for a socket connection
                Socket sock = server.accept();
                
                // read the contents into a string buffer
                InputStreamReader in = new InputStreamReader(sock.getInputStream());
                StringBuffer sb = new StringBuffer();
                char[] buf = new char[256];
                while(true) {
                    int n = in.read(buf);
                    if(n < 0) { break; }
                    sb.append(buf,0,n);
                }
                // split the string buffer into strings
                String[] results = sb.toString().split("\\n");
                // call other main
                otherMain(results);
            } catch (IOException ex) {
                System.out.println("ex: " + ex);
                ex.printStackTrace();
            }
        }
        
    }

    public void relaunch(String[] args) {
        try {
            // open a socket to the original instance
            Socket sock = new Socket("localhost",PORT);
            
            // write the args to the output stream
            OutputStreamWriter out = new OutputStreamWriter(sock.getOutputStream());
            for(int i=0; i<args.length; i++) {
                out.write(args[i]+"\n");
                p("wrote: " + args[i]);
            }
            // cleanup
            out.flush();
            out.close();
        } catch (Exception ex) {
            System.out.println("ex: " + ex);
            ex.printStackTrace();
        }
    }
    
    
    
    
    public void firstMain(String[] args) {
        JFrame frame = new JFrame("Single Launch Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String word = "";
        if(args.length >= 1) {
            word = args[0];
        }
        label = new JLabel("The word of the day is: " + word);
        frame.getContentPane().add(label);
        frame.pack();
        frame.show();
    }
    
    
    public void otherMain(final String[] args) {
        if(args.length >= 1) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    label.setText("The word of the day is: " + args[0]);
                }
            });
        }
    }
    
    
    public static void main(String[] args) {
        new SingleLauncherApplication().launch(args);
    }

    public static void p(String str) {
        System.out.println(str);
    }
}
