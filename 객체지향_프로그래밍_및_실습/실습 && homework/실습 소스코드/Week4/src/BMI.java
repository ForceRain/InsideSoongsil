
class BMI {
	private double weight;
	private double height;
	
	public void input_weight(double weight)
	{
		this.weight=weight;
	}
	
	public void input_height(double height)
	{
		this.height=height;
	}
	
	public double calculate_BMI()
	{
		return weight/(height*height);
	}
}
