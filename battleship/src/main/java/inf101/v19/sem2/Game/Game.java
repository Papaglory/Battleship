package inf101.v19.sem2.Game;

import java.util.ArrayList;
import java.util.List;

import inf101.v19.sem2.Actors.*;
import inf101.v19.sem2.Board.*;
import inf101.v19.sems2.other.UserInterface;
import inf101.v19.sem2.battleship.objects.*;

public class Game implements IGame {
	private IBoard aiBoard;
	private IBoard attackBoard;
	private IBoard playerBoard;
	private UserInterface UI;
	private Player player;
	private Opponent opponent;
	
	private boolean gameOver = false;
	private int width;
	private int height;
	
	public Game(int width, int height) {
		if (width < 5 || height < 5) {
			throw new IllegalArgumentException("width and height must be greater than 4"
					+ " else competitors will not be able to place all ships");
		}
		this.width = width;
		this.height = height;
		initializeGame();
	}
	
	/**
	 * is called when there is a new round, either at startup or at game restart.
	 * every field variable will point to new objects. Thus the garbage collector
	 * will remove the objects from the previous round
	 */
	private void initializeGame() {
		gameOver = false;
		aiBoard = new Board(width, height);
		attackBoard = new Board(width, height);
		playerBoard = new Board(width, height);
		player = new Player(this);
		opponent = new Opponent(this);
		UI = new UserInterface(this);
		
		opponent.setup(this);
		UI.printHelp();
		player.setup(this);
	}
	
	@Override
	public void doTurn() {
		//Check if anyone has won the game yet
		if (!gameOver) {
			if (player.getHealth() < 1) {
				winner("ai");
			}
			else if (opponent.getHealth() < 1) {
				winner("player");
			}
			else {
				//continue with next turn
				System.out.println("Player's turn");
				player.doTurn(this);
				System.out.println("Opponent's turn");
				opponent.doTurn(this);
			}
		} else {
			//ask the player to make an end game decision
			player.endGame(this);
		}
	}
	
	@Override
	public boolean replaceBoardObject(IBoardObject o, int x, int y, boolean horizontal, String board) {
		switch (board) {
		case "player":
			return playerBoard.replaceBoardObject(o, x, y, horizontal);
		case "ai":
			return aiBoard.replaceBoardObject(o, x, y, horizontal);
		case "attack":
			return attackBoard.replaceBoardObject(o, x, y, horizontal);
		default:
			throw new IllegalArgumentException("Board " + board + " was not recognized");
		}
	}
	
	@Override
	public boolean placeBoardObject(IBoardObject o, int x, int y, boolean horizontal, String board) {
		switch (board) {
		case "player":
			return playerBoard.placeBoardObject(o, x, y, horizontal);
		case "ai":
			return aiBoard.placeBoardObject(o, x, y, horizontal);
		case "attack":
			return attackBoard.placeBoardObject(o, x, y, horizontal);
		default:
			throw new IllegalArgumentException("Board " + board + " was not recognized");
		}
	}
	
	@Override
	public IBoardObject createBoardObject(String o) {
		switch (o) {
		case "Carrier":
			return new Carrier();
		case "Battleship":
			return new Battleship();
		case "Destroyer":
			return new Destroyer();
		case "Submarine":
			return new Submarine();
		case "Patrol Boat":
			return new PatrolBoat();
		case "Hit Marker":
			return new HitMarker();
		case "Miss Marker":
			return new MissMarker();
		case "Water":
			return new Water();
		default:
			//TODO sjekk her. water ble før lagd i default. Kanskje er noe ødelagt
			throw new IllegalArgumentException(o + " was not recognized as a factory template");
		}
	}
	
	@Override
	public void attack(int x, int y, String attacker) {
		//horizontal is unimportant due to markers having length 1
		boolean horizontal = false;
		IBoardObject target = null;
		IBoardObject hitMarker = createBoardObject("Hit Marker");
		IBoardObject missMarker = createBoardObject("Miss Marker");
		
		switch (attacker) {
		case "player":
			target = aiBoard.getLocation(x, y).getData();
			//need attackBoard to check if the player have already shot at this location
			IBoardObject attackBoardTarget = attackBoard.getLocation(x, y).getData();
			if (target.getName() == "Water" && attackBoardTarget.getName() == "Water") {
				//hit water and first time shooting at this location
				System.out.println("Miss!");
				replaceBoardObject(missMarker, x, y, horizontal, "attack");
			}
			else if (attackBoardTarget.getName() == "Hit Marker" ||
					attackBoardTarget.getName() == "Miss Marker") {
				//shooting at a location already shot at
				//TODO bruke UI?
				System.out.println("Have already shot at this location");
				System.out.println("Nothing happens...");
			} else {
				//hit something that is not water and it's the first time shooting at this location
				System.out.println("Hit!");
				opponent.hit();
				replaceBoardObject(hitMarker, x, y, horizontal, "attack");
			}
			return;
		case "ai":
			//AI cannot shoot at the same location, thus it's not necessary to check for markers 
			char xInChar = (char) ('a' + x - 1);
			System.out.println("The opponent shot at " + xInChar + y);
			target = playerBoard.getLocation(x, y).getData();
			if (target.getName() == "Water") {
				//hit water
				System.out.println("Miss!");
				replaceBoardObject(missMarker, x, y, horizontal, "player");
			} else {
				//hit something that is not water
				System.out.println("Hit!");
				player.hit();
				replaceBoardObject(hitMarker, x, y, horizontal, "player");
			}
			return;
		}
	}
	
	@Override
	public void resetGame() {
		initializeGame();
	}
	
	@Override
	public void winner(String s) {
		gameOver = true;
		System.out.println(s + " wins");
		//print result
		UI.printBoard(getPlayerBoardCopy());
		UI.printBoard(getAttackBoardCopy());
	}
	
	@Override
	public void exitGame() {
		//terminate program
		System.out.println("terminating program");
		System.exit(0);
		throw new Error("Program did not exit properly");
	}
	
	@Override
	public IBoard getAiBoardCopy() {
		return aiBoard.copy();
	}
	
	@Override
	public IBoard getPlayerBoardCopy() {
		return playerBoard.copy();
	}
	
	@Override
	public IBoard getAttackBoardCopy() {
		return attackBoard.copy();
	}
	
	@Override
	public UserInterface getUI() {
		return UI;
	}
	
	@Override
	public List<String> getShipTemplate() {
		List<String> shipTemplate = new ArrayList<String>();
		shipTemplate.add("Carrier");
		shipTemplate.add("Destroyer");
		shipTemplate.add("Battleship");
		shipTemplate.add("Submarine");
		shipTemplate.add("Patrol Boat");
		return shipTemplate;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
}