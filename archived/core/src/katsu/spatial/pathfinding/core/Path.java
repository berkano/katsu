package katsu.spatial.pathfinding.core;

public interface Path {
	
	boolean hasNextMove();
	
	Location getNextMove();
	
	Path clone();

}
