package ext.pathfinding.core.astar;

import ext.pathfinding.core.Location;

public interface SortedLocationList {
	
	public void add(Location location);
	
	public Location getNext();
	
	public boolean hasNext();

}
