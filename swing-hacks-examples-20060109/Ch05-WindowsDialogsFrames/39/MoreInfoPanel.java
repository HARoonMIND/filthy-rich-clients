import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MoreInfoPanel extends JPanel {

    public Component topComponent;
    protected SpinWidget spinWidget;
    public Component bottomComponent;

    public static final int SPIN_WIDGET_HEIGHT = 14;

    public MoreInfoPanel (Component tc, Component mic) {
        topComponent = tc;
        spinWidget = new SpinWidget();
        bottomComponent = mic;
        doMyLayout();
    }


    protected void doMyLayout() {
        setLayout (new BoxLayout (this, BoxLayout.Y_AXIS));
        add (topComponent);
        add (spinWidget);
        add (bottomComponent);
        resetBottomVisibility();
    }

    protected void resetBottomVisibility() {
        if ((bottomComponent == null) ||
            (spinWidget == null))
            return;
        bottomComponent.setVisible (spinWidget.isOpen());
        revalidate();
        if (isShowing()) {
            Container ancestor = getTopLevelAncestor();
            if ((ancestor != null) && (ancestor instanceof Window))
                ((Window) ancestor).pack();
            repaint();
        }
    }

    public void showBottom (boolean b) {
        spinWidget.setOpen (b);
    }

    public boolean isBottomShowing () {
        return spinWidget.isOpen();
    }

    
    public class SpinWidget extends JPanel {
        boolean open;
        Dimension mySize = new Dimension (SPIN_WIDGET_HEIGHT,
                                          SPIN_WIDGET_HEIGHT);
        final int HALF_HEIGHT = SPIN_WIDGET_HEIGHT / 2;
        int[] openXPoints =
            { 1, HALF_HEIGHT, SPIN_WIDGET_HEIGHT-1};
        int[] openYPoints =
            { HALF_HEIGHT, SPIN_WIDGET_HEIGHT-1, HALF_HEIGHT};
        int[] closedXPoints =
            { 1, 1, HALF_HEIGHT};
        int[] closedYPoints =
            { 1, SPIN_WIDGET_HEIGHT-1, HALF_HEIGHT };
        Polygon openTriangle = 
            new Polygon (openXPoints, openYPoints, 3);
        Polygon closedTriangle = 
            new Polygon (closedXPoints, closedYPoints, 3);

        public SpinWidget() {
            setOpen (false);
            addMouseListener (new MouseAdapter() {
                    public void mouseClicked (MouseEvent e) {
                        handleClick();
                    }
                });
        }
        
        public void handleClick() {
            setOpen (! isOpen());
        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen (boolean o) {
            open = o;
            resetBottomVisibility();
        }

        public Dimension getMinimumSize() { return mySize; }
        public Dimension getPreferredSize() { return mySize; }

        // don't override update(), get the default clear
        public void paint (Graphics g) {
            if (isOpen())
                g.fillPolygon (openTriangle);
            else
                g.fillPolygon (closedTriangle);
        }

    }

}
