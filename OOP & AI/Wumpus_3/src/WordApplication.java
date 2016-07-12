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
	String query="";
	String KB="";
	String results="";
	private static ArrayList<String> queryList;				// query list.
	private static ArrayList<String> stringKB;				// knowledge base with String
	private static ArrayList<Sentence> knowledgeBase;		// knowledge base with Sentence
	private static ArrayList<String> entailResult;			// entailment result.
	private static char[][][] wumpusWorld;					// map data.
	int algo;
	int worldSize;

	public static void main (String args[]) throws Exception {
		WorldApplication wa=new WorldApplication();
		boolean nonDeterministicMode=false;
				
		if(wa.readPara(args)==6)
		{
			wa.readQueryFile(wa.query);
		    wumpusWorld = readWumpusWorld(wa.worldSize,wa.gameboard);
			wa.initKnowledgeBase();				// fill knowledge base with raw string information.
			wa.parseToStnce();				// parse raw string information into Sentence class.
			
		    // convert each proposition into clauses.
		    if ( wa.algo == 1 ){
		    	wa.resolutionALGO();
		    }
		    else
		    	wa.WALKSAT();
		    // execute query with resolution or WALKSAT		    
		    wa.writeResult();
		}
		else
		{
			wa.usage();
		}
	}
	
	/*	write entailment result into file.	*/
	private void writeResult(){
		try{
			FileOutputStream fwriter = new FileOutputStream(this.results);
			for (int i=0;i<entailResult.size();i++){
				fwriter.write(entailResult.get(i).getBytes());
			}
			
			fwriter.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/* resolution deriver */
	private void resolutionALGO(){
		int loopCnt=1;
		entailResult = new ArrayList<String>();
		for (String a : queryList){
			Sentence q = new Sentence(a);
			ArrayList<Sentence> ars = q.transformation();
			
			boolean result=true;
			for (int i=0;i<ars.size();i++){			
				result = result && PLResolution(ars.get(i));
			}
			System.out.println("Query "+loopCnt+" completed.");
			if (result)
				entailResult.add(new String(loopCnt+".yes\n"));
			else
				entailResult.add(new String(loopCnt+".no\n"));
			loopCnt++;
		}
	}
	
	/* resolution algorithm */
	private boolean PLResolution(Sentence p){
		ArrayList<Sentence> activeKB = (ArrayList<Sentence>)knowledgeBase.clone();
		ArrayList<Sentence> tCC = p.negationDivideClause();
		
		for (int i=0;i<tCC.size();i++){
			activeKB.add(tCC.get(i));
		}
	
		int befSz = activeKB.size();
		do{
			befSz = activeKB.size();
			for (int i=0;i<befSz;i++){
				for (int j=0;j<i;j++){
					if ( i != j ){
						boolean addToKB = true;
						Sentence candidate = PLResolve(activeKB.get(i),activeKB.get(j));
						
						if (candidate.isEmptySentence())
							return true;
						for (int k=0;k<befSz;k++){
							if ( candidate.equal(activeKB.get(k)) ){
								addToKB=false;
								break;
							}
						}
						if (addToKB){
							activeKB.add(candidate);
						}
					}
				}
			}
			System.out.println("bef : "+befSz+", after:"+activeKB.size());
		}
		while ( befSz != activeKB.size() );			// no empty clause.
		return false;
	}
	/*	resolve	*/
	private Sentence PLResolve(Sentence a, Sentence b){
		return a.fullResolution(b);
	}
	
	/* WALKSAT algorithm */
	private void WALKSAT(){
		entailResult = new ArrayList<String>();
		Random randGen = new Random();
		int maxFilp = 3000000;
		int loopCnt = 1;
		for (String a : queryList){
			int MFCounter = 0;
			boolean [][] Pit = new boolean[this.worldSize][this.worldSize];
			boolean [][] Breeze = new boolean[this.worldSize][this.worldSize];
			
			for (int i=0;i<this.worldSize;i++){
				for (int j=0;j<this.worldSize;j++){
					Pit[i][j] = true;
					Breeze[i][j] = true;
				}
			}				// randomly initialized.
			ArrayList<Sentence> activeKB = (ArrayList<Sentence>)knowledgeBase.clone();
			Sentence que = new Sentence( a );
			ArrayList<Sentence> add = que.transformation();
			for (int i=0;i<add.size();i++){
				activeKB.add(add.get(i));
			}
			for ( int i=0;i<activeKB.size();i++ ){
				if ( activeKB.get(i).sentenceLen() == 1 ){			/*	for facts.	*/
					Literal tmpa = ((Literal)activeKB.get(i).getSymbol(0));
					Integer[] arr=tmpa.getPosition();
					if ( tmpa.isNegative() ){
						if ( tmpa.getKind()=='B' )
							Breeze[arr[0]][arr[1]]=false;
						else
							Pit[arr[0]][arr[1]]=false;
					}
					else{
						if ( tmpa.getKind()=='B' )
							Breeze[arr[0]][arr[1]]=true;
						else
							Pit[arr[0]][arr[1]]=true;
					}
				}
				else{				/*	for not only one literal.	*/
					int loop = activeKB.get(i).sentenceLen();
					int rx=0,ry=0;
					char k='B';
					boolean allFalse = true;
					for ( int j=0;j<loop;j++ ){
						Symbol tmpb = activeKB.get(i).getSymbol(j);
						if (tmpb.whoRU().equals("Literal")){
							Literal tmpa = ((Literal)tmpb);
							Integer[] ar = tmpa.getPosition();
							rx=ar[0];ry=ar[1];k=tmpa.getKind();
							if ( !tmpa.isNegative() )
								allFalse = false;
						}
					}
					if (allFalse){
						if (k=='B')
							Breeze[rx][ry]=false;
						else
							Pit[rx][ry]=false;
					}
				}
			}
			while ( MFCounter < maxFilp ){
				boolean result = true;
				int correctionPos = 0;
				for ( int i=0;i<activeKB.size();i++ ){
					result = result && activeKB.get(i).getCompositionValue(Pit,Breeze);		
					if ( !result )	{correctionPos = i; break;}	
				}
				if ( result )	break;
				// replacement needed.
				if ( (randGen.nextInt()%2) !=0 ){		// select one symbol randomly, flip it.
					while ( true ){
						int getPos = randGen.nextInt(activeKB.get(correctionPos).sentenceLen());
						if ( activeKB.get(correctionPos).getSymbol(getPos).whoRU().equals("Literal") ){
							Literal check = ((Literal)activeKB.get(correctionPos).getSymbol(getPos));
							Integer[] pp = check.getPosition();
							char kind = check.getKind();
							if (kind == 'B'){
								if (Breeze[pp[0]][pp[1]])
									Breeze[pp[0]][pp[1]] = false;
								else
									Breeze[pp[0]][pp[1]] = true;
							}
							else{
								if (Pit[pp[0]][pp[1]])
									Pit[pp[0]][pp[1]] = false;
								else
									Pit[pp[0]][pp[1]] = true;
							}
							break;
						}
					}
				}
				else{						// select one symbol and flip it with maximize satisfying clause.
					int [][] Bchecking = new int[this.worldSize][this.worldSize];
					int [][] Pchecking = new int[this.worldSize][this.worldSize];
					int maxX=0,maxY=0,maxVal=-1;
					char maxKind='B';
					for (int i=0;i<this.worldSize;i++)	for (int j=0;j<this.worldSize;j++)	{Bchecking[i][j]=0;	Pchecking[i][j]=0;	}
					for ( int j=0;j<activeKB.size();j++ ){
						Sentence tmpb = activeKB.get(j);
						for (int k=0;k<tmpb.sentenceLen();k++){
							Symbol tmps= tmpb.getSymbol(k);
							if (tmps.whoRU().equals("Literal")){
								Integer[] tPos = ((Literal)tmps).getPosition();
								if (tmps.isNegative()){
									if (tmps.getKind()=='B')
										Bchecking[tPos[0]][tPos[1]]--;
									else
										Pchecking[tPos[0]][tPos[1]]--;
								}									
								else{
									if (tmps.getKind()=='B')
										Bchecking[tPos[0]][tPos[1]]++;
									else
										Pchecking[tPos[0]][tPos[1]]++;
								}
								if ( maxVal < Bchecking[tPos[0]][tPos[1]] ){
									maxX = tPos[0];maxY = tPos[1];maxVal=Bchecking[tPos[0]][tPos[1]];maxKind='B';
								}
								if ( maxVal < Pchecking[tPos[0]][tPos[1]] ){
									maxX = tPos[0];maxY = tPos[1];maxVal=Pchecking[tPos[0]][tPos[1]];maxKind='P';
								}
							}
						}
					}
					if (maxKind=='B'){
						if (Breeze[maxX][maxY])
							Breeze[maxX][maxY] = false;
						else
							Breeze[maxX][maxY] = true;
					}
					else{
						if (Pit[maxX][maxY])
							Pit[maxX][maxY] = false;
						else
							Pit[maxX][maxY] = true;
					}
				}
				MFCounter++;
			}
			System.out.println("Query "+(loopCnt)+" completed.");
			if ( MFCounter == maxFilp )
				entailResult.add(loopCnt+".no\n");
			else
				entailResult.add(loopCnt+".yes\n");
			loopCnt++;
		}
	}
	
	private void usage() {
		System.out.println("Usage:\n\n-i map eg gameboard.txt");
		System.out.println("-o1 the file that writes results of KB based on map. eg KB.txt");
		System.out.println("-o2 the file that writes results of queries. eg.results.txt");
		System.out.println("-q the inut file of queries. eg query.txt");
		System.out.println("-ws worldsize eg. 4");
		System.out.println("-h algorithms 1 for resolution and 2 for walksat");
	}
	
	/*	initialize knowledge base.	*/
	private void initKnowledgeBase(){
		stringKB = new ArrayList<String>();
		ArrayList<String> outputBase = new ArrayList<String>();
		
		for (int i=0;i<this.worldSize;i++){
			for (int j=0;j<this.worldSize;j++){
				if ( this.wumpusWorld[i][j][0] == 'B' ){
					outputBase.add("B"+i+","+j);				// arrayList for KB
					outputBase.add("~P"+i+","+j);
					stringKB.add("B"+i+","+j);			// base information (:= fact)
					stringKB.add("~P"+i+","+j);			// if breeze, it guarantee there is no pit.
					
					String additional = "B"+i+","+j+"<=>(";
					String addiKB = "(";
					boolean addChk=false;
					if ( (i-1) >=0 ){
						addChk=true;
						additional = additional + "P"+(i-1)+","+j+"V";
						addiKB = addiKB + "P"+(i-1)+","+j+"V" ;
					}
						
					if ( (j-1) >=0 ){
						addChk=true;
						additional = additional + "P"+i+","+(j-1)+"V";
						addiKB = addiKB + "P"+i+","+(j-1)+"V";
					}
						
					if ( (i+1) <this.worldSize ){
						addChk=true;
						additional = additional + "P"+(i+1)+","+j+"V";
						addiKB = addiKB + "P"+(i+1)+","+j+"V";
					}
						
					if ( (j+1) <this.worldSize ){
						addChk=true;
						additional = additional + "P"+i+","+(j+1)+"V";		// biconditional information.
						addiKB = addiKB + "P"+i+","+(j+1)+"V";
					}					
					if (addChk){
						additional = additional.substring(0, additional.length()-1);
						addiKB = addiKB.substring(0, addiKB.length()-1);
					}
					additional = additional + ")";
					addiKB = addiKB + ")";
					
					if (addChk){
						outputBase.add(additional);
						stringKB.add("~B"+i+","+j+"V"+addiKB);
				//		stringKB.add("~"+addiKB+"V"+"B"+i+","+j);
					}	
				}
				else{			/*		*/					
					String additional = "P"+i+","+j+"<=>(";
					String addiKB = "(";
					boolean addChk=false;
					if ( (i-1) >=0 ){
						addChk=true;
						additional = additional + "B"+(i-1)+","+j+"^";
						addiKB = addiKB + "B"+(i-1)+","+j+"^" ;
					}
						
					if ( (j-1) >=0 ){
						addChk=true;
						additional = additional + "B"+i+","+(j-1)+"^";
						addiKB = addiKB + "B"+i+","+(j-1)+"^";
					}
						
					if ( (i+1) <this.worldSize ){
						addChk=true;
						additional = additional + "B"+(i+1)+","+j+"^";
						addiKB = addiKB + "B"+(i+1)+","+j+"^";
					}
						
					if ( (j+1) <this.worldSize ){
						addChk=true;
						additional = additional + "B"+i+","+(j+1)+"^";		// biconditional information.
						addiKB = addiKB + "B"+i+","+(j+1)+"^";
					}					
					if (addChk){
						additional = additional.substring(0, additional.length()-1);
						addiKB = addiKB.substring(0, addiKB.length()-1);
					}
					additional = additional + ")";
					addiKB = addiKB + ")";
					if (addChk){
						outputBase.add(additional);
						stringKB.add("~"+addiKB+"V"+"P"+i+","+j);
					}
				}
			}
		}
		try{		/*	write to KB File.	*/
			FileWriter kbOut=new FileWriter(this.KB);
			int cnt = 1;
			for ( String a : outputBase ){
				a = (cnt++) + "." + a + "\n";
				kbOut.write(a);
			}
			kbOut.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	/*	parse and add to KB.	*/
	private void parseToStnce(){
		knowledgeBase = new ArrayList<Sentence>();
		for (String a : stringKB){
			Sentence stn = new Sentence(a);
			stn.KBInitCorrection();
			knowledgeBase.add(stn);
		}
				
		for (int k=0;k<knowledgeBase.size();k++){
			ArrayList<Sentence> res = knowledgeBase.get(k).KBClauseCorrection();
			
			if (res.size()!=0){					// delete not good one.
				knowledgeBase.remove(k);
				int sz = res.size();
				
				for ( int i=0;i<sz;i++ ){		// add good one.
					knowledgeBase.add(k,res.get(i));
				}
				if ( k!=0 )
					k--;
			}
		}
		System.out.println("Knowledge Base initializing completed.");
	}
	/*	read paramter from argument.	*/
	private int readPara(String args[]) {
		int n=0;
		for(int i=0;i<args.length;i++)
		{
			if(args[i].equals("-i"))
			{	this.gameboard=args[i+1]; n++;}
			else if(args[i].equals("-q"))
			{	this.query=args[i+1]; n++;}
			else if(args[i].equals("-ws"))
			{	this.worldSize=Integer.parseInt(args[i+1]); n++;}
			else if(args[i].equals("-h"))
			{	this.algo=Integer.parseInt(args[i+1]); n++;}
			else if(args[i].equals("-o1"))
			{	this.KB=args[i+1]; n++;}
			else if(args[i].equals("-o2"))
			{	this.results=args[i+1]; n++;}
		}
		return n;
	}
	/*	read query from file.	*/
	public void readQueryFile(String fileName){
		queryList = new ArrayList<String>();
		try{
			File f = new File(fileName);
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()){
				String l = sc.nextLine();
				if (l.length()!=0){
					for (int i=0;i<l.length();i++){
						if (l.charAt(i)=='.'){
							l = l.substring(i+1);
						}
					}
					queryList.add(l);
				}
			}
			sc.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/*	read map from file.	*/
	public static char[][][] readWumpusWorld(int size, String gameboard) throws Exception {
		char[][][] newWorld = new char[size][size][4];
		char[][] readWorld = new char[size*3+1][size*6+1];
		File file=new File(gameboard);
		Scanner f_read=new Scanner(file);
		//4 P,W,G,A
		int h_size=size*3+1;
		int w_size=size*6+1;
		try{
			for (int i=0;i<h_size;i++){
				String tmp = f_read.nextLine();
				if (tmp.length()==0)
					i--;
				else
					readWorld[i]=tmp.toCharArray();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		f_read.close();
		for (int i=1,h_ptr=1;i<h_size;i+=3,h_ptr++){
			for (int j=1,w_ptr=0;j<w_size;j+=6,w_ptr++){
				newWorld[size-h_ptr][w_ptr][0]=readWorld[i][j+1];	// Breeze.
			}
		}		
		return newWorld;
	}	
}
/* Sentence */
class Sentence{
	private ArrayList<Symbol> symContainer;
	public Sentence(String input){
		symContainer = new ArrayList<Symbol>();
		int len = input.length();
		for (int i=0;i<len;i++){
			if ( 	( (i-1) >= 0 )
					&& Character.isDigit(input.charAt(i-1)) 
					&& ( input.charAt(i)==',' )
					&& ( (i+1) < len )
					&& Character.isDigit(input.charAt(i+1)) ){		// symbol
				String sbStr = input.substring(i-2, i+2);
				Literal lit = new Literal(sbStr);				// positive literal.
				if ( ( (i-3) >= 0 ) && (input.charAt(i-3) == '~') )		// negative literal.
					lit.negation();
				symContainer.add(lit);
			}
			else if ( input.charAt(i) == '(' || input.charAt(i) == ')' ){		//parenthesis.
				if ( input.charAt(i)=='(' ){
					Operand opr = new Operand("(");
					symContainer.add(opr);
				}
				else{
					Operand opr = new Operand(")");
					symContainer.add(opr);
				}
			}
			else if ( (input.charAt(i) == '~') 
						&& ( (i+1) < len ) 
						&&	(input.charAt(i+1)) =='(' ){		// negation operand only for parenthesis.
				Operand opr = new Operand("~");
				symContainer.add(opr);
			}
			else if ( ( i-1 >= 0)
					&& input.charAt(i-1) == '<'
					&& input.charAt(i) == '=' 
					&& (i+1) < len
					&& input.charAt(i+1) == '>'){				// bidirectional operand.
				Operand opr = new Operand(input.substring(i-1,i+2));
				symContainer.add(opr);
			}
			else if ( (i-1) >= 0							// implication operand.
					&& input.charAt(i-1) == '='
					&& input.charAt(i) == '>'
					){
				if ( (i-2) >=0  ){
					if (input.charAt(i-2) == '<')
						continue;
				}
				Operand opr = new Operand(input.substring(i-1,i+1));
				symContainer.add(opr);
			}
			else if ( input.charAt(i) == 'V' ){				// disjunction operand.
				Operand opr = new Operand("V");
				symContainer.add(opr);
			}
			else if ( input.charAt(i) == '^' ){				// conjunction operand.
				Operand opr = new Operand("^");
				symContainer.add(opr);
			}
		}
	}
	public void KBInitCorrection(){				// addressing negation symbol only for knowledge base.
		int sz = symContainer.size();
		for ( int i=0;i<sz;i++ ){
			if ( symContainer.get(i).toString().equals("~") 
					&& ((i+1) < sz) 
					&& symContainer.get(i+1).toString().equals("(")){			// if it was negation symbol,
				symContainer.remove(i);
				sz--;
				for ( ; !symContainer.get(i).toString().equals(")") ; i++ ){
					symContainer.set(i, symContainer.get(i).negation());
				}
			}
		}
	}
	public void negatePos(int pos){			/* negate specified position. */
		symContainer.set(pos, symContainer.get(pos).negation());
	}
	public void allNegation(){			/* negate all position. */
		for (int i=0;i<symContainer.size();i++){
			symContainer.set(i,symContainer.get(i).negation());
		}
	}
	public ArrayList<Sentence> negationDivideClause(){		/* negation with division. */
		ArrayList<Sentence> aryCon = new ArrayList<Sentence>();
		this.allNegation();
		String tmp = "";
		for (int i=0;i<symContainer.size();i++){
			if ( symContainer.get(i).toString().equals("^") ){
				// applicate distribution rule.
				Sentence temp = new Sentence(tmp);
				aryCon.add(temp);
				tmp = "";
			}
			else{
				tmp = tmp + symContainer.get(i).toString();
				if ( i == (symContainer.size()-1) ){
					Sentence temp = new Sentence(tmp);
					aryCon.add(temp);
				}
			}
		}
		if (aryCon.size()==0)
			aryCon.add(this);
		return aryCon;
	}
	
	public ArrayList<Sentence> transformation(){
		ArrayList<Sentence> tCon = new ArrayList<Sentence>();
		int sz = symContainer.size();
		int lp=0,rp=sz;
		
		for (int i=0;i<sz;i++){
			if (symContainer.get(i).toString().equals("<=>")){
				lp=0;rp=i-1;
				if ((i+2 < sz ) && symContainer.get(i+1).toString().equals("("))
					{	lp = i+2;}
				if ((i-2 >= 0 ) && symContainer.get(i-1).toString().equals(")"))
					{	rp = i-2;}
				
				String st1="(",st2=")";
				for (int j=lp; j<sz && !symContainer.get(j).toString().equals(")") ; j++){
					st1 = st1 + symContainer.get(j).toString();
				}
				for (int j=rp; j>=0 && !symContainer.get(j).toString().equals("("); j--){
					st2 = symContainer.get(j).toString() + st2;
				}
				st1 = st1+ ")";
				st2 = "(" + st2;
				Sentence ob1 = new Sentence(st1+"=>"+st2);
				Sentence ob2 = new Sentence(st2+"=>"+st1);
				ArrayList<Sentence> t1 = ob1.transformation();	// ->
				ArrayList<Sentence> t2 = ob2.transformation();	// <-
				
				for (int k=0;k<t1.size();k++)
					tCon.add(t1.get(k));
				
				for (int k=0;k<t2.size();k++)
					tCon.add(t2.get(k));
			}
		}
		lp=0;rp=sz;
		for (int i=0;i<sz;i++){
			if (symContainer.get(i).toString().equals("=>")){
										
				for (int j=lp;j<i;j++)
					symContainer.set(j,symContainer.get(j).negation());
				
				symContainer.remove(i);
				symContainer.add(i, new Operand("V"));
			}			// if is was arrow, do negation.
			if (symContainer.get(i).toString().equals("("))		// find leftmost parenthesis.
				lp=i;
		}		
		
		// check whether parenthesis could be omitted.
		boolean allDis=true;
		for (int i=0;i<symContainer.size();i++){
			if ( symContainer.get(i).whoRU().equals("Operand") 
					&& (!symContainer.get(i).toString().equals("V")) )
				allDis = false;
		}
		if (allDis){
			for (int i=symContainer.size()-1;i>=0;i--){
				if ( symContainer.get(i).toString().equals(")")
						|| symContainer.get(i).toString().equals("("))
					symContainer.remove(i);
			}
		}

		if (tCon.size() == 0)
			tCon.add(this);
		return tCon;
	}
	public boolean getCompositionValue(boolean [][]Pit, boolean [][]Breeze){
		boolean [] valStack = new boolean[symContainer.size()];
		String [] oprndStack = new String[symContainer.size()];
		int valStkPtr = 0, opStkPtr = 0;
		for ( int i=0;i<symContainer.size();i++ ){
			if ( symContainer.get(i).toString().equals("(") ){
				oprndStack[opStkPtr++]=symContainer.get(i).toString();
			}
			else if ( symContainer.get(i).toString().equals(")") ){
				while ( (opStkPtr>0)&& (valStkPtr>0) && (!oprndStack[opStkPtr].toString().equals("(") ) ){
					opStkPtr--;
					valStack[valStkPtr-1] = calBoolean(valStack[valStkPtr],
															valStack[valStkPtr-1],
															oprndStack[opStkPtr].toString());
					valStkPtr--;
				}
			}
			else{
				Symbol check = symContainer.get(i);
				if ( check.whoRU().equals("Literal") ){
					if (check.getKind() =='B'){
						Integer[] pos = ((Literal)check).getPosition();
						if (check.isNegative())
							valStack[valStkPtr++] = !Breeze[pos[0]][pos[1]];
						else
							valStack[valStkPtr++] = Breeze[pos[0]][pos[1]];
					}
					else{
						Integer[] pos = ((Literal)check).getPosition();
						if (check.isNegative())
							valStack[valStkPtr++] = !Pit[pos[0]][pos[1]];
						else
							valStack[valStkPtr++] = Pit[pos[0]][pos[1]];
					}
				}
				else
					oprndStack[opStkPtr++] = check.toString();
			}
		}
		if ( valStkPtr !=0 && opStkPtr!=0){
			while ( (opStkPtr>0)&& (valStkPtr>0) ){
				opStkPtr--;
				valStack[valStkPtr-1] = calBoolean(valStack[valStkPtr],
														valStack[valStkPtr-1],
														oprndStack[opStkPtr].toString());
				valStkPtr--;
			}
		}
		return valStack[0];
	}
	
	public boolean calBoolean( boolean a, boolean b, String sym ){
		if (sym.equals("^"))
			return a&&b;
		else
			return a||b;
	}
	
	public int sentenceLen(){
		return symContainer.size();
	}
	
	public Symbol getSymbol(int pos){
		if ( pos >= symContainer.size() )
			return null;
		else
			return symContainer.get(pos);
	}
	
	public ArrayList<Sentence> KBClauseCorrection(){	// transformation into clauses only for knowledge base.
		int sz = symContainer.size();
		int rp=-1,lp=-1;
		int dis=0;
		ArrayList<Sentence> tmpContainer = new ArrayList<Sentence>();
		
		for ( int i=0;i<sz;i++ ){
			if ( symContainer.get(i).toString().equals("(") ){
				lp = i;
			}
			else if ( symContainer.get(i).toString().equals(")") ){
				rp = i;
				if ( dis == 0 ){					// all disjunction == useless parenthesis.
					symContainer.remove(rp);
					symContainer.remove(lp);
					String temp="";
					for ( Symbol e : symContainer )
						temp += e.toString();
					tmpContainer.add(new Sentence(temp));
					sz-=2;
				}
				else{
					if ( lp == 0 ){			// there exists one symbol after rightmost parenthesis.
						Symbol tgt = symContainer.get(rp+2);
						for ( int j = lp ; j < rp; j++ ){
							if ( symContainer.get(j).whoRU().equals("Literal") ){
								String tmp = tgt.toString()+"V"+symContainer.get(j).toString(); 
								tmpContainer.add(new Sentence(tmp));
							}
						}
					}
					else{					// there exists one symbol before leftmost parenthesis.
						Symbol tgt = symContainer.get(lp-2);
						for ( int j = lp ; j < rp; j++ ){
							if ( symContainer.get(j).whoRU().equals("Literal") ){
								String tmp = tgt.toString()+"V"+symContainer.get(j).toString(); 
								tmpContainer.add(new Sentence(tmp));
							}
						}
					}
				}
				lp = -1;
				dis = 0;
			}
			else if ( lp!=-1 && symContainer.get(i).toString().equals("^") ){
				dis=-1;
			}
		}
		return tmpContainer;
	}
		
	public Sentence fullResolution(Sentence oppo){
		ArrayList<Symbol> home = new ArrayList<Symbol>();
		ArrayList<Symbol> opponent = new ArrayList<Symbol>();
		for (int j=0;j<this.symContainer.size();j++)	home.add(symContainer.get(j));
		for (int k=0;k<oppo.symContainer.size();k++)	opponent.add(oppo.symContainer.get(k));
		
		boolean brkLoop=false;
		for (int i=0;i<home.size() && !brkLoop;i++){
			for (int j=0;j<opponent.size() && !brkLoop ;j++){
				Symbol t1 = home.get(i);
				Symbol t2 = opponent.get(j);
				if ( t1.whoRU().equals("Literal") && t2.whoRU().equals("Literal") ){
					if ( t1.symOnly().equals(t2.symOnly()) 	// same literal && different sign
						&& (t1.isNegative()^t2.isNegative())){
						if ( i == 0 ){
							if ( i+1 < home.size() )
								home.remove(i+1);
							home.remove(i);
						}
						else{
							home.remove(i);
							home.remove(i-1);
						}
						if ( j == 0 ){
							if ( j+1 < opponent.size() )
								opponent.remove(j+1);
							opponent.remove(j);
						}
						else{
							opponent.remove(j);
							opponent.remove(j-1);
						}
						brkLoop=true;
					}
				}
			}
		}
		String output = "";
		ArrayList<String> symbolCheck = new ArrayList<String>();
				
		for (int i=0;i<home.size();i++){
			boolean okay = true;
			for (int j=0;j<symbolCheck.size();j++){
				if ( symbolCheck.get(j).equals(home.get(i).toString()) )
					okay = false;
			}
			if (okay && home.get(i).whoRU().equals("Literal") )
				symbolCheck.add(home.get(i).toString());
		}
		for (int i=0;i<opponent.size();i++){
			boolean okay = true;
			for (int j=0;j<symbolCheck.size();j++){
				if ( symbolCheck.get(j).equals(opponent.get(i).toString()) )
					okay = false;
			}
			if (okay && opponent.get(i).whoRU().equals("Literal"))
				symbolCheck.add(opponent.get(i).toString());
		}
		
		for (int i=0;i<symbolCheck.size();i++){
			if (i==0)
				output = output + symbolCheck.get(i);
			else
				output = output + "V" + symbolCheck.get(i);
			
		}
		
		if (brkLoop){
			return new Sentence(output);
		}
		else
			return this;
	}
	
	public String toString(){
		String output="";
		for ( Symbol a : symContainer ){
			output = output + a.toString();
		}
		return output;
	}
	
	/*	equality checking.	*/
	public boolean equal(Sentence candidate){		
		
		if ( this.symContainer.size() != candidate.symContainer.size() )
			return false;
		else
		{
			int sz = symContainer.size();
			int cnt = 0;
			for ( int i=0;i<sz;i++ ){
				for ( int j=0;j<sz;j++ ){
					if ( symContainer.get(i).toString().equals( candidate.symContainer.get(j).toString() ) ){
						cnt++;
						break;
					}
				}
			}	
			return cnt == symContainer.size();
		}
	}	
	/* emptyness checking. */
	public boolean isEmptySentence(){
		return symContainer.size() == 0;
	}
}

interface Symbol{			/* interface. */
	public Symbol negation();
	public String whoRU();
	public boolean isNegative();
	public String symOnly();
	public char getKind();
}
/*	Literal	*/
class Literal implements Symbol{
	private boolean isPositive;
	private String litrl;
	private Integer[] litPos;
	private char kind;
	public Literal(String lit){
		litrl = lit;
		isPositive = true;			// default == positive literal.
		int divPos = 0;
		for (int i=0;i<litrl.length();i++)	if (litrl.charAt(i)==',')	{
			divPos = i; break;
		}
		kind = litrl.charAt(0);
		litPos = new Integer[2];		// x,y
		litPos[0] = Integer.parseInt(litrl.substring(1,divPos));
		litPos[1] = Integer.parseInt(litrl.substring(divPos+1));
	}
	public char getKind(){
		return this.kind;
	}
	public Integer[] getPosition(){
		return litPos;
	}
	public Symbol negation(){
		isPositive = isPositive ^ true;
		return this;
	}
	public boolean isNegative(){
		return !isPositive;
	}
	public String toString(){
		String out = this.litrl;
		if (!isPositive)
			out = "~" + out;
		return out;
	}
	@Override
	public String whoRU() {
		// TODO Auto-generated method stub
		return "Literal";
	}
	@Override
	public String symOnly() {
		// TODO Auto-generated method stub
		return this.litrl;
	}
}
/*	Operand	*/
class Operand implements Symbol{
	private String oprnd;
	public Operand(String op){
		this.oprnd = op;
	}
	public Symbol negation(){
		if (oprnd.equals("V"))
			oprnd = "^";
		else if (oprnd.equals("^"))
			oprnd = "V";
		return this;
	}
	public String toString(){
		return oprnd;
	}
	public boolean isNegative(){
		return false;
	}
	@Override
	public String whoRU() {
		// TODO Auto-generated method stub
		if ( oprnd.equals("(") || oprnd.equals(")") )
			return "Parenthesis";
		else
			return "Operand";
	}
	@Override
	public String symOnly() {
		// TODO Auto-generated method stub
		return oprnd;
	}
	@Override
	public char getKind() {
		// TODO Auto-generated method stub
		return 0;
	}
}