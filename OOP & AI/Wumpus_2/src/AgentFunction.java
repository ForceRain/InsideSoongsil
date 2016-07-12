/*
 * Class that defines the agent function.
 * 
 * Written by James P. Biagioni (jbiagi1@uic.edu)
 * for CS511 Artificial Intelligence II
 * at The University of Illinois at Chicago
 * 
 * Last modified 2/19/07 
 * 
 * DISCLAIMER:
 * Elements of this application were borrowed from
 * the client-server implementation of the Wumpus
 * World Simulator written by Kruti Mehta at
 * The University of Texas at Arlington.
 * 
 */

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentLinkedQueue;

class AgentFunction {
	public static int GO_FORWARD = 1;
	public static int TURN_RIGHT = 2;
	public static int TURN_LEFT = 3;
	public static int GRAB = 4;
	public static int SHOOT = 5;
	public static int NO_OP = 6;
	public static int END_TRIAL = 7;			// added for action sequence.
	
	private String agentName = "Agent Smith";
	private HashMap<Position,Character> mapInfo;
	private boolean[][] visited;
	private int[] agentPos;
	private int[] goalPos;
	private int[][] direction;
	private int heuristicNumber;
	private int worldSize;
	private Queue<Integer> actionSequence = new LinkedList<Integer>();
	private PriorityQueue<Node> pQueue = new PriorityQueue<Node>(10000000,new nodeComparator());
	private boolean searchDone = false;
	private char agentHead;
	private String tmpNodeAS="";
	private int addCost=0;
	private int addi=0;
	
