import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class ApplicationMirrorTest {
    Map component_map;
    public ApplicationMirrorTest() {
        component_map = new ComponentMap();
        
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        
        final JButton button = new JButton("action generator");
        button.setName("button");
        frame.getContentPane().add(button);
        
        JTextField tf = new JTextField("text field");
        tf.setName("textfield");
        frame.getContentPane().add(tf);
        
        frame.pack();
        frame.show();
    }
    
    public void openSender(Socket sock) throws Exception {
        final ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
            
        Toolkit.getDefaultToolkit().addAWTEventListener(
            new AWTEventListener() {
                public void eventDispatched(AWTEvent evt) {
                    try {
                        if(evt instanceof MouseEvent) {
                            MouseEvent me = (MouseEvent)evt;
                            out.writeObject(me.getComponent().getName());
                            out.writeObject(evt);
                        }
                    } catch (Exception ex) { }
                }
            },
            AWTEvent.ACTION_EVENT_MASK | 
            AWTEvent.MOUSE_EVENT_MASK
        );
    }
    
    public void openReceiver() throws Exception  {
        // receive events
        System.out.println("couldn't open socket. must be the server");
        ServerSocket server = new ServerSocket(6754);
        Socket sock = server.accept();
        
        EventQueue eq = Toolkit.getDefaultToolkit().getSystemEventQueue();
        
        ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
        while(true ) {
            String id = (String) in.readObject();
            AWTEvent evt = (AWTEvent) in.readObject();
            if(evt instanceof MouseEvent) {
                MouseEvent me = (MouseEvent)evt;
                MouseEvent me2 = new MouseEvent(
                    (Component)component_map.get(id),
                    me.getID(),
                    me.getWhen(),
                    me.getModifiers(),
                    me.getX(),
                    me.getY(),
                    me.getClickCount(),
                    me.isPopupTrigger(),
                    me.getButton()
                    );
                eq.postEvent(me2);
            }
        }

    }
    
    public void start() {
        try {
            // send events
            final Socket sock = new Socket("localhost",6754);
            openSender(sock);
        } catch (Exception ex) {
            try {
                openReceiver();
            } catch (Exception ex2) {
                System.out.println("exception: " + ex);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ApplicationMirrorTest mirror = new ApplicationMirrorTest();
        mirror.start();        
    }
    


    public static void p(String str) {
        System.out.println(str);
    }

}
