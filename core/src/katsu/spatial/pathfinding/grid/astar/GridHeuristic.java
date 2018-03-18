package katsu.spatial.pathfinding.grid.astar;

import katsu.spatial.pathfinding.grid.GridLocation;

public interface GridHeuristic {
	
	double getDistance(int x, int y, GridLocation location);

}
