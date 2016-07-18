
public class Ques7Tester {
	public static void main(String[] args)
	{
		Invoice obj1=new Invoice("0xas1","JAVA",30,20.01);
		System.out.println("part number :"+obj1.getPartNum());
		System.out.println("part description :"+obj1.getPartDes());
		System.out.println("quantity of the item being purchase :"+obj1.getQuantity());
		System.out.println("prive per item :"+obj1.getPPI());
		System.out.println(obj1.getInvoiceamount());
		
		obj1.setPartNum("0x12adf");
		obj1.setPartDes("C++");
		obj1.setQuantity(-20);
		obj1.setPPI(-0.20);
		System.out.println();
		
		System.out.println("part number :"+obj1.getPartNum());
		System.out.println("part description :"+obj1.getPartDes());
		System.out.println("quantity of the item being purchase :"+obj1.getQuantity());
		System.out.println("prive per item :"+obj1.getPPI());
	}
}
