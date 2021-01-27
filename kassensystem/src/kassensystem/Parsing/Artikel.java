package Parsing;

import java.io.*;

public class Artikel 
{
		private XMLParser xmlParser;
		private String name;
		private String kategorie;
		private String[] einheiten = {"g", "kg", "l", "ml", "p"};
		private String einheit;
		private String ean;
		private String plu;
		private String gewicht;
		private String anzahl;
		private String preis;
		private String grundpreis;
	  // id?

	  public boolean checkName(String name) 
	  {
	    // name überprüfen
	    return 2 <= name.length() && name.length() <= 32;
	  }

	  public boolean checkKategorie(String kategorie) 
	  {
	    // kategorie überprüfen
	    return !kategorie.matches("[a-zA-Z]+") ? 3 <= kategorie.length() && kategorie.length() <= 32 : false;
	  }

	  public boolean checkPlu(String plu) {

	    // EAN überprüfen
	    if (!ean.matches("[0-9]+")) {
	      return false;
	    }

	    if (plu.length() == 4 || plu.length() == 5) {
	      return true;
	    } else {
	      return false;
	    }
	  }

	  public boolean checkGewicht(String gewicht)
	  {

	    // gewicht überprüfen

	    // regex erkennt floats und integers
	    
	    return !gewicht.matches("[0-9]*,?[0-9]+") || 
	    	!(einheit == "Gramm" & 1 <= Float.parseFloat(gewicht) && Float.parseFloat(gewicht) <= 100000) || 
	    	!(einheit == "Kilo" & 0.1 <= Float.parseFloat(gewicht) && Float.parseFloat(gewicht) <= 100) ||
	    	!(einheit == "Liter" & 0.1 <= Float.parseFloat(gewicht) && Float.parseFloat(gewicht) <= 100) ||
	    	!(einheit == "ml" & 1 <= Float.parseFloat(gewicht) && Float.parseFloat(gewicht) <= 10000);
	  }

	  public boolean checkAnzahl(String anzahl) {
	    
	   if (!anzahl.matches("[0-9]+")) {
	     if (anzahl.equals("n")) {
	       return true;
	     }
	     return false;
	   }
	   
	   return (1 <= Integer.parseInt(anzahl) && Integer.parseInt(anzahl) <= 1000);
	  }
	  
	  public boolean checkPreis(String preis) 
	  {
	    // preis überprüfen
	  
	    return preis.matches("[0-9]*,?[0-9]+") && (0.001 <= Float.parseFloat(preis.replace(",", ".")) && Float.parseFloat(preis.replace(",", ".")) <= 100000);
	  }

	  public boolean checkGrundpreis(String grundpreis) 
	  {
	    
	    return grundpreis.matches("[0-9]*,?[0-9]+") || !(0.001 <= Float.parseFloat(grundpreis.replace(",", ".")) && Float.parseFloat(grundpreis.replace(",", "."))<= 100000);
	  }
	    
	  public boolean checkEinheit(String einheit) {

	    for (int i = 0; i < einheiten.length; i++) {
	      if (einheiten[i].equals(einheit)) {
	        return true;
	      }
	    }
	    return false;
	  }

	  public Artikel(String name, String ean, String kategorie, String einheit, String plu, String gewicht, String anzahl, String preis, String grundpreis) 
	  {	  
		  setName(name);
		  setKategorie(kategorie);
		  setEan(ean);
		  setPlu(plu);
		  setGewicht(gewicht);
		  setAnzahl(anzahl);
		  setPreis(preis);
		  setGrundpreis(grundpreis);
		  setEinheit(einheit);
	  }

	  public Artikel(String xml) 
		{
			xmlParser = new XMLParser(xml);
			setNameFromXML();
			setKategorieFromXML();
			setEanFromXML();
			setPluFromXML();
			setGewichtFromXML();
			setAnzahlFromXML();
			setPreisFromXML();
			setEinheitFromXML();
			setGrundpreisFromXML();
		}

