
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
		System.out.println("\n�Էµ� ���� ���...");
		System.out.println("name :"+this.name);
		System.out.println("phoneNumber :"+this.phoneNumber);
	}
	
	public String GetName()
	{
		return name;
	}
}
