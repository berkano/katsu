package ext.pathfinding.test;

import ext.pathfinding.grid.GridMap;

import java.awt.*;

public class DrawGridMap {
	
	GridMap map;
	
	public DrawGridMap(GridMap map){
		this.map = map;
	}
	
	public GridMap getMap(){
		return map;
	}
	
	public void drawMap(Graphics2D g){
		int d = 8;
		g.translate(10, 10);
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(1));
		for(int i=0; i<map.getSizeX(); i++){
			for(int j=0; j<map.getSizeY(); j++){
				if(map.get(i, j) == GridMap.WALL){
					int x = d*i;
					int y = d*j;
					g.fillRect(x+1, y+1, d-2, d-2);
				}
			}
		}
		
		g.setColor(Color.gray);
		g.setStroke(new BasicStroke(6));
		g.drawRect(-6, -6, map.getSizeX()*d + 12, map.getSizeY()*d + 12);
		g.translate(-10, -10);
		
	}

}
