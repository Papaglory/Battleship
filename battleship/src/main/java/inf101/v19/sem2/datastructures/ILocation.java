package inf101.v19.sem2.datastructures;

public interface ILocation<T> {

	/**
	 * @return get x value of this location
	 */
	int getX();
	
	/**
	 * @return get y value of this location
	 */
	int getY();

	/**
	 * Set this location to store a new data
	 * @param data
	 */
	void setData(T data);
	
	/**
	 * @return the data this location is storing
	 */
	T getData();
}