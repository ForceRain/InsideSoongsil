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

public class SelectPanel extends JPanel
{
	private JRadioButton Bull;
	private JRadioButton Lion;
	private JRadioButton temp1;
	private JRadioButton temp2;
	private JRadioButton temp3;
	private JRadioButton temp4;	
	
	private JComboBox SelectMusic;
	private String CurrentAnimal;
	private ButtonGroup BtnGroup;
	private RadioListener RDListener;
	private ComboListener CBListener;
	
	private JPanel AnimalSelectPanel;
	private JPanel SoundSelectPanel;
	private JPanel BackgroundSelectPanel;
	
	private JButton backChange;
	private JButton soundOn;
	private ButtonListener BTListener;
	private File inFile;
	private AudioInputStream audioStream;
	private Clip stream;

	private String ChooseAnimal;
	private String ChooseMusic;
	private boolean currentMusicState;
	
	private int backGroundNum;
	private BackgroundChange BGChange;
	
	public SelectPanel()
	{
		ChooseAnimal="Choose Animal!";
		ChooseMusic="Choose Music!";
		
		AnimalSelectPanel=new JPanel();
		SoundSelectPanel=new JPanel();
		BackgroundSelectPanel=new JPanel();
		
		Bull=new JRadioButton("Bull");
		Lion=new JRadioButton("Lion");
		temp1=new JRadioButton("temp1");
		temp2=new JRadioButton("temp2");
		temp3=new JRadioButton("temp3");
		temp4=new JRadioButton("temp4");		
		
		RDListener=new RadioListener();
		CBListener=new ComboListener();
		SelectMusic=new JComboBox();

		BTListener=new ButtonListener();
		soundOn=new JButton("Music on/off");
		soundOn.addActionListener(BTListener);
		
		backChange=new JButton("ChangeBackground!");
		BGChange=new BackgroundChange();
		backChange.addActionListener(BGChange);
		
		CurrentAnimal="Bull";
		currentMusicState=true;
		
		BtnGroup=new ButtonGroup();
		BtnGroup.add(Bull);
		BtnGroup.add(Lion);
		BtnGroup.add(temp1);
		
		SelectMusic.addActionListener(CBListener);
		SelectMusic.addItem("crazyMusic");
		SelectMusic.addItem("YodellingSong");
		
		AnimalSelectPanel.setPreferredSize(new Dimension(500,70));
		AnimalSelectPanel.setBorder(new TitledBorder(new EtchedBorder(),ChooseAnimal));
		AnimalSelectPanel.add(Bull);
		AnimalSelectPanel.add(Lion);
		AnimalSelectPanel.add(temp1);
		AnimalSelectPanel.add(temp2);
		AnimalSelectPanel.add(temp3);
		AnimalSelectPanel.add(temp4);
		
		SoundSelectPanel.setPreferredSize(new Dimension(300,70));
		SoundSelectPanel.setBorder(new TitledBorder(new EtchedBorder(),ChooseMusic));
		SoundSelectPanel.add(SelectMusic);
		SoundSelectPanel.add(soundOn);
		
		BackgroundSelectPanel.setPreferredSize(new Dimension(200,70));
		BackgroundSelectPanel.setBorder(new TitledBorder(new EtchedBorder(),"Change Background!"));
		BackgroundSelectPanel.add(backChange);
		
		Bull.addActionListener(RDListener);
		Lion.addActionListener(RDListener);
		temp1.addActionListener(RDListener);
		temp2.addActionListener(RDListener);
		temp3.addActionListener(RDListener);
		temp4.addActionListener(RDListener);
		
		Bull.setSelected(true);
		
		this.add(AnimalSelectPanel);
		this.add(SoundSelectPanel);
		this.add(BackgroundSelectPanel);
		
		backGroundNum=0;
	}
	
	class BackgroundChange implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (backGroundNum==3)
				backGroundNum-=3;
			
			backGroundNum++;
			System.out.println("backGroundNum : "+backGroundNum);
		}
	}
	
	public int getBackNum()
	{
		return backGroundNum;
	}
	
	class RadioListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (Bull.isSelected())
				CurrentAnimal="Bull";
			if (Lion.isSelected())
				CurrentAnimal="Lion";
			//if (Gorilla.isSelected())
			//	CurrentAnimal="Gorilla";
		}
	}
	
	class ComboListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String tmp=(String)SelectMusic.getSelectedItem();
			System.out.println(tmp);
			ChangeMusic(tmp);
		}
	}
	
	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (currentMusicState==true)
			{
				currentMusicState=false;
				soundOn.setText("Play Music");
				stream.stop();
			}
			else
			{
				currentMusicState=true;
				soundOn.setText("Stop Music");
				stream.start();
			}
		}
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
			System.out.println("Go?");
			
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
}
