package ext.pathfinding.grid.astar;

import ext.pathfinding.grid.GridLocation;

public interface GridHeuristic {
	
	public double getDistance(int x, int y, GridLocation location);

}
