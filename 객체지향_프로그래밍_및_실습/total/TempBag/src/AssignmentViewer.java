import javax.swing.JFrame;
import java.io.File;
import java.awt.Toolkit;
import java.awt.Image;
import java.io.IOException;
import javax.swing.UIManager;

public class AssignmentViewer
{
	public static void main(String[] args)
	{	/*
		try
		{
			UIManager.setLookAndFeel("net.sourceforge.napkinlaf.NapkinLookAndFeel");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}*/
		JFrame frame=new JFrame();
		frame.setSize(1100,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Object Oriented Programming and Practical using Java:::Assignment[My Mini Zoo]");
		
		TempAssignmentPanel panel=new TempAssignmentPanel();
		frame.add(panel);
		
		frame.setVisible(true);
	}
}
