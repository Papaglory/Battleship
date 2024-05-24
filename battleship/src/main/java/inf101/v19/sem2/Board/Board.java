package inf101.v19.sem2.Board;

import java.util.ArrayList;
import java.util.List;

import inf101.v19.sem2.battleship.objects.*;
import inf101.v19.sem2.datastructures.*;

public class Board implements IBoard {

	private IGrid<ILocation<IBoardObject>> grid;
	
	public Board(int width, int height) {
		grid  = new Grid<ILocation<IBoardObject>>(width, height);
		//board will have water everywhere when initialized
		IBoardObject water = new Water();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				ILocation<IBoardObject> loc = new Location<IBoardObject>(x, y, water);
				grid.set(x, y, loc);
			}
		}
	}
	
	@Override
	public boolean replaceBoardObject(IBoardObject o, int x, int y, boolean horizontal) {
		if (x >= getWidth() || x < 0 || y >= getHeight() || y < 0) {
			throw new IndexOutOfBoundsException("position ("+x+", "+y+") is outside of the board");
		}
		ILocation<IBoardObject> oldLoc = getLocation(x, y);
		int length = o.getLength();
		//only need to set one location
		if (length == 0 || length == 1) {
			oldLoc.setData(o);
			return true;
		} else {
			//need to check multiple locations
			//loop through this list at the end when placing a ship instead of
			//looping through the algorithm twice
			List<ILocation<IBoardObject>> locations = new ArrayList<ILocation<IBoardObject>>();
			locations.add(oldLoc);
			ILocation<IBoardObject> newLoc = null;
			for (int i = 0; i < length-1; i++) {
				if (horizontal) {
					newLoc = getLocationRight(oldLoc);
				}
				else {
					newLoc = getLocationUnder(oldLoc);
				}
				
				if (newLoc == null) {
					//newLoc == null means we are outside of the board
					System.out.println(o.toString() + " was not placed because"
							+ "one or more locations are outside of the board");
					return false;
				}
				else {
					//not outside of the board
					locations.add(newLoc);
					oldLoc = newLoc;
					//continue to check the next location
					continue;
				}
			}
			//all locations have been checked and ship can now be placed
			for (ILocation<IBoardObject> location : locations) {
				location.setData(o);
			}
			//TODO remove
			System.out.println(o.getName() + " was placed correctly");
			return true;
		}
	}

	@Override
	public boolean placeBoardObject(IBoardObject o, int x, int y, boolean horizontal) {
		if (x >= getWidth() || x < 0 || y >= getHeight() || y < 0) {
			throw new IndexOutOfBoundsException("position (" + x + " ," + y + ") is outside the board");
		}
		int length = o.getLength();
		ILocation<IBoardObject> loc = getLocation(x, y);
		ILocation<IBoardObject> newLoc = null;
		
		//if length is 0 or 1 and there is no object occupying this tile (not including water)
		if ((length == 0 || length == 1) && (loc.getData() == null || loc.getData().getName() == "Water")) {
			loc.setData(o);
			return true;
		}
		
		List<ILocation<IBoardObject>> locations = new ArrayList<ILocation<IBoardObject>>();
		if (loc.getData().getName() != "Water") {
			System.out.println("A non-water object is at this tile. Cannot place object");
			return false;
		}
		//safe to add because this location was evaluated above
		locations.add(loc);
		//-1 because the first location was evaluated above
		for (int i = 0; i < length-1; i++) {
			if (horizontal)	newLoc = getLocationRight(loc);
			else newLoc = getLocationUnder(loc);
			
			if (newLoc == null) {
				//newLoc == null means we are outside of the board
				System.out.println(o.toString() + " was not placed because"
						+ "one or more locations are outside of the board");
				return false;
			}
			else if (newLoc.getData().getName() == "Water") {
				//not outside of the board
				locations.add(newLoc);
				loc = newLoc;
				//continue to check the next location
				continue;
			} else {
				//have encountered a ship because data is not water and data is not null 
				System.out.println("tile is already occupied by a ship");
				return false;
			}
		}

		//all locations have been checked and ship can now be placed
		for (ILocation<IBoardObject> location : locations) {
			location.setData(o);
		}
		//TODO remove
		System.out.println(o.getName() + " was placed correctly");
		return true;
	}
	
	/**
	 * @param loc
	 * 			location that is to the left of the location we want to evaluate
	 * @return 	get the location to the right of location loc. Returns null if
	 * 			there is no location to the right (location loc is an edge of the board) 
	 */
	private ILocation<IBoardObject> getLocationRight(ILocation<IBoardObject> loc) {
		if (loc.getX() + 1 < grid.getWidth()) {
			return grid.get(loc.getX() +1 , loc.getY());
		}
		System.out.println("getLocationRight(): " + loc.toString() + " is at the edge of the board");
		return null;
	}
	
	/**
	 * @param loc
	 * 			location that is above the location we want to evaluate
	 * @return 	get the location under location loc. Returns null if
	 * 			there is no location under location loc (location loc is an edge of the board) 
	 */
	private ILocation<IBoardObject> getLocationUnder(ILocation<IBoardObject> loc) {
		if (loc.getY() + 1 < grid.getHeight()) {
			return grid.get(loc.getX() , loc.getY() + 1);
		}
		System.out.println("getLocationUnder: " + loc.toString() + " is at the edge of the board");
		return null;
	}
	
	@Override
	public ILocation<IBoardObject> getLocation(int x, int y) {
		return grid.get(x, y);
	}
	
	public int getWidth() {
		return grid.getWidth();
	}
	
	public int getHeight() {
		return grid.getHeight();
	}
	
	@Override
	public IBoard copy() {
		//there will be water-objects on every tile at start because of board-constructor
		IBoard copy = new Board(grid.getWidth(), grid.getHeight());
		for (int y = 0; y < grid.getHeight(); y++) {
			for (int x = 0; x < grid.getWidth(); x++) {
				//location (x,y) on the new board
				ILocation<IBoardObject> cloneLoc = copy.getLocation(x, y);
				//get data from original board
				IBoardObject data = grid.get(x, y).getData();
				//copy data from original board
				IBoardObject dataCopy = data.copy();
				//place data copy on the new board
				cloneLoc.setData(dataCopy);
			}
		}
		return copy;
	}
}