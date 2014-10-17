package ext.pathfinding.grid.util;

import java.util.ArrayList;

public class GridDouble {
	
	protected int sizeX;
	protected int sizeY;
	protected ArrayList<ArrayList<Double>> gridMap;
	
	public GridDouble(int x, int y){
		sizeX = x;
		sizeY = y;
		gridMap = new ArrayList<ArrayList<Double>>();
		for(int i=0; i<x; i++){
			gridMap.add(new ArrayList<Double>());
		}
		initialize(1);
	}
	
	public GridDouble clone(){
		GridDouble newList = new GridDouble(sizeX, sizeY);
		for(int i=0; i<sizeX; i++){
			for(int j=0; j<sizeY; j++){
				newList.set(i, j, get(i, j));
			}
		}
		return newList;
	}
	
	public void set(int x, int y, double value){
		gridMap.get(x).set(y, value);
	}
	
	public double get(int x, int y){
		return gridMap.get(x).get(y);
	}
	
	public int getSizeX(){
		return sizeX;
	}
	
	public int getSizeY(){
		return sizeY;
	}
	
	public void reset(double value){
		for(int i=0; i<sizeX; i++){
			for(int j=0; j<sizeY; j++){
				set(i, j, value);
			}
		}
	}
	
	private void initialize(double value){
		for(int i=0; i<sizeX; i++){
			for(int j=0; j<sizeY; j++){
				gridMap.get(i).add(value);
			}
		}
	}

}
