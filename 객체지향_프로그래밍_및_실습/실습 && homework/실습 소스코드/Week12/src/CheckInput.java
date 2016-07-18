import java.util.Scanner;
import java.lang.Exception;

public class CheckInput 
{
	private Scanner keyboard;
	private double sum;
	private int check=0;
	
	public CheckInput()
	{
		keyboard=new Scanner(System.in);
		sum=0.0;
		check=0;
	}
	
	public boolean getInput() throws Exception
	{
		if (isEnd())
			return false;
		
		System.out.print("Give input :");
		String input=keyboard.nextLine();
		
		if (Character.isLetter(input.charAt(0)))
		{
			check++;
			throw new Exception("Please input float-point value.");
		}
		
		sum+=Double.parseDouble(input);
		
		return true;
	}
	
	public boolean isEnd()
	{
		return check==2;
	}
	
	public double getResult()
	{
		return sum;
	}
}
