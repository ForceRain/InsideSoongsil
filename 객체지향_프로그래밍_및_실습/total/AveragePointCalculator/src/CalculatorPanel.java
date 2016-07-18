import javax.swing.JPanel;
import java.io.FileReader;
import java.util.Scanner;
import java.io.PrintWriter;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.JTextArea;
import java.util.StringTokenizer;

public class CalculatorPanel extends JPanel 
{	
	private InputSemester inputS;
	private InputPoint inputP;
	private ShowCurrentSemester showCS;
	
	public CalculatorPanel()
	{
		JOptionPane.showMessageDialog(null,"P/F과목은 제외한 성적을 입력해 주십시오.");
		this.setLayout(new BorderLayout());
		
		inputS=new InputSemester();
		inputP=new InputPoint();
		showCS=new ShowCurrentSemester();
		
		inputP.isInput(false);
		this.add(inputS,BorderLayout.NORTH);
		this.add(inputP,BorderLayout.CENTER);
		this.add(showCS,BorderLayout.SOUTH);
	}
	
	class InputSemester extends JPanel
	{
		private JComboBox selectBox;
		private JTextField getInputYear;
		private JTextField getInputSeason;
		private JLabel year;
		private JLabel season;
		private JButton getName;
		private PrintWriter fwrite;
		private ComboListener CListener;
		private ButtonListener BListener;
		
		public InputSemester()
		{
			selectBox=new JComboBox();
			selectBox.addItem("직접입력");
			try
			{
				FileReader fread=new FileReader("semesterData.txt");
				Scanner read=new Scanner(fread);
				while (read.hasNextLine())
				{
					String temp=read.nextLine();
					selectBox.addItem(temp);
				}
				read.close();
				fread.close();
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null,"최초 사용입니다.");
				try
				{
					fwrite=new PrintWriter("semesterData.txt");
				}
				catch (Exception out)
				{
					out.printStackTrace();
				}
			}
			
			CListener=new ComboListener();
			BListener=new ButtonListener();
			
			year=new JLabel("학년도");
			season=new JLabel("학기");
			getInputYear=new JTextField(4);
			getInputSeason=new JTextField(4);
			getName=new JButton("입력");
			
			this.add(selectBox);
			this.add(getInputYear);
			this.add(year);
			this.add(getInputSeason);
			this.add(season);
			this.add(getName);
			
			selectBox.addActionListener(CListener);
			getName.addActionListener(BListener);
			
			String check=(String)selectBox.getSelectedItem();
			if (check.equals("직접입력"))
			{
				getInputYear.setEditable(true);
				getInputSeason.setEditable(true);
				getName.setEnabled(true);
			}
			else
			{
				getInputYear.setEditable(false);
				getInputSeason.setEditable(false);
				getName.setEnabled(false);
			}
		}
		
