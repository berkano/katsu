package ext.pathfinding.grid.astar;

import ext.pathfinding.core.astar.Astar;
import ext.pathfinding.grid.GridLocation;
import ext.pathfinding.grid.GridMap;
import ext.pathfinding.grid.GridPath;
import ext.pathfinding.grid.util.GridDouble;

import java.util.ArrayList;

public class GridAstar implements Astar{
	
	private int sizeX;
	private int sizeY;
	
	private final int unvisited = 0;
	private final int visited = 1;
	
	private GridDouble visitedMap; //0 mean not visited, 1 mean visited
	private GridDouble distanceMap; //all value start at Integer.MAX_VALUE;
	private GridSortedLocationList sortedLocationList;
	
	private GridLocation start;
	private GridLocation end;
	private GridMap map;
	private GridHeuristic heuristic;
	
	public GridAstar(GridLocation start, GridLocation end, GridMap map, GridHeuristic heuristic){
		this.start = start;
		this.end = end;
		this.map = map;
		this.heuristic = heuristic;
		sizeX = map.getSizeX();
		sizeY = map.getSizeY();
		visitedMap = new GridDouble(sizeX, sizeY);
		visitedMap.reset(unvisited);
		distanceMap = new GridDouble(sizeX, sizeY);
		distanceMap.reset(Integer.MAX_VALUE);
		sortedLocationList = new GridSortedLocationList();
	}
	
	public GridPath getPath(){
		GridPath path = null;
		
		if(!validLocation(start.getX(), start.getY()) || !validLocation(end.getX(), end.getY())){
			return null;
		}
		
		GridLocationAstar startingLocation = new GridLocationAstar(start.getX(), start.getY(), start.isEnd(), 0, -1);
		sortedLocationList.add(startingLocation);
		distanceMap.set(start.getX(), start.getY(), startingLocation.getDoneDistance());
		
		GridLocationAstar location;
		boolean endIsFound = false;
		
		//Find a path...
		while(sortedLocationList.hasNext()){
			location = sortedLocationList.getNext();
			if(visitedMap.get(location.getX(), location.getY()) == visited){
				continue;
			}
			visitedMap.set(location.getX(), location.getY(), visited);
			if(location.isEnd()){
				endIsFound = true;
				break;
			}
			addAdjacent(location);
		}
		
		//Resolve path...
		if(endIsFound){
			path = traceBackThePath();
		}
		
		return path;
	}
	
	/* ---------------------- HELPING METHODS -----------------------------*/
	
	/* ------------------ RESOLVE A PATH HELPER ------------------------------*/
	
	@SuppressWarnings("unused")
	private void printDistanceMap(){
		for(int j=0; j<distanceMap.getSizeY(); j++){
			String s = "";
			for(int i=0; i<distanceMap.getSizeX(); i++){
				int n = (int)distanceMap.get(i, j);
				int n2 = (int)(distanceMap.get(i, j)*10);
				double d = n2/10.0;
				if(n == Integer.MAX_VALUE){
					s += "-1";
				}else{
					s += d;
				}
				s += " : ";
			}
			System.out.println(s);
		}
	}
	
	private GridPath traceBackThePath(){
		GridPath path;
		GridLocation currentLocation;
		
		ArrayList<GridLocation> locationList = new ArrayList<GridLocation>();
		
		currentLocation = end;
		int x = currentLocation.getX();
		int y = currentLocation.getY();
		locationList.add(currentLocation);
		
		while(!(x == start.getX() && y == start.getY())){
			currentLocation = findNextLocation(x, y);
			x = currentLocation.getX();
			y = currentLocation.getY();
			locationList.add(currentLocation);
		}
		
		path = new GridPath(locationList);
		return path;
	}
	
	private GridLocation findNextLocation(int startX, int startY){
		int x = startX;
		int y = startY;
		
		DiagonalMove diagonal = new DiagonalMove(startX, startY);
		
		int bestX;
		int bestY;
		double bestValue = Integer.MAX_VALUE;
		//Above
		y = startY + 1;
		x = startX - 1;
		bestX = x;
		bestY = y;
		if(getValue(x, y) < bestValue && diagonal.isValid(diagonal.top_left)){
			bestX = x;
			bestY = y;
			bestValue = distanceMap.get(x, y);
		}
		x = startX;
		if(getValue(x, y) < bestValue){
			bestX = x;
			bestY = y;
			bestValue = distanceMap.get(x, y);
		}
		x = startX + 1;
		if(getValue(x, y) < bestValue && diagonal.isValid(diagonal.top_right)){
			bestX = x;
			bestY = y;
			bestValue = distanceMap.get(x, y);
		}
		//Center
		y = startY;
		x = startX - 1;
		if(getValue(x, y) < bestValue){
			bestX = x;
			bestY = y;
			bestValue = distanceMap.get(x, y);
		}
		x = startX + 1;
		if(getValue(x, y) < bestValue){
			bestX = x;
			bestY = y;
			bestValue = distanceMap.get(x, y);
		}
		//Under
		y = startY - 1;
		x = startX - 1;
		if(getValue(x, y) < bestValue && diagonal.isValid(diagonal.bottom_left)){
			bestX = x;
			bestY = y;
			bestValue = distanceMap.get(x, y);
		}
		x = startX;
		if(getValue(x, y) < bestValue){
			bestX = x;
			bestY = y;
			bestValue = distanceMap.get(x, y);
		}
		x = startX + 1;
		if(getValue(x, y) < bestValue && diagonal.isValid(diagonal.bottom_right)){
			bestX = x;
			bestY = y;
			bestValue = distanceMap.get(x, y);
		}
		return createLocation(bestX, bestY);
	}
	
