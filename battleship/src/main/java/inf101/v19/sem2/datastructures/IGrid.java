package inf101.v19.sem2.datastructures;

public interface IGrid<T> {
	
	/**
	 * @return width of the grid
	 */
	int getWidth();
	
	/**
	 * @return height of the grid
	 */
	int getHeight();
	
	/**
	 * @param x
	 * 			x position in the grid, must be greater than 0
	 * @param y
	 * 			y position in the grid, must be greater than 0
	 * @param o
	 * 			element to put at position (x,y) in the grid
	 */
	void set(int x, int y, T o);
	
	/**
	 * @param x
	 * 			x position in the grid, must be greater than 0
	 * @param y
	 * 			y position in the grid, must be greater than 0
	 * @return element at position (x,y)
	 */
	T get(int x, int y);
	
	/**
	 * @return get size of the 2-dimensional grid
	 */
	int getSize();
}