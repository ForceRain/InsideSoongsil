
class myDate {
	private int Month;
	private int Day;
	private int Year;

	public myDate(int month,int day, int year)
	{
		this.Month=month;
		this.Day=day;
		this.Year=year;
	}
	
	public int getmonth()
	{
		return this.Month;
	}
	
	public int getday()
	{
		return this.Day;
	}
	
	public int getyear()
	{
		return this.Year;
	}
	
	public void setmonth(int mon)
	{
		this.Month=mon;
	}
	
	public void setday(int day)
	{
		this.Day=day;
	}
	
	public void setyear(int year)
	{
		this.Year=year;
	}
	
	public void GetDate()
	{
		System.out.println(Month+"/"+Day+"/"+Year);
	}
}
