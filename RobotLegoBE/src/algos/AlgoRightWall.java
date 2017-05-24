package algos;

import be.Coordonnees;
import be.Deplacement;
import be.Fonctions;
import be.GurrenLagann;
import be.Orientation;
import be.RobotInterface;

public class AlgoRightWall {
	
	/* Un des algorithmes les plus simples et les plus utilisés pour
	 * sortir d'un labyrinthe : Le robot garde un mur à sa droite 
	 */
	
	private static RobotInterface robot = new GurrenLagann();

	// Tant qu'il y a un mur devant le robot, tourne à droite, puis à gauche
	// jusqu'à trouver une direction libre d'accès
	private static void wall(Orientation[] ori) {
		int cpt = 0;
		
		while(cpt == 0 || robot.regardeToutDroit()) {
			switch(cpt) {
				case 0:
					Deplacement.turnRight(ori);
					cpt++;
					break;
				case 1:
		   			Deplacement.turnLeft(ori);
					cpt++;
					break;
				case 2 :
		   			Deplacement.turnLeft(ori);
		   			cpt++;
					break;
				default :
		   			Deplacement.turnLeft(ori);
					break;
			}
	    }
	}
	
	public static void main(Coordonnees pos, Coordonnees goal, Orientation[] ori) {
		while(!Fonctions.fini(pos, goal)) {
			wall(ori);
			Deplacement.avancer(pos, ori, 1);
		}
	}
	
}
