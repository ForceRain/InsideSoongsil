import java.util.Scanner;

public class Ques6Tester {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		int gap,year,month,day;
		System.out.println("Please type day");
		day=in.nextInt();
		System.out.println("Please type month");
		month=in.nextInt();
		System.out.println("Please type year");
		year=in.nextInt();
		
		myDate obj1=new myDate(month,day,year);
		obj1.GetDate();
		
		obj1.setday(1);
		obj1.setmonth(1);
		obj1.setyear(2014);
		obj1.GetDate();
		
		System.out.println("Month : "+obj1.getmonth());
		System.out.println("Year : "+obj1.getyear());
		System.out.println("Day : "+obj1.getday());
	}
}
