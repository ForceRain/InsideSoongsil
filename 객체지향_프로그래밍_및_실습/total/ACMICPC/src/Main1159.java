import java.util.Scanner;

class Operator
{
	private int []arr;
	private boolean ready;
	private String output;
	
	public Operator()
	{
		ready=false;
		arr=new int[26];
		output="";
	}
	public void push(char input)
	{
		arr[input-'a']++;
		
		if (arr[input-'a']==5)
		{
			ready=true;
		}
	}
	
	public boolean isOkay()
	{
		return ready;
	}
	
	public String toString()
	{
		for (int i=0;i<26;i++)
		{
			if (arr[i]==5)
			{
				//String tmp=Integer;
				output+=arr[i]+'a';
			}
		}
		return output;
	}
}

public class Main1159
{
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		int times=Integer.parseInt(in.nextLine());
		Operator obj=new Operator();
		
		for (int i=0;i<times;i++)
		{
			String tmp=in.nextLine();
			obj.push(tmp.charAt(0));
		}
		
		if (obj.isOkay())
			System.out.println(obj);
		else
			System.out.println("PREDAJA");
	}
}