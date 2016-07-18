
class CheckArray {
		private int len;
		private int[] array;
		public CheckArray() 
		{
			array=new int[20];
			len=0;
		}
		
		public void SetData(int input)
		{
			array[len++]=input;
		}
		
		public void OrderData()
		{
			int i;
			int j;
			
			for (i=0;i<len-1;i++)
			{
				for (j=len-1;j>i;j--)
				{
					if (array[j-1]>array[j])
					{
						int temp=array[j-1];
						array[j-1]=array[j];
						array[j]=temp;
					}
				}
			}
		}
		
		public boolean CheckData()
		{
			for (int i=1;i<len-1;i++)
			{
				if (array[i-1]==array[i]|| array[i]==array[i+1])
					return true;
			}
			return false;
		}
}
