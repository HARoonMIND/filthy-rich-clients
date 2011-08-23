import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.border.*;

public class SpotlightDemo extends JFrame
{
    private SpotlightPanel glassPane;
    private JComponent[] books;

    public SpotlightDemo()
    {
		super("Spotlight Demo");

		glassPane = new SpotlightPanel(true);
		this.setGlassPane(glassPane);

        Container c = new GradientPanel();
        setContentPane(c);
		c.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        HeaderPanel header = new HeaderPanel();
        headerPanel.add(BorderLayout.NORTH, header);
        headerPanel.add(BorderLayout.SOUTH, new JSeparator(JSeparator.HORIZONTAL));
        c.add(BorderLayout.NORTH, headerPanel);

        JPanel booksPanel = new GradientPanel();
        booksPanel.setOpaque(false);
        booksPanel.setLayout(new GridLayout(2, 3));
        booksPanel.setBorder(new EmptyBorder(6, 0, 0, 0));
        createBooks(booksPanel);
        c.add(BorderLayout.CENTER, booksPanel);

        JPanel searchPanel = new JPanel();
        searchPanel.setOpaque(false);
        JLabel label = new JLabel("Search for books:");
        searchPanel.add(label);
        JTextField searchField = new JTextField(12);
        searchField.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                String text = ((JTextField) evt.getSource()).getText();
                if ("sci-fi".equals(text))
                    createSciFiSpots();
                else if ("books".equals(text))
                    createSpotsForAll();
                else if ("pratchett".equals(text))
                    createPratchettSpots();
                else if ("adams".equals(text))
                    createAdamsSpots();
            }
        });
        searchPanel.add(searchField);
        c.add(BorderLayout.SOUTH, searchPanel);

        pack();
        setResizable(false);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createSpotsForAll()
    {
        for (int i = 0; i < 6; i++)
        {
            addSpotForBook(i);
        }
    }

    private void createSciFiSpots()
    {
        addSpotForBook(0);
        for (int i = 3; i < 6; i++)
        {
            addSpotForBook(i);
        }
    }

    private void createAdamsSpots()
    {
        for (int i = 3; i < 6; i++)
        {
            addSpotForBook(i);
        }
    }

    private void createPratchettSpots()
    {
        addSpotForBook(2);
    }

    private void addSpotForBook(int i)
    {
        Point p = new Point(books[i].getLocation());
        SwingUtilities.convertPointToScreen(p, books[i].getParent());
        SwingUtilities.convertPointFromScreen(p, glassPane);
        glassPane.addSpotlight(p.x - 4, p.y - 4, 96, 152);
    }

    private void createBooks(JPanel container)
    {
        books = new JComponent[6];
        for (int i = 0; i < books.length; i++)
        {
            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);
            buttonPanel.add(books[i] = UIHelper.createLabel("", "cover" + (i + 1) + "_small_button"));
            container.add(buttonPanel);
        }
    }

    private static class GradientPanel extends JPanel
    {
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            if (!isOpaque())
            {
                return;
            }

            int width  = getWidth();
            int height = getHeight();
    
            Graphics2D g2 = (Graphics2D) g;
    
            Paint storedPaint = g2.getPaint();
            g2.setPaint(new GradientPaint(0, 0, Color.WHITE, width, height, new Color(200, 200, 200)));
            g2.fillRect(0, 0, width, height);
            g2.setPaint(storedPaint);
        }
    }

    public static void main(String[] args)
    {
        SpotlightDemo demo = new SpotlightDemo();
        demo.setVisible(true);
    }
}