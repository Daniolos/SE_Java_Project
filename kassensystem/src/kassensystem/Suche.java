package kassensystem;

import java.awt.*;
import java.util.Vector;
import java.awt.event.*;
import java.lang.Math;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;


public class Suche extends JFrame
{
	//Namen der Tabellenspalten
	String[] title = {"Artikelname", "EAN", "Preis", "Stückzahl"};
  	
	  //Tabellendaten
	 Object[][] playerdata = {
			  	{"Apfel", "0192992", "19,92", 2},
				{"Tomate", "3432342", "2,34", 3},
				{"Brot", "2345323","213,32", 7},
				};
	  
	Object[][] suchdata = {};
	  
	  //Erstellung eines Model und einer Tabelle
	DefaultTableModel suchModell = new DefaultTableModel(suchdata, title);
	JTable SuchListe = new JTable(suchModell);
	DefaultTableModel NormalModell = new DefaultTableModel(playerdata, title);
	JTable NormalListe = new JTable(NormalModell);
	

	
	final JTextField field = new JTextField("Suchtext bitte hier eingeben");
	
	public void fillSuchtable() {
	  	  
	  	for (int i = 0; i <= NormalListe.getRowCount()-1; i++) {
	  		System.out.println(i);
	  		Vector neueZeile = new  Vector(NormalModell.getDataVector().elementAt(i));
	  		suchModell.addRow(neueZeile);
	  		SuchListe.repaint();
	  	  }
	    }
	    
	    
	    private void deleteSuchtable() {
	  	  
	  	  DefaultTableModel model;
	  	  JTable suchtable;
	  	  suchModell.getDataVector().removeAllElements();
	  	  SuchListe.repaint();
	    }
	    
	    private void FilterElement(String Input) {
	    	for (int i = 0; i <= SuchListe.getRowCount(); i++) {
	    		if ( !(((String)SuchListe.getValueAt(i,0)).contains(Input)) && !(((String)SuchListe.getValueAt(i,1)).contains(Input))) {
	    			suchModell.removeRow(i);
	    			i--;
	    		}
	    	}
	    }
	  
	    private Suche() {
	    setLayout(new FlowLayout());
	    	 
	  	  
		SuchListe.setPreferredScrollableViewportSize(new Dimension(500,800));
		SuchListe.setFillsViewportHeight(true);
		JButton Suchen = new JButton("Suchen");
		JScrollPane scrollPane = new JScrollPane(SuchListe);
		this.getContentPane().add(scrollPane);
	    this.getContentPane().add(field); 
	    this.getContentPane().add(Suchen);
	    fillSuchtable();
	    Suchen.addActionListener(new ActionListener() {
	    	  public void actionPerformed(ActionEvent e) {
	    		  deleteSuchtable();	
	    		  fillSuchtable();
	    		  FilterElement(field.getText());
	    		  }

	    		  });
	    field.addMouseListener(new MouseAdapter() 
		{
	        @Override
	        public void mouseClicked(MouseEvent e)
	        {
	            field.setText("");	
	        }
		});
	    

	
}
	  public static void main(String[] args) 
		{
       Suche gui = new Suche();
       gui.setTitle("Suche");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH);  //Fullscreen
		gui.setVisible(true);
  }}