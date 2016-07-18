
public class Question2Tester 
{
	public static void main(String[] args)
	{
		DataSet control=new DataSet();
		
		Person per1=new Person("Kim",189);
		Person per2=new Person("Park",200);
		Person per3=new Person("John",150);
		
		control.add(per1);
		control.add(per2);
		control.add(per3);
		
		System.out.println("Average :"+control.getAverage());
		System.out.println("Maximum =="+control.getMaximum());
	}
}
