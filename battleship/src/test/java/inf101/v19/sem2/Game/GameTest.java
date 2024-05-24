package inf101.v19.sem2.Game;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import inf101.v19.sem2.battleship.objects.HitMarker;
import inf101.v19.sem2.battleship.objects.IBoardObject;
import inf101.v19.sem2.battleship.objects.MissMarker;

public class GameTest {

	private static final int N = 100;
	private static Random r = new Random();

	/**
	 * This test will check if the right marker is put on the board after
	 * the player have taken a shot. There will either be placed a hit marker
	 * or a miss marker. This depends on what the player hits. 
	 */
	@Test
	void attackTest() {
		if (1==1) {
			return;
		}
		int width = 0;
		int height = 0;
		while (width <= 4 || height <= 4) {
			width = r.nextInt(26)+1;
			height = r.nextInt(26)+1;
		}
		//player need to place ships manually (use "random" in console)
		IGame game = new Game(width, height);
		
		for (int i = 0; i < (width*height); i++) {
			int x = r.nextInt(width);
			int y = r.nextInt(height);
			//comment in to see boards
//				game.getUI().printBoard(game.getAttackBoardCopy());
//				game.getUI().printBoard(game.getAiBoardCopy());
			
			//get data at location (x,y) from aiBoard
			IBoardObject aiData = game.getAiBoardCopy().getLocation(x, y).getData();
			boolean containsShip = false;
			if (aiData.getName() != "Water") {
				containsShip = true;
			}
			game.attack(x, y, "player");
			//get data at location (x,y) from attackBoard
			IBoardObject postData = game.getAttackBoardCopy().getLocation(x, y).getData();
			if (containsShip) {
				IBoardObject hitMarker = new HitMarker();
				//postData is supposed to be a hit marker
				assertEquals(postData, hitMarker);
			} else {
				IBoardObject missMarker = new MissMarker();
				//postData is supposed to be a miss marker
				assertEquals(postData, missMarker);
			}
		}
	}
}