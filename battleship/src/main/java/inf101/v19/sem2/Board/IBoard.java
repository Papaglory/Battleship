package inf101.v19.sem2.Board;

import inf101.v19.sem2.battleship.objects.IBoardObject;
import inf101.v19.sem2.datastructures.ILocation;

public interface IBoard {
	
	/**
	 * Used to place objects on the board at the start. Can place objects on tiles
	 * containing water-objects or if tile object is null.
	 * See replaceBoardObject for putting objects on the board without restrictions.
	 * Notice: board constructor will initialize all tiles with water-objects 
	 * @param o
	 * 			object to be placed at grid position (x, y)
	 * @param x
	 * 			x position in the grid
	 * @param y
	 * 			y position in the grid
	 * @param horizontal
	 * 			if true, objects of length > 0 will be placed horizontally with grid
	 * 			position (x, y) as a pivot point. placed vertically if false
	 * @return 	true if the object was placed. False if tiles were not available due to being
	 * 			out of board or an object already occupying the tile (not including water-object).
	 */
	boolean placeBoardObject(IBoardObject o, int x, int y, boolean horizontal);
	
	/**
	 * Used to place objects on the board. Objects can be placed even though an
	 * object of any type is already occupying it
	 * @param o
	 * 			object to be placed at grid position (x, y)
	 * @param x
 * 				x position in the grid
	 * @param y
	 * 			y position in the grid
	 * @param horizontal
	 * 			if true, objects of length > 0 will be placed horizontally with grid
	 * 			position (x, y) as a pivot point. placed vertically if false
	 * @return 	true if the object was placed. False if tiles were not available due to being
	 * 			out of board.
	 */
	boolean replaceBoardObject(IBoardObject o, int x, int y, boolean horizontal);
	
	/**
	 * @param x
	 * 			x position in the grid
	 * @param y
	 * 			y position in the grid
	 * @return the location at position (x,y) in the grid
	 */
	ILocation<IBoardObject> getLocation(int x, int y);
	
	int getWidth();

	int getHeight();
	
	/**
	 * Copying a board will also make a copy of every single object in the grid.
	 * A copy of every single object in the grid is necessary to keep the copy-board
	 * from pointing to objects in the original board
	 * Doing this will prevent direct changes by objects outside of the Board-class.
	 * @return a copy of this board with same width and height and identical objects
	 */
	IBoard copy();
}