package swinghacks.ch11.NativeIntegrationAndPackaging.hack81;
import java.awt.*;
import javax.swing.*;
import java.util.Properties;

public class MenuNameDemo {

    static {
        System.setProperty ("com.apple.mrj.application.apple.menu.about.name",
                       "MyDemo");
    }

    public static void main (String[] args) {
        // meaningless - just want to keep the app
        // hanging around
        JFrame frame = new JFrame ("Empty JFrame");
        frame.setVisible(true);
    }

}
