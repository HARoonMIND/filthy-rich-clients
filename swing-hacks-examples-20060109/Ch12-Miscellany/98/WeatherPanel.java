
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.io.*;
import java.util.Properties;
import java.util.Collection;
import java.util.Iterator;

public class WeatherPanel {
    private JEditorPane htmlPane;

    public WeatherPanel() {
        htmlPane = createHtmlPanel();
    }

    public void displayWeather(String html, Collection weather){
        String result = html;
        try {
            VelocityContext context = createContext(weather);
            result = processString(context, html);
        } catch (Exception e){
            e.printStackTrace();
        }
        htmlPane.setText(result);
    }

    public void displayWeatherByFile(String fileName, Collection weather){
        displayWeather(readFile(fileName), weather);
    }

    private JEditorPane createHtmlPanel() {
        JEditorPane editorPane = new JEditorPane();
        HTMLEditorKit editorKit = new HTMLEditorKit();
        editorKit.install(editorPane);
        editorPane.setEditorKit(editorKit);
        editorPane.setEditable(false);
        return editorPane;
    }

    public Component getComponent() {
        return new JScrollPane(htmlPane);
    }

    private String readFile(String fileName) {
        StringBuffer htmlBuffer = new StringBuffer();

        try {
            InputStream inputStream = WeatherPanel.class.getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            while (true){
                String line = reader.readLine();
                if (line != null){
                    htmlBuffer.append(line);
                } else {
                    break;
                }
            }
        } catch (IOException iox){
            iox.printStackTrace();
        }
        return htmlBuffer.toString();
    }

    private String processString(VelocityContext context, String htmlText) throws Exception {
        StringWriter writer = new StringWriter();
        Properties properties = new Properties();
        Velocity.init(properties);
        Velocity.evaluate(context,
                writer,
                null,
                htmlText);
        return writer.getBuffer().toString();
    }

    private VelocityContext createContext(Collection weatherCollection) {
        VelocityContext context = new VelocityContext();
        int index = 1;

        for (Iterator iterator = weatherCollection.iterator(); iterator.hasNext();) {
            Weather weather = (Weather) iterator.next();
            String day = "DAY" + index;
            context.put(day, weather.getDay());
            context.put(day + "_TEMP", weather.getTemperature());
            context.put(day + "_HUMIDITY", weather.getHumidity());
            context.put(day + "_PRESSURE", weather.getPressure());
            index++;
        }

        return context;
    }

}
