package inf101.v19.sem2.Actors;

import inf101.v19.sem2.Game.IGame;

public interface ICompetitor {

	/**
	 * called to make the actors setup their boards. Ships will be placed and 
	 * field variable health will have value correspondingto the amount of
	 * tiles containing friendly ships.
	 * @param game
	 * 			the game-object the competitor are a part of
	 */
	void setup(IGame game);
	
	/**
	 * make competitors do their turn
	 * @param game
	 * 			the game-object the competitor are a part of
	 */
	void doTurn(IGame game);
	
	/**
	 * @return health the competitor has. Health is used to evaluate if a competitor has
	 * lost all ships
	 */
	int getHealth();
	
	/**
	 * called by the game if a ship belonging to this competitor is hit
	 */
	void hit();
}
