package kassensystem;

import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.*;


public class AusVideo extends JFrame
	implements ActionListener, ItemListener, ListSelectionListener
{

	// Boxes und Panels --> also alle Container
	private Box boxButtons = Box.createVerticalBox();
	private Box boxShoppingList = Box.createVerticalBox(); //Einkaufsliste 
	private Box boxStorageList = Box.createVerticalBox(); //Bestandsliste, hier soll der Kassierer und der Admin später suchen können
	private Box boxListBorder = Box.createVerticalBox();
	private JPanel contents;
	private JPanel panelCenter;
	private JPanel panelSouth;
	
	// Komponenten
	private JButton add;
	private JButton remove;
	private JButton deleteAll;
	private JButton admin;

	private JLabel lblShoppingList;  
	private JLabel lblStorageList;
	private JLabel lblListBorder;
	private JLabel lblSelectedArticle;

private JTable shoppingList;
private JTable storageList;
	
// Daten
private String [] articles = {"Apfel","Tomate","Brot"}; // Hier werden dann später die Daten irgendwie reingeklatscht

//Table models
}