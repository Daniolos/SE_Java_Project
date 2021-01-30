package kasino;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class Filialleitung extends JFrame implements ActionListener {
	// hier werden alle benötigten Klassen deklariert
	// Hier sind alle Interaktionsvariablen
	private JPanel formularPanel;
	private JPanel interactionsPanel;
	private JPanel bestandsListePanel;
	private JScrollPane scrollPane;
	private TableRowSorter<DefaultTableModel> tr;
	private JButton switchButton;
	private JLabel artikelLabel;
	private JButton addButton;
	private JButton modifyButton;
	private int selectedForModification;
	private JButton removeButton;
	private JLabel createCategoryLabel;
	private JTextField createCategoryInput;
	private JButton createCategoryButton;
	private JTable bestandsListe;
	private DefaultTableModel bestandsListeModel;
	private JLabel suchLabel;
	private JTextField suchInput;
	private JButton suchButton;
	
	// Hier sind alle Formular Variablen
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
	private DefaultComboBoxModel<String> categoryModel;
	private JComboBox<String> categoryBox;
	private JButton cancelButton;
	private JButton submitButton;
	private JButton clearButton;
	private JButton submitModificationsButton;
	
	
	
	// Alle Spaltennamen werden in einem String Array gespeichert
	String[] columnNames = {"Artikelname", "EAN", "Stückpreis (€)", "Stückzahl", "Grundpreis","Gp. Einheit", "Menge","M. Einheit", "Kategorie"};
	
	String[] categories = {};
	String[] grundPreisEinheiten = {}; 	
	String[] mengenEinheiten = {};
	String[][] dataBestand = {};
	
	// hier wird die Bidlschirm-Größe mit dem Dimension Object initialisiert
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();
	
	
	
	
	public Filialleitung() {
		setGrundPreisEinheiten();
		setMengenEinheiten();
		setCategories();
		setDataBestand();
		
		// hier werden alle benötigten Klassen instanziiert
		setLayout(new BorderLayout());
		formularPanel = new JPanel();
		interactionsPanel = new JPanel();
		switchButton = new JButton("zu Einkauf wechseln");
		artikelLabel = new JLabel("Artikel");
		addButton = new JButton("Artikel Hinzufügen");
		modifyButton = new JButton("Artikel Ändern");
		removeButton = new JButton("Artikel Löschen");
		createCategoryLabel = new JLabel("Kategorie"); 
		createCategoryInput = new JTextField("");
		createCategoryButton = new JButton("Erstellen");
		suchLabel = new JLabel("Suche");
		suchInput = new JTextField("");
		suchButton = new JButton("Eingabe");
		
		switchButton.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 try {
	        		 Main.switchToEinkauf();
	        	 } catch (Exception ex) {
	        		 System.out.println(ex);
	        	 }
	         }
	  
	    });
		
		addButton.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 addButton.setEnabled(false);
	        	 clearForm();
	        	 submitModificationsButton.setVisible(false);
	        	 formularPanel.setVisible(true);
	        	 
	        	 articleAmountLabel.setText("Menge in "+mengenEinheiten[0]);
	         }
	  
	    });
		
		modifyButton.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 // fill the form with data from a selected a row
	        	 // make changes to the row and save them
	        	 if (bestandsListe.getSelectedRow() == -1) {
        			 return;
        		 }
	        	 
        		 suchInput.setText("");
	        	 suche();
	        	 
	        	 int selectedRow = bestandsListe.getSelectedRow();
        		 selectedForModification = selectedRow;
        		 
        		 addButton.setEnabled(true);
        		 removeButton.setEnabled(false);
	        	 
	        	 submitModificationsButton.setVisible(true);
	        	 formularPanel.setVisible(true);
	        	 
	        	 String name = dataBestand[selectedRow][0].toString();
        		 String ean = dataBestand[selectedRow][1].toString();
        		 String articlePrice = dataBestand[selectedRow][2].toString();
        		 String articleQuantity = dataBestand[selectedRow][3].toString();
        		 String basePrice = dataBestand[selectedRow][4].toString();
        		 String basePriceUnit = dataBestand[selectedRow][5].toString();
        		 String articleAmount = dataBestand[selectedRow][6].toString();
        		 //articleAmountUnit is set automatically
        		 String category = dataBestand[selectedRow][8].toString();
	        	 
	        	 articleNameInput.setText(name);
	        	 eanInput.setText(ean);
	        	 articlePriceInput.setText(articlePrice);
	        	 articleQuantityInput.setText(articleQuantity);
	        	 basePriceInput.setText(basePrice);
	        	 basePriceUnitBox.setSelectedItem(basePriceUnit);
	        	 articleAmountInput.setText(articleAmount);
	        	 categoryBox.setSelectedItem(category);

	         }
	  
	    });
		
		removeButton.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 // remove a selected row
	        	 if (bestandsListe.getSelectedRow() == -1) {
        			 return;
        		 }
      		 
        		 suchInput.setText("");
	        	 suche();
	        	 
        		 int selectedRow = bestandsListe.getSelectedRow();
        		 
        		 String[][] copy = Arrays.copyOf(dataBestand, dataBestand.length-1);
        		 for (int i = 0, j = 0; i < dataBestand.length; i++) {
        		     if (i != selectedRow) {
        		         copy[j++] = dataBestand[i];
        		     }
        		 }
        		 dataBestand = Arrays.copyOf(copy, copy.length);
	        	 
	        	 bestandsListeModel.removeRow(selectedRow);
	        	 
	        	 
	         }
	  
	    });
		
		createCategoryButton.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 if (!createCategoryInput.getText().equals("")) {
	        		 String input = createCategoryInput.getText();
	        		 
	        		 categories = Arrays.copyOf(categories, categories.length+1);
	        		 categories[categories.length-1] = input;
	        		 
	        		 categoryModel.addElement(input);
	        		 
	        		 createCategoryInput.setText("");
	        	 }
	         }
	  
	    });
		
		suchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		  		suche();
		  	}
		});

	
		
		articleNameLabel = new JLabel("Artikelname");
		articleNameInput = new JTextField("");
		eanLabel = new JLabel("EAN / PLU");
		eanInput = new JTextField("");
		articlePriceLabel = new JLabel("Stückpreis in €");
		articlePriceInput = new JTextField("");
		articleQuantityLabel = new JLabel("Stückzahl");
		articleQuantityInput = new JTextField("");
		basePriceLabel = new JLabel("Grundpreis");
		basePriceInput = new JTextField("");
		basePriceUnitLabel = new JLabel("Grundpreiseinheit");
		basePriceUnitBox = new JComboBox<String>(grundPreisEinheiten);
		articleAmountLabel = new JLabel("Menge in "); // soll dynamisch anpassbar
		articleAmountInput = new JTextField("");
		categoryLabel = new JLabel("Kategorie");
		categoryModel = new DefaultComboBoxModel<String>(categories);
		categoryBox = new JComboBox<>(categoryModel);
		submitButton = new JButton("Artikel Hinzufügen");
		submitModificationsButton = new JButton("Artikel Ändern");
		clearButton = new JButton("Formular leeren");
		cancelButton = new JButton("Abbrechen");
		
		clearButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent arg0) {
	        	 clearForm();
	         }
	    });
		
		cancelButton.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 addButton.setEnabled(true);
	        	 removeButton.setEnabled(true);
	        	 clearForm();
	        	 formularPanel.setVisible(false);
	         }
	  
	    });
		
		submitButton.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 removeButton.setEnabled(true);
	        	 addButton.setEnabled(true);
	        	 
	        	 String name = articleNameInput.getText();
	        	 String ean = eanInput.getText();
	        	 String articlePrice = articlePriceInput.getText();
	        	 String articleQuantity = articleQuantityInput.getText();
	        	 String basePrice = basePriceInput.getText();
	        	 String basePriceUnit = String.valueOf(basePriceUnitBox.getSelectedItem());
	        	 String articleAmount = articleAmountInput.getText();
	        	 int index = Arrays.asList(grundPreisEinheiten).indexOf(basePriceUnit);
	        	 String articleAmountUnit = mengenEinheiten[index];
	        	 String category = String.valueOf(categoryBox.getSelectedItem());
	     		
	        	 String[] completeArticle = { 
	     				name, 
	     				ean, 
	     				articlePrice, 
	     				articleQuantity, 
	     				basePrice,
	     				basePriceUnit, 
	     				articleAmount,
	     				articleAmountUnit,
	     				category
	     			};
	        	 
	        	 //hier überprüfen ob die eingegebenen Eigenschaften in ordnung sind

	        	 dataBestand = Arrays.copyOf(dataBestand, dataBestand.length+1);
	        	 dataBestand[dataBestand.length-1] = completeArticle;
	     		
	        	 bestandsListeModel.addRow(completeArticle);
	        	 clearForm();
	        	 formularPanel.setVisible(false);
	         }
	  
	    });
		
		submitModificationsButton.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 removeButton.setEnabled(true);
	        	 
	        	 String name = articleNameInput.getText();
	        	 String ean = eanInput.getText();
	        	 String articlePrice = articlePriceInput.getText();
	        	 String articleQuantity = articleQuantityInput.getText();
	        	 String basePrice = basePriceInput.getText();
	        	 String basePriceUnit = String.valueOf(basePriceUnitBox.getSelectedItem());
	        	 String articleAmount = articleAmountInput.getText();
	        	 int index = Arrays.asList(grundPreisEinheiten).indexOf(basePriceUnit);
	        	 String articleAmountUnit = mengenEinheiten[index];
	        	 String category = String.valueOf(categoryBox.getSelectedItem());
	     		
	        	 String[] completeArticle = { 
	     				name, 
	     				ean, 
	     				articlePrice, 
	     				articleQuantity, 
	     				basePrice,
	     				basePriceUnit, 
	     				articleAmount,
	     				articleAmountUnit,
	     				category
	     			};
	        	 
	        	 //hier überprüfen ob die eingegeben Eigenschaften in Ordnung sind
	        	 
	        	 int selectedRow = selectedForModification;
        		 if (selectedRow == -1) {
        			 return;
        		 }
        		 
        		 String[][] copy = Arrays.copyOf(dataBestand, dataBestand.length-1);
        		 for (int i = 0, j = 0; i < dataBestand.length; i++) {
        		     if (i != selectedRow) {
        		         copy[j++] = dataBestand[i];
        		     }
        		 }
        		 dataBestand = Arrays.copyOf(copy, copy.length);
	        	 
	        	 bestandsListeModel.removeRow(selectedRow);
	        	 
	        	 dataBestand = Arrays.copyOf(dataBestand, dataBestand.length+1);
	        	 dataBestand[dataBestand.length-1] = completeArticle;
	     		
	        	 bestandsListeModel.addRow(completeArticle);
	        	 clearForm();
	        	 formularPanel.setVisible(false);
	         }
	  
	    });
		
		basePriceUnitBox.addActionListener(new ActionListener() {

	         public void actionPerformed(ActionEvent arg0) {
	        	 try {
			        	 String basePriceUnitValue = String.valueOf(basePriceUnitBox.getSelectedItem());
			        	 int index = Arrays.asList(grundPreisEinheiten).indexOf(basePriceUnitValue);
			        	 if (basePriceUnitValue == "n"){ 
			        		 articleAmountInput.setEnabled(false);
			        		 articleAmountInput.setText("n");
			        		 articleAmountLabel.setText("Mengenangabe nicht möglich");
			        	 } else {
			        		 articleAmountInput.setEnabled(true);
			        		 articleAmountLabel.setText("Menge in "+ mengenEinheiten[index]);
			        		 articleAmountInput.setText("");
			        	 }
	        	 } catch(Exception ex) {
	        		 System.out.println(ex);
	        	 }
	         }
	  
	    });
		
		formularPanel.setVisible(false);
		formularErzeugen();
		
		interaktionenErzeugen();
		
		bestandsListeErzeugen();

	}
	
	private void setGrundPreisEinheiten() {
		//import Array from DataBase
		String[] unitsFromDB = { "€/kg", "€/l", "n"};
		grundPreisEinheiten = Arrays.copyOf(unitsFromDB, unitsFromDB.length);
		
	}
	
	private void setMengenEinheiten() {
		//import Array from DataBase
		String[] unitsFromDB = { "kg", "l", "n"};
		mengenEinheiten = Arrays.copyOf(unitsFromDB, unitsFromDB.length);
	}
	
	private void setCategories() {
		//import 
	}

	private void setDataBestand() {
		//import Array from Database
		
		
	}

	public static void main(String[] args) 
	{
		Filialleitung admin = new Filialleitung();
		admin.setTitle("Adminansicht");
		admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		admin.setExtendedState(JFrame.MAXIMIZED_BOTH);  //Fullscreen
		admin.setVisible(true);
	}
	
	private void suche() {
		String text = suchInput.getText();
  		if (text.length() == 0) {
        tr.setRowFilter(null);
  		} else {
        tr.setRowFilter(RowFilter.regexFilter(text, 0, 1));
  		}
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
		formularPanel.setPreferredSize(new Dimension((int)screenWidth * 2 / 10,(int) screenHeight - 200));
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
		formularPanel.add(clearButton);
		formularPanel.add(submitModificationsButton);
		submitModificationsButton.setVisible(false);
	}
	
	private void interaktionenErzeugen() {
		// Hier finden alle Interaktionen statt
		this.getContentPane().add(interactionsPanel, BorderLayout.CENTER);
		interactionsPanel.setLayout(new GridLayout(15,1,5,5));
		interactionsPanel.setBorder(BorderFactory.createTitledBorder("Interaktionen"));
		interactionsPanel.add(switchButton);
		interactionsPanel.add(artikelLabel);
		interactionsPanel.add(addButton);
		interactionsPanel.add(modifyButton);
		interactionsPanel.add(removeButton);
		interactionsPanel.add(createCategoryLabel);
		interactionsPanel.add(createCategoryInput);
		interactionsPanel.add(createCategoryButton);
		interactionsPanel.add(suchLabel);
		interactionsPanel.add(suchInput);
		interactionsPanel.add(suchButton);
	}
	
	private void bestandsListeErzeugen() {
		//Bestandslisten GUI
		bestandsListePanel = new JPanel();
		
		this.getContentPane().add(bestandsListePanel, BorderLayout.EAST);
		bestandsListePanel.setBorder(BorderFactory.createTitledBorder("Bestandsliste"));
		bestandsListeModel = new DefaultTableModel(dataBestand, columnNames);	// neue Objekte erzeugen
		bestandsListe = new JTable(bestandsListeModel){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		bestandsListe.getTableHeader().setEnabled(false);
		bestandsListe.setPreferredScrollableViewportSize(new Dimension((int)screenWidth * 2 / 3,(int) screenHeight-200));
		bestandsListe.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(bestandsListe);
		bestandsListePanel.add(scrollPane);
		tr = new TableRowSorter<DefaultTableModel>(bestandsListeModel);
		bestandsListe.setRowSorter(tr);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
