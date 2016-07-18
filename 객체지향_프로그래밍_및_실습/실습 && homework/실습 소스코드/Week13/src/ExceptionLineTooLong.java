import java.lang.Exception;

public class ExceptionLineTooLong extends Exception
{
	public ExceptionLineTooLong()
	{
		System.out.println("The string is too long.");
	}
}
