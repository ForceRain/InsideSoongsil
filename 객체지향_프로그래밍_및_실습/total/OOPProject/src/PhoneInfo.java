
public class PhoneInfo {
	private String name;
	private String phoneNumber;
	
	public PhoneInfo(String name,String phoneNumber)
	{
		this.name=name;
		this.phoneNumber=phoneNumber;
	}
		
	public void ShowPhoneInfo()
	{
		System.out.println("\n입력된 정보 출력...");
		System.out.println("name :"+this.name);
		System.out.println("phoneNumber :"+this.phoneNumber);
	}
	
	public String GetName()
	{
		return name;
	}
}
