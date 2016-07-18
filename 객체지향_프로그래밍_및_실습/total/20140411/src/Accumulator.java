
class Accumulator 
{
	private int val;
	
	public Accumulator(int init) {val=init;}
	
	protected void accumulate(int num)
	{
		if (num<0)
			return;
		val+=num;
	}
	protected int getAccVal() {return val;}
}
