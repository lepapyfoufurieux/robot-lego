package be;

public class Coordonnees {

	public int x;
	public int y;
	
	public Coordonnees(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Coordonnees c) {
		return (this.x == c.x && this.y == c.y);
	}
	
}
