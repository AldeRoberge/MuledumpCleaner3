package cleanmymuledump;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class ShowMessage implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent event) {
		JOptionPane.showMessageDialog(Run.frame, "Welcome! \n Test.");
	}
	
}
