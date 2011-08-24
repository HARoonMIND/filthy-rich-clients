package swinghacks.ch08.Rendering.hack58;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

/* as a future enhancment you could make it beep or
pop up a dialog box saying stuff is in process, do you wish
to quit.
*/

public class WindowBlocker extends JComponent
    implements MouseInputListener {
    private Cursor old_cursor;
    public WindowBlocker() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public void mouseMoved(MouseEvent e) {
    }
    public void mouseDragged(MouseEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
        Toolkit.getDefaultToolkit().beep();
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    
    public void block() {
        old_cursor = getCursor();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        setVisible(true);
    }
    
    public void unBlock() {
        setCursor(old_cursor);
        setVisible(false);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Blocking Window");
        JTextArea jta = new JTextArea(10,40);
        JScrollPane scroll = new JScrollPane(jta);
        JButton start = new JButton("Start Processing");
        JLabel status = new JLabel("status");
        
        WindowBlocker blocker = new WindowBlocker();
        frame.setGlassPane(blocker);
        start.addActionListener(new LongProcess(status,blocker));
        
        
        Container comp = frame.getContentPane();
        comp.add("North",start);
        comp.add("Center",scroll);
        comp.add("South",status);

        frame.pack();
        frame.setVisible(true);
    }
    
}

class LongProcess implements ActionListener, Runnable {
    JLabel status;
    WindowBlocker blocker;
    public LongProcess(JLabel status, WindowBlocker blocker) {
        this.blocker = blocker;
        this.status = status;
    }
    
    public void actionPerformed(ActionEvent evt) {
        blocker.block();
        new Thread(this).start();
    }
    
    public void setText(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                status.setText(text);
            }
        });
    }
    
    public void run() {
        for(int i=10; i>0; i--) {
            // set the label
            final String text = "("+i+") seconds left";
            setText(text);

            // sleep for 1 second
            try {
                Thread.currentThread().sleep(1000);
            } catch (Exception ex) {
            }
        }
        // set the final status string
        setText("Process Complete");
        blocker.unBlock();
    }
    
}
