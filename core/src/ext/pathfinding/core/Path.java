package ext.pathfinding.core;

public interface Path {
	
	boolean hasNextMove();
	
	Location getNextMove();
	
	Path clone();

}
