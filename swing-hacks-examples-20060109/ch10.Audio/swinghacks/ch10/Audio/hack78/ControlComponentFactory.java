package swinghacks.ch10.Audio.hack78;
import javax.swing.*;
import javax.sound.sampled.*;

public class ControlComponentFactory {

    private ControlComponentFactory() {super();}

    public static JComponent getComponentFor (Control control) {
        System.out.println (control.getType().getClass());
        if (control instanceof BooleanControl)
            return new BooleanControlComponent ((BooleanControl) control);
        else if (control instanceof FloatControl)
            return new FloatControlComponent ((FloatControl) control);
        return new JLabel ("unsupported");
    }

}
