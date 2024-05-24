package inf101.v19.sem2.Game;

import java.util.List;

import inf101.v19.sem2.Board.IBoard;
import inf101.v19.sem2.battleship.objects.IBoardObject;
import inf101.v19.sems2.other.UserInterface;

public interface IGame {

	/**
	 * s called to progress in the game. This method will call ICompetitor.doTurn() to make
	 * all the competitors do their turn. 
	 */
	void doTurn();
	
	/**
	 * a factory method used to create objects
	 * @param o
	 * 			a string containing the name of the object to create
	 * @return object with IBoardObject.getName() corresponding to string o
	 */
	IBoardObject createBoardObject(String o);
	
	void attack(int x, int y, String attacker);

	/**
	 * Used to place objects on the board at the start. Can place objects on tiles
	 * containing water-objects or if tile object is null. String parameter is necessary 
	 * because the game keeps track of multiple boards.
	 * See replaceBoardObject for putting objects on the board without restrictions. 
	 * @param o
	 * 			object to be placed at board position (x, y)
	 * @param x
	 * 			x position on the board
	 * @param y
	 * 			y position on the board
	 * @param horizontal
	 * 			if true, objects of length > 0 will be placed horizontally with board
	 * 			position (x, y) as a pivot point. placed vertically if false
	 * @param board
	 * 			name of the board 
	 * @return 	true if the object was placed. False if tiles were not available due to being
	 * 			out of board or an object already occupying the tile (not including water-object).
	 */
	boolean placeBoardObject(IBoardObject o, int x, int y, boolean horizontal, String board);
	
	/**
	 * Used to place objects on the board. Objects can be placed even though an
	 * object of any type is already occupying it. String parameter is necessary 
	 * because the game keeps track of multiple boards.
	 * @param o
	 * 			object to be placed at board position (x, y)
	 * @param x
 * 				x position on the board
	 * @param y
	 * 			y position on the board
	 * @param horizontal
	 * 			if true, objects of length > 0 will be placed horizontally with grid
	 * 			position (x, y) as a pivot point. placed vertically if false
	 * @param board
	 * 			name of the board
	 * @return 	true if the object was placed. False if tiles were not available due to being
	 * 			out of board.
	 */
	boolean replaceBoardObject(IBoardObject o, int x, int y, boolean horizontal, String board);

	/**
	 * @return user interface that is used to print board information
	 */
	UserInterface getUI();
	
	/**
	 * @return 	a list containing string elements with names of the
	 * 			ships that the competitors need to create at start 
	 */
	List<String> getShipTemplate();
	
	/**
	 * @return 	a copy of the AI-board containing information about
	 * 			where the AI ships are located
	 */
	IBoard getAiBoardCopy();
	
	/**
	 * @return 	a copy of the player-board containing information about
	 * 			where the player ships are located
	 */
	IBoard getPlayerBoardCopy();
	
	/**
	 * @return 	a copy of the attack-board containing information about
	 * 			where the player have shot at
	 */
	IBoard getAttackBoardCopy();
	
	/**
	 * called by the player to reset the game
	 */
	void resetGame();
	
	/**
	 * is called when either the player or the opponent wins
	 * @param s
	 * 			contains name of the winner
	 */
	void winner(String s);
	
	/**
	 * called by the player to terminate the game
	 */
	void exitGame();
	
	/**
	 * @return the shared width of all the boards
	 */
	int getWidth();
	/**
	 * @return the shared height of all the boards
	 */
	int getHeight();
}