		class ComboListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				String check=(String)selectBox.getSelectedItem();
				if (check.equals("직접입력"))
				{
					getInputYear.setEditable(true);
					getInputSeason.setEditable(true);
					getName.setEnabled(true);
					inputP.isInput(false);
				}
				else
				{
					inputP.setCurrent(check);
					inputP.setClear();
					inputP.isInput(true);
					getInputYear.setEditable(false);
					getInputSeason.setEditable(false);
					getName.setEnabled(false);
					showCS.showData(check);
				}
			}
		}
		
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				String tmp1=getInputYear.getText();
				String tmp2=getInputSeason.getText();
				
				if (tmp1.equals("") || tmp2.equals(""))
				{
					JOptionPane.showMessageDialog(null,"년도와 학기를 입력하십시오");
					return;
				}
				
				getInputYear.setText("");
				getInputSeason.setText("");
				
				try
				{
					fwrite=new PrintWriter(tmp1+"년 "+tmp2+"학기.txt");
				}
				catch (Exception e)
				{
					System.out.println("Error");
				}
				finally
				{
					JOptionPane.showMessageDialog(null,"생성되었습니다.");
				}
				
				try
				{
					FileReader tmp=new FileReader("semesterData.txt");
					PrintWriter pwrite=new PrintWriter("semesterDatacopy.txt");
					Scanner in=new Scanner(tmp);
					
					while (in.hasNextLine())
					{
						pwrite.println(in.nextLine());
					}
					
					pwrite.println(tmp1+"년 "+tmp2+"학기");
					
					tmp.close();
					in.close();
					pwrite.close();
					
					tmp=new FileReader("semesterDatacopy.txt");
					pwrite=new PrintWriter("semesterData.txt");
					in=new Scanner(tmp);
					
					while (in.hasNextLine())
					{
						pwrite.println(in.nextLine());
					}
					
					tmp.close();
					in.close();
					pwrite.close();
				}
				catch (Exception e)
				{
					System.out.println("BListener Error");
				}
				finally
				{
					check();
				}
			}
		}
		public void check()
		{
			try
			{
				FileReader fread=new FileReader("semesterData.txt");
				Scanner in=new Scanner(fread);
			
				while (in.hasNextLine())
				{
					String tmp=in.nextLine();
					if (!already(tmp))
						selectBox.addItem(tmp);
				}
				
				in.close();
			}
			catch (Exception e)
			{
				System.out.println("check() error");
				e.printStackTrace();
			}
			finally
			{
				JOptionPane.showMessageDialog(null,"목록이 업데이트 되었습니다.");
			}
		}
		
		public boolean already(String input)
		{
			int max=selectBox.getItemCount();
			
			if (max==0)
				return false;
			
			for (int i=0;i<max;i++)
			{
				if (input.equals(selectBox.getItemAt(i)))
					return true;
			}
			
			return false;
		}
		
	}
	
	class InputPoint extends JPanel
	{
		private JLabel currentState;
		private JLabel subjectName;
		private JLabel subjectPoint;
		private JLabel subjectPerform;
		private JTextField nameField;
		private JTextField pointField;
		private JTextField performField;
		private JButton press;
		private JPanel left;
		private ButtonListener BTListener;
		private String currentFile;
		
		public InputPoint()
		{
			currentState=new JLabel("학기를 선택하거나 입력해주세요");
			this.setLayout(new FlowLayout());
			left=new JPanel();
			
			currentFile="";
			subjectName=new JLabel("과목명");
			subjectPoint=new JLabel("학점");
			subjectPerform=new JLabel("받은 학점");
			nameField=new JTextField(10);
			pointField=new JTextField(10);
			performField=new JTextField(10);
			
			BTListener=new ButtonListener();
			press=new JButton("입력완료");
			press.addActionListener(BTListener);
			
			this.add(currentState);
			
			left.add(subjectName);
			left.add(nameField);
			left.add(subjectPoint);
			left.add(pointField);
			left.add(subjectPerform);
			left.add(performField);
			
			this.add(left);
			this.add(press);
		}
		
		public void isInput(boolean changeOK)
		{
			if (changeOK)
			{
				nameField.setEditable(true);
				pointField.setEditable(true);
				performField.setEditable(true);
				press.setEnabled(true);
			}
			else
			{
				nameField.setEditable(false);
				pointField.setEditable(false);
				performField.setEditable(false);
				press.setEnabled(false);
			}
		}
		
		public void setClear()
		{
			nameField.setText("");
			pointField.setText("");
			performField.setText("");
		}
		
		public void setCurrent(String input)	
		{
			currentFile=input;
			currentState.setText("선택된 학기 :"+input);
		}
		
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				String name=nameField.getText();
				String point=pointField.getText();
				String perform=performField.getText();
				
				if (!Character.isDigit(point.charAt(0)))
				{
					JOptionPane.showMessageDialog(null,"학점은 숫자로만 입력되어야 합니다.\n입력된 내용 : "+point);
					return;
				}
				if (Character.isDigit(perform.charAt(0)))
				{
					JOptionPane.showMessageDialog(null,"받은 학점은 알파벳으로만 입력되어야 합니다.\n입력된 내용 : "+perform);
					return;
				}
				
				setClear();
				write(name,point,perform);
				showCS.showData(currentFile);
				showCS.calAverageSemester();
			}
		}
		
		public void write(String name,String point,String perform)
		{			
			ArrayList<String> total=new ArrayList<String>();
			try
			{
				FileReader fread=new FileReader(currentFile+".txt");
				Scanner reader=new Scanner(fread);
				
				while (reader.hasNextLine())
				{
					total.add(reader.nextLine());
				}
				total.add(name+" "+point+" "+perform);
				
				PrintWriter write=new PrintWriter(currentFile+".txt");
								
				for (int i=0;i<total.size();i++)
					write.println(total.get(i));
				
				fread.close();
				reader.close();
				write.close();
				JOptionPane.showMessageDialog(null,"성공적으로 기록되었습니다.");
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null,"InputPointclass에서 write() 실행중 오류 발생");
				e.printStackTrace();
			}
		}
	}
	
	class ShowCurrentSemester extends JPanel
	{
		private JLabel performAverage;
		private JLabel semesterAverage;
		private JTextArea subjectPool;
		private JScrollPane pane;
		private JPanel lower;
		private double sem_totalPnt;
		private double sem_totalPerformPnt;
		private JLabel title;
		
		public ShowCurrentSemester()
		{
			sem_totalPnt=0;
			sem_totalPerformPnt=0;
			
			title=new JLabel("선택한 학기 :: ");
			
			this.setLayout(new BorderLayout());
			
			lower=new JPanel();
			lower.setLayout(new FlowLayout());
			
			subjectPool=new JTextArea(15,3);
			subjectPool.setEditable(false);
			pane=new JScrollPane(subjectPool);
			
			performAverage=new JLabel("평점평균 : ");
			semesterAverage=new JLabel("학기평균 : "+"                        ");
			
			this.setBorder(new TitledBorder(new EtchedBorder(),title.getText()));
			this.add(subjectPool,BorderLayout.NORTH);
			
			lower.add(semesterAverage,FlowLayout.LEFT);
			lower.add(performAverage);
			
			this.add(lower,BorderLayout.SOUTH);
			calAverageSemester();
		}
		
		public void showData(String input)
		{			
			try
			{
				FileReader fread=new FileReader(input+".txt");
				Scanner in=new Scanner(fread);
				String output="";
				String pnt="";
				String perPnt="";
				
				double temp=0;
				sem_totalPnt=0;
				sem_totalPerformPnt=0;
				
				while (in.hasNextLine())
				{
					StringTokenizer cut=new StringTokenizer(in.nextLine());
					
					output+="과목명 :";
					output+=cut.nextToken();
					output+="  학점 : ";
					
					pnt=cut.nextToken();
					temp=Double.parseDouble(pnt);
					sem_totalPnt+=temp;
					output+=pnt;
					
					try
					{
						output+="  받은 학점 : ";
						perPnt=cut.nextToken();
						sem_totalPerformPnt+=temp*alphaToNum(perPnt);					
						output+=perPnt;
						output+="\n";
					}
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(null,"받은 학점 형식이 잘못 되었습니다.");
						e.printStackTrace();
					}
				}
				
				in.close();
				
				if (sem_totalPnt==0)
					semesterAverage.setText("학기평균 : 0.0                        ");
				else
					semesterAverage.setText("학기평균 : "+sem_totalPerformPnt/sem_totalPnt+"                        ");
				
				subjectPool.setText(output);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null,"ShowCurrentSemesterclass에서 showData() 실행중 오류 발생");
				e.printStackTrace();
			}
		}
		
		public double alphaToNum(String input)
		{
			if (input.equals("A+"))
				return 4.5;
			if (input.equals("A0"))
				return 4.3;
			if (input.equals("A-"))
				return 4.0;
			if (input.equals("B+"))
				return 3.5;
			if (input.equals("B0"))
				return 3.3;
			if (input.equals("B-"))
				return 3.0;
			if (input.equals("C+"))
				return 2.5;
			if (input.equals("C0"))
				return 2.3;
			if (input.equals("C-"))
				return 2.0;
			if (input.equals("D+"))
				return 1.5;
			if (input.equals("D0"))
				return 1.3;
			if (input.equals("D-"))
				return 1.0;
			
			return 0.0;
		}
		
		public void calAverageSemester()
		{
			ArrayList<String> filename=new ArrayList<String>();
			double tmp;
			double totalPnt=0;
			double totalCalPnt=0;
			
			try
			{
				FileReader fread=new FileReader("semesterData.txt");
				Scanner read=new Scanner(fread);
				
				while (read.hasNextLine())
				{
					filename.add(read.nextLine());
				}
		
				fread.close();
				read.close();
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null,"ShowCurrentSemesterclass에서 calAverageSemester() 실행중 오류 발생");
				e.printStackTrace();
			}
			
			for (int i=0;i<filename.size();i++)
			{
				try
				{
					FileReader fr=new FileReader(filename.get(i)+".txt");
					Scanner reader=new Scanner(fr);
										
					while (reader.hasNext())
					{
						reader.next();
						tmp=Double.parseDouble(reader.next());
						totalPnt+=tmp;
						totalCalPnt+=tmp*alphaToNum(reader.next());
					}
					
					fr.close();
					reader.close();
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null,"ShowCurrentSemesterclass에서 calAverageSemester() 실행중 오류 발생");
					e.printStackTrace();
				}
			}
			
			if (totalPnt==0)
				performAverage.setText("평점평균 : 0.0");
			else
				performAverage.setText("평점평균 : "+totalCalPnt/totalPnt);
		}
	}
	
}
