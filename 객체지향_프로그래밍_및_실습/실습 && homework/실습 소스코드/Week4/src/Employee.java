
class Employee {
	private String name;
	private int ID;
	private String number;
	
	Employee (String name,int Id,String num)
	{
		this.name=name;
		this.ID=Id;
		this.number=num;
	}
	
	public void getDetail()
	{
		System.out.println("name :"+this.name);
		System.out.println("ID :"+this.ID);
		System.out.println("phone number :"+this.number);
	}
	
	public void change_number(String input)
	{
		this.number=input;
	}
}
