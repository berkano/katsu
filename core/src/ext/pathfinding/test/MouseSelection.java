package ext.pathfinding.test;

import ext.pathfinding.grid.GridLocation;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseSelection {
	
	int startX = -1;
	int startY;
	int endX = -1;
	int endY;
	
	public boolean isSet(){
		return (startX != -1 && endX != -1);
	}
	
	public GridLocation getStart(){
		GridLocation location = new GridLocation(startX, startY, false);
		return location;
	}
	
	public GridLocation getEnd(){
		GridLocation location = new GridLocation(endX, endY, true);
		return location;
	}
	
	public boolean setMouse(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1){
			int x = (e.getX()-10)/8; //Translation and size of a cell...
			int y = (e.getY()-10)/8; //Translation and size of a cell...
			if(x >= 0 && x < 100 && y >= 0 && y < 100){
				startX = x;
				startY = y;
				return true;
			}
		}else if(e.getButton() == MouseEvent.BUTTON3){
			int x = (e.getX()-10)/8; //Translation and size of a cell...
			int y = (e.getY()-10)/8; //Translation and size of a cell...
			if(x >= 0 && x < 100 && y >= 0 && y < 100){
				endX = x;
				endY = y;
				return true;
			}
		}
		return false;
	}
	
	public void drawMouseSelection(Graphics2D g){
		int d = 8;
		int t = 10;
		g.translate(t, t);
		g.setColor(Color.red);
		if(startX != -1){
			g.fillRect(startX*d, startY*d, d, d);
		}
		g.setColor(Color.blue);
		if(endX != -1){
			g.fillRect(endX*d, endY*d, d, d);
		}
		g.translate(-t, -t);
	}

}
