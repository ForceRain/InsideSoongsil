import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Bank 
{
	private ArrayList<BankAccount> array;
	
	public Bank()
	{
		array=new ArrayList<BankAccount>();
	}
	
	public void addAccount(BankAccount obj)
	{
		array.add(obj);
	}
	
	public void readFile(String input) throws Exception 
	{
		FileReader read=new FileReader(input+".txt");
		Scanner in=new Scanner(read);
		
		while (in.hasNext())
		{
			int tmp=Integer.parseInt(in.next());
			double tmp1=Double.parseDouble(in.next());
			
			BankAccount obj=new BankAccount(tmp,tmp1);
			
			addAccount(obj);
		}
		
		read.close();
		in.close();
	}
	
	public BankAccount getHighestBalance()
	{
		BankAccount tmp=new BankAccount(-1,0);
		
		for (int i=0;i<array.size();i++)
		{
			if (tmp.getBalance()<array.get(i).getBalance())
				tmp=array.get(i);
		}
		
		return tmp;
	}
}
