import java.lang.Math;

public class ComputePI {
	public static void main(String[] args)
	{
		final double EPSILON=1E-7;
		
		double Pi=1.0;
		double BefPi;
		int i=1;
		BefPi=Pi;
		while (true)
		{
			BefPi=Pi;
			if (i%2==1)
			{
				Pi-=1.0/(2*i+1);
				System.out.println("- after PI is :"+Pi);
			}
			else
			{
				Pi+=1.0/(2*i+1);
				System.out.println("+ after PI is :"+Pi);
			}
			i++;
			
			if (Math.abs(BefPi-Pi)<=EPSILON)
				break;
		}
		System.out.println("PI is :"+4*Pi);
		System.out.println("# of loop call :"+i);
	}
}
