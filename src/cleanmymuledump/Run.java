package cleanmymuledump;

import java.awt.Container;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class Run {
	
	// GUI
	
	public static JButton getAccountFile;
	public static JButton cleanAction;
	public static JButton getSource;
	public static JButton getMD;
	public static JTextArea console;
	public static Container container;
	public static JFrame frame;
	public static JLabel info;
	public static JCheckBox removeBannedAccounts;
	public static JScrollPane scrollpane;
	
	public static String version = "Build 1";
	
	// DATA
	public static int numberOfAccountsInMDFile = 0;
	public static int answered = 0; //How many answer we've got from the appspot
	public static int added = 0; //How many were kept in the md file
	public static int removed = 0; //How many were removed
	public static int error = 0; //Error occurred (will try again)
	public static int fatalError = 0; //Fatal error occurred (will not try again)
	
	public static int maximumRank = 0;
	
	public static final String accountJsFileDirectory = "muledump/accounts.js";
	
	public static HashMap<String, String> accounts = new HashMap<String, String>(); // Email , Password
	
	public static HashMap<String, Integer> cleanedVersion = new HashMap<String, Integer>();
	
	// SETTINGS
	
	public static boolean debugging = true;
	
	public static void main(String[] args) {
		Run.window();
	}
	
	public static void print(String print) {
		Run.console.append(print + "\n");
		Run.frame.repaint();
	}
	
	public static void debug(String debug) {
		//TODO : debug button and no more lazy syso
		System.out.println(debug);
		if (debugging) {
			//Run.print(debug);
		}
		
	}
	
	public static void window() {
		
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		
		Run.frame = new JFrame("Clean my Muledump");
		
		try {
			Run.frame.setIconImage(ImageIO.read(new URL("https://i.imgur.com/tFW0bLQ.png")));
			//Backup : https://i.imgur.com/2A1lnjq.png
		} catch (final Exception e) {
			e.printStackTrace();
		}
		
		Run.frame.setSize(600, 300);
		Run.frame.setResizable(false);
		Run.frame.setLayout(null);
		Run.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Run.frame.setLocationRelativeTo(null);
		Run.frame.setVisible(true);
		
		Run.container = Run.frame.getContentPane();
		
		Run.removeBannedAccounts = new JCheckBox("Keep banned accounts");
		Run.console = new JTextArea(30, 20);
		Run.getAccountFile = new JButton("Get account file");
		Run.cleanAction = new JButton("Clean");
		Run.getSource = new JButton("Get source");
		Run.getMD = new JButton("Get MD File");
		Run.info = new JLabel("Ready");
		
		Run.getSource.addActionListener(new URLButtonAction("https://github.com/iobsidian"));
		
		Run.scrollpane = new JScrollPane(Run.console);
		
		Run.scrollpane.setBounds(10, 10, 400, 235); // Console
		Run.console.setEditable(true);
		
		Run.info.setBounds(11, 247, 400, 20); //TODO EDIT SIZE PROPERLY
		
		Run.removeBannedAccounts.setBounds(425, 70, 150, 20);
		Run.removeBannedAccounts.setToolTipText("Remove banned accounts");
		
		Run.getAccountFile.setBounds(425, 10, 150, 20);
		Run.cleanAction.setBounds(425, 30, 150, 20);
		Run.cleanAction.setFocusable(false);
		Run.getAccountFile.setFocusable(false);
		Run.getSource.setBounds(425, 240, 150, 20);
		Run.getSource.setFocusable(false);
		
		Run.getMD.setBounds(425, 50, 150, 20);
		Run.getMD.setFocusable(false);
		
		Run.cleanAction.setEnabled(false);
		
		Run.getAccountFile.addActionListener(new GetAccountFile());
		
		Run.cleanAction.addActionListener(new Cleaner());
		Run.getMD.addActionListener(new MuledumpFileGenerator());
		
		Run.getMD.setEnabled(false);
		
		
		Run.getAccountFile.setToolTipText("Read from accounts.js");
		Run.getMD.setToolTipText("Save only working accounts to accounts.js");
		Run.getSource.setToolTipText("Get the source code from Github");
		
		
		Run.container.add(Run.removeBannedAccounts);
		Run.container.add(Run.getAccountFile);
		Run.container.add(Run.cleanAction);
		Run.container.add(Run.getSource);
		Run.container.add(Run.getMD);
		Run.container.add(Run.info);
		Run.frame.add(Run.scrollpane);
		
		Run.info.setText("Clean my Muledump " + version + " — Ready.");
		
		Run.print("Welcome to Clean my Muledump!");
		
		Run.frame.repaint();
		
		frame.setVisible(true);
		
		while (true) {

			/*
			public static int totalAdded = 0;
			public static int totalRemoved = 0;
			public static int totalError = 0;
			
			public static int totalFatalError = 0;
			public static int totalAccounts = 0;
			public static int totalCalculed = 0;
			*/
		}
		
	}
	
}
