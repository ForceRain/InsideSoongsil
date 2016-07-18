import java.util.Scanner;

public class Ques2 {

	public static void main(String[] args) {
			Scanner in=new Scanner(System.in);
			System.out.println("Give me number :");
			int input=in.nextInt();
			
			for (int j=0;j<input+1;j++)
			{
				for (int k=j;k<input;k++)
					System.out.print(" ");
				for (int l=0;l<2*j+1;l++)
					System.out.print("*");
				System.out.println();
			}
			
			for (int j=0;j<input;j++)
			{
				for (int k=0;k<=j;k++)
					System.out.print(" ");
				for (int l=0;l<2*(input-j-1)+1;l++)
					System.out.print("*");
				System.out.println();
			}
	}

}
