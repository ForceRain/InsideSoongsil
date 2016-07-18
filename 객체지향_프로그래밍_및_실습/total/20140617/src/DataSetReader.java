import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DataSetReader 
{
	private double[] data;
	
	public double[] readFile(String filename) throws IOException
	{
		File inFile =new File(filename);
		Scanner in=new Scanner(inFile);
		
		try
		{
			readData(in);
			return data;
		}
		finally
		{
			in.close();
		}
	}
	
	private void readData(Scanner in) throws BadDataException
	{
		if (!in.hasNextInt())
			throw new BadDataException("Length expected");
		int numberOfValues=in.nextInt();
		data=new double[numberOfValues];
		
		for (int i=0;i<numberOfValues;i++)
			readValue(in,i);
		
		if (!in.hasNext())
			throw new BadDataException("End of file expected");
	}
	
	private void readValue(Scanner in,int i)throws BadDataException
	{
		if (!in.hasNextDouble())
			throw new BadDataException("Data value expected");
		data[i]=in.nextDouble();
	}
	
	public void showValue()
	{
		for (double arr: data)
			System.out.println(arr);
	}
}
