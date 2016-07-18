import javax.swing.JFrame;

public class RecMain {
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		
		frame.setSize(500,500);
		frame.setTitle("Random Rectangle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		RectangleCom component=new RectangleCom();
		frame.add(component);
		
		CircleCom component1=new CircleCom();
		frame.add(component1);
		
		frame.setVisible(true);
	}
}
