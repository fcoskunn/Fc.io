package game.fc.io;

import java.awt.Graphics;
import java.awt.MouseInfo;


public class playerCell extends Cell{
	
    int targetX;
    int targetY;
    
	public void draw(Graphics g, int gamePanelX, int gamePanelY) {
		g.setColor(getColor());
		g.fillOval(Game.WindowWidth/2-(int)getRadius()/2,Game.WindowHeight/2-(int)getRadius()/2 ,(int)getRadius(),(int)getRadius());
	}
	
	public void setTarget(int gamePointX, int gamePointY) {
		setTargetPosX(MouseInfo.getPointerInfo().getLocation().x+gamePointX);
		setTargetPosY(MouseInfo.getPointerInfo().getLocation().y+gamePointY);	
	}
	
	@Override
	public void eat(Cell[] cells,Dot[] dots) {
		super.eat(cells,dots);
		Grid.ChangeGridMargin((int)getRadius(), getxPos(), getyPos());
	}
	
}
