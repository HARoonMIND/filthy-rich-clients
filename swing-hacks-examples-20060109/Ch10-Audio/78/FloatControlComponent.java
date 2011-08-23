import javax.swing.event.*;
import javax.swing.*;
import javax.sound.sampled.*;

public class FloatControlComponent extends JPanel 
    implements ChangeListener {

    FloatControl control;
    JSlider slider;
    float min, max, range;
    final static int SLIDER_MIN = 0;
    final static int SLIDER_MAX = 1000;
    final static float SLIDER_RANGE = SLIDER_MAX - SLIDER_MIN;

    public FloatControlComponent (FloatControl c) {
        control = c;
        /*
        System.out.println ("Control " + control +
                            ": min = " +  control.getMinimum() +
                            ", max = " +  control.getMaximum());
        */
        min = c.getMinimum();
        max = c.getMaximum();
        range = max - min;
        add (new JLabel (control.getMinLabel()));
        slider = new JSlider (SLIDER_MIN, SLIDER_MAX);
        slider.addChangeListener (this);
        setSliderFromControl();
        add (slider);
        add (new JLabel (control.getMaxLabel()));
    }

    private void setSliderFromControl() {
        // figure out value as percent of range
        float offsetValue = control.getValue() - min;
        // System.out.println ("offsetValue = " + offsetValue);
        float percent = 0.0f;
        if (range != 0.0)
            percent = offsetValue / range;
        // System.out.println ("percent = " + offsetValue/range);
        // apply that to SLIDER_RANGE
        int sliderValue = (int) (percent * SLIDER_RANGE);
        // System.out.println ("sliderValue = " + sliderValue);
        slider.setValue (sliderValue);
    }

    private void setControlFromSlider() {
        // figure out slider percentage
        float sliderPercentage =
            (float) slider.getValue() / SLIDER_RANGE;
        // System.out.println ("new slider value = " + slider.getValue() +
        //                    ", percentage= " + sliderPercentage);
        // figure out value for that percentage of range
        float rangeOffset = sliderPercentage * range;
        float newValue = rangeOffset + min;
        // System.out.println ("rangeOffset = " + rangeOffset +
        //                     ", newValue = " + newValue);
        control.setValue (newValue);
    }

    // ChangeListener implementation
    public void stateChanged (ChangeEvent e) {
        setControlFromSlider();
    }

}
