package algos;

import be.Coordonnees;
import be.Deplacement;
import be.Fonctions;
import be.Orientation;

public class AlgoProfondeur {

	/* A chaque intersection, le robot explore entièrement une branche
	 * (en suivant l'ordre NORD / EST / SUD / OUEST).
	 * Il remonte ensuite à l'intersection précédente une fois la branche
	 * explorée.
	 */
	
	private static void aux(Coordonnees pos, Coordonnees goal, Orientation[] ori, boolean[][] isChecked, int x, int y, int orientation) {
		if(!Fonctions.fini(pos, goal) && Fonctions.valide(pos.x + x, pos.y + y, 2) && !isChecked[pos.x + x][pos.y + y] && Deplacement.deplacement(Orientation.values()[orientation], ori, pos, 1)) {
			main(pos, goal, ori, isChecked);
			if(!Fonctions.fini(pos, goal)) Deplacement.deplacement(Orientation.values()[(orientation + 2) % 4], ori, pos, 1);
		}
	}
	
	public static void main(Coordonnees pos, Coordonnees goal, Orientation[] ori, boolean[][] isChecked) {

		isChecked[pos.x][pos.y] = true;
		
		aux(pos, goal, ori, isChecked, 0, -1, 0);
		aux(pos, goal, ori, isChecked, 1, 0, 1);
		aux(pos, goal, ori, isChecked, 0, 1, 2);
		aux(pos, goal, ori, isChecked, -1, 0, 3);		
	}

}
