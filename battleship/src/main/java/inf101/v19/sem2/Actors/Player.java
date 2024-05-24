package inf101.v19.sem2.Actors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import inf101.v19.sem2.Board.IBoard;
import inf101.v19.sem2.Game.IGame;
import inf101.v19.sem2.battleship.objects.IBoardObject;
import inf101.v19.sems2.other.UserInterface;

public class Player implements ICompetitor {

	//map to convert alphabetic value to numerical value. Used to get x-coordinate
	private Map<Character, Integer> alphabetMap = new HashMap<Character, Integer>();
	//contains valid numerical values for y-coordinate
	private List<Integer> numberCoordinate = new ArrayList<Integer>();
	private List<String> shipTemplate = new ArrayList<String>();
	
	private Scanner sc = new Scanner(System.in);
	private boolean horizontal = true;
	private int health;
	
	public Player(IGame game) {
		//Cannot have a board with length or width greater than letters in the alphabet (26)
		if (game.getWidth() > 35 || game.getWidth() > 35) {
			throw new IllegalArgumentException("Can't handle board size. Try board size less than 27");
		}
		//starts at 10 to skip char version of 1 to 9 (Character.forDigit(1, 36) = '1')
		for (int i = 10; i < game.getWidth()+10; i++) {
			char c = Character.forDigit(i, 36);
			c = Character.toUpperCase(c);
			//add corresponding alphabet letter to a number on the x-axis of the board
			alphabetMap.put(c, i-9);
			numberCoordinate.add(i-9);
		}
		shipTemplate = game.getShipTemplate();
	}
	
	/**
	 * Takes in a string of length 2 and checks if the input correspond to a 
	 * position on the board. The first character of input will be in 
	 * alphabetic value-form. The method will transform it into numerical value
	 * @param UI
	 * 			User interface used to print out information in the console
	 * @param input
	 * 			first value of the string contains alphabetic value and second
	 * 			value of the string contains numerical value
	 * @return 	null if the given coordinate point is not a valid point on the board
	 * 			if the coordinate point is valid the method will return a string
	 * 			containing x-value in the first position and y-value in the second
	 * 			position of the string.
	 */
	private String evaluateCoordinatePoint(UserInterface UI, String input) {
		if (input.length() != 2) {
			throw new IllegalArgumentException("String input is not of size 2");
		}
		//Check if the first character is in alphabetMap
		char charValue = Character.toUpperCase(input.charAt(0));
		Integer alphabetNumber = alphabetMap.get(charValue);
		//Maps return null if key does not correspond to a value
		if (alphabetNumber == null) {
			UI.printInvalid(input);
			return null;
		}
		//Get numerical value of last char in input string
		Integer numberValue = Character.getNumericValue(input.charAt(1));
		boolean validNumber = false;
		for (Integer num : numberCoordinate) {
			if (numberValue.equals(num)) {
				validNumber = true;
				break;
			}
		}
		if (!validNumber) {
			UI.printInvalid(input);
			return null;
		}		
		String result = alphabetNumber.toString() + numberValue.toString();
		return result;
	}
	
	/**
	 * a method the player can utilize during the setup stage of the game
	 * to place ships at random locations
	 * @param game
	 * 			the game-object the competitor are a part of
	 */
	private void placeRandom(IGame game) {
		Random r = new Random();
		while(!shipTemplate.isEmpty()) {
			int x = r.nextInt(game.getWidth());
			int y = r.nextInt(game.getHeight());
			//create ship corresponding to template in shipTemplate
			IBoardObject currentShip = game.createBoardObject(shipTemplate.get(0));
			boolean horizontal = r.nextBoolean();
			boolean success = game.placeBoardObject(currentShip, x, y, horizontal, "player");
			if (success) {
				//add the number of tiles this ship occupies to health
				health += currentShip.getLength();
				shipTemplate.remove(0);
			}
		}
		//TODO bruke UI istedet?
		System.out.println("Player ships have been randomly placed on the board");
	}
	
