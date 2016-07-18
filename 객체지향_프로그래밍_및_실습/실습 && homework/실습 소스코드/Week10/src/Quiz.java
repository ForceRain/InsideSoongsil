

public class Quiz implements Measurable
{
	double score;
	char grade;
	
	public Quiz(double input,char inGrade)
	{
		score=input;
		grade=inGrade;
	}
	
	public double getMeasure()
	{
		return score;
	}
	
	public String toString()
	{
		return "Score :"+score+"  Grade :"+grade;
	}
}
