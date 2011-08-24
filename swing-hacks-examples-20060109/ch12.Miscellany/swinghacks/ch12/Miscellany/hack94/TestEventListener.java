package swinghacks.ch12.Miscellany.hack94;

import java.util.EventListener;
import java.util.EventObject;

public class TestEventListener extends Object implements EventListener {
	String id;

	public TestEventListener(String id) {
		this.id = id;
	}

	public void handleEvent(EventObject o) {
		System.out.println(id + " called");
		if (id.equals("C")) {
			((TestEventSource) o.getSource()).removeListener(this);
		}
	}
}
