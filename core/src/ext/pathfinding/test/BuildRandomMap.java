package ext.pathfinding.test;

import ext.pathfinding.grid.GridMap;

public class BuildRandomMap {
	
	GridMap map;
	
	public BuildRandomMap(int sizeX, int sizeY){
		map = new GridMap(sizeX, sizeY);
		for(int i=0; i<sizeX; i++){
			for(int j=0; j<sizeY; j++){
				int value;
				if(Math.random() > 0.32){
					value = 1;
				}else{
					value = GridMap.WALL;
				}
				map.set(i, j, value);
			}
		}
	}
	
	public GridMap getMap(){
		return map;
	}

}
