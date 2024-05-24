package inf101.v19.sem2.datastructures;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class GridTest {
	
	private static final int N = 100;
	private static Random r = new Random();
	
	@Test
	void setTest() {
		for (int i = 0; i < N; i++) {
			//+1 to get values greater than zero
			int xSize = r.nextInt(10) +1;
			int ySize = r.nextInt(10) +1;
			IGrid<Character> grid1 = new Grid<Character>(xSize, ySize);
			
			char foo = 'f';
			
			int x = r.nextInt(xSize);
			int y = r.nextInt(ySize);
			
			grid1.set(x, y, foo);
			char bar = grid1.get(x, y);
			assertEquals(foo, bar);
		}
	}
	
	@Test
	void getSizeTest() {
		for (int i = 0; i < N; i++) {
			//+1 to get values greater than zero
			int xSize = r.nextInt(10) +1;
			int ySize = r.nextInt(10) +1;
			
			int correctSize = xSize * ySize;
			IGrid<Character> grid1 = new Grid<Character>(xSize, ySize);
			
			assertEquals(grid1.getSize(), correctSize);
		}
	}
}