import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

class Test {
	public static void main(String[] args) throws Exception
	{
		URL imageLocation=new URL("http://horstmann.com/java4everyone/duke.gif");
		
		JOptionPane.showMessageDialog(null,"Greetings!","�λ��Ѵ�",JOptionPane.PLAIN_MESSAGE,new ImageIcon(imageLocation));
	}
}
