import java.util.ArrayList;

class Ques3ArrayList {
	ArrayList<Integer> arr;
	
	public Ques3ArrayList()
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
	
	public boolean CheckData()
	{
		for (int i=1;i<arr.size()-1;i++)
		{
			if (arr.get(i+1)==arr.get(i) || arr.get(i)==arr.get(i+1))
				return true;
		}
		return false;
	}
}
