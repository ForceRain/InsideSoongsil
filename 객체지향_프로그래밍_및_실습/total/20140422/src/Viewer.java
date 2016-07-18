import javax.swing.JFrame;

public class Viewer {
	public static void main(String[] args)
	{
		JFrame frame =new JFrame();
		ComboClass_1 compo=new ComboClass_1();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(compo);
		
		frame.setSize(400,400);
		frame.setVisible(true);
	}
}
