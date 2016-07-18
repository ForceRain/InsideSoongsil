
public class Pair {
	private double aFirst;
	private double aSecond;
	
	public Pair(double aFirst, double aSecond)
	{
		this.aFirst=aFirst;
		this.aSecond=aSecond;
	}
	
	public double getSum()
	{
		return aFirst+aSecond;
	}
	
	public double getDif()
	{
		if (aFirst>aSecond)
			return aFirst-aSecond;
		else
			return aSecond-aFirst;
	}
	
	public double getAverage()
	{
		return (aFirst+aSecond)/2.0;
	}
	
	public double getMul()
	{
		return aFirst*aSecond;
	}
}
