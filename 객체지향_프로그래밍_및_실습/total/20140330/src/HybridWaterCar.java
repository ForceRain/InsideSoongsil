
class HybridWaterCar extends HybridCar {
	int waterGauge;
	
	public HybridWaterCar(int in1,int in2,int in3)
	{
		super(in1,in2);
		waterGauge=in3;
	}
	
	public void showCurrentGauge()
	{
		System.out.println("잔여 가솔린 :"+gasolineGauge);
		System.out.println("잔여 전기량 :"+electricGauge);
		System.out.println("잔여 워터량 :"+waterGauge);
	}
}
