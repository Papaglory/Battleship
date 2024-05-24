package inf101.v19.sem2.Board;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.JUnitException;

import inf101.v19.sem2.battleship.objects.Battleship;
import inf101.v19.sem2.battleship.objects.Carrier;
import inf101.v19.sem2.battleship.objects.Destroyer;
import inf101.v19.sem2.battleship.objects.IBoardObject;
import inf101.v19.sem2.battleship.objects.PatrolBoat;
import inf101.v19.sem2.battleship.objects.Submarine;
import inf101.v19.sem2.battleship.objects.Water;
import inf101.v19.sem2.datastructures.ILocation;

public class BoardTest {

	private static final int N = 100;
	private static Random r = new Random();
	
	@Test
	void initializationTest() {
		for (int i = 0; i < N; i++) {
			int width = r.nextInt(26)+1;
			int height = r.nextInt(26)+1;
			IBoard b = new Board(width, height);
			
			IBoardObject water = new Water();
			for (int y = 0; y < b.getHeight(); y++) {
				for (int x = 0; x < b.getWidth(); x++) {
					IBoardObject bObject = b.getLocation(x, y).getData();
					//just need to check if the object on every tile is of type Water
					if ((bObject instanceof Water) == false) {
						throw new JUnitException("not all tiles contain a water-object");
					}
					//here is the "proper" way to do it. Found it somewhat silly to use this method though
					//since Water objects only have a String name field variable. 
					assertEquals(water, bObject);
					assertEquals(water.hashCode(), bObject.hashCode());
				}
			}
		}
	}
	
	@Test
	void placeTest() {
		for (int i = 0; i < N; i++) {
			int width = 0;
			int height = 0;
			while (width < 5 && height < 5) {
				width = r.nextInt(26)+1;
				height = r.nextInt(26)+1;
			}
			List<IBoardObject> ships = new ArrayList<IBoardObject>();
			ships.add(new Carrier());
			ships.add(new Battleship());
			ships.add(new Destroyer());
			ships.add(new Submarine());
			ships.add(new PatrolBoat());
			
			Collections.shuffle(ships);
			IBoard b = new Board(width, height);
			
			for (int t = 0; t < ships.size(); t++) {
				IBoardObject ship = ships.get(0);
				int x1 = r.nextInt(width);
				int y1 = r.nextInt(height);
				boolean horizontal = r.nextBoolean();
				boolean success = b.placeBoardObject(ship, x1, y1, horizontal);
				if (success) {
					ships.remove(0);
					List<ILocation<IBoardObject>> actualLocations = new ArrayList<ILocation<IBoardObject>>();
					//gather the locations from the board containing objects of type ship
					for (int y = 0; y < b.getHeight(); y++) {
						for (int x = 0; x < b.getWidth(); x++) {
							if (ship.equals(b.getLocation(x, y).getData())) {
								actualLocations.add(b.getLocation(x, y));
							}
						}
					}
					List<ILocation<IBoardObject>> expectedLocations = new ArrayList<ILocation<IBoardObject>>();
					//gather the expected locations
					if (horizontal) {
						for (int j = 0; j < ship.getLength(); j++) {
							ILocation<IBoardObject> expLoc = b.getLocation(x1+j, y1);
							expectedLocations.add(expLoc);
						}
					} else {
						for (int j = 0; j < ship.getLength(); j++) {
						ILocation<IBoardObject> expLoc = b.getLocation(x1, y1+j);
						expectedLocations.add(expLoc);
						}
					}
					//compare locations found in actualLocations with expectedLocations
					List<Boolean> locationsFound = new ArrayList<Boolean>();
					for (int k = 0; k < actualLocations.size(); k++) {
						for (int l = 0; l < expectedLocations.size(); l++) {
							//check if the variables point to the same location
							if (actualLocations.get(k) == expectedLocations.get(l)) {
								//same location in both actualLocations and expectedLocations
								locationsFound.add(true);
								break;
							}
						}
					}
					if (locationsFound.size() == ship.getLength()) {
						//same size mean the locations in expectedList was found in actualList
						//therefore the ships was placed at the correct place
					} else {
						throw new JUnitException("ship was not placed at expected locations");
					}
				}
			}
		}
	}
}