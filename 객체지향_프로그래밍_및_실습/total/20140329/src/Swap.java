
class Swap {
	public static int[][] Change(int[][] arr)
	{
		int[][]b=new int[arr.length][arr[0].length];
		
		for (int i=0;i<arr[0].length;i++)
			b[0][i]=arr[arr.length-1][i];
		
		for (int i=1;i<arr.length;i++)
		{
			for (int j=0;j<arr[i].length;j++)
			{
				b[i][j]=arr[i-1][j];
			}
		}
		
		return b;
	}
}
