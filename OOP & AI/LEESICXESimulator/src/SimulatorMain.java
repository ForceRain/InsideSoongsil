import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * Project구현에 있어서 크게 4부분을 담고 있는 클래스이다.
 * 내부적으로 visual simulator,resource manager, loader, sic simulator의 클래스를 담고 있다. 
 *
 */
public class SimulatorMain {
	private static SimulatorPanel myPanel=new SimulatorPanel();			// visual simulator.
	private static SimulatorResourceManager myRManager=new SimulatorResourceManager();	// resource manager.
	private static SimulatorLoader myLoader=new SimulatorLoader();	// sic loader.
	private static SimulatorSIC mySimulator=new SimulatorSIC();		// sic simulator.
	private static InstTable OPTAB=new InstTable();					// 명령어에 대한 mnemonic등의 정보를 담은 OPTAB
	private static int programLoadAddr=0;							// 프로그램이 로드되는 시작 주소. 0으로 지정.
	private static externalDefTable ESYMTAB=new externalDefTable();			// external symbol table.
	private static modificationTable Moditab=new modificationTable();		// modification table.
	private static RegisterSet registerSet;									// sic,sic/xe machine의 레지스터 모음.
	private static ArrayList<Integer> programStart=new ArrayList<Integer>();	// 프로그램 시작 주소.
	private static InstructionTable iTab=new InstructionTable();		// parsing한 instruction을 담을 container
	private static int returnProgramAddr=0x00002431;				// 현재 프로그램이 마치고 되돌아갈 지점.
	private static String currentFileName="No File";				// 현재 test device에서 만질 파일.

	/**
	 * main함수. Frame을 관리한다.
	 * @param args
	 */
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		try {
			  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}
		frame.setSize(700,1000);									// frame 설정.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Sic/XE Simulator");
		
