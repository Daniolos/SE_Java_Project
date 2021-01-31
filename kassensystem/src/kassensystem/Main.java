import javax.swing.JFrame;

public class Main {
	public static Filialleitung admin;
	public static GUIEinkauf gui;
	
	public Main() {		
		gui = new GUIEinkauf();
		gui.setTitle("Einkaufsansicht");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH);  //Fullscreen
		gui.setVisible(true);
	}

	public static void main(String[] args) {
		Main Kasino = new Main();
	}
	
	public static void switchToAdmin() {
		gui.dispose();
		admin = new Filialleitung();
		admin.setTitle("Adminansicht");
		admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		admin.setExtendedState(JFrame.MAXIMIZED_BOTH);
		admin.setVisible(true);		
	}
	
	public static void switchToEinkauf() {
		admin.dispose();
		gui = new GUIEinkauf();
		gui.setTitle("Einkaufsansicht");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH);  //Fullscreen
		gui.setVisible(true);		
	}

}
