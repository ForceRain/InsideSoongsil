
public class Person 
{
	private String name;
	private int year;
	public Person(String name,int year)
	{
		this.name=name;
		this.year=year;
	}
	
	public String toString()
	{
		return "name :"+this.name+" birth :"+this.year;
	}
}
