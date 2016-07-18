import java.util.Scanner;
import java.io.IOException;

public class Ques6Tester 
{
	public static void main(String[] args)
	{
     	boolean done = false;
     	Scanner in = new Scanner(System.in);
     	Bank bank = new Bank();
     	while (!done)
     	{
       		System.out.print("Filename: ");
       		String filename = in.next();
     		try
       		{
          		bank.readFile(filename);
          		BankAccount highest = bank.getHighestBalance();
          		System.out.println(highest.getAccountNumber()+ " " + highest.getBalance());
            	done = true;
         	}   
         	catch (Exception e)
         	{   
         		e.printStackTrace();    
         	}
      	}
   	}
}
