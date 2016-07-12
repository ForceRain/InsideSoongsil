import java.util.ArrayList;

public class Token // 소스코드를 입력 받아서 나눠서 저장할 토큰 테이블.
{
	private int locationCounter;
	private int instructionFormat;
	private String label;
	private String operator;
	private ArrayList<String> operand;
	private String comment;
	public Token()		// 기본 생성자.
	{
		locationCounter=0;
		instructionFormat=-1;
		label="";
		operator="";
		operand=null;
		comment=null;
	}
	public Token(int locCtr,int instForm,String lb,String oper,ArrayList<String> oprnd,String cmt)
	{					// 매개변수를 받는 생성자.
		locationCounter=locCtr;
		instructionFormat=instForm;
		label=lb;
		operator=oper;
		operand=oprnd;
		comment=cmt;
	}			// 각 멤버 변수에 대해서 get,set 함수를 생성한다.
	public void setLocationCounter(int locCtr){
		locationCounter=locCtr;
	}
	public void setInstructionFormat(int inst){
		instructionFormat=inst;
	}
	public void setLabel(String lb){
		label=lb;
	}
	public void setOperator(String oper){
		operator=oper;
	}
	public void setOperand(ArrayList<String> opera){
		operand=opera;
	}
	public void setComment(String cmt){
		comment=cmt;
	}
	public int getLocationCounter(){
		return locationCounter;
	}
	public int getInstructionFormat(){
		return instructionFormat;
	}
	public String getLabel(){
		return label;
	}
	public String getOperator(){
		return operator;
	}
	public ArrayList<String> getOperand(){
		return operand;
	}
	public String getComment(){
		return comment;
	}
}

class Instruction				// 각 명령어에 대해서 이름, 형식, opcode 번호, 연산자 개수등의 정보를 담을 OPTAB을 위한 객체를 만들 클래스.
{
	private String instructionName;
	private String instructionFormat;
	private String opcode;
	private String operNum;
	private String comment;
	public Instruction()
	{
		instructionName="";
		instructionFormat="";
		opcode="";
		operNum="";
		comment="";
	}
	
	public Instruction(String iN,String iF,String op,String oN,String co)
	{
		instructionName=iN;
		instructionFormat=iF;
		opcode=op;
		operNum=oN;
		comment=co;
	}
	
	public String getInstructionName(){
		return instructionName;
	}
	public String getInstructionFormat(){
		return instructionFormat;
	}
	public String getOpcode(){
		return opcode;
	}
	public String getOperNum(){
		return operNum;
	}
	public String getComment(){
		return comment;
	}
}

class Symbol					// Symbol Table에 정보를 담을 객체를 위한 클래스.
{
	private String symbol;
	private int address;
	public Symbol()
	{
		symbol=null;
		address=0;
	}
	public Symbol(String in, int addr){
		symbol=in;address=addr;
	}
	public void setSymbol(String in){
		symbol=in;
	}
	public void setAddress(int addr){
		address=addr;
	}
	public String getSymbol(){
		return symbol;
	}
	public int getAddress(){
		return address;
	}
}

class Literal					// Literal Table에 정보를 담을 객체를 위한 클래스.
{
	private String literal;
	private int address;
	public Literal()	{
		literal="";
		address=0;
	}
	public Literal(String in,int addr){
		literal=in;address=addr;
	}
	public void setLiteral(String in){
		literal=in;
	}
	public void setAddress(int addr){
		address=addr;
	}
	public String getLiteral(){
		return literal;
	}
	public int getAddress(){
		return address;
	}
}

class Modification				// Modification record작성을 위해서 필요한 정보를 담을 객체를 위한 클래스.
{
	private String modificationName;
	private int address;
	private char operator;
	private boolean isOpcode;
	private int changeSize;
	public Modification()
	{
		modificationName="";
		address=0;
		operator=' ';
		isOpcode=false;
		changeSize=0;
	}
	public Modification(String mName,int addr,char op,boolean isOp,int cSize)
	{
		modificationName=mName;
		address=addr;
		operator=op;
		isOpcode=isOp;
		changeSize=cSize;
	}
	public void setModificationName(String mName){
		modificationName=mName;
	}
	public void setAddress(int addr){
		address=addr;
	}
	public void setOperator(char op){
		operator=op;
	}
	public void setIsOpcode(boolean isOp){
		isOpcode=isOp;
	}
	public void setChangeSize(int cSize){
		changeSize=cSize;
	}
	public String getModificationName(){
		return modificationName;
	}
	public int getAddress(){
		return address;
	}
	public char getOperator(){
		return operator;
	}
	public boolean getIsOpcode(){
		return isOpcode;
	}
	public int getChangeSize(){
		return changeSize;
	}
}