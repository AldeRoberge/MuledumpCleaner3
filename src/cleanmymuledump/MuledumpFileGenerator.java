package cleanmymuledump;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;

public class MuledumpFileGenerator implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Run.info.setText("Saving MD file.");
		
		Run.getMD.setEnabled(false);
		
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(Run.accountJsFileDirectory, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writer.println("/** Muledump by Atomizer ");
		writer.println("    Muledump Cleaner by Alde */");
		writer.println("");
		writer.println("accounts = {");
		writer.println("");
		
		for (int i = 0; i < Run.maximumRank; i++) {
			for (final Entry<String, Integer> entry : Run.cleanedVersion.entrySet()) {
				if (i == entry.getValue()) {
					
					Run.accounts.remove(entry.getKey());
					
					if (entry.getKey().contains("//")) {
						writer.println(entry.getKey());
					} else {
						writer.println(entry.getKey());
					}
					
				}
			}
		}
		
		writer.println("");
		writer.println("}");
		writer.println("");
		writer.print("rowlength = 7 \ntesting = 0 \nprices = 0 \nmulelogin = 0 \nnomasonry = 1");
		
		writer.close();
		
		Run.getMD.setText("Saved to file");
		Run.info.setText("File saved.");
		Run.print("Successfully saved to file.");
		
	}
}
