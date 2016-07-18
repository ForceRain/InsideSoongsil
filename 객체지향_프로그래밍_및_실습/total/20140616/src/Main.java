import java.io.FileReader;
import java.util.Scanner;
import java.io.PrintWriter;

public class Main 
{
	public static void main(String[] args)
	{
		try
		{
			FileReader fread=new FileReader("HangManPrinter.java");
			Scanner read=new Scanner(fread);
			
			PrintWriter write=new PrintWriter("Output.txt");
			
			while (read.hasNextLine())
			{
				write.println(read.nextLine());
			}
			
			System.out.println("done.");
			
			fread.close();
			read.close();
			write.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
