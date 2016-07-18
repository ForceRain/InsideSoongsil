import java.io.PrintWriter;
import java.lang.Exception;
import java.util.Scanner;

public class Question1 
{
	public static void main(String[] args)
	{
		try
		{
			System.out.println("Please type Filename :");
			Scanner keyboard=new Scanner(System.in);
			String input=keyboard.nextLine();
			
			PrintWriter obj=new PrintWriter(input+".txt");
		
			obj.println("Hello, My first program in File.");
		
			obj.close();
		}
		catch (Exception E)
		{
			System.out.println("Undefined access");
		}
	}
}
