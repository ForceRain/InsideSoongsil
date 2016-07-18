import java.lang.IllegalArgumentException;

public class BankAccount 
{
	private int AccountNum;
	private double balance;
	
	public BankAccount(int Acnum,double input) throws IllegalArgumentException
	{
		if (input<0)
			throw new IllegalArgumentException("Negative balance is not allowed.");
		
		AccountNum=Acnum;
		balance=input;
	}
	
	public void deposit(double amount) throws IllegalArgumentException
	{
		if (amount<0)
			throw new IllegalArgumentException("Negative deposit is not allowed");
	}
	
	public void withdraw(double amount) throws IllegalArgumentException
	{
		if (amount>balance)
			throw new IllegalArgumentException("amount is less than balance.");
		balance-=amount;
	}
	
	public int getAccountNumber()
	{
		return AccountNum;
	}
	
	public double getBalance()
	{
		return balance;
	}
}
