import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import java.awt.Dimension;
import javax.sound.sampled.*;
import java.io.File;
import javax.swing.JButton;
import java.util.Random;

public class SelectPanel extends JPanel
{
	private JRadioButton Bull;
	private JRadioButton Lion;
	private JRadioButton Elephant;
	private JRadioButton Bear;
	private JRadioButton Frog;
	private JRadioButton Gorilla;	
	
	private JComboBox SelectMusic;
	private String CurrentAnimal;
	private ButtonGroup BtnGroup;
	private RadioListener RDListener;
	private ComboListener CBListener;
	private FeverListener FVListener;
	
	private JPanel AnimalSelectPanel;
	private JPanel SoundSelectPanel;
	private JPanel BackgroundSelectPanel;
	private JPanel FeverTimePanel;
	
	private JButton FeverBtn;	
	private JButton backChange;
	private JButton soundOn;
	private ButtonListener BTListener;
	
	private File inFile;
	private AudioInputStream audioStream;
	private Clip stream;
	private Clip Feverstream;

	private String ChooseAnimal;
	private String ChooseMusic;
	private boolean currentMusicState;
	private boolean CurrentMovingState;
	
	private int backGroundNum;
	private BackgroundChange BGChange;
	
	public SelectPanel()
	{
		ChooseAnimal="Choose Animal!";
		ChooseMusic="Choose Music!";
		
		AnimalSelectPanel=new JPanel();
		SoundSelectPanel=new JPanel();
		BackgroundSelectPanel=new JPanel();
		FeverTimePanel=new JPanel();
		
		Bull=new JRadioButton("Bull");
		Lion=new JRadioButton("Lion");
		Elephant=new JRadioButton("Moon-walk Elephant");
		Bear=new JRadioButton("Hooray Bear");
		Frog=new JRadioButton("Frog");
		Gorilla=new JRadioButton("Gorilla");		
		
		RDListener=new RadioListener();
		CBListener=new ComboListener();
		SelectMusic=new JComboBox();

		BTListener=new ButtonListener();
		soundOn=new JButton("Music on/off");
		soundOn.addActionListener(BTListener);
		
		backChange=new JButton("ChangeBackground!");
		BGChange=new BackgroundChange();
		backChange.addActionListener(BGChange);
		
		FeverBtn=new JButton("Let's Move!");
		FVListener=new FeverListener();
		FeverBtn.addActionListener(FVListener);
		
		CurrentAnimal="Bull";
		currentMusicState=true;
		
		BtnGroup=new ButtonGroup();
		BtnGroup.add(Bull);
		BtnGroup.add(Lion);
		BtnGroup.add(Elephant);
		BtnGroup.add(Bear);
		BtnGroup.add(Frog);
		BtnGroup.add(Gorilla);
		
		SelectMusic.addActionListener(CBListener);
		SelectMusic.addItem("BlaBlaSong");
		SelectMusic.addItem("NiceMusic");
		
		AnimalSelectPanel.setPreferredSize(new Dimension(500,70));
		AnimalSelectPanel.setBorder(new TitledBorder(new EtchedBorder(),ChooseAnimal));
		AnimalSelectPanel.add(Bull);
		AnimalSelectPanel.add(Lion);
		AnimalSelectPanel.add(Elephant);
		AnimalSelectPanel.add(Bear);
		AnimalSelectPanel.add(Frog);
		AnimalSelectPanel.add(Gorilla);
		
		SoundSelectPanel.setPreferredSize(new Dimension(230,70));
		SoundSelectPanel.setBorder(new TitledBorder(new EtchedBorder(),ChooseMusic));
		SoundSelectPanel.add(SelectMusic);
		SoundSelectPanel.add(soundOn);
		
		BackgroundSelectPanel.setPreferredSize(new Dimension(170,70));
		BackgroundSelectPanel.setBorder(new TitledBorder(new EtchedBorder(),"Change Background!"));
		BackgroundSelectPanel.add(backChange);
		
		FeverTimePanel.setPreferredSize(new Dimension(120,70));
		FeverTimePanel.setBorder(new TitledBorder(new EtchedBorder(),"FEVERTIME"));
		FeverTimePanel.add(FeverBtn);
		
		Bull.addActionListener(RDListener);
		Lion.addActionListener(RDListener);
		Elephant.addActionListener(RDListener);
		Bear.addActionListener(RDListener);
		Frog.addActionListener(RDListener);
		Gorilla.addActionListener(RDListener);
		
		Bull.setSelected(true);
		
		this.add(AnimalSelectPanel);
		this.add(SoundSelectPanel);
		this.add(BackgroundSelectPanel);
		this.add(FeverTimePanel);
		
		backGroundNum=0;
		CurrentMovingState=true;
		
		try
		{
			File tmp=new File("YodellingSong.wav");
			
			AudioInputStream atmp=AudioSystem.getAudioInputStream(tmp);
			Feverstream=AudioSystem.getClip();
			Feverstream.open(atmp);
		}
		catch (Exception e)
		{}
	}

