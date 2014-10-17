package ext.pathfinding.core;

public interface Path {
	
	public boolean hasNextMove();
	
	public Location getNextMove();
	
	public Path clone();

}
