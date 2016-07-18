import java.io.FileReader;
import java.util.Scanner;

public class FileRead 
{
	private FileReader fread;
	private Scanner read;
	public FileRead(String input)
	{
		try
		{
			fread=new FileReader(input+".txt");
			read=new Scanner(fread);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	public void read() throws ExceptionLineTooLong
	{
		int sum=0;
		while (read.hasNextLine())
		{
			sum+=read.nextLine().length();
		}
		if (sum>80)
			throw new ExceptionLineTooLong();
		else
			System.out.println("done.");
	}
}
