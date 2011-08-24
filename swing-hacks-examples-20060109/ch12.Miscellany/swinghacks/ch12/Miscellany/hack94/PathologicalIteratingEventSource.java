package swinghacks.ch12.Miscellany.hack94;
import java.util.*;

public class PathologicalIteratingEventSource 
    extends TestEventSource {

    ArrayList listeners = new ArrayList();

    public void addListener (TestEventListener l) {
        listeners.add (l);
    }
    
    public void removeListener (TestEventListener l) {
        listeners.remove (l);
    }

    public void fireEvent (EventObject o) {
        Iterator it = listeners.iterator();
        while (it.hasNext()) {
            TestEventListener l = (TestEventListener) it.next();
            l.handleEvent (o);
        }
    }

    public static void main (String[] args) {
        PathologicalIteratingEventSource pies = 
            new PathologicalIteratingEventSource();
        pies.test();
    }


}
