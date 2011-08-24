package swinghacks.ch10.Audio.hack78;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class ControlComponentFactory {

	private ControlComponentFactory() {
		super();
	}

	public static JComponent getComponentFor(Control control) {
		System.out.println(control.getType().getClass());
		if (control instanceof BooleanControl)
			return new BooleanControlComponent((BooleanControl) control);
		else if (control instanceof FloatControl)
			return new FloatControlComponent((FloatControl) control);
		return new JLabel("unsupported");
	}

}
