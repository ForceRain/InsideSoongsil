
public class Person implements Measurable
{
	private String name;
	double height;
	
	public Person(String name,double height)
	{
		this.name=name;
		this.height=height;
	}
	
	public String toString()
	{
		return "Name :"+name+"  height :"+height;
	}
	
	public double getMeasure()
	{
		return height;
	}
}
