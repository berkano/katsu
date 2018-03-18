package katsu.spatial.pathfinding.grid;

import katsu.spatial.pathfinding.core.Location;
import katsu.spatial.pathfinding.core.Map;
import katsu.spatial.pathfinding.core.Pathfinding;
import katsu.spatial.pathfinding.grid.astar.GridAstar;
import katsu.spatial.pathfinding.grid.astar.GridHeuristic;
import katsu.spatial.pathfinding.grid.astar.GridHeuristicManhattan;

public class GridPathfinding implements Pathfinding{

	GridAstar astar;
	GridHeuristic heuristic;
	
	public GridPathfinding(){
		heuristic = new GridHeuristicManhattan();
	}
	
	@Override
	public GridPath getPath(Location s, Location e, Map m) {

		GridLocation start = (GridLocation)s;
		GridLocation end = (GridLocation)e;
		GridMap map = (GridMap)m;
		
		astar = new GridAstar(start, end, map, heuristic);
		
		return astar.getPath();
	}

}
