
public class Executive extends Manager
{
	public Executive(String name,int salary,String department)
	{
		super(name,salary,department);
	}
	
	public String toString()
	{
		return "Executive :: \n"+super.toString();
	}
}
