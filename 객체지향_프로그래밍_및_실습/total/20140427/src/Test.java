import java.util.Scanner;

public class Test {
	public static void main(String[]args)
	   {
	      Homework hw = new Homework();
	      String re = "yes";
	      Scanner in = new Scanner(System.in);
	      
	      while(re=="yes"||re=="Yes"||re=="Y"||re=="y")
	      {
	         
	         hw. printmenu();
	         String select = in.next();
	         in.nextLine();
	         
	         if(select.equals("1"))
	         {
	            hw.inputdata();
	            
	            System.out.println("\n Do you want to continue? ");
	            in.nextLine();
	            re = in.next();
	            
	         }
	         
	         else if(select.equals("2"))
	         {
	            
	         }
	         
	         else
	         {
	            System.out.println("\nSelect wrong number ");
	            re = "yes";
	         }
	         
	         
	      }
	      
	      in.close();
	      in.close();
	      
	   }
}
