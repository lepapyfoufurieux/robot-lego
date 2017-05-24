package be;

public class Deplacement {

	private static RobotInterface robot = new GurrenLagann();
	//private static RobotInterface robot = new RobotGary();
	//static RobotInterface robot = new NXT();
		
	public static void turnLeft(Orientation[] ori) {
		robot.tourneAGauche();
		ori[0] = ori[0].previous();
	}
	
	public static void turnRight(Orientation[] ori) {
		robot.tourneADroite();
		ori[0] = ori[0].next();
	}
	
	public static void turnAround(Orientation[] ori) {
		robot.tourneDemiTour();
		ori[0] = ori[0].next();
		ori[0] = ori[0].next();
	}
	
	private static void majPos(Coordonnees pos, Orientation[] ori, int pas) {
	    switch(ori[0]) {
			case NORD: pos.y -= pas;
				break;
			case EST: pos.x += pas;
				break;
			case SUD: pos.y += pas;
				break;
			case OUEST: pos.x -= pas;
				break;
		}
	}
	
	public static void avancer(Coordonnees pos, Orientation[] ori, int pas) {
		robot.avanceUneCase();
		majPos(pos, ori, pas);
	}
	
	public static boolean deplacement(Orientation goal, Orientation[] ori, Coordonnees pos, int pas) {
		switch(ori[0]) {
			case NORD:
				switch(goal) {
					case NORD:
						break;
					case EST: turnRight(ori);
						break;
					case SUD: turnAround(ori);
						break;
					case OUEST: turnLeft(ori);
						break;
				}
				break;
			case EST:
				switch(goal) {
					case NORD: turnLeft(ori);
						break;
					case EST:
						break;
					case SUD: turnRight(ori);
						break;
					case OUEST: turnAround(ori);
						break;
				}
				break;
			case SUD:
				switch(goal) {
					case NORD: turnAround(ori);
						break;
					case EST: turnLeft(ori);
						break;
					case SUD:
						break;
					case OUEST: turnRight(ori);
						break;
				}
				break;
			case OUEST:
				switch(goal) {
					case NORD: turnRight(ori);
						break;
					case EST: turnAround(ori);
						break;
					case SUD: turnLeft(ori);
						break;
					case OUEST:
						break;
				}
				break;
		}
		
		if(robot.regardeToutDroit())
			return false;
		
		avancer(pos, ori, pas);
		return true;
	}
	
}
