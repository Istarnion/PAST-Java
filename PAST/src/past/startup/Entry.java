package past.startup;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Entry {

	private final String[] MENU_OPTIONS = {
			"Launch PAST",
			"Launch Echoes",
			"Add content",
			"Quit"
		};
		
	private final int
		PAST_OPTION		= 0,
		ECHOES_OPTION	= 1,
		CONTENT_OPTION	= 2,
		QUIT_OPTION		= 3;
	
	public Entry() {
		int input = 0;
		do {
			input = JOptionPane.showOptionDialog(null, "Welcome to P.A.S.T developer interface.", "P.A.S.T.",
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, MENU_OPTIONS, MENU_OPTIONS[QUIT_OPTION]);
			
			switch (input) {
				case PAST_OPTION:
				{
					showNotImplementedDialog();
				} break;
				case ECHOES_OPTION:
				{
					showNotImplementedDialog();
				} break;
				case CONTENT_OPTION:
				{
					showNotImplementedDialog();
				} break;
				default:
					input = QUIT_OPTION;
					break;
			}
		}
		while(input != QUIT_OPTION);
	}

	public static void showNotImplementedDialog() {
		JOptionPane.showMessageDialog(null, "This functionality is not yet implemented.",
				"P.A.S.T.", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		}
		catch(Exception e) {
			System.out.println("Failed setting JTattoo laf. Reverting to fallback.");
			try {
				// Set System L&F
				UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
			} 
			catch (Exception ex) {
				System.out.println("Failed setting System laf. Reverting to Java defult.");
			}
		}
		
		new Entry();
	}

}
