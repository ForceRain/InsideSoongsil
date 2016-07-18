
public class Instructor extends Person
{
	private int salary;
	public Instructor(String name,int year,int salary)
	{
		super(name,year);
		this.salary=salary;
	}
	
	public String toString()
	{
		return super.toString()+" salary :"+this.salary;
	}
}
