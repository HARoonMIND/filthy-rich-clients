package swinghacks.ch10.Audio.hack78;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.BooleanControl;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class BooleanControlComponent extends JPanel implements ActionListener {
	BooleanControl control;
	JCheckBox box;

	public BooleanControlComponent(BooleanControl c) {
		control = c;
		box = new JCheckBox();
		box.setSelected(control.getValue());
		add(box);
		box.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae) {
		control.setValue(box.isSelected());
	}
}
