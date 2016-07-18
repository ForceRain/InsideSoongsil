import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuCheck extends JPanel
{
	private JMenuBar bar;
	private JMenu menu;
	private JMenu submenu;
	private JMenuItem test1;
	private JPanel panel;
	
	public MenuCheck()
	{
		bar=new JMenuBar();
		
		menu=new JMenu("Test1");
		submenu=new JMenu("Test2");
		test1=new JMenuItem("Item1");
		menu.add(test1);
		menu.add(submenu);
		
		bar.add(menu);
		
		this.setLayout(new BorderLayout());
		
		this.add(bar,BorderLayout.NORTH);
	}
	
	class MBListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			
		}
	}
}
