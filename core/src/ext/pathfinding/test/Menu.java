package ext.pathfinding.test;

import java.awt.*;

public class Menu {
	
	public void drawMenu(Graphics2D g){
		//Starting point
		g.setColor(Color.red);
		g.fillRect(2, 830, 8, 8);
		g.setColor(Color.black);
		g.drawString("Starting Point", 13, 838);
		//End point
		g.setColor(Color.blue);
		g.fillRect(202, 830, 8, 8);
		g.setColor(Color.black);
		g.drawString("Ending Point", 213, 838);
		//Path
		g.setColor(Color.orange);
		g.fillRect(402, 830, 8, 8);
		g.setColor(Color.black);
		g.drawString("Path", 413, 838);
		//Wall
		g.setColor(Color.black);
		g.fillRect(602, 830, 6, 6);
		g.setColor(Color.black);
		g.drawString("Wall", 613, 838);
	}

}
