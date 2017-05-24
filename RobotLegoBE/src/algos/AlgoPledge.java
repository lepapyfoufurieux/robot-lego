package algos;

import be.Coordonnees;
import be.Deplacement;
import be.Fonctions;
import be.GurrenLagann;
import be.Orientation;
import be.RobotInterface;

public class AlgoPledge {
	
	/* Amélioration de l'algorithme consistant à garder un mur à sa droite :
	 * Une variable est initialisée à 0. On l'incrémente ou la décrémente selon
	 * si le robot à droite ou à gauche.
	 * Si cette variable est égale à 0, le robot avance jusqu'à trouver un mur.
	 * Cela empêche au robot de tourner autour d'un ilot central.
	 */
	
	private static RobotInterface robot = new GurrenLagann();

	private static int wall(Orientation[] ori, int cptPledge) {
		int cpt = 0;
		
		while (cpt == 0 || robot.regardeToutDroit()) {
			switch(cpt) {
				case 0:
					Deplacement.turnLeft(ori);
					cpt++;
					cptPledge--;
					break;
				case 1:
					Deplacement.turnRight(ori);
					cpt++;
					cptPledge++;
					break;
				case 2 :
					Deplacement.turnRight(ori);
					cpt++;
					cptPledge++;
					break;
				default :
					Deplacement.turnRight(ori);
		   			cptPledge++;
					break;
			}
	    }
		return cptPledge;
	}

	public static void main(Coordonnees pos, Coordonnees goal, Orientation[] ori) {
		int cpt = 0;

		while(!Fonctions.fini(pos, goal)) {
			if(cpt == 0) {
				while(!robot.regardeToutDroit())
	    			Deplacement.avancer(pos, ori, 1);
	   			Deplacement.turnRight(ori);
	   			cpt++;
	   			if(robot.regardeToutDroit())
	   				cpt = wall(ori, cpt);
			}
	  		Deplacement.avancer(pos, ori, 1);
			cpt = wall(ori, cpt);
		}
	}
}
