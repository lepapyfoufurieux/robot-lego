package be;

public enum Orientation {
	NORD,
	EST,
	SUD,
	OUEST;
	
	public Orientation next() {
		return values()[(ordinal()+1) % 4];
	}
	
	public Orientation previous() {
		return values()[(ordinal()+3) % 4];
	}
	
	public String toString() {
		switch(ordinal()) {
		case 0: return "NORD";
		case 1: return "EST";
		case 2: return "SUD";
		case 3: return "OUEST";
		default: return "X";
		}
	}
}
