package be;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.util.Delay;

public class Main {
	
	static final int nbGoals = 4;
	static final int nbRobots = 2;
	static int nbInactifs = 0;
	
	static RemoteDevice btrdGL = Bluetooth.getKnownDevice("Gurren Lagann");
	static BTConnection btcGL = Bluetooth.connect(btrdGL);
	static InputStream isGL = btcGL.openInputStream();
	static OutputStream osGL = btcGL.openOutputStream();
	static DataInputStream disGL = new DataInputStream(isGL);
	static DataOutputStream dosGL = new DataOutputStream(osGL);
	
	static RemoteDevice btrdGary = Bluetooth.getKnownDevice("Gary");
	static BTConnection btcGary = Bluetooth.connect(btrdGary);
	static InputStream isGary = btcGary.openInputStream();
	static OutputStream osGary = btcGary.openOutputStream();
	static DataInputStream disGary = new DataInputStream(isGary);
	static DataOutputStream dosGary = new DataOutputStream(osGary);
	/*
	static RemoteDevice btrdNXT = Bluetooth.getKnownDevice("NXT");
	static BTConnection btcNXT = Bluetooth.connect(btrdNXT);
	static InputStream isNXT = btcNXT.openInputStream();
	static OutputStream osNXT = btcNXT.openOutputStream();
	static DataInputStream disNXT = new DataInputStream(isNXT);
	static DataOutputStream dosNXT = new DataOutputStream(osNXT);*/
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Connected !");
		
		int[][] goals = new int[nbGoals][2];
		int[][] starts = new int[nbRobots][2];
		int[][] sortie = new int [4][2];
		int[] message = new int[nbRobots];
		int indice, currentGoal;
		boolean fin = false;
		boolean[][] inactifs = new boolean[nbRobots][2];
		
		for(int i = 0; i < nbRobots; i++) {
			inactifs[i][0] = false;
			inactifs[i][1] = false;
		}
		
		sortie[0][0] = 0;	sortie[0][1] = 0;
		sortie[1][0] = 0;	sortie[1][1] = 4;
		sortie[2][0] = 4;	sortie[2][1] = 0;
		sortie[3][0] = 4;	sortie[3][1] = 4;
		
		goals[0][0] = 3; goals[0][1] = 0;
		goals[1][0] = 0; goals[1][1] = 1;
		goals[2][0] = 3; goals[2][1] = 2;
		goals[3][0] = 3; goals[3][1] = 4;
		//goals[4][0] = 4; goals[4][1] = 3;
		//goals[5][0] = 0; goals[5][1] = 0;
		//goals[6][0] = 0; goals[6][1] = 0;
		
		// ------------------------------ //
		
		starts[0][0] = disGL.readInt();
		starts[0][1] = disGL.readInt();
		message[0] = starts[0][0] + starts[0][1] * 10;
		indice = envoieGoal(goals, message[0], nbGoals);
		if(goals[indice][0] != 555)
			currentGoal = goals[indice][0] + goals[indice][1] * 10;
		else
			currentGoal = 555;
		dosGL.writeInt(currentGoal);
		dosGL.flush();
		goals[indice][0] = 555;
		goals[indice][1] = 555;
		
		// ------------------------------ //
		
		starts[1][0] = disGary.readInt();
		starts[1][1] = disGary.readInt();
		message[1] = starts[1][0] + starts[1][1] * 10;
		indice = envoieGoal(goals, message[1], nbGoals);
		if(goals[indice][0] != 555)
			currentGoal = goals[indice][0] + goals[indice][1] * 10;
		else
			currentGoal = 555;
		dosGary.writeInt(currentGoal);
		dosGary.flush();
		goals[indice][0] = 555;
		goals[indice][1] = 555;
		
		// ------------------------------ //
		
		/* starts[2][0] = disNXT.readInt();
		starts[2][1] = disNXT.readInt();
		message[2] = starts[2][0] + starts[2][1] * 10;
		indice = envoieGoal(goals, message[2]);
		if(goals[indice][0] != 555)
			currentGoal = goals[indice][0] + goals[indice][1] * 10;
		else
			currentGoal = 555;
		dosNXT.writeInt(currentGoal);
		dosNXT.flush();
		goals[indice][0] = 555;
		goals[indice][1] = 555; */
		
		// ------------------------------ //
		
