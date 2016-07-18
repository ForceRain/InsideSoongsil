import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TwoButton extends JPanel
{
	private JButton right;
	private JButton left;
	
	private RButtonListener rightListen;
	private LButtonListener leftListen;
	
	private int lCnt;
	private int rCnt;
	
	public TwoButton()
	{
		lCnt=0;
		rCnt=0;
		right=new JButton("Click me!!");
		left=new JButton("Click me!!");
		
		this.add(left);
		this.add(right);
		rightListen=new RButtonListener();
		leftListen=new LButtonListener();
		
		right.addActionListener(rightListen);
		left.addActionListener(leftListen);
	}
	
	class LButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			lCnt++;
			System.out.println("Left button clicked "+lCnt+" times!");
		}
	}
	class RButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			rCnt++;
			System.out.println("Right button clicked "+rCnt+" times!");
		}
	}
}
