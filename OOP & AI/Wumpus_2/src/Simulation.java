/*
 * Class that defines the simulation environment.
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

import java.util.*;

class Simulation {
	
	private int currScore = 0;
	private static int actionCost = 1;
	private static int deathCost = 0;
	private static int shootCost = 2;
	private static int goldCost = 0;
	private int stepCounter = 0;
	private int lastAction = 0;
	
	private boolean simulationRunning;
	
	private Agent agent;
	private Environment environment;
	private TransferPercept transferPercept;
	
	private ArrayList<Integer> StateSeq=new ArrayList<Integer>();
	private ArrayList<Integer[]> path=new ArrayList<Integer[]>();
	
	
	public Simulation(Environment wumpusEnvironment, int maxSteps, boolean nonDeterministic, int heuristic) { 
		simulationRunning = true;
		transferPercept = new TransferPercept(wumpusEnvironment);
		environment = wumpusEnvironment;
		
		agent = new Agent(environment, transferPercept, nonDeterministic);
		
		environment.placeAgent(agent);
		agent.setMapInfo();
		StateSeq.add(0);			// start
		agent.startSearching(heuristic);		// start search with specified number of heuristic function.
		
		System.out.println("Searching End.");
		
		while (true){
			int action = agent.chooseAction();
			if (action == -1)
				break;
			StateSeq.add(action);
		}
	}
	public String getStateSeq()
	{
		String seq=Action.printAction(StateSeq.get(0))+",";
		
		for(int i=1;i<StateSeq.size()-1;i++)
		{
			seq+=(i)+"_"+Action.printAction(StateSeq.get(i))+",";
		}
		
		seq+=Action.printAction(StateSeq.get(StateSeq.size()-1));
		
		return seq;
	}
	public String getpath()
	{
		String path="("+this.path.get(0)[0]+","+this.path.get(0)[1]+")";
		
		for(int i=1;i<this.path.size();i++)
			path+=",("+this.path.get(i)[0]+","+this.path.get(i)[1]+")";
		
		return path;
	}
	public int getScore()
	{	
	//	System.out.println("gg");
		for(int i=0;i<StateSeq.size();i++)
		{
			int tmpArr[]=new int[2];
			tmpArr=environment.getAgentLocation();
			Integer[] tmpObj=new Integer[2];
			tmpObj[0]=tmpArr[0];
			tmpObj[1]=tmpArr[1];
			this.path.add(tmpObj);
	//		System.out.println(currScore);
			
			handleAction(StateSeq.get(i));
		}
		return currScore;
	}
	
	public void handleAction(int action) {
		
		try {
		
			if (action == Action.GO_FORWARD) {
				
				if (environment.getBump() == true) environment.setBump(false);
				
				agent.goForward();
				environment.placeAgent(agent);
				
				if (environment.checkDeath() == true) {
					
					currScore += deathCost;
					simulationRunning = false;
					
					agent.setIsDead(true);
				}
				else {
					currScore += actionCost;
				}
				
				if (environment.getScream() == true) environment.setScream(false);
				
				lastAction = Action.GO_FORWARD;
			}
			else if (action == Action.TURN_RIGHT) {
				
				currScore += actionCost;
				agent.turnRight();		
				environment.placeAgent(agent);
				
				if (environment.getBump() == true) environment.setBump(false);
				if (environment.getScream() == true) environment.setScream(false);
				
				lastAction = Action.TURN_RIGHT;
			}
			else if (action == Action.TURN_LEFT) {
				
				currScore += actionCost;
				agent.turnLeft();		
				environment.placeAgent(agent);
				
				if (environment.getBump() == true) environment.setBump(false);
				if (environment.getScream() == true) environment.setScream(false);
				
				lastAction = Action.TURN_LEFT;
			}
			else if (action == Action.GRAB) {
				
				if (environment.grabGold() == true) {
					
					currScore += goldCost;
					simulationRunning = false;
					
					agent.setHasGold(true);
				}
				else currScore += actionCost;
				
				environment.placeAgent(agent);
				
				if (environment.getBump() == true) environment.setBump(false);
				if (environment.getScream() == true) environment.setScream(false);
				
				lastAction = Action.GRAB;
			}
			else if (action == Action.SHOOT) {
				
				currScore += shootCost;
				
				
				environment.placeAgent(agent);
				
				if (environment.getBump() == true) environment.setBump(false);
				
				lastAction = Action.SHOOT;
			}
			else if (action == Action.NO_OP) {
				
				environment.placeAgent(agent);
				
				if (environment.getBump() == true) environment.setBump(false);
				if (environment.getScream() == true) environment.setScream(false);
				
				lastAction = Action.NO_OP;
			}
			
		}
		catch (Exception e) {
			
			System.out.println("An exception was thrown: " + e);
		}
	}
	
	
	
}