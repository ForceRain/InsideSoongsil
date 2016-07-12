/*
 * Wumpus-Lite, version 0.21 alpha
 * A lightweight Java-based Wumpus World Simulator
 * 
 * Written by James P. Biagioni (jbiagi1@uic.edu)
 * for CS511 Artificial Intelligence II
 * at The University of Illinois at Chicago
 * 
 * Thanks to everyone who provided feedback and
 * suggestions for improving this application,
 * especially the students from Professor
 * Gmytrasiewicz's Spring 2007 CS511 class.
 * 
 * Last modified 4/14/08
 * 
 * DISCLAIMER:
 * Elements of this application were borrowed from
 * the client-server implementation of the Wumpus
 * World Simulator written by Kruti Mehta at
 * The University of Texas at Arlington.
 * 
 */


import java.io.*;
import java.util.*;


class WorldApplication {
	
	private static String VERSION = "v0.21a";
	String gameboard="";
	String out="";
	int numTrials;
	int maxSteps;
	int worldSize;
	int hurestic;
	static char[][] readWorld;
	static char[][][] newWorld;

	public static void main (String args[]) throws Exception {
		
		WorldApplication wa=new WorldApplication();
		
		boolean nonDeterministicMode=false;

		if(wa.readPara(args)==6)
		{
			 FileWriter fw=new FileWriter(wa.out);
			
		
			    int trialScores[] = new int[wa.numTrials];
			    String trialStateSeqs[]=new String[wa.numTrials];
			    String path[]=new String[wa.numTrials];
			    int totalScore = 0;
		    
			    for (int currTrial = 0; currTrial < wa.numTrials; currTrial++) {
			    	
			    	HashMap<Position,Character> wumpusWorld = new HashMap<Position,Character>
			    							( readWumpusWorld(wa.worldSize,wa.gameboard));
					 
					Environment wumpusEnvironment = new Environment(wa.worldSize, wumpusWorld); 
			    		    	
			    	Simulation trial = new Simulation(wumpusEnvironment, wa.maxSteps, nonDeterministicMode,wa.hurestic); //, outputWriter, nonDeterministicMode);
			    	trialScores[currTrial] = trial.getScore();
			    	trialStateSeqs[currTrial]=trial.getStateSeq();
			    	path[currTrial]=trial.getpath();
			    }

			    for (int i = 0; i < wa.numTrials; i++) {
			    	
			    	fw.write("\nTrial " + (i+1) + " score: " + trialScores[i] + "\n");
			    	fw.write("Trial " + (i+1) + " StateSeq: " + trialStateSeqs[i] + "\n");
			    	fw.write("Trial " + (i+1) + " path: " + path[i] + "\n");
			    	
			    	totalScore += trialScores[i];
			    }
			    
			    fw.write("\nNumber of trials: "+wa.numTrials+"\n");
			    fw.write("Total Score: " + totalScore + "\n");			    
			    fw.write("Average Score: " + ((double)totalScore/(double)wa.numTrials)+"\n");
			    fw.close();
		}
		else
		{
			wa.usage();
		}
		
	}
	private void usage() {
		
		System.out.println("Usage:\n\n-i gameboard.txt");
		System.out.println("-o output.txt");
		System.out.println("-n number of trails");
		System.out.println("-ms max steps");
		System.out.println("-ws world size");
		System.out.println("-h heuristic number 1,2,3,4,5");
		
		System.out.println("\njava WorldApplication -i gameborad.txt -o output.txt -n 1 -ms 50 -ws 4 -h 1");
		
	}

	private int readPara(String args[]) {
		
		int n=0;
		
		for(int i=0;i<args.length;i++)
		{
			if(args[i].equals("-i"))
			{	this.gameboard=args[i+1]; n++;}
			else if(args[i].equals("-o"))
			{	this.out=args[i+1]; n++;}
			else if(args[i].equals("-n"))
			{	this.numTrials=Integer.parseInt(args[i+1]); n++;}
			else if(args[i].equals("-ms"))
			{	this.maxSteps=Integer.parseInt(args[i+1]); n++;}
			else if(args[i].equals("-ws"))
			{	this.worldSize=Integer.parseInt(args[i+1]); n++;}
			else if(args[i].equals("-h"))
			{	this.hurestic=Integer.parseInt(args[i+1]); n++;}
		}
		
		return n;
	}

	public static HashMap<Position,Character> readWumpusWorld(int size, String gameboard) throws Exception {		
	//	newWorld = new char[size][size][4];
		readWorld = new char[4][size*6+1];
		HashMap<Position,Character> container = new HashMap<Position,Character>(100000000);
		File file=new File(gameboard);
		Scanner f_read=new Scanner(file);
		String in="";
		
		//4 P,W,G,A
		int h_size=size*3+1;
		int w_size=size*6+1;
		int r_ptr=0;
		try{
			for (int i=0,h_ptr=1;i<h_size;i++){
				in=f_read.nextLine();
				if (in.length() != 0)
					readWorld[r_ptr++]=in.toCharArray();
				if (r_ptr==3){
					for (int j=1,w_ptr=0;j<w_size;j+=6,w_ptr++){
				//		newWorld[size-h_ptr][w_ptr][0]=readWorld[1][j+1];	// P
				//		newWorld[size-h_ptr][w_ptr][1]=readWorld[1][j+3];	// W
				//		newWorld[size-h_ptr][w_ptr][2]=readWorld[2][j+1];	// G
				//		newWorld[size-h_ptr][w_ptr][3]=readWorld[2][j+3];	// A
						if (readWorld[1][j+1] != ' ')
							container.put(new Position(size-h_ptr,w_ptr), readWorld[1][j+1]);
						else if (readWorld[1][j+3] != ' ')
							container.put(new Position(size-h_ptr,w_ptr), readWorld[1][j+3]);
						else if (readWorld[2][j+1] != ' ')
							container.put(new Position(size-h_ptr,w_ptr), readWorld[2][j+1]);
						else if (readWorld[2][j+3] != ' ')
							container.put(new Position(size-h_ptr,w_ptr), readWorld[2][j+3]);
					//	else
					//		container.put(new Position(size-h_ptr,w_ptr), ' ');
					}
					r_ptr=0;
					h_ptr++;
				}
			}
		}	
		catch(Exception e){
			e.printStackTrace();
		}
	//	System.out.println(container);
		f_read.close();
		//4 P,W,G,A
		return container;
	}	
}

class Position
{
	private int X;
	private int Y;
	public Position(int x,int y){
		this.X=x;
		this.Y=y;
	}
	public String toString(){
		return X+","+Y;
	}
	public boolean equals(Object x){
		Position t = (Position)x;
		return t.X==this.X && t.Y==this.Y;
	}
	public int hashCode(){
		String s=""+X+" "+Y;
		return s.hashCode();
	}
}

