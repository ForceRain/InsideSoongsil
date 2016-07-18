import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import java.awt.Dimension;
import javax.sound.sampled.*;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;

public class SelectPanel extends JPanel
{
	private JRadioButton Bull;
	private JRadioButton Lion;
	//private JRadioButton Gorilla;
	private JLabel musicState;
	private JComboBox SelectMusic;
	private String CurrentAnimal;
	private ButtonGroup BtnGroup;
	private RadioListener RDListener;
	private ComboListener CBListener;
	private JPanel AnimalSelectPanel;
	private JPanel SoundSelectPanel;
	private JButton soundOn;
	private ButtonListener BTListener;
	private File inFile;
	private AudioInputStream audioStream;
	private Clip stream;

	//private String crazyMusic;
	//private String anotherMusic;
	private boolean currentMusicState;
	
	public SelectPanel()
	{
		AnimalSelectPanel=new JPanel();
		SoundSelectPanel=new JPanel();
		Bull=new JRadioButton("Bull");
		Lion=new JRadioButton("Lion");
		//Gorilla=new JRadioButton("Gorilla");
		RDListener=new RadioListener();
		CBListener=new ComboListener();
		SelectMusic=new JComboBox();
		musicState=new JLabel();
		
		BTListener=new ButtonListener();
		soundOn=new JButton("Music on/off");
		soundOn.addActionListener(BTListener);
		
		CurrentAnimal="Bull";
		currentMusicState=true;
		
		BtnGroup=new ButtonGroup();
		BtnGroup.add(Bull);
		BtnGroup.add(Lion);
		//BtnGroup.add(Gorilla);
		
		SelectMusic.addActionListener(CBListener);
		SelectMusic.addItem("crazyMusic");
		SelectMusic.addItem("anothercrazyMusic");
		
		musicState.setText("Music is playing...");
		
		AnimalSelectPanel.setPreferredSize(new Dimension(600,70));
		AnimalSelectPanel.setBorder(new TitledBorder(new EtchedBorder(),"Choose Animal!"));
		AnimalSelectPanel.add(Bull);
		AnimalSelectPanel.add(Lion);
		//AnimalSelectPanel.add(Gorilla);
		
		SoundSelectPanel.setPreferredSize(new Dimension(400,70));
		SoundSelectPanel.setBorder(new TitledBorder(new EtchedBorder(),"Choose Music!"));
		SoundSelectPanel.add(SelectMusic);
		SoundSelectPanel.add(soundOn);
		SoundSelectPanel.add(musicState);
		
		Bull.addActionListener(RDListener);
		Lion.addActionListener(RDListener);
		//Gorilla.addActionListener(RDListener);
		
		Bull.setSelected(true);
		
		
		this.add(AnimalSelectPanel);
		this.add(SoundSelectPanel);
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
				musicState.setText("Music stopped");
				stream.stop();
			}
			else
			{
				currentMusicState=true;
				musicState.setText("Music is playing...");
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
			musicState.setText("Music is playing...");
			
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
