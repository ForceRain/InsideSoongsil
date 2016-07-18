import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class Question6Viewer 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Question6");
		
		String posX=JOptionPane.showInputDialog("Type X");
		String posY=JOptionPane.showInputDialog("Type Y");
		String radius=JOptionPane.showInputDialog("Type radius");
		
		DrawCircle obj=new DrawCircle(Double.parseDouble(posX),Double.parseDouble(posY),Double.parseDouble(radius));
		frame.add(obj);
		
		frame.setVisible(true);
	}
}
