package cleanmymuledump;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class ProgressBar {
	
	public static void spashLoadScreen() {
		
		new Thread() {
			
			public void run() {
				
				JFrame f = new JFrame("JProgressBar Sample");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Container content = f.getContentPane();
				JProgressBar progressBar = new JProgressBar();
				progressBar.setValue(Run.answered * 100 / (Run.accounts.size() + 1));
				progressBar.setStringPainted(true);
				Border border = BorderFactory.createTitledBorder("Working...");
				progressBar.setBorder(border);
				content.add(progressBar, BorderLayout.NORTH);
				f.setSize(300, 100);
				f.setVisible(true);
				
			}
		}.start();
	}
	
}
