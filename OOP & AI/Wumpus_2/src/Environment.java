import java.util.HashMap;

/*
 * Class that defines the environment.
 * 
 * Written by James P. Biagioni (jbiagi1@uic.edu)
 * for CS511 Artificial Intelligence II
 * at The University of Illinois at Chicago
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

//import java.io.BufferedWriter;

class Environment {
	
//	private char[][][] wumpusWorld;
	private HashMap<Position,Character> wumpusworld;
	private int worldSize;
	private String bar;
	
	private Agent agent;
	//private BufferedWriter outputWriter;
	
	private int[] prevAgentPosition;
	private boolean bump;
	private boolean scream;
	
	public Environment(int size, HashMap<Position,Character> world) { //, BufferedWriter outWriter) {
	
		worldSize = size;
		
	//	wumpusWorld = new char[worldSize][worldSize][4];
		//outputWriter = outWriter;
		wumpusworld = new HashMap<Position,Character>(world);
		prevAgentPosition = getAgentLocation();
		
		bump = false;
		scream = false;
		
		// store world definition
		
				
		// initialize bar to the empty string
		bar = "";
		
		// create divider bar for display output
		for (int i = 0; i < (worldSize * 5) + worldSize - 1; i++) {
			bar = bar + "-";
		}
				
	}
	
	public HashMap<Position,Character> getWumpusWorld() {
		return wumpusworld;
	}
	
	public int getWorldSize() {
		return worldSize;
	}
	
	public char getAgentDirection() {
		
		for (int i = 0; i < worldSize; i++) {
			for (int j = 0; j < worldSize; j++) {
				if (wumpusworld.containsKey(new Position(i,j)) && 
						wumpusworld.get(new Position(i,j)) == 'A') return 'N';
				if (wumpusworld.containsKey(new Position(i,j)) && 
						wumpusworld.get(new Position(i,j))== '>') return 'E';
				if (wumpusworld.containsKey(new Position(i,j)) && 
						wumpusworld.get(new Position(i,j)) == 'V') return 'S';
				if (wumpusworld.containsKey(new Position(i,j)) && 
						wumpusworld.get(new Position(i,j)) == '<') return 'W';
			}
		}
		
		return '@';
	}
	
	public int[] getAgentLocation() {
		
		int[] agentPos = new int[2];
		
		for (int i = 0; i < worldSize; i++) {
			for (int j = 0; j < worldSize; j++) {
				if ((wumpusworld.containsKey(new Position(i,j)) && wumpusworld.get(new Position(i,j)) == 'A')
						|| (wumpusworld.containsKey(new Position(i,j)) && wumpusworld.get(new Position(i,j)) == '<') 
						|| (wumpusworld.containsKey(new Position(i,j)) && wumpusworld.get(new Position(i,j)) == '>')
						|| (wumpusworld.containsKey(new Position(i,j)) && wumpusworld.get(new Position(i,j)) == 'V')
						) {
					agentPos[0] = i;
					agentPos[1] = j;
				}
			}
		}
		
		return agentPos;		
	}
	
	public void placeAgent(Agent theAgent) {
		Position pA = new Position(prevAgentPosition[0],prevAgentPosition[1]);
	//	wumpusWorld[prevAgentPosition[0]][prevAgentPosition[1]][3] = ' ';
		wumpusworld.put(pA,' ');
		
		agent = theAgent;		
	//	wumpusWorld[agent.getLocation()[0]][agent.getLocation()[1]][3] = agent.getAgentIcon();
		Position p2A = new Position(agent.getLocation()[0],agent.getLocation()[1]);
		wumpusworld.put(p2A, agent.getAgentIcon());
		
		prevAgentPosition[0] = agent.getLocation()[0];
		prevAgentPosition[1] = agent.getLocation()[1];
		
	}
	
	public void setBump(boolean bumped) {
		bump = bumped;
	}
	
	public boolean getBump() {
		return bump;
	}
	
	public void setScream(boolean screamed) {
		scream = screamed;
	}
	
	public boolean getScream() {
		return scream;
	}
	

	public boolean grabGold() {
		
		if (//percepts[agent.getLocation()[0]][agent.getLocation()[1]][2] == 'G'
			wumpusworld.containsKey(new Position(agent.getLocation()[0],agent.getLocation()[1]))	&&
			wumpusworld.get(new Position(agent.getLocation()[0],agent.getLocation()[1])) =='G'	) {
	//		percepts[agent.getLocation()[0]][agent.getLocation()[1]][2] = ' ';
	//		wumpusWorld[agent.getLocation()[0]][agent.getLocation()[1]][2] = ' ';
			wumpusworld.put(new Position(agent.getLocation()[0],agent.getLocation()[1]),' ');
			return true;
		}
		return false;
		
	}
	
	public boolean checkDeath() {
		
	//	if (wumpusWorld[agent.getLocation()[0]][agent.getLocation()[1]][0] == 'P') return true;
		if (wumpusworld.get(new Position(agent.getLocation()[0],agent.getLocation()[1]))=='P')	return true;
	//	else if (wumpusWorld[agent.getLocation()[0]][agent.getLocation()[1]][1] == 'W') return true;
		else if (wumpusworld.get(new Position(agent.getLocation()[0], agent.getLocation()[1]))=='W')	return true;
		return false;
		
	}
	
	public boolean shootArrow() {
		
		if (agent.getDirection() == 'N') {
			
			for (int i = agent.getLocation()[0]; i < worldSize; i++) {
				if (//wumpusWorld[i][agent.getLocation()[1]][1] == 'W'
						wumpusworld.get(new Position(i,agent.getLocation()[1])) =='W') {
				//	wumpusWorld[i][agent.getLocation()[1]][1] = '*';
					wumpusworld.put(new Position(i,agent.getLocation()[1]), '*');
					int x = i;
					int y = agent.getLocation()[1];
					
				
					
					return true;
				}
			}
		}
		else if (agent.getDirection() == 'E') {
			
			for (int i = agent.getLocation()[1]; i < worldSize; i++) {
				if (//wumpusWorld[agent.getLocation()[0]][i][1] == 'W'
						wumpusworld.get(new Position(agent.getLocation()[0],i)) =='W') {
				//	wumpusWorld[agent.getLocation()[0]][i][1] = '*';
					wumpusworld.put(new Position(agent.getLocation()[0],i), '*');
					int x = agent.getLocation()[0];
					int y = i;
					
					
					return true;
				}
			}
		}
		else if (agent.getDirection() == 'S') {
			
			for (int i = agent.getLocation()[0]; i >= 0; i--) {
				if (//wumpusWorld[i][agent.getLocation()[1]][1] == 'W'
						wumpusworld.get(new Position(i,agent.getLocation()[1])) =='W') {
				//	wumpusWorld[i][agent.getLocation()[1]][1] = '*';
					wumpusworld.put(new Position(i,agent.getLocation()[1]), '*');
					int x = i;
					int y = agent.getLocation()[1];
					
				
					return true;
				}
			}
		}
		else if (agent.getDirection() == 'W') {
			
			for (int i = agent.getLocation()[1]; i >= 0; i--) {
				if (//wumpusWorld[agent.getLocation()[0]][i][1] == 'W'
						wumpusworld.get(new Position(agent.getLocation()[0],i)) =='W') {
				//	wumpusWorld[agent.getLocation()[0]][i][1] = '*';
					wumpusworld.put(new Position(agent.getLocation()[0],i), '*');
					int x = agent.getLocation()[0];
					int y = i;
					
				
					//printPercepts();
					
					return true;
				}
			}			
		}
		
		return false;
		
	}
}