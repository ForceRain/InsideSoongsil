
public class PrimeSequence implements Sequence
{
	private int maker;
	
	public PrimeSequence()
	{
		maker=1;
	}
	
	public int factory()
	{
		while (true)
		{
			int cnt=0;
			maker++;
			for (int i=1;i<=maker;i++)
			{
				if (maker%i==0)
					cnt++;
			}
			if (cnt==2)
				return maker;
		}
	}
	
	public int next()
	{
		return factory();
	}
}
