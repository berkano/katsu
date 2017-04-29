package ext.pathfinding.core.astar;

import ext.pathfinding.core.Location;

public interface SortedLocationList {
	
	void add(Location location);
	
	Location getNext();
	
	boolean hasNext();

}
