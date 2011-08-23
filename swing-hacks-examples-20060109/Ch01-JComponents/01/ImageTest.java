import javax.swing.*;
import java.awt.event.*;

public class ImageTest {

    public static void main(String[] args) {
        ImagePanel panel = new ImagePanel(new ImageIcon("images/background.png").getImage());

        
        ImageLabel label = new ImageLabel(new ImageIcon("images/reactor.png"));
        label.setLocation(29,37);
        panel.add(label);
        
        final ImageButton button = new ImageButton("images/button.png");
        button.setPressedIcon(new ImageIcon("images/button-down.png"));
        button.setRolloverIcon(new ImageIcon("images/button-over.png"));
        button.setSelectedIcon(new ImageIcon("images/button-sel.png"));
        button.setRolloverSelectedIcon(new ImageIcon("images/button-sel-over.png"));
        button.setDisabledIcon(new ImageIcon("images/button-disabled.png"));
        button.setDisabledSelectedIcon(new ImageIcon("images/button-disabled-selected.png"));
        button.setLocation(60,74);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                button.setSelected(!button.isSelected());
                System.out.println("selecting");
            }
        });
        //button.setSelected(true);
        //button.setDisabled(false);
        panel.add(button);
        
        
        final JCheckBox checkbox = new JCheckBox("Disable");
        checkbox.setLocation(70,150);
        checkbox.setOpaque(false);
        checkbox.setSize(checkbox.getPreferredSize());
        panel.add(checkbox);
        checkbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                button.setEnabled(!checkbox.isSelected());
            }
        });
        
        

        JFrame frame = new JFrame("Hack #XX: Image Components");
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
