package cleanmymuledump;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GetAccountFile implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		Run.getAccountFile.setText("Loading");
		Run.getAccountFile.setEnabled(false);
		
		
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(Run.accountJsFileDirectory));
		} catch (FileNotFoundException e2) {
			Run.print("File \"accounts.js\" not found!");
			Run.getAccountFile.setEnabled(true); //Re-set the button to true
			Run.getAccountFile.setText("Get account file");
		}
		
		String line;
		
		try {
			while ((line = br.readLine()) != null) {
				if (line.contains("': '") && line.startsWith("'")) {
					
					Run.numberOfAccountsInMDFile++;
					
					// 'email@domain.net': 'password',
					
					System.out.println("x");
					System.out.println(line);
					
					
					String email = line.substring(line.indexOf("'")+1, line.indexOf("': '"));
					String password = line.substring(line.indexOf("': '") + 4, line.indexOf("',"));
					
					
					
					Run.accounts.put(email, password);
					
					//System.out.println(email + " " + password);
				} else System.out.println("Unhandled : "+line); {
					
				}
			}
			
			if (Run.accounts.size() == 0) {
				Run.debug("Error : No accounts found in file.");
				Run.print("Error : No accounts found in specified file.");
				Run.getAccountFile.setEnabled(true); //Re-set the button to true
				Run.getAccountFile.setText("Get account file");
			} else {
				
				Run.cleanAction.setEnabled(true);
				
				Run.getAccountFile.setText("Ready");
				
				
				Run.debug("Found " + Run.accounts.size() + " account(s) and " + (Run.numberOfAccountsInMDFile - Run.accounts.size()) + " duplicate(s).");
				Run.print("Found " + Run.accounts.size() + " account(s) and " + (Run.numberOfAccountsInMDFile - Run.accounts.size()) + " duplicate(s).");
				
				br.close();
				
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
}
