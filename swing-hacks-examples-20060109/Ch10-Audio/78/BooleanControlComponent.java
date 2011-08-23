import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;

public class BooleanControlComponent extends JPanel
    implements ActionListener {
    BooleanControl control;
    JCheckBox box;
    public BooleanControlComponent (BooleanControl c) {
        control = c;
        box = new JCheckBox ();
        box.setSelected (control.getValue());
        add (box);
        box.addActionListener (this);
    }

    public void actionPerformed (ActionEvent ae) {
        control.setValue (box.isSelected());
    }
}
