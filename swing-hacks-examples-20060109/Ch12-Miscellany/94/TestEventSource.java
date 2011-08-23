public abstract class TestEventSource {
    public abstract void addListener (TestEventListener l);
    public abstract void removeListener (TestEventListener l);
    public abstract void fireEvent (java.util.EventObject o);
    public void test() {
        addListener (new TestEventListener ("A"));
        addListener (new TestEventListener ("B"));
        addListener (new TestEventListener ("C"));
        addListener (new TestEventListener ("D"));
        addListener (new TestEventListener ("E"));
        fireEvent(new java.util.EventObject(this));
    }
}
