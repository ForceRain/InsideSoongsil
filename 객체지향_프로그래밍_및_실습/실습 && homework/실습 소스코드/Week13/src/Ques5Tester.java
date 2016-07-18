
public class Ques5Tester 
{
	public static void main(String[] args)
	{
		try
		{
			FileRead obj=new FileRead("Question5");
			obj.read();
		}
		catch (ExceptionLineTooLong exception)
		{}
	}
}
