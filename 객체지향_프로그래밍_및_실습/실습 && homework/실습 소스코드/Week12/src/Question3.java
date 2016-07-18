import java.io.PrintWriter;
import java.io.FileReader;
import java.util.Scanner;

public class Question3 
{
	public static void main(String[] args)
	{
		try
		{
			FileReader fread=new FileReader("babynames.txt");
			Scanner read=new Scanner(fread);
			int num=1;
			
			PrintWriter boys=new PrintWriter("boynames.txt");
			PrintWriter girls=new PrintWriter("girlnames.txt");
			
			while (read.hasNext())
			{
				boys.print(Integer.toString(num)+" ");
				read.next();
				boys.print(read.next()+" ");
				boys.print(read.next()+" ");
				boys.println(read.next()+" ");
				
				girls.print(Integer.toString(num)+" ");
				girls.print(read.next()+" ");
				girls.print(read.next()+" ");
				girls.println(read.next()+" ");
				
				num++;
			}
			
			boys.close();
			girls.close();
			read.close();
			fread.close();
		}
		catch (Exception E)
		{
			System.out.println("Undefined Access");
		}
		finally
		{
			System.out.println("Done.");
		}
	}
}
