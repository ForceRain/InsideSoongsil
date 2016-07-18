
class AddArr {
	public static void addOneDArr(int[] arr,int add)
	{
		for (int i=0;i<arr.length;i++)
			arr[i]+=add;
	}
	
	public static void addTwoDarr(int[][] arr,int add)
	{
		for (int j=0;j<arr.length;j++)
			addOneDArr(arr[j],add);
	}
}
