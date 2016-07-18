
class Student {
	private String name;
	private String student_ID;
	private String Contact_Number;
	
	public Student(String name,String ID,String num)
	{
		this.name=name;
		this.student_ID=ID;
		this.Contact_Number=num;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getID()
	{
		return student_ID;
	}
	
	public String getPhone_Number()
	{
		return Contact_Number;
	}
	
}
