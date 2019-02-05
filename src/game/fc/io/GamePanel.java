package game.fc.io;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static Cell[] cells = new Cell[200];
	private static Dot[] dots = new Dot[600];
	private playerCell player = new playerCell();
	private Grid grid = new Grid();
	public static int mapWidth = 4000;
	public static int mapHeigh = 3000;
	private int gameWidth = player.getxPos() - Game.WindowWidth / 2;
	private int gameHeigh = player.getyPos() - Game.WindowHeight / 2;
	private double ZoomComponent;
	private LeaderBoard board;
	Timer timer;
	private double InitialTime = System.currentTimeMillis();
	private Graphics2D g2;
	
	public  Boolean win = null;
	public playerCell getPlayer() {
		return player;
	}

	public int getGameHeigh() {
		return gameHeigh;
	}

	public double getZoomComponent() {
		return ZoomComponent;
	}

	public void setZoomComponent(double zoomComponent) {
		ZoomComponent = zoomComponent;
	}

	public static Cell[] getCells() {
		return cells;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setGameWidth(int gameWidth) {
		this.gameWidth = gameWidth;
	}

	public GamePanel() {
		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell();
		}

		for (int i = 0; i < dots.length; i++) {
			dots[i] = new Dot();
		}
		player.setRadius(50);
		cells[0] = player;
		board = new LeaderBoard();
		player.setName("Player");
		timer = new Timer(10, this);
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		grid.draw(g);
		g2 = (Graphics2D) g;
		setBackground(Color.getHSBColor(50, 40, 25));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (int i = 1; i < cells.length; i++) {
			cells[i].draw(g2, gameWidth, gameHeigh);
			// ((50 - (player.getRadius() / 20.0)) / 50.0)
		}
		for (int i = 0; i < dots.length; i++) {
			dots[i].draw(g2, gameWidth, gameHeigh);
		}
		board.draw(g2, cells);
		player.draw(g2, gameWidth, gameHeigh);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g2 = (Graphics2D) g;
		if(win!=null && win) {
			
			g2.setColor(Color.red);
			g2.setFont(new Font("Times New Roman", Font.BOLD, 50));
			g2.drawString("You Win", Game.WindowWidth/2-100, Game.WindowHeight/2-50);
		}
		else if(win!=null) {
			g2.setColor(Color.ORANGE);
			g2.setFont(new Font("Times New Roman", Font.BOLD, 50));
			g2.drawString("You Lose", Game.WindowWidth/2-100, Game.WindowHeight/2-50);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		player.setTarget(gameWidth, gameHeigh);
		gameWidth = player.getxPos() - Game.WindowWidth / 2;
		gameHeigh = player.getyPos() - Game.WindowHeight / 2;
		repaint();
		Arrays.sort(cells);
		for (int i = 0; i < cells.length; i++) {
			cells[i].eat(cells, dots);
			cells[i].move();
			cells[i].setVelocity();

		}
		if (player.isDead()) {
			timer.stop();
			for (int i = 0; i < cells.length; i++) {
				cells[i].setColor(Color.black);
				player.setColor(Color.black);
				win=false;
			}
			remove(this);
		}
		if (player.getRadius() > 500) {
			repaint();
			timer.stop();
			for (int i = 0; i < cells.length; i++) {
				cells[i].setColor(Color.blue);
				player.setColor(Color.blue);
				win=true;
			}
			remove(this);
		}
		if ((System.currentTimeMillis() - InitialTime) % 200 < 100) {
			for (int i = 0; i < cells.length; i++) {
				cells[i].setTarget(cells, dots);
			}
		}
	}
}
