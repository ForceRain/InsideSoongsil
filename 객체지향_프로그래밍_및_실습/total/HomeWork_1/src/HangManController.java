import java.util.ArrayList;
import java.util.Random;
import java.lang.StringBuilder;

class HangManController {
	private ArrayList<String>Data; 
	private Random RandNum;
	private HangManPrinter Printer;
	
	private int Rand_num_idx;
	private int Data_num;
	private int Wrong_num;
	private String Ques;
	private char[] storage;
	private int storageIdx;
	
	public HangManController()
	{
		Data=new ArrayList<String>();
		Printer=new HangManPrinter();
		RandNum=new Random();
		Data_num=0;
		Rand_num_idx=0;
		storage=new char[5];
		storageIdx=0;
	}

	public void SetSize(int input)
	{
		Data_num=input;
	}
	
	public void SetData(String input)
	{
		Data.add(input);
	}
	
	public String RandGetData()
	{
		Rand_num_idx=RandNum.nextInt(Data_num);
		return Data.get(Rand_num_idx);
	}

	public void showString()
	{
		for (int i=0;i<Ques.length();i++)
		{
			System.out.print(Ques.charAt(i));
			System.out.print(" ");
		}
		System.out.println();
	}
	
	public String toString()
	{
		return Printer.ShowHangMan(Wrong_num);
	}
	
	public boolean StartGame()
	{
		Wrong_num=0;
		if (Data_num==0)
		{
			System.out.println("Undefined Access");
			return false;
		}
		String temp=RandGetData();
		StringBuilder build=new StringBuilder(temp);
		
		for (int i=0;i<temp.length();i++)				//you should check here
		{
			build.setCharAt(i,'_');
		}
		
		Ques=build.toString();
		return true;
	}
	
	public boolean CheckSame(String input)
	{
		for (int i=0;i<storageIdx;i++)
		{
			if (storage[i]==input.charAt(0))
				return true;
		}
		return false;
	}
	
	public void ShowChar()
	{
		System.out.print("Already give :");
		for (int i=0;i<storageIdx;i++)
		{
			System.out.print(storage[i]+" ");
		}
		System.out.println();
	}
	
	public boolean CheckChar(String input)
	{
		//ques String should be checked by input
		if (CheckSame(input))
		{
			return false;
		}
		
		storage[storageIdx++]=input.charAt(0);
		
		int right=0;
		StringBuilder build=new StringBuilder(Ques);
		
		for (int i=0;i<Ques.length();i++)
		{
			if (Data.get(Rand_num_idx).charAt(i)==input.charAt(0))
			{
				build.setCharAt(i, input.charAt(0));
				right++;
			}
		}
		Ques=build.toString();
		
		if (right==0)
			Wrong_num++;
		
		return true;
	}
	
	public boolean CheckAns()
	{
		if (Ques.equals(Data.get(Rand_num_idx)))
			return true;
		else
			return false;
	}
	
	public boolean IsOut(String input)
	{
		if (input.equals("n") || input.equals("N"))
			return true;
		else
			return false;
	}
}