	private double getValue(int x, int y){
		if(x < 0 || x >= sizeX || y < 0 || y >= sizeY){
			return Integer.MAX_VALUE;
		}
		return distanceMap.get(x, y);
	}
	
	private GridLocation createLocation(int x, int y){
		boolean isEnd = false;
		if(x == end.getX() && y == end.getY()){
			isEnd = true;
		}
		return new GridLocation(x, y, isEnd);
	}
	
	
	
	/* --------------------- FIND A PATH HELPER ------------------------------*/
	
	private void addAdjacent(GridLocationAstar previousLocation){
		int currentX = previousLocation.getX();
		int currentY = previousLocation.getY();
		
		DiagonalMove diagonal = new DiagonalMove(currentX, currentY);
		
		int x;
		int y;
		
		//Above
		//Top left
		y = currentY + 1;
		x = currentX - 1;
		if(unvisited(x, y) && diagonal.isValid(diagonal.top_left)){
			addLocation(x, y, previousLocation);
		}
		//Side
		x = currentX;
		if(unvisited(x, y)){
			addLocation(x, y, previousLocation);
		}
		//Top right
		x = currentX + 1;
		if(unvisited(x, y) && diagonal.isValid(diagonal.top_right)){
			addLocation(x, y, previousLocation);
		}
		//Center
		//Side
		y = currentY;
		x = currentX - 1;
		if(unvisited(x, y)){
			addLocation(x, y, previousLocation);
		}
		//Side
		x = currentX + 1;
		if(unvisited(x, y)){
			addLocation(x, y, previousLocation);
		}
		//Under
		//Bottom left
		y = currentY - 1;
		x = currentX - 1;
		if(unvisited(x, y) && diagonal.isValid(diagonal.bottom_left)){
			addLocation(x, y, previousLocation);
		}
		//Side
		x = currentX;
		if(unvisited(x, y)){
			addLocation(x, y, previousLocation);
		}
		//Bottom right
		x = currentX + 1;
		if(unvisited(x, y) && diagonal.isValid(diagonal.bottom_right)){
			addLocation(x, y, previousLocation);
		}
	}
	
	private GridLocationAstar createLocation(int x, int y, GridLocationAstar previousLocation){
		GridLocationAstar newLocation;
		boolean isEnd = end.getX() == x && end.getY() == y;
		double doneDist = previousLocation.getDoneDistance() + getDist(x, y, previousLocation.getX(), previousLocation.getY());
		double todoDist = heuristic.getDistance(x, y, end);
		newLocation = new GridLocationAstar(x, y, isEnd, doneDist, todoDist);
		return newLocation;
	}
	
	private void addLocation(int x, int y, GridLocationAstar previousLocation){
		GridLocationAstar location;
		
		location = createLocation(x, y, previousLocation);
		sortedLocationList.add(location);
		double dist = Math.min(location.getDoneDistance(), distanceMap.get(x, y));
		distanceMap.set(x, y, dist);
	}
	
	private class DiagonalMove{

		private final int top_right = 0;
		private final int top_left = 1;
		private final int bottom_right = 2;
		private final int bottom_left = 3;
		
		private int x;
		private int y;
		
		private DiagonalMove(int x, int y){
			this.x = x;
			this.y = y;
		}

		private boolean isValid(int direction){
			//Top right
			if(validLocation(x+1, y) && validLocation(x, y+1) && direction == top_right){
				return true;
			}
			//Bottom right
			else if(validLocation(x+1, y) && validLocation(x, y-1) && direction == bottom_right){
				return true;
			}
			//Top left
			else if(validLocation(x-1, y) && validLocation(x, y+1) && direction == top_left){
				return true;
			}
			//Bottom left
			else if(validLocation(x-1, y) && validLocation(x, y-1) && direction == bottom_left){
				return true;
			}
			return false;
		}

	}
	
	private boolean isWall(int x, int y){
		return map.get(x, y) == GridMap.WALL;
	}
	
	private boolean validLocation(int x, int y){
		boolean result = true;
		result &= x >= 0 && x < sizeX && y >= 0 && y < sizeY;
		if(result){
			result &= !isWall(x, y);
		}
		return result;
	}
	
	private boolean unvisited(int x, int y){
		return validLocation(x, y) && visitedMap.get(x, y) == unvisited;
	}
	
	private double getDist(int x, int y, int nx, int ny){
		double result = 0;
		int dx = x - nx;
		int dy = y - ny;
		result += Math.sqrt(dx*dx + dy*dy);
		result = result*0.5*map.get(x, y) + result*0.5*map.get(nx, ny);
		return result;
	}

}
