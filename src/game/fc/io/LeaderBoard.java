package game.fc.io;

import java.awt.Color;
import java.awt.Graphics;

public class LeaderBoard {
	public void draw(Graphics g, Cell[] c) {
		Color color=new Color(50,50,50,20);
		g.setColor(color);
		g.fillRect(Game.WindowWidth-175, 25, 175, 400);
		for (int i = 0; i < 15; i++) {
			g.setColor(c[i].getColor());
			g.drawString(c[i].getName()+"("+(int)c[i].getRadius()+")", 1200, 50+i*25);
		}
		g.setColor(Color.black);
	}

}
