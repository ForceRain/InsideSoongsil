import java.lang.Math;

class IceCreamCone {
		private double radius;
		private double height;
		
		public IceCreamCone(double radius,double height)
		{
				this.radius=radius;
				this.height=height;
		}
		
		public double getSurfaceArea()
		{
				
				return Math.sqrt(height*height+radius*radius)*3.14*radius;
		}
		
		public double getVolume()
		{
				return 1/3.0*(radius)*(radius)*3.14*height;
		}
}
