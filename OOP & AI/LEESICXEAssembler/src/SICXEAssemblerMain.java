import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class SICXEAssemblerMain {
	private static final int MAX_OPERAND=3;
	
	private static ArrayList<Instruction> inst=new ArrayList<Instruction>();		// 파일로부터 받은 명령어를 나눠 넣을 OPTAB을 ArrayList로 구현.
	private static int instIndex=0;								// OPTAB의 길이를 담고 있는 변수.
	
	private static ArrayList<String> inputData=new ArrayList<String>();		// 파일로부터 입력받은 source code를 담을 ArrayList.
	private static int lineNum=0;								// source code의 최종 길이를 담은 변수.
	private static int curLineNum=0;							// 전체 파일 기준으로 한 줄씩을 가리키는 포인터.
	
	private static ArrayList<Token> tokenTable=new ArrayList<Token>();		// source code를 각 위치별로 나누어서 담아둘 tokenTable을 정의.
	private static int tokenLine=0;							// tokenTable의 길이를 담고 있는 변수.
	
	private static ArrayList< ArrayList<Symbol> > symbolTable=new ArrayList< ArrayList<Symbol>>();		// symbol을 담아둘 SYMTAB을 정의.
	private static ArrayList<Integer> labelNum=new ArrayList<Integer>();				// 각 프로그램 별로 symbol의 개수를 관리하기 위한 ArrayList정의.
	
	private static ArrayList< ArrayList<Literal> > literalTable=new ArrayList< ArrayList<Literal>>();	// literal을 담아둘 LITTAB을 정의.
	private static ArrayList<Integer> literalNum=new ArrayList<Integer>();			// 각 프로그램 별로 literal의 개수를 관리하기 위한 ArrayList정의.
	private static int literalPtr=-1;
	
	private static ArrayList< ArrayList<String> > xrefTable=new ArrayList< ArrayList<String>>();		// 외부  참조하는 symbol들을 담아두는 table정의.
	private static ArrayList<Integer> xrefNum=new ArrayList<Integer>();				// 각 프로그램 별로 외부 참조 개수를 담아줄 ArrayList정의.
	private static ArrayList< ArrayList<String> > xdefTable=new ArrayList< ArrayList<String>>();		// 외부 정의를 하는 symbol들을 담아두는 table정의.
	private static ArrayList<Integer> xdefNum=new ArrayList<Integer>();				// 각 프로그램 별로 외부 정의 개수를 담아둘 ArrayList정의.
	
	private static ArrayList< ArrayList<Modification>> modificationTable=new ArrayList< ArrayList<Modification>>();		// 4형식이나, 외부 참조를 하는 Operand들에 대해서 modification이 필요한 symbol들을 담아둠.
	private static ArrayList<Integer> modiNum=new ArrayList<Integer>();				// 각 프로그램 별로 modification record의 개수를 담아둘 ArrayList정의.
	
	private static ArrayList<Integer> programLength=new ArrayList<Integer>();		// 각 프로그램 마다 길이가 얼마인지를 저장하는 ArrayList.
	private static boolean lengthChecked=false;						// 각 control section이 끝나거나, 프로그램의 마지막에 다다랐을 때, 프로그램 길이를 저장했는지 확인하는 플래그.
	private static int programNumber=0;								// 프로그램 번호를 알려주는 변수.
	private static int locationCounter=0;							// location counter 역할을 하는 변수.
	private static boolean subProgramStart=false;					// main routine이 아닌, sub routine 프로그램이 시작될 때, 그 프로그램 이름을 token table에 담는 역할을 하게 하는 플래그.
	private static boolean EQUCheck=false;						// label을 할당하는 부분에서, EQU directive를 만나면 location counter를 할당하지 않게 하는 플래그.
	private static boolean pcOrBase=true;						// true=pc-relative addressing, false=base-relative addressing을 구별하는 플래그.
	
	private static Symbol baseRegister=new Symbol();			// base-relative addressing을 할 경우에 사용되는 base register를 생성.
		
	private static final int A=0,X=1,L=2,B=3,S=4,T=5,F=6,PC=8,SW=9;		// SIC/XE machine에 존재하는 register를 표현. 후에 2형식 addressing을 할 때 사용된다.
		
	
	/*
	 * void main()
	 * : assembler 초기화, pass1, pass2 진행 후 outputfile을 생성하는 것을 담당하는 함수이다.
	 * 
	 * */
	public static void main(String[] args)
	{
		if (initMyAssembler()<0)				// assembler 초기화.
		{
			System.out.println("initMyAssembler : 프로그램 초기화에 실패 했습니다.");
			return;
		}
		if (assemPass1()<0)						// pass1 진행.
		{
			System.out.println("assemPass1() : 프로그램 초기화에 실패 했습니다.");
			return;
		}
		
		if (assemPass2()<0)						// pass2 진행.
		{
			System.out.println("assemPass2() : 프로그램 초기화에 실패 했습니다.");
			return;
		}
		
		makeOutput("output.txt");				// intermediate file, object code file 생성.
	}
	
	/*
	 * initMyAssembler()
	 * : 파일에 있는 OPTAB내용과, source code의 내용을 프로그램에 불러와서 각각의 ArrayList에 저장하는 함수이다.
	 * : 정상종료시 0 반환, 비정상 종료시 -1반환.
	 * 
	 */
	public static int initMyAssembler()
	{
		int result;
		
		if ((result=initInstFile("inst.data.txt"))<0)			// OPTAB 초기화.
			return -1;
		if ((result=initInputFile("input.txt"))<0)				// source code 초기화.
			return -1;
		return result;
	}
	
	/*
	 * initInstFile()
	 * : 매개로 파일 이름을 받는다. 그 파일에서 OPTAB의 내용을 프로그램의 ArrayList에 저장하는 함수이다.
	 * : 정상종료시 0 반환, 비정상종료시 -1반환.
	 * 
	 */
	public static int initInstFile(String filename)
	{
		File file=new File(filename);
		String iN,iF,op,oN,com;
		try
		{
			Scanner fScanner=new Scanner(file);
			while (fScanner.hasNext())
			{
				iN=fScanner.next();								// 띄어쓰기,탭 단위로 파일이 쓰여 있다고 가정할 때의 과정이다.
				iF=fScanner.next();
				op=fScanner.next();
				oN=fScanner.next();
				com=fScanner.next();
				Instruction tmp=new Instruction(iN,iF,op,oN,com);
				inst.add(tmp);
				instIndex++;									// OPTAB의 최종 길이를 나타낼 instIndex
			}
			fScanner.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	/*
	 * initInputFile()
	 * : 매개로 파일 이름을 받는다. 그 파일에서 source code의 내용을 한 줄 단위로 읽어서 ArrayList에 저장한다.
	 * : 정상종료시 0반환, 비정상종료시 -1반환.
	 * 
	 */
	public static int initInputFile(String filename)
	{
		File file=new File(filename);
		String input="";
		try
		{
			Scanner fScanner=new Scanner(file);
			while (fScanner.hasNextLine())				// file로 부터 한줄 읽어 들인다.
			{
				input=fScanner.nextLine();
				inputData.add(input);
				lineNum++;								// 최종적으로 source code의 줄 수를 가지게 된다.
			}
			fScanner.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	/*
	 * assemPass1()
	 * : assembler의 pass1과정을 진행하는 함수이다. 파일의 끝까지 다다를때까지 진행하고, 한 줄씩 token parsing을 진행한다.
	 * : 정상종료시 0반환, 비정상종료시 -1반환.
	 * 
	 */
	public static int assemPass1()
	{
		String programName;
		String opcode;
		String startAddress;
		int idx=1;
		
		StringTokenizer sTokenizer=new StringTokenizer(inputData.get(0));		// source code의 첫 줄을 읽어들인다.
		programName=sTokenizer.nextToken();
		opcode=sTokenizer.nextToken();
		startAddress=sTokenizer.nextToken();
		
		if (opcode.equals("START"))													// 만약 'START'라는 directive였다면,
			locationCounter=Integer.parseInt(startAddress);							// operand부분의 값을 location counter의 시작 값으로 지정한다.
		else
			locationCounter=0;														// 아니었다면, location counter의 값을 0으로 초기화한다.
		
		Token obj=new Token(locationCounter,-1,programName,opcode,null,null);		// 첫 줄을 token table에 저장한다.
		tokenTable.add(obj);
		
		programNumber=0;
		curLineNum=1;
		literalNum.add(-1);
		labelNum.add(0); 
		literalPtr=-1;
		subProgramStart=false;
		baseRegister.setAddress(0);
		baseRegister.setSymbol(null);													// pass1에서 쓰일 변수들을 초기화한다.
		
		symbolTable.add(new ArrayList<Symbol>());
		symbolTable.get(programNumber).add(new Symbol(programName,locationCounter));		// 첫 줄에서 program name과 location counter를 symbol table에 저장한다.
		labelNum.set(programNumber,labelNum.get(programNumber)+1);				
		
		literalTable.add(new ArrayList<Literal>());
		modificationTable.add(new ArrayList<Modification>());
		modiNum.add(0);
		
		while (tokenParsing(idx))													// source code의 끝에 도달 할 때까지 token을 parsing함.
		{
			idx++;
			curLineNum++;															// curLineNum은 tokenParsing함수 이외에서도 증가 가능하므로, tokenParsing의 parameter로 넘겨주면 안된다.
		}
		tokenLine=curLineNum;
		
		return 0;
	}
	
	/*
	 * assemPass2()
	 * : assembler의 pass2과정을 진행하는 함수이다. token으로 나눈 한줄씩에 대해서 진행하며, addressing을 진행한다.
	 * : 정상종료시 0반환, 비정상종료시 -1반환.
	 * 
	 */
	public static int assemPass2()
	{
		String outputStream;
		int index,opcodeNumber,temp,opcodeCnt=0;
		boolean passOrNot=false,isLooping=false;
		programNumber=0;
		baseRegisterInitialize();						// base register가 설정 되었을 경우에 base 설정을 도와주는 함수.
		
		for (int i=0;i<tokenLine;i++)					// token table의 끝까지 진행.
		{
			index=searchOpcode(tokenTable.get(i).getOperator());		// opcode가 OPTAB에서 위치한 줄의 번호를 반환.
			isLooping=false;
			opcodeCnt=0;
			pcOrBase=true;
			passOrNot=false;
			opcodeNumber=0;
			
			if (index>0)
			{
				opcodeNumber=Integer.parseInt(inst.get(index).getOpcode(),16);		// opcode는 string형태이므로 wrapper class를 이용해서 값을 넣는다.
				opcodeNumber+=indirectImmediate(i,index);
			}
			else if (index==-1)												// opcode가 존재하지 않는 경우.
			{
				if (tokenTable.get(i).getOperator().charAt(0)=='=')			// literal이라면 값을 배정한다.
					valuePacking(3,i,tokenTable.get(i).getOperator());
				else if (tokenTable.get(i).getOperator().equals("BYTE") 	// 'WORD','BYTE'에 대해서도 값을 배정한다.
						|| tokenTable.get(i).getOperator().equals("WORD"))
					valuePacking(2,i,tokenTable.get(i).getOperand().get(0));
				else if (tokenTable.get(i).getOperator().equals("EQU"))		// 'EQU'에 대해서도 값을 배정한다.
					equateRefine(i,tokenTable.get(i).getOperand().get(0));
				else if (tokenTable.get(i).getOperator().equals("CSECT"))	// 'CSECT'에 대해서는 프로그램 번호만 증가시킨다.
					programNumber++;
				passOrNot=true;										// 이 조건문을 실행한 단계에서는 addressing작업을 하지 않기 위해서 플래그 설정.
			}
			else
				passOrNot=true;				// 그 외의 경우(주석)에도 addressing작업을 하지 않는다.
			
			if (!passOrNot)					// addressing작업을 하는 부분.
			{
				if (tokenTable.get(i).getInstructionFormat()==1)		// 1형식이라면,
				{
					outputStream=Integer.toHexString(opcodeNumber).toUpperCase();	// 16진수로 읽어들인다.
					if (outputStream.length()!=2)
					{
						while (outputStream.length()!=2)
							outputStream="0"+outputStream;				// 길이를 맞춰주는 부분.
					}					
					tokenTable.get(i).setComment(outputStream);
				}
				else if (tokenTable.get(i).getInstructionFormat()==2)	// 2형식이라면,
				{
					for (int k=0;k<tokenTable.get(i).getOperand().size();k++)
					{
						if (tokenTable.get(i).getOperand()!=null)
						{
							if (tokenTable.get(i).getOperand().get(k)!=null)
							{
								opcodeNumber=opcodeNumber<<4;			//  shift연산 후 operand하나 넣는 방식으로 진행.
								if (tokenTable.get(i).getOperand().get(k).equals("A")) opcodeNumber=opcodeNumber+A;
								if (tokenTable.get(i).getOperand().get(k).equals("X")) opcodeNumber=opcodeNumber+X;
								if (tokenTable.get(i).getOperand().get(k).equals("L")) opcodeNumber=opcodeNumber+L;
								if (tokenTable.get(i).getOperand().get(k).equals("B")) opcodeNumber=opcodeNumber+B;
								if (tokenTable.get(i).getOperand().get(k).equals("S")) opcodeNumber=opcodeNumber+S;
								if (tokenTable.get(i).getOperand().get(k).equals("T")) opcodeNumber=opcodeNumber+T;
								if (tokenTable.get(i).getOperand().get(k).equals("F")) opcodeNumber=opcodeNumber+F;
								if (tokenTable.get(i).getOperand().get(k).equals("PC")) opcodeNumber=opcodeNumber+PC;
								if (tokenTable.get(i).getOperand().get(k).equals("SW")) opcodeNumber=opcodeNumber+SW;
								opcodeCnt++;
							}
						}
					}
					if (opcodeCnt==1)			// 만약, operand가 1개였다면, 형식유지를 위해서 shift연산을 취해준다.
						opcodeNumber<<=4;
					
					outputStream=Integer.toHexString(opcodeNumber).toUpperCase();
					if (outputStream.length()!=4)
					{
						while (outputStream.length()!=4)	// 제일 앞부분이 0이면, 숫자로 사용하면 생략되므로, 인위적으로 0을 붙여준다.
							outputStream="0"+outputStream;
					}
					tokenTable.get(i).setComment(outputStream);
				}
				else if (tokenTable.get(i).getInstructionFormat()==3 
						|| tokenTable.get(i).getInstructionFormat()==4)			// 3형식,4형식이라면,
				{
					for (int j=0;j<tokenTable.get(i).getOperand().size();j++)
					{
						if (tokenTable.get(i).getOperand()!=null)
						{
							if (tokenTable.get(i).getOperand().get(j).equals("X"))
								isLooping=true;
						}
					}															// X register를 통해서 looping을 지원하는지를 확인한다.
					opcodeNumber<<=4;								// 받아온 opcode번호를 한 바이트 만큼 shift한다.
					
					if (isLooping)
						opcodeNumber+=8;							// looping지원을 하면 8을 더하고,
					if (tokenTable.get(i).getInstructionFormat()==4)
						opcodeNumber+=1;							// 4형식이라면 1을 더한다.
					
					if (tokenTable.get(i).getOperand()!=null && tokenTable.get(i).getOperand().size()>0)	// operand가 있는경우.
					{
						if (tokenTable.get(i).getOperand().get(0).charAt(0)=='#')				// immediate addressing인 경우.
						{
							if (Character.isDigit(tokenTable.get(i).getOperand().get(0).charAt(1)))	// 숫자로 바로 immediate addressing을 진행하는 경우.
							{
								temp=Integer.parseInt(tokenTable.get(i).getOperand().get(0).substring(1));
								if (tokenTable.get(i).getInstructionFormat()==4)			// 4형식이라면, 20bit shifting
									opcodeNumber<<=20;
								else														// 3형식이라면, 12bit shifting
									opcodeNumber<<=12;
								
								opcodeNumber|=temp;										// bitwise OR연산을 통해서 opcodeNumber에 offset을 반영한다.
								outputStream=Integer.toHexString(opcodeNumber).toUpperCase();
							}
							else										// 변수로 immediate addressing을 하는 경우.
							{
								temp=relativeOffset(tokenTable.get(i).getInstructionFormat(),
										tokenTable.get(i+1).getLocationCounter(),
										tokenTable.get(i).getOperand().get(0));		// 상대주소값을 가져온다.
								if (tokenTable.get(i).getInstructionFormat()!=4)	// 4형식이 아닌 경우에는,
								{
									if (pcOrBase==true)			// pc-relative addressing인 경우.
										opcodeNumber+=2;
									else if (pcOrBase==false)	// base-relative addressing인 경우.
										opcodeNumber+=4;
									else
										return -1;
								}
								if (tokenTable.get(i).getInstructionFormat()==4)				// 4형식이라면, 20bit shifting
									opcodeNumber<<=20;
								else															// 3형식이라면, 12bit shifting
									opcodeNumber<<=12;
								
								opcodeNumber|=temp;
							}
						}
						else if (tokenTable.get(i).getOperand().get(0).charAt(0)=='@')		// indirect addressing이라면,
						{
							temp=relativeOffset(tokenTable.get(i).getInstructionFormat(),	// 일반 addressing과 흡사하므로 offset계산을 한다.
									tokenTable.get(i+1).getLocationCounter(),
									tokenTable.get(i).getOperand().get(0).substring(1));
							if (tokenTable.get(i).getInstructionFormat()!=4)		// 4형식이 아니라면,
							{
								if (pcOrBase==true)			// pc-relative addressing인 경우.
									opcodeNumber+=2;
								else if (pcOrBase==false)	// base-relative addressing인 경우.
									opcodeNumber+=4;
								else
									return -1;
							}
							
							if (tokenTable.get(i).getInstructionFormat()==4)			// 4형식이라면, 20bit shifting
								opcodeNumber<<=20;
							else														// 3형식이라면, 12bit shifting
								opcodeNumber<<=12;
							
							opcodeNumber|=temp;
						}
						else					// 그 외의 addressing이라면,
						{
							temp=relativeOffset(tokenTable.get(i).getInstructionFormat()
									,tokenTable.get(i+1).getLocationCounter(),
									tokenTable.get(i).getOperand().get(0));
							if (tokenTable.get(i).getInstructionFormat()!=4)
							{
								if (pcOrBase==true)
									opcodeNumber+=2;				// 형식을 맞춰주기 위해서 0을 추가.
								else if (pcOrBase==false)
									opcodeNumber+=4;
								else
									return -1;
							}
							if (tokenTable.get(i).getInstructionFormat()==4)		// 4형식이라면, 20bit shifting
								opcodeNumber<<=20;
							else													// 3형식이라면, 12bit shifting
								opcodeNumber<<=12;
							
							opcodeNumber|=temp;
						}
					}
					else
					{
						if (tokenTable.get(i).getInstructionFormat()==4)	// 4형식이라면, 20bit shifting
							opcodeNumber<<=20;
						else							// 3형식이라면, 12bit shifting
							opcodeNumber<<=12;
					}
					
					if (tokenTable.get(i).getInstructionFormat()==4)		// 4형식 이라면,
					{
						outputStream=Integer.toHexString(opcodeNumber).toUpperCase();
						if (outputStream.length()!=8)
						{
							while (outputStream.length()!=8)
								outputStream="0"+outputStream;		// 8자리 형식을 맞춰주기 위해서 0을 추가.
						}
					}
					else						// 3형식 이라면,
					{
						outputStream=Integer.toHexString(opcodeNumber).toUpperCase();
						if (outputStream.length()!=6)
						{
							while (outputStream.length()!=6)
								outputStream="0"+outputStream;		// 6자리 형식을 맞춰주기 위해서 0을 추가.
						}
					}
					tokenTable.get(i).setComment(outputStream);		// comment위치에 조합된 기계어 코드를 배정.
				}
			}
		}
		return 0;
	}
	
	/*
	 * relativeOffset()
	 * : 매개로 명령어 형식, 다음 location counter의 값, symbol을 받는다.
	 * : OPCODE나 n,i,x,b,p,e같은 플래그 부분을 제외한 addressing 부분을 계산해서 반환하는 함수이다.
	 * : 정상종료시 계산한 address값,혹은 0 반환, 비정상종료시 -1반환.
	 * 
	 */
	public static int relativeOffset(int format,int nextLocationCounter,String symbol)
	{
		int goAddr=getSymbolValue(programNumber,symbol);		// target addressing에 쓸 symbol 주소를 받아온다.		
		int masking=1;						// address 부분을 masking을 통해서 반환하기 위한 변수.
		if (format==3){						// 3형식 이라면 12bit를 masking할 수 있게 만든다.
			for (int i=0;i<11;i++){
				masking<<=1;
				masking|=1;
			}
		}
		else{								// 4형식 이라면 20bit를 masking할 수 있게 만든다.
			for (int i=0;i<19;i++){
				masking<<=1;
				masking|=1;
			}
		}
		if (goAddr==-1){				// SYMTAB에서 찾지 못했을 경우에는, LITTAB에서 찾아본다.
			for (int i=0;i<=literalNum.get(programNumber);i++){
				if (literalTable.get(programNumber).size()>i)	{
					if (literalTable.get(programNumber).
							get(i).getLiteral().equals(symbol))	{
						int value=literalTable.get(programNumber).
								get(i).getAddress()-nextLocationCounter;
						int subValue=literalTable.get(programNumber).
								get(i).getAddress()-baseRegister.getAddress();
						if ((-2048<=value) && (value<=2048)){		// 이 값 사이의 offset이라면 pc-relative
							pcOrBase=true;
							return (value&masking);
						}
						else if ((0<=subValue) && (subValue<=4096)){	// 이 값 사이의 offset이라면 base-relative
							pcOrBase=false;
							return (subValue&masking);
						}
						else return -1;
					}
				}
			}
			return 0;
		}
		else if (goAddr==0)						// goAddr가 0이면 외부 참조를 하는 symbol이므로 값을 모르니 0을 반환한다.
			return 0;
		
		// 그 외의 일반적인 경우에는 다음과 같이 진행한다.
		if ((-2048<=goAddr-nextLocationCounter) 
				&& (goAddr-nextLocationCounter<=2048))	{
			pcOrBase=true;				// 계산한 offset이 범위 안이라면 pc-relative addressing.
			return (goAddr-nextLocationCounter)&masking;
		}
		else if ((0<=goAddr-baseRegister.getAddress()) 
				&& (goAddr-baseRegister.getAddress()<=4096))	{
			pcOrBase=false;				// 계산한 offset이 범위 안이라면 base-relative addressing.
			return (goAddr-baseRegister.getAddress())&masking;
		}
		else return -1;
	}
	
	/*
	 * equateRefine()
	 * : 매개로 tokenTable의 줄 번호와 문자열 하나를 받는다.
	 * : pass2를 진행하면서 EQU directive가 나오면, 그것의 operand의 symbol의 값을 계산해서 SYMTAB의 address부분에 반영해주는 함수이다.
	 * : 정상종료시 0반환.
	 * 
	 */
	public static int equateRefine(int lineNumber,String str)
	{
		int strLength=str.length();
		char operator;
		int operatorPtr=0,op1,op2;
		int i=0;
		
		if (str.equals("*"))			// * 라면 현재 location counter의 값을 symbol의 값으로 가지겠다는 의미. 이미 pass1에서 배정했으므로 덮어쓰지 않는다.
			return 0;
		else	{
			while (!tokenTable.get(lineNumber).getLabel().
					equals(symbolTable.get(programNumber).get(i++).getSymbol()));
			i--;						// tokenTable에도 반영해 주기위해서 tokenTable에서의 줄 번호를 찾는다.
			
			while (Character.isAlphabetic(str.charAt(operatorPtr)) && operatorPtr<strLength) operatorPtr++;
			operator=str.charAt(operatorPtr);
			op1=getSymbolValue(programNumber,str.substring(0,operatorPtr));
			op2=getSymbolValue(programNumber,str.substring(operatorPtr+1));
			
			if (operator=='-')
				symbolTable.get(programNumber).get(i).setAddress(op1-op2);
			else if (operator=='+')
				symbolTable.get(programNumber).get(i).setAddress(op1+op2);
			else if (operator=='*')
				symbolTable.get(programNumber).get(i).setAddress(op1*op2);
			else if (operator=='/')
				symbolTable.get(programNumber).get(i).setAddress(op1/op2);			// 각 연산자에 대한 연산을 진행하고, SYMTAB의 address에 반영한다.
			
			tokenTable.get(lineNumber).setLocationCounter(symbolTable.get(programNumber).get(i).getAddress());	// tokenTable에도 동일하게 적용한다.
		}
		return 0;
	}
	
	/*
	 * getSymbolValue()
	 * : 매개로 프로그램 번호, symbol을 입력받는다.
	 * : assembler의 pass1과정을 진행하는 함수이다. 파일의 끝까지 다다를때까지 진행하고, 한 줄씩 token parsing을 진행한다.
	 * : 정상종료시 매개로 받은 symbol의 address값 반환, 비정상종료시 -1반환.
	 * 
	 */
	public static int getSymbolValue(int prNum,String symbol)
	{
		if (labelNum.size()>0)			// symbol의 개수가 적어도 하나는 있는지 확인.
		{
			for (int i=0;i<labelNum.get(prNum);i++)			// 프로그램의 모든 symbol에 대해서 진행.
			{
				if (symbolTable.get(prNum).get(i).getSymbol().equals(symbol))	// 매개로 받은 symbol과 같은 symbol을 SYMTAB에서 찾으면,
					return symbolTable.get(prNum).get(i).getAddress();		// 그 SYMTAB의 symbol의 address를 반환.
			}
		}
		
		if (xrefNum.size()>0)			// 외부 참조의 개수가 적어도 하나는 있는지 확인.
		{
			for (int i=0;i<xrefNum.get(prNum);i++)			// 프로그램의 모든 외부 참조에 대해서 진행.
			{
				if (xrefTable.get(prNum).get(i).equals(symbol))		// 만약 같은 symbol을 외부 참조 table에서 찾으면,
					return 0;								// address값을 모르니 0을 반환.
			}
		}
		return -1;						// 못 찾으면 -1 반환.
	}
	
	/*
	 * baseRegisterInitialize()
	 * : pass2진행하기 전에 BASE directive가 pass1에 발견되었다면, 그것에 대해서 base register 초기화 과정을 거치게 하는 함수이다.
	 * 
	 */
	public static void baseRegisterInitialize()
	{
		if (baseRegister.getSymbol()==null)	return;			// BASE directive에 할당 된 symbol이 없으면 그냥 끝.
		else	baseRegister.setAddress(
				getSymbolValue(programNumber,baseRegister.getSymbol()));	
		// 있다면 그것의 symbol에 대한 주소의 값을 baseRegister symbol의 address로 취한다.
	}
	
	/*
	 * valuePacking()
	 * : 매개로 값이 최초로 등장하는 문자열 위치, tokenTable에서의 줄 번호, 전체 리터럴 문자열을 받는다.
	 * : literal의 값을 배정하거나, WORD나 BYTE같은 값을 배정하는 경우에 쓰이는 함수이다.
	 * : pass2에서 쓰이는 함수이므로, 외부 참조하는 symbol에 대해서도 진행한다. 
	 * : symbol끼리의 이항 연산까지만 가능하다.
	 * : 정상종료시 0반환.
	 * 
	 */
	public static int valuePacking(int startPos,int lineNumber,String str)
	{
		String tmpBuffer="";		// startPos는 값이 최초로 등장하는 위치를 가지고 있다.
		String strOp1="";
		String strOp2="";
		int end=str.length()-1;
		int thisLiteral=0,XCMul=1,tempVal=0,operatorPtr=0,result=0;
		char operator='+';
		int value,op1,op2;
		
		if (str.charAt(0)=='=')		thisLiteral=1;		// thisLiteral이 1이면 literal에 대한 처리, 
		else	thisLiteral=2;			// 2라면 WORD나 BYTE에서 값을 할당하는 처리.
		
		if (str.charAt(startPos-2)=='X')	// 만약 16진수표현에 대한 값을 배정해야 한다면,
		{
			XCMul=2;				// 값으로 지정된 문자열 길이의 절반만큼을 location counter를 증가시킨다.
			value=Integer.parseInt(str.substring(startPos,end),16);		// 16진수로 읽어 들인다.
			tempVal=value;
		}
		else if (str.charAt(startPos-2)=='C')		// 만약 문자에 대한 값을 배정해야 한다면,
		{
			for (int i=startPos;i<end;i++)
			{
				if (i!=startPos)
					result<<=8;
				result|=str.charAt(i);				// 한 바이트 크기만큼 왼쪽으로 shifting하면서 읽고 bitwise OR연산을 취한다.
			}
			tempVal=result;
		}
		else if (Character.isDigit(str.charAt(startPos-2)))		// 만약 숫자에 대한 literal을 처리해야 한다면,
		{
			thisLiteral=0;
			tempVal=Integer.parseInt(str.substring(startPos-2));	// 16진수로 읽어들여서 반영한다.
		}
		else
		{
			thisLiteral=0;
			while (Character.isAlphabetic(str.charAt(operatorPtr)) && operatorPtr<=end)
				operatorPtr++;							// 연산자를 만날때까지, 끝에 다다르기 전까지 탐색한다.
			
			operator=str.charAt(operatorPtr);
			strOp1=str.substring(0,operatorPtr);
			op1=getSymbolValue(programNumber,strOp1);		// 각 symbol에 대해서 symbol의 주소를 찾아낸다.
			strOp2=str.substring(operatorPtr+1);
			op2=getSymbolValue(programNumber,strOp2);
			
			if (operator=='-')
				tempVal=op1-op2;
			else if (operator=='+')
				tempVal=op1+op2;
			else if (operator=='*')
				tempVal=op1*op2;
			else if (operator=='/')
				tempVal=op1/op2;						// 각 연산자에 대한 연산을 진행한다.
		}
		
		if (thisLiteral==1)	tokenTable.get(lineNumber).setInstructionFormat((end-3)/XCMul);
		else if (thisLiteral==2)	tokenTable.get(lineNumber).setInstructionFormat((end-2)/XCMul);
		else	tokenTable.get(lineNumber).setInstructionFormat(3);		// 출력이나 object code출력을 할 때 형식을 통해 출력하기 위해서 형식 지정.
		
		tmpBuffer=Integer.toHexString(tempVal).toUpperCase();			// 16진수 대문자화 시킨다.
		if (tmpBuffer.length()!=tokenTable.get(lineNumber).getInstructionFormat()*2)	// 출력의 틀에 맞춰 주기 위해서
		{
			while (tmpBuffer.length()!=tokenTable.get(lineNumber).getInstructionFormat()*2)	// (지정한 형식*2)의 길이가 될 때까지 앞부분에 '0'을 추가한다.
				tmpBuffer="0"+tmpBuffer;
		}
		tokenTable.get(lineNumber).setComment(tmpBuffer);			// comment위치에 완성된 값을 지정한다.
		
		// 이 아래 부분은 외부 참조를 하는 symbol들에 대해서 modification record를 쓰기 위해 진행하는 조건문.
		if (xrefNum.size()>0)
		{
			// 첫 번째 피 연산자에 대해서 진행한다.
			for (int j=0;j<xrefNum.get(programNumber);j++)
			{
				if (tokenTable.get(lineNumber).getOperator()!=null)
				{
					if (xrefTable.get(programNumber).get(j).equals(strOp1))	// 현재 값을 배정해야 하는 symbol이 외부참조를 하는 symbol이라면,
					{
						modificationTable.get(programNumber).add(new Modification());		// modification table에 현재 symbol의 이름과 연산자, 위치등의 정보를 저장한다.
						modificationTable.get(programNumber).
						get(modiNum.get(programNumber)).setIsOpcode(false);
						modificationTable.get(programNumber).
						get(modiNum.get(programNumber)).setModificationName(strOp1);
						modificationTable.get(programNumber).
						get(modiNum.get(programNumber)).setAddress(tokenTable.get(lineNumber).getLocationCounter());
						
						if (tokenTable.get(lineNumber).getOperator().equals("WORD"))
							modificationTable.get(programNumber).
							get(modiNum.get(programNumber)).setChangeSize(6);
						if (tokenTable.get(lineNumber).getOperator().equals("BYTE"))
							modificationTable.get(programNumber).
							get(modiNum.get(programNumber)).setChangeSize(2);
						
						modificationTable.get(programNumber).
						get(modiNum.get(programNumber)).setOperator('+');		// 첫 번째 피 연산자의 부호는 +라고 가정한다.
						modiNum.set(programNumber,modiNum.get(programNumber)+1);		// 프로그램의 외부 참조의 개수 하나 증가.
					}
				}
			}
			// 두번째 피 연산자에 대해서 진행한다.
			for (int j=0;j<xrefNum.get(programNumber);j++)
			{
				if (tokenTable.get(lineNumber).getOperator()!=null)
				{
					if (xrefTable.get(programNumber).get(j).equals(strOp2))
					{
						modificationTable.get(programNumber).add(new Modification());			// modification table에 현재 symbol의 이름과 연산자, 위치등의 정보를 저장한다.
						modificationTable.get(programNumber).
						get(modiNum.get(programNumber)).setModificationName(strOp2);
						modificationTable.get(programNumber).
						get(modiNum.get(programNumber)).setAddress(tokenTable.get(lineNumber).getLocationCounter());
						modificationTable.get(programNumber).
						get(modiNum.get(programNumber)).setIsOpcode(false);
	
						if (tokenTable.get(lineNumber).getOperator().equals("WORD"))
							modificationTable.get(programNumber).
							get(modiNum.get(programNumber)).setChangeSize(6);
						if (tokenTable.get(lineNumber).getOperator().equals("BYTE"))
							modificationTable.get(programNumber).
							get(modiNum.get(programNumber)).setChangeSize(2);
						
						modificationTable.get(programNumber).
						get(modiNum.get(programNumber)).setOperator(operator);
						modiNum.set(programNumber,modiNum.get(programNumber)+1);		// 프로그램의 외부 참조의 개수 하나 증가.
					}
				}
			}
		}
		return 0;
	}
	
	/*
	 * indirectImmediate()
	 * : 매개로 tokenTable의 줄번호와 OPTAB에서의 opcode의 줄번호를 받는다.
	 * : indirect addressing인지, immediate addressing인지를 판별해서, instruction 중앙부분의 값을 만들어서 반환해준다.
	 * : 정상종료시 0반환, 비정상종료시 -1반환.
	 * 
	 */
	public static int indirectImmediate(int lineNumber,int opcodeLine)
	{
		if (!inst.get(opcodeLine).getOperNum().equals("0"))			// operand가 존재하는 경우,
		{
			if (tokenTable.get(lineNumber).getInstructionFormat()==3 
					|| tokenTable.get(lineNumber).getInstructionFormat()==4)	// 3,4형식에 대해서
			{
				if (tokenTable.get(lineNumber).getOperand().get(0).charAt(0)=='#')		// operand첫부분이 '#'라면
					return 1;															// immediate addressing
				else if (tokenTable.get(lineNumber).getOperand().get(0).charAt(0)=='@')	// operand 첫부분이 '@'라면
					return 2;															// indirect addressing
				else
					return 3;										// 그 외의 경우는 SIC/XE의 direct addressing이다.
			}
			else													// 1,2 형식은 indirect, immediate를 지원하지 않으므로 제외.
				return 0;
		}
		else														// operand가 없는경우,
		{
			if (tokenTable.get(lineNumber).getInstructionFormat()==3 
					|| tokenTable.get(lineNumber).getInstructionFormat()==4)	// 3형식이나, 4형식이면 3을 반환.
				return 3;
			else
				return 0;											// 그 외의 경우에는 0반환.
		}
	}
	
	/*
	 * makeOutput()
	 * : 매개로 파일 이름을 받는다.
	 * : pass1, pass2 과정을 거치고 짧은 기계어 코드들을 가지고 intermediateFile을 화면에 출력하는 함수이다.
	 * 
	 */
	public static void makeOutput(String filename)
	{
		boolean printed=false;						// operand출력시, ','를 출력할지 말지를 알려주는 플래그.
		for (int i=0;i<curLineNum;i++)
		{
			printed=false;
			if (tokenTable.get(i).getInstructionFormat()!=0 || i==0)		// 명령어 형식이 0(임의로 정함)이 아니거나, 첫 줄일 경우에는 location counter를 출력한다.
				System.out.printf("%04X	",tokenTable.get(i).getLocationCounter());
			else
				System.out.printf("	");
			
			if (tokenTable.get(i).getLabel()!=null)						// label이 있다면 label을 출력한다.
				System.out.printf("%s	", tokenTable.get(i).getLabel());
			else
				System.out.printf("	");
			
			if (tokenTable.get(i).getOperator()!=null)
				System.out.printf("%s	",tokenTable.get(i).getOperator());
			else
				System.out.printf("	");
			
			for (int j=0;j<MAX_OPERAND;j++)								// operand 최대 개수에 대해서 진행한다.
			{
				if (tokenTable.get(i).getOperand()!=null && (tokenTable.get(i).getOperand().size()>j))	// operand가 있고, j가 총 operand 개수를 넘지 않았을 경우에는
				{
					if (tokenTable.get(i).getOperand().get(j)!=null)
					{
						if (!printed)
						{																		// 각 operand를 출력한다.
							System.out.printf("%s",tokenTable.get(i).getOperand().get(j));
							printed=true;
						}
						else
							System.out.printf(",%s",tokenTable.get(i).getOperand().get(j));
					}
				}
				else
					System.out.printf("	");
			}
			
			if (tokenTable.get(i).getComment()!=null)							// comment가 있다면 comment를 출력한다.
				System.out.printf("%s\n",tokenTable.get(i).getComment());
			else
				System.out.printf("\n");
		}
		if (filename!=null)								// 매개로 받은 filename이 null이 아니라면,
			makeObjectcode(filename);					// object code를 파일에 쓰는 함수를 실행한다.
	}
	
	/*
	 * makeObjectcode()
	 * : 매개로 파일이름을 받는다.
	 * : 짧은 기계어 코드들을 가지고 object program형식에 맞게 쓰기 위해 여러 과정을 가지는 함수이다.
	 * 
	 */
	public static void makeObjectcode(String filename)
	{
		File outputFile=new File(filename);
		String temp="";
		try
		{
			FileWriter fwriter=new FileWriter(outputFile);
			int lineMax=0,objectCodeSum=0,reserved=-1,outPtr=0,befPtr=0;
			int lineStartPtr=0;
			int lineNumPtr=0;
			
			for (int i=0;i<=programNumber;i++){
				lineMax=0;
				outPtr=0;
				reserved=0;
				
				temp=String.format("H%-6s",symbolTable.get(i).get(0).getSymbol());			// 프로그램 이름.
				fwriter.write(temp);
				temp=String.format("%06X",symbolTable.get(i).get(0).getAddress());			// 프로그램 시작 주소.
				fwriter.write(temp);
				temp=String.format("%06X\r\n",programLength.get(i));					// 프로그램 길이.
				fwriter.write(temp);
				
				if (xdefNum.size()>i && xdefNum.get(i)!=0)			// 외부 정의의 symbol을 쓰는 부분.
				{
					fwriter.write("D");
					for (int j=0;j<xdefNum.get(i);j++)
						fwriter.write(String.format("%-6s%06X",xdefTable.get(i).get(j),		// address와 symbol을 같이 적는다.
								getSymbolValue(i,xdefTable.get(i).get(j))));
					fwriter.write("\r\n");			// 개행 출력시, '\r\n'을 동시에 출력해주어야 메모장에도 반영됨. 
				}
				if (xrefNum.size()>i && xrefNum.get(i)!=0)			// 외부 참조의 symbol을 쓰는 부분.
				{
					fwriter.write("R");
					for (int j=0;j<xrefNum.get(i);j++)
						fwriter.write(String.format("%-6s",xrefTable.get(i).get(j)));			// symbol만 적는다.
					fwriter.write("\r\n");
				}
				
				while (true)				// object program의 text record부분.
				{
					objectCodeSum=0;
					befPtr=0;
					for (lineMax=0; (lineMax<=30) && (lineNumPtr<tokenLine);)		// 한 줄의 바이트 수가 30이 넘지 않고, tokenTable의 끝에 다다르지 않았을 경우.
					{
						if (tokenTable.get(lineNumPtr).getOperator()!=null)
						{
							if (tokenTable.get(lineNumPtr).getOperator().equals("CSECT"))	// 'CSECT'를 만나면 프로그램을 바꿔서 써주어야함.
							{
								outPtr=1;
								break;
							}
							if (tokenTable.get(lineNumPtr).getOperator().equals("RESW") ||	// 'RESB','RESW'등의 예약하는 부분또한 줄바꿈을 해주어야 한다.
									tokenTable.get(lineNumPtr).getOperator().equals("RESB"))
							{
								reserved=1;
								break;
							}
						}
						if (tokenTable.get(lineNumPtr).getComment()!=null)					// comment부분에 pass2에서 진행한 기계어 코드들이 있다.
						{
							lineMax+=tokenTable.get(lineNumPtr).getInstructionFormat();
							befPtr=tokenTable.get(lineNumPtr).getInstructionFormat();
						}
						lineNumPtr++;
					}
					
					if (lineMax>30){		// 만약, 위 반복문이 30바이트를 넘겨서 종료 되었다면,
						lineNumPtr--;		// tokenTable지시자를 하나 줄이고,
						objectCodeSum=(lineMax-befPtr);		// 그 크기만큼 쓸 코드 크기도 줄인다.
					}
					else					// 그렇지 않다면 
						objectCodeSum=lineMax;		// 그대로 할당한다.
					
					if (lineNumPtr>=tokenLine)
						outPtr=1;			// tokenTable의 끝에 다다르면 while문 탈출.
					
					fwriter.write(String.format("T%06X",tokenTable.
							get(lineStartPtr).getLocationCounter()));		// 시작 주소 출력.
					fwriter.write(String.format("%02X",objectCodeSum));							// text record의 코드 길이 출력.
					for (int j=lineStartPtr;j<lineNumPtr;j++){
						if (tokenTable.get(j).getComment()!=null)
							fwriter.write(String.format("%s",tokenTable.get(j).getComment()));
					}
					fwriter.write("\r\n");
					
					if (reserved==1)			// 'RES*'등의 directive가 나오면, 이것들이 후반부에 선언되었다고 가정할 때, 지시자가 'CSECT' directive를 찾도록 한다.
					{
						while (lineNumPtr<tokenLine)
						{
							if (tokenTable.get(lineNumPtr).getOperator()!=null)
								if (tokenTable.get(lineNumPtr).getOperator().equals("CSECT"))	{
									outPtr=1;
									break;
								}
							if (tokenTable.get(lineNumPtr).getComment()!=null && 
									tokenTable.get(lineNumPtr).getComment().length()!=0)
								break;
							lineNumPtr++;
						}
					}
					lineStartPtr=lineNumPtr;
					if (outPtr==1)				// while문 깨고 나감. 다음 프로그램을 쓸 준비.
						break;
				}
				if (lineNumPtr<tokenLine)
					while (tokenTable.get(lineNumPtr).getComment()==null)	lineNumPtr++;
				lineStartPtr=lineNumPtr;			// comment부분이 비었으면 무시하고, comment부분에 값이 있을 때까지 지시자를 옮긴다.
				
				for (int j=0;j<modiNum.get(i);j++)		// modification record에 대해서 진행.
				{
					fwriter.write("M");
					if (modificationTable.get(i).get(j).getIsOpcode())			// instruction에 대해서는 location counter의 값에 +1(기계어 코드 머리부분의 2바이트 띄우고 바꿔줘야 함.)
						fwriter.write(String.format("%06X",modificationTable.get(i).get(j).getAddress()+1));
					else
						fwriter.write(String.format("%06X",modificationTable.get(i).get(j).getAddress()));
					fwriter.write(String.format("%02X",modificationTable.get(i).get(j).getChangeSize()));
					fwriter.write(String.format("%C",modificationTable.get(i).get(j).getOperator()));
					fwriter.write(String.format("%-6s\r\n",modificationTable.get(i).get(j).getModificationName()));
				}
				if (i==0)			// 첫 번째로온 main program이었다면 시작주소를 end record에 출력.
					fwriter.write(String.format("E%06X\r\n",symbolTable.get(i).get(0).getAddress()));
				else				// sub program이었다면 시작주소를 출력하지 않음.
					fwriter.write("E\r\n");
				fwriter.write("\r\n");
			}
			fwriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * tokenParsing()
	 * : 매개로 inputData의 줄 번호가 들어온다.
	 * : inputData의 입력의 형식이 일정하지 않으므로 그것에 대한 처리를 하는 함수.
	 * : 정상종료시 true반환, 비정상종료시 false반환.
	 * 
	 */
	public static boolean tokenParsing(int idx)
	{
		String stringBuffer;
		String tmp;
		int temp;
		
		if (idx>=lineNum)									// parsing 과정에서 source code 줄수를 넘어갔다면 끝.
			return false;
		
		StringTokenizer sTokenizer=new StringTokenizer(inputData.get(idx));		// source code한 줄을 stringTokenizer객체에 담는다.
		stringBuffer=sTokenizer.nextToken();				// 제일 처음 토큰을 읽어 온다.
		
		if ((searchOpcode(stringBuffer))>=0)				// 읽어 온 것이 instruction이라면,
		{
			if (parseOpcode(stringBuffer,idx)<0)
				return false;
			if (subProgramStart)							// main이 아닌 sub program이 동작하는 중이라면,
			{
				String tp=symbolTable.get(programNumber).get(0).getSymbol();
				tokenTable.get(curLineNum).setLabel(tp);
				subProgramStart=false;
			}
			else
				tokenTable.get(curLineNum).setLabel("");
		}
		else if (inputData.get(idx).charAt(0)=='.')			// 읽어 온 것의 제일 첫 문자가 '.'이라면 comment로 간주한다.
		{
			tokenTable.add(new Token());
			tokenTable.get(curLineNum).setLabel(inputData.get(idx));
			tokenTable.get(curLineNum).setComment(null);
			tokenTable.get(curLineNum).setInstructionFormat(0);
			tokenTable.get(curLineNum).setLocationCounter(0);
			tokenTable.get(curLineNum).setOperand(null);
			tokenTable.get(curLineNum).setOperator(null);
		}
		else if (stringBuffer.equals("END"))				// 'END' directive라면,
		{
			tmp=sTokenizer.nextToken();
			
			tokenTable.add(new Token());
			tokenTable.get(curLineNum).setLabel("");
			tokenTable.get(curLineNum).setComment(null);
			tokenTable.get(curLineNum).setInstructionFormat(0);
			tokenTable.get(curLineNum).setLocationCounter(0);
			tokenTable.get(curLineNum).setOperand(new ArrayList<String>());
			tokenTable.get(curLineNum).getOperand().add(tmp);
			tokenTable.get(curLineNum).setOperator(stringBuffer);
			
			literalNum.set(programNumber,literalPtr);
			while (literalPtr>=0)							// LITTAB에 담아둔 literal모두를 tokenTable에 할당한다.
				literalOrganization();
			
			if (!lengthChecked)								// 프로그램 길이를 체크하지 않았다면,
			{
				programLength.add(locationCounter);			// programLength에 값을 넣는다.
				lengthChecked=true;
			}
			return true;
		}
		else if (stringBuffer.equals("LTORG"))				// 'LTORG' directive라면,
		{
			tokenTable.add(new Token());
			tokenTable.get(curLineNum).setLabel("");
			tokenTable.get(curLineNum).setInstructionFormat(0);
			tokenTable.get(curLineNum).setComment("");
			tokenTable.get(curLineNum).setOperand(null);
			tokenTable.get(curLineNum).setLocationCounter(0);
			tokenTable.get(curLineNum).setOperator(stringBuffer);
			
			literalNum.set(programNumber,literalPtr);
			
			while (literalPtr>=0)							// 'LTORG'가 호출 되기 전까지의 LITTAB에 담아둔 literal모두를 tokenTable에 할당한다.
				literalOrganization();
		}
		else if (stringBuffer.equals("EXTDEF"))				// 'EXTDEF'라면,
		{
			tmp=sTokenizer.nextToken();	
			tokenTable.add(new Token());
			tokenTable.get(curLineNum).setLabel("");
			tokenTable.get(curLineNum).setOperator(stringBuffer);
			tokenTable.get(curLineNum).setOperand(new ArrayList<String>());
			tokenTable.get(curLineNum).getOperand().add(tmp);
			tokenTable.get(curLineNum).setComment("");
			tokenTable.get(curLineNum).setInstructionFormat(0);
			tokenTable.get(curLineNum).setLocationCounter(0);
			
			xdefTable.add(new ArrayList<String>());
			xdefNum.add(operandDivider(tokenTable.get(curLineNum).getOperand().get(0),
					xdefTable.get(programNumber)));			// 연산자 개수와, ArrayList에 연산자를 나누어서 담는다.
		}
		else if (stringBuffer.equals("EXTREF"))				// 'EXTREF'라면,
		{
			tmp=sTokenizer.nextToken();
			
			tokenTable.add(new Token());
			tokenTable.get(curLineNum).setLabel("");
			tokenTable.get(curLineNum).setOperator(stringBuffer);
			tokenTable.get(curLineNum).setOperand(new ArrayList<String>());
			tokenTable.get(curLineNum).getOperand().add(tmp);
			tokenTable.get(curLineNum).setComment("");
			tokenTable.get(curLineNum).setLocationCounter(0);
			tokenTable.get(curLineNum).setInstructionFormat(0);
			
			xrefTable.add(new ArrayList<String>());
			xrefNum.add(operandDivider(tokenTable.get(curLineNum).getOperand().get(0),
					xrefTable.get(programNumber)));			// 연산자 개수와, ArrayList에 연산자를 나누어서 담는다.
		}
		else if (stringBuffer.equals("BASE"))				// 'BASE'라면,
		{
			tmp=sTokenizer.nextToken();
			
			tokenTable.add(new Token());
			tokenTable.get(curLineNum).setLabel("");
			tokenTable.get(curLineNum).setComment("");
			tokenTable.get(curLineNum).setOperator(stringBuffer);
			tokenTable.get(curLineNum).setLocationCounter(0);
			tokenTable.get(curLineNum).setOperand(new ArrayList<String>());
			tokenTable.get(curLineNum).getOperand().add(tmp);
			tokenTable.get(curLineNum).setInstructionFormat(0);
			baseRegister.setSymbol(tmp);					// symbol을 baseRegister변수에 할당한다.
		}
		else												// 그 외의 경우는 label이라고 가정한다.
		{
			tmp=sTokenizer.nextToken();						// 다음 토큰을 읽어온다.
			temp=locationCounter;							// parseOpcode과정에서 location counter가 변할 수 있으므로 미리 저장해둔다.

			if (tmp.equals("CSECT"))						// 그 다음 토큰이 'CSECT'라면,
			{
				tokenTable.add(new Token());
				tokenTable.get(curLineNum).setLabel(stringBuffer);
				tokenTable.get(curLineNum).setLocationCounter(0);
				tokenTable.get(curLineNum).setOperand(null);
				tokenTable.get(curLineNum).setComment(null);
				tokenTable.get(curLineNum).setInstructionFormat(0);
				tokenTable.get(curLineNum).setOperator(tmp);
				
				if (!lengthChecked)							// 프로그램 길이를 체크하지 않았다면,
				{
					programLength.add(locationCounter);		// programLength의 현재 프로그램 길이를 저장한다.
					lengthChecked=true;
				}
				
				locationCounter=0;
				programNumber++;
				labelNum.add(1);
				literalPtr=-1;
				lengthChecked=false;
				subProgramStart=true;
				symbolTable.add(new ArrayList<Symbol>());
				symbolTable.get(programNumber).add(new Symbol(stringBuffer,locationCounter));		// 다음 프로그램의 symbol을 저장할 ArrayList.		
				literalTable.add(new ArrayList<Literal>());
				literalNum.add(0);
				modificationTable.add(new ArrayList<Modification>());
				modiNum.add(0);
														// 새로운 프로그램을 시작하기 위한 flag변수 및, 공간 할당 과정.
				return true;
			}
			else											// CSECT가 아니었다면, 
			{
				symbolTable.get(programNumber).add(new Symbol());				// label을 저장하기 위해 symbol객체를 하나 늘려준다.
				labelNum.set(programNumber,labelNum.get(programNumber)+1);		// label개수 증가.
				if (parseOpcode(tmp,idx)<0)									// opcode를 값을 tokenTable에 지정한다.
					return false;		
				
				symbolTable.get(programNumber).get(labelNum.get(programNumber)-1).setSymbol(stringBuffer);	// 새로 추가한 symbol의 이름 지정.
				if (EQUCheck)
					EQUCheck=false;							// 'EQU' directive였다면 새로 넣은 symbol에 location counter를 할당하지 않는다.
				else
					symbolTable.get(programNumber).get(labelNum.get(programNumber)-1).setAddress(temp);	// 그 외의 경우에는 새로 만든 symbol에 location counter를 할당한다.
						
				tokenTable.get(curLineNum).setLabel(stringBuffer);			// label을 지정한다.
			}
		}
		return true;
	}
	
	/*
	 * parseOpcode()
	 * : 매개로 명령어를 문자열으로 받고, inputData의 줄 번호를 index로 받는다.
	 * : 정상종료시 0반환, 비정상종료시 -1반환.
	 * 
	 */
	public static int parseOpcode(String opcode,int index)
	{
		String buf1,tmpOp="";
		StringTokenizer sTokenizer=new StringTokenizer(inputData.get(index));
		boolean isFour=false,isTwo=false,isOne=false;
		int checkOp;
		
		while (!(opcode.equals(sTokenizer.nextToken())));
		
		if (opcode.charAt(0)=='+')				// 만약, instruction이 4형식 이라면,
		{
			isFour=true;
			tmpOp=opcode;
			opcode=opcode.substring(1);			// 부분 문자열을 취한다.
		}
		checkOp=searchOpcode(opcode);			// OPTAB에서 instruction에 해당 하는 OPCODE를 찾는다.
		
		if (checkOp<0)							// instruction이 OPTAB에 없는 경우. directive인 경우.
		{
			if (opcode.equals("WORD") || opcode.equals("RESW")
					|| opcode.equals("RESB")|| opcode.equals("BYTE"))
			{
				buf1=sTokenizer.nextToken();				// operand에 값이나, 개수가 들어있으므로 읽어온다.
				
				tokenTable.add(new Token());
				tokenTable.get(curLineNum).setComment("");
				tokenTable.get(curLineNum).setInstructionFormat(1);
				tokenTable.get(curLineNum).setLocationCounter(locationCounter);
				tokenTable.get(curLineNum).setOperator(opcode);
				tokenTable.get(curLineNum).setOperand(new ArrayList<String>());
				tokenTable.get(curLineNum).getOperand().add(buf1);
				
				if (opcode.equals("WORD"))
					locationCounter+=3;
				else if (opcode.equals("RESW"))
					locationCounter+=3*Integer.parseInt(buf1);
				else if (opcode.equals("BYTE"))
					locationCounter+=1;
				else if (opcode.equals("RESB"))
					locationCounter+=Integer.parseInt(buf1);
				return 0;						// 각 directive에 따라서 location counter 증가값을 달리한다.
			}
			else if (opcode.equals("EQU"))			// 'EQU' directive라면,
			{
				buf1=sTokenizer.nextToken();		// operand부분에 symbol이나 '*'가 주어질 수 있다.
				
				tokenTable.add(new Token());
				tokenTable.get(curLineNum).setComment("");
				tokenTable.get(curLineNum).setOperator(opcode);
				tokenTable.get(curLineNum).setOperand(new ArrayList<String>());
				tokenTable.get(curLineNum).getOperand().add(buf1);
				
				if (buf1.equals("*"))				// '*'이라면, 현재 location counter의 값을 symbol과 함께 SYMTAB에 저장한다.
				{
					tokenTable.get(curLineNum).setLocationCounter(locationCounter);
					symbolTable.get(programNumber).get(labelNum.get(programNumber)-1).setAddress(locationCounter);
				}
				else								// 그렇지 않다면, pass1에서는 값을 지정하지 않는다.
				{
					tokenTable.get(curLineNum).setLocationCounter(0);
					symbolTable.get(programNumber).get(labelNum.get(programNumber)-1).setAddress(0);
				}
				
				if (!lengthChecked)					// 'EQU'가 프로그램 제일 마지막에 주어졌다고 가정한 상태임.
				{									// SYMTAB안에는 들어가지만 location counter증감에는 영향이 없기 때문에 마지막이라고 생각하고 길이를 측정한다.
					programLength.add(locationCounter);
					lengthChecked=true;
				}
				EQUCheck=true;						// 다시 돌아갈 함수에 'EQU'에 대해서 진행했다고 알리는 플래그.
			}
			else
				return -1;
		}
		else			// instruction이 OPTAB에 존재하는 경우.
		{
			if (inst.get(checkOp).getInstructionFormat().equals("2"))		// 2형식일 경우.
				isTwo=true;
			else if (inst.get(checkOp).getInstructionFormat().equals("1"))	// 1형식일 경우.
				isOne=true;
			
			if (isFour)								// 4형식 이라면, 다시 instruction을 '+'있는 상태로 바꿔 준다.
				opcode=tmpOp;
			buf1=sTokenizer.nextToken();			// operand를 가져온다.
			
			if (buf1.charAt(0)=='=')				// operand가 literal이라면,
			{
				if (checkLiteralTable(buf1))		// LITTAB에 중복 되는지를 확인하고, 없다면
				{
					literalPtr++;
					literalTable.get(programNumber).add(new Literal());
					literalTable.get(programNumber).get(literalPtr).setLiteral(buf1);		// LITTAB에 추가한다.
				}
				tokenTable.add(new Token());
				tokenTable.get(curLineNum).setLocationCounter(locationCounter);
				tokenTable.get(curLineNum).setComment("");
				tokenTable.get(curLineNum).setOperator(opcode);
				tokenTable.get(curLineNum).setOperand(new ArrayList<String>());
				tokenTable.get(curLineNum).getOperand().add(buf1);
			}
			else						// llteral이 아니라면,
			{
				tokenTable.add(new Token());
				tokenTable.get(curLineNum).setOperator(opcode);
				tokenTable.get(curLineNum).setOperand(new ArrayList<String>());
				tokenTable.get(curLineNum).setLocationCounter(locationCounter);
				
				if (!inst.get(checkOp).getOperNum().equals("0"))		//만약, operand 개수가 0이 아니라면,
					operandDivider(buf1,tokenTable.get(curLineNum).getOperand());	// operand 입력 받은것을 나누어서 넣는다.
				
				tokenTable.get(curLineNum).setComment("");
			}
		}
		
		if (isFour)						// 4형식 이라면,
		{
			locationCounter+=4;			// location counter를 4증가시키고,
			tokenTable.get(curLineNum).setInstructionFormat(4);			// control section 에서는 4형식을 쓰면 relocation문제 때문에 modification record를 써야 한다.
			modificationTable.add(new ArrayList<Modification>());
			modificationTable.get(programNumber).add(new Modification());
			modificationTable.get(programNumber).get(modiNum.get(programNumber)).
			setAddress(tokenTable.get(curLineNum).getLocationCounter());
			modificationTable.get(programNumber).get(modiNum.get(programNumber)).
			setChangeSize(5);
			modificationTable.get(programNumber).get(modiNum.get(programNumber)).
			setOperator('+');
			modificationTable.get(programNumber).get(modiNum.get(programNumber)).
			setIsOpcode(true);
			modificationTable.get(programNumber).get(modiNum.get(programNumber)).
			setModificationName(tokenTable.get(curLineNum).getOperand().get(0));
			modiNum.set(programNumber, modiNum.get(programNumber)+1);
		}
		else if (isTwo)					// 2형식 이라면,
		{
			locationCounter+=2;
			tokenTable.get(curLineNum).setInstructionFormat(2);
		}
		else if (isOne)					// 1형식 이라면,
		{
			locationCounter++;
			tokenTable.get(curLineNum).setInstructionFormat(1);
		}
		else							// 그 외의 경우는 3형식이다.
		{
			locationCounter+=3;
			tokenTable.get(curLineNum).setInstructionFormat(3);
		}
		return 0;
	}
	
	/*
	 * searchOpcode()
	 * : 매개로 명령어의 문자열을 받는다.
	 * : 매개로 받은 명령어를 OPTAB에서 찾아주는 함수이다. 만약, 4형식이라면 '+'부분을 지우고 OPTAB에서 찾는다.
	 * : 찾았다면 OPTAB에서의 줄 번호를 반환, 찾지 못했다면 -1반환, key가 null이라면 -2반환.
	 * 
	 */
	public static int searchOpcode(String key)
	{
		String temp=key;						// 부분 문자열을 취할 경우에 대비해서 복사해둔다.
		if (key==null)							// 매개로 받은 key자체가 null이면 -2 반환.
			return -2;
		if (key.charAt(0)=='+')					// key가 4형식이라면 부분 문자열을 취함.
			temp=key.substring(1);
		
		for (int i=0;i<instIndex;i++)			// OPTAB의 전체 instruction에 대해서,
		{
			if (temp.equals(inst.get(i).getInstructionName()))
				return i;						// instruction을 찾으면 바로 그 위치를 반환한다.
		}
		return -1;								// 찾지 못하면 -1을 반환한다.
	}
	
	/*
	 * literalOrganization()
	 * : pass1과정에서 LTORG나 END와 같은 directive를 만났을 때 실행되는 함수이다.
	 * : 값은 pass2에서 배정하므로, 이 함수에서는 location counter를 증가시키는 것이 주 목표이다.
	 * : 정상종료시 true반환, 비정상종료시 false반환.
	 * 
	 */
	public static boolean literalOrganization()
	{
		int temp;
		curLineNum++;						// tokenTable에 담기위해 tokenTable의 줄 번호 증가시킴.
		literalTable.get(programNumber).get(literalPtr).setAddress(locationCounter);
		
		if ((temp=literalIncrease(literalTable.get(programNumber).get(literalPtr).getLiteral()))<0)
			return false;					// 만약, 음수가 반환 된다면 false 반환.
		
		tokenTable.add(new Token());		// 토큰 객체 추가후, 값을 초기화 해준다.
		tokenTable.get(curLineNum).setComment("");
		tokenTable.get(curLineNum).setInstructionFormat(temp);
		tokenTable.get(curLineNum).setLabel("*");
		tokenTable.get(curLineNum).setOperator(literalTable.get(programNumber).get(literalPtr).getLiteral());
		tokenTable.get(curLineNum).setOperand(null);
		tokenTable.get(curLineNum).setLocationCounter(locationCounter);
		
		locationCounter+=temp;				// literal크기만큼 location counter 증가.
		literalPtr--;						// literal을 하나 생성하였으니 literalPtr 하나 감소.
		
		return true;
	}
	
	/*
	 * literalIncrease()
	 * : 매개로 operand의 내용을 문자열으로 받는다.
	 * : literalOrganization함수에서 location counter를 배정할 때, 얼마 크기만큼 location counter를 올려야 하는지를 str의 내용을 보고 알려주는 함수.
	 * : 문자열이면 그 크기만큼 반환, 16진수 표현이라면 길이의 절반을 반환, 그 외의 경우에는 한 WORD로 할당하기로 가정.
	 * 
	 */
	public static int literalIncrease(String str)
	{
		int start=2;
		int end=str.length()-1;
		
		if (str.charAt(1)=='C')					// 만약 문자형이라면,
			return end-start-1;					// 문자열 길이만큼 location counter 증가하게 함.
		else if (str.charAt(1)=='X')			// 16진수 표현이라면,
			return (end-start-1)/2;				// 길이의 절반만큼을 location counter 증가하게 한다.
		else
			return 3;							// 그 외의 경우에는 WORD라고 가정한다.
	}
	
	/*
	 * operandDivider()
	 * : 매개로 나누어지지 않은 문자열과, 나눈 문자열을 저장할 ArrayList를 받는다.
	 * : 나누어 지지 않은 문자열을 ArrayList에 넣어준다.
	 * : 나눈 토큰의 개수를 반환한다.
	 * 
	 */
	public static int operandDivider(String gradient,ArrayList<String> reservoir)
	{		
		String temp=gradient;
		String tbuff;
		int startPtr=0,endPtr=-1,numToken=0;
		
		for (int i=0;i<temp.length();i++)			// 나눌 문자열의 길이에 대해서,
		{
			if (temp.charAt(i)==',')				// ','기준으로 나눈다.
			{
				startPtr=endPtr+1;
				endPtr=i;
				tbuff=temp.substring(startPtr, endPtr);	// 부분 문자열을 취한 후,
				reservoir.add(tbuff);				// ArrayList에 저장한다.
				numToken++;							// 토큰 개수 증가.
			}
		}
		startPtr=endPtr+1;					// 마지막 부분은 아직 ArrayList에 담기지 않았으므로
		tbuff=temp.substring(startPtr);		// 그것들에 대해서 진행한다.
		reservoir.add(tbuff);
		numToken++;
		return numToken;					// 나눈 토큰의 개수 반환.
	}
	
	/*
	 * checkLiteralTable()
	 * : 매개로 literal이 들어온다.
	 * : pass1에서 LITTAB에 담을 경우, 중복 되는 literal이 있다면 저장하지 않고, 최초로 나온 literal이라면 LITTAB에 저장하는 함수.
	 * : 저장 가능하면 true, 불가능 하면 false 반환.
	 * 
	 */
	public static boolean checkLiteralTable(String inputLiteral)
	{
		for (int i=0;i<=literalPtr;i++)						// 현재 프로그램에 있는 모든 literal에 대해서 진행.
			if (literalTable.get(programNumber).get(i).getLiteral().equals(inputLiteral))		// 같은 것이 있다면,
				return false;		// false 반환.
		return true;		// 없다면 true 반환.
	}
}
