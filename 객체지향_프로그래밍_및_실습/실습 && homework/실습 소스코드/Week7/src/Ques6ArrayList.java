import java.util.ArrayList;

class Ques6ArrayList {
	private ArrayList<Integer> arr;
	
	public Ques6ArrayList ()
	{
		arr=new ArrayList<Integer>();
	}
	
	public void SetData(int input)
	{
		arr.add(input);
	}
	
	public void OrderData()
	{
		for (int i=0;i<arr.size()-1;i++)
		{
			for (int j=arr.size()-1;j>i;j--)
			{
				if (arr.get(j-1)>arr.get(j))
				{
					int temp=arr.get(j-1);
					arr.set(j-1,arr.get(j));
					arr.set(j,temp);
				}
			}
		}
	}
	
	public int ShowResult()
	{
		return arr.get(arr.size()-2);
	}
}
