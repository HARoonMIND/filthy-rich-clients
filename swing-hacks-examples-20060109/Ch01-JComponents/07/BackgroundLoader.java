import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.regex.*;

public class BackgroundLoader implements Runnable {

    private ScrollPaneWatermark watermark;
    public BackgroundLoader(ScrollPaneWatermark watermark) {
        this.watermark = watermark;
    }
    
    public void run() {
        
        while(true) {
            try { 
                String base_url = "http://antwrp.gsfc.nasa.gov/apod/";
                URL url = new URL(base_url);
                
                p("loading the page: " + url);
                Reader input = new InputStreamReader(url.openStream());
                char buf[] = new char[1024];
                StringBuffer page_buffer = new StringBuffer();
                while(true) {
                    int n = input.read(buf);
                    if(n < 0) { break; }
                    page_buffer.append(buf,0,n);
                }
            
                Pattern pattern = Pattern.compile("<IMG SRC=\"(.*)\"");
                Matcher matcher = pattern.matcher(page_buffer);
                matcher.find();
                String img_url = base_url + matcher.group(1);
                p("loading the image: " + img_url);
                //System.out.println("base image = " + img);
                watermark.setBackgroundTexture(new URL(img_url));
                watermark.repaint();
                Thread.currentThread().sleep(1000*60*60*2); 
            } catch (Exception ex) {
                System.out.println("exception: " + ex);
                ex.printStackTrace();
                
            }
            
        }
    }
    
    
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Blocking Window");
        JTextArea jta = new JTextArea(10,40);
        jta.setForeground(Color.white);
        
        ScrollPaneWatermark viewport = new ScrollPaneWatermark();
        viewport.setView(jta);
        viewport.setOpaque(false);

        JScrollPane scroll = new JScrollPane();
        scroll.setViewport(viewport);
        
        Container comp = frame.getContentPane();
        comp.add("Center",scroll);

        frame.pack();
        frame.show();
        
        new Thread(new BackgroundLoader(viewport)).start();
    }
    
    public static void p(String str) {
        System.out.println(str);
    }
    
}

