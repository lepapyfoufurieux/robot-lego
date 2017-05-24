package algos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import be.Case;
import be.Coordonnees;
import be.Deplacement;
import be.Fonctions;
import be.GurrenLagann;
import be.Main;
import be.Orientation;
import be.RobotGary;
import be.RobotInterface;
import lejos.util.Delay;

public class AlgoDStar {

	static RobotInterface robot = new GurrenLagann();
	//static RobotInterface robot = new RobotGary();
	//static RobotInterface robot = new NXT();
	
	private static void majMap(boolean[][] maze, Coordonnees pos, Orientation[] ori) {
		switch(ori[0]) {
			case NORD: maze[pos.x][pos.y - 1] = false;
				break;
			case EST: maze[pos.x + 1][pos.y] = false;
				break;
			case SUD: maze[pos.x][pos.y + 1] = false;
				break;
			case OUEST: maze[pos.x - 1][pos.y] = false;
				break;
	    }
	}
	
	private static void scan(boolean[][] maze, Coordonnees pos, Orientation[] ori) {
		if(robot.regardeToutDroit())
	          majMap(maze, pos, ori);
		
	    ori[0] = ori[0].next();
	    if(robot.regardeADroite())
	          majMap(maze, pos, ori);
	    
	    ori[0] = ori[0].previous();
	    ori[0] = ori[0].previous();
	    if(robot.regardeAGauche())
	          majMap(maze, pos, ori);
	    
		ori[0] = ori[0].next();
	}

	private static void envoieMessage(boolean[][] maze, Coordonnees pos) throws IOException {
		int message = pos.x/2 + 10 * (pos.y/2);
    	if (maze[pos.x-1][pos.y])
    		message += 1 * 100;
    	if (maze[pos.x][pos.y+1])
    		message += 1 * 1000;
    	if (maze[pos.x+1][pos.y])
    		message += 1 * 10000;
    	if (maze[pos.x][pos.y-1])
    		message += 1 * 100000;
    	
    	Main.dos.writeInt(message);
    	Main.dos.flush();
	}
	
	private static void receptionMessage(boolean[][]maze, boolean[][] isChecked, Coordonnees pos) throws IOException {
		int x, y, message;
    	while((message = Main.dis.readInt()) != 0) {
    		x = message % 10;
    		message /= 10;
    		y = message % 10;
    		message /= 10;
    		isChecked[x][y] = true;
    		x = x * 2 + 1;
    		y = y * 2 + 1;
    		if (message % 10 == 0)
    			maze[x-1][y] = false;
    		message /= 10;
    		if (message % 10 == 0)
    			maze[x][y+1] = false;
    		message /= 10;
    		if (message % 10 == 0)
    			maze[x+1][y] = false;
    		message /= 10;
    		if (message % 10 == 0)
    			maze[x][y-1] = false;
    		message /= 10;
    	}
	}
	
	private static void parcourirChemin(boolean[][] maze, boolean[][] isChecked, Coordonnees pos, Orientation[] ori, List<Orientation> l, boolean comm) throws IOException {
		boolean fin = false;
		
	    while(!l.isEmpty() && !fin) {
	    	if(!Deplacement.deplacement(l.get(l.size()-1), ori, pos, 2))
	    		fin = true;
			else
	    		l.remove(l.size()-1);
	    	
    		if(!isChecked[pos.x/2][pos.y/2]) {
    			scan(maze, pos, ori);
    			isChecked[pos.x/2][pos.y/2] = true; 
    		}
	    	
    		if(comm) {
    			envoieMessage(maze, pos);
    			receptionMessage(maze, isChecked, pos);
    		}
	    }
	    
	    l.clear();
	}
	