		frame.add(myPanel);
		frame.setVisible(true);
	}
	
	/**
	 * visual simulator역할을 하는 클래스.
	 *
	 */
	public static class SimulatorPanel extends JPanel
	{
		private JPanel inputSpace;							// file dialog를 위한 button이 위치한 panel.
		private JPanel headerRecord;						// header record부분의 panel
		private JPanel register;							// register부분의 panel
		private JPanel registerXE;							// registerXE부분의 panel
		private JPanel endRecord;							// end record부분의 panel
		private JPanel log;									// log부분의 panel
		private JPanel leftPanel;							// 중심 기준 왼쪽 부분내용을 담을 panel
		private JPanel rightPanel;							// 중심 기준 오른쪽 부분내용을 담을 panel
		private JTextArea logArea=new JTextArea(15,3);		// log내용을 나타낼 부분.
		
		private JTextField fileName=new JTextField(10);			// file이름을 나타낼 부분.
		private JTextField programName=new JTextField(8);			// program이름을 나타낼 부분.
		private JTextField startAddrProgram=new JTextField(7);		// 프로그램 시작 주소를 나타낼 부분.
		private JTextField programLength=new JTextField(7);			// 프로그램 길이를 나타낼 부분.
		
		private JTextField addressOfFirstInst=new JTextField(7);			// 첫번째 명령어의 주소를 나타낼 부분.
		private JTextField startAddressinMemory=new JTextField(8);			// 메모리의 시작 주소를 나타낼 부분.
		private JTextField targetAddress=new JTextField(8);					// target주소를 나타낼 부분. 3,4형식에 대해서 바뀐다.
		
		private JButton oneStepBtn=new JButton("실행 (1 step)");			// 한 단계만 진행하는 버튼.
		private JButton allStepBtn=new JButton("실행 (All)");				// 전부 다 진행하는 버튼.
		private JButton quitBtn=new JButton("종료");						// 프로그램을 종료하는 버튼.
		private JButton currentStatusBtn=new JButton("현재 메모리 상태");		// 현재 메모리 상태를 보여주는 버튼.
		private JButton fileOpenBtn=new JButton("File open");
		
		private JTextField ADecimal=new JTextField(5);
		private JTextField AHexa=new JTextField(5);
		
		private JTextField XDecimal=new JTextField(5);
		private JTextField XHexa=new JTextField(5);
		
		private JTextField LDecimal=new JTextField(5);
		private JTextField LHexa=new JTextField(5);
		
		private JTextField PCDecimal=new JTextField(5);
		private JTextField PCHexa=new JTextField(5);
		
		private JTextField BDecimal=new JTextField(5);
		private JTextField BHexa=new JTextField(5);
		
		private JTextField SDecimal=new JTextField(5);
		private JTextField SHexa=new JTextField(5);
		
		private JTextField TDecimal=new JTextField(5);
		private JTextField THexa=new JTextField(5);				// 각 레지스터 별 10진수, 16진수표기를 나타낼 부분.
		
		private JTextField StatusWord=new JTextField(11);		// status word는 큼,같음,작음을 나타내는 flag용도로만 사용. 1이 큼, 2가 같음, 3이 작음.
		private JTextField Floating=new JTextField(11);
		
		private JTextField currentDevice=new JTextField(6);		// 사용중인 장치를 나타내는 필드.
		
		private JList instPool;									// parsing한 instruction들을 나타낼 부분.
		
		public SimulatorPanel(){
			initialize();
		}

		public void allStep() {
			// 남은 명령어를 모두 실행하는 메소드.
			JOptionPane.showMessageDialog(null, "All step진행이 완료 되었습니다.");
		}

		public void oneStep() {
			// 하나의 명령어만 실행하는 메소드.
			PoolInstruction line=mySimulator.getCurLine();		// 현재 진행중인 명령어를 location counter와 같이 받아옴.
			int location=0;
			for (int i=0;i<instPool.getModel().getSize();i++){				// instPool에 대해서 탐색.
				PoolInstruction temp=(PoolInstruction)instPool.getModel().getElementAt(i);
				if (temp.line.equals(line.line) && temp.locationCounter==line.locationCounter){
					location=i;										// 둘다 일치하면 index저장.
					break;
				}
			}
			instPool.setSelectedIndex(location);				// 그 위치로 focus이동.
			instPool.ensureIndexIsVisible(location);
			repaint();											// 다시 그리기 요청.
		}
		public void initialize() {
			// 시뮬레이터 동작을 위해 세팅진행. 로더 수행. 로드된 값을 읽음.
			// 하나의 스텝, 전체 실행이 가능한 상태로 대기하게 만든다.
			class buttonListener implements ActionListener{
				public void actionPerformed(ActionEvent e){
					JFileChooser chooser=new JFileChooser();			// file선택을 위한 fileChooser클래스.
					File file;
					mySimulator.initialize(new File("inst.data.txt"));	// OPTAB초기화.
					int returnVal=chooser.showOpenDialog(null);
					if (returnVal==JFileChooser.APPROVE_OPTION){		// file이 선택 되었다면,
						programLoadAddr=0;								// load되는 부분을 0번지라고 가정.
						file=chooser.getSelectedFile();					// 선택한 file을 받아옴.
						fileName.setText(chooser.getName(file));		// file이름 field에 이름을 설정.
						myLoader.load(file);
						currentDevice.setText(currentFileName);			// input device와 output device의 내용을 설정.
						oneStepBtn.setEnabled(true);
						allStepBtn.setEnabled(true);
						currentStatusBtn.setEnabled(true);
						addressOfFirstInst.setText(String.format("%06X",programLoadAddr));
						startAddressinMemory.setText(String.format("%06X",programLoadAddr));	// 첫 명령어주소, memory에서 위치 초기화.
						myRManager.initializeMemory();
						myRManager.initializeRegister();
						repaint();
					}
				}
			}
			class exitListener implements ActionListener{			// 종료버튼을 누르면, GUI와 프로그램을 종료하게함.
				public void actionPerformed(ActionEvent e){
					System.exit(0);
				}
			}
			class oneStepListener implements ActionListener{
				public void actionPerformed(ActionEvent e){
					mySimulator.oneStep();
					oneStep();
					mySimulator.addLog();
					repaint();
				}
			}
			class allStepListener implements ActionListener{
				public void actionPerformed(ActionEvent e){
					mySimulator.allStep();
					allStep();
					mySimulator.addLog();
					repaint();
				}
			}
			class memoryShowListener implements ActionListener{
				public void actionPerformed(ActionEvent e){
					myRManager.currentStatus();
				}
			}
			currentStatusBtn.setEnabled(false);
			oneStepBtn.setEnabled(false);
			allStepBtn.setEnabled(false);							// 초기에는 작동 못하도록 제한.
			quitBtn.addActionListener(new exitListener());
			oneStepBtn.addActionListener(new oneStepListener());
			allStepBtn.addActionListener(new allStepListener());			// button listener 세팅.
			currentStatusBtn.addActionListener(new memoryShowListener());
			registerSet=new RegisterSet();
			
			String[] wp={"파일을","열어주세요."};
			instPool=new JList(wp);
			JScrollPane instPoolScr=new JScrollPane(instPool);				// instruction pool 초기화.
						
			this.setLayout(new BorderLayout());			// layout 설정.
			
			leftPanel=new JPanel();							// 좌측 패널.
			rightPanel=new JPanel();						// 우측 패널.
			inputSpace=new JPanel();
			inputSpace.setLayout(new FlowLayout());
			inputSpace.add(new JLabel("FileName :"));
			inputSpace.add(fileName);
			
			fileOpenBtn.addActionListener(new buttonListener());
			inputSpace.add(fileOpenBtn);
			
			leftPanel.setLayout(new GridLayout(3,1));
			rightPanel.setLayout(new GridLayout(1,1));
			
			headerRecord=new JPanel();
			headerRecord.setBorder(new TitledBorder("H(Header Record)"));
			headerRecord.setLayout(new GridLayout(3,1));
			JPanel temp=new JPanel();
			temp.add(new JLabel("Program Name :"));
			temp.add(programName);
			headerRecord.add(temp);
			temp=new JPanel();
			temp.add(new JLabel("<html>Start Address<br> of Object Program :</html>"));
			temp.add(startAddrProgram);
			headerRecord.add(temp);
			temp=new JPanel();
			temp.add(new JLabel("Length of Program :"));
			temp.add(programLength);
			headerRecord.add(temp);
			
			register=new JPanel();
			register.setBorder(new TitledBorder("Register"));
			register.setLayout(new GridLayout(6,3));
			register.add(new JLabel("	"));
			register.add(new JLabel("Dec"));
			register.add(new JLabel("Hex"));
			register.add(new JLabel("A (#0) "));
			register.add(ADecimal);
			register.add(AHexa);
			registerSet.insert(0, ADecimal, AHexa);
			register.add(new JLabel("X (#1) "));
			register.add(XDecimal);
			register.add(XHexa);
			registerSet.insert(1, XDecimal, XHexa);
			register.add(new JLabel("L (#2) "));
			register.add(LDecimal);
			register.add(LHexa);
			registerSet.insert(2, LDecimal, LHexa);
			register.add(new JLabel("PC (#8) "));
			register.add(PCDecimal);
			register.add(PCHexa);
			registerSet.insert(8, PCDecimal, PCHexa);
			register.add(new JLabel("SW (#9) "));
			register.add(StatusWord);
			registerSet.insert(9, StatusWord);
			
			registerXE=new JPanel();
			registerXE.setBorder(new TitledBorder("Register(for XE)"));
			registerXE.setLayout(new GridLayout(5,3));
			registerXE.add(new JLabel("	"));
			registerXE.add(new JLabel("Dec"));
			registerXE.add(new JLabel("Hex"));
			registerXE.add(new JLabel("B (#3) "));
			registerXE.add(BDecimal);
			registerXE.add(BHexa);
			registerSet.insert(3, BDecimal, BHexa);
			registerXE.add(new JLabel("S (#4) "));
			registerXE.add(SDecimal);
			registerXE.add(SHexa);
			registerSet.insert(4, SDecimal, SHexa);
			registerXE.add(new JLabel("T (#5) "));
			registerXE.add(TDecimal);
			registerXE.add(THexa);
			registerSet.insert(5, TDecimal, THexa);
			registerXE.add(new JLabel("F (#6) "));
			registerXE.add(Floating);
			registerSet.insert(6, Floating);
			
			endRecord=new JPanel();
			endRecord.setBorder(new TitledBorder("E (End Record)"));
			endRecord.setLayout(new GridLayout(7,1));
			temp=new JPanel();
			temp.add(new JLabel("<html>Address of <br>First Instruction :</html>"));
			temp.add(addressOfFirstInst);
			endRecord.add(temp);
			temp=new JPanel();
			temp.add(new JLabel("<html>Start Address<br> in Mem :</html>"));
			temp.add(startAddressinMemory);
			endRecord.add(temp);
			temp=new JPanel();
			temp.add(new JLabel("Target Address :"));
			temp.add(targetAddress);
			endRecord.add(temp);
			temp=new JPanel();
			temp.setLayout(new GridLayout(1,2));
			temp.add(new JLabel("Instructions :"));
			temp.add(instPoolScr);
			endRecord.add(temp);
			temp=new JPanel();
			temp.setLayout(new FlowLayout());
			temp.add(new JLabel("사용중인 장치 : "));
			temp.add(currentDevice);
			endRecord.add(temp);
			temp=new JPanel();
			temp.add(oneStepBtn);
			temp.add(allStepBtn);
			temp.add(quitBtn);
			endRecord.add(temp);
			endRecord.add(currentStatusBtn);
			
			log=new JPanel();
			log.setBorder(new TitledBorder("Log (명령어 수행 관련) :"));
			log.setLayout(new BorderLayout());
			JScrollPane jp=new JScrollPane(logArea);
			log.add(jp);
			logArea.setEditable(false);
			
			this.add(inputSpace,BorderLayout.NORTH);
			leftPanel.add(headerRecord);
			leftPanel.add(register);
			leftPanel.add(registerXE);
			rightPanel.add(endRecord);
			this.add(leftPanel,BorderLayout.CENTER);
			this.add(rightPanel,BorderLayout.EAST);
			this.add(log,BorderLayout.SOUTH);
		}
	}
	public static class SimulatorSIC{
		private boolean readEnd=false;
		private String addToLog="";
		private String currentLine;
		private int currentLocctr;
		int seqZeroCnt=0;
		
		public void addLog() {
			// 실행 결과를 Log에 더해주는 메소드.
			String t=myPanel.logArea.getText();
			myPanel.logArea.setText(t+'\n'+addToLog);
		}

		public void allStep() {
			// 남은 명령어 모두 수행. object code를 모두 수행하고 난 값의 변화를 보여준다. 실질적 동작.
			while (doAction());
			addToLog="All Step 실행이 완료되었습니다.\n재 실행을 원하시면 프로그램을 재시작 해주세요.";
			myPanel.allStepBtn.setEnabled(false);
			myPanel.oneStepBtn.setEnabled(false);
			myPanel.fileOpenBtn.setEnabled(false);
		}

		public void initialize(File fileName) {
			// 시뮬레이터 동작을 위해서 세팅, 초기화 작업 진행. inst.data file을 읽고 담아둔다.
			OPTAB.initialize(fileName);
		}

		public void oneStep() {
			// 하나의 명령어만 수행. 수행 된 후의 변화를 보여주며, 다음 명령어를 가리킨다. 실질적 동작.
			if (!doAction())			// 반환값이 false면 프로그램 실행 완료.
			{
				JOptionPane.showMessageDialog(null,"One step실행이 완료되었습니다.");			
				addToLog="One Step 실행이 완료되었습니다.\n재 실행을 원하시면 프로그램을 재시작 해주세요.";
				myPanel.allStepBtn.setEnabled(false);
				myPanel.oneStepBtn.setEnabled(false);
				myPanel.fileOpenBtn.setEnabled(false);
			}
		}
		public PoolInstruction getCurLine(){
			return new PoolInstruction(currentLocctr,currentLine);
		}
		/**
		 * instruction하나에 대해서 진행하는 함수.
		 * @return 다음 명령어가 없으면 false, 있다면 true반환.
		 */
		public boolean doAction(){
			myPanel.currentDevice.setText(currentFileName);
			int currentPC=registerSet.getRegister("PC").getValue();
			if (currentPC==returnProgramAddr)			// 만약 현재 program counter가 돌아갈 program counter라면,
				return false;
			
			InstructionData tmp=iTab.getProperty(currentPC);
			currentLine=tmp.getObjCode();
			currentLocctr=tmp.getLocationCounter();
			int nextPC=currentPC+tmp.getFormat();					// base-relative없이 pc-relative만 있다고 가정.
			String mnemonic=OPTAB.getMnemonic(tmp.getObjectCode());	// OPTAB에서 정보를 받아옴.
			addToLog=OPTAB.getComment(tmp.getObjectCode());
			registerSet.getRegister("PC").setValue(currentPC+tmp.getFormat());
			
			if (tmp.getFormat()==4)
				nextPC=0;									// 4형식이라면 offset주소를 그대로 사용하기 위해서 값을 설정하지 않는다.
			
			if (mnemonic.equals("ADD")){
				int targetAddr;
				if (tmp.getFormat()!=4)
					targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;		// 4형식이 아니라면 더해주고,
				else
					targetAddr=Integer.parseInt(tmp.getOffset(),16);			// 4형식이면 더하지 않음.
				
				if (!tmp.isIndirect() && tmp.isImmediate())					// immediate addressing.
					targetAddr=Integer.parseInt(tmp.getOffset(),16);
				
				if (tmp.isIndirect() && !tmp.isImmediate()){ 				// indirect addressing.
					targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
					byte[] data=myRManager.getMemory(targetAddr, 3);
					int tt=0;
					for (int i=0;i<3;i++){
						tt<<=8;
						tt|=data[i];
					}
					myPanel.targetAddress.setText(String.format("%06X",targetAddr));
					myRManager.setRegister("A", myRManager.getRegister("A")+tt);
					return true;
				}
				byte[] oper=myRManager.getMemory(targetAddr,3);
				int tt=0;
				for (int i=0;i<3;i++){
					tt<<=8;
					tt|=oper[i];
				}
				tt=tt&0x00FFFFFF;
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("A", myRManager.getRegister("A")+tt);
			} else if (mnemonic.equals("ADDF")){
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=myRManager.getMemory(targetAddr, 6);
				long tt=0;
				for (int i=0;i<6;i++){
					tt<<=8;
					tt|=oper[i];
				}
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("F",myRManager.getRegister("F")+(double)tt);
			} else if (mnemonic.equals("ADDR")){
				String r1=tmp.getObjCode().substring(2,3);
				String r2=tmp.getObjCode().substring(3,4);
				
				myRManager.setRegister(Integer.parseInt(r2),myRManager.getRegister(Integer.parseInt(r1))+
						myRManager.getRegister(Integer.parseInt(r2)));
			} else if (mnemonic.equals("AND")){
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=myRManager.getMemory(targetAddr,3);
				int tt=0;
				for (int i=0;i<3;i++){
					tt<<=8;
					tt|=oper[i];
				}
				tt=tt&0x00FFFFFF;
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("A", myRManager.getRegister("A")&tt);		
			} else if (mnemonic.equals("CLEAR")){
				String r1=tmp.getObjCode().substring(2,3);
				myRManager.setRegister(Integer.parseInt(r1),0);
			} else if (mnemonic.equals("COMP")) {
				if (tmp.isImmediate() && !tmp.isIndirect()){
					int targetVal=Integer.parseInt(tmp.getOffset(),16);
					int AVal=myRManager.getRegister("A");
					if (AVal>targetVal) myRManager.setRegister("SW",1);
					else if (AVal<targetVal) myRManager.setRegister("SW", 3);
					else myRManager.setRegister("SW",2);
				}
				else{
					int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
					byte[] oper=myRManager.getMemory(targetAddr,3);
					int tt=0;
					for (int i=0;i<3;i++){
						tt<<=8;
						tt|=oper[i];
					}
					tt=tt&0x00FFFFFF;
					int t=myRManager.getRegister("A");
					if (t>tt) myRManager.setRegister("SW",1);
					else if (t<tt) myRManager.setRegister("SW", 3);
					else myRManager.setRegister("SW",2);
					myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				}
			} else if (mnemonic.equals("COMPF")) {
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=myRManager.getMemory(targetAddr, 6);
				long tt=0;
				for (int i=0;i<6;i++){
					tt<<=8;
					tt|=oper[i];
				}
				long t=myRManager.getRegister("F");
				if (t>tt) myRManager.setRegister("SW",1);
				else if (t<tt) myRManager.setRegister("SW", 3);
				else myRManager.setRegister("SW",2);
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
			} else if (mnemonic.equals("COMPR")){
				String r1=tmp.getObjCode().substring(2,3);
				String r2=tmp.getObjCode().substring(3,4);
				
				int t=myRManager.getRegister(Integer.parseInt(r1));
				int tt=myRManager.getRegister(Integer.parseInt(r2));
				if (t>tt) myRManager.setRegister("SW",1);
				else if (t<tt) myRManager.setRegister("SW", 3);
				else myRManager.setRegister("SW",2);
			} else if (mnemonic.equals("DIV")){
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=myRManager.getMemory(targetAddr,3);
				int tt=0;
				for (int i=0;i<3;i++){
					tt<<=8;
					tt|=oper[i];
				}
				tt=tt&0x00FFFFFF;
				int t=myRManager.getRegister("A");
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("A",t/tt);
			} else if (mnemonic.equals("DIVF")){
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=myRManager.getMemory(targetAddr, 6);
				long tt=0;
				for (int i=0;i<6;i++){
					tt<<=8;
					tt|=oper[i];
				}
				long t=myRManager.getRegister("F");
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("F",t/tt);
			} else if (mnemonic.equals("DIVR")){
				String r1=tmp.getObjCode().substring(2,3);
				String r2=tmp.getObjCode().substring(3,4);
				
				int t=myRManager.getRegister(Integer.parseInt(r1));
				int tt=myRManager.getRegister(Integer.parseInt(r2));
				myRManager.setRegister(Integer.parseInt(r2),t/tt);
			} else if (mnemonic.equals("FIX")){
				myRManager.setRegister("A",(int)myRManager.getRegister("F"));
			} else if (mnemonic.equals("FLOAT")){
				myRManager.setRegister("F",(double)myRManager.getRegister("A"));
			} else if (mnemonic.equals("HIO")){		
			} else if (mnemonic.equals("J")){
				int targetAddr;
				if (tmp.getFormat()!=4)
					targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;		// 4형식이 아니라면 더해주고,
				else
					targetAddr=Integer.parseInt(tmp.getOffset(),16);			// 4형식이면 더하지 않음.
				
				if (!tmp.isIndirect() && tmp.isImmediate())					// immediate addressing.
					targetAddr=Integer.parseInt(tmp.getOffset(),16);
				
				if (tmp.isIndirect() && !tmp.isImmediate()){ 				// indirect addressing.
					targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
					byte[] data=myRManager.getMemory(targetAddr, 3);
					int tt=0;
					for (int i=0;i<3;i++){
						tt<<=8;
						tt|=data[i];
					}
					myPanel.targetAddress.setText(String.format("%06X",targetAddr));
					myRManager.setRegister("PC",tt);
					return true;
				}
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("PC", targetAddr);
			} else if (mnemonic.equals("JEQ")){
				int cc=myRManager.getRegister("SW");
				if (cc==2){
					int targetAddr;
					if (tmp.getFormat()!=4)
						targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;		// 4형식이 아니라면 더해주고,
					else
						targetAddr=Integer.parseInt(tmp.getOffset(),16);			// 4형식이면 더하지 않음.
					
					if (!tmp.isIndirect() && tmp.isImmediate())					// immediate addressing.
						targetAddr=Integer.parseInt(tmp.getOffset(),16);
					
					if (tmp.isIndirect() && !tmp.isImmediate()){ 				// indirect addressing.
						targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
						byte[] data=myRManager.getMemory(targetAddr, 3);
						int tt=0;
						for (int i=0;i<3;i++){
							tt<<=8;
							tt|=data[i];
						}
						myPanel.targetAddress.setText(String.format("%06X",targetAddr));
						myRManager.setRegister("PC",tt);
						return true;
					}
					myPanel.targetAddress.setText(String.format("%06X",targetAddr));
					myRManager.setRegister("PC",targetAddr);
				}
			} else if (mnemonic.equals("JGT")){
				int cc=myRManager.getRegister("SW");
				if (cc==1){
					int targetAddr;
					if (tmp.getFormat()!=4)
						targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;		// 4형식이 아니라면 더해주고,
					else
						targetAddr=Integer.parseInt(tmp.getOffset(),16);			// 4형식이면 더하지 않음.
					
					if (!tmp.isIndirect() && tmp.isImmediate())					// immediate addressing.
						targetAddr=Integer.parseInt(tmp.getOffset(),16);
					
					if (tmp.isIndirect() && !tmp.isImmediate()){ 				// indirect addressing.
						targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
						byte[] data=myRManager.getMemory(targetAddr, 3);
						int tt=0;
						for (int i=0;i<3;i++){
							tt<<=8;
							tt|=data[i];
						}
						myPanel.targetAddress.setText(String.format("%06X",targetAddr));
						myRManager.setRegister("PC",tt);
						return true;
					}
					myPanel.targetAddress.setText(String.format("%06X",targetAddr));
					myRManager.setRegister("PC",targetAddr);
				}
			} else if (mnemonic.equals("JLT")){
				int cc=myRManager.getRegister("SW");
				if (cc==3){
					int targetAddr;
					if (tmp.getFormat()!=4)
						targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;		// 4형식이 아니라면 더해주고,
					else
						targetAddr=Integer.parseInt(tmp.getOffset(),16);			// 4형식이면 더하지 않음.
					
					if (!tmp.isIndirect() && tmp.isImmediate())					// immediate addressing.
						targetAddr=Integer.parseInt(tmp.getOffset(),16);
					
					if (tmp.isIndirect() && !tmp.isImmediate()){ 				// indirect addressing.
						targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
						byte[] data=myRManager.getMemory(targetAddr, 3);
						int tt=0;
						for (int i=0;i<3;i++){
							tt<<=8;
							tt|=data[i];
						}
						myPanel.targetAddress.setText(String.format("%06X",targetAddr));
						myRManager.setRegister("PC",tt);
						return true;
					}
					myPanel.targetAddress.setText(String.format("%06X",targetAddr));
					myRManager.setRegister("PC",targetAddr);
				}
			} else if (mnemonic.equals("JSUB")){
				int targetAddr;
				if (tmp.getFormat()!=4)
					targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;		// 4형식이 아니라면 더해주고,
				else
					targetAddr=Integer.parseInt(tmp.getOffset(),16);			// 4형식이면 더하지 않음.
				
				if (!tmp.isIndirect() && tmp.isImmediate())					// immediate addressing.
					targetAddr=Integer.parseInt(tmp.getOffset(),16);
				
				if (tmp.isIndirect() && !tmp.isImmediate()){ 				// indirect addressing.
					targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
					byte[] data=myRManager.getMemory(targetAddr, 3);
					int tt=0;
					for (int i=0;i<3;i++){
						tt<<=8;
						tt|=data[i];
					}
					myPanel.targetAddress.setText(String.format("%06X",targetAddr));
					myRManager.setRegister("PC",tt);
					return true;
				}
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("L",currentPC+tmp.getFormat());
				myRManager.setRegister("PC",targetAddr);
			} else if (mnemonic.equals("LDA") || mnemonic.equals("LDB") || mnemonic.equals("LDL") ||
					mnemonic.equals("LDS") || mnemonic.equals("LDT") || mnemonic.equals("LDX") ){
				int targetAddr;
				if (tmp.getFormat()!=4)
					targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				else
					targetAddr=Integer.parseInt(tmp.getOffset(),16);
				
				if (!tmp.isIndirect() && tmp.isImmediate()){
					int ttt=Integer.parseInt(tmp.getOffset(),16);
					if (mnemonic.equals("LDA"))
						myRManager.setRegister("A",ttt);
					if (mnemonic.equals("LDB"))
						myRManager.setRegister("B",ttt);
					if (mnemonic.equals("LDL"))
						myRManager.setRegister("L",ttt);
					if (mnemonic.equals("LDS"))
						myRManager.setRegister("S",ttt);
					if (mnemonic.equals("LDT"))
						myRManager.setRegister("T",ttt);
					if (mnemonic.equals("LDX"))
						myRManager.setRegister("X",ttt);
					return true;
				}

				byte[] oper=myRManager.getMemory(targetAddr,3);
				int tt=0;
				for (int i=0;i<3;i++){
					tt<<=8;
					tt|=oper[i];
				}
				tt=tt&0x00FFFFFF;
				
				if (mnemonic.equals("LDA"))
					myRManager.setRegister("A",tt);
				if (mnemonic.equals("LDB"))
					myRManager.setRegister("B",tt);
				if (mnemonic.equals("LDL"))
					myRManager.setRegister("L",tt);
				if (mnemonic.equals("LDS"))
					myRManager.setRegister("S",tt);
				if (mnemonic.equals("LDT"))
					myRManager.setRegister("T",tt);
				if (mnemonic.equals("LDX"))
					myRManager.setRegister("X",tt);
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
			} else if (mnemonic.equals("LDCH")){
				int targetAddr;
				if (tmp.getFormat()!=4)					// 4형식 3형식 구분
					targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				else 
					targetAddr=Integer.parseInt(tmp.getOffset(),16);
				
				if (tmp.isLooping())				// looping 지원.
					targetAddr+=myRManager.getRegister("X");
				
				byte[] oper=myRManager.getMemory(targetAddr,1);
				int tt=0;
				for (int i=0;i<1;i++){
					tt<<=8;
					tt|=oper[i];
				}
				tt=tt&0x000000FF;
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("A",tt);
			} else if (mnemonic.equals("LDF")){
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=myRManager.getMemory(targetAddr, 6);
				long tt=0;
				for (int i=0;i<6;i++){
					tt<<=8;
					tt|=oper[i];
				}
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("F",(double)tt);
			} else if (mnemonic.equals("LPS")){
			} else if (mnemonic.equals("MUL")){
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=myRManager.getMemory(targetAddr,3);
				int tt=0;
				for (int i=0;i<3;i++){
					tt<<=8;
					tt|=oper[i];
				}
				tt=tt&0x00FFFFFF;
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("A",myRManager.getRegister("A")*tt);
			} else if (mnemonic.equals("MULF")){
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=myRManager.getMemory(targetAddr, 6);
				long tt=0;
				for (int i=0;i<6;i++){
					tt<<=8;
					tt|=oper[i];
				}
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("F",myRManager.getRegister("F")*(double)tt);
			} else if (mnemonic.equals("MULR")){
				String r1=tmp.getObjCode().substring(2,3);
				String r2=tmp.getObjCode().substring(3,4);
				
				int t=myRManager.getRegister(Integer.parseInt(r1));
				int tt=myRManager.getRegister(Integer.parseInt(r2));
				myRManager.setRegister(Integer.parseInt(r2), t*tt);
			} else if (mnemonic.equals("NORM")){
			} else if (mnemonic.equals("OR")){
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=myRManager.getMemory(targetAddr,3);
				int tt=0;
				for (int i=0;i<3;i++){
					tt<<=8;
					tt|=oper[i];
				}
				tt=tt&0x00FFFFFF;
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("A",myRManager.getRegister("A")|tt);
			} else if (mnemonic.equals("RD")){
				if (readEnd)	{
					myRManager.setRegister("SW",2);
					return true;
				}
				int tt=0;
		
				byte[] data=myRManager.readDevice(currentFileName,1);
				tt=data[0];	
				tt=tt&0x000000FF;
				if ((char)tt=='0')seqZeroCnt++;
				else seqZeroCnt=0;
				
				if (seqZeroCnt==2) {
					tt=0;
					readEnd=true;
					myRManager.setRegister("X",myRManager.getRegister("X")-2);
				}
				myRManager.setRegister("A",tt);
				
			} else if (mnemonic.equals("RMO")){
				String r1=tmp.getObjCode().substring(2,3);
				String r2=tmp.getObjCode().substring(3,4);
				
				myRManager.setRegister(r2,myRManager.getRegister(r1));
			} else if (mnemonic.equals("RSUB")){
				myRManager.setRegister("PC",myRManager.getRegister("L"));
			} else if (mnemonic.equals("SHIFTL")){
				String r1=tmp.getObjCode().substring(2,3);
				String n=tmp.getObjCode().substring(3,4);
				
				int ti=myRManager.getRegister(Integer.parseInt(r1));			// logical shifting not circular shifting
				ti=ti<<Integer.parseInt(n);
				myRManager.setRegister(Integer.parseInt(r1),ti);
			} else if (mnemonic.equals("SHIFTR")){
				String r1=tmp.getObjCode().substring(2,3);
				String n=tmp.getObjCode().substring(3,4);
				
				int ti=myRManager.getRegister(Integer.parseInt(r1));			// logical shifting not circular shifting
				ti=ti>>Integer.parseInt(n);
				myRManager.setRegister(Integer.parseInt(r1),ti);
			} else if (mnemonic.equals("SIO") || mnemonic.equals("SSK") || mnemonic.equals("STI")){
			} else if (mnemonic.equals("STA")|| mnemonic.equals("STB") ||mnemonic.equals("STL")
					|| mnemonic.equals("STS") || mnemonic.equals("STT") || mnemonic.equals("STX") || mnemonic.equals("STSW")){
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=new byte[3];
				int tt=0;
				if (mnemonic.equals("STA")) tt=myRManager.getRegister("A");
				if (mnemonic.equals("STB")) tt=myRManager.getRegister("B");
				if (mnemonic.equals("STL")) tt=myRManager.getRegister("L");
				if (mnemonic.equals("STS")) tt=myRManager.getRegister("S");
				if (mnemonic.equals("STT")) tt=myRManager.getRegister("T");
				if (mnemonic.equals("STX")) tt=myRManager.getRegister("X");
				if (mnemonic.equals("STSW")) tt=myRManager.getRegister("SW");
				for (int i=0;i<3;i++){
					oper[2-i]=(byte)tt;
					tt>>=8;
				}
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setMemory(targetAddr, oper, 3);
			} else if (mnemonic.equals("STCH")){
				int targetAddr;
				if (tmp.getFormat()!=4)
					targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				else
					targetAddr=Integer.parseInt(tmp.getOffset(),16);
				
				if (tmp.isLooping())
					targetAddr+=myRManager.getRegister("X");
				
				byte[] oper=new byte[1];
				int tt=myRManager.getRegister("A");
				oper[0]=(byte)tt;
				
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setMemory(targetAddr, oper, 1);
			} else if (mnemonic.equals("STF")){
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=new byte[6];
				long tt=0;
				for (int i=0;i<6;i++){
					tt>>=8;
					oper[5-i]=(byte)tt;
				}
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setMemory(targetAddr, oper, 6);
			} else if (mnemonic.equals("SUB")){
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=myRManager.getMemory(targetAddr,3);
				int tt=0;
				for (int i=0;i<3;i++){
					tt<<=8;
					tt|=oper[i];
				}
				tt=tt&0x00FFFFFF;
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("A", myRManager.getRegister("A")-(double)tt);
			} else if (mnemonic.equals("SUBF")){
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=myRManager.getMemory(targetAddr, 6);
				long tt=0;
				for (int i=0;i<6;i++){
					tt<<=8;
					tt|=oper[i];
				}
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
				myRManager.setRegister("F",myRManager.getRegister("F")-(double)tt);
			} else if (mnemonic.equals("SUBR")){
				String r1=tmp.getObjCode().substring(2,3);
				String r2=tmp.getObjCode().substring(3,4);
				
				int t=myRManager.getRegister(Integer.parseInt(r1));
				int tt=myRManager.getRegister(Integer.parseInt(r2));
				myRManager.setRegister(Integer.parseInt(r2), t-tt);
			} else if (mnemonic.equals("SVC")|| mnemonic.equals("TIO")){
			}else if (mnemonic.equals("TD")){
				int targetAddr;
				if (tmp.getFormat()!=4)
					targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;		// 4형식이 아니라면 더해주고,
				else
					targetAddr=Integer.parseInt(tmp.getOffset(),16);			// 4형식이면 더하지 않음.
				
				if (!tmp.isIndirect() && tmp.isImmediate())					// immediate addressing.
					targetAddr=Integer.parseInt(tmp.getOffset(),16);
				
				if (tmp.isIndirect() && !tmp.isImmediate()){ 				// indirect addressing.
					targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
					byte[] data=myRManager.getMemory(targetAddr, 3);
					int tt=0;
					for (int i=0;i<3;i++){
						tt<<=8;
						tt|=data[i];
					}
					myPanel.targetAddress.setText(String.format("%06X",targetAddr));
					myRManager.setRegister("SW",1);
					return true;
				}
				
				byte[] dat=myRManager.getMemory(targetAddr,1);
				String ttt=String.format("%02X",dat[0]);
				currentFileName=ttt+".txt";
				myRManager.initialDevice(currentFileName);
				myRManager.setRegister("SW",1);
			}
			else if (mnemonic.equals("TIX")){
				myRManager.setRegister("X",myRManager.getRegister("X")+1);
				int targetAddr=Integer.parseInt(tmp.getOffset(),16)+nextPC;
				byte[] oper=myRManager.getMemory(targetAddr,3);
				int tt=0;
				for (int i=0;i<3;i++){
					tt<<=8;
					tt|=oper[i];
				}
				tt=tt&0x00FFFFFF;
				int t=myRManager.getRegister("X");
				if (t>tt) myRManager.setRegister("SW",1);
				else if (t<tt) myRManager.setRegister("SW", 3);
				else myRManager.setRegister("SW",2);
				myPanel.targetAddress.setText(String.format("%06X",targetAddr));
			} else if (mnemonic.equals("TIXR")){
				myRManager.setRegister("X",myRManager.getRegister("X")+1);
				String r1=tmp.getObjCode().substring(2,3);
				
				int t=myRManager.getRegister("X");
				int tt=myRManager.getRegister(Integer.parseInt(r1));
				if (t>tt) myRManager.setRegister("SW",1);
				else if (t<tt) myRManager.setRegister("SW", 3);
				else myRManager.setRegister("SW",2);
			} else if (mnemonic.equals("WD")){
				byte[] oper=new byte[1];
				int tt=myRManager.getRegister("A")&0x000000FF;
				oper[0]=(byte)tt;
				myRManager.writeDevice(currentFileName, oper, 1);
			}
			return true;			// 진행완료후 true 반환.
		}
	}
	/**
	 * OPTAB을 관리하는 class이다.
	 */
	public static class InstTable{
		private ArrayList<InstData> instTab=new ArrayList<InstData>();
	
		public void initialize(File file){
			try{
				Scanner fin=new Scanner(file);
				while (fin.hasNext()){
					instTab.add(new InstData(fin.nextLine()));
				}
				fin.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		/**
		 * OPTAB안의 내용을 반환.
		 * @param key
		 * @return ??
		 */
		public boolean Search(String Key){
			for (InstData finding:instTab){
				if (finding.getOpcode().equals(Key))
					return true;
			}
			return false;
		}
		/**
		 * OPTAB내부에서 Key에 해당하는 opcode를 찾아서 형식을 반환.
		 * @param Key
		 * @return 형식 반환.
		 */
		public String getFormat(String Key){
			for (InstData finding:instTab){
				if(finding.getOpcode().equals(Key))
					return finding.getFormat();
			}
			return null;
		}
		/**
		 * OPTAB내부에서 Key에 해당하는 opcode를 찾아서 mnemonic을 반환.
		 * @param Key
		 * @return mnemonic
		 */
		public String getMnemonic(String Key){
			for (InstData finding:instTab){
				if (finding.getOpcode().equals(Key)){
					return finding.getMnemonic();
				}
			}
			return null;
		}
		/**
		 * comment를 반환해주는 함수.
		 * @param Key
		 * @return comment
		 */
		public String getComment(String Key){
			for (InstData finding:instTab){
				if (finding.getOpcode().equals(Key))
					return finding.getComment();
			}
			return null;
		}
	}
	
	/**
	 * OPTAB에 들어갈 내용을 저장하기 위한 class.
	 */
	public static class InstData{
		private String mnemonic;
		private String format;
		private String opcode;
		private String operandNum;
		private String comment;
		public InstData(String line){
			StringTokenizer sToke=new StringTokenizer(line);
			mnemonic=sToke.nextToken();
			format=sToke.nextToken();
			opcode=sToke.nextToken();
			operandNum=sToke.nextToken();
			int len=mnemonic.length()+format.length()+opcode.length()+operandNum.length()+4;
			comment=line.substring(len);
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public String getMnemonic() {
			return mnemonic;
		}
		public void setMnemonic(String mnemonic) {
			this.mnemonic = mnemonic;
		}
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {
			this.format = format;
		}
		public String getOpcode() {
			return opcode;
		}
		public void setOpcode(String opcode) {
			this.opcode = opcode;
		}
		public String getOperandNum() {
			return operandNum;
		}
		public void setOperandNum(String operandNum) {
			this.operandNum = operandNum;
		}
	}
	
	/**
	 * register 여럿을 묶어놓고 관리하는 클래스.
	 */
	public static class RegisterSet{
		private Register[] registerSet=new Register[10];
		public RegisterSet(){
			for (int i=0;i<10;i++){
				registerSet[i]=new Register();
			}
		}
		/**
		 * 레지스터를 추가.
		 * @param regNum
		 * @param fid
		 */
		public void insert(int regNum,JTextField fid){
			registerSet[regNum].initialize(fid);
		}

		/**
		 * 레지스터를 추가.
		 * @param regNum
		 * @param dec
		 * @param hex
		 */
		public void insert(int regNum,JTextField dec,JTextField hex){
			registerSet[regNum].initialize(dec, hex);
		}
		/**
		 * 매개로 받은 register번호에 해당하는 register 반환.
		 * @param regNum
		 * @return register반환.
		 */
		public Register getRegister(int regNum){
			return registerSet[regNum];
		}
		
		/**
		 * 매개로 받은 register이름의 register를 반환.
		 * @param regName
		 * @return
		 */
		public Register getRegister(String regName){
			if (regName.equals("A"))	return registerSet[0];
			else if(regName.equals("X"))	return registerSet[1];
			else if(regName.equals("L"))	return registerSet[2];
			else if(regName.equals("PC"))	return registerSet[8];
			else if(regName.equals("SW"))	return registerSet[9];
			else if(regName.equals("B"))	return registerSet[3];
			else if(regName.equals("S"))	return registerSet[4];
			else if(regName.equals("T"))	return registerSet[5];
			else return registerSet[6];
		}
	}
	
	public static class Register{
		private int registerVal;
		private JTextField decimal;
		private JTextField hexa;
		/**
		 * 하나만 있는 레지스터 값을 초기화.
		 * @param fld
		 */
		public void initialize(JTextField fld){
			registerVal=0;
			decimal=fld;
			hexa=null;
			decimal.setText(String.format("%d",0));
		}
		/**
		 * 10진수 16진수 모두 있는 레지스터 값을 초기화.
		 * @param dec
		 * @param hex
		 */
		public void initialize(JTextField dec,JTextField hex){
			registerVal=0;
			decimal=dec;
			hexa=hex;
			decimal.setText(String.format("%d",0));
			hexa.setText(String.format("%06X",0));
		}
		/**
		 * register에 값을 세팅하는 부분
		 */
		public void setValue(int value){
			registerVal=value;
			decimal.setText(String.format("%d",value));
			if (hexa!=null)
				hexa.setText(String.format("%06X",value));
		}
		/**
		 * register에 값을 세팅하는 부분
		 */
		public void setValue(long value){
			registerVal=(int)value;
			decimal.setText(String.format("%ld",value));
			if (hexa!=null)
				hexa.setText(String.format("%06X",value));
		}
		/**
		 * register에 값을 세팅하는 부분
		 */
		public void setValue(double value){
			registerVal=(int)value;
			decimal.setText(String.format("%lf",value));
			if (hexa!=null)
				hexa.setText(String.format("%06X",value));
		}
		/**
		 * register값을 가져오는 부분.
		 * @return register 값을 반환.
		 */
		public int getValue(){
			return registerVal;
		}
	}
	
	
	/**
	 * 레지스터나 memory부분을 관리하는 클래스.
	 */
	public static class SimulatorResourceManager
	{
		private byte[] memory=new byte[1024*1024];		// 메모리 영역 (2^20크기)
		private FileOutputStream outputDev;
		private FileInputStream inputDev;
		private String befFileName="SP";
		/**
		 * 메모리 영역으로부터 값을 받아오는 메소드.
		 * @param locate 메모리 시작위치
		 * @param size 읽어올 크기
		 * @return 메모리 시작위치로부터 size만큼 읽은 내용
		 */
		public byte[] getMemory(int locate, int size) {
			// 메모리 영역으로부터 값을 가져오는 메소드
			byte[] locMem=new byte[size];
			for (int i=0;i<size;i++){
				locMem[i]=memory[locate+i];
			}
			return locMem;
		}
		/**
		 * 레지스터 번호를 받고 그 레지스터의 값을 반환하는 메소드.
		 * @param regName
		 * @return 해당 레지스터 값.
		 */
		public int getRegister(int regNum) {
			// 레지스터로부터 값을 가져오는 메소드
			return registerSet.getRegister(regNum).getValue();
		}
		
		/**
		 * 레지스터 이름을 받고 그 레지스터의 값을 반환하는 메소드.
		 * @param regName
		 * @return 해당 레지스터 값.
		 */
		public int getRegister(String regName) {
			// 레지스터로부터 값을 가져오는 메소드
			return registerSet.getRegister(regName).getValue();
		}

		/**
		 * 디바이스 초기화. 사용가능한 상태로 만드는 메소드.
		 * @param deviceName
		 */
		public void initialDevice(String deviceName) {
			// 디바이스 접근에 대한 메소드. 해당 디바이스를 사용가능한 상태로 만드는 메소드.
			if (!befFileName.equals(deviceName)){
				try {
					outputDev=new FileOutputStream(new File(deviceName),true);		// 어떻게 될지 모르니 일단 둘다 개방.
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputDev=new FileInputStream(deviceName);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			befFileName=deviceName;
		}

		/**
		 * 메모리 영역을 초기화하는 메소드.
		 */
		public void initializeMemory() {
			// 메모리 영역을 초기화 하는 메소드.
			memory=myLoader.getMemory();
		}
		
		/**
		 * 레지스터 영역을 초기화하는 메소드.
		 */
		public void initializeRegister() {
			// 레지스터 값을 초기화 하는 메소드.
			setRegister("L",returnProgramAddr);
		}
		/**
		 * deviceName으로부터 size만큼 읽어오는 메소드
		 * @param deviceName
		 * @param size
		 * @return 읽어온 크기만큼의 내용을 반환.
		 */
		public byte[] readDevice(String deviceName, int size) {
			// 현재 프로그램은 'F1'이라는 파일 명에서 내용을 읽어 온다고 가정한다.
			byte[] data=new byte[size];
			try {
				inputDev.read(data);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return data;
		}
		
		/**
		 * 현재 memory상태를 반환하는 함수.
		 * @return current memory status
		 */
		public void currentStatus(){
			String output="<html>메모리 주소 	메모리 값<br>";
			int length=programStart.get(programStart.size()-1);
			for (int i=programLoadAddr/0x00000010;i<length;i++){
				if ((i%0x00000010)==0)
					output=output+String.format("0x%08X ",i);
				output=output+String.format("%02X",memory[i]);
				if ((i+1)%0x00000010==0)
					output=output+"<br>";				// 개행추가.
			}
			output=output+"</html>";
			
			JFrame testFrame=new JFrame();
			testFrame.setTitle("::현재 memory상태 ::");
			testFrame.setSize(350,800);						// 새로운 frame창에서 띄워줌.
			
			JPanel testPanel=new JPanel();
			testPanel.add(new JLabel(output));
			testPanel.setAutoscrolls(true);
			JScrollPane jspp=new JScrollPane(testPanel);			// 스크롤 가능하게,
			
			testFrame.add(jspp);
			testFrame.setVisible(true);
		}

		/**
		 * locate위치부터 시작해서 size만큼 data의 내용을 memory영역에 씀.
		 * @param locate
		 * @param data
		 * @param size
		 */
		public void setMemory(int locate, byte[] data, int size) {
			// 메모리 영역에 값을 쓰는 메소드.
			for (int i=0;i<size;i++){
				memory[i+locate]=data[i];		// 받은 data배열의 내용을 저장.
			}
		}
		/**
		 * 해당 레지스터 번호에 value의 값을 지정.
		 * @param registerNum
		 * @param value
		 */
		public void setRegister(int registerNum, int value) {
			// registerNum에 value를 쓰는 메소드
			registerSet.getRegister(registerNum).setValue(value);
		}
		/**
		 * 해당 레지스터 번호에 value의 값을 지정.
		 * @param registerNum
		 * @param value
		 */
		public void setRegister(int registerNum, long value) {
			// registerNum에 value를 쓰는 메소드
			registerSet.getRegister(registerNum).setValue(value);
		}
		/**
		 * 해당 레지스터 번호에 value의 값을 지정.
		 * @param registerNum
		 * @param value
		 */
		public void setRegister(int registerName, double value) {
			// registerNum에 value를 쓰는 메소드
			registerSet.getRegister(registerName).setValue(value);
		}
		/**
		 * 해당 레지스터 이름에 value의 값을 지정.
		 * @param registerNum
		 * @param value
		 */
		public void setRegister(String registerName, int value) {
			// registerNum에 value를 쓰는 메소드
			registerSet.getRegister(registerName).setValue(value);
		}
		/**
		 * 해당 레지스터 이름에 value의 값을 지정.
		 * @param registerNum
		 * @param value
		 */
		public void setRegister(String registerName, long value) {
			// registerNum에 value를 쓰는 메소드
			registerSet.getRegister(registerName).setValue(value);
		}
		/**
		 * 해당 레지스터 이름에 value의 값을 지정.
		 * @param registerNum
		 * @param value
		 */
		public void setRegister(String registerName, double value) {
			// registerNum에 value를 쓰는 메소드
			registerSet.getRegister(registerName).setValue(value);
		}
		/**
		 * 해당 deviceName에 size만큼의 data를 쓰는 메소드.
		 * @param deviceName
		 * @param data
		 * @param size
		 */
		public void writeDevice(String deviceName, byte[] data, int size) {
			// 가리키는 파일명(기계라고 가정)에 값을 쓰는 메소드.
			try {
				outputDev.write(data);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 외부 정의의 내용을 담는 클래스.
	 */
	public static class externalDef{
		private String symbolName;
		private String address;
		public externalDef(String sN,String addr){
			symbolName=sN;
			address=String.format("%06X",Integer.parseInt(addr,16)+programLoadAddr);
		}
	}
	/**
	 * 외부 정의된 내용들을 담는 ESYMTAB의 역할을 하는 클래스.
	 */
	public static class externalDefTable{
		private ArrayList<externalDef> externalDeftab=new ArrayList<externalDef>();
		public void insert(String line){
			externalDeftab.add(new externalDef(line.substring(0,6),line.substring(6)));
		}
		/**
		 * 외부정의 table에서 symbol 찾아서 주소 던져주는애.
		 * @param Key
		 * @return 찾으면 주소, 못찾으면 -1
		 */
		public int search(String Key){
			for (externalDef finding: externalDeftab){
				if (finding.symbolName.equals(Key)){
					return Integer.parseInt(finding.address,16);
				}
			}
			return -1;
		}
	}

	/**
	 * Loading과정에서 읽는 modification record의 내용을 담는 클래스.
	 */
	public static class modificationRecord{
		private String symbolName;
		private int targetAddr;
		private int changeSize;
		private String operator;
		public modificationRecord(String sN,int tA,int cS,String ope){
			this.symbolName=sN;
			this.targetAddr=tA;
			this.changeSize=cS;
			this.operator=ope;
		}
		public String getSymbolName() {
			return symbolName;
		}
		public void setSymbolName(String symbolName) {
			this.symbolName = symbolName;
		}
		public int getTargetAddr() {
			return targetAddr;
		}
		public void setTargetAddr(int targetAddr) {
			this.targetAddr = targetAddr;
		}
		public int getChangeSize() {
			return changeSize;
		}
		public void setChangeSize(int changeSize) {
			this.changeSize = changeSize;
		}
		public String getOperator() {
			return operator;
		}
		public void setOperator(String operator) {
			this.operator = operator;
		}
	}
	
	/**
	 * modification table을 관리하는 class이다.
	 */
	public static class modificationTable{
		private ArrayList<modificationRecord> ModificationTab=new ArrayList<modificationRecord>();
		private int nextMod=0;
		public void insert(String targetSymbol,int target,int length,String operator){			
			ModificationTab.add(new modificationRecord(targetSymbol,target,length,operator));
		}
		public modificationRecord nextModification(){
			return ModificationTab.get(nextMod++);
		}
		/**
		 * 다음 modification record있는지 확인.
		 * @return 존재하면 true, 없으면 false반환.
		 */
		public boolean hasNextModi(){
			return !(nextMod>=ModificationTab.size());
		}
	}
	
	public static class InstructionData{
		private int locationCounter;
		private String objCode;
		private String opcode;
		private boolean pcRelative;
		private boolean baseRelative;
		private boolean format4;
		private boolean immediate;
		private boolean indirect;
		private boolean looping;
		private int format;
		private String offset;
		
		public InstructionData(String objCode, String opcode,
				boolean pcRelative, boolean baseRelative, boolean format4,
				boolean immediate, boolean indirect, boolean looping,
				int format, String offset,int locationCounter) {
			this.objCode = objCode.toUpperCase();
			this.opcode = opcode.toUpperCase();
			this.pcRelative = pcRelative;
			this.baseRelative = baseRelative;
			this.format4 = format4;
			this.immediate = immediate;
			this.indirect = indirect;
			this.looping = looping;
			this.format = format;
			this.offset = offset.toUpperCase();
			this.locationCounter=locationCounter;
		}
		public int getLocationCounter() {
			return locationCounter;
		}
		public void setLocationCounter(int locationCounter) {
			this.locationCounter = locationCounter;
		}
		public String getOffset() {
			String tmp=offset;
			{
				int i=3;
				if (tmp.charAt(i)!='0' && !this.format4){ 		// 4형식이 아니고, 숫자가 존재한다면.
					if (tmp.charAt(i)=='8' || tmp.charAt(i)=='9' 
							|| ('A'<=tmp.charAt(i) && tmp.charAt(i)<='F')){
						// 음수를 나타내는 offset에 대해서.
						int tt=0x00001000-Integer.parseInt(offset.substring(i),16);
						tmp="-"+Integer.toHexString(tt).toUpperCase();
						// '-'부호를 더해서 음수를 나타낸다.
					}
				}
			}
			return tmp;
		}
		public void setOffset(String offset) {
			this.offset = offset.toUpperCase();
		}
		/**
		 * 통째로 반환.
		 * @return
		 */
		public String getObjCode() {
			return objCode;
		}
		public void setObjCode(String objCode) {
			this.objCode = objCode.toUpperCase();
		}
		public String getObjectCode() {
			if (opcode.length()<2)
				opcode="0"+opcode;
			return opcode;
		}
		/**
		 * 부분적 반환.
		 * @param objectCode
		 */
		public void setObjectCode(String objectCode) {
			this.opcode = objectCode.toUpperCase();
		}
		public boolean isPcRelative() {
			return pcRelative;
		}
		public void setPcRelative(boolean pcRelative) {
			this.pcRelative = pcRelative;
		}
		public boolean isBaseRelative() {
			return baseRelative;
		}
		public void setBaseRelative(boolean baseRelative) {
			this.baseRelative = baseRelative;
		}
		public boolean isFormat4() {
			return format4;
		}
		public void setFormat4(boolean format4) {
			this.format4 = format4;
		}
		public boolean isImmediate() {
			return immediate;
		}
		public void setImmediate(boolean immediate) {
			this.immediate = immediate;
		}
		public boolean isIndirect() {
			return indirect;
		}
		public void setIndirect(boolean indirect) {
			this.indirect = indirect;
		}
		public boolean isLooping() {
			return looping;
		}
		public void setLooping(boolean looping) {
			this.looping = looping;
		}
		public int getFormat() {
			return format;
		}
		public void setFormat(int format) {
			this.format = format;
		}
	}
	
	/**
	 * parsing한 instruction들을 관리하는 class이다.
	 */
	public static class InstructionTable{
		private ArrayList<InstructionData> instTab=new ArrayList<InstructionData>();
		public void insert(String objCode, String opcode,
				boolean pcRelative, boolean baseRelative, boolean format4,
				boolean immediate, boolean indirect, boolean looping,
				int format, String offset,int locationCounter){
			instTab.add(new InstructionData(objCode, opcode, pcRelative, 
					baseRelative, format4, immediate, 
					indirect, looping, format, offset,locationCounter));
		}
		/**
		 * location counter에 해당하는 명령어를 반환한다.
		 * @param locationCounter
		 * @return
		 */
		public InstructionData getProperty(int locationCounter){
			for (InstructionData finding:instTab){
				if (finding.getLocationCounter()==locationCounter)
					return finding;
			}
			return null;
		}
	}
	
	/**
	 * GUI부분에서 명령어가 동작하는 부분을 나타내기 위한 명령어와 location counter를 담는 class이다.
	 */
	public static class PoolInstruction{
		private int locationCounter;
		private String line;
		public PoolInstruction(int lc,String l){
			this.locationCounter=lc;
			this.line=l;
		}
		public String toString(){
			return line;
		}
	}
	
	/**
	 * Loader의 역할을 하는 class이다.
	 *
	 */
	public static class SimulatorLoader {
		private boolean firstCheck=false;
		private ArrayList<PoolInstruction> textObjectCode=new ArrayList<PoolInstruction>();
		private byte[] mem=new byte[1024*1024];			// resource manager에 넘겨주기 전까지 임시적으로 사용할 메모리 대용공간.
		private int memPtr;							// Loader에서 사용하는 프로그램 올릴 위치를 나타내는 지시자.
		private int befProgramLen=0;
		private String progLen;
		
		public void load(File objectFile) { 		// 파일로부터 object code를 읽어와서 메모리에 load.
			String t=myPanel.logArea.getText();
			myPanel.logArea.setText(t+"프로그램 Loading과정 시작.");
			memPtr=programLoadAddr;
			try{
				Scanner fread=new Scanner(objectFile);
				while(fread.hasNext()){
					readLine(fread.nextLine());				// 한줄씩 읽어서 가져와서 memory영역에 올린다.
				}
				fread.close();
			} catch (Exception e){
				e.printStackTrace();
			}
			programStart.add(befProgramLen);					// 프로그램의 최종적 길이를 저장.
			myPanel.instPool.removeAll();
			t=myPanel.logArea.getText();
			myPanel.logArea.setText(t+"\n읽기 완료.");
			
			correction();			// 보정 과정.
			t=myPanel.logArea.getText();
			myPanel.logArea.setText(t+"\nModification Record보정 완료.");
			parsing();				// parsing과정.
			myPanel.instPool.setListData(textObjectCode.toArray());			// parsing한 명령어를 pool에 추가.
			t=myPanel.logArea.getText();
			myPanel.logArea.setText(t+"\nInstruction parsing과정 완료.");
		}
		/**
		 * Loader에서 설정한 memory를 resource manager로 넘겨주는 method.
		 * @return memory
		 */
		public byte[] getMemory(){
			return mem;
		}
		
		/**
		 * Loading과정에서 object code에 있는 text record를 그대로 올린후, modification record에 의해서 보정 작업을 하는 메소드.
		 */
		public void correction(){
			while (Moditab.hasNextModi()){
				modificationRecord tmp=Moditab.nextModification();	// 바꿔야 할 내용이 있는 modification table탐색.
				int tm=tmp.getChangeSize();							// 바꿔줘야 할 크기.
				int value=ESYMTAB.search(tmp.getSymbolName());		// external symbol table에서 가져옴.
				int targetVal=0;
				int masking=0x00F00000;								// x,b,p,e부분만 읽어온다.
				for (int i=0;i<(tm+1)/2;i++){ 						// 이미 memory에 올라간 애를 대상으로 진행한다.
					targetVal<<=8;
					targetVal|=mem[tmp.getTargetAddr()+programLoadAddr+i];		// bitwise-OR 연산으로 memory에 있는 내용 가져옴.
				}
				int realVal=(targetVal&(masking));					// x,b,p,e가 있는 부분은 혹시 값이 흘러넘칠 경우에 대비해서 복사해준다.
				targetVal=targetVal&(~realVal);						// 위의 네개의 플래그를 뺸 부분을 구한다.
				int output=0;
				String op=tmp.getOperator();					// 연산자를 가져온다.
				if (op.equals("+")){
					output=targetVal+value;
				}else if (op.equals("-")){
					output=targetVal-value;
				}else if (op.equals("*")){
					output=targetVal*value;
				}else if (op.equals("/")){
					output=targetVal/value;						// 연산자에 알맞게 연산 진행한다.
				}
				int result=realVal|output;						// bitwise-OR연산으로 값에 반영.
				int resultMask=0x000000FF;						// byte가 signed-extension이 일어나므로 masking을 해준다.
				for (int i=(tm+1)/2-1;i>=0;i--){
					mem[tmp.getTargetAddr()+programLoadAddr+i]=(byte)(result&resultMask);		// mem의 위치에 다시 써준다.
					result=result>>8;
				}
			}
		}
		/**
		 * 1byte ASCII문자2개를 1byte에 넣는 작업.
		 * @param input
		 * @param len
		 */
		public void packing (String input,int len){	//len은 만들고자 하는 길이.
			int tmp=0;
			int tmp1=0;
			for (int i=0;i<len;i++){
				tmp=0;
				tmp1=0;
				tmp=Character.digit(input.charAt(2*i),16);		// 상단 부분.
				tmp=tmp<<4;			// 절반을 shift.
				tmp1=Character.digit(input.charAt(2*i+1),16);	// 하단 부분.
				mem[memPtr++]=(byte)((tmp|tmp1)&0x000000FF);
			}
		}
		/**
		 * memory영역에 올리고나서 parsing과정을 통해서 instruction을 찾아내는 메소드.
		 */
		public void parsing() {
			int increase=0;					// 얼만큼 startPtr를 움직일 것인지의 정보를 담고 있는 변수.
			int curProgram=0;					// 현재 프로그램 번호.
			int startPtr=programLoadAddr;			// load된 프로그램의 주소.
			int endPtr=befProgramLen+programLoadAddr;			// 프로그램의 끝을 나타내는 pointer.
			boolean EOP=false;					// 프로그램의 끝을 나타내는 flag.
			String oppcode;
			String offset;
			while (startPtr<endPtr){
				increase=0;
				oppcode="";						// 정제된 opcode를 담음.
				offset="";						// offset부분.
				boolean pcRelative=false;
				boolean baseRelative=false;
				boolean format4=false;
				boolean immediate=false;
				boolean indirect=false;
				boolean looping=false;			// n,i,x,b,p,e에 해당하는 플래그를 나타내는 변수들.
				int format=3;					// 기본적으로 3형식이라고 가정.
				
				int opcode=mem[startPtr]&0x000000FF;	// startPtr위치의 내용을 읽어온다. signed-extension의 가능성이 있으므로 bitwise-AND연산을 취한다.
				if (opcode==0 || EOP){			// SIC/XE machine에 대해서 진행하므로 opcode가 0이 되는 경우는 존재하지 않는다고 가정.
					curProgram++;
					startPtr=programStart.get(curProgram)+programLoadAddr;  // 다음 프로그램의 시작 위치로 startPtr이동.
					EOP=false;
				}else{
					int cor=(opcode/4)&0x000000FF;
					int temp=(cor*4)&(0x000000FF);				// n,i플래그를 제외한 정제된 opcode를 찾아내는 과정.
					int tOffset=0;
					
					oppcode=Integer.toString(temp,16);
					if (OPTAB.Search(String.format("%02X",temp))){	// opcode부분이 존재하는가 확인.
						if (String.format("%02X",temp).equals("4C"))		EOP=true;
																	// 'RSUB'이후의 명령어는 변수로 취급.
						String obCode="";
						int check=opcode;
						if ((check%4)==3){ 			// sic/xe machine direct addressing.
							indirect=true;
							immediate=true;
						} else if ((check%4)==2){ 	// sic/xe indirect
							indirect=true;
						} else if ((check%4)==1){ 	// sic/xe immediate
							immediate=true;
						} else{ 			// 1형식, 2형식인지 확인.
							String t=OPTAB.getFormat(String.format("%02X",temp));	// opcode의 형식 확인.
							if (t!=null){
								if (t.equals("2")){		// 2형식이라면,
									obCode=String.format("%02X",mem[startPtr]);
									obCode=obCode+String.format("%02X",mem[startPtr+1]);
									format=2;
									increase=2;
								} else if (t.equals("1")){	// 1형식이라면,
									obCode=String.format("%02X",mem[startPtr]);
									format=1;
									increase=1;
								}
							}
						}
						if ((check%4)!=0){
							int func=mem[startPtr+1]&0x000000FF;	// x,b,p,e부분을 추려냄.
							func=func>>4;
							int loop=8,base=4,pc=2,for4=1;
							if (func==0){			// 뒤에 x,b,p,e부분이 없는 경우.
								obCode=String.format("%02X",mem[startPtr]);
								obCode=obCode+String.format("%02X",mem[startPtr+1]);
								obCode=obCode+String.format("%02X",mem[startPtr+2]);
								tOffset=Integer.parseInt(obCode,16)&0x000FFF;
								increase=3;
							}
							else {					// 있는 경우.
								if ((func/loop)==1){ 			// looping지원.
									obCode=String.format("%02X",mem[startPtr]);
									obCode=obCode+String.format("%02X",mem[startPtr+1]);
									obCode=obCode+String.format("%02X",mem[startPtr+2]);
									tOffset=Integer.parseInt(obCode,16)&0x000FFF;
									looping=true;
									increase=3;
									func=func-loop;
								} if ((func/base)==1){ 			// base-relative addressing.
									obCode=String.format("%02X",mem[startPtr]);
									obCode=obCode+String.format("%02X",mem[startPtr+1]);
									obCode=obCode+String.format("%02X",mem[startPtr+2]);
									tOffset=Integer.parseInt(obCode,16)&0x000FFF;
									baseRelative=true;
									increase=3;
									func=func-base;
								} if ((func/pc)==1){			// pc-relative addressing.
									obCode=String.format("%02X",mem[startPtr]);
									obCode=obCode+String.format("%02X",mem[startPtr+1]);
									obCode=obCode+String.format("%02X",mem[startPtr+2]);
									tOffset=Integer.parseInt(obCode,16)&0x000FFF;
									pcRelative=true;
									increase=3;
									func=func-pc;
								} if ((func/for4)==1){			// 4형식.
									obCode=String.format("%02X",mem[startPtr]);
									obCode=obCode+String.format("%02X",mem[startPtr+1]);
									obCode=obCode+String.format("%02X",mem[startPtr+2]);
									obCode=obCode+String.format("%02X",mem[startPtr+3]);
									tOffset=Integer.parseInt(obCode,16)&0x000FFFFF;
									format=4;
									format4=true;
									increase=4;
									func=func-pc;
								}
							}
							offset=String.format("%06X",tOffset);
						}
						iTab.insert(obCode, oppcode, pcRelative, baseRelative, 
								format4, immediate, indirect, looping, format, offset,startPtr);		//startPtr가 location counter역할.
						textObjectCode.add(new PoolInstruction(startPtr,obCode.toUpperCase()));	// instruction pool에 저장.
						startPtr=startPtr+increase;					// startPtr증가.
						
					}
				}
			}
		}
		/**
		 * 한 줄을 매개로 받아서, 각 record의 종류에 알맞는 일을 하는 메소드.
		 * @param line
		 */
		public void readLine(String line) {
			// object code의 한 줄을 읽고, 헤더에 맞는 기능을 수행. 메모리 및 명령어 리스트를 초기화.
			if (line.length()==0)	return;
			char first=line.charAt(0);		// record 제일 앞머리를 가져온다.
			if (first=='T'){ 				// text record
				memPtr=Integer.parseInt(line.substring(1,7),16)+befProgramLen+programLoadAddr;
				int lineByteLen=Integer.parseInt(line.substring(7,9),16);
				int startPtr=9;
				int endPtr=startPtr+2*lineByteLen;				
				packing(line.substring(startPtr,endPtr),lineByteLen);			// packing을 통해 memory영역에 올린다.
			}
			else if (first=='M'){ 			// used with ESYMTAB
				int target=Integer.parseInt(line.substring(1,7),16);
				int length=Integer.parseInt(line.substring(7,9),16);
				String operator=line.substring(9,10);
				String targetSymbol=line.substring(10);
				Moditab.insert(targetSymbol,target+befProgramLen+programLoadAddr,length,operator);			
			} 
			else if (first=='H'){ 			// program name, start addr, program length를 담고 있다.
				String tline=line.substring(1);
				String pName=tline.substring(0,6);
				String pStAddr=tline.substring(6,12);
				progLen=tline.substring(12,18);
				programStart.add(Integer.parseInt(pStAddr,16)+befProgramLen);
				String programModi=String.format("%6s%06X",pName,
						Integer.parseInt(pStAddr,16)+befProgramLen+programLoadAddr);
				ESYMTAB.insert(programModi);		// program이름은 external symbol table에 저장한다.
				
				if (!firstCheck){ 				// 첫번째로 나오는 프로그램만 check함.
					myPanel.programName.setText(pName);
					myPanel.startAddrProgram.setText(pStAddr);
					myPanel.programLength.setText(progLen);
				}
			}
			else if (first=='E'){ 		// end record.
				memPtr=Integer.parseInt(progLen,16)+befProgramLen+programLoadAddr;		// memory pointer를 다음 프로그램 시작위치로 옮긴다.
				befProgramLen+=Integer.parseInt(progLen,16);	// 이전 프로그램의 길이를 누적.
				if (line.length()>1)
					firstCheck=true;					// 첫번쨰 main프로그램이라면 check.
			}
			else if (first=='D'){ 		// external definition.
				int startPtr=1;
				int endPtr=line.length();
				while (startPtr<endPtr){			// 잘라서 external symbol table에 저장한다.
					ESYMTAB.insert(line.substring(startPtr,startPtr+12));			
					startPtr=startPtr+12;
				}
				String t=myPanel.logArea.getText();
				myPanel.logArea.setText(t+"\n외부 정의 확인완료.");		// log에 알림지정.
			}
		}
	}
}
