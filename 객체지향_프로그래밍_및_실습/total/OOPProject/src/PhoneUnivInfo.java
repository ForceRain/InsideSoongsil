
public class PhoneUnivInfo extends PhoneInfo
{
	private String major;
	private int year;
	
	public PhoneUnivInfo(String name,String phoneNum,String major,int year)
	{
		super(name,phoneNum);
		this.major=major;
		this.year=year;
	}
	
	public void ShowPhoneInfo()
	{
		super.GetName();
		super.ShowPhoneInfo();
		System.out.println("전공 :"+this.major);
		System.out.println("학년 :"+this.year);
		System.out.println();
	}
}
