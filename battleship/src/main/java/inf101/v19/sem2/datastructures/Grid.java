package inf101.v19.sem2.datastructures;

import java.util.ArrayList;
import java.util.List;

public class Grid<T> implements IGrid<T> {
	
	private int width;
	private int height;
	private List<T> tiles;

	/**
	 * @param width
	 * 				horizontal size of the grid, must be greater than zero
	 * @param height
	 * 				vertical size of the grid, must be greater than zero
	 */
	public Grid(int width, int height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("width and height must be greater than zero");
		}
		this.width = width;
		this.height = height;
		tiles = new ArrayList<T>();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles.add(null);
			}
		}
	}
	
	@Override
	public T get(int x, int y) {
		if (x >= width || x < 0 || y >= height || y < 0) {
			throw new IndexOutOfBoundsException("width or height is out of bound");
		}
		int pos = (x + y*width);
		return tiles.get(pos);
	}
	
	@Override
	public void set(int x, int y, T o) {
		if (x > width || x < 0 || y > height || y < 0) {
			throw new IndexOutOfBoundsException("width or height is out of bound");
		}
		int pos = (x + y*width);
		tiles.set(pos, o);
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public int getSize() {
		//notice that width and height must be greater than 0
		return width * height;
	}
}