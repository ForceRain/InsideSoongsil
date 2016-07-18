import java.lang.Exception;

public class NumberTooSmallException extends Exception
{
	public NumberTooSmallException()
	{
		System.out.println("Number Less than Zero!");
	}
}
