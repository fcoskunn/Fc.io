package game.fc.io;

import java.awt.Color;
import java.awt.Graphics;

public class Grid{	
	static int OldGridLength, GridLength=49,Xcomponent=5,Ycomponent=5;
	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		for (int i = Xcomponent; i <= Game.WindowWidth; i+=GridLength) {
			g.drawLine(i, 0, i, Game.WindowWidth);	
		}
		
		for (int i= Ycomponent; i <= Game.WindowHeight; i+=GridLength) {
			g.drawLine(0, i, Game.WindowWidth, i);
		}
	}
	public static void ChangeGridMargin(int radius,int posX, int posY) {
		OldGridLength=GridLength;
		GridLength=50-radius/20;
		Xcomponent=((int) ((Game.WindowWidth-posX)% GridLength));
		Ycomponent=((int) ((Game.WindowHeight-posY)% GridLength));
		if (GridLength<15) {
			GridLength=15;
		}
	}
	
}
