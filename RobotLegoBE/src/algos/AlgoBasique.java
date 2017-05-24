package algos;

import be.Coordonnees;
import be.Deplacement;
import be.Fonctions;
import be.GurrenLagann;
import be.Orientation;
import be.RobotInterface;

public class AlgoBasique {

	private static RobotInterface robot = new GurrenLagann();
	
	// Tant qu'il y a un mur devant le robot, tourne à droite, puis fais un demi-tour, puis tourne à gauche
	private static void wall(Orientation[] ori) {
		int cpt = 0;
		
		while(robot.regardeToutDroit()) {
			switch(cpt) {
				case 0:
	    			Deplacement.turnRight(ori);
					cpt++;
					break;
				case 1:
	       			Deplacement.turnAround(ori);
					cpt++;
					break;
				default:
	       			Deplacement.turnLeft(ori);
					break;
			}
		}
	}
	
	// Le robot avance jusqu'à trouver un mur, puis tourne vers une direction libre d'accès
	public static void main(Coordonnees pos, Coordonnees goal, Orientation ori[]) {		
		while(!Fonctions.fini(pos, goal)) {
	    	while(!robot.regardeToutDroit()) {
				if(Fonctions.fini(pos, goal)) break;
				Deplacement.avancer(pos, ori, 1);
			}
			wall(ori);
		}		
	}

}
