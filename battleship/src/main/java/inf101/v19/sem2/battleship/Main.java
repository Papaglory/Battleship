package inf101.v19.sem2.battleship;

import inf101.v19.sem2.Game.Game;
import inf101.v19.sem2.Game.IGame;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Application started");
//		initialize the game;
		IGame game = new Game(10, 10);
		for (;;) {
			//execute game.doTurn()
			//Player can terminate application or restart game from console
			game.doTurn();
		}
	}
}
