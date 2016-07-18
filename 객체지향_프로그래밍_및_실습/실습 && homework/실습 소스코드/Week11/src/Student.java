
public class Student extends Person
{
	private String major;
	
	public Student(String name,int year,String major)
	{
		super(name,year);
		this.major=major;
	}
	
	public String toString()
	{
		return super.toString()+" major :"+this.major;
	}
}

