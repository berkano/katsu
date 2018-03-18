package katsu.spatial.pathfinding.grid;

import katsu.spatial.pathfinding.core.Map;
import katsu.spatial.pathfinding.grid.util.GridDouble;

public class GridMap extends GridDouble implements Map {

	public static final int WALL = -1;
	
	private final double minimumValue = 0.01;
	
	public GridMap(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void set(int x, int y, double value){
		if(value == WALL){
			super.set(x, y, value);
		}else{
			if(value < minimumValue){
				value = minimumValue;
			}
			super.set(x, y, value);
		}
	}
	
	@Override
	public void reset(double value){
		if(value == WALL){
			super.reset(value);
		}else{
			if(value < minimumValue){
				value = minimumValue;
			}
			super.reset(value);
		}
	}

}
