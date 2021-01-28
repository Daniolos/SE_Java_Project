package kassensystem;

import java.awt.*;
import java.util.Vector;
import java.awt.event.*;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.Color;
import java.lang.Math.*;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.JTable;

public class GUIEinkauf extends JFrame
	{
	private JPanel interactionsPanel;
	private JPanel einkaufsListePanel;
	private JPanel bestandsListePanel;
	private JLabel zwischensumme;
	private JButton addButton;
	private JButton addValueButton;
	private JButton removeButton;
	private JButton removeAllButton;
	private JButton searchButton;
	private JTable bestandsListe;
	private JTable einkaufsListe;
	private JTextField insertValue;
	private JTextField searchField;
	private float zValue = 0f;

	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();
	
	
	/**
	 * 
	 * @param Input - ein Float, der den zu verändernden Betrag der Zwischensumme enthält
	 */
	private void changeZwischensumme (Float Input) {
		zValue = zValue + Input;
		zwischensumme.setText("Zwischensumme: "+zValue + " €");
		}

	

	private GUIEinkauf() {
		setLayout(new BorderLayout());
		JPanel einkaufsListePanel = new JPanel();
		JPanel interactionsPanel = new JPanel();
		JPanel bestandsListePanel = new JPanel();
		addButton = new JButton("Hinzufügen");
		removeButton = new JButton("Ausgewähltes Element entfernen");
		removeAllButton = new JButton("Alles entfernen");
		insertValue = new JTextField("Hier Wert eingeben");
		addValueButton = new JButton("Menge eingeben");
		zwischensumme = new JLabel("Zwischensumme: "+ zValue +" €");
		searchField = new JTextField("Suchwort: ");
		searchButton = new JButton("Suche!");
		
	
		
		
		String[] columnNames = 
			{"Artikelname", "EAN","Stückpreis", "Stückzahl", "Grundpreis", "Grundpreiseinheit", "Menge", "Mengeneinheit","Kategorie"};
			
		Object[][] dataBestand = 
		{												// Datensätze müssen später noch aus txt oder ähnlichen dateien geholt werden
			{"Apfel", "0192992", "19.92", "200","n","n","n", "Stück","Obst"},
			{"Tomate", "3432342", "2.34", "3","n","n","n", "Stück","Gemüse"},
			{"Brot", "2345323","213.32", "7","n","n","n", "Stück","Backwaren"},
			{"Rinderfilet", "6787383","n","n", "2498.33", "€/Kilogramm" , "20000" , "Gramm","Fleisch"},
			{"Rinderwurst", "2327383","n","n", "298.13", "€/Kilogramm" , "200" , "Kilogramm","Fleisch"},
		};
		/*
		 * Zur Übersichtlichkeit und Wartungsfreundlichkeit:
		 * Die Endpreisspalte ist immer die letzte Spalte, nur bei einkausListe(-Model) verwendbar!!!!
		 */
		
		int NameSpalte = 0;
		int EANSpalte = 1;
		int StkPreis = 2;
		int StkZahl = 3;
		int GrundPreis= 4;
		int GPreisEinheitSpalte= 5;
		int MengeSpalte= 6;
		int MengeEinheitSpalte = 7;
		// 8 wäre Kategorie, wird hier aber nicht genutzt
		int EndpreisSpalte = 9; 		
		
		
		Object[][] dataEinkauf = {};
		
		
	
		
		this.getContentPane().add(einkaufsListePanel, BorderLayout.WEST);
		einkaufsListePanel.setBorder(BorderFactory.createTitledBorder("Einkaufsliste"));
		DefaultTableModel einkaufsListeModel = new DefaultTableModel(dataEinkauf, columnNames);
		einkaufsListe = new JTable(einkaufsListeModel);
		einkaufsListe.setPreferredScrollableViewportSize(new Dimension((int)screenWidth / 3,(int) screenHeight - 200));
		einkaufsListe.setFillsViewportHeight(true);
		JScrollPane scrollPane2 = new JScrollPane(einkaufsListe);
		einkaufsListePanel.add(scrollPane2);
	
		this.getContentPane().add(interactionsPanel, BorderLayout.CENTER);
		interactionsPanel.setLayout(new GridLayout(15,1,5,5));
		interactionsPanel.setBorder(BorderFactory.createTitledBorder("Interactionen"));
		interactionsPanel.add(addButton);
		interactionsPanel.add(removeButton);
		interactionsPanel.add(removeAllButton);
		interactionsPanel.add(insertValue);
		interactionsPanel.add(addValueButton);
		interactionsPanel.add(zwischensumme);
		interactionsPanel.add(searchField);
		interactionsPanel.add(searchButton);
		insertValue.setVisible(false);
		addValueButton.setVisible(false);
		
		this.getContentPane().add(bestandsListePanel, BorderLayout.EAST);
		bestandsListePanel.setBorder(BorderFactory.createTitledBorder("Bestandsliste"));
		DefaultTableModel bestandsListeModel = new DefaultTableModel(dataBestand, columnNames);	// neue Objekte erzeugen
		bestandsListe = new JTable(bestandsListeModel);
		bestandsListe.setPreferredScrollableViewportSize(new Dimension((int)screenWidth / 3,(int) screenHeight - 200));
		bestandsListe.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(bestandsListe);
		bestandsListePanel.add(scrollPane);
	
	
		// Zum späteren späteren Filtern
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(bestandsListeModel);
		bestandsListe.setRowSorter(tr);	
		
		einkaufsListeModel.addColumn("Endpreis");
		einkaufsListe.getColumnModel().getColumn(EANSpalte).setMinWidth(0);	
		einkaufsListe.getColumnModel().getColumn(EANSpalte).setMaxWidth(0); //	--> um EAN in der Einkaufsliste zu verbergen
		einkaufsListe.getColumnModel().getColumn(StkPreis).setMinWidth(0);	
		einkaufsListe.getColumnModel().getColumn(StkPreis).setMaxWidth(0);
		einkaufsListe.getColumnModel().getColumn(GPreisEinheitSpalte).setMinWidth(0);	
		einkaufsListe.getColumnModel().getColumn(GPreisEinheitSpalte).setMaxWidth(0);
		
		
		/**
		 * Hier wird der "addButton" mit seinen Funktionen definiert. Dieser überträgt bei Benutzung die ausgewählte Zeile der Bestandsliste in die Einkaufsliste.
		 * Anfangs wird außerdem direkt abgefangen, dass der Benutzer nicht mehr Artikel übertragen kann, als vorhanden sind.
		 * 
		 * Handelt es sich um ein Produkt dessen Menge in "Stück" angegeben wird, wird pro Mausklick je ein Produkt übernommen und einzeln in der Einkaufsliste aufgeführt.
		 * 
		 * Handelt es sich um ein Produkt dessen Menge nicht in Stück angegeben ist, so wird ein extra Textfeld mit Button eingeblendet ("insertValue" und "addValueButton" zum bestätigen).
		 * Mit diesen kann dann eine konkrete Menge durch den Nutzer angegeben werden.
		 * Je nach Produkt und Preis-/Mengeneinheit wird entsprechend umgerechnet (um den Faktor "Mult"). Dieser wird zur Berechnung des endgültigen Preises für das Produkt und der Zwischensumme verwendet.
		 * 
		 */
		addButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				insertValue.setVisible(false);
				addValueButton.setVisible(false);	
				if (bestandsListe.getValueAt(bestandsListe.getSelectedRow(), StkZahl) != "n") {
						if (Integer.parseInt((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), StkZahl)) > 0)
						{
					
						bestandsListeModel.setValueAt(String.valueOf(Integer.parseInt((String)bestandsListeModel.getValueAt(bestandsListe.getSelectedRow(), StkZahl)) - 1), bestandsListe.getSelectedRow(), StkZahl);
						Vector neuerArtikelAufEinkaufsListe = new  Vector(bestandsListeModel.getDataVector().elementAt(bestandsListe.getSelectedRow()));
						einkaufsListeModel.addRow(neuerArtikelAufEinkaufsListe);
						einkaufsListeModel.setValueAt("1", einkaufsListeModel.getRowCount() - 1, StkZahl);
						einkaufsListeModel.setValueAt((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), StkPreis), einkaufsListeModel.getRowCount() - 1, EndpreisSpalte);
						changeZwischensumme(Float.parseFloat((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), StkPreis)));}
					}
					else
						{
						if (Integer.parseInt((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeSpalte)) > 0) {
							insertValue.setVisible(true);
							addValueButton.setVisible(true);
							addValueButton.addActionListener(new ActionListener () 
							{
								public void actionPerformed(ActionEvent e) {
									if (Integer.parseInt(((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeSpalte))) >= Integer.parseInt(insertValue.getText()) && Integer.parseInt(insertValue.getText()) > 0 ) {
										
										if (((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeEinheitSpalte)).equals(((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), GPreisEinheitSpalte)).substring(2,((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), GPreisEinheitSpalte)).length()))) {
											System.out.println("1");
											bestandsListeModel.setValueAt(String.parseString(Integer.parseInt((String)bestandsListeModel.getValueAt(bestandsListe.getSelectedRow(), MengeSpalte)) - Integer.parseInt(insertValue.getText()))), bestandsListe.getSelectedRow(), MengeSpalte);
											Vector neuerArtikelAufEinkaufsListe = new  Vector(bestandsListeModel.getDataVector().elementAt(bestandsListe.getSelectedRow()));
											einkaufsListeModel.addRow(neuerArtikelAufEinkaufsListe);
											einkaufsListeModel.setValueAt(insertValue.getText(), einkaufsListeModel.getRowCount() - 1, MengeSpalte);
											einkaufsListeModel.setValueAt(Integer.parseInt(insertValue.getText()) * Float.parseFloat(((String)einkaufsListeModel.getValueAt(einkaufsListeModel.getRowCount() - 1, GrundPreis))), einkaufsListeModel.getRowCount() - 1, EndpreisSpalte);
								
										}
										else {
											
											float Mult = 10f;
											/*
											 * Hier werden bei den nicht-Stück Mengen die einzelnen Umrechnungen zwischen den Einheiten durchgeführt
											 */
											if (((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeEinheitSpalte)).contains("Gramm") & ((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), GPreisEinheitSpalte)).contains("Kilogramm")) {
												Mult = 0.001f;
											};
											
											if (((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeEinheitSpalte)).contains("Kilogramm") & ((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), GPreisEinheitSpalte)).contains("Gramm")) {
												Mult = 10f;
											};
											
											if (((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeEinheitSpalte)).contains("Liter") & ((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), GPreisEinheitSpalte)).contains("Milliliter")) {
												Mult = 10f;
											};
											
											if (((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeEinheitSpalte)).contains("Milliliter") & ((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), GPreisEinheitSpalte)).contains("Liter")) {
												Mult = 0.001f;
											};
											bestandsListeModel.setValueAt(((Integer) bestandsListeModel.getValueAt(bestandsListe.getSelectedRow(), MengeSpalte)) - Integer.parseInt(insertValue.getText()), bestandsListe.getSelectedRow(), MengeSpalte);
											Vector neuerArtikelAufEinkaufsListe = new  Vector(bestandsListeModel.getDataVector().elementAt(bestandsListe.getSelectedRow()));
											einkaufsListeModel.addRow(neuerArtikelAufEinkaufsListe);
											einkaufsListeModel.setValueAt(Integer.parseInt(insertValue.getText()), einkaufsListeModel.getRowCount() - 1, MengeSpalte);
											einkaufsListeModel.setValueAt(Integer.parseInt(insertValue.getText()) * Mult * ((Float)einkaufsListeModel.getValueAt(einkaufsListeModel.getRowCount()-1, GrundPreis)), einkaufsListeModel.getRowCount() - 1, EndpreisSpalte);
											changeZwischensumme((Float)einkaufsListeModel.getValueAt(einkaufsListeModel.getRowCount()-1, EndpreisSpalte));
											insertValue.setVisible(false);
											addValueButton.setVisible(false);
										}
									}
								}
						});
		
		/**
		 * Dieser MouseListener löscht den Inhalt des Eingabefeldes "insertValue", sobald dieses mit der Maus angeklickt wird. 
		 * Dies dient vorallem dazu die Bedienerfreundlichkeit der Funktion "addValueButton" zu erhöhen. 
		 * Beachte: Dieses wird nur angezeigt, wenn ein Produkt zur Einkaufsliste hinzugefügt werden soll, dessen Einheit nicht in "Stück" angegeben ist. (siehe auch "addButton"-Funktion)
		 * 
		 */
		insertValue.addMouseListener(new MouseAdapter() 
		{
	        @Override
	        public void mouseClicked(MouseEvent e)
	        {
	            insertValue.setText("");	
	        }
		});
		/**
		 *  Dieser MouseListener löscht den Inhalt des Eingabefeldes "searchField", sobald dieses mit der Maus angeklickt wird. 
		 * Dies dient vorallem dazu die Bedienerfreundlichkeit der Funktion "addValueButton" zu erhöhen. 
		 *  
		 */
		searchField.addMouseListener(new MouseAdapter() 
		{
	        @Override
	        public void mouseClicked(MouseEvent e)
	        {
	        	searchField.setText("");	
	        }
		});
		
		/**
		 * Dieser Actionlistener löscht einzelne Elemente aus der Einkaufsliste. Es wird immer das Element gelöscht das vom Nutzer ausgewählt wurde.
		 * Dieser geht die gesamte Bestandsliste durch, bis das, im Bestand, zu reduzierende Produkt gefunden worden ist, dieses wird dann um den entsprechenden Betrag verringert.
		 * 		Pro Stück jeweils um den Wert 1, bei nicht-Stück Mengen um den jeweiligen Betrag, um den das Produkt vorher hinzugefügt wurde.
		 */
		removeButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				Object name = einkaufsListeModel.getValueAt(einkaufsListe.getSelectedRow(), NameSpalte);
				int i = 0;
				while (name != bestandsListeModel.getValueAt(i,NameSpalte)) 
				{
					i++;
				}
				if (bestandsListe.getValueAt(bestandsListe.getSelectedRow(), StkPreis) != "n") {
					bestandsListeModel.setValueAt(((Integer) bestandsListeModel.getValueAt(i,MengeSpalte)) + 1, i, MengeSpalte);
					}	
					else {
					bestandsListeModel.setValueAt(((Integer) einkaufsListe.getValueAt(einkaufsListe.getSelectedRow(),MengeSpalte)) + (Integer)bestandsListe.getValueAt(i,MengeSpalte), i, MengeSpalte);
					}
				
				changeZwischensumme((Float) (einkaufsListe.getValueAt(einkaufsListe.getSelectedRow(), EndpreisSpalte)) - 2* (Float) (einkaufsListe.getValueAt(einkaufsListe.getSelectedRow(), EndpreisSpalte)));
				einkaufsListeModel.removeRow(einkaufsListe.getSelectedRow());
				}
			});
		/**
		 * Funktioniert im Kern wie die Funktionalität des "removeButtons". Es wird zusätzlich für jedes Element über die gesamte Einkaufsliste itteriert, um jedes Element zu entfernen.
		 */
		removeAllButton.addActionListener(new ActionListener()  // Hier muss mit den nicht-Stück Menge noch etwas passieren
		{
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < (Integer)einkaufsListe.getRowCount(); i++) 
				{
					Object name = einkaufsListeModel.getValueAt(i, NameSpalte);
					int j = 0;
					while (name != bestandsListeModel.getValueAt(j,NameSpalte)) 
					{
						j++;
					}
					if (bestandsListe.getValueAt(bestandsListe.getSelectedRow(), StkPreis) != "n") {
					bestandsListeModel.setValueAt(((Integer) bestandsListeModel.getValueAt(j,MengeSpalte)) + 1, j, MengeSpalte);
					}	
					else {
					bestandsListeModel.setValueAt(((Integer) einkaufsListe.getValueAt(i,MengeSpalte)), j, MengeSpalte);
					}
				}
				einkaufsListeModel.getDataVector().removeAllElements();
				einkaufsListe.repaint();
				zwischensumme.setText("Zwischensumme: 0€");
					zValue = 0f;
					}
				});	
		
		/**
		 * Nach dem Betätigen des "searchButtons" wird diese Suche durchgeführt.
		 * Mittels eines RowFilters wird die Liste nach der Eingabe im "searchField" gefiltert. D.h., alle Artikel werden angezeigt, die den Suchtext enthalten. Ist keine Suche eingegeben, wird alles ungefiltert ausgegeben.
		 */
		searchButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		  		String text = searchField.getText();
		          if (text.length() == 0) {
		            tr.setRowFilter(null);
		          } else {
		            tr.setRowFilter(RowFilter.regexFilter(text, NameSpalte, EANSpalte));
		          }
		  	}
		});
		
	}
	
		
		
		
	
	public static void main(String[] args) 
	{
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
		GUIEinkauf gui = new GUIEinkauf();
		gui.setTitle("Einkaufsansicht");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH);  //Fullscreen
		gui.setVisible(true);}});
	}
}