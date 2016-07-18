import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Button extends JPanel
{
	private JButton obj;
	private int count;
	private ButtonListener listen;
	
	public Button()
	{
		obj=new JButton("Click Me!!");
		listen=new ButtonListener();
		count=0;
		
		this.add(obj);
		obj.addActionListener(listen);
	}
	
	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			count++;
			System.out.println("I was clicked "+count+" times!!");
		}
	}
}
