package ext.pathfinding.grid;

import ext.pathfinding.core.Location;
import ext.pathfinding.core.Map;
import ext.pathfinding.core.Pathfinding;
import ext.pathfinding.grid.astar.GridAstar;
import ext.pathfinding.grid.astar.GridHeuristic;
import ext.pathfinding.grid.astar.GridHeuristicManathan;

public class GridPathfinding implements Pathfinding{

	GridAstar astar;
	GridHeuristic heuristic;
	
	public GridPathfinding(){
		heuristic = new GridHeuristicManathan();
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
