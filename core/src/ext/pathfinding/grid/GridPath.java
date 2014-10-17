package ext.pathfinding.grid;

import ext.pathfinding.core.Path;

import java.util.ArrayList;

public class GridPath implements Path{

	private ArrayList<GridLocation> locationList;
	
	public GridPath(ArrayList<GridLocation> locationList){
		this.locationList = locationList;
	}
	
	public ArrayList<GridLocation> getList(){
		ArrayList<GridLocation> locList = new ArrayList<GridLocation>();
		for(int i=0; i<locationList.size(); i++){
			locList.add(locationList.get(i));
		}
		return locList;
	}
	
	@Override
	public boolean hasNextMove() {
		return locationList.size() > 0;
	}
	
	@Override
	public GridLocation getNextMove(){
		if(locationList.size() > 0){
			return locationList.remove(0);
		}
		return null;
	}
	
	@Override
	public GridPath clone(){
		GridPath path;
		
		ArrayList<GridLocation> locList = new ArrayList<GridLocation>();
		for(int i=0; i<locationList.size(); i++){
			locList.add(locationList.get(i));
		}
		
		path = new GridPath(locList);
		return path;
	}

	
	
	
}
