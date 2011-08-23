import java.util.*;
import javax.swing.event.*;

public class EventListenerListEventSource 
    extends TestEventSource {

    EventListenerList listenerList = new EventListenerList();

    public void addListener (TestEventListener l) {
        listenerList.add (TestEventListener.class, l);
    }
    
    public void removeListener (TestEventListener l) {
        listenerList.remove (TestEventListener.class, l);
    }

    public void fireEvent (EventObject o) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i] == TestEventListener.class) {
                ((TestEventListener) listeners[i+1]).handleEvent(o);
            }
        }
    }

    public static void main (String[] args) {
        EventListenerListEventSource bfles = 
            new EventListenerListEventSource();
        bfles.test();
    }


}
