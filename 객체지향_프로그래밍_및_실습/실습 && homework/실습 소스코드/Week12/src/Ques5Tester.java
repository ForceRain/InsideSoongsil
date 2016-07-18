
public class Ques5Tester 
{
	public static void main(String[] args)
	{
		CheckInput obj=new CheckInput();
		
		while (true)
		{
			try
			{
				if (!obj.getInput())
					break;
			}
			catch (Exception E)
			{
				System.out.println(E.getMessage());
			}
		}
		System.out.println("sum : "+obj.getResult());
	}
}
