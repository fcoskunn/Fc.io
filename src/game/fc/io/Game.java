package game.fc.io;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Game {
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int WindowWidth = (int) screenSize.getWidth();
	static int WindowHeight = (int) screenSize.getHeight();

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		GamePanel panel = new GamePanel();
		frame.setTitle("Fc.io");
		frame.setSize(WindowWidth, WindowHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setVisible(true);
		
	}
}
