public class AreaCalculation 
{
	private double length;
	private double width;
	public AreaCalculation(double length, double width)
	{
		this.length=length;
		this.width=width;
	}
	public double getArea()
	{
		return length*width;
	}
}