package algos;

import be.Coordonnees;
import be.Deplacement;
import be.Fonctions;
import be.GurrenLagann;
import be.Orientation;
import be.RobotInterface;

public class AlgoPledge {
	
	/* Am�lioration de l'algorithme consistant � garder un mur � sa droite :
	 * Une variable est initialis�e � 0. On l'incr�mente ou la d�cr�mente selon
	 * si le robot � droite ou � gauche.
	 * Si cette variable est �gale � 0, le robot avance jusqu'� trouver un mur.
	 * Cela emp�che au robot de tourner autour d'un ilot central.
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
