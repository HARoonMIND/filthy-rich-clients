import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class KeyboardFlasher implements AWTEventListener {
    
    public static void main(String[] args) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        KeyboardFlasher flasher = new KeyboardFlasher();
        tk.addAWTEventListener(flasher, AWTEvent.KEY_EVENT_MASK);
        
        JFrame frame = new JFrame("Hack #63: Keyboard Flasher");
        JTextField tf = new JTextField("this is some text");
        frame.getContentPane().add(tf);
        frame.pack();
        frame.setVisible(true);
    }
    
    
    public void eventDispatched(AWTEvent evt) {
        if(evt instanceof KeyEvent) {
            KeyEvent kevt = (KeyEvent)evt;
            if(kevt.getID() == KeyEvent.KEY_PRESSED) {
                System.out.println("key event: " + evt);
                if(kevt.getKeyCode() != KeyEvent.VK_SCROLL_LOCK) {
                    flipScrollLock();
                }
            }
        }
    }
    
    public void flipScrollLock() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        boolean state = tk.getLockingKeyState(KeyEvent.VK_SCROLL_LOCK);
        tk.setLockingKeyState(KeyEvent.VK_SCROLL_LOCK,!state);
    }
    

}