	  public void update (String name, String ean, String kategorie, String einheit, String plu, String gewicht, String anzahl, String preis, String grundpreis)
		{
			setName(name);
			setKategorie(kategorie);
			setEan(ean);
			setPlu(plu);
			setAnzahl(anzahl);
			setGewicht(gewicht);
			setEinheit(einheit);
			setPreis(preis);
			setGrundpreis(grundpreis);
		}
	  /*
	   * GETTER - METHODEN
	   * 
	   * 
	   */

	  public String getName() {
	    return name;
	  }

	  public String getKategorie() {
	    return kategorie;
	  }

	  public String getEinheit() {
	    return einheit;
	  }

	  public String getEan() {
	    return ean;
	  }

	  public String getGewicht() {
	    return gewicht;
	  }

	  public String getAnzahl() {
	    return anzahl;
	  }

	  public String getPreis() {
	    return preis;
	  }

	  public String getPlu() {
	    return plu;
	  }

	  public String getGrundpreis() {
	    return grundpreis;
	  }


	  /*
	   * SETTER - METHODEN AUS GUI/USEREINGABE
	   * 
	   * 
	   */

	  public void setName(String name) 
	  {
	    this.name = checkName(name) ? name : "";
	  }

	  public void setKategorie(String kategorie) 
	  {
	    this.kategorie = checkKategorie(kategorie) ? kategorie : "";
	  }

	  public void setEan(String ean) 
	  {
	    this.ean = new EanVerifizierer(ean).getformatierteEan();
	  }

	  public void setPlu(String plu) 
	  {
	    this.plu = checkPlu(plu) ? plu : "";
	  }

	  public void setGewicht(String gewicht) 
	  {
	    this.gewicht = checkGewicht(gewicht) ? gewicht : "";
	  }

	  public void setAnzahl(String anzahl) 
	  {
	    this.anzahl = checkAnzahl(anzahl) ? anzahl : "";
	  }

	  public void setPreis(String preis) 
	  {
	    if (checkPreis(preis))
	    	this.preis = preis.replace(",", ".");
//	    this.preis = checkPreis(preis.replace(",", ".")) ? preis.replace(",", ".") : "";
	  }

	  public void setGrundpreis(String grundpreis)
	  {
	    if (checkGrundpreis(grundpreis))
	    	this.grundpreis = grundpreis.replace(",", ".");
//	    this.grundpreis = checkGrundpreis(grundpreis.replace(",", ".")) ? grundpreis.replace(",", ".") : "";
	  }

	  public void setEinheit(String einheit) 
	  {
	    this.einheit = checkEinheit(einheit) ? einheit : "";
	  }
	  
	  /*
	   * SETTER - METHODEN AUS XML
	   * 
	   * 
	   */
	  
	  private void setNameFromXML()
		{
			name = xmlParser.getChild("name");
		}
	  
	  private void setKategorieFromXML()
		{
			kategorie = xmlParser.getChild("kategorie");
		}
	  
	  private void setEanFromXML()
		{
			ean = xmlParser.getChild("ean");
		}
	  
	  private void setPluFromXML()
		{
			plu = xmlParser.getChild("plu");
		}
	  
	  private void setGewichtFromXML()
		{
			gewicht = xmlParser.getChild("gewicht");
		}
	  
	  private void setAnzahlFromXML()
		{
			anzahl = xmlParser.getChild("anzahl");
		}
	  
	  private void setPreisFromXML()
		{
			preis = xmlParser.getChild("preis");
		}
		
	  private void setEinheitFromXML()
		{
			einheit = xmlParser.getChild("einheit");
		}
	  
	  private void setGrundpreisFromXML()
	  	{
			grundpreis = xmlParser.getChild("grundpreis");
		}
	  
	  public void print()
	  {
		  System.out.println(name + ", " + ean + ", " + kategorie + ", " + einheit + ", " + plu + ", " + gewicht + ", " + anzahl + ", " + preis + ", " + grundpreis);
	  }
}
