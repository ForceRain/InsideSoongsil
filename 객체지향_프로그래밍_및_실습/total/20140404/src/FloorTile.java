import java.util.Scanner;

public class FloorTile {
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
		int row,columns;
		
		System.out.println("Please type row :");
		row=in.nextInt();
		System.out.println("Please type columns :");
		columns=in.nextInt();
		
		for (int i=0;i<row;i++)
		{
			for (int j=0;j<columns;j++)
			{
				if ((i+j)%2!=1)
				{
					System.out.print("B");
				}
				else
				{
					System.out.print("W");
				}
			}
			System.out.println();
		}
	}
}
