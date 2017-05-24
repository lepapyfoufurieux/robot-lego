package be;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import algos.AlgoDStar;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Main {
	
	public static final int XMAZE = 5;
	public static final int YMAZE = 5;
	public static final int XSTART = 0;
	public static final int YSTART = 0;
	public static final int XGOAL = 0;
	public static final int YGOAL = 0;
	public static final Orientation ORISTART = Orientation.EST;
	
	private static BTConnection btc = Bluetooth.waitForConnection();
	private static InputStream is = btc.openInputStream();
	private static OutputStream os = btc.openOutputStream();
    public static DataInputStream dis = new DataInputStream(is);
    public static DataOutputStream dos = new DataOutputStream(os);
	
	public static void main(String[] args) throws IOException{
		
		Coordonnees start = new Coordonnees(XSTART, YSTART);
		Coordonnees goal = new Coordonnees(XGOAL, YGOAL);
		
		Orientation[] ori = new Orientation[1];
		ori[0] = ORISTART;

	    boolean[][] isChecked = new boolean[XMAZE][YMAZE];
	    boolean[][] maze = new boolean[XMAZE*2+1][YMAZE*2+1];
	    initChecked(isChecked);
	    initMaze(maze);
	    
		dos.writeInt(XSTART);
		dos.flush();
		dos.writeInt(YSTART);
		dos.flush();
		
		start.x = XSTART * 2 + 1;
		start.y = YSTART * 2 + 1;
		
		int currentGoal;
		currentGoal = dis.readInt();
		goal.x = currentGoal % 10;
		goal.y = (currentGoal / 10) % 10; 		
		
		while(currentGoal != 555) {
			goal.x = goal.x * 2 + 1;
			goal.y = goal.y * 2 + 1;
			
			AlgoDStar.main(start, goal, ori, isChecked, maze, true);
			int message = (start.x/2) + (start.y/2) * 10 + 555 * 100;
			dos.writeInt(message);
			dos.flush();
			
			currentGoal = dis.readInt();
			if(currentGoal != 555) {
				goal.x = currentGoal % 10;
				goal.y = (currentGoal / 10) % 10;
			}
		}
		
		currentGoal = dis.readInt();
		goal.x = (currentGoal % 10) * 2 + 1;
		goal.y = ((currentGoal / 10) % 10) * 2 + 1;
		AlgoDStar.main(start, goal, ori, isChecked, maze, false);
		
		dis.close();
		dos.close();
		btc.close();
	}
	
	private static void initMaze(boolean[][] maze) {
		int mazeX = XMAZE * 2 + 1;
		int mazeY = YMAZE * 2 + 1;
		
		for(int i = 0; i < mazeX; i++)
			for(int j = 0; j < mazeY; j++) {
				if (i == 0 || j == 0 || i == (mazeX - 1) || j == (mazeY - 1))
					maze[i][j] = false;
				else
					maze[i][j] = !((i % 2 == 0) && (j % 2 == 0));
			}
	}
	
	private static void initChecked(boolean[][] tab) {
		for(int i = 0; i < XMAZE; i++)
			for(int j = 0; j < YMAZE; j++)
				tab[i][j] = false;
	}

}
