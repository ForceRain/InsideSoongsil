import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class SICXEAssemblerMain {
	private static final int MAX_OPERAND=3;
	
	private static ArrayList<Instruction> inst=new ArrayList<Instruction>();		// ���Ϸκ��� ���� ��ɾ ���� ���� OPTAB�� ArrayList�� ����.
	private static int instIndex=0;								// OPTAB�� ���̸� ��� �ִ� ����.
	
	private static ArrayList<String> inputData=new ArrayList<String>();		// ���Ϸκ��� �Է¹��� source code�� ���� ArrayList.
	private static int lineNum=0;								// source code�� ���� ���̸� ���� ����.
	private static int curLineNum=0;							// ��ü ���� �������� �� �پ��� ����Ű�� ������.
	
	private static ArrayList<Token> tokenTable=new ArrayList<Token>();		// source code�� �� ��ġ���� ����� ��Ƶ� tokenTable�� ����.
	private static int tokenLine=0;							// tokenTable�� ���̸� ��� �ִ� ����.
	
	private static ArrayList< ArrayList<Symbol> > symbolTable=new ArrayList< ArrayList<Symbol>>();		// symbol�� ��Ƶ� SYMTAB�� ����.
	private static ArrayList<Integer> labelNum=new ArrayList<Integer>();				// �� ���α׷� ���� symbol�� ������ �����ϱ� ���� ArrayList����.
	
	private static ArrayList< ArrayList<Literal> > literalTable=new ArrayList< ArrayList<Literal>>();	// literal�� ��Ƶ� LITTAB�� ����.
	private static ArrayList<Integer> literalNum=new ArrayList<Integer>();			// �� ���α׷� ���� literal�� ������ �����ϱ� ���� ArrayList����.
	private static int literalPtr=-1;
	
	private static ArrayList< ArrayList<String> > xrefTable=new ArrayList< ArrayList<String>>();		// �ܺ�  �����ϴ� symbol���� ��Ƶδ� table����.
	private static ArrayList<Integer> xrefNum=new ArrayList<Integer>();				// �� ���α׷� ���� �ܺ� ���� ������ ����� ArrayList����.
	private static ArrayList< ArrayList<String> > xdefTable=new ArrayList< ArrayList<String>>();		// �ܺ� ���Ǹ� �ϴ� symbol���� ��Ƶδ� table����.
	private static ArrayList<Integer> xdefNum=new ArrayList<Integer>();				// �� ���α׷� ���� �ܺ� ���� ������ ��Ƶ� ArrayList����.
	
	private static ArrayList< ArrayList<Modification>> modificationTable=new ArrayList< ArrayList<Modification>>();		// 4�����̳�, �ܺ� ������ �ϴ� Operand�鿡 ���ؼ� modification�� �ʿ��� symbol���� ��Ƶ�.
	private static ArrayList<Integer> modiNum=new ArrayList<Integer>();				// �� ���α׷� ���� modification record�� ������ ��Ƶ� ArrayList����.
	
	private static ArrayList<Integer> programLength=new ArrayList<Integer>();		// �� ���α׷� ���� ���̰� �������� �����ϴ� ArrayList.
	private static boolean lengthChecked=false;						// �� control section�� �����ų�, ���α׷��� �������� �ٴٶ��� ��, ���α׷� ���̸� �����ߴ��� Ȯ���ϴ� �÷���.
	private static int programNumber=0;								// ���α׷� ��ȣ�� �˷��ִ� ����.
	private static int locationCounter=0;							// location counter ������ �ϴ� ����.
	private static boolean subProgramStart=false;					// main routine�� �ƴ�, sub routine ���α׷��� ���۵� ��, �� ���α׷� �̸��� token table�� ��� ������ �ϰ� �ϴ� �÷���.
	private static boolean EQUCheck=false;						// label�� �Ҵ��ϴ� �κп���, EQU directive�� ������ location counter�� �Ҵ����� �ʰ� �ϴ� �÷���.
	private static boolean pcOrBase=true;						// true=pc-relative addressing, false=base-relative addressing�� �����ϴ� �÷���.
	
	private static Symbol baseRegister=new Symbol();			// base-relative addressing�� �� ��쿡 ���Ǵ� base register�� ����.
		
	private static final int A=0,X=1,L=2,B=3,S=4,T=5,F=6,PC=8,SW=9;		// SIC/XE machine�� �����ϴ� register�� ǥ��. �Ŀ� 2���� addressing�� �� �� ���ȴ�.
		
	
	/*
	 * void main()
	 * : assembler �ʱ�ȭ, pass1, pass2 ���� �� outputfile�� �����ϴ� ���� ����ϴ� �Լ��̴�.
	 * 
	 * */
	public static void main(String[] args)
	{
		if (initMyAssembler()<0)				// assembler �ʱ�ȭ.
		{
			System.out.println("initMyAssembler : ���α׷� �ʱ�ȭ�� ���� �߽��ϴ�.");
			return;
		}
		if (assemPass1()<0)						// pass1 ����.
		{
			System.out.println("assemPass1() : ���α׷� �ʱ�ȭ�� ���� �߽��ϴ�.");
			return;
		}
		
		if (assemPass2()<0)						// pass2 ����.
		{
			System.out.println("assemPass2() : ���α׷� �ʱ�ȭ�� ���� �߽��ϴ�.");
			return;
		}
		
		makeOutput("output.txt");				// intermediate file, object code file ����.
	}
	
	/*
	 * initMyAssembler()
	 * : ���Ͽ� �ִ� OPTAB�����, source code�� ������ ���α׷��� �ҷ��ͼ� ������ ArrayList�� �����ϴ� �Լ��̴�.
	 * : ��������� 0 ��ȯ, ������ ����� -1��ȯ.
	 * 
	 */
	public static int initMyAssembler()
	{
		int result;
		
		if ((result=initInstFile("inst.data.txt"))<0)			// OPTAB �ʱ�ȭ.
			return -1;
		if ((result=initInputFile("input.txt"))<0)				// source code �ʱ�ȭ.
			return -1;
		return result;
	}
	
	/*
	 * initInstFile()
	 * : �Ű��� ���� �̸��� �޴´�. �� ���Ͽ��� OPTAB�� ������ ���α׷��� ArrayList�� �����ϴ� �Լ��̴�.
	 * : ��������� 0 ��ȯ, ����������� -1��ȯ.
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
				iN=fScanner.next();								// ����,�� ������ ������ ���� �ִٰ� ������ ���� �����̴�.
				iF=fScanner.next();
				op=fScanner.next();
				oN=fScanner.next();
				com=fScanner.next();
				Instruction tmp=new Instruction(iN,iF,op,oN,com);
				inst.add(tmp);
				instIndex++;									// OPTAB�� ���� ���̸� ��Ÿ�� instIndex
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
	 * : �Ű��� ���� �̸��� �޴´�. �� ���Ͽ��� source code�� ������ �� �� ������ �о ArrayList�� �����Ѵ�.
	 * : ��������� 0��ȯ, ����������� -1��ȯ.
	 * 
	 */
	public static int initInputFile(String filename)
	{
		File file=new File(filename);
		String input="";
		try
		{
			Scanner fScanner=new Scanner(file);
			while (fScanner.hasNextLine())				// file�� ���� ���� �о� ���δ�.
			{
				input=fScanner.nextLine();
				inputData.add(input);
				lineNum++;								// ���������� source code�� �� ���� ������ �ȴ�.
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
	 * : assembler�� pass1������ �����ϴ� �Լ��̴�. ������ ������ �ٴٸ������� �����ϰ�, �� �پ� token parsing�� �����Ѵ�.
	 * : ��������� 0��ȯ, ����������� -1��ȯ.
	 * 
	 */
	public static int assemPass1()
	{
		String programName;
		String opcode;
		String startAddress;
		int idx=1;
		
		StringTokenizer sTokenizer=new StringTokenizer(inputData.get(0));		// source code�� ù ���� �о���δ�.
		programName=sTokenizer.nextToken();
		opcode=sTokenizer.nextToken();
		startAddress=sTokenizer.nextToken();
		
		if (opcode.equals("START"))													// ���� 'START'��� directive���ٸ�,
			locationCounter=Integer.parseInt(startAddress);							// operand�κ��� ���� location counter�� ���� ������ �����Ѵ�.
		else
			locationCounter=0;														// �ƴϾ��ٸ�, location counter�� ���� 0���� �ʱ�ȭ�Ѵ�.
		
		Token obj=new Token(locationCounter,-1,programName,opcode,null,null);		// ù ���� token table�� �����Ѵ�.
		tokenTable.add(obj);
		
		programNumber=0;
		curLineNum=1;
		literalNum.add(-1);
		labelNum.add(0); 
		literalPtr=-1;
		subProgramStart=false;
		baseRegister.setAddress(0);
		baseRegister.setSymbol(null);													// pass1���� ���� �������� �ʱ�ȭ�Ѵ�.
		
		symbolTable.add(new ArrayList<Symbol>());
		symbolTable.get(programNumber).add(new Symbol(programName,locationCounter));		// ù �ٿ��� program name�� location counter�� symbol table�� �����Ѵ�.
		labelNum.set(programNumber,labelNum.get(programNumber)+1);				
		
		literalTable.add(new ArrayList<Literal>());
		modificationTable.add(new ArrayList<Modification>());
		modiNum.add(0);
		
		while (tokenParsing(idx))													// source code�� ���� ���� �� ������ token�� parsing��.
		{
			idx++;
			curLineNum++;															// curLineNum�� tokenParsing�Լ� �̿ܿ����� ���� �����ϹǷ�, tokenParsing�� parameter�� �Ѱ��ָ� �ȵȴ�.
		}
		tokenLine=curLineNum;
		
		return 0;
	}
	
	/*
	 * assemPass2()
	 * : assembler�� pass2������ �����ϴ� �Լ��̴�. token���� ���� ���پ��� ���ؼ� �����ϸ�, addressing�� �����Ѵ�.
	 * : ��������� 0��ȯ, ����������� -1��ȯ.
	 * 
	 */
	public static int assemPass2()
	{
		String outputStream;
		int index,opcodeNumber,temp,opcodeCnt=0;
		boolean passOrNot=false,isLooping=false;
		programNumber=0;
		baseRegisterInitialize();						// base register�� ���� �Ǿ��� ��쿡 base ������ �����ִ� �Լ�.
		
		for (int i=0;i<tokenLine;i++)					// token table�� ������ ����.
		{
			index=searchOpcode(tokenTable.get(i).getOperator());		// opcode�� OPTAB���� ��ġ�� ���� ��ȣ�� ��ȯ.
			isLooping=false;
			opcodeCnt=0;
			pcOrBase=true;
			passOrNot=false;
			opcodeNumber=0;
			
			if (index>0)
			{
				opcodeNumber=Integer.parseInt(inst.get(index).getOpcode(),16);		// opcode�� string�����̹Ƿ� wrapper class�� �̿��ؼ� ���� �ִ´�.
				opcodeNumber+=indirectImmediate(i,index);
			}
			else if (index==-1)												// opcode�� �������� �ʴ� ���.
			{
				if (tokenTable.get(i).getOperator().charAt(0)=='=')			// literal�̶�� ���� �����Ѵ�.
					valuePacking(3,i,tokenTable.get(i).getOperator());
				else if (tokenTable.get(i).getOperator().equals("BYTE") 	// 'WORD','BYTE'�� ���ؼ��� ���� �����Ѵ�.
						|| tokenTable.get(i).getOperator().equals("WORD"))
					valuePacking(2,i,tokenTable.get(i).getOperand().get(0));
				else if (tokenTable.get(i).getOperator().equals("EQU"))		// 'EQU'�� ���ؼ��� ���� �����Ѵ�.
					equateRefine(i,tokenTable.get(i).getOperand().get(0));
				else if (tokenTable.get(i).getOperator().equals("CSECT"))	// 'CSECT'�� ���ؼ��� ���α׷� ��ȣ�� ������Ų��.
					programNumber++;
				passOrNot=true;										// �� ���ǹ��� ������ �ܰ迡���� addressing�۾��� ���� �ʱ� ���ؼ� �÷��� ����.
			}
			else
				passOrNot=true;				// �� ���� ���(�ּ�)���� addressing�۾��� ���� �ʴ´�.
			
			if (!passOrNot)					// addressing�۾��� �ϴ� �κ�.
			{
				if (tokenTable.get(i).getInstructionFormat()==1)		// 1�����̶��,
				{
					outputStream=Integer.toHexString(opcodeNumber).toUpperCase();	// 16������ �о���δ�.
					if (outputStream.length()!=2)
					{
						while (outputStream.length()!=2)
							outputStream="0"+outputStream;				// ���̸� �����ִ� �κ�.
					}					
					tokenTable.get(i).setComment(outputStream);
				}
				else if (tokenTable.get(i).getInstructionFormat()==2)	// 2�����̶��,
				{
					for (int k=0;k<tokenTable.get(i).getOperand().size();k++)
					{
						if (tokenTable.get(i).getOperand()!=null)
						{
							if (tokenTable.get(i).getOperand().get(k)!=null)
							{
								opcodeNumber=opcodeNumber<<4;			//  shift���� �� operand�ϳ� �ִ� ������� ����.
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
					if (opcodeCnt==1)			// ����, operand�� 1�����ٸ�, ���������� ���ؼ� shift������ �����ش�.
						opcodeNumber<<=4;
					
					outputStream=Integer.toHexString(opcodeNumber).toUpperCase();
					if (outputStream.length()!=4)
					{
						while (outputStream.length()!=4)	// ���� �պκ��� 0�̸�, ���ڷ� ����ϸ� �����ǹǷ�, ���������� 0�� �ٿ��ش�.
							outputStream="0"+outputStream;
					}
					tokenTable.get(i).setComment(outputStream);
				}
				else if (tokenTable.get(i).getInstructionFormat()==3 
						|| tokenTable.get(i).getInstructionFormat()==4)			// 3����,4�����̶��,
				{
					for (int j=0;j<tokenTable.get(i).getOperand().size();j++)
					{
						if (tokenTable.get(i).getOperand()!=null)
						{
							if (tokenTable.get(i).getOperand().get(j).equals("X"))
								isLooping=true;
						}
					}															// X register�� ���ؼ� looping�� �����ϴ����� Ȯ���Ѵ�.
					opcodeNumber<<=4;								// �޾ƿ� opcode��ȣ�� �� ����Ʈ ��ŭ shift�Ѵ�.
					
					if (isLooping)
						opcodeNumber+=8;							// looping������ �ϸ� 8�� ���ϰ�,
					if (tokenTable.get(i).getInstructionFormat()==4)
						opcodeNumber+=1;							// 4�����̶�� 1�� ���Ѵ�.
					
					if (tokenTable.get(i).getOperand()!=null && tokenTable.get(i).getOperand().size()>0)	// operand�� �ִ°��.
					{
						if (tokenTable.get(i).getOperand().get(0).charAt(0)=='#')				// immediate addressing�� ���.
						{
							if (Character.isDigit(tokenTable.get(i).getOperand().get(0).charAt(1)))	// ���ڷ� �ٷ� immediate addressing�� �����ϴ� ���.
							{
								temp=Integer.parseInt(tokenTable.get(i).getOperand().get(0).substring(1));
								if (tokenTable.get(i).getInstructionFormat()==4)			// 4�����̶��, 20bit shifting
									opcodeNumber<<=20;
								else														// 3�����̶��, 12bit shifting
									opcodeNumber<<=12;
								
								opcodeNumber|=temp;										// bitwise OR������ ���ؼ� opcodeNumber�� offset�� �ݿ��Ѵ�.
								outputStream=Integer.toHexString(opcodeNumber).toUpperCase();
							}
							else										// ������ immediate addressing�� �ϴ� ���.
							{
								temp=relativeOffset(tokenTable.get(i).getInstructionFormat(),
										tokenTable.get(i+1).getLocationCounter(),
										tokenTable.get(i).getOperand().get(0));		// ����ּҰ��� �����´�.
								if (tokenTable.get(i).getInstructionFormat()!=4)	// 4������ �ƴ� ��쿡��,
								{
									if (pcOrBase==true)			// pc-relative addressing�� ���.
										opcodeNumber+=2;
									else if (pcOrBase==false)	// base-relative addressing�� ���.
										opcodeNumber+=4;
									else
										return -1;
								}
								if (tokenTable.get(i).getInstructionFormat()==4)				// 4�����̶��, 20bit shifting
									opcodeNumber<<=20;
								else															// 3�����̶��, 12bit shifting
									opcodeNumber<<=12;
								
								opcodeNumber|=temp;
							}
						}
						else if (tokenTable.get(i).getOperand().get(0).charAt(0)=='@')		// indirect addressing�̶��,
						{
							temp=relativeOffset(tokenTable.get(i).getInstructionFormat(),	// �Ϲ� addressing�� ����ϹǷ� offset����� �Ѵ�.
									tokenTable.get(i+1).getLocationCounter(),
									tokenTable.get(i).getOperand().get(0).substring(1));
							if (tokenTable.get(i).getInstructionFormat()!=4)		// 4������ �ƴ϶��,
							{
								if (pcOrBase==true)			// pc-relative addressing�� ���.
									opcodeNumber+=2;
								else if (pcOrBase==false)	// base-relative addressing�� ���.
									opcodeNumber+=4;
								else
									return -1;
							}
							
							if (tokenTable.get(i).getInstructionFormat()==4)			// 4�����̶��, 20bit shifting
								opcodeNumber<<=20;
							else														// 3�����̶��, 12bit shifting
								opcodeNumber<<=12;
							
							opcodeNumber|=temp;
						}
						else					// �� ���� addressing�̶��,
						{
							temp=relativeOffset(tokenTable.get(i).getInstructionFormat()
									,tokenTable.get(i+1).getLocationCounter(),
									tokenTable.get(i).getOperand().get(0));
							if (tokenTable.get(i).getInstructionFormat()!=4)
							{
								if (pcOrBase==true)
									opcodeNumber+=2;				// ������ �����ֱ� ���ؼ� 0�� �߰�.
								else if (pcOrBase==false)
									opcodeNumber+=4;
								else
									return -1;
							}
							if (tokenTable.get(i).getInstructionFormat()==4)		// 4�����̶��, 20bit shifting
								opcodeNumber<<=20;
							else													// 3�����̶��, 12bit shifting
								opcodeNumber<<=12;
							
							opcodeNumber|=temp;
						}
					}
					else
					{
						if (tokenTable.get(i).getInstructionFormat()==4)	// 4�����̶��, 20bit shifting
							opcodeNumber<<=20;
						else							// 3�����̶��, 12bit shifting
							opcodeNumber<<=12;
					}
					
					if (tokenTable.get(i).getInstructionFormat()==4)		// 4���� �̶��,
					{
						outputStream=Integer.toHexString(opcodeNumber).toUpperCase();
						if (outputStream.length()!=8)
						{
							while (outputStream.length()!=8)
								outputStream="0"+outputStream;		// 8�ڸ� ������ �����ֱ� ���ؼ� 0�� �߰�.
						}
					}
					else						// 3���� �̶��,
					{
						outputStream=Integer.toHexString(opcodeNumber).toUpperCase();
						if (outputStream.length()!=6)
						{
							while (outputStream.length()!=6)
								outputStream="0"+outputStream;		// 6�ڸ� ������ �����ֱ� ���ؼ� 0�� �߰�.
						}
					}
					tokenTable.get(i).setComment(outputStream);		// comment��ġ�� ���յ� ���� �ڵ带 ����.
				}
			}
		}
		return 0;
	}
	
	/*
	 * relativeOffset()
	 * : �Ű��� ��ɾ� ����, ���� location counter�� ��, symbol�� �޴´�.
	 * : OPCODE�� n,i,x,b,p,e���� �÷��� �κ��� ������ addressing �κ��� ����ؼ� ��ȯ�ϴ� �Լ��̴�.
	 * : ��������� ����� address��,Ȥ�� 0 ��ȯ, ����������� -1��ȯ.
	 * 
	 */
	public static int relativeOffset(int format,int nextLocationCounter,String symbol)
	{
		int goAddr=getSymbolValue(programNumber,symbol);		// target addressing�� �� symbol �ּҸ� �޾ƿ´�.		
		int masking=1;						// address �κ��� masking�� ���ؼ� ��ȯ�ϱ� ���� ����.
		if (format==3){						// 3���� �̶�� 12bit�� masking�� �� �ְ� �����.
			for (int i=0;i<11;i++){
				masking<<=1;
				masking|=1;
			}
		}
		else{								// 4���� �̶�� 20bit�� masking�� �� �ְ� �����.
			for (int i=0;i<19;i++){
				masking<<=1;
				masking|=1;
			}
		}
		if (goAddr==-1){				// SYMTAB���� ã�� ������ ��쿡��, LITTAB���� ã�ƺ���.
			for (int i=0;i<=literalNum.get(programNumber);i++){
				if (literalTable.get(programNumber).size()>i)	{
					if (literalTable.get(programNumber).
							get(i).getLiteral().equals(symbol))	{
						int value=literalTable.get(programNumber).
								get(i).getAddress()-nextLocationCounter;
						int subValue=literalTable.get(programNumber).
								get(i).getAddress()-baseRegister.getAddress();
						if ((-2048<=value) && (value<=2048)){		// �� �� ������ offset�̶�� pc-relative
							pcOrBase=true;
							return (value&masking);
						}
						else if ((0<=subValue) && (subValue<=4096)){	// �� �� ������ offset�̶�� base-relative
							pcOrBase=false;
							return (subValue&masking);
						}
						else return -1;
					}
				}
			}
			return 0;
		}
		else if (goAddr==0)						// goAddr�� 0�̸� �ܺ� ������ �ϴ� symbol�̹Ƿ� ���� �𸣴� 0�� ��ȯ�Ѵ�.
			return 0;
		
		// �� ���� �Ϲ����� ��쿡�� ������ ���� �����Ѵ�.
		if ((-2048<=goAddr-nextLocationCounter) 
				&& (goAddr-nextLocationCounter<=2048))	{
			pcOrBase=true;				// ����� offset�� ���� ���̶�� pc-relative addressing.
			return (goAddr-nextLocationCounter)&masking;
		}
		else if ((0<=goAddr-baseRegister.getAddress()) 
				&& (goAddr-baseRegister.getAddress()<=4096))	{
			pcOrBase=false;				// ����� offset�� ���� ���̶�� base-relative addressing.
			return (goAddr-baseRegister.getAddress())&masking;
		}
		else return -1;
	}
	
	/*
	 * equateRefine()
	 * : �Ű��� tokenTable�� �� ��ȣ�� ���ڿ� �ϳ��� �޴´�.
	 * : pass2�� �����ϸ鼭 EQU directive�� ������, �װ��� operand�� symbol�� ���� ����ؼ� SYMTAB�� address�κп� �ݿ����ִ� �Լ��̴�.
	 * : ��������� 0��ȯ.
	 * 
	 */
	public static int equateRefine(int lineNumber,String str)
	{
		int strLength=str.length();
		char operator;
		int operatorPtr=0,op1,op2;
		int i=0;
		
		if (str.equals("*"))			// * ��� ���� location counter�� ���� symbol�� ������ �����ڴٴ� �ǹ�. �̹� pass1���� ���������Ƿ� ����� �ʴ´�.
			return 0;
		else	{
			while (!tokenTable.get(lineNumber).getLabel().
					equals(symbolTable.get(programNumber).get(i++).getSymbol()));
			i--;						// tokenTable���� �ݿ��� �ֱ����ؼ� tokenTable������ �� ��ȣ�� ã�´�.
			
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
				symbolTable.get(programNumber).get(i).setAddress(op1/op2);			// �� �����ڿ� ���� ������ �����ϰ�, SYMTAB�� address�� �ݿ��Ѵ�.
			
			tokenTable.get(lineNumber).setLocationCounter(symbolTable.get(programNumber).get(i).getAddress());	// tokenTable���� �����ϰ� �����Ѵ�.
		}
		return 0;
	}
	
	/*
	 * getSymbolValue()
	 * : �Ű��� ���α׷� ��ȣ, symbol�� �Է¹޴´�.
	 * : assembler�� pass1������ �����ϴ� �Լ��̴�. ������ ������ �ٴٸ������� �����ϰ�, �� �پ� token parsing�� �����Ѵ�.
	 * : ��������� �Ű��� ���� symbol�� address�� ��ȯ, ����������� -1��ȯ.
	 * 
	 */
	public static int getSymbolValue(int prNum,String symbol)
	{
		if (labelNum.size()>0)			// symbol�� ������ ��� �ϳ��� �ִ��� Ȯ��.
		{
			for (int i=0;i<labelNum.get(prNum);i++)			// ���α׷��� ��� symbol�� ���ؼ� ����.
			{
				if (symbolTable.get(prNum).get(i).getSymbol().equals(symbol))	// �Ű��� ���� symbol�� ���� symbol�� SYMTAB���� ã����,
					return symbolTable.get(prNum).get(i).getAddress();		// �� SYMTAB�� symbol�� address�� ��ȯ.
			}
		}
		
		if (xrefNum.size()>0)			// �ܺ� ������ ������ ��� �ϳ��� �ִ��� Ȯ��.
		{
			for (int i=0;i<xrefNum.get(prNum);i++)			// ���α׷��� ��� �ܺ� ������ ���ؼ� ����.
			{
				if (xrefTable.get(prNum).get(i).equals(symbol))		// ���� ���� symbol�� �ܺ� ���� table���� ã����,
					return 0;								// address���� �𸣴� 0�� ��ȯ.
			}
		}
		return -1;						// �� ã���� -1 ��ȯ.
	}
	
	/*
	 * baseRegisterInitialize()
	 * : pass2�����ϱ� ���� BASE directive�� pass1�� �߰ߵǾ��ٸ�, �װͿ� ���ؼ� base register �ʱ�ȭ ������ ��ġ�� �ϴ� �Լ��̴�.
	 * 
	 */
	public static void baseRegisterInitialize()
	{
		if (baseRegister.getSymbol()==null)	return;			// BASE directive�� �Ҵ� �� symbol�� ������ �׳� ��.
		else	baseRegister.setAddress(
				getSymbolValue(programNumber,baseRegister.getSymbol()));	
		// �ִٸ� �װ��� symbol�� ���� �ּ��� ���� baseRegister symbol�� address�� ���Ѵ�.
	}
	
	/*
	 * valuePacking()
	 * : �Ű��� ���� ���ʷ� �����ϴ� ���ڿ� ��ġ, tokenTable������ �� ��ȣ, ��ü ���ͷ� ���ڿ��� �޴´�.
	 * : literal�� ���� �����ϰų�, WORD�� BYTE���� ���� �����ϴ� ��쿡 ���̴� �Լ��̴�.
	 * : pass2���� ���̴� �Լ��̹Ƿ�, �ܺ� �����ϴ� symbol�� ���ؼ��� �����Ѵ�. 
	 * : symbol������ ���� ��������� �����ϴ�.
	 * : ��������� 0��ȯ.
	 * 
	 */
	public static int valuePacking(int startPos,int lineNumber,String str)
	{
		String tmpBuffer="";		// startPos�� ���� ���ʷ� �����ϴ� ��ġ�� ������ �ִ�.
		String strOp1="";
		String strOp2="";
		int end=str.length()-1;
		int thisLiteral=0,XCMul=1,tempVal=0,operatorPtr=0,result=0;
		char operator='+';
		int value,op1,op2;
		
		if (str.charAt(0)=='=')		thisLiteral=1;		// thisLiteral�� 1�̸� literal�� ���� ó��, 
		else	thisLiteral=2;			// 2��� WORD�� BYTE���� ���� �Ҵ��ϴ� ó��.
		
		if (str.charAt(startPos-2)=='X')	// ���� 16����ǥ���� ���� ���� �����ؾ� �Ѵٸ�,
		{
			XCMul=2;				// ������ ������ ���ڿ� ������ ���ݸ�ŭ�� location counter�� ������Ų��.
			value=Integer.parseInt(str.substring(startPos,end),16);		// 16������ �о� ���δ�.
			tempVal=value;
		}
		else if (str.charAt(startPos-2)=='C')		// ���� ���ڿ� ���� ���� �����ؾ� �Ѵٸ�,
		{
			for (int i=startPos;i<end;i++)
			{
				if (i!=startPos)
					result<<=8;
				result|=str.charAt(i);				// �� ����Ʈ ũ�⸸ŭ �������� shifting�ϸ鼭 �а� bitwise OR������ ���Ѵ�.
			}
			tempVal=result;
		}
		else if (Character.isDigit(str.charAt(startPos-2)))		// ���� ���ڿ� ���� literal�� ó���ؾ� �Ѵٸ�,
		{
			thisLiteral=0;
			tempVal=Integer.parseInt(str.substring(startPos-2));	// 16������ �о�鿩�� �ݿ��Ѵ�.
		}
		else
		{
			thisLiteral=0;
			while (Character.isAlphabetic(str.charAt(operatorPtr)) && operatorPtr<=end)
				operatorPtr++;							// �����ڸ� ����������, ���� �ٴٸ��� ������ Ž���Ѵ�.
			
			operator=str.charAt(operatorPtr);
			strOp1=str.substring(0,operatorPtr);
			op1=getSymbolValue(programNumber,strOp1);		// �� symbol�� ���ؼ� symbol�� �ּҸ� ã�Ƴ���.
			strOp2=str.substring(operatorPtr+1);
			op2=getSymbolValue(programNumber,strOp2);
			
			if (operator=='-')
				tempVal=op1-op2;
			else if (operator=='+')
				tempVal=op1+op2;
			else if (operator=='*')
				tempVal=op1*op2;
			else if (operator=='/')
				tempVal=op1/op2;						// �� �����ڿ� ���� ������ �����Ѵ�.
		}
		
		if (thisLiteral==1)	tokenTable.get(lineNumber).setInstructionFormat((end-3)/XCMul);
		else if (thisLiteral==2)	tokenTable.get(lineNumber).setInstructionFormat((end-2)/XCMul);
		else	tokenTable.get(lineNumber).setInstructionFormat(3);		// ����̳� object code����� �� �� ������ ���� ����ϱ� ���ؼ� ���� ����.
		
		tmpBuffer=Integer.toHexString(tempVal).toUpperCase();			// 16���� �빮��ȭ ��Ų��.
		if (tmpBuffer.length()!=tokenTable.get(lineNumber).getInstructionFormat()*2)	// ����� Ʋ�� ���� �ֱ� ���ؼ�
		{
			while (tmpBuffer.length()!=tokenTable.get(lineNumber).getInstructionFormat()*2)	// (������ ����*2)�� ���̰� �� ������ �պκп� '0'�� �߰��Ѵ�.
				tmpBuffer="0"+tmpBuffer;
		}
		tokenTable.get(lineNumber).setComment(tmpBuffer);			// comment��ġ�� �ϼ��� ���� �����Ѵ�.
		
		// �� �Ʒ� �κ��� �ܺ� ������ �ϴ� symbol�鿡 ���ؼ� modification record�� ���� ���� �����ϴ� ���ǹ�.
		if (xrefNum.size()>0)
		{
			// ù ��° �� �����ڿ� ���ؼ� �����Ѵ�.
			for (int j=0;j<xrefNum.get(programNumber);j++)
			{
				if (tokenTable.get(lineNumber).getOperator()!=null)
				{
					if (xrefTable.get(programNumber).get(j).equals(strOp1))	// ���� ���� �����ؾ� �ϴ� symbol�� �ܺ������� �ϴ� symbol�̶��,
					{
						modificationTable.get(programNumber).add(new Modification());		// modification table�� ���� symbol�� �̸��� ������, ��ġ���� ������ �����Ѵ�.
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
						get(modiNum.get(programNumber)).setOperator('+');		// ù ��° �� �������� ��ȣ�� +��� �����Ѵ�.
						modiNum.set(programNumber,modiNum.get(programNumber)+1);		// ���α׷��� �ܺ� ������ ���� �ϳ� ����.
					}
				}
			}
			// �ι�° �� �����ڿ� ���ؼ� �����Ѵ�.
			for (int j=0;j<xrefNum.get(programNumber);j++)
			{
				if (tokenTable.get(lineNumber).getOperator()!=null)
				{
					if (xrefTable.get(programNumber).get(j).equals(strOp2))
					{
						modificationTable.get(programNumber).add(new Modification());			// modification table�� ���� symbol�� �̸��� ������, ��ġ���� ������ �����Ѵ�.
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
						modiNum.set(programNumber,modiNum.get(programNumber)+1);		// ���α׷��� �ܺ� ������ ���� �ϳ� ����.
					}
				}
			}
		}
		return 0;
	}
	
	/*
	 * indirectImmediate()
	 * : �Ű��� tokenTable�� �ٹ�ȣ�� OPTAB������ opcode�� �ٹ�ȣ�� �޴´�.
	 * : indirect addressing����, immediate addressing������ �Ǻ��ؼ�, instruction �߾Ӻκ��� ���� ���� ��ȯ���ش�.
	 * : ��������� 0��ȯ, ����������� -1��ȯ.
	 * 
	 */
	public static int indirectImmediate(int lineNumber,int opcodeLine)
	{
		if (!inst.get(opcodeLine).getOperNum().equals("0"))			// operand�� �����ϴ� ���,
		{
			if (tokenTable.get(lineNumber).getInstructionFormat()==3 
					|| tokenTable.get(lineNumber).getInstructionFormat()==4)	// 3,4���Ŀ� ���ؼ�
			{
				if (tokenTable.get(lineNumber).getOperand().get(0).charAt(0)=='#')		// operandù�κ��� '#'���
					return 1;															// immediate addressing
				else if (tokenTable.get(lineNumber).getOperand().get(0).charAt(0)=='@')	// operand ù�κ��� '@'���
					return 2;															// indirect addressing
				else
					return 3;										// �� ���� ���� SIC/XE�� direct addressing�̴�.
			}
			else													// 1,2 ������ indirect, immediate�� �������� �����Ƿ� ����.
				return 0;
		}
		else														// operand�� ���°��,
		{
			if (tokenTable.get(lineNumber).getInstructionFormat()==3 
					|| tokenTable.get(lineNumber).getInstructionFormat()==4)	// 3�����̳�, 4�����̸� 3�� ��ȯ.
				return 3;
			else
				return 0;											// �� ���� ��쿡�� 0��ȯ.
		}
	}
	
	/*
	 * makeOutput()
	 * : �Ű��� ���� �̸��� �޴´�.
	 * : pass1, pass2 ������ ��ġ�� ª�� ���� �ڵ���� ������ intermediateFile�� ȭ�鿡 ����ϴ� �Լ��̴�.
	 * 
	 */
	public static void makeOutput(String filename)
	{
		boolean printed=false;						// operand��½�, ','�� ������� ������ �˷��ִ� �÷���.
		for (int i=0;i<curLineNum;i++)
		{
			printed=false;
			if (tokenTable.get(i).getInstructionFormat()!=0 || i==0)		// ��ɾ� ������ 0(���Ƿ� ����)�� �ƴϰų�, ù ���� ��쿡�� location counter�� ����Ѵ�.
				System.out.printf("%04X	",tokenTable.get(i).getLocationCounter());
			else
				System.out.printf("	");
			
			if (tokenTable.get(i).getLabel()!=null)						// label�� �ִٸ� label�� ����Ѵ�.
				System.out.printf("%s	", tokenTable.get(i).getLabel());
			else
				System.out.printf("	");
			
			if (tokenTable.get(i).getOperator()!=null)
				System.out.printf("%s	",tokenTable.get(i).getOperator());
			else
				System.out.printf("	");
			
			for (int j=0;j<MAX_OPERAND;j++)								// operand �ִ� ������ ���ؼ� �����Ѵ�.
			{
				if (tokenTable.get(i).getOperand()!=null && (tokenTable.get(i).getOperand().size()>j))	// operand�� �ְ�, j�� �� operand ������ ���� �ʾ��� ��쿡��
				{
					if (tokenTable.get(i).getOperand().get(j)!=null)
					{
						if (!printed)
						{																		// �� operand�� ����Ѵ�.
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
			
			if (tokenTable.get(i).getComment()!=null)							// comment�� �ִٸ� comment�� ����Ѵ�.
				System.out.printf("%s\n",tokenTable.get(i).getComment());
			else
				System.out.printf("\n");
		}
		if (filename!=null)								// �Ű��� ���� filename�� null�� �ƴ϶��,
			makeObjectcode(filename);					// object code�� ���Ͽ� ���� �Լ��� �����Ѵ�.
	}
	
	/*
	 * makeObjectcode()
	 * : �Ű��� �����̸��� �޴´�.
	 * : ª�� ���� �ڵ���� ������ object program���Ŀ� �°� ���� ���� ���� ������ ������ �Լ��̴�.
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
				
				temp=String.format("H%-6s",symbolTable.get(i).get(0).getSymbol());			// ���α׷� �̸�.
				fwriter.write(temp);
				temp=String.format("%06X",symbolTable.get(i).get(0).getAddress());			// ���α׷� ���� �ּ�.
				fwriter.write(temp);
				temp=String.format("%06X\r\n",programLength.get(i));					// ���α׷� ����.
				fwriter.write(temp);
				
				if (xdefNum.size()>i && xdefNum.get(i)!=0)			// �ܺ� ������ symbol�� ���� �κ�.
				{
					fwriter.write("D");
					for (int j=0;j<xdefNum.get(i);j++)
						fwriter.write(String.format("%-6s%06X",xdefTable.get(i).get(j),		// address�� symbol�� ���� ���´�.
								getSymbolValue(i,xdefTable.get(i).get(j))));
					fwriter.write("\r\n");			// ���� ��½�, '\r\n'�� ���ÿ� ������־�� �޸��忡�� �ݿ���. 
				}
				if (xrefNum.size()>i && xrefNum.get(i)!=0)			// �ܺ� ������ symbol�� ���� �κ�.
				{
					fwriter.write("R");
					for (int j=0;j<xrefNum.get(i);j++)
						fwriter.write(String.format("%-6s",xrefTable.get(i).get(j)));			// symbol�� ���´�.
					fwriter.write("\r\n");
				}
				
				while (true)				// object program�� text record�κ�.
				{
					objectCodeSum=0;
					befPtr=0;
					for (lineMax=0; (lineMax<=30) && (lineNumPtr<tokenLine);)		// �� ���� ����Ʈ ���� 30�� ���� �ʰ�, tokenTable�� ���� �ٴٸ��� �ʾ��� ���.
					{
						if (tokenTable.get(lineNumPtr).getOperator()!=null)
						{
							if (tokenTable.get(lineNumPtr).getOperator().equals("CSECT"))	// 'CSECT'�� ������ ���α׷��� �ٲ㼭 ���־����.
							{
								outPtr=1;
								break;
							}
							if (tokenTable.get(lineNumPtr).getOperator().equals("RESW") ||	// 'RESB','RESW'���� �����ϴ� �κж��� �ٹٲ��� ���־�� �Ѵ�.
									tokenTable.get(lineNumPtr).getOperator().equals("RESB"))
							{
								reserved=1;
								break;
							}
						}
						if (tokenTable.get(lineNumPtr).getComment()!=null)					// comment�κп� pass2���� ������ ���� �ڵ���� �ִ�.
						{
							lineMax+=tokenTable.get(lineNumPtr).getInstructionFormat();
							befPtr=tokenTable.get(lineNumPtr).getInstructionFormat();
						}
						lineNumPtr++;
					}
					
					if (lineMax>30){		// ����, �� �ݺ����� 30����Ʈ�� �Ѱܼ� ���� �Ǿ��ٸ�,
						lineNumPtr--;		// tokenTable�����ڸ� �ϳ� ���̰�,
						objectCodeSum=(lineMax-befPtr);		// �� ũ�⸸ŭ �� �ڵ� ũ�⵵ ���δ�.
					}
					else					// �׷��� �ʴٸ� 
						objectCodeSum=lineMax;		// �״�� �Ҵ��Ѵ�.
					
					if (lineNumPtr>=tokenLine)
						outPtr=1;			// tokenTable�� ���� �ٴٸ��� while�� Ż��.
					
					fwriter.write(String.format("T%06X",tokenTable.
							get(lineStartPtr).getLocationCounter()));		// ���� �ּ� ���.
					fwriter.write(String.format("%02X",objectCodeSum));							// text record�� �ڵ� ���� ���.
					for (int j=lineStartPtr;j<lineNumPtr;j++){
						if (tokenTable.get(j).getComment()!=null)
							fwriter.write(String.format("%s",tokenTable.get(j).getComment()));
					}
					fwriter.write("\r\n");
					
					if (reserved==1)			// 'RES*'���� directive�� ������, �̰͵��� �Ĺݺο� ����Ǿ��ٰ� ������ ��, �����ڰ� 'CSECT' directive�� ã���� �Ѵ�.
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
					if (outPtr==1)				// while�� ���� ����. ���� ���α׷��� �� �غ�.
						break;
				}
				if (lineNumPtr<tokenLine)
					while (tokenTable.get(lineNumPtr).getComment()==null)	lineNumPtr++;
				lineStartPtr=lineNumPtr;			// comment�κ��� ������� �����ϰ�, comment�κп� ���� ���� ������ �����ڸ� �ű��.
				
				for (int j=0;j<modiNum.get(i);j++)		// modification record�� ���ؼ� ����.
				{
					fwriter.write("M");
					if (modificationTable.get(i).get(j).getIsOpcode())			// instruction�� ���ؼ��� location counter�� ���� +1(���� �ڵ� �Ӹ��κ��� 2����Ʈ ���� �ٲ���� ��.)
						fwriter.write(String.format("%06X",modificationTable.get(i).get(j).getAddress()+1));
					else
						fwriter.write(String.format("%06X",modificationTable.get(i).get(j).getAddress()));
					fwriter.write(String.format("%02X",modificationTable.get(i).get(j).getChangeSize()));
					fwriter.write(String.format("%C",modificationTable.get(i).get(j).getOperator()));
					fwriter.write(String.format("%-6s\r\n",modificationTable.get(i).get(j).getModificationName()));
				}
				if (i==0)			// ù ��°�ο� main program�̾��ٸ� �����ּҸ� end record�� ���.
					fwriter.write(String.format("E%06X\r\n",symbolTable.get(i).get(0).getAddress()));
				else				// sub program�̾��ٸ� �����ּҸ� ������� ����.
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
	 * : �Ű��� inputData�� �� ��ȣ�� ���´�.
	 * : inputData�� �Է��� ������ �������� �����Ƿ� �װͿ� ���� ó���� �ϴ� �Լ�.
	 * : ��������� true��ȯ, ����������� false��ȯ.
	 * 
	 */
	public static boolean tokenParsing(int idx)
	{
		String stringBuffer;
		String tmp;
		int temp;
		
		if (idx>=lineNum)									// parsing �������� source code �ټ��� �Ѿ�ٸ� ��.
			return false;
		
		StringTokenizer sTokenizer=new StringTokenizer(inputData.get(idx));		// source code�� ���� stringTokenizer��ü�� ��´�.
		stringBuffer=sTokenizer.nextToken();				// ���� ó�� ��ū�� �о� �´�.
		
		if ((searchOpcode(stringBuffer))>=0)				// �о� �� ���� instruction�̶��,
		{
			if (parseOpcode(stringBuffer,idx)<0)
				return false;
			if (subProgramStart)							// main�� �ƴ� sub program�� �����ϴ� ���̶��,
			{
				String tp=symbolTable.get(programNumber).get(0).getSymbol();
				tokenTable.get(curLineNum).setLabel(tp);
				subProgramStart=false;
			}
			else
				tokenTable.get(curLineNum).setLabel("");
		}
		else if (inputData.get(idx).charAt(0)=='.')			// �о� �� ���� ���� ù ���ڰ� '.'�̶�� comment�� �����Ѵ�.
		{
			tokenTable.add(new Token());
			tokenTable.get(curLineNum).setLabel(inputData.get(idx));
			tokenTable.get(curLineNum).setComment(null);
			tokenTable.get(curLineNum).setInstructionFormat(0);
			tokenTable.get(curLineNum).setLocationCounter(0);
			tokenTable.get(curLineNum).setOperand(null);
			tokenTable.get(curLineNum).setOperator(null);
		}
		else if (stringBuffer.equals("END"))				// 'END' directive���,
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
			while (literalPtr>=0)							// LITTAB�� ��Ƶ� literal��θ� tokenTable�� �Ҵ��Ѵ�.
				literalOrganization();
			
			if (!lengthChecked)								// ���α׷� ���̸� üũ���� �ʾҴٸ�,
			{
				programLength.add(locationCounter);			// programLength�� ���� �ִ´�.
				lengthChecked=true;
			}
			return true;
		}
		else if (stringBuffer.equals("LTORG"))				// 'LTORG' directive���,
		{
			tokenTable.add(new Token());
			tokenTable.get(curLineNum).setLabel("");
			tokenTable.get(curLineNum).setInstructionFormat(0);
			tokenTable.get(curLineNum).setComment("");
			tokenTable.get(curLineNum).setOperand(null);
			tokenTable.get(curLineNum).setLocationCounter(0);
			tokenTable.get(curLineNum).setOperator(stringBuffer);
			
			literalNum.set(programNumber,literalPtr);
			
			while (literalPtr>=0)							// 'LTORG'�� ȣ�� �Ǳ� �������� LITTAB�� ��Ƶ� literal��θ� tokenTable�� �Ҵ��Ѵ�.
				literalOrganization();
		}
		else if (stringBuffer.equals("EXTDEF"))				// 'EXTDEF'���,
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
					xdefTable.get(programNumber)));			// ������ ������, ArrayList�� �����ڸ� ����� ��´�.
		}
		else if (stringBuffer.equals("EXTREF"))				// 'EXTREF'���,
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
					xrefTable.get(programNumber)));			// ������ ������, ArrayList�� �����ڸ� ����� ��´�.
		}
		else if (stringBuffer.equals("BASE"))				// 'BASE'���,
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
			baseRegister.setSymbol(tmp);					// symbol�� baseRegister������ �Ҵ��Ѵ�.
		}
		else												// �� ���� ���� label�̶�� �����Ѵ�.
		{
			tmp=sTokenizer.nextToken();						// ���� ��ū�� �о�´�.
			temp=locationCounter;							// parseOpcode�������� location counter�� ���� �� �����Ƿ� �̸� �����صд�.

			if (tmp.equals("CSECT"))						// �� ���� ��ū�� 'CSECT'���,
			{
				tokenTable.add(new Token());
				tokenTable.get(curLineNum).setLabel(stringBuffer);
				tokenTable.get(curLineNum).setLocationCounter(0);
				tokenTable.get(curLineNum).setOperand(null);
				tokenTable.get(curLineNum).setComment(null);
				tokenTable.get(curLineNum).setInstructionFormat(0);
				tokenTable.get(curLineNum).setOperator(tmp);
				
				if (!lengthChecked)							// ���α׷� ���̸� üũ���� �ʾҴٸ�,
				{
					programLength.add(locationCounter);		// programLength�� ���� ���α׷� ���̸� �����Ѵ�.
					lengthChecked=true;
				}
				
				locationCounter=0;
				programNumber++;
				labelNum.add(1);
				literalPtr=-1;
				lengthChecked=false;
				subProgramStart=true;
				symbolTable.add(new ArrayList<Symbol>());
				symbolTable.get(programNumber).add(new Symbol(stringBuffer,locationCounter));		// ���� ���α׷��� symbol�� ������ ArrayList.		
				literalTable.add(new ArrayList<Literal>());
				literalNum.add(0);
				modificationTable.add(new ArrayList<Modification>());
				modiNum.add(0);
														// ���ο� ���α׷��� �����ϱ� ���� flag���� ��, ���� �Ҵ� ����.
				return true;
			}
			else											// CSECT�� �ƴϾ��ٸ�, 
			{
				symbolTable.get(programNumber).add(new Symbol());				// label�� �����ϱ� ���� symbol��ü�� �ϳ� �÷��ش�.
				labelNum.set(programNumber,labelNum.get(programNumber)+1);		// label���� ����.
				if (parseOpcode(tmp,idx)<0)									// opcode�� ���� tokenTable�� �����Ѵ�.
					return false;		
				
				symbolTable.get(programNumber).get(labelNum.get(programNumber)-1).setSymbol(stringBuffer);	// ���� �߰��� symbol�� �̸� ����.
				if (EQUCheck)
					EQUCheck=false;							// 'EQU' directive���ٸ� ���� ���� symbol�� location counter�� �Ҵ����� �ʴ´�.
				else
					symbolTable.get(programNumber).get(labelNum.get(programNumber)-1).setAddress(temp);	// �� ���� ��쿡�� ���� ���� symbol�� location counter�� �Ҵ��Ѵ�.
						
				tokenTable.get(curLineNum).setLabel(stringBuffer);			// label�� �����Ѵ�.
			}
		}
		return true;
	}
	
	/*
	 * parseOpcode()
	 * : �Ű��� ��ɾ ���ڿ����� �ް�, inputData�� �� ��ȣ�� index�� �޴´�.
	 * : ��������� 0��ȯ, ����������� -1��ȯ.
	 * 
	 */
	public static int parseOpcode(String opcode,int index)
	{
		String buf1,tmpOp="";
		StringTokenizer sTokenizer=new StringTokenizer(inputData.get(index));
		boolean isFour=false,isTwo=false,isOne=false;
		int checkOp;
		
		while (!(opcode.equals(sTokenizer.nextToken())));
		
		if (opcode.charAt(0)=='+')				// ����, instruction�� 4���� �̶��,
		{
			isFour=true;
			tmpOp=opcode;
			opcode=opcode.substring(1);			// �κ� ���ڿ��� ���Ѵ�.
		}
		checkOp=searchOpcode(opcode);			// OPTAB���� instruction�� �ش� �ϴ� OPCODE�� ã�´�.
		
		if (checkOp<0)							// instruction�� OPTAB�� ���� ���. directive�� ���.
		{
			if (opcode.equals("WORD") || opcode.equals("RESW")
					|| opcode.equals("RESB")|| opcode.equals("BYTE"))
			{
				buf1=sTokenizer.nextToken();				// operand�� ���̳�, ������ ��������Ƿ� �о�´�.
				
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
				return 0;						// �� directive�� ���� location counter �������� �޸��Ѵ�.
			}
			else if (opcode.equals("EQU"))			// 'EQU' directive���,
			{
				buf1=sTokenizer.nextToken();		// operand�κп� symbol�̳� '*'�� �־��� �� �ִ�.
				
				tokenTable.add(new Token());
				tokenTable.get(curLineNum).setComment("");
				tokenTable.get(curLineNum).setOperator(opcode);
				tokenTable.get(curLineNum).setOperand(new ArrayList<String>());
				tokenTable.get(curLineNum).getOperand().add(buf1);
				
				if (buf1.equals("*"))				// '*'�̶��, ���� location counter�� ���� symbol�� �Բ� SYMTAB�� �����Ѵ�.
				{
					tokenTable.get(curLineNum).setLocationCounter(locationCounter);
					symbolTable.get(programNumber).get(labelNum.get(programNumber)-1).setAddress(locationCounter);
				}
				else								// �׷��� �ʴٸ�, pass1������ ���� �������� �ʴ´�.
				{
					tokenTable.get(curLineNum).setLocationCounter(0);
					symbolTable.get(programNumber).get(labelNum.get(programNumber)-1).setAddress(0);
				}
				
				if (!lengthChecked)					// 'EQU'�� ���α׷� ���� �������� �־����ٰ� ������ ������.
				{									// SYMTAB�ȿ��� ������ location counter�������� ������ ���� ������ �������̶�� �����ϰ� ���̸� �����Ѵ�.
					programLength.add(locationCounter);
					lengthChecked=true;
				}
				EQUCheck=true;						// �ٽ� ���ư� �Լ��� 'EQU'�� ���ؼ� �����ߴٰ� �˸��� �÷���.
			}
			else
				return -1;
		}
		else			// instruction�� OPTAB�� �����ϴ� ���.
		{
			if (inst.get(checkOp).getInstructionFormat().equals("2"))		// 2������ ���.
				isTwo=true;
			else if (inst.get(checkOp).getInstructionFormat().equals("1"))	// 1������ ���.
				isOne=true;
			
			if (isFour)								// 4���� �̶��, �ٽ� instruction�� '+'�ִ� ���·� �ٲ� �ش�.
				opcode=tmpOp;
			buf1=sTokenizer.nextToken();			// operand�� �����´�.
			
			if (buf1.charAt(0)=='=')				// operand�� literal�̶��,
			{
				if (checkLiteralTable(buf1))		// LITTAB�� �ߺ� �Ǵ����� Ȯ���ϰ�, ���ٸ�
				{
					literalPtr++;
					literalTable.get(programNumber).add(new Literal());
					literalTable.get(programNumber).get(literalPtr).setLiteral(buf1);		// LITTAB�� �߰��Ѵ�.
				}
				tokenTable.add(new Token());
				tokenTable.get(curLineNum).setLocationCounter(locationCounter);
				tokenTable.get(curLineNum).setComment("");
				tokenTable.get(curLineNum).setOperator(opcode);
				tokenTable.get(curLineNum).setOperand(new ArrayList<String>());
				tokenTable.get(curLineNum).getOperand().add(buf1);
			}
			else						// llteral�� �ƴ϶��,
			{
				tokenTable.add(new Token());
				tokenTable.get(curLineNum).setOperator(opcode);
				tokenTable.get(curLineNum).setOperand(new ArrayList<String>());
				tokenTable.get(curLineNum).setLocationCounter(locationCounter);
				
				if (!inst.get(checkOp).getOperNum().equals("0"))		//����, operand ������ 0�� �ƴ϶��,
					operandDivider(buf1,tokenTable.get(curLineNum).getOperand());	// operand �Է� �������� ����� �ִ´�.
				
				tokenTable.get(curLineNum).setComment("");
			}
		}
		
		if (isFour)						// 4���� �̶��,
		{
			locationCounter+=4;			// location counter�� 4������Ű��,
			tokenTable.get(curLineNum).setInstructionFormat(4);			// control section ������ 4������ ���� relocation���� ������ modification record�� ��� �Ѵ�.
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
		else if (isTwo)					// 2���� �̶��,
		{
			locationCounter+=2;
			tokenTable.get(curLineNum).setInstructionFormat(2);
		}
		else if (isOne)					// 1���� �̶��,
		{
			locationCounter++;
			tokenTable.get(curLineNum).setInstructionFormat(1);
		}
		else							// �� ���� ���� 3�����̴�.
		{
			locationCounter+=3;
			tokenTable.get(curLineNum).setInstructionFormat(3);
		}
		return 0;
	}
	
	/*
	 * searchOpcode()
	 * : �Ű��� ��ɾ��� ���ڿ��� �޴´�.
	 * : �Ű��� ���� ��ɾ OPTAB���� ã���ִ� �Լ��̴�. ����, 4�����̶�� '+'�κ��� ����� OPTAB���� ã�´�.
	 * : ã�Ҵٸ� OPTAB������ �� ��ȣ�� ��ȯ, ã�� ���ߴٸ� -1��ȯ, key�� null�̶�� -2��ȯ.
	 * 
	 */
	public static int searchOpcode(String key)
	{
		String temp=key;						// �κ� ���ڿ��� ���� ��쿡 ����ؼ� �����صд�.
		if (key==null)							// �Ű��� ���� key��ü�� null�̸� -2 ��ȯ.
			return -2;
		if (key.charAt(0)=='+')					// key�� 4�����̶�� �κ� ���ڿ��� ����.
			temp=key.substring(1);
		
		for (int i=0;i<instIndex;i++)			// OPTAB�� ��ü instruction�� ���ؼ�,
		{
			if (temp.equals(inst.get(i).getInstructionName()))
				return i;						// instruction�� ã���� �ٷ� �� ��ġ�� ��ȯ�Ѵ�.
		}
		return -1;								// ã�� ���ϸ� -1�� ��ȯ�Ѵ�.
	}
	
	/*
	 * literalOrganization()
	 * : pass1�������� LTORG�� END�� ���� directive�� ������ �� ����Ǵ� �Լ��̴�.
	 * : ���� pass2���� �����ϹǷ�, �� �Լ������� location counter�� ������Ű�� ���� �� ��ǥ�̴�.
	 * : ��������� true��ȯ, ����������� false��ȯ.
	 * 
	 */
	public static boolean literalOrganization()
	{
		int temp;
		curLineNum++;						// tokenTable�� ������� tokenTable�� �� ��ȣ ������Ŵ.
		literalTable.get(programNumber).get(literalPtr).setAddress(locationCounter);
		
		if ((temp=literalIncrease(literalTable.get(programNumber).get(literalPtr).getLiteral()))<0)
			return false;					// ����, ������ ��ȯ �ȴٸ� false ��ȯ.
		
		tokenTable.add(new Token());		// ��ū ��ü �߰���, ���� �ʱ�ȭ ���ش�.
		tokenTable.get(curLineNum).setComment("");
		tokenTable.get(curLineNum).setInstructionFormat(temp);
		tokenTable.get(curLineNum).setLabel("*");
		tokenTable.get(curLineNum).setOperator(literalTable.get(programNumber).get(literalPtr).getLiteral());
		tokenTable.get(curLineNum).setOperand(null);
		tokenTable.get(curLineNum).setLocationCounter(locationCounter);
		
		locationCounter+=temp;				// literalũ�⸸ŭ location counter ����.
		literalPtr--;						// literal�� �ϳ� �����Ͽ����� literalPtr �ϳ� ����.
		
		return true;
	}
	
	/*
	 * literalIncrease()
	 * : �Ű��� operand�� ������ ���ڿ����� �޴´�.
	 * : literalOrganization�Լ����� location counter�� ������ ��, �� ũ�⸸ŭ location counter�� �÷��� �ϴ����� str�� ������ ���� �˷��ִ� �Լ�.
	 * : ���ڿ��̸� �� ũ�⸸ŭ ��ȯ, 16���� ǥ���̶�� ������ ������ ��ȯ, �� ���� ��쿡�� �� WORD�� �Ҵ��ϱ�� ����.
	 * 
	 */
	public static int literalIncrease(String str)
	{
		int start=2;
		int end=str.length()-1;
		
		if (str.charAt(1)=='C')					// ���� �������̶��,
			return end-start-1;					// ���ڿ� ���̸�ŭ location counter �����ϰ� ��.
		else if (str.charAt(1)=='X')			// 16���� ǥ���̶��,
			return (end-start-1)/2;				// ������ ���ݸ�ŭ�� location counter �����ϰ� �Ѵ�.
		else
			return 3;							// �� ���� ��쿡�� WORD��� �����Ѵ�.
	}
	
	/*
	 * operandDivider()
	 * : �Ű��� ���������� ���� ���ڿ���, ���� ���ڿ��� ������ ArrayList�� �޴´�.
	 * : ������ ���� ���� ���ڿ��� ArrayList�� �־��ش�.
	 * : ���� ��ū�� ������ ��ȯ�Ѵ�.
	 * 
	 */
	public static int operandDivider(String gradient,ArrayList<String> reservoir)
	{		
		String temp=gradient;
		String tbuff;
		int startPtr=0,endPtr=-1,numToken=0;
		
		for (int i=0;i<temp.length();i++)			// ���� ���ڿ��� ���̿� ���ؼ�,
		{
			if (temp.charAt(i)==',')				// ','�������� ������.
			{
				startPtr=endPtr+1;
				endPtr=i;
				tbuff=temp.substring(startPtr, endPtr);	// �κ� ���ڿ��� ���� ��,
				reservoir.add(tbuff);				// ArrayList�� �����Ѵ�.
				numToken++;							// ��ū ���� ����.
			}
		}
		startPtr=endPtr+1;					// ������ �κ��� ���� ArrayList�� ����� �ʾ����Ƿ�
		tbuff=temp.substring(startPtr);		// �װ͵鿡 ���ؼ� �����Ѵ�.
		reservoir.add(tbuff);
		numToken++;
		return numToken;					// ���� ��ū�� ���� ��ȯ.
	}
	
	/*
	 * checkLiteralTable()
	 * : �Ű��� literal�� ���´�.
	 * : pass1���� LITTAB�� ���� ���, �ߺ� �Ǵ� literal�� �ִٸ� �������� �ʰ�, ���ʷ� ���� literal�̶�� LITTAB�� �����ϴ� �Լ�.
	 * : ���� �����ϸ� true, �Ұ��� �ϸ� false ��ȯ.
	 * 
	 */
	public static boolean checkLiteralTable(String inputLiteral)
	{
		for (int i=0;i<=literalPtr;i++)						// ���� ���α׷��� �ִ� ��� literal�� ���ؼ� ����.
			if (literalTable.get(programNumber).get(i).getLiteral().equals(inputLiteral))		// ���� ���� �ִٸ�,
				return false;		// false ��ȯ.
		return true;		// ���ٸ� true ��ȯ.
	}
}
