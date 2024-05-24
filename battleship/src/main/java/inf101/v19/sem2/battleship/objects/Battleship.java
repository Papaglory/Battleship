package inf101.v19.sem2.battleship.objects;

public class Battleship implements IBoardObject {

	private int length = 4;
	private String name = "Battleship";
	
	@Override
	public int getLength() {
		return length;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public char getSymbol() {
		return 'B';
	}
	
	@Override
	public String toString() {
		return getName() + ", " + hashCode();
	}
	
	@Override
	public IBoardObject copy() {
		return new Battleship();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + length;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Battleship other = (Battleship) obj;
		if (length != other.length)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	
}