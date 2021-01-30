import java.awt.*;
import java.util.Arrays;
import java.util.Vector;
import java.awt.event.*;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.*;

import Database.DatenLeser;
import Database.DatenSchreiber;
import Database.Lager;
import Parsing.Artikel;
import Parsing.XMLParser;

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
	//alle graphischen Elemente deklarieren:
	private JPanel interactionsPanel;
	private JPanel interactionsPanelAbschluss;
	private JPanel einkaufsListePanel;
	private JPanel bestandsListePanel;
	private JTextArea hinweisArea;
	private JTextArea mengeHinweisArea;
	private JTextArea AbschlussArea;
	private JTextArea ErrorArea;
	private JLabel zwischensumme;
	private JButton addButton;
	private JButton addValueButton;
	private JButton removeButton;
	private JButton removeAllButton;
	private JButton einkaufAbschliessenButton;
	private JButton WechelgeldBerechnenButton;
	private JButton einkaufEndgueltigAbschliessenButton;
	private JTable bestandsListe;
	private JTable einkaufsListe;
	private JTextField insertValue;
	private JTextField searchField;
	private JTextField BarGeldField;
	private float zValue = 0f;
	private Lager lager;

	int NameSpalte = 0;
	int EANSpalte = 1;
	int StkPreis = 2;
	int StkZahl = 3;
	int GrundPreis= 4;
	int GPreisEinheitSpalte= 5;
	int MengeSpalte= 6;
	int MengeEinheitSpalte = 7;
	int KategorieSpalte = 8;
	int EndpreisSpalte = 9; 	
	
	//Bildschirmauflösung des Nutzers ermitteln um das Fenster und die Elemente dynamisch anpassen
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();
	
	
	/**
	 * Diese Funktion aktualisiert die Zwischensumme, welche in der Variable zValue gespeichert wird, um den Wert "Input".
	 * Anschließend wird der Wert noch auf 2 Nachkommastellen gerundet.
	 * @param Input - ein Float, der den zu verändernden Betrag der Zwischensumme enthält
	 */
	private void changeZwischensumme (Float Input) {
		zValue = zValue + Input;
		zValue = Math.round(zValue * 100);
		zValue = zValue / 100;
		zwischensumme.setText("Zwischensumme: "+zValue + " €");
		}
	
	private int getRow(JTable table) {
		int row = table.getSelectedRow();
		if (table.getRowSorter() != null ) {
		    row = table.getRowSorter().convertRowIndexToModel(row);
		    }
		return row;
	}
	/**
	 * 
	 */
	
	
	

	private void saveBestand(DefaultTableModel daten) 
	 {
		for (int i = 0; i < daten.getRowCount(); i++)
		{
			Artikel article = lager.search((String)daten.getValueAt(i, EANSpalte));
			
			
			try 
			{
			if (!article.getAnzahl().equals("n"))
			{
				float merke = Float.parseFloat(article.getAnzahl());
				article.setAnzahl(String.valueOf(merke -Float.parseFloat((String)daten.getValueAt(i, StkZahl))));
				continue;
			}
			
			float merke = Float.parseFloat(article.getGewicht());
			article.setGewicht(String.valueOf(merke -Float.parseFloat((String)daten.getValueAt(i, MengeSpalte))));
			continue;
			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
		}
	 }

	private GUIEinkauf() 
	{
		
		//Initialisierung aller graphischen Elemente
		setLayout(new BorderLayout());
		JPanel einkaufsListePanel = new JPanel();
		JPanel interactionsPanel = new JPanel();
		JPanel interactionsPanelAbschluss = new JPanel();
		JPanel bestandsListePanel = new JPanel();
		addButton = new JButton("Hinzufügen");
		removeButton = new JButton("Ausgewähltes Element entfernen");
		removeAllButton = new JButton("Alles entfernen");
		insertValue = new JTextField("Hier Wert eingeben. ");
		mengeHinweisArea = new JTextArea("Beachte! \nDie Menge muss in der Einheit angegeben werden, wie diese auch ein der Bestandsliste in der Spalte Menge angegeben ist. \nKommata bitte als Punkt angeben. (z.B. 2.3)");
		addValueButton = new JButton("Menge eingeben");
		zwischensumme = new JLabel("Zwischensumme: "+ zValue +" €");
		searchField = new JTextField("Suchwort: ");
		BarGeldField = new JTextField("erhaltene Bargeldsumme eingeben");
		hinweisArea = new JTextArea("Suchefelder werden durch hineinklicken automatisch gelöscht.");
		AbschlussArea = new JTextArea("Endsumme, Wechselgeld");
		ErrorArea = new JTextArea("");
		einkaufAbschliessenButton = new JButton("Einkauf abschließen.");
		WechelgeldBerechnenButton = new JButton("Wechselgeld berechnen");
		einkaufEndgueltigAbschliessenButton = new JButton("Einkauf endgültig abschließen und neuen Einkauf starten.");
		
	
		
		
		String[] columnNames = 
			{"Artikelname", "EAN","Stückpreis", "Stückzahl", "Grundpreis", "Grundpreiseinheit", "Menge", "Mengeneinheit","Kategorie"};
		/*
		 * Zur Übersichtlichkeit und Wartungsfreundlichkeit:
		 * Die Endpreisspalte ist immer die letzte Spalte, nur bei einkausListe(-Model) verwendbar!!!!
		 */
		
		
		
		
		Object[][] dataBestand = 
		{											
//			{"Apfel", "0192992", "19.92", "200","n","n","n", "Stück","Obst"},
//			{"Tomate", "3432342", "2.34", "3","n","n","n", "Stück","Gemüse"},
//			{"Brot", "2345323","213.32", "7","n","n","n", "Stück","Backwaren"},
//			{"Rinderfilet", "6787383","n","n", "2498.33", "€/Kilogramm" , "20000" , "Gramm","Fleisch"},
//			{"Rinderwurst", "2327383","n","n", "298.13", "€/100 Gramm" , "200" , "Kilogramm","Fleisch"},
		};
	
		
		
		Object[][] dataEinkauf = {};
		
		
		this.getContentPane().add(einkaufsListePanel, BorderLayout.WEST);
		einkaufsListePanel.setBorder(BorderFactory.createTitledBorder("Einkaufsliste"));
		DefaultTableModel einkaufsListeModel = new DefaultTableModel(dataEinkauf, columnNames);
		einkaufsListe = new JTable(einkaufsListeModel);
		einkaufsListe.setPreferredScrollableViewportSize(new Dimension((int)screenWidth / 3,(int) screenHeight - 200));
		einkaufsListe.setFillsViewportHeight(true);
		JScrollPane scrollPane2 = new JScrollPane(einkaufsListe);
		einkaufsListePanel.add(scrollPane2);
	
		
		this.getContentPane().add(interactionsPanelAbschluss, BorderLayout.CENTER);
		interactionsPanelAbschluss.setLayout(new GridLayout(15,1,5,5));
		interactionsPanelAbschluss.setVisible(false);
		interactionsPanelAbschluss.setBorder(BorderFactory.createTitledBorder("Einkaufsabschluss"));
		interactionsPanelAbschluss.add(BarGeldField);
		interactionsPanelAbschluss.add(WechelgeldBerechnenButton);
		interactionsPanelAbschluss.add(AbschlussArea);
		AbschlussArea.setEditable(false);
		interactionsPanelAbschluss.add(ErrorArea);
		ErrorArea.setEditable(false);
		ErrorArea.setBackground(interactionsPanel.getBackground());
		interactionsPanelAbschluss.add(einkaufEndgueltigAbschliessenButton);
	
				
		this.getContentPane().add(interactionsPanel, BorderLayout.CENTER);
		interactionsPanel.setLayout(new GridLayout(15,1,5,5));
		interactionsPanel.setBorder(BorderFactory.createTitledBorder("Interaktionen"));
		interactionsPanel.add(addButton);
		interactionsPanel.add(removeButton);
		interactionsPanel.add(removeAllButton);
		interactionsPanel.add(insertValue);
		interactionsPanel.add(mengeHinweisArea);
		mengeHinweisArea.setBackground(interactionsPanel.getBackground());
		mengeHinweisArea.setForeground(Color.red);
		interactionsPanel.add(addValueButton);
		interactionsPanel.add(searchField);
		interactionsPanel.add(hinweisArea);
		interactionsPanel.add(einkaufAbschliessenButton);
		interactionsPanel.add(zwischensumme);
		hinweisArea.setEditable(false);
		hinweisArea.setBackground(interactionsPanel.getBackground());
		insertValue.setVisible(false);
		addValueButton.setVisible(false);
		mengeHinweisArea.setVisible(false);
		
		
		this.getContentPane().add(bestandsListePanel, BorderLayout.EAST);
		bestandsListePanel.setBorder(BorderFactory.createTitledBorder("Bestandsliste"));
		DefaultTableModel bestandsListeModel = new DefaultTableModel(dataBestand, columnNames);	// neue Objekte erzeugen
		bestandsListe = new JTable(bestandsListeModel);
		bestandsListe.setPreferredScrollableViewportSize(new Dimension((int)screenWidth / 3,(int) screenHeight - 200));
		bestandsListe.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(bestandsListe);
		bestandsListePanel.add(scrollPane);
		
		DatenLeser bla = new DatenLeser();
	    XMLParser xml = new XMLParser(bla.getData());
	    System.out.println(xml.getXML());
//	    String article = xml.getChild("articles");
		lager = new Lager(xml.getXML());
		String[][] Array = lager.toStringArray();
		for (int i = 0; i < Array.length; i++)
		{
			bestandsListeModel.addRow(Array[i]);
			System.out.println(Array[i]);
		}
		System.out.println(Arrays.deepToString(Array));
		
	
		// Zum späteren späteren Filtern
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(bestandsListeModel);
		bestandsListe.setRowSorter(tr);	
		
		einkaufsListeModel.addColumn("Endpreis");
		einkaufsListe.getColumnModel().getColumn(EANSpalte).setMinWidth(0);	
		einkaufsListe.getColumnModel().getColumn(EANSpalte).setMaxWidth(0); //	--> um EAN in der Einkaufsliste zu verbergen
		einkaufsListe.getColumnModel().getColumn(StkPreis).setMinWidth(0);	
		einkaufsListe.getColumnModel().getColumn(StkPreis).setMaxWidth(0);
		einkaufsListe.getColumnModel().getColumn(StkZahl).setMinWidth(0);	
		einkaufsListe.getColumnModel().getColumn(StkZahl).setMaxWidth(0);
		einkaufsListe.getColumnModel().getColumn(KategorieSpalte).setMinWidth(0);	
		einkaufsListe.getColumnModel().getColumn(KategorieSpalte).setMaxWidth(0);
		
		
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
				if (bestandsListeModel.getValueAt(getRow(bestandsListe), StkZahl) != "n") {
						if (Float.parseFloat((String)bestandsListeModel.getValueAt(getRow(bestandsListe), StkZahl)) > 0)
						{
						bestandsListeModel.setValueAt(String.valueOf(Float.parseFloat((String)bestandsListeModel.getValueAt(getRow(bestandsListe), StkZahl)) - 1), getRow(bestandsListe), StkZahl);
						Vector neuerArtikelAufEinkaufsListe = new  Vector(bestandsListeModel.getDataVector().elementAt(getRow(bestandsListe)));
						einkaufsListeModel.addRow(neuerArtikelAufEinkaufsListe);
						einkaufsListeModel.setValueAt("1", einkaufsListeModel.getRowCount() - 1, StkZahl);
						einkaufsListeModel.setValueAt((String)bestandsListeModel.getValueAt(getRow(bestandsListe), StkPreis), einkaufsListeModel.getRowCount() - 1, EndpreisSpalte);
						changeZwischensumme(Float.parseFloat((String)bestandsListeModel.getValueAt(getRow(bestandsListe), StkPreis)));}
				}
				
					else
						{
						if (Float.parseFloat((String)bestandsListeModel.getValueAt(getRow(bestandsListe), MengeSpalte)) > 0) {
							insertValue.setVisible(true);
							addValueButton.setVisible(true);
							mengeHinweisArea.setVisible(true);
							addValueButton.addActionListener(new ActionListener () 
							{
								public void actionPerformed(ActionEvent e) {
									if (Float.parseFloat(((String)bestandsListeModel.getValueAt(getRow(bestandsListe), MengeSpalte))) >= Float.parseFloat(insertValue.getText()) && Float.parseFloat(insertValue.getText()) > 0 ) {
										if (((String)bestandsListeModel.getValueAt(getRow(bestandsListe), MengeEinheitSpalte)).equals(((String)bestandsListeModel.getValueAt(getRow(bestandsListe), GPreisEinheitSpalte)).substring(2,((String)bestandsListeModel.getValueAt(getRow(bestandsListe), GPreisEinheitSpalte)).length()))) {
											bestandsListeModel.setValueAt(String.valueOf(Float.parseFloat((String)bestandsListeModel.getValueAt(getRow(bestandsListe), MengeSpalte)) - Float.parseFloat(insertValue.getText())), getRow(bestandsListe), MengeSpalte);
											Vector neuerArtikelAufEinkaufsListe = new  Vector(bestandsListeModel.getDataVector().elementAt(getRow(bestandsListe)));
											einkaufsListeModel.addRow(neuerArtikelAufEinkaufsListe);
											einkaufsListeModel.setValueAt(String.valueOf(insertValue.getText()), einkaufsListeModel.getRowCount() - 1, MengeSpalte);
											einkaufsListeModel.setValueAt(String.valueOf(Float.parseFloat(insertValue.getText()) * Float.parseFloat(((String)einkaufsListeModel.getValueAt(einkaufsListeModel.getRowCount() - 1, GrundPreis)))), einkaufsListeModel.getRowCount() - 1, EndpreisSpalte);
											changeZwischensumme(Float.parseFloat((String)einkaufsListeModel.getValueAt(einkaufsListeModel.getRowCount()-1, EndpreisSpalte)));
										}
										else {
											
											float Mult = 10f;
											/*
											 * Hier werden bei den nicht-Stück Mengen die einzelnen Umrechnungen zwischen den Einheiten durchgeführt
											 * "Mult" dient hierbei als Umrechnungsfaktor
											 */
											if (((String)bestandsListeModel.getValueAt(getRow(bestandsListe), MengeEinheitSpalte)).contains("Gramm") & ((String)bestandsListeModel.getValueAt(getRow(bestandsListe), GPreisEinheitSpalte)).contains("Kilogramm")) {
												// Umrechnung: Artikelmenge in Gramm --> Preisberechnung in €/kg (Faktor 0,001)
												Mult = 0.001f;
											};
											
											if (((String)bestandsListeModel.getValueAt(getRow(bestandsListe), MengeEinheitSpalte)).contains("Kilogramm") & ((String)bestandsListeModel.getValueAt(getRow(bestandsListe), GPreisEinheitSpalte)).contains("Gramm")) {
												// Umrechnung: Artikelmenge in Kilogramm --> Preisberechnung in €/100 Gramm (Faktor 0,001)
												Mult = 10f;
											};
											
											if (((String)bestandsListeModel.getValueAt(getRow(bestandsListe), MengeEinheitSpalte)).contains("Liter") & ((String)bestandsListeModel.getValueAt(getRow(bestandsListe), GPreisEinheitSpalte)).contains("Milliliter")) {
												// Umrechnung: Artikelmenge in Liter --> Preisberechnung in €/100 Milliliter (Faktor 0,001)
												Mult = 10f;
											};
											
											if (((String)bestandsListeModel.getValueAt(getRow(bestandsListe), MengeEinheitSpalte)).contains("Milliliter") & ((String)bestandsListeModel.getValueAt(getRow(bestandsListe), GPreisEinheitSpalte)).contains("Liter")) {
												// Umrechnung: Artikelmenge in Milliliter --> Preisberechnung in €/100 Milliliter (Faktor 0,001)
												Mult = 0.001f;
											};
											bestandsListeModel.setValueAt(String.valueOf((Float.parseFloat((String) bestandsListeModel.getValueAt(getRow(bestandsListe), MengeSpalte))) - Float.parseFloat(insertValue.getText())), getRow(bestandsListe), MengeSpalte);
											Vector neuerArtikelAufEinkaufsListe = new  Vector(bestandsListeModel.getDataVector().elementAt(getRow(bestandsListe)));
											einkaufsListeModel.addRow(neuerArtikelAufEinkaufsListe);
											einkaufsListeModel.setValueAt(String.valueOf(Float.parseFloat(insertValue.getText())), einkaufsListeModel.getRowCount() - 1, MengeSpalte);
											einkaufsListeModel.setValueAt(String.valueOf(Float.parseFloat(insertValue.getText()) * Mult * (Float.parseFloat((String)einkaufsListeModel.getValueAt(einkaufsListeModel.getRowCount()-1, GrundPreis)))), einkaufsListeModel.getRowCount() - 1, EndpreisSpalte);
											changeZwischensumme(Float.parseFloat((String)einkaufsListeModel.getValueAt(einkaufsListeModel.getRowCount()-1, EndpreisSpalte)));
											
											insertValue.setVisible(false);
											addValueButton.setVisible(false);
											mengeHinweisArea.setVisible(false);
										}
										bestandsListe.clearSelection();
									}
								}
							});			
						}
					}
				}
			}
		);
		
		/**
		 * Dieser MouseListener löscht den Inhalt des Eingabefeldes "insertValue", sobald dieses mit der Maus angeklickt wird. 
		 * Dies dient vorallem dazu die Bedienerfreundlichkeit der Funktion unter dem "addValueButton" zu erhöhen. 
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
		 * Dies dient vorallem dazu die Bedienerfreundlichkeit der Funktion unter dem "searchButton" zu erhöhen. 
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
		 *  Dieser MouseListener löscht den Inhalt des Eingabefeldes "BarGeldField", sobald dieses mit der Maus angeklickt wird. 
		 * Dies dient vorallem dazu die Bedienerfreundlichkeit der Funktion unter dem "WechelgeldBerechnenButton" zu erhöhen. 
		 * Beachte: Dieses Feld mit dem dazugehörigen Button wird erst nach dem Abschließen des Einkaufs sichtbar.
		 *  
		 */
		BarGeldField.addMouseListener(new MouseAdapter() 
		{
	        @Override
	        public void mouseClicked(MouseEvent e)
	        {
	        	BarGeldField.setText("");	
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
				Object name = einkaufsListeModel.getValueAt(getRow(einkaufsListe), NameSpalte);
				searchField.setText("");
				int i = 0;
				while (name != bestandsListeModel.getValueAt(i,NameSpalte)) 
				{
					i++;
		
				}
				if ((String)bestandsListeModel.getValueAt(i, StkPreis) != "n") {
					bestandsListeModel.setValueAt(String.valueOf(Float.parseFloat((String) bestandsListeModel.getValueAt(i,StkZahl)) + 1), i, StkZahl);
					}	
					else {
						bestandsListeModel.setValueAt(String.valueOf(Float.parseFloat((String) einkaufsListeModel.getValueAt(getRow(einkaufsListe),MengeSpalte)) + Float.parseFloat((String) bestandsListeModel.getValueAt(i,MengeSpalte))), i, MengeSpalte);
					}
				
				changeZwischensumme(Float.parseFloat((String)(einkaufsListeModel.getValueAt(getRow(einkaufsListe), EndpreisSpalte))) - 2* Float.parseFloat((String)(einkaufsListeModel.getValueAt(getRow(einkaufsListe), EndpreisSpalte))));
				einkaufsListeModel.removeRow(getRow(einkaufsListe));
				}
			});
		/**
		 * Funktioniert im Kern wie die Funktionalität des "removeButtons". Es wird zusätzlich für jedes Element über die gesamte Einkaufsliste itteriert, um jedes Element zu entfernen.
		 * Jedoch werden nur die Artikel auf der Einkaufsliste wieder der Bestandsliste hinzugefügt.
		 * Zur Vereinfachung wird die Zwischensumme am Ende auf "Zwischensumme: 0€" gesetzt und die Einkaufsliste komplett gelöscht.
		 */
		removeAllButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < (Integer)einkaufsListe.getRowCount(); i++) 
				{
					String name = String.valueOf(einkaufsListeModel.getValueAt(i, NameSpalte));
					int j = 0;
					while (name != bestandsListeModel.getValueAt(j,NameSpalte)) 
					{
						j++;
					}
					if (einkaufsListeModel.getValueAt(i, StkPreis) != "n") {
					bestandsListeModel.setValueAt(String.valueOf(Float.parseFloat((String) bestandsListeModel.getValueAt(j,StkZahl)) + 1), j, StkZahl);
					}	
					else {
					bestandsListeModel.setValueAt(String.valueOf(Float.parseFloat((String) einkaufsListeModel.getValueAt(i,MengeSpalte)) + Float.parseFloat((String) bestandsListeModel.getValueAt(j,MengeSpalte))), j, MengeSpalte);
					}
				}
				einkaufsListeModel.getDataVector().removeAllElements();
				einkaufsListe.repaint();
				zwischensumme.setText("Zwischensumme: 0€");
					zValue = 0f;
					}
				});	
		/**
		 * Hiermit wird der Einkauf abgeschlossen. Die nun übrige Bestandsliste wird als neuer Bestand behandelt. Aus diesem Grund wird die xml Datei, aus der anfänglich die Bestandsliste ausgelesen wurde mit der überarbeiteten Bestandsliste überschrieben.
		 */
		
		einkaufEndgueltigAbschliessenButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				interactionsPanelAbschluss.setVisible(false);
				interactionsPanel.setVisible(true);
				saveBestand(einkaufsListeModel);
				DatenSchreiber DatenSchreiber = new DatenSchreiber(lager);				
				DatenSchreiber.Schreiben();
				
				//Die Funktion, um dann die Bestandsliste wieder zurückzuführen.
			
			//removeAll kopieren.
			}
		}
		);
		

		einkaufAbschliessenButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				interactionsPanel.setVisible(false);	
				add(interactionsPanelAbschluss, BorderLayout.CENTER);
				interactionsPanelAbschluss.setVisible(true);
				AbschlussArea.setText("Endsumme: " + zValue + "\n Wechselgeld: 0€");
			}
		}
		);
		
		WechelgeldBerechnenButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try {
					float moneyInput = Float.parseFloat(BarGeldField.getText());
					ErrorArea.setText("");
					AbschlussArea.setText("Endsumme: " + zValue + "\n Wechselgeld: " + (Float.parseFloat(BarGeldField.getText()) - zValue) + " €");
				}
				catch (NumberFormatException e1)
				{
					ErrorArea.setText("Invalid number");
					ErrorArea.setForeground(Color.red);
				}
			}
		}
		);
		
		/**
		 * Durch Änderungen im Suchfeld "searchField" wird die Suche durchgeführt.
		 * Mittels eines RowFilters wird die Liste nach der Eingabe im "searchField" gefiltert. D.h., alle Artikel werden angezeigt, die den Suchtext enthalten. Ist keine Suche eingegeben, wird alles ungefiltert ausgegeben.
		 */
		searchField.getDocument().addDocumentListener(new DocumentListener()
        {
            public void changedUpdate(DocumentEvent arg0) 
            {
            	String text = searchField.getText();
            	 if (text.length() == 0) {
 		            tr.setRowFilter(null);
 		          } else {
 		            tr.setRowFilter(RowFilter.regexFilter(text, NameSpalte, EANSpalte));
 		          }
            }
            public void insertUpdate(DocumentEvent arg0) 
            {
            	String text = searchField.getText();
            	 if (text.length() == 0) {
 		            tr.setRowFilter(null);
 		          } else {
 		            tr.setRowFilter(RowFilter.regexFilter(text, NameSpalte, EANSpalte));
 		          }
 		  	}
            public void removeUpdate(DocumentEvent arg0) 
            {
            	String text = searchField.getText();
            	 if (text.length() == 0) {
 		            tr.setRowFilter(null);
 		          } else {
 		            tr.setRowFilter(RowFilter.regexFilter(text, NameSpalte, EANSpalte));
 		          }
 		  	}
        });	
	}
		
	//(\w+)\.getSelectedRow\(\)
	// getRow\($1\)
	
	public static void main(String[] args) 
	{
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
			GUIEinkauf gui = new GUIEinkauf();
			gui.setTitle("Einkaufsansicht");
			gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gui.setExtendedState(JFrame.MAXIMIZED_BOTH);  //Fullscreen
			gui.setVisible(true);
			}
		});
	}
}
