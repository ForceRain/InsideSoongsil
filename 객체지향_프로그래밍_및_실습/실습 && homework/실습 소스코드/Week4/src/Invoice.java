
class Invoice {
	private String partNum;
	private String partDes;
	private int Quantity;
	private double PPI;
	
	public Invoice(String PN,String PD,int Q,double PPI)
	{
		this.partNum=PN;
		this.partDes=PD;
		this.Quantity=Q;
		this.PPI=PPI;
	}
	
	public void setPartNum(String input)
	{
		this.partNum=input;
	}
	
	public void setPartDes(String input)
	{
		this.partDes=input;
	}
	
	public void setQuantity(int Q)
	{
		if (Q<0)
			this.Quantity=0;
		else
			this.Quantity=Q;
	}
	
	public void setPPI(double PPI)
	{
		if (PPI<0.0)
			this.PPI=0.0;
		else
			this.PPI=PPI;
	}
	
	public String getPartNum()
	{
		return this.partNum;
	}
	
	public String getPartDes()
	{
		return this.partDes;
	}
	
	public int getQuantity()
	{
		return this.Quantity;
	}
	
	public double getPPI()
	{
		return this.PPI;
	}
	
	public double getInvoiceamount()
	{
		return (this.PPI)*(this.Quantity);
	}
}
