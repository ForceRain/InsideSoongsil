
public class Question3Tester {
	public static void main(String[] args)
	{
		PrimeSequence obj=new PrimeSequence();
		
		for (int i=0;i<10;i++)
		{
			System.out.println((i+1)+"'th prime number is :"+obj.next());
		}
	}
}