	private static Orientation newDir(Coordonnees pos1, Coordonnees pos2) {
		int xDiff = pos2.x - pos1.x;
		int yDiff = pos2.y - pos1.y;
	    
		if(Math.abs(xDiff) > Math.abs(yDiff)) {
			if(xDiff < 0)
				return Orientation.OUEST;
			else
				return Orientation.EST;
		} else {
			if(yDiff < 0)
				return Orientation.NORD;
			else
				return Orientation.SUD;
		}
	}
	
	private static boolean appartient(List<Case> openList, Case node) {
		for(Case c : openList) {
			if(c.pos.x == node.pos.x && c.pos.y == node.pos.y)
				return true;
		}
		return false;
	}
	
	private static boolean rechercheChemin(boolean[][] maze, Coordonnees pos, Coordonnees goal, List<Orientation> l) {
		
		List<Case> openList = new LinkedList<Case>();
        List<Case> closedList = new LinkedList<Case>();
        
		Case first = new Case();
		first.pos = pos;
		first.cout = 0;
		first.heuristique = Fonctions.heuristique(pos, goal);
        
        openList.add(first);
        
        Case current = new Case();
        boolean done = false;
        while(!done) {
            current = lowestFInOpen(openList);
            closedList.add(current);
            openList.remove(current);

            if ((current.pos.x == goal.x) && (current.pos.y == goal.y)) {
                calcPath(first, current, l);
                return true;
            }
            
            List<Case> adjacentNodes = getAdjacent(current, maze);
            for (int i = 0; i < adjacentNodes.size(); i++) {
                Case currentAdj = adjacentNodes.get(i);
                
                if(!appartient(openList, currentAdj) && !appartient(closedList, currentAdj)) {
                    currentAdj.pere = current;
                    currentAdj.heuristique = Fonctions.heuristique(currentAdj.pos, goal);
                    currentAdj.cout = current.cout + 1;
                    openList.add(currentAdj);
                }
            }

            if (openList.isEmpty()) {
                return false;
            }
        }
        return false;
	}
	
	private static void aux(Case current, boolean[][] maze, int x, int y, List<Case> liste) {
		Coordonnees newPos = new Coordonnees(current.pos.x + x, current.pos.y + y);
		Case newC = new Case();
		
		newC.pos = newPos;
		if(Fonctions.valide(newPos.x, newPos.y, 1) && maze[current.pos.x + x/2][current.pos.y + y/2] == true)
			liste.add(newC);
	}
	
	private static List<Case> getAdjacent(Case current, boolean[][] maze) {
		List<Case> liste = new LinkedList<Case>();
		
		aux(current, maze, 2, 0, liste);
		aux(current, maze, 0, 2, liste);
		aux(current, maze, -2, 0, liste);
		aux(current, maze, 0, -2, liste);
		
		return liste;
	}

	private static void calcPath(Case first, Case last, List<Orientation> l) {
		while(!first.equals(last)) {
			l.add(newDir(last.pere.pos, last.pos));
			last = last.pere;
		}
	}

	private static Case lowestFInOpen(List<Case> openSet) {
		Case c = openSet.get(0);
		for(int i = 0; i < openSet.size(); i++) {
			if((c.cout + c.heuristique) > openSet.get(i).cout + openSet.get(i).heuristique) {
				c = openSet.get(i);
			}
		}
		return c;
	}

	public static void main(Coordonnees pos, Coordonnees goal, Orientation[] ori, boolean[][] isChecked, boolean[][] maze, boolean comm) throws IOException {
		List<Orientation> l = new ArrayList<Orientation>();
	    
		if(!isChecked[pos.x/2][pos.y/2]) {
			scan(maze, pos, ori);
			isChecked[pos.x/2][pos.y/2] = true;
		}
		
	    while(!Fonctions.fini(pos, goal)) {
			if(!rechercheChemin(maze, pos, goal, l)) {
				System.out.println("Pas de chemin");
				System.out.println("vers la sortie.");
				Delay.msDelay(1000);
				break;
			}
			parcourirChemin(maze, isChecked, pos, ori, l, comm);
	    }
	}

}
