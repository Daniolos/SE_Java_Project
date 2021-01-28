package kassensystem;

import java.awt.*;
import java.util.Vector;
import java.awt.event.*;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
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

public class GUITest extends JFrame
	{
	// https://www.javacodeexamples.com/convert-array-to-vector-in-java-example/3281 --> Array zu Vector
	
	private JLabel bestandsListeLabel;
	private JLabel einkaufsListeLabel;
	private JButton addButton;
	private JButton removeButton;
	private JButton removeAllButton;
	private JTable bestandsListe;
	private JTable einkaufsListe;
	
	private GUITest() {
		setLayout(new FlowLayout());
		
		String[] columnNames = 
			{"Artikelname", "EAN","Stückpreis", "Stückzahl", "Grundpreis", "Grundpreiseinheit", "Menge", "Mengeneinheit","Kategorie"};
			
		Object[][] dataBestand = 
		{												// Datensätze müssen später noch aus txt oder ähnlichen dateien geholt werden
			{"Apfel", "0192992", "19.92", "200","n","n","n", "Stück","Obst"},
			{"Tomate", "3432342", "2.34", "3","n","n","n", "Stück","Gemüse"},
			{"Brot", "2345323","213.32", "7","n","n","n", "Stück","Backwaren"},
			{"Rinderfilet", "6787383","n","n", 2498.33f, "€/Kilogramm" , 20000 , "Gramm","Fleisch"},
		};
		Object[][] dataEinkauf = {};
			
		DefaultTableModel bestandsListeModel = new DefaultTableModel(dataBestand, columnNames);	// neue Objekte erzeugen
		bestandsListe = new JTable(bestandsListeModel);
		DefaultTableModel einkaufsListeModel = new DefaultTableModel(dataEinkauf, columnNames);
		einkaufsListe = new JTable(einkaufsListeModel);
		einkaufsListeLabel = new JLabel("Einkaufsliste");
		bestandsListeLabel = new JLabel("Bestandsliste");
		addButton = new JButton("HinzufÃ¼gen");
		removeButton = new JButton("AusgewÃ¤hltes Element entfernen");
		removeAllButton = new JButton("Alles entfernen");
		
		this.getContentPane().add(einkaufsListeLabel);											// Einkaufsliste zur graphischen Oberfläche hinzufügen
		einkaufsListe.setPreferredScrollableViewportSize(new Dimension(500,200));
		einkaufsListe.setFillsViewportHeight(true);
		JScrollPane scrollPane2 = new JScrollPane(einkaufsListe);
		add(scrollPane2);
		
		this.getContentPane().add(bestandsListeLabel);											// Bestandsliste zur graphischen Oberfläche hinzufügen
		bestandsListe.setPreferredScrollableViewportSize(new Dimension(500,200));
		bestandsListe.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(bestandsListe);
		add(scrollPane);
		
		this.getContentPane().add(addButton);
		this.getContentPane().add(removeButton);
		this.getContentPane().add(removeAllButton);
		/*addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bestandsListeModel.setValueAt(((Integer) bestandsListeModel.getValueAt(bestandsListe.getSelectedRow(), 3)) - 1, bestandsListe.getSelectedRow(), 3);
				Vector neuerArtikelAufEinkaufsListe = new  Vector(bestandsListeModel.getDataVector().elementAt(bestandsListe.getSelectedRow()));
				einkaufsListeModel.addRow(neuerArtikelAufEinkaufsListe);
				einkaufsListeModel.setValueAt(1, einkaufsListeModel.getRowCount() - 1, 3);
			}
		});
		
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object name = einkaufsListeModel.getValueAt(einkaufsListe.getSelectedRow(), 0);
				int i = 0;
				while (name != bestandsListeModel.getValueAt(i,0)) {
					i++;
				}
				bestandsListeModel.setValueAt(((Integer) bestandsListeModel.getValueAt(i,3)) + 1, i, 3);
				einkaufsListeModel.removeRow(einkaufsListe.getSelectedRow());
				}
			});
		
		removeAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < (Integer)einkaufsListe.getRowCount(); i++) {
					Object name = einkaufsListeModel.getValueAt(i, 0);
					int j = 0;
					while (name != bestandsListeModel.getValueAt(j,0)) {
						j++;
					}
					bestandsListeModel.setValueAt(((Integer) bestandsListeModel.getValueAt(j,3)) + 1, j, 3);		
				}
				einkaufsListeModel.getDataVector().removeAllElements();
				einkaufsListe.repaint();
				}
			});		*/
}	
	
	public static void main(String[] args) {
		GUITest gui = new GUITest();
		gui.setTitle("Einkaufsansicht");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH);  //Fullscreen
		gui.setVisible(true);
	}
}