package katsu.spatial.pathfinding.core.astar;

import katsu.spatial.pathfinding.core.Location;

public interface SortedLocationList {
	
	void add(Location location);
	
	Location getNext();
	
	boolean hasNext();

}
