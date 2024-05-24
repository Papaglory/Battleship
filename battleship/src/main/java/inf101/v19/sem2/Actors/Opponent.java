package inf101.v19.sem2.Actors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import inf101.v19.sem2.Board.IBoard;
import inf101.v19.sem2.Game.IGame;
import inf101.v19.sem2.battleship.objects.IBoardObject;
import inf101.v19.sem2.datastructures.ILocation;

public class Opponent implements ICompetitor {

	private int health;
	//locations will be pulled randomly out from the list and used to shoot at the player board
	private List<ILocation<IBoardObject>> locations = new ArrayList<ILocation<IBoardObject>>();
	//list containing the names of the ships that is created during setup
	private List<String> shipTemplate = new ArrayList<String>();
	
	public Opponent(IGame game) {
		//need board to get all possible locations to shoot at
		IBoard board = game.getAttackBoardCopy();
		//put all available locations in the locations list
		for (int y = 0; y < board.getHeight(); y++) {
			for (int x = 0; x < board.getWidth(); x++) {
				ILocation<IBoardObject> loc = board.getLocation(x, y);
				locations.add(loc);
			}
		}
		//shuffle ILocation-objects in the list
		Collections.shuffle(locations);
	}
	
	@Override
	public void setup(IGame game) {
		shipTemplate = game.getShipTemplate();
		//spawning AI ships randomly
		for (String s : shipTemplate) {
			IBoardObject ship = game.createBoardObject(s);
			Random r = new Random();
			boolean successfulPlacement = false;
			while (successfulPlacement == false) {
				boolean horizontal = r.nextBoolean();
				int x = r.nextInt(game.getWidth());
				int y = r.nextInt(game.getHeight());
				successfulPlacement = game.placeBoardObject(ship, x, y, horizontal, "ai");
				if (successfulPlacement) {
					//increase health by x when a ship occupies x tiles on the board
					health += ship.getLength();
				}
			}
		}
	}
	
	@Override
	public void doTurn(IGame game) {
		//Attacking
		if (!locations.isEmpty()) {
			ILocation<IBoardObject> targetLoc = locations.remove(0);
			int x = targetLoc.getX();
			int y = targetLoc.getY();
			game.attack(x, y, "ai");
		}
	}
	
	@Override
	public void hit() {
		health--;
	}
	
	@Override
	public int getHealth() {
		return health;
	}
}