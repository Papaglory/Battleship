package inf101.v19.sem2.battleship.objects;

public interface IBoardObject {

	String getName();

	/**
	 * @return symbol used to represent this object on the board
	 */
	char getSymbol();
	
	/**
	 * @return length of this object. Objects with length zero occupy only one tile
	 */
	int getLength();

	/**
	 * is used by IBoard.copy() when making a copy of a board. Every object corresponding to
	 * a tile will be copied and put on the new board
	 * @return a copy of this object
	 */
	IBoardObject copy();
}
