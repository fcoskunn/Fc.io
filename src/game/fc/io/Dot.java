package game.fc.io;

import java.awt.Color;
import java.awt.Graphics;
import java.security.SecureRandom;

public class Dot {
	private int xPos, yPos, radius = 5;

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public Dot() {
		boolean IsProper = true;
		while (true) {
			SecureRandom random = new SecureRandom();
			int tempRadius = 35 + random.nextInt(10);
			int tempXPos = 10 + random.nextInt(GamePanel.mapWidth - radius);
			int tempYPos = 10 + random.nextInt(GamePanel.mapHeigh - radius);

			for (int i = 0; i < GamePanel.getCells().length; i++) {
				if (GamePanel.getCells()[i] == null
						|| Math.sqrt(Math.pow(GamePanel.getCells()[i].getxPos() - tempXPos, 2)
								+ Math.pow(GamePanel.getCells()[i].getyPos() - tempYPos,
										2)) > (tempRadius + GamePanel.getCells()[i].getRadius()) / 2 + 5) {
					IsProper = true;
				} else {
					IsProper = false;
					break;
				}
			}
			if (IsProper) {
				setxPos(tempXPos);
				setyPos(tempYPos);
				break;
			}
		}

	}

	public void draw(Graphics g, int gamePanelX, int gamePanelY) {
		g.setColor(Color.red);
		g.fillRect(xPos - gamePanelX - 5, yPos - gamePanelY - 5, radius, radius);
	}

}