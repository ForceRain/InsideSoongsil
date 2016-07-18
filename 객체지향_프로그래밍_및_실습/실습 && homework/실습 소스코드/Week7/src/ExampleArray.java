import java.util.StringTokenizer;

class ExampleArray {
		private int[] array=null;
		private int size;
		
		public ExampleArray(String input)
		{
			StringTokenizer obj1=new StringTokenizer(input);
			int Tokens=obj1.countTokens();
			int i=0;
			
			while (obj1.hasMoreTokens())
			{
				if (array==null)
				{
					size=Integer.parseInt(obj1.nextToken());
					array=new int[size];
				}
				else
					array[i++]=Integer.parseInt(obj1.nextToken());
			}
		}
		public int[] GiveArray()
		{
			return array;
		}
		public int GiveSize()
		{
			return size;
		}
		
		public void CheckArray(ExampleArray obj)
		{
			int[] arr1=obj.GiveArray();
			int roll=obj.GiveSize();
			
			for (int i=0;i<roll;i++)
			{
				for (int j=0;j<this.size;j++)
				{
					if (arr1[i]==array[j])
					{
						System.out.println(arr1[i]);
						break;
					}
				}
			}
		}
}
