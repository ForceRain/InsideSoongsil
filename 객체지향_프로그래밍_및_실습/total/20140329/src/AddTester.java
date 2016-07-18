
public class AddTester {

	public static void main(String[] args) {
		AddArr obj=new AddArr();
		
		int[][] arr={{1,2,3},{3,4,5},{4,5,6}};
		
		obj.addTwoDarr(arr, 7);
		
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