	class FeverListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (CurrentMovingState)
			{
				FeverBtn.setText("Relax....");
				CurrentMovingState=false;					//false==FeverTime
				feverMusic();
			}
			else
			{
				FeverBtn.setText("Let's Move!");
				CurrentMovingState=true;					//true==normal moving
				feverMusic();
			}
		}
	}
	
	class BackgroundChange implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (backGroundNum==4)
				backGroundNum-=4;
			
			backGroundNum++;
			if (backGroundNum==1)
				backChange.setText("Palm tree & Orangutan");
			if (backGroundNum==2)
				backChange.setText("Mountain with Valley");
			if (backGroundNum==3)
				backChange.setText("Village");
			if (backGroundNum==4)
				backChange.setText("Farm with Chickens");
		}
	}

	class RadioListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (Bull.isSelected())
				CurrentAnimal="Bull";
			if (Lion.isSelected())
				CurrentAnimal="Lion";
			if (Elephant.isSelected())
				CurrentAnimal="Elephant";
			if (Bear.isSelected())
				CurrentAnimal="Bear";
			if (Frog.isSelected())
				CurrentAnimal="Frog";
			if (Gorilla.isSelected())
				CurrentAnimal="Gorilla";
		}
	}
	
	class ComboListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String tmp=(String)SelectMusic.getSelectedItem();
			ChangeMusic(tmp);
		}
	}
	
	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (currentMusicState)
			{
				currentMusicState=false;
				soundOn.setText("Play Music");
				stream.stop();
			}
			else if (!currentMusicState && CurrentMovingState)
			{
				currentMusicState=true;
				soundOn.setText("Stop Music");
				stream.start();
			}
		}
	}
	
	public int getBackNum()
	{
		return backGroundNum;
	}
	
	public void feverMusic()
	{
		if (!CurrentMovingState)
		{
			stream.stop();
			Feverstream.start();
		}
		else
		{
			Feverstream.stop();
			if (currentMusicState)
				stream.start();
		}
	}
	
	public boolean getMovingState()
	{
		return CurrentMovingState;
	}
	
	public void ChangeMusic(String Filename)
	{
		try
		{
			inFile=new File(Filename+".wav");
			audioStream=AudioSystem.getAudioInputStream(inFile);
			
			if (stream==null)
				stream=AudioSystem.getClip();
			
			Clip tmpstream=AudioSystem.getClip();
			
			if (stream.isRunning())
				stream.stop();
			
			tmpstream.open(audioStream);
			tmpstream.start();
			
			currentMusicState=true;
			soundOn.setText("Stop Music");
			
			stream=tmpstream;			
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	public String WhatAnimal()
	{
		return CurrentAnimal;
	}
	
	public int getRandNum(int input)
	{
		Random rand=new Random();
		return rand.nextInt(input-5)+5;
	}
}
