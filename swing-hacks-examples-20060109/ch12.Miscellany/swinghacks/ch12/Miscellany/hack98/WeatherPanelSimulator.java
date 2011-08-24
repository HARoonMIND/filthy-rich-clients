package swinghacks.ch12.Miscellany.hack98;

import com.jonathansimon.swing.hacks.velocityhtml.WeatherPanel;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Jonathan Simon
 * Date: Mar 3, 2005
 * Time: 3:41:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class WeatherPanelSimulator {

    public WeatherPanelSimulator() {
        JFrame frame = new JFrame("Weather Panel Simulator");
        frame.setBounds(200,200, 500, 350);

        Weather weather1 = new Weather(new BigDecimal("82"), new BigDecimal("40.0"), new BigDecimal(1), "Monday");
        Weather weather2 = new Weather(new BigDecimal("75"), new BigDecimal("65.0"), new BigDecimal(1), "Tuesday");
        Weather weather3 = new Weather(new BigDecimal("85"), new BigDecimal("43.0"), new BigDecimal(1), "Wednessday");

        ArrayList list = new ArrayList();
        list.add(weather1);
        list.add(weather2);
        list.add(weather3);


        WeatherPanel weatherPanel = new WeatherPanel();
        weatherPanel.displayWeatherByFile("html/today.html", list);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(weatherPanel.getComponent(), BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();

    }


    public static void main(String[] args) {
        new WeatherPanelSimulator();
    }

}
