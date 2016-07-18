import java.util.StringTokenizer;
import java.util.Random;
import java.lang.StringBuilder;

class CoinToss {
		StringBuilder temp=new StringBuilder(100);
		String Toss;
		int[] arr;
		public CoinToss()
		{
			arr=new int[100];
			
			Random obj1=new Random();
			for (int i=0;i<100;i++)
			{
				int go=obj1.nextInt(2);
				
				if (go==0)
					temp=temp.insert(i, 'T');
				else
					temp=temp.insert(i, 'H');
			}
			Toss=temp.toString();
		}
		
		public String ShowToss()
		{
			return Toss;
		}
		
		public void checkToken()
		{
			StringTokenizer obj=new StringTokenizer(Toss,"T");
			
			while (obj.hasMoreTokens())
			{
				arr[(obj.nextToken().length())-1]++;
			}
		}
		
		public void ShowResult()
		{
			System.out.println("Length      Numbers of runs of heads");
			for (int i=0;i<100;i++)
				System.out.println((i+1)+"     "+arr[i]);
		}
}
