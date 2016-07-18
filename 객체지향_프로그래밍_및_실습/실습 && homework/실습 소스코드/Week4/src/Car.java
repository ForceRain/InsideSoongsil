
class Car {
	private int fuel_efficiency;
	private double gas_tank;
	private int distance;
	
	public Car(int input)
	{
		fuel_efficiency=input;
		distance=0;
	}
	
	public void drive(int dis)
	{
		distance+=dis;
		gas_tank-=(dis)/(fuel_efficiency);
	}
	
	public double getGasInTank()
	{
		return gas_tank;
	}
	
	public void addGas(int in)
	{
		gas_tank+=in;
	}
}
