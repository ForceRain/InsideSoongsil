import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.BorderLayout;
import javax.swing.Timer;

public class Ques1Panel extends JPanel
{
	private Car obj;
	private JPanel choosePanel;
	private JRadioButton day;
	private JRadioButton night;
	private ButtonGroup bgroup;
	private String state;
	private Color background;
	private ButtonListener BTListener;
	private Timer timer;
	private moveTimer MVTimer;
	
	public Ques1Panel()
	{
		obj=new Car(50,300);
		
		Thread tmp=new Thread(obj);
		tmp.start();
		
		choosePanel=new JPanel();
		
		day=new JRadioButton("Day");
		night=new JRadioButton("Night");
		
		day.setSelected(true);
		this.setLayout(new BorderLayout());
		
		bgroup=new ButtonGroup();
		bgroup.add(day);
		bgroup.add(night);
		
		background=Color.WHITE;
		
		BTListener=new ButtonListener();
		day.addActionListener(BTListener);
		night.addActionListener(BTListener);
		
		choosePanel.add(day);
		choosePanel.add(night);
		
		this.add(choosePanel,BorderLayout.SOUTH);
		
		MVTimer=new moveTimer();
		timer=new Timer(100,MVTimer);
		timer.start();
	}
	
	class moveTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			repaint();
		}
	}
	
	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			repaint();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
		
		if (day.isSelected())
			this.setBackground(Color.WHITE);
		if (night.isSelected())
			this.setBackground(Color.GRAY);
		
		obj.draw(g2);
	}
}
