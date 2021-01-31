import javax.swing.JFrame;

/**
 * 
 * @author Daniel Jürgeleit, Julius Rappich, Felix Schulz
 *
 */

public class Main {
	public static Filialleitung admin;
	public static GUIEinkauf gui;

	/**
	 * Das Programm startet mit dem Einkaufsbildschirm.
	 */

	public Main() {
		gui = new GUIEinkauf();
		gui.setTitle("Einkaufsansicht");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
		gui.setVisible(true);
	}

	/**
	 * Startet das Programm.
	 * 
	 * @param args Formell notwendige Variable.
	 */

	public static void main(String[] args) {
		Main Kasino = new Main();
	}

	/**
	 * Wechselt vom Einkaufsbildschirm zum Adminbildschirm, indem ersterer
	 * geschlossen und letzterer geöffnet wird.
	 */

	public static void switchToAdmin() {
		gui.dispose();
		admin = new Filialleitung();
		admin.setTitle("Adminansicht");
		admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		admin.setExtendedState(JFrame.MAXIMIZED_BOTH);
		admin.setVisible(true);
	}

	/**
	 * Wechselt vom Adminbildschirm zum Einkaufsbildschirm, indem ersterer
	 * geschlossen und letzterer geöffnet wird.
	 */

	public static void switchToEinkauf() {
		admin.dispose();
		gui = new GUIEinkauf();
		gui.setTitle("Einkaufsansicht");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
		gui.setVisible(true);
	}

}
