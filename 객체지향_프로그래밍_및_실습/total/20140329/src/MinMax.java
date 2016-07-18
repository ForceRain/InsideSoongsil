
class MinMax {
	public static int MinValue(int[] arr)
	{
		int min=arr[0];
		for (int i:arr)
		{
			if (min>i)
				min=i;
		}
		return min;
	}
	public static int MaxValue(int[] arr)
	{
		int max=arr[0];
		for (int i:arr)
		{
			if (max<i)
				max=i;
		}
		return max;
	}
}
