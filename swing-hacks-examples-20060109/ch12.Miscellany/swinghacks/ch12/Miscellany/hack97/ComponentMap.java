package swinghacks.ch12.Miscellany.hack97;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class ComponentMap extends HashMap implements AWTEventListener {
    
    public ComponentMap() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.addAWTEventListener(this,
            AWTEvent.COMPONENT_EVENT_MASK);
    }

    public void eventDispatched(AWTEvent evt) {
        try {
            // p("evt = " + evt);
            ComponentEvent ce = (ComponentEvent)evt;
            // p("storing component: " + ce.getComponent().getName());
            this.put(
                ce.getComponent().getName(),
                ce.getComponent()
                );
        } catch (Exception ex) {
            // p("ex: " + ex);
        }
    }

}
