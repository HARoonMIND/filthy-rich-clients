import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.net.*;
import javax.swing.text.*;

public class AWTBlockModels extends JFrame {

    JButton blockButton, dontBlockButton;
    JMenuItem blockMenuItem, dontBlockMenuItem, quitMenuItem;
    JTextField urlField;
    JTextArea contentArea;
    final static String DEFAULT_URL =
        "http://java.sun.com/j2se/1.4.2/docs/api/java/awt/Component.html";
    Thread loaderThread;
    JProgressBar progressBar;
    javax.swing.Timer progressBarUpdater;

    public AWTBlockModels () {
        super ("AWT Thread Blocking");
        initMainLayout();
        initMenus();
        initActions();
    }

    private void initMainLayout() {
        urlField = new JTextField (DEFAULT_URL, 60);
        JPanel topPanel = new JPanel ();
        topPanel.setLayout (new BoxLayout (topPanel, BoxLayout.Y_AXIS));
        topPanel.add (urlField);
        JPanel buttonPanel = new JPanel();
        blockButton = new JButton ("Load (blocking)");
        dontBlockButton = new JButton ("Load (non-blocking)");
        buttonPanel.add (blockButton);
        buttonPanel.add (dontBlockButton);
        topPanel.add (buttonPanel);
        contentArea = new JTextArea (25, 60);
        JScrollPane scroller = 
            new JScrollPane (contentArea,
                             ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add (topPanel, BorderLayout.NORTH);
        getContentPane().add (scroller, BorderLayout.CENTER);
        progressBar = new JProgressBar (0, 100);
        getContentPane().add (progressBar, BorderLayout.SOUTH);
    }

    private void initMenus() {
        JMenuBar bar = new JMenuBar();
        JMenu fileMenu = new JMenu ("File");
        blockMenuItem = new JMenuItem ("Load (blocking)");
        dontBlockMenuItem = new JMenuItem ("Load (non-blocking)");
        fileMenu.add (blockMenuItem);
        fileMenu.add (dontBlockMenuItem);
        fileMenu.addSeparator();
        quitMenuItem = new JMenuItem ("Quit");
        fileMenu.add (quitMenuItem);
        bar.add (fileMenu);
        setJMenuBar (bar);
    }

    private void initActions() {
        quitMenuItem.addActionListener (new QuitAction());
        BlockingLoadAction blocker = new BlockingLoadAction();
        blockButton.addActionListener (blocker);
        blockMenuItem.addActionListener (blocker);
        NonBlockingLoadAction nonBlocker = new NonBlockingLoadAction();
        dontBlockButton.addActionListener (nonBlocker);
        dontBlockMenuItem.addActionListener (nonBlocker);
    }

    private void makeProgressBarUpdaterFor (NonBlockingURLDocument nbud) {
        final NonBlockingURLDocument updatingDoc = nbud;
        updateProgressBar (0);
        ActionListener callback = new ActionListener() {
            public void actionPerformed (ActionEvent ev) {
                progressBar.setEnabled (true);
                int progress = (int) (updatingDoc.getProgress() * 100);
                updateProgressBar (progress);
                if (! updatingDoc.isAlive())
                    progressBarUpdater.stop();
            }
        };
        progressBarUpdater = new javax.swing.Timer (2000, callback);
        progressBarUpdater.start();
    }

    private void updateProgressBar (int progress) {
        // System.out.println ("update progress bar: " + progress);
        if (progress > 0) {
            progressBar.setValue (progress);
        }
        else
            progressBar.setEnabled (false);
    }

    public static void main (String[] args) {
        AWTBlockModels awtbm = new AWTBlockModels();
        awtbm.pack();
        awtbm.setVisible (true);
    }
    
    class QuitAction extends AbstractAction {
        public void actionPerformed (ActionEvent e) {
            System.exit(0);
        }
    }

    class BlockingLoadAction extends AbstractAction {
        public void actionPerformed (ActionEvent e) {
            BlockingURLDocument bud =
                new BlockingURLDocument (urlField.getText());
            progressBar.setEnabled (true);
            progressBar.setValue (0);
            contentArea.setDocument (bud);
            progressBar.setValue (100);
        }
    }

    class NonBlockingLoadAction extends AbstractAction  {
        public void actionPerformed (ActionEvent e) {
            NonBlockingURLDocument nbud =
                new NonBlockingURLDocument (urlField.getText());
            contentArea.setDocument (nbud);
            makeProgressBarUpdaterFor (nbud);
        }
    }

    class BlockingURLDocument extends PlainDocument {
        public BlockingURLDocument (String urlString) {
            super();
            try {
                URL url = new URL (urlField.getText());
                BufferedReader in =
                    new BufferedReader (
                        new InputStreamReader (url.openStream()));
                StringBuffer sbuf = new StringBuffer();
                char[] buffy = new char [16 * 1024];
                int bytesRead = 0;
                while ((bytesRead = in.read (buffy, 0, buffy.length)) > -1) {
                    sbuf.append (buffy, 0, bytesRead);
                    // if your net connection is too fast to see blocking
                    // add the following here
                    // Thread.sleep (1000);
                }
                remove (0, getLength());
                insertString (0, sbuf.toString(), null);
            } catch (Exception e) {
                CharArrayWriter writer = new CharArrayWriter();
                e.printStackTrace (new PrintWriter (writer));
                try {
                    remove (0, getLength());
                    insertString (0, writer.toString(), null);
                } catch (Exception e2) {e2.printStackTrace();}
            }
        }
    }

    class NonBlockingURLDocument extends PlainDocument 
        implements Runnable {
        protected int length = -1;
        protected int totalBytesRead = 0;
        protected String urlString;
        protected Thread readThread;
        public NonBlockingURLDocument (String urlString) {
            super();
            this.urlString = urlString;
            // start thread here
            readThread = new Thread (this);
            readThread.start();
        }
        public void run() {
            try {
                remove (0, getLength());
                URL url = new URL (urlField.getText());
                URLConnection conn = url.openConnection();
                length = conn.getContentLength();
                System.out.println ("length is " + length);
                BufferedReader in =
                    new BufferedReader (
                        new InputStreamReader (conn.getInputStream()));
                // char[] buffy = new char [16 * 1024];
                char[] buffy = new char [80];
                totalBytesRead = 0;
                int bytesRead = -1;
                while ((bytesRead = in.read (buffy, 0, buffy.length)) > -1) {
                    final String str = new String (buffy, 0, bytesRead);
                    final int finalTBR = totalBytesRead;
                    Runnable worker = new Runnable () {
                        public void run() {
                            try { 
                                insertString (finalTBR, str, null);
                            } catch (BadLocationException ble) {
                                ble.printStackTrace();
                            }
                        }
                    };
                    SwingUtilities.invokeLater (worker);
                    totalBytesRead += bytesRead;
                    System.out.println ("read " + totalBytesRead +
                                        " of " + length +
                                        ", progress == " + getProgress());
                    // if your net connection is too fast to see updating,
                    // make buffy smaller above (maybe 512 bytes) and
                    // add the following here:
                    Thread.sleep (500);
                }

            } catch (Exception e) {
                CharArrayWriter writer = new CharArrayWriter();
                e.printStackTrace (new PrintWriter (writer));
                try {
                    remove (0, getLength());
                    insertString (0, writer.toString(), null);
                } catch (Exception e2) {e2.printStackTrace();}
            } finally {
                readThread = null;
            }
        }
        public boolean isAlive() {
            return (readThread != null) && (readThread.isAlive());
        }
        public float getProgress() {
            return (float) totalBytesRead/length;
        }
        
    }

}