	@Override
	public void setup(IGame game) {
		System.out.println("Player's setup turn");
		UserInterface UI = game.getUI();
		int shipCount = 0;
		while (shipCount < 5) {
			IBoard playerBoard = game.getPlayerBoardCopy();
			//auto print for convenience
			UI.printBoard(playerBoard);
			//Ask for input
			String input = sc.nextLine();
			//Check if the given input is a coordinate point
			if (input.length() == 2) {
				//will return null if the input is invalid
				String result = evaluateCoordinatePoint(UI, input);
				if (result != null) {
					int x = Character.getNumericValue(result.charAt(0));
					int y = Character.getNumericValue(result.charAt(1));
					//need to increment by 1 for the indexing
					x--;y--;
					//create ship corresponding to template in shipTemplate
					IBoardObject o = game.createBoardObject(shipTemplate.get(0));
					boolean success = game.placeBoardObject(o, x, y, horizontal, "player");
					if (success) {
						shipCount++;
						//add the number of tiles this ship occupies to health
						health += o.getLength();
						shipTemplate.remove(0);
						continue;
					} else {
						System.out.println("tiles occupied or placing ship will"
								+ "lead to ship being outside of the board");
						//TODO
						//UI.printOccupied();
						//UI.printShipOutOfBoard(); UI.noFit();
						continue;
					}
				}
			}
			//input was not a valid coordinate point. Check if it's one of the commands under 
			switch (input) {
			case "random":
				//place ships randomly
				placeRandom(game);
				return;
			case "vertical":
				//TODO ui print
				System.out.println("Ships will now be placed verticaly");
				horizontal = false;
				break;
			case "horizontal":
				//TODO ui print
				System.out.println("Ships will now be placed horizontaly");
				horizontal = true;
				break;
			case "pboard":
				UI.printBoard(playerBoard);
				break;
			case "help":
				UI.printHelp();
				break;
			case "exit":
				game.exitGame();
			default:
				UI.printInvalid(input);
				break;
			}
		}
	}
	
	@Override
	public void doTurn(IGame game) {
		UserInterface UI = game.getUI();
		//will keep looping until the player have done a valid action
		for (;;) {
			//TODO
			//UI.announceTurn("player");
			//ask for input
			String input = sc.nextLine();
			//Check if the given input is a coordinate point
			if (input.length() == 2) {
				//will return null if the input is invalid
				String result = evaluateCoordinatePoint(UI, input);
				if (result != null) {
					//result do now contain valid attack-coordinates
					int x = Character.getNumericValue(result.charAt(0));
					int y = Character.getNumericValue(result.charAt(1));
					x--;y--;
					game.attack(x, y, "player");
					return;
				}
			}
			//input was not a valid coordinate point. Check if it's one of the commands under
			switch (input) {
			case "W":
				UI.printBoard(game.getAiBoardCopy());
				break;
			case "E":
				UI.printBoard(game.getAttackBoardCopy());
				break;
			case "help":
				UI.printHelp();
				break;
			case "p":
				UI.printBoard(game.getPlayerBoardCopy());
				break;
			case "status":
				UI.printStatus(game);
				break;
			case "exit":
				game.exitGame();
			default:
				UI.printInvalid(input);
				break;
			}
		}
	}
	
	@Override
	public void hit() {
		health--;
	}

	/**
	 * game-object will call this method when the game is over. 
	 * The player can then decide to restart or exit the game
	 * @param game
	 * 			the game-object the competitor are a part of
	 */
	public void endGame(IGame game) {
		System.out.println("endGame called");
		UserInterface UI = game.getUI();
		//Ask for input
		String input = sc.nextLine();
		switch (input) {
		case "restart":
			game.resetGame();
			break;
		case "exit":
			game.exitGame();
		default:
			UI.printInvalid(input);
			break;
		}
	}
	
	@Override
	public int getHealth() {
		return health;
	}
}