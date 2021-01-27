package kassensystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Filialleitung extends JFrame implements ActionListener {
	// hier werden alle benötigten Klassen deklariert
	private JPanel formularPanel;
	private JPanel interactionsPanel;
	private JPanel bestandsListePanel;
	private JButton addButton;
	private JButton modifyButton;
	private JButton removeButton;
	private JButton createCategoryButton;
	private JTextField categoryInput;
	private JTable bestandsListe;
	
	// HIer sind alle Formular Variablen
	private JLabel articleNameLabel;
	private JTextField articleNameInput;
	private JLabel eanLabel;
	private JTextField eanInput;
	private JLabel articlePriceLabel;
	private JTextField articlePriceInput;
	private JLabel articleQuantityLabel;
	private JTextField articleQuantityInput;
	private JLabel basePriceLabel;
	private JTextField basePriceInput;
	private JLabel basePriceUnitLabel;
	private JComboBox basePriceUnitBox;
	private JLabel articleAmountLabel;
	private JTextField articleAmountInput;
	private JLabel categoryLabel;
	private JComboBox<Object> categoryBox;
	private JButton cancelButton;
	private JButton submitButton;
	
	
	
	// Alle Spaltennamen werden in einem String Array gespeichert
	String[] columnNames = {"Artikelname", "EAN", "Stückpreis", "Stückzahl", "Grundpreis", "Menge", "Kategorie"};
	
	// Datensätze müssen hier aus der DB eingebunden werden
	String[] categories = {"Obst", "Gemüse", "Backwaren", "Fleisch"};
	
	String[] basePriceUnits = { "€/kg", "€/100g", "€/l", "€/100ml", "n"}; 	
	/* zum umrechnen in Mengeneinheit
	* if (indexOf(basePriceUnit) === gerade) { Menge / 1000; measuringUnits[indexOf(basePriceUnit) - 1]; } 
	*/
	String[] measuringUnits = { "kg", "100g", "l", "100ml","n" };
	
	// Alle Datenbstände werden in einem mehrdimensionalen Objekt gespeichert
	Object[][] dataBestand = 
		{
			{"Apfel", "0192992", 1.50f+" €", 200, "n", "n", categories[0]},
			{"Tomate", "3432342", "n", "n", 2 + " " + basePriceUnits[0], 100 + " " + measuringUnits[0], categories[1]},
			{"Brot", "2345323", 213.32f+" €", 7, "n", "n", categories[2]},
			{"Rinderfilet", "6787383", 2498.33f+" €", "€/kg" , 20000 , "Gramm", categories[3]},
		};
	
	// hier wird die Bidlschirm-Größe mit dem Dimension Object initialisiert
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();
	
	private Filialleitung() {
		// hier werden alle benötigten Klassen instanziiert
		setLayout(new BorderLayout());
		formularPanel = new JPanel();
		interactionsPanel = new JPanel();
		addButton = new JButton("Artikel Hinzufügen");
		modifyButton = new JButton("Ausgewähltes Element bearbeiten");
		removeButton = new JButton("Ausgewähltes Element entfernen");
		categoryInput = new JTextField("");
		createCategoryButton = new JButton("Katgorie hinzufügen");
		
		addButton.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 formularPanel.setVisible(true);
	         }
	  
	    });
		
		modifyButton.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 // fill the form with data from a selected a row
	        	 // make changes to the row and save them
	        	 formularPanel.setVisible(true);
	         }
	  
	    });
		
		removeButton.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 // remove a selected row
	         }
	  
	    });
		
		createCategoryButton.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 if (!categoryInput.getText().equals("")) {
	        		 categories = Arrays.copyOf(categories, categories.length+1);
	        		 categories[categories.length-1] = categoryInput.getText();
	        		 formularErzeugen();
	        	 }
	         }
	  
	    });
		
		articleNameLabel = new JLabel("Artikelname");
		articleNameInput = new JTextField("");
		eanLabel = new JLabel("EAN / PLU");
		eanInput = new JTextField("");
		articlePriceLabel = new JLabel("Stückpreis");
		articlePriceInput = new JTextField("");
		articleQuantityLabel = new JLabel("Stückzahl");
		articleQuantityInput = new JTextField("");
		basePriceLabel = new JLabel("Grundpreis");
		basePriceInput = new JTextField("");
		basePriceUnitLabel = new JLabel("Grundpreiseinheit");
		basePriceUnitBox = new JComboBox<Object>(basePriceUnits);
		articleAmountLabel = new JLabel("Menge in " + measuringUnits[0]); // soll dynamisch anpassbar
		articleAmountInput = new JTextField("");
		categoryLabel = new JLabel("Kategorie");
		categoryBox = new JComboBox<Object>(categories);
		submitButton = new JButton("Bestätigen");
		cancelButton = new JButton("Bonabbruch");
		
		cancelButton.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 clearForm();
	        	 formularPanel.setVisible(false);
	         }
	  
	    });
		
		submitButton.addActionListener(this);
		
		basePriceUnitBox.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 String basePriceUnitValue = String.valueOf(basePriceUnitBox.getSelectedItem());
	        	 int index = Arrays.asList(basePriceUnits).indexOf(basePriceUnitValue);
	        	 if (basePriceUnitValue == "n"){ 
	        		 articleAmountInput.setEnabled(false);
	        		 articleAmountInput.setText("n");
	        		 articleAmountLabel.setText("Mengenangabe nicht möglich");
	        	 } else {
	        		 articleAmountInput.setEnabled(true);
	        		 articleAmountLabel.setText("Menge in "+ measuringUnits[index]);
	        		 articleAmountInput.setText("");
	        	 }
	         }
	  
	    });
		
		formularPanel.setVisible(false);
		formularErzeugen();
		
		interaktionenErzeugen();
		
		bestandErzeugen();
	}
	
	public static void main(String[] args) 
	{
		Filialleitung admin = new Filialleitung();
		admin.setTitle("Adminansicht");
		admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		admin.setExtendedState(JFrame.MAXIMIZED_BOTH);  //Fullscreen
		admin.setVisible(true);
	}

	
	public void actionPerformed(ActionEvent e) {
		
		String name = articleNameInput.getText();
		String ean = eanInput.getText();
		String articlePrice = articlePriceInput.getText();
		String articleQuantity = articleQuantityInput.getText();
		String basePrice = basePriceInput.getText();
		String basePriceUnit = String.valueOf(basePriceUnitBox.getSelectedItem());
		String articleAmount = articleAmountInput.getText();
		int index = Arrays.asList(basePriceUnits).indexOf(basePriceUnit);
		String articleAmountUnit = measuringUnits[index];
		String category = String.valueOf(categoryBox.getSelectedItem());
		
		Object[] completeArticle = { 
				name, 
				ean, 
				articlePrice, 
				articleQuantity, 
				basePrice + " "+ basePriceUnit, 
				articleAmount + " " + articleAmountUnit,
				category
			};

		dataBestand = Arrays.copyOf(dataBestand, dataBestand.length+1);
		dataBestand[dataBestand.length-1] = completeArticle;
		
		bestandErzeugen();
		clearForm();
		formularPanel.setVisible(false);
		
	}
	
	private void clearForm() {
		articleNameInput.setText("");
		eanInput.setText("");
		articlePriceInput.setText("");
		articleQuantityInput.setText("");
		basePriceInput.setText("");
		basePriceUnitBox.setSelectedItem(0);
		articleAmountInput.setText("");
		categoryBox.setSelectedItem(0);
	}
	
	private void formularErzeugen() {
		// Formular
		this.getContentPane().add(formularPanel, BorderLayout.WEST);
		formularPanel.setPreferredSize(new Dimension((int)screenWidth * 2 / 5,(int) screenHeight - 200));
		formularPanel.setLayout(new GridLayout(15,2,5,5));
		formularPanel.setBorder(BorderFactory.createTitledBorder("Formular"));
		formularPanel.add(articleNameLabel);
		formularPanel.add(articleNameInput);
		formularPanel.add(eanLabel);
		formularPanel.add(eanInput);
		formularPanel.add(articlePriceLabel);
		formularPanel.add(articlePriceInput);
		formularPanel.add(articleQuantityLabel);
		formularPanel.add(articleQuantityInput);
		formularPanel.add(basePriceLabel);
		formularPanel.add(basePriceInput);
		formularPanel.add(basePriceUnitLabel);
		formularPanel.add(basePriceUnitBox);
		formularPanel.add(articleAmountLabel);
		formularPanel.add(articleAmountInput);
		formularPanel.add(categoryLabel);
		formularPanel.add(categoryBox);
		formularPanel.add(cancelButton);
		formularPanel.add(submitButton);
	}
	
	private void interaktionenErzeugen() {
		// Hier finden alle Interaktionen statt
		this.getContentPane().add(interactionsPanel, BorderLayout.CENTER);
		interactionsPanel.setLayout(new GridLayout(15,1,5,5));
		interactionsPanel.setBorder(BorderFactory.createTitledBorder("Interaktionen"));
		interactionsPanel.add(addButton);
		interactionsPanel.add(modifyButton);
		interactionsPanel.add(removeButton);
		interactionsPanel.add(categoryInput);
		interactionsPanel.add(createCategoryButton);
	}
	
	private void bestandErzeugen() {
		bestandsListePanel = new JPanel();
	
		this.getContentPane().add(bestandsListePanel, BorderLayout.EAST);
		bestandsListePanel.setBorder(BorderFactory.createTitledBorder("Bestandsliste"));
		DefaultTableModel bestandsListeModel = new DefaultTableModel(dataBestand, columnNames);	// neue Objekte erzeugen
		bestandsListe = new JTable(bestandsListeModel); // repaint um zu updaten
		bestandsListe.setPreferredScrollableViewportSize(new Dimension((int)screenWidth * 2 / 5,(int) screenHeight - 200));
		bestandsListe.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(bestandsListe);
		bestandsListePanel.add(scrollPane);
	}
	
}
