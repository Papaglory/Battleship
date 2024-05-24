package inf101.v19.sem2.datastructures;

public class Location<T> implements ILocation<T> {

	private int x;
	private int y;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	private T data;
	
	public Location(int x, int y, T data) {
		this.x = x;
		this.y = y;
		this.data = data;
	}
	
	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public void setData(T data) {
		this.data = data;
	}
	
	@Override
	public T getData() {
		return data;
	}
	
	@Override
	public String toString() {
		String s = "(" + x + ", " + y + ")@" + hashCode();
		return s;
	}
}