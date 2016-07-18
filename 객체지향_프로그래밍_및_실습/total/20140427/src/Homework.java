import java.util.Scanner;
import java.util.ArrayList;

public class Homework 
{
   int size;
   
   Homework()
   {
      
   }
   
   public void printmenu()
   {
      System.out.println("\n\n**********************************");
      System.out.println("* Homework1 -----------  HangMan *");
      System.out.println("**********************************");
      System.out.println("\n   ---------------------------");
      System.out.println("   |  1. Setup the datalist   |");
      System.out.println("   |--------------------------|");
      System.out.println("   |  2. Start Game           |");
      System.out.println("   ----------------------------");
      System.out.print("Please enter yout selection : ");
   }
   
   public void inputdata()
   {
      Scanner in1 = new Scanner(System.in);
      Scanner in2 = new Scanner(System.in);
      ArrayList<String> data = new ArrayList<String>();
      System.out.print("\nWhat is the size of the data list? ");
      size = in1.nextInt();
      in1.nextLine();
      
      for(int i=0; i<size; i++)
      {
         System.out.print("\nStart Enter the Data #" +(i+1)+" ");
         String tmp = in2.nextLine();
         data.add(tmp);
      }
      
      in1.close();
      in2.close();
            
   }
}