
public class PhoneCompanyInfo extends PhoneInfo
{
	private String company;
	
	public PhoneCompanyInfo(String name, String phoneNum,String company)
	{
		super(name, phoneNum);
		this.company=company;
	}			
	
	public void ShowPhoneInfo()
	{
		super.GetName();
		super.ShowPhoneInfo();
		System.out.println("ȸ�� :"+this.company);
		System.out.println();
	}
}
