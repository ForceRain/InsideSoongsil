import java.util.Scanner;

public class TesterCone {
		public static void main(String[] args)
		{
				Scanner in=new Scanner(System.in);
				double height,radius;
				System.out.println("Please type height :");
				height=in.nextDouble();
				System.out.println("Please type radius :");
				radius=in.nextDouble();
			
				IceCreamCone obj= new IceCreamCone(radius,height);
				
				System.out.println("SurfaceArea :"+obj.getSurfaceArea());
				System.out.println("Volume :"+obj.getVolume());
		}
}
