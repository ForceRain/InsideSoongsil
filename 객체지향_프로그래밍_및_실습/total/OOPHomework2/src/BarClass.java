import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BarClass extends JPanel
{
	private JComboBox box;
	private String CurrentState;
	private ComboBoxListener CBListen;
	private CenterClass centerClass;
	
	public BarClass()
	{
		box=new JComboBox();
		CBListen=new ComboBoxListener();
		centerClass=new CenterClass();
		
		box.addItem("Rectangle");
		box.addItem("Ellipse");
		box.addItem("Square");
		box.addItem("Circle");
		box.addItem("RoundRectangle");
		box.addActionListener(CBListen);
		
		this.setLayout(new BorderLayout());
		this.add(box,BorderLayout.NORTH);
		this.add(centerClass,BorderLayout.CENTER);
	}
	
	public void receivedColor(String inputColor)
	{
		centerClass.LastColor(inputColor);
	}
	
	public void receivedNotice(String inputState)
	{
		centerClass.LastReceive(inputState);
	}
		
	class ComboBoxListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			CurrentState=(String)box.getSelectedItem();
			//System.out.println(CurrentState);
			centerClass.LastShape(CurrentState);
		}
	}
}
