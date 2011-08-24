package swinghacks.ch05.WindowsDialogsFrames.hack37;
import java.io.*;
import javax.swing.*;
import java.util.Properties;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class WindowSaver implements AWTEventListener {
    
    private static WindowSaver saver;
    private HashMap framemap;
    
    private WindowSaver() {
        framemap = new HashMap();
    }
    
    public static WindowSaver getInstance() {
        if(saver == null) {
            saver = new WindowSaver();
        }
        return saver;
    }
    
    public void eventDispatched(AWTEvent evt) {
        try {
            if(evt.getID() == WindowEvent.WINDOW_OPENED) {
                ComponentEvent cev = (ComponentEvent)evt;
                if(cev.getComponent() instanceof JFrame) {
                    p("event: " + evt);
                    JFrame frame = (JFrame)cev.getComponent();
                    loadSettings(frame);
                }
            }
        }catch(Exception ex) {
            p(ex.toString());
        }
    }
    
    public static void loadSettings(JFrame frame) throws IOException {
        Properties settings = new Properties();
        // if this file does not already exist, create an empty one
        try {
            settings.load(new FileInputStream("configuration.props"));
        } catch (FileNotFoundException fnfe) {
            settings.store (new FileOutputStream ("configuration.props"),
                            "Window settings");
        }
        String name = frame.getName();
        int x = getInt(settings,name+".x",100);
        int y = getInt(settings,name+".y",100);
        int w = getInt(settings,name+".w",500);
        int h = getInt(settings,name+".h",500);
        frame.setLocation(x,y);
        frame.setSize(new Dimension(w,h));
        saver.framemap.put(name,frame);
        frame.validate();
    }
    
    public static int getInt(Properties props, String name, int value) {
        String v = props.getProperty(name);
        if(v == null) {
            return value;
        }
        return Integer.parseInt(v);
    }
    
    public static void saveSettings() throws IOException {
        Properties settings = new Properties();
        try {
            settings.load(new FileInputStream("configuration.props"));
        } catch (FileNotFoundException fnfe) {
            // quietly ignore and overwrite anyways
        }
        Iterator it = saver.framemap.keySet().iterator();
        while(it.hasNext()) {
            String name = (String)it.next();
            JFrame frame = (JFrame)saver.framemap.get(name);
            settings.setProperty(name+".x",""+frame.getX());
            settings.setProperty(name+".y",""+frame.getY());
            settings.setProperty(name+".w",""+frame.getWidth());
            settings.setProperty(name+".h",""+frame.getHeight());
        }
        settings.store(new FileOutputStream("configuration.props"),null);
    }
    
    public static void p(String str) {
        System.out.println(str);
    }



    public static void main(String[] args) throws Exception {
        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.addAWTEventListener(WindowSaver.getInstance(),AWTEvent.WINDOW_EVENT_MASK);
        
        final JFrame frame = new JFrame("Hack X");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setName("WSTes.main");
        frame.getContentPane().add(new JButton("a button"));
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.add(new AbstractAction("Quit") {
            public void actionPerformed(ActionEvent evt) {
                try {
                    WindowSaver.saveSettings();
                    System.exit(0);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        mb.add(menu);
        frame.setJMenuBar(mb);
        frame.pack();
        frame.setVisible(true);
    }
    
}
