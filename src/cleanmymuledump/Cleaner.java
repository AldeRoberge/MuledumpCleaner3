package cleanmymuledump;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map.Entry;

public class Cleaner implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Run.cleanAction.setEnabled(false);
		Run.cleanAction.setText("Done");
		Run.getMD.setText("Working...");
		Run.getAccountFile.setText("Waiting...");
		
		//ProgressBar.spashLoadScreen();
		
		new Thread() {
			
			public void run() {
				
				for (final Entry<String, String> entry : Run.accounts.entrySet()) {
					
					Run.answered++;
					
					URL oracle = null;
					try {
						oracle = new URL(getAppspotLink(entry.getKey(), entry.getValue()));
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					BufferedReader in = null;
					try {
						in = new BufferedReader(new InputStreamReader(oracle.openStream()));
						
						String answer = "";
						
						try {
							while ((answer = in.readLine()) != null) {
								
								String addedMsg = "Added "+Run.numberOfAccountsInMDFile+" mules.";
								String removedMsg = "Removed "+Run.removed+" mules.";
								
								Run.info.setText("Completed at " + Run.answered * 100 / Run.accounts.size() + "% | " + addedMsg + " | " + removedMsg);
								
								System.out.println("LINE : "+answer);
								System.out.println("Account : "+entry.getKey()+" "+entry.getValue());
								
								if (answer.contains("<Error>")) {
									
									if (answer.contains("Account is under maintenance")) { //Account is Banned
										
										
										
										if (!Run.removeBannedAccounts.isEnabled()){
											Run.print("Added banned account \"" + entry.getKey() + "\".");
											parse(entry.getKey(), entry.getValue(), answer);
											Run.added++;
										} else {
											Run.print("Removed banned account with \"" + entry.getKey() + "\".");
											Run.fatalError++;
										}
										
									} else if (answer.contains("Account credentials not valid")) { //Credentials are Invalid
										Run.fatalError++;
										Run.print("Removed invalid credentials account with \"" + (entry.getKey() + "\"."));
									} else if (answer.contains("Account in use")) { //Account is in Use
										Run.error++;
										Run.print("Added account in use with \"" + (entry.getKey() + "\"."));
										
										Run.added++;
										parse(entry.getKey(), entry.getValue(), answer);
									} else if (answer.contains("Server error")) { //Server Error
										Run.error++;
										Run.print("Added account with server error with \"" + entry.getKey() + "\".");
										
										Run.added++;
										parse(entry.getKey(), entry.getValue(), answer);
									} else { //Unhandled Error
										Run.fatalError++;
										Run.print("Unandled Error : " + answer);
									}
									
								} else {
									
									parse(entry.getKey(), entry.getValue(), answer);
									
									//System.out.println(nextCharId);
									
								}
								
							}
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try {
							in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				Run.info.setText("Added " + Run.added + " and removed " + Run.fatalError + " account from file — Done.");
				Run.debug("Completed.");
				
				Run.getMD.setText("Save file");
				Run.getMD.setEnabled(true); //Set the button to get muledump to true when done
				
			}
			
		}.start();
	}
	
	public static void parse(String email, String password, String answer){
		Run.added++;
		
		//TODO: Check the answer for
		
		int nextCharId = Integer.parseInt(answer.substring((answer.indexOf("nextCharId=\"") + 12), answer.indexOf("\" maxNumChars")));
		
		if (nextCharId > Run.maximumRank) {
			Run.maximumRank = nextCharId;
		}
		
		if (answer.contains("<Account><Name>")) {
			String name = answer.substring(answer.indexOf("<Account><Name>") + 15, answer.indexOf("</Name>"));
			Run.print("Added new account \"" + name + "\".");
			Run.cleanedVersion.put("'" + email + "': '" + password + "', //" + name, nextCharId);
			Run.debug("Added " + name + ".");
		} else {
			Run.debug("Error : Could not find " + email + "'s name. Added him anyway.");
			Run.print("Added new account \"" + email + "\".");
			Run.cleanedVersion.put("'" + email + "': '" + password + "',", nextCharId);
		}
	}
	
	public static String getAppspotLink(String email, String pass) {
		return "http://realmofthemadgodhrd.appspot.com/char/list?guid=" + email + "&password=" + pass;
	}
	
}
