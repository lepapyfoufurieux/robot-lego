package be;

public class Fonctions {

	public static boolean valide(int posX, int posY, int i) {
		return (posX >= 0 && posX < ((Main.XMAZE * 2 + 1) / i) && 
				posY >= 0 && posY < ((Main.YMAZE * 2 + 1) / i));
	}

	public static double heuristique(Coordonnees pos, Coordonnees goal) {
		int x = goal.x - pos.x;
		int y = goal.y - pos.y;
		return Math.sqrt((x * x) + (y * y));
	}

	public static boolean fini(Coordonnees pos, Coordonnees goal) {
		return pos.equals(goal);
	}

}
