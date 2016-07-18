

public class Ques4Tester 
{
	public static void main(String[] args)
	{
		try
		{
			BankAccount per1=new BankAccount(10,-333);			
		}
		catch (Exception E)
		{
			System.out.println(E.getMessage());
		}
		
		try
		{
			BankAccount per2=new BankAccount(1,10);	
			per2.deposit(-300);
		}
		catch (Exception E)
		{
			System.out.println(E.getMessage());
		}
		
		try
		{
			BankAccount per3=new BankAccount(9,300);
			per3.withdraw(400);
		}
		catch (Exception E)
		{
			System.out.println(E.getMessage());
		}
	}
}
