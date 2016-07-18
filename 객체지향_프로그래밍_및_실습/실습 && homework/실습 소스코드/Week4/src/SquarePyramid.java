
class SquarePyramid {
	private int Height;
	private int SideLength;
	
	public void getHeight(int height)
	{
		this.Height=height;
	}
	
	public void getLength_of_side(int length)
	{
		this.SideLength=length;
	}
	
	public double calculate_area()
	{
		return (1/3.0)*Height*SideLength*SideLength;
	}
}
