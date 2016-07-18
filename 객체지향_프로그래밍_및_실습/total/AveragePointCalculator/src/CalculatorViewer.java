import javax.swing.JFrame;

public class CalculatorViewer 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550,500);
		frame.setTitle("평점평균계산기Ver 0.2");
		
		CalculatorPanel component=new CalculatorPanel();
		frame.add(component);
		
		frame.setVisible(true);
	}
}
