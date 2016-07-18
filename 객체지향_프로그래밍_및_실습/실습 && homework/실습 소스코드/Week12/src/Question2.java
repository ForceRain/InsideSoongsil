import java.io.FileReader;
import java.util.Scanner;
import java.io.PrintWriter;

public class Question2 
{
	public static void main(String[] args)
	{
		try
		{
			Scanner in=new Scanner(System.in);
			System.out.println("Please type filename : ");
			String name=in.nextLine();
			
			FileReader freader=new FileReader(name+".txt");
			Scanner read=new Scanner(freader);
			
			int num_char=0;
			int num_word=0;
			
			while (read.hasNext())
			{
				
				num_char+=(read.next()).length();
				num_word++;
			}
			
			PrintWriter writer=new PrintWriter(name+"result.txt");
			writer.println("Total characters :"+num_char);
			writer.println("Total words : "+num_word);
			
			writer.close();
			freader.close();
			read.close();
		}
		catch (Exception E)
		{
			System.out.println("Undefined access");
		}
	}
}
