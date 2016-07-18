
class SecL {
	private int[] arr;
	private int max;
	
	public SecL()
	{
		arr=new int[20];
		max=0;
	}
	
	public void SetData(int input)
	{
		arr[max++]=input;
	}
	
	public int GetSecData()
	{
		for (int i=0;i<max-1;i++)
		{
			for (int j=max-1;j>i;j--)
			{
				if (arr[j-1]>arr[j])
				{
					int temp=arr[j-1];
					arr[j-1]=arr[j];
					arr[j]=temp;
				}
			}
		}
		
		return arr[max-2];
	}
}
