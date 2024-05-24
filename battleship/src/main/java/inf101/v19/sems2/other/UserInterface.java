package inf101.v19.sems2.other;

import java.util.HashMap;
import java.util.Map;

import inf101.v19.sem2.Board.IBoard;
import inf101.v19.sem2.Game.IGame;
import inf101.v19.sem2.battleship.objects.IBoardObject;
import inf101.v19.sem2.datastructures.ILocation;

public class UserInterface {
	
	//space between the elements in the string when printed
	final String spacing = "  ";
	//map to convert numerical value to alphabetic value. Used to represent x-coordinate
	private Map<Integer, Character> alphabetMap = new HashMap<Integer, Character>();
	
	public UserInterface(IGame game) {
		//Cannot have a board with length or width greater than letters in the alphabet (26)
		if (game.getWidth() > 26 || game.getHeight() > 26) {
			throw new IllegalArgumentException("Can't handle board size. Try board size less than 27");
		}
		
		//starts at 10 to skip char version of 1 to 9 (Character.forDigit(1, 36) = '1')
		//10 --> a, 11 --> b, ..., 36 --> z
		for (int i = 10; i < game.getWidth()+10; i++) {
			char c = Character.forDigit(i, 36);
			c = Character.toUpperCase(c);
			//add number as key and alphabet character as value
			alphabetMap.put(i-9, c);
		}
	}

	//TODO remove
	public void printStatus(IGame game) {
		//FIXME
//		System.out.println("friendly ship tiles left is " + health);
	}
	
	/**
	 * print help instructions for the player
	 */
	public void printHelp() {
		String help ="""
			Welcome to Battleship!
			
			Main Commands:
			- w: Print AI board
			- E: Get attack board copy
			- help: Print help
			- p: Print player board
			- status: Print status of the game
			- exit: Exit the game
			
			Setup Commands:
			- random: Place ships randomly
			- vertical: Place ships vertically
			- horizontal: Place ships horizontally
			- pboard: See player board
			- help: See help
			- exit: Exit the setup
			
			Good luck and have fun!
			""";


		System.out.println(help);
	}
	
	/**
	 * @param s
	 * 			s is a string containing the invalid input the player gave
	 */
	public void printInvalid(String s) {
		System.out.println(s + " is an invalid command");
	}
	
	/**
	 * print a text representation of IBoard board.
	 * @param board
 * 				the board to print a representation of
	 */
	public void printBoard(IBoard board) {
		//s1 represent numbers on the x-axis
		String s1 = " " + " " + spacing + " " + spacing;
		//s2 represent a line to divide the x-axis and the board
		String s2 = " " + " " + spacing + " " + spacing;
		for (int i = 1; i <= board.getWidth(); i++) {
			s1 += alphabetMap.get(i).toString() + spacing;
			s2 += "-" + spacing;
		}
		System.out.println(s1);
		System.out.println(s2);
		for (int y = 0; y < board.getHeight(); y++) {
			String row = "";
			//if-else statement under is for proper padding when grid size is greater than 9
			if (y+1 < 10) {
				row = " " + (y+1) + spacing + "|" + spacing;
			} else {
				row = y+1 + spacing + "|" + spacing;
			}
			
			for (int x = 0; x < board.getWidth(); x++) {
				//add new information in the row string for each column on the board
				ILocation<IBoardObject> loc = board.getLocation(x, y);
				IBoardObject o = loc.getData();
				char c = o.getSymbol();
				row += c;
				//padding for next symbol
				row += spacing;
			}
			System.out.println(row);
		}
	}
}