	class nodeComparator implements Comparator<Node>{				// comparator used in PriorityQueue.
		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub
			if (o1.sumCost < o2.sumCost)				// low evaluation cost, high priority.
				return -1;
			else if (o1.sumCost > o2.sumCost)
				return 1;
			else
				return 0;
		}
	}
	
	class Node{							// representing state space.
		private int posX;
		private int posY;
		private int cost;
		private double hCost;
		private double sumCost;
		private char befHead;
		private int reqAction;
		private String nodeActSeq;
		private int arrow;
		private int imX;
		private int imY;
		private int posCase;
		
		public Node (int pos1,int pos2,int cost,double heuristicVal,
				char bH,int rA,int arrNum,String seq,
				int ix,int iy,int pc){
			posX=pos1;posY=pos2;this.cost=cost;this.hCost=heuristicVal;
			reqAction=rA;befHead=bH;
			nodeActSeq=seq;
			arrow=arrNum;
			imX=ix;imY=iy;
			posCase=pc;
		}
		
		public void updateCost(){
			sumCost = cost+hCost;			// update value with heuristic.
		}
		public int nextCost(){
			int val=0;
			if ( befHead == 'N' ){
				if (reqAction == 0)			val=2;		// go right
				else if (reqAction == 1)	val=2;		// go left
				else if (reqAction == 2)	val=1;		// go up
				else					val=3;			// go down
			}
			else if (befHead == 'E'){
				if (reqAction == 0)			val=1;
				else if (reqAction == 1)	val=3;
				else if (reqAction == 2)	val=2;
				else					val=2;
			}
			else if (befHead == 'S'){
				if (reqAction == 0)			val=2;
				else if (reqAction == 1)	val=2;
				else if (reqAction == 2)	val=3;
				else				val=1;
			}
			else{
				if (reqAction == 0)			val=3;
				else if (reqAction == 1)	val=1;
				else if (reqAction == 2)	val=2;
				else				val=2;
			}
			return val;
		}
		
		public String toString(){
			return posX+","+posY+"::cost is :"+cost+", sum cost is :"+sumCost+"\n";
		}
		public void setImmuneToWumpus(){
			imX=posX;imY=posY;
			if ( befHead == 'N' ){
				posCase=1;	
			}else if (befHead == 'E'){
				posCase=2;
			}else if (befHead == 'W'){
				posCase=3;
			}else{
				posCase=4;
			}
		}
		public boolean isImmuneLocation(int x,int y){
			if ( (imX == -1) && (imY == -1 ))
				return false;
			if ( posCase == -1 )
				return false;
			if ( posCase == 1 ){
				if ( y != imY )	return false;
				if ( x >= imX )	return true;
				else return false;
			}else if ( posCase == 2 ){
				if ( x != imX )	return false;
				if ( y >= imY )	return true;
				else return false;
			}else if ( posCase == 3 ){
				if ( x != imX )	return false;
				if ( y <= imY )	return true;
				else return false;
			}else{
				if ( y != imY )	return false;
				if ( x <= imX )	return true;
				else return false;
			}
		}
	}
	
	public AgentFunction(){
		direction = new int[4][2];
		direction[0][0]=0;direction[0][1]=1;			// go right.
		direction[1][0]=0;direction[1][1]=-1;			// go left.
		direction[2][0]=1;direction[2][1]=0;			// go up.
		direction[3][0]=-1;direction[3][1]=0;			// go down.
	}
	
	public void init(int[] loc,int heuristic) {
		agentPos=loc;
		heuristicNumber=heuristic;
		goalPos=findGoal();
	}
	
	public void search() {
		if (goalPos[0]==-1 && goalPos[1]==-1){		// if there is no gold, terminate.
			actionSequence.add(END_TRIAL);
			return;
		}
	//	System.out.println(agentPos[0]+","+agentPos[1]);
	//	System.out.println(goalPos[0]+","+goalPos[1]);
		visited[agentPos[0]][agentPos[1]]=true;		// start point.
		pQueue.add(new Node(agentPos[0],agentPos[1],0,
				HeuristicFunction(agentPos[0],agentPos[1]),agentHead,-1,1,"",-1,-1,-1));
		AstarSearching();
	}
	
	public void AstarSearching(){		
		int []curPos = new int[2];
		String curNodeAS="";
		
		while (!pQueue.isEmpty()){
			Node explored=pQueue.remove();
			
			curNodeAS=explored.nodeActSeq;
			curPos[0]=explored.posX;
			curPos[1]=explored.posY;
						
			if ( GOAL_TEST(curPos) == true ){
				StringTokenizer strTkn=new StringTokenizer(curNodeAS);
				while (strTkn.hasMoreTokens()){
					int tkn=Integer.parseInt(strTkn.nextToken());
					actionSequence.add(tkn);
				}
				actionSequence.add(GRAB);
				actionSequence.add(END_TRIAL);
				searchDone = true;
				break;
			}
			else{
				if (searchDone == false){
					for (int i=0;(i<4) && (searchDone == false );i++){
						int newX=explored.posX+direction[i][0];
						int newY=explored.posY+direction[i][1];
						if ( mapInfo.containsKey(new Position(newX,newY)) == false )
							mapInfo.put(new Position(newX,newY),' ');
						if ( (newX)>=0 && (newY>=0) && (newX<worldSize) && (newY<worldSize)){
							if ( (visited[newX][newY] == false) && notDanger(newX,newY) ){
								visited[newX][newY] = true;
								tmpNodeAS=explored.nodeActSeq;
								char nHead=nextHead(explored.befHead,i);
								
								Node ans = new Node (newX,newY,explored.cost+addCost,
										HeuristicFunction(newX,newY),nHead,i,explored.arrow,tmpNodeAS
										,explored.imX,explored.imY,explored.posCase);
								ans.updateCost();		// update cost with heuristic.
								pQueue.add(ans);
							}
							// in case about killing Wumpus!!!
							if ( (visited[newX][newY] == false) && wumpusDetected(newX,newY) && (explored.arrow!=0) ){
								
								visited[newX][newY]=true;		// add it to explored set.
								tmpNodeAS=explored.nodeActSeq;
								char tmpHead=killWumps(explored.befHead,i);							
								char nHead=nextHead(tmpHead,i);
								Node ans = new Node (newX,newY,explored.cost+addCost+addi,
										HeuristicFunction(newX,newY),nHead,i,0,tmpNodeAS,
										explored.imX,explored.imY,explored.posCase);
								ans.setImmuneToWumpus();
								ans.updateCost();		// update cost.	
								pQueue.add(ans);
							}
							if ((visited[newX][newY]==false) && explored.isImmuneLocation(newX, newY)){
								visited[newX][newY]=true;		// add it to explored set.
								
								char nHead=nextHead(explored.befHead,i);
								Node ans = new Node (newX,newY,explored.cost+addCost+addi,
										HeuristicFunction(newX,newY),nHead,i,0,tmpNodeAS,
										explored.imX,explored.imY,explored.posCase);
							//	ans.setImmuneToWumpus();
								ans.updateCost();		// update cost.	
								pQueue.add(ans);
							}
						}
					}					
				}
			}
		}
	}
	
	public char killWumps(char befHead,int reqAction){
		int value=0;
		char curHeadPos=befHead;
		int towards=reqAction;
		addi=0;
		if (tmpNodeAS.length() != 0)
			tmpNodeAS=tmpNodeAS+" ";
		
		if ( curHeadPos == 'N' ){		// if agent head is now heading North,
			if (towards == 0){			// and requiring action is moving East,
				tmpNodeAS=tmpNodeAS+TURN_RIGHT+" ";
				befHead='E';
				value=1;
			}
			else if (towards == 1){			// and requiring action is moving West,
				tmpNodeAS=tmpNodeAS+TURN_LEFT+" ";
				befHead='W';
				value=1;
			}
			else if (towards == 2){		// and requiring action is moving North,
				befHead='N';
				value=0;
			}
			else{							// and requiring action is moving South,
				tmpNodeAS=tmpNodeAS+TURN_LEFT+" "+TURN_LEFT+" ";
				befHead='S';
				value=2;
			}
		}
		else if (curHeadPos == 'E'){	// if agent head is now heading East,
			if (towards == 0){		// and requiring action is moving East,
				befHead='E';
				value=0;
			}
			else if (towards == 1){		// and requiring action is moving West,
				tmpNodeAS=tmpNodeAS+TURN_LEFT+" "+TURN_LEFT+" ";
				befHead='W';
				value=2;
			}
			else if (towards == 2){		// and requiring action is moving North,
				tmpNodeAS=tmpNodeAS+TURN_LEFT+" ";
				befHead='N';
				value=1;
			}
			else{						// and requiring action is moving South,
				tmpNodeAS=tmpNodeAS+TURN_RIGHT+" ";
				befHead='S';
				value=1;
			}
		}
		else if (curHeadPos == 'S'){	// if agent head is now heading South,
			if (towards == 0){		// and requiring action is moving East,
				tmpNodeAS=tmpNodeAS+TURN_LEFT+" ";
				befHead='E';
				value=1;
			}
			else if (towards == 1){		// and requiring action is moving West,
				tmpNodeAS=tmpNodeAS+TURN_RIGHT+" ";
				befHead='W';
				value=1;
			}
			else if (towards == 2){			// and requiring action is moving North,
				tmpNodeAS=tmpNodeAS+TURN_LEFT+" "+TURN_LEFT+" ";
				befHead='N';
				value=2;
			}
			else{						// and requiring action is moving South,
				befHead='S';
				value=0;
			}
		}
		else{							// if agent head is now heading West,
			if (towards == 0){		// and requiring action is moving East,
				tmpNodeAS=tmpNodeAS+TURN_LEFT+" "+TURN_LEFT+" ";
				befHead='E';
				value=2;
			}
			else if (towards == 1){	// and requiring action is moving West,
				befHead='W';
				value=0;
			}
			else if (towards == 2){		// and requiring action is moving North,
				tmpNodeAS=tmpNodeAS+TURN_RIGHT+" ";
				befHead='N';
				value=1;
			}
			else{					// and requiring action is moving South,
				tmpNodeAS=tmpNodeAS+TURN_LEFT+" ";
				befHead='S';
				value=1;
			}
		}
		value+=2;			// add shoot cost.
		tmpNodeAS=tmpNodeAS+SHOOT;		// add shoot action to action sequence.
		addi=value;
		return befHead;
	}
	
	public char nextHead(char befHead,int reqAction){		
		char nextHd;
		addCost=0;
		if (tmpNodeAS.length()!=0)
			tmpNodeAS=tmpNodeAS+" ";
		if ( befHead == 'N' ){
			if (reqAction == 0){			// go right
				tmpNodeAS=tmpNodeAS+TURN_RIGHT+" "+GO_FORWARD;
				addCost+=2;
				nextHd='E';
			}
			else if (reqAction == 1){			// go left
				tmpNodeAS=tmpNodeAS+TURN_LEFT+" "+GO_FORWARD;
				addCost+=2;
				nextHd='W';
			}
			else if (reqAction == 2){		// go up
				tmpNodeAS=tmpNodeAS+GO_FORWARD;
				addCost+=1;
				nextHd='N';
			}
			else{							// go down
				tmpNodeAS=tmpNodeAS+TURN_RIGHT+" "+TURN_RIGHT+" "+GO_FORWARD;
				addCost+=3;
				nextHd='S';
			}
		}
		else if (befHead == 'E'){
			if (reqAction == 0){
				tmpNodeAS=tmpNodeAS+GO_FORWARD;
				addCost+=1;
				nextHd='E';
			}
			else if (reqAction == 1){
				tmpNodeAS=tmpNodeAS+TURN_RIGHT+" "+TURN_RIGHT+" "+GO_FORWARD;
				addCost+=3;
				nextHd='W';
			}
			else if (reqAction == 2){
				tmpNodeAS=tmpNodeAS+TURN_LEFT+" "+GO_FORWARD;
				addCost+=2;
				nextHd='N';
			}
			else{
				tmpNodeAS=tmpNodeAS+TURN_RIGHT+" "+GO_FORWARD;
				addCost+=2;
				nextHd='S';
			}
		}
		else if (befHead == 'S'){
			if (reqAction == 0){
				tmpNodeAS=tmpNodeAS+TURN_LEFT+" "+GO_FORWARD;
				addCost+=2;
				nextHd='E';
			}
			else if (reqAction == 1){
				tmpNodeAS=tmpNodeAS+TURN_RIGHT+" "+GO_FORWARD;
				addCost+=2;
				nextHd='W';
			}
			else if (reqAction == 2){
				tmpNodeAS=tmpNodeAS+TURN_RIGHT+" "+TURN_RIGHT+" "+GO_FORWARD;
				addCost+=3;
				nextHd='N';
			}
			else{
				tmpNodeAS=tmpNodeAS+GO_FORWARD;
				addCost+=1;
				nextHd='S';
			}
		}
		else{
			if (reqAction == 0){
				tmpNodeAS=tmpNodeAS+TURN_RIGHT+" "+TURN_RIGHT+" "+GO_FORWARD;
				addCost+=3;
				nextHd='E';
			}
			else if (reqAction == 1){
				tmpNodeAS=tmpNodeAS+GO_FORWARD;
				addCost+=1;
				nextHd='W';
			}
			else if (reqAction == 2){
				tmpNodeAS=tmpNodeAS+TURN_RIGHT+" "+GO_FORWARD;
				addCost+=2;
				nextHd='N';
			}
			else{
				tmpNodeAS=tmpNodeAS+TURN_LEFT+" "+GO_FORWARD;
				addCost+=2;
				nextHd='S';
			}
		}
		return nextHd;
	}
		
	public int killWumpus(char curHeadPos,int towards){
		int value=0;
		if ( curHeadPos == 'N' ){
			if (towards == 0){			// go right
				value=1;
			}
			else if (towards == 1){			// go left
				value=1;
			}
			else if (towards == 2){		// go up
				value=0;
			}
			else{							// go down
				value=2;
			}
		}
		else if (curHeadPos == 'E'){
			if (towards == 0){
				value=0;
			}
			else if (towards == 1){
				value=2;
			}
			else if (towards == 2){
				value=1;
			}
			else{
				value=1;
			}
		}
		else if (curHeadPos == 'S'){
			if (towards == 0){
				value=1;
			}
			else if (towards == 1){
				value=1;
			}
			else if (towards == 2){
				value=2;
			}
			else{
				value=0;
			}
		}
		else{
			if (towards == 0){
				value=2;
			}
			else if (towards == 1){
				value=0;
			}
			else if (towards == 2){
				value=1;
			}
			else{
				value=1;
			}
		}
		value+=2;			// shoot cost.			
		return value;
	}
	
	public boolean wumpusDetected(int posX,int posY){
		return (mapInfo.containsKey(new Position(posX,posY)) && 
				mapInfo.get(new Position(posX,posY)).toString().charAt(0)=='W');
	}
		
	public boolean notDanger(int X,int Y){
		return (mapInfo.containsKey(new Position(X,Y)) && 
				mapInfo.get(new Position(X,Y)).toString().charAt(0)!='P') 
				&& (mapInfo.get(new Position(X,Y)).toString().charAt(0)!='W');
	}
	
	public double HeuristicFunction(int pos1,int pos2){
		if (heuristicNumber == 1){
			return Math.abs(pos1-goalPos[0])+Math.abs(pos2-goalPos[1]);
		}
		else if (heuristicNumber == 2){
			int val1=(pos1-goalPos[0])*(pos1-goalPos[0]);
			int val2=(pos2-goalPos[1])*(pos2-goalPos[1]);
			return Math.sqrt(val1+val2);
		}
		else if (heuristicNumber == 3){
			return Math.max(Math.abs(pos1-goalPos[0]),Math.abs(pos2-goalPos[1]));
		}
		else if (heuristicNumber == 4){
			return Math.min(Math.abs(pos1-goalPos[0]),Math.abs(pos2-goalPos[1]));
		}
		else
			return 0;
	}
	
	public boolean GOAL_TEST(int[] pos){
		if ( mapInfo.containsKey(new Position(pos[0],pos[1])) &&
				mapInfo.get(new Position(pos[0],pos[1])) == 'G'){
			return true;
		}
		return false;
	}
	
	public void setAgentHead(char dir){
		agentHead=dir;
	}
	
	public void setMapSize(int sz) {
		worldSize=sz;
		visited = new boolean[sz+2][sz+2];
	}
	
	public int[] findGoal (){
		int []pos=new int[2];
	//	System.out.println(mapInfo);
		pos[0]=-1;pos[1]=-1;
		for (int i=0;i<worldSize;i++){
			for (int j=0;j<worldSize;j++){
		//		System.out.print(i+":"+j+mapInfo.get(new Position(i,j) ).toString().charAt(0)+",");
		//		System.out.println(mapInfo.containsKey(new Position(i,j)));
				if (mapInfo.containsKey(new Position(i,j)) &&
						(mapInfo.get(new Position(i,j) ).toString().charAt(0) == 'G') ){
					pos[0]=i;pos[1]=j;
				}
			}
		}
	//	System.out.println(pos[0]+","+pos[1]);
		return pos;
	}
	
	public int process()
	{	
		if ( !actionSequence.isEmpty() ){
			int value = actionSequence.remove();
	//		System.out.println(value);
			return value;			
		}
		return -1;
	}
	
	// public method to return the agent's name
	// do not remove this method
	public String getAgentName() {
		return agentName;
	}
	public void setMap(HashMap<Position,Character> parm){
		mapInfo=new HashMap<Position,Character>(parm);
	}
	
}