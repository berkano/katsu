package ext.pathfinding.grid.astar;

import ext.pathfinding.grid.GridLocation;

public interface GridHeuristic {
	
	double getDistance(int x, int y, GridLocation location);

}
