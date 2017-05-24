package algos;

import java.util.LinkedList;
import java.util.List;

import be.Case;
import be.Coordonnees;
import be.Deplacement;
import be.Fonctions;
import be.Main;
import be.Orientation;

public class AlgoPerso {

	/* Algorithme personnalisé :
	 * Le robot crée la liste des cases alentours. Il essaie d'accéder à celle
	 * avec la meilleure heuristique. Il essaie la suivante et ainsi de suite
	 * s'il est bloqué par un mur.
	 * Il augmente l'heuristique des cases sur lesquelles il est déjà
	 * passé pour éviter d'y repasser.
	 * Cet algorithme peut être très performant dans certains cas.
	 */
	
	// Initialisation d'un tableau contenant l'heuristique de chaque case
	private static void initTabH(double[][] tabH, Coordonnees goal) {
		Coordonnees pos = new Coordonnees(0, 0);
		for(int i = 0; i < Main.XMAZE; i++) {
			for(int j = 0; j < Main.YMAZE; j++) {
				pos.x = i;
				pos.y = j;
				tabH[i][j] = Fonctions.heuristique(pos, goal);
			}
		}
	}
	
	// Tri à bulle en fonction de l'heuristique des cases du tableau
	private static void tri(List<Case> tab) {
		boolean fini = false;
		Case n = new Case();
		int taille = tab.size() - 1;
		
		while(!fini) {
			fini = true;
			for(int i = 0; i < taille; i++) {
				if(tab.get(i).heuristique > tab.get(i+1).heuristique){
					n = tab.get(i);
					tab.set(i, tab.get(i+1));
					tab.set(i+1, n);
					fini = false;
				}
			}
			taille--;
		}
	}
	
	private static void aux(int x, int y, double tabH[][], List<Case> caseA, Coordonnees pos) {
		Case n = new Case();
		int posX = pos.x + x;
		int posY = pos.y + y;
		Coordonnees newPos = new Coordonnees(posX, posY);
		if(Fonctions.valide(posX, posY, 2)) {
			n.pos = newPos;
			if(x == -1) n.ori = Orientation.OUEST;
			else if(x == 1) n.ori = Orientation.EST;
			else if(y == -1) n.ori = Orientation.NORD;
			else n.ori = Orientation.SUD;
			n.heuristique = tabH[posX][posY];
			caseA.add(n);
		}
	}
	
	public static void main(Coordonnees pos, Coordonnees goal, Orientation[] ori) {
		double[][] tabH = new double[Main.XMAZE][Main.YMAZE];
		List<Case> caseAdjacente = new LinkedList<Case>();
		initTabH(tabH, goal);
		
		while(!Fonctions.fini(pos, goal)) {
			aux(-1, 0, tabH, caseAdjacente, pos);
			aux(0, -1, tabH, caseAdjacente, pos);
			aux(0, 1, tabH, caseAdjacente, pos);
			aux(1, 0, tabH, caseAdjacente, pos);
			tri(caseAdjacente);

			while(!caseAdjacente.isEmpty() && !Deplacement.deplacement(caseAdjacente.get(0).ori, ori, pos, 1))
				caseAdjacente.remove(0);
			caseAdjacente.clear();
			tabH[pos.x][pos.y] += 2;
		}
	}

}
