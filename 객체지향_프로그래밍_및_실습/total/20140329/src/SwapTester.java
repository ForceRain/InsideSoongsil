
public class SwapTester {
	public static void main(String[] args)
	{
		Swap obj=new Swap();
		
		int[][] arr={{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
		
		arr=obj.Change(arr);
		
		for (int i=0;i<arr.length;i++)
		{
			for (int j=0;j<arr[i].length;j++)
			{
				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}
}
