package swinghacks.ch08.Rendering.hack62;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;

public class VectorButtonTest {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		JButton vb = new VectorButton();
		vb.setFont(new Font("Dialog", Font.PLAIN, 36));
		vb.setForeground(new Color(50, 50, 255));
		vb.setBackground(Color.white);
		vb.setForeground(new Color(50, 255, 0));
		vb.setText("Button");
		// vb.setMargin(new Insets(0,30,0,30));
		vb.setBorderPainted(false);
		frame.getContentPane().add(vb);
		frame.pack();
		frame.setVisible(true);
	}

}

//     public static void p(String s) {
//         System.out.println(s);
//     }
// }
