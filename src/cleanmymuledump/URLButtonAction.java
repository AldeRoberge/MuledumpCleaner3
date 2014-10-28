package cleanmymuledump;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

public class URLButtonAction implements ActionListener {
	
	public static URI uri;
	
	public URLButtonAction(String suri) {
		try {
			URLButtonAction.uri = new URI(suri);
		} catch (final URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			Desktop.getDesktop().browse(URLButtonAction.uri);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
}