		while(!fin) {
			
			if(!inactifs[0][0] && (message[0] = disGL.readInt()) / 100 == 555) {
				Delay.msDelay(500);
				message[0] %= 100;
				indice = envoieGoal(goals, message[0], nbGoals);
				if(indice == -1) {
					indice = 0;
					nbInactifs++;
					inactifs[0][0] = true;
				}
				
				if(goals[indice][0] != 555)
					currentGoal = goals[indice][0] + goals[indice][1] * 10;
				else
					currentGoal = 555;
				dosGL.writeInt(currentGoal);
				dosGL.flush();
				goals[indice][0] = 555;
				goals[indice][1] = 555;
				inactifs[0][1] = true;
				
				if(inactifs[0][0]) {
					indice = envoieGoal(sortie, message[0], 4);
					currentGoal = sortie[indice][0] + sortie[indice][1] * 10;
					sortie[indice][0] = 555;
					sortie[indice][1] = 555;
					dosGL.writeInt(currentGoal);
					dosGL.flush();
				}
			}
			
			// ------------------------------ //
			
			if(!inactifs[1][0] && (message[1] = disGary.readInt()) / 100 == 555) {
				Delay.msDelay(500);
				message[1] %= 100;
				indice = envoieGoal(goals, message[1], nbGoals);
				if(indice == -1) {
					indice = 0;
					nbInactifs++;
					inactifs[1][0] = true;
				}
				
				if(goals[indice][0] != 555)
					currentGoal = goals[indice][0] + goals[indice][1] * 10;
				else
					currentGoal = 555;
				dosGary.writeInt(currentGoal);
				dosGary.flush();
				goals[indice][0] = 555;
				goals[indice][1] = 555;
				inactifs[1][1] = true;
				
				if(inactifs[1][0]) {
					indice = envoieGoal(sortie, message[1], 4);
					currentGoal = sortie[indice][0] + sortie[indice][1] * 10;
					sortie[indice][0] = 555;
					sortie[indice][1] = 555;
					dosGary.writeInt(currentGoal);
					dosGary.flush();
				}
			}
			
			// ------------------------------ //
			
			/* if(!inactifs[2][0] && (message[2] = disNXT.readInt()) / 100 == 555) {
				Delay.msDelay(500);
				message[2] %= 100;
				indice = envoieGoal(goals, message[2], nbGoals);
				if(indice == -1) {
					indice = 0;
					nbInactifs++;
					inactifs[2][0] = true;
				}
				
				if(goals[indice][0] != 555)
					currentGoal = goals[indice][0] + goals[indice][1] * 10;
				else
					currentGoal = 555;
				dosNXT.writeInt(currentGoal);
				dosNXT.flush();
				goals[indice][0] = 555;
				goals[indice][1] = 555;
				inactifs[2][1] = true;
				
				if(inactifs[2][0]) {
					indice = envoieGoal(sortie, message[2], 4);
					currentGoal = sortie[indice][0] + sortie[indice][1] * 10;
					sortie[indice][0] = 555;
					sortie[indice][1] = 555;
					dosNXT.writeInt(currentGoal);
					dosNXT.flush();
				}
			} */
			
			// ------------------------------ //
			
			for(int i = 0; i < nbRobots; i++) {
				for(int j = 0; j < nbRobots; j++) {
					if(i != j && message[j] / 100 != 555 && !inactifs[j][0] && !inactifs[i][0])
						envoie(message[j], i);
				}
				if(!inactifs[i][1])
					envoie(0, i);
				else if(!inactifs[i][0])
					inactifs[i][1] = false;
			}
			
			fin = goalsDispo(goals) && (nbInactifs == nbRobots);
		}
		
		System.out.println("Fin");
		Delay.msDelay(500);
	}
	
	private static boolean goalsDispo(int[][] goals) {
		boolean fin = true;
		for(int i = 0; i < nbGoals; i++)
			if(goals[i][0] != 555) fin = false;
		return fin;
	}

	private static int envoieGoal(int[][] goals, int message, int choix) {
		int x = message % 10;
		int y = (message/10) % 10;
		
		if(goalsDispo(goals)) return -1;
		
		int indice = -1;
		double h = 100, newH;
		for(int i = 0; i < choix; i++) {
			if((newH = heuristique(x, y, goals[i][0], goals[i][1])) < h) {
				h = newH;
				indice = i;
			}
		}
		return indice;
	}

	public static void envoie(int message, int x) throws IOException {
		switch(x) {
			case 0: dosGL.writeInt(message);
					dosGL.flush();
				break;
			case 1: dosGary.writeInt(message);
					dosGary.flush();
				break;
			/*case 2: dosNXT.writeInt(message);
					dosNXT.flush();
				break;*/
			default:
				break;
		}
	}
	
	public static double heuristique(int xs, int ys, int xg, int yg) {
		if(xg == 555 || yg == 555) return 100;
		int x = xg - xs;
		int y = yg - ys;
		return Math.sqrt((x * x) + (y * y));
	}
	
}
