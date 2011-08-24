package swinghacks.ch07.Text.hack48;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.util.regex.*;

public class IncrementalSearch implements DocumentListener, ActionListener {
    protected JTextComponent content;
    protected Matcher matcher;
    public IncrementalSearch(JTextComponent comp) {
        this.content = comp;
    }
    
    /* DocumentListener implementation */
    public void insertUpdate(DocumentEvent evt) {
        runNewSearch(evt.getDocument());
    }
    public void removeUpdate(DocumentEvent evt) {
        runNewSearch(evt.getDocument());
    }
    public void changedUpdate(DocumentEvent evt) {
        runNewSearch(evt.getDocument());
    }
    
    /* ActionListener implementation */
    public void actionPerformed(ActionEvent evt) {
        continueSearch();
    }
    
    private void runNewSearch(Document query_doc) {
        try {
            String query = query_doc.getText(0,query_doc.getLength());
            Pattern pattern = Pattern.compile(query);
            Document content_doc = content.getDocument();
            String body = content_doc.getText(0,content_doc.getLength());
            matcher = pattern.matcher(body);
            continueSearch();
        } catch (Exception ex) {
            p("exception: " + ex);
            ex.printStackTrace();
        }
    }
    
    private void continueSearch() {
        if(matcher != null) {
            if(matcher.find()) {
                content.getCaret().setDot(matcher.start());
                content.getCaret().moveDot(matcher.end());
                content.getCaret().setSelectionVisible(true);
            }
        }
    }
            
    
    
    public static void main(String[] args) {
        JTextArea text_area = new JTextArea(10,20);
        JScrollPane scroll = new JScrollPane(text_area);
        IncrementalSearch isearch = new IncrementalSearch(text_area);

        JTextField search_field = new JTextField();
        search_field.getDocument().addDocumentListener(isearch);
        search_field.addActionListener(isearch);
        
        JFrame frame = new JFrame("Incremental Search Hack");
        frame.getContentPane().add("North",search_field);
        frame.getContentPane().add("Center",scroll);
        frame.pack();
        frame.show();
    }
    
    public static void p(String str) {
        System.out.println(str);
    }

}
