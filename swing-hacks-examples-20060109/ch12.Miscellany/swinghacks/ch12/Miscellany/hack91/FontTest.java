package swinghacks.ch12.Miscellany.hack91;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FontTest {
	public static void p(String str) {
		System.out.println(str);
	}

	public static void main(String[] args) {
		final int w = 20;
		final int side = 25;
		final int[][] grid = new int[50][w];

		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				Font font = new Font("WingDings", Font.PLAIN, 14);
				g.setFont(font);
				g.setColor(Color.black);
				p("font: " + font);
				int off = 0;
				for (int i = 0; i < w; i++) {
					g.drawLine(i * side - 2, 0, i * side - 2, getHeight());
				}
				for (int i = 0; i < (getHeight() / side); i++) {
					g.drawLine(0, i * side + 5, getWidth(), i * side + 5);
				}

				for (int i = 0; i < 256 * 256; i++) {
					if (font.canDisplay((char) i)) {
						off++;
						grid[off / w][off % w] = i;
						int x = off % w * side;
						int y = (off / w) * side + side;
						g.drawString("" + (char) i, x, y);
						//System.out.println(Integer.toHexString(i) +" " + ((char)i) + " | " + x + " x " + y);
					}
				}
			}
		};
		panel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				int x = evt.getX() / side;
				int y = evt.getY() / side;
				p("hit: " + x + " " + y + " char = " + Integer.toHexString(grid[y][x]));
			}
		});

		JFrame frame = new JFrame("asdf");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setSize(300, 300);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setSize(300, 300);
		frame.setVisible(true);
		p("\ndone\n");
	}
}
