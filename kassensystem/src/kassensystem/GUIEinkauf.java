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
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
	private JTable bestandsListe;
	private JTable einkaufsListe;
	private JTextField insertValue;
	private float zValue = 0f;
	/*XMLDecoder xd = new XMLDecoder(is);
    model = (DefaultTableModel)xd.readObject();
    table.setModel(model);                                   so oder so ähnlich dann aus xml
    */

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();
	
	/*
	 * Diese Funktion wird verwendet, um die Zwischensumme zu aktualisieren. Je nach Input (positiv/negativ) steigt oder sinkt die Zwischensumme.
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
	

	
	
	String[] columnNames = 
		{"Artikelname", "EAN", "Preis", "Preiseinheit", "Menge", "Mengeneinheit"};
		
	Object[][] dataBestand = 
	{												// Datensätze müssen später noch aus txt oder ähnlichen dateien geholt werden
		{"Apfel", "0192992", 19.92f, "€", 200, "Stück"},
		{"Tomate", "3432342", 2.34f, "€", 3, "Stück"},
		{"Brot", "2345323",213.32f, "€", 7, "Stück"},
		{"Rinderfilet", "6787383", 2498.33f, "€/Kilogramm" , 20000 , "Gramm"},
	};
	/*
	 * Zur Übersichtlichkeit und Wartungsfreundlichkeit:
	 */
	int NameSpalte = 0;
	int EANSpalte = 1;
	int PreisSpalte = 2;
	int PreisEinheitSpalte = 3;
	int MengeSpalte = 4;
	int MengeEinheitSpalte = 5;
	int EndpreisSpalte = 6; 			//immer die letzte Spalte, nur bei einkausListe(-Model) verwendbar!!!!
	
	
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


	
	
	// Einkaufsliste zur graphischen Oberfl夨e hinzufügen

	
	
										// Bestandsliste zur graphischen Oberfl夨e hinzuf??
	
	
	
	
	einkaufsListeModel.addColumn("Endpreis");
	einkaufsListe.getColumnModel().getColumn(EANSpalte).setMinWidth(0);	
	einkaufsListe.getColumnModel().getColumn(EANSpalte).setMaxWidth(0); //	--> um EAN in der Einkaufsliste zu verbergen
	einkaufsListe.getColumnModel().getColumn(PreisSpalte).setMinWidth(0);	
	einkaufsListe.getColumnModel().getColumn(PreisSpalte).setMaxWidth(0);
	einkaufsListe.getColumnModel().getColumn(PreisEinheitSpalte).setMinWidth(0);	
	einkaufsListe.getColumnModel().getColumn(PreisEinheitSpalte).setMaxWidth(0);
	
	
	/*
	 * Hier wird der "addButton" mit seinen Funktionen definiert. Dieser überträgt bei Benutzung die ausgewählte Zeile der Bestandsliste in die Einkaufsliste.
	 * Anfangs wird außerdem direkt abgefangen, dass der Benutzer nicht mehr Artikel übertragen kann, als vorhanden sind.
	 * 
	 * Handelt es sich um ein Produkt dessen Menge in "Stück" angegeben wird, wird pro Mausklick je ein Produkt übernommen und einzeln in der Einkaufsliste aufgeführt.
	 * 
	 * Handelt es sich um ein Produkt dessen Menge nicht in Stück angegeben ist, so wird ein extra Textfeld mit Button eingeblendet ("insertValue" und "addValueButton" zum bestätigen).
	 * Mit diesen kann dann eine konkrete Menge durch den Nutzer angegeben werden.
	 * 
	 * 
	 */
	addButton.addActionListener(new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			insertValue.setVisible(false);
			addValueButton.setVisible(false);	
			if ((bestandsListe.getValueAt(bestandsListe.getSelectedRow(), 5) == "Stück") & ((Integer)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeSpalte) > 0)) 
			{
				bestandsListeModel.setValueAt(((Integer) bestandsListeModel.getValueAt(bestandsListe.getSelectedRow(), MengeSpalte)) - 1, bestandsListe.getSelectedRow(), MengeSpalte);
				Vector neuerArtikelAufEinkaufsListe = new  Vector(bestandsListeModel.getDataVector().elementAt(bestandsListe.getSelectedRow()));
				einkaufsListeModel.addRow(neuerArtikelAufEinkaufsListe);
				einkaufsListeModel.setValueAt(1, einkaufsListeModel.getRowCount() - 1, MengeSpalte);
				einkaufsListeModel.setValueAt(bestandsListe.getValueAt((Integer)bestandsListe.getSelectedRow(), PreisSpalte), einkaufsListeModel.getRowCount() - 1, EndpreisSpalte);
				changeZwischensumme((Float)(bestandsListe.getValueAt(bestandsListe.getSelectedRow(), PreisSpalte)));}
				
			else
				{
				if ((Integer)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeSpalte) > 0) {
					insertValue.setVisible(true);
					addValueButton.setVisible(true);
					addValueButton.addActionListener(new ActionListener () 
					{
						public void actionPerformed(ActionEvent e) {
							if (((Integer)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeSpalte) >= Integer.parseInt(insertValue.getText())) & Integer.parseInt(insertValue.getText()) > 0 ) {
								if (bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeEinheitSpalte) == ((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), PreisEinheitSpalte)).substring(2,((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), PreisEinheitSpalte)).length())) {
									bestandsListeModel.setValueAt(((Integer) bestandsListeModel.getValueAt(bestandsListe.getSelectedRow(), MengeSpalte)) - Integer.parseInt(insertValue.getText()), bestandsListe.getSelectedRow(), MengeSpalte);
									Vector neuerArtikelAufEinkaufsListe = new  Vector(bestandsListeModel.getDataVector().elementAt(bestandsListe.getSelectedRow()));
									einkaufsListeModel.addRow(neuerArtikelAufEinkaufsListe);
									einkaufsListeModel.setValueAt(Integer.parseInt(insertValue.getText()), einkaufsListeModel.getRowCount() - 1, MengeSpalte);
									einkaufsListeModel.setValueAt(Integer.parseInt(insertValue.getText()) * ((Float)einkaufsListeModel.getValueAt(einkaufsListeModel.getRowCount() - 1, PreisSpalte)), einkaufsListeModel.getRowCount() - 1, EndpreisSpalte);
						
								}
								else {
									
									/*
									 * Hier werden bei den nicht-Stück Mengen die einzelnen Umrechnungen zwischen den Einheiten durchgeführt
									 */
									if (((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeEinheitSpalte)).contains("Gramm") & ((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), PreisEinheitSpalte)).contains("Kilogramm")) {
										System.out.println("1");
									};
									
									if (((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeEinheitSpalte)).contains("Kilogramm") & ((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), PreisEinheitSpalte)).contains("Gramm")) {
										System.out.println("2");
									};
									
									if (((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeEinheitSpalte)).contains("Liter") & ((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), PreisEinheitSpalte)).contains("Milliliter")) {
										System.out.println("3");
									};
									
									if (((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), MengeEinheitSpalte)).contains("Milliliter") & ((String)bestandsListe.getValueAt(bestandsListe.getSelectedRow(), PreisEinheitSpalte)).contains("Liter")) {
										System.out.println("4");
									};
									
									
								}
							}
						}
					});
				}
			}
		}
	});
	
	/*
	 * Diese Funktion löscht den Inhalt des Eingabefeldes "insertValue", sobald dieses mit der Maus angeklickt wird. 
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
	/*
	 * Diese Funktion löscht einzelne Elemente aus der Einkaufsliste. Es wird immer das Element gelöscht das vom Nutzer ausgewählt wurde.
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
			bestandsListeModel.setValueAt(((Integer) bestandsListeModel.getValueAt(i,MengeSpalte)) + 1, i, MengeSpalte);
			
			changeZwischensumme((Float) (einkaufsListe.getValueAt(einkaufsListe.getSelectedRow(), PreisSpalte)) - 2* (Float) (einkaufsListe.getValueAt(einkaufsListe.getSelectedRow(), PreisSpalte)));
			einkaufsListeModel.removeRow(einkaufsListe.getSelectedRow());
			}
		});
	/*
	 * 
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
				bestandsListeModel.setValueAt(((Integer) bestandsListeModel.getValueAt(j,MengeSpalte)) + 1, j, MengeSpalte);		
			}
			einkaufsListeModel.getDataVector().removeAllElements();
			einkaufsListe.repaint();
			zwischensumme.setText("Zwischensumme: 0€");
				zValue = 0f;
				}
			});		
}	
	
	public static void main(String[] args) 
	{
		GUIEinkauf gui = new GUIEinkauf();
		gui.setTitle("Einkaufsansicht");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH);  //Fullscreen
		gui.setVisible(true);
	}
}