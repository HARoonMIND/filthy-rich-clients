import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.beans.*;

public class DropDownTest extends JPanel {
    
    public static void main(String[] args) {

        final JButton status = new JButton("Color");
        final JPanel panel = new ColorSelectionPanel();
        final DropDownComponent dropdown = new DropDownComponent(status,panel);
        panel.addPropertyChangeListener("selectedColor",new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                dropdown.hidePopup();
                status.setBackground((Color)evt.getNewValue());
            }
        });
        
        
        JFrame frame = new JFrame("Drop Down Test");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(  ));
        frame.getContentPane().add("North",dropdown);
        frame.getContentPane().add("Center",new JLabel("Drop Down Test"));
        frame.pack();
        frame.setSize(300,300);
        frame.show();
        
    }

}
