import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RobotClass extends JPanel 
{
	private JPanel top;
	private JButton button;
	private JRadioButton redBtn;
	private JRadioButton greenBtn;
	private JRadioButton blueBtn;
	private JRadioButton orangeBtn;
	private JRadioButton yellowBtn;
	private JRadioButton blackBtn;
	private ButtonGroup group;
	private String CurrentStateColor;
	private String CurrentMoving;
	private BarClass barClass;
	private RadioBtnListener RBListen;
	private ButtonListener BTListen;
	
	public RobotClass() 
	{
		top=new JPanel();
		redBtn=new JRadioButton("Red");
		greenBtn=new JRadioButton("Green");
		blueBtn=new JRadioButton("Blue");
		orangeBtn=new JRadioButton("Orange");
		yellowBtn=new JRadioButton("Yellow");
		blackBtn=new JRadioButton("Black");
		
		group=new ButtonGroup();
		button=new JButton("Move my Robot");
		barClass=new BarClass();
		
		CurrentStateColor="Red";
		CurrentMoving="Stop";
		
		RBListen=new RadioBtnListener();
		BTListen=new ButtonListener();
		button.addActionListener(BTListen);

		this.setLayout(new BorderLayout());
		top.setLayout(new FlowLayout());
		
		group.add(redBtn);
		group.add(greenBtn);
		group.add(blueBtn);
		group.add(orangeBtn);
		group.add(yellowBtn);
		group.add(blackBtn);
		
		redBtn.addActionListener(RBListen);
		greenBtn.addActionListener(RBListen);
		blueBtn.addActionListener(RBListen);
		orangeBtn.addActionListener(RBListen);
		yellowBtn.addActionListener(RBListen);
		blackBtn.addActionListener(RBListen);
		
		top.add(redBtn);
		top.add(greenBtn);
		top.add(blueBtn);
		top.add(orangeBtn);
		top.add(yellowBtn);
		top.add(blackBtn);
		
		redBtn.setSelected(true);
		
		this.add(barClass,BorderLayout.CENTER);
		this.add(button,BorderLayout.SOUTH);
		this.add(top,BorderLayout.NORTH);
	}
	
	class RadioBtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (redBtn.isSelected())
				CurrentStateColor="Red";
			else if (greenBtn.isSelected())
				CurrentStateColor="Green";
			else if (blueBtn.isSelected())
				CurrentStateColor="Blue";
			else if (orangeBtn.isSelected())
				CurrentStateColor="Orange";
			else if (yellowBtn.isSelected())
				CurrentStateColor="Yellow";
			else
				CurrentStateColor="Black";
			barClass.receivedColor(CurrentStateColor);
		}
	}
	
	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (CurrentMoving.equals("Stop"))
				CurrentMoving="Move";
			else
				CurrentMoving="Stop";
			barClass.receivedNotice(CurrentMoving);
		}
	}													
}
