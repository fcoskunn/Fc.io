package game.fc.io;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.security.SecureRandom;

public class Cell implements Comparable<Cell> {
	private String name = "";
	private int xPos, yPos;
	private double radius;
	private Color color;
	private boolean isDead = false;
	private int targetPosX;
	private int targetPosY;
	private double velocity;
	private static int cellID = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		if (xPos < GamePanel.mapWidth && xPos > 0)
			this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		if (yPos < GamePanel.mapHeigh && yPos > 0)
			this.yPos = yPos;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setTarget(Cell[] cells, Dot[] dots) {
		int cellDistance = 10000;
		Cell dangerCell = new Cell();
		Dot targetDot = new Dot();
		for (int i = 0; i < cells.length; i++) {
			int distance = (int) Math
					.sqrt(Math.pow((cells[i].getxPos() - xPos), 2) + Math.pow((cells[i].getyPos() - yPos), 2));
			if ((distance < cellDistance && cells[i] != this)
					|| (distance < radius * 2 && cells[i].getRadius() > radius)) {
				cellDistance = distance;
				dangerCell = cells[i];
			}
		}
		int dotDistance = 10000;
		for (int i = 0; i < dots.length; i++) {
			int distance = (int) Math
					.sqrt(Math.pow((dots[i].getxPos() - xPos), 2) + Math.pow((dots[i].getyPos() - yPos), 2));
			if (distance < dotDistance) {
				dotDistance = distance;
				targetDot = dots[i];
			}
		}
		int vectorX = 1, vectorY = 1;
		if (dangerCell.getxPos() - xPos >= 0 && dangerCell.getyPos() - yPos >= 0) {
			vectorX = +1;
			vectorY = +1;
		} else if (dangerCell.getxPos() - xPos >= 0 && dangerCell.getyPos() - yPos <= 0) {
			vectorX = +1;
			vectorY = -1;
		} else if (dangerCell.getxPos() - xPos <= 0 && dangerCell.getyPos() - yPos >= 0) {
			vectorX = -1;
			vectorY = +1;
		} else if (dangerCell.getxPos() - xPos <= 0 && dangerCell.getyPos() - yPos <= 0) {
			vectorX = -1;
			vectorY = -1;
		}

		int vectorXForDots = 1, vectorYForDots = 1;

		if (targetDot.getxPos() - xPos >= 0 && targetDot.getyPos() - yPos >= 0) {
			vectorXForDots = +1;
			vectorYForDots = +1;
		} else if (targetDot.getxPos() - xPos >= 0 && targetDot.getyPos() - yPos <= 0) {
			vectorXForDots = +1;
			vectorYForDots = -1;
		} else if (targetDot.getxPos() - xPos <= 0 && targetDot.getyPos() - yPos >= 0) {
			vectorXForDots = -1;
			vectorYForDots = +1;
		} else if (targetDot.getxPos() - xPos <= 0 && targetDot.getyPos() - yPos <= 0) {
			vectorXForDots = -1;
			vectorYForDots = -1;
		}

		if (cellDistance < 3 * radius) {
			if (radius > dangerCell.getRadius()) {
				setTargetPosX((int) (xPos + cellDistance * 1.8 * vectorX));
				setTargetPosY((int) (yPos + cellDistance * 1.8 * vectorY));
			} else {
				setTargetPosX((int) (xPos - cellDistance * 1.8 * vectorX));
				setTargetPosY((int) (yPos - cellDistance * 1.8 * vectorY));
			}
		} else {
			setTargetPosX((int) (xPos + dotDistance * 1.8 * vectorXForDots));
			setTargetPosY((int) (yPos + dotDistance * 1.8 * vectorYForDots));
		}

	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity() {
		velocity = 0.08 / Math.sqrt(getRadius());
		// velocity ;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public int getTargetPosX() {
		return targetPosX;
	}

	public void setTargetPosX(int targetPosX) {
		this.targetPosX = targetPosX;
	}

	public int getTargetPosY() {
		return targetPosY;
	}

	public void setTargetPosY(int targetPosY) {
		this.targetPosY = targetPosY;
	}

	public Cell() {
		boolean IsProper = true;
		while (true) {
			SecureRandom random = new SecureRandom();
			int tempRadius = 35 + random.nextInt(15);
			int tempXPos = 10 + random.nextInt((int) (GamePanel.mapWidth - radius));
			int tempYPos = 10 + random.nextInt((int) (GamePanel.mapHeigh - radius));

			for (int i = 0; i < GamePanel.getCells().length; i++) {
				if (GamePanel.getCells()[i] == null
						|| Math.sqrt(Math.pow(GamePanel.getCells()[i].getxPos() - tempXPos, 2)
								+ Math.pow(GamePanel.getCells()[i].getyPos() - tempYPos,
										2)) > (tempRadius + GamePanel.getCells()[i].getRadius()) / 2 + 7) {
					IsProper = true;
				} else {
					IsProper = false;
					break;
				}
			}
			if (IsProper) {
				setRadius(tempRadius);
				setxPos(tempXPos);
				setyPos(tempYPos);
				setVelocity();
				setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				setName("Cell" + cellID);
				cellID++;
				setVelocity();
				break;
			}
		}
	}

	public void draw(Graphics g, int gamePanelX, int gamePanelY) {
		g.setColor(getColor());
		g.fillOval(xPos - gamePanelX - (int) getRadius() / 2, yPos - gamePanelY - (int) getRadius() / 2, (int) (radius),
				(int) (radius));
		Font f = new Font("Courier", Font.ITALIC, 15);
		g.setFont(f);
		if (getRadius() > 100) {
			g.setColor(Color.WHITE);
			g.drawString(name, (int) (xPos - gamePanelX - radius / 4), yPos - gamePanelY);
		}
	}

	public void move() {
		setxPos((int) ((getxPos() + (targetPosX - xPos) * velocity) + 0.01));
		setyPos((int) ((getyPos() + (targetPosY - yPos) * velocity) + 0.01));
	}

	public void eat(Cell[] cells, Dot[] dots) {
		for (int i = 0; i < cells.length; i++) {
			if (cells[i].getRadius() / radius < 0.95) {
				int distance = (int) Math
						.sqrt(Math.pow((cells[i].getxPos() - xPos), 2) + Math.pow((cells[i].getyPos() - yPos), 2));
				if ((distance * 2) < (radius)) {
					double V1 = cells[i].getRadius() * cells[i].getRadius();
					double V2 = radius * radius;
					radius = Math.sqrt((V1 + V2));
					cells[i].isDead = true;
					cells[i] = new Cell();
					break;
				}
			}
		}
		for (int i = 0; i < dots.length; i++) {
			int distance = (int) Math
					.sqrt(Math.pow((dots[i].getxPos() - xPos), 2) + Math.pow((dots[i].getyPos() - yPos), 2));
			if (distance * 2 < radius) {
				radius++;
				dots[i] = new Dot();
				break;
			}

		}

	}

	@Override
	public int compareTo(Cell o) {
		return Double.compare(o.radius, this.radius);
	}

}
