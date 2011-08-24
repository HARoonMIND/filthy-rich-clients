package swinghacks.ch12.Miscellany.hack98;

import com.jonathansimon.swing.hacks.velocityhtml.VelocityHtmlPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jonathan Simon
 * Date: Mar 3, 2005
 * Time: 3:41:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class VelocityHtmlPanelSimulator {

    public VelocityHtmlPanelSimulator() {
        JFrame frame = new JFrame("Velocity HTML Panel Simulator");
        frame.setBounds(200,200, 500, 350);

        VelocityHtmlPanel velocityHtmlPanel = new VelocityHtmlPanel();

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(velocityHtmlPanel.getComponent(), BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();

    }


    public static void main(String[] args) {
        new VelocityHtmlPanelSimulator();
    }

}
