
class HybridWaterCar extends HybridCar {
	int waterGauge;
	
	public HybridWaterCar(int in1,int in2,int in3)
	{
		super(in1,in2);
		waterGauge=in3;
	}
	
	public void showCurrentGauge()
	{
		System.out.println("�ܿ� ���ָ� :"+gasolineGauge);
		System.out.println("�ܿ� ���ⷮ :"+electricGauge);
		System.out.println("�ܿ� ���ͷ� :"+waterGauge);
	}
}
