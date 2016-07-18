
class Patient {
	private String pID;
	private String pName;
	private String pConnum;
	
	public Patient()
	{
		pID="NoID";
		pName="NoName";
		pConnum="NoNum";
	}
	
	public Patient(String ID,String name,String connum)
	{
		this.pID=ID;
		this.pName=name;
		this.pConnum=connum;
	}
	
	public String toString()
	{
		return "ID :"+pID+" Name :"+pName+" Contact Number :"+pConnum;
	}
}
