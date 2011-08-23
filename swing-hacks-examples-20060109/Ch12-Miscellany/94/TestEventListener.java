import java.util.*;

public class TestEventListener extends Object 
    implements EventListener {
    String id;
    public TestEventListener (String id) {
        this.id = id;
    }
    public void handleEvent (EventObject o) {
        System.out.println (id + " called");
        if (id.equals ("C")) {
            ((TestEventSource) o.getSource()).removeListener (this);
        }
    }
}
