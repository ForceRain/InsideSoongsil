
public class LeapYear {
	private int year;
	
	public LeapYear(int input)
	{
		year=input;
	}
	public boolean IsLeapYear()
	{
		if ((year%4==0 && year%100!=0) || (year%400==0))
			return true;
		else
			return false;
	}
}
