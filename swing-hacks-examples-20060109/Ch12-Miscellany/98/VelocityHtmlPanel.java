
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.io.*;
import java.util.Properties;
import java.math.BigDecimal;

public class VelocityHtmlPanel {
    private JEditorPane htmlPane;

    public VelocityHtmlPanel() {
        htmlPane = createHtmlPanel();

        String htmlText = readFile("html/today.html");
        String htmlTextPostTemplate = runTemplateAgainstHtml(htmlText);

        htmlPane.setText(htmlTextPostTemplate);


    }

    private JEditorPane createHtmlPanel() {
        JEditorPane editorPane = new JEditorPane();
        HTMLEditorKit editorKit = new HTMLEditorKit();
        editorKit.install(editorPane);
        editorPane.setEditorKit(editorKit);
        editorPane.setEditable(false);
        return editorPane;
    }

    private String runTemplateAgainstHtml(String htmlText) {
        String result = htmlText;
        try {

            Weather weather = new Weather(new BigDecimal("86.9"), new BigDecimal("68"), new BigDecimal("5"));
            VelocityContext context = createContext(weather);
            result = processString(context, htmlText);
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public Component getComponent() {
        return new JScrollPane(htmlPane);
    }

    private String readFile(String fileName) {
        StringBuffer htmlBuffer = new StringBuffer();

        try {
            InputStream inputStream = VelocityHtmlPanel.class.getResourceAsStream(fileName);
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
        properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        Velocity.init(properties);
        Velocity.evaluate(context,
                writer,
                "LOG", // used for logging
                htmlText);
        return writer.getBuffer().toString();
    }

    private VelocityContext createContext(Weather weather) {
        VelocityContext context = new VelocityContext();
        context.put("TEMP", weather.getTemperature() + " F");
        context.put("HUMIDITY", weather.getHumidity() + " %");
        context.put("PRESSURE", "10" + " bars");
        return context;
    }

}
