import java.awt.Rectangle;

public class AnotherRectangle extends Rectangle
{
	private int width;
	private int height;
	
	public AnotherRectangle(int width,int height)
	{
		this.width=width;
		this.height=height;
	}
	
	public boolean equals(AnotherRectangle obj)
	{
		return (width==obj.width)&&(height==obj.height);
	}
}
