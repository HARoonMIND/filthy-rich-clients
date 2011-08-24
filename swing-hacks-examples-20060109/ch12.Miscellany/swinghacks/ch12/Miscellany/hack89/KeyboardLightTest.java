package swinghacks.ch12.Miscellany.hack89;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.Thread;

public class KeyboardLightTest {
    public static void main(String[] args) {
        // create the spinner
        final SpinnerThread spinner = new SpinnerThread();

        //create a frame and button
        JFrame frame = new JFrame();
        JButton button = new JButton("Stop");
        frame.getContentPane().add(button);
        frame.pack();
        // hook up an action to stop the spinner and quit
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                spinner.quit();
            }
        });

        // start'er up!
        spinner.start();
        frame.show();
    }
}

class SpinnerThread extends Thread {
    private boolean go;
    public void quit() {
        go = false;
    }
    public void run() {
        go = true;
        // get a toolkit
        Toolkit tk = Toolkit.getDefaultToolkit();
        boolean old_num, old_caps, old_scroll;
        
        // save the old key states
        old_num = tk.getLockingKeyState(KeyEvent.VK_NUM_LOCK);
        old_caps = tk.getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
        old_scroll = tk.getLockingKeyState(KeyEvent.VK_SCROLL_LOCK);

        // set all keys to off
        tk.setLockingKeyState(KeyEvent.VK_NUM_LOCK,false);
        tk.setLockingKeyState(KeyEvent.VK_CAPS_LOCK,false);
        tk.setLockingKeyState(KeyEvent.VK_SCROLL_LOCK,false);

        int key = -1;
        boolean state = false;
        // loop through 100 times
        int counter = 0;
        while(go) {
            // select each key every 3rd time
            if(counter%3 == 0) { key = KeyEvent.VK_NUM_LOCK; }
            if(counter%3 == 1) { key = KeyEvent.VK_CAPS_LOCK; }
            if(counter%3 == 2) { key = KeyEvent.VK_SCROLL_LOCK; }
            // flip the state
            state = tk.getLockingKeyState(key);
            tk.setLockingKeyState(key,!state);
            // sleep for 500 msec
            try { Thread.currentThread().sleep(500);
            } catch (InterruptedException ex) {}
            // increment counter
            counter++;
        }

        // restore the key settings
        tk.setLockingKeyState(KeyEvent.VK_NUM_LOCK,old_num);
        tk.setLockingKeyState(KeyEvent.VK_CAPS_LOCK,old_caps);
        tk.setLockingKeyState(KeyEvent.VK_SCROLL_LOCK,old_scroll);
    }
}

