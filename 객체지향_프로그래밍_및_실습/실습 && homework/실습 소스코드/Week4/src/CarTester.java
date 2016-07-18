import java.util.Scanner;

public class CarTester {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		int gap;
		System.out.println("Please type fuel-efficiency");
		gap=in.nextInt();
		Car myHybrid=new Car(gap);
		System.out.println("Please type added fuel");
		gap=in.nextInt();
		myHybrid.addGas(gap);
		System.out.println("Please type drive distance");
		gap=in.nextInt();
		myHybrid.drive(gap);
		double gasLeft=myHybrid.getGasInTank();
		System.out.println("Ramaining Gas is "+gasLeft);
	}
}
