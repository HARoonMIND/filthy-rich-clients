import javax.swing.plaf.metal.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.SystemColor;

public class NativeTheme extends DefaultMetalTheme {

    protected ColorUIResource getPrimary1() { 
        return new ColorUIResource(SystemColor.controlText);
    }
    // protected ColorUIResource getPrimary2() { 
    //     return new ColorUIResource(SystemColor.control);
    // }
    // protected ColorUIResource getPrimary3() { 
    //     return new ColorUIResource(255,255,255);
    // }
    
    // component borders
    // protected ColorUIResource getSecondary1() { 
    //     return new ColorUIResource(SystemColor.control);
    // }
    
    // component backgrounds
    protected ColorUIResource getSecondary3() { 
        return new ColorUIResource(SystemColor.control);
    }

    // selected components (button down state)
    // protected ColorUIResource getSecondary2() { 
    //     return new ColorUIResource(0,0,0);
    // }
    // component backgrounds
    // protected ColorUIResource getSecondary3() { 
    //     return new ColorUIResource(255,255,255);
    // }

}
