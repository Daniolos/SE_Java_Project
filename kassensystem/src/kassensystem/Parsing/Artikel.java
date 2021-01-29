package Parsing;

import java.io.*;

public class Artikel {
	private XMLParser xmlParser;
	private String name;
	private String ean;
	private String preis;
	private String anzahl;
	private String grundpreis;
	private String preiseinheit; 
	private String gewicht;
	private String[] einheiten = { "kg", "g", "l", "ml", "p" };
	private String einheit;
	private String kategorie;


	public boolean checkPreiseinheit(String preiseinheit) {
		try {
			
			if (preiseinheit.equals("n")) { return true; }
			for (String s : einheiten) {
				if (preiseinheit.contains(s)) {
					return true;
				}
			}
			return false;
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.err.println("Ungültige Eingabe bei Preiseinheit.");
			return false;
		}
	}

	public boolean checkName(String name) {
		return 2 <= name.length() && name.length() <= 32;
	}

	public boolean checkKategorie(String kategorie) {
		return kategorie.matches("[a-zäöüßA-ZÄÖÜ]+") ? (3 <= kategorie.length() && kategorie.length() <= 32) : false;
	}

	public boolean checkEan(String ean) {

		if (!ean.matches("[0-9]+")) {
			return false;
		}

		if (ean.length() == 8 || ean.length() == 13) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkGewicht(String gewicht) {

		if (!gewicht.matches("[0-9]*(,|.)?[0-9]+")) {
			return false;
		}

		try {
			if (((einheit == "g") && (Float.parseFloat(gewicht) >= 1) && (Float.parseFloat(gewicht) <= 100000))
				|| ((einheit == "kg") && (Float.parseFloat(gewicht) >= 0.1) && (Float.parseFloat(gewicht) <= 100))
				|| ((einheit == "l") && (Float.parseFloat(gewicht) >= 0.1) && (Float.parseFloat(gewicht) <= 100))
				|| ((einheit == "ml") && (Float.parseFloat(gewicht) >= 1) && (Float.parseFloat(gewicht) <= 10000))) {
				
				return true;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.err.println("Ungültige Eingabe bei Gewicht.");
			return false;
		}
		return true;
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

	public boolean checkPreis(String preis) {

		if (!preis.matches("[0-9]*(,|.)?[0-9]+")) {
			return false;
		}
		
		try {
			if (0.001 <= Float.parseFloat(preis.replace(",", ".")) && Float.parseFloat(preis.replace(",", ".")) <= 100000) {
				return true;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.err.println("Ungültige Eingabe bei Preis.");
			return false;
		}
		return true;
	}

	public boolean checkGrundpreis(String grundpreis) {

		if (!grundpreis.matches("[0-9]*(,|.)?[0-9]+")) {
			return false;
		}
		
		try {
			if (0.001 <= Float.parseFloat(grundpreis.replace(",", ".")) && Float.parseFloat(grundpreis.replace(",", ".")) <= 100000) {
				return true;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.err.println("Ungültige Eingabe bei Grundpreis.");
			return false;
		}
		return true;
	}

	public boolean checkEinheit(String einheit) {

		for (int i = 0; i < einheiten.length; i++) {
			if (einheiten[i].equals(einheit)) {
				return true;
			}
		}
		return false;
	}

	public Artikel(String name, String ean, String kategorie, String einheit, String preiseinheit, String gewicht, String anzahl,
			String preis, String grundpreis) {
		setName(name);
		setKategorie(kategorie);
		setEan(ean);
		setPreiseinheit(preiseinheit);
		setGewicht(gewicht);
		setAnzahl(anzahl);
		setPreis(preis);
		setGrundpreis(grundpreis);
		setEinheit(einheit);
	}

	public Artikel(String xml) {
		xmlParser = new XMLParser(xml);
		setNameFromXML();
		setKategorieFromXML();
		setEanFromXML();
		setPreiseinheitFromXML();
		setGewichtFromXML();
		setAnzahlFromXML();
		setPreisFromXML();
		setEinheitFromXML();
		setGrundpreisFromXML();
	}

	public void update(String name, String ean, String kategorie, String einheit, String preiseinheit, String gewicht,
			String anzahl, String preis, String grundpreis) {
		setName(name);
		setKategorie(kategorie);
		setEan(ean);
		setPreiseinheit(preiseinheit);
		setAnzahl(anzahl);
		setGewicht(gewicht);
		setEinheit(einheit);
		setPreis(preis);
		setGrundpreis(grundpreis);
	}
	
	public String[] toStringArray() {
		String[] arr = new String[9];
		arr[0] = this.getName();
		arr[1] = this.getEan();
		arr[2] = this.getPreis();
		arr[3] = this.getAnzahl();
		arr[4] = this.getGrundpreis();
		arr[5] = this.getPreiseinheit();
		arr[6] = this.getGewicht();
		arr[7] = this.getEinheit();
		arr[8] = this.getKategorie();		
		return arr;

	}
	/*
	 * GETTER - METHODEN
	 * 
	 * 
	 */

	public String getPreiseinheit() {
		return preiseinheit;
	}
	
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

	public String getGrundpreis() {
		return grundpreis;
	}

	/*
	 * SETTER - METHODEN AUS GUI/USEREINGABE
	 * 
	 * 
	 */

	public void setPreiseinheit(String preiseinheit) {
		this.preiseinheit = checkPreiseinheit(preiseinheit) ? preiseinheit : "";
	}
	
	public void setName(String name) {
		this.name = checkName(name) ? name : "";
	}

	//TODO Mit KategorieListe integrieren. Überprüfen unnötig?
	public void setKategorie(String kategorie) {
		this.kategorie = checkKategorie(kategorie) ? kategorie : "";
	}

	public void setEan(String ean) {
		this.ean = new EanVerifizierer(ean).getformatierteEan();
	}

	public void setGewicht(String gewicht) {
		this.gewicht = checkGewicht(gewicht) ? gewicht.replace(",", ".") : "";
	}

	public void setAnzahl(String anzahl) {
		this.anzahl = checkAnzahl(anzahl) ? anzahl : "";
	}

	public void setPreis(String preis) {
		if (checkPreis(preis))
			this.preis = preis.replace(",", ".");
//	    this.preis = checkPreis(preis.replace(",", ".")) ? preis.replace(",", ".") : "";
	}

	public void setGrundpreis(String grundpreis) {
		if (checkGrundpreis(grundpreis))
			this.grundpreis = grundpreis.replace(",", ".");
//	    this.grundpreis = checkGrundpreis(grundpreis.replace(",", ".")) ? grundpreis.replace(",", ".") : "";
	}

	public void setEinheit(String einheit) {
		this.einheit = checkEinheit(einheit) ? einheit : "";
	}

	/*
	 * SETTER - METHODEN AUS XML
	 * 
	 * 
	 */

	private void setNameFromXML() {
		name = xmlParser.getChild("name");
	}

	private void setKategorieFromXML() {
		kategorie = xmlParser.getChild("kategorie");
	}

	private void setEanFromXML() {
		ean = xmlParser.getChild("ean");
	}

	private void setPreiseinheitFromXML() {
		preiseinheit = xmlParser.getChild("preiseinheit");
	}

	private void setGewichtFromXML() {
		gewicht = xmlParser.getChild("gewicht");
	}

	private void setAnzahlFromXML() {
		anzahl = xmlParser.getChild("anzahl");
	}

	private void setPreisFromXML() {
		preis = xmlParser.getChild("preis");
	}

	private void setEinheitFromXML() {
		einheit = xmlParser.getChild("einheit");
	}

	private void setGrundpreisFromXML() {
		grundpreis = xmlParser.getChild("grundpreis");
	}

	public void print() {
		System.out.println(name + ", " + ean + ", " + kategorie + ", " + einheit + ", " + preiseinheit + ", " + gewicht + ", "
				+ anzahl + ", " + preis + ", " + grundpreis);
	}
}