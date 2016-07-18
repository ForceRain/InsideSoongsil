import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.Rectangle;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ComboClass extends JFrame
{
	private final static int FRAME_WIDTH=300;
	private final static int FRAME_HEIGHT=400;
	
	private JPanel checkPanel;
	private JComboBox comBox;
	
	public ComboClass()
	{
		checkPanel=new JPanel();
	
		checkPanel.setLayout(new BorderLayout());
		add(checkPanel,BorderLayout.CENTER);
		createControlPanel();
		setSampleDiagram("Rectangle");
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		setVisible(true);
	}
	
	public void createControlPanel()
	{
		class ShapeListener implements ItemListener
		{
			public void itemStateChanged(ItemEvent event)
			{
				String input=(String)event.getItem();
				setSampleDiagram(input);
			}
		}
		ItemListener listener=new ShapeListener();
		
		comBox=new JComboBox();
		comBox.addItem("Rectangle");
		comBox.addItem("Circle");
		comBox.addItemListener(listener);
		
		JPanel controlPanel=new JPanel();
		
		controlPanel.setLayout(new BorderLayout());
		controlPanel.add(comBox);
		
		add(controlPanel,BorderLayout.SOUTH);
	}
	
	public void setSampleDiagram(String input)
	{
		
		if (input.equals("Rectangle"))
		{
			RectangleCom component=new RectangleCom();
			checkPanel.add(component);
			//add(checkPanel,BorderLayout.CENTER);
		}
		else if (input.equals("Circle"))
		{
			
		}
	}
}
