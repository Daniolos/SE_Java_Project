package Parsing;

import java.io.*;

public class Artikel {
	private XMLParser xmlParser;
	private String name;
	private String kategorie;
	String[] einheiten = { "g", "kg", "l", "ml", "p" };
	private String einheit;
	private String ean;
	private String plu;
	private String gewicht;
	private String anzahl;
	private String preis;
	private String grundpreis;
//TODO fix assignment of Einheit
//TODO kategorieListe as parameter of artikel?

	public boolean checkName(String name) {
		// name überprüfen
		return name.length() >= 2 && name.length() <= 32;
	}

	public boolean checkKategorie(String kategorie) {
		// kategorie überprüfen
		return kategorie.matches("[a-zäöüßA-ZÄÖÜ]+") ? (3 <= kategorie.length() && kategorie.length() <= 32) : false;
	}

	public boolean checkEan(String ean) {

		// EAN überprüfen
		if (!ean.matches("[0-9]+")) {
			return false;
		}

		if (ean.length() == 8 || ean.length() == 13) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkPlu(String plu) {

		if (!plu.matches("[0-9]+")) {
			return false;
		}

		if (plu.length() == 4 || plu.length() == 5) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkGewicht(String gewicht) {

		// regex erkennt floats und integers

		if (!(gewicht.matches("[0-9]*(,|.)?[0-9]+"))) {
			return false;
		}

		// gewicht überprüfen
		try {

			if (((einheit == "Gramm") && (Float.parseFloat(gewicht) >= 1) && (Float.parseFloat(gewicht) <= 100000))
					|| ((einheit == "Kilo") && (Float.parseFloat(gewicht) >= 0.1) && (Float.parseFloat(gewicht) <= 100))
					|| ((einheit == "Liter") && (Float.parseFloat(gewicht) >= 0.1)
							&& (Float.parseFloat(gewicht) <= 100))
					|| ((einheit == "ml") && (Float.parseFloat(gewicht) >= 1)
							&& (Float.parseFloat(gewicht) <= 10000))) {

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
		// preis überprüfen

		if (!preis.matches("[0-9]*(,|.)?[0-9]+")) {
			return false;
		}
		try {
			if (0.001 <= Float.parseFloat(preis.replace(",", "."))
					&& Float.parseFloat(preis.replace(",", ".")) <= 100000) {
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

		return grundpreis.matches("[0-9]*,?[0-9]+") || !(0.001 <= Float.parseFloat(grundpreis.replace(",", "."))
				&& Float.parseFloat(grundpreis.replace(",", ".")) <= 100000);
	}

	public boolean checkEinheit(String einheit) {

		for (int i = 0; i < einheiten.length; i++) {
			if (einheiten[i].equals(einheit)) {
				return true;
			}
		}
		return false;
	}

	// GUI Constructor

	public Artikel(String name, String ean, String kategorie, String einheit, String plu, String gewicht, String anzahl,
			String preis, String grundpreis) {
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

	// XML Constructor
	public Artikel(String xml) {
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
	
	public String[] toStringArray() {
		String[] arr = new String[9];
		arr[0] = this.getName();
		arr[1] = this.getEan();
		arr[2] = this.getKategorie();
		arr[3] = this.getEinheit();
		arr[4] = this.getPlu();
		arr[5] = this.getGewicht();
		arr[6] = this.getAnzahl();
		arr[7] = this.getPreis();
		arr[8] = this.getGrundpreis();
		return arr;
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

	public void setName(String name) {
		if (checkName(name))
			this.name = name;
	}
//TODO irgendwie mit KategorieListe integrieren
	public void setKategorie(String kategorie) {
		if (checkKategorie(kategorie))
			this.kategorie = kategorie;
	}

	public void setEan(String ean) {
		if (checkEan(ean))
			this.ean = ean;
	}

	public void setPlu(String plu) {
		if (checkPlu(plu)) {
			this.plu = plu;
		}
	}

	public void setGewicht(String gewicht) {
		this.gewicht = checkGewicht(gewicht) ? gewicht.replace(",", ".") : "";
	}

	public void setAnzahl(String anzahl) {
		this.anzahl = checkAnzahl(anzahl) ? anzahl : "0";
	}

	public void setPreis(String preis) {
		if (checkPreis(preis))
			this.preis = preis.replace(",", ".");
	}

	public void setGrundpreis(String grundpreis) {
		if (checkGrundpreis(grundpreis))
			this.grundpreis = grundpreis.replace(",", ".");
	}

	public void setEinheit(String einheit) {
		//if (checkEinheit(einheit))
			//this.einheit = einheit;
		this.einheit = (checkEinheit(einheit) ? einheit : "");
	}

	/*
	 * SETTER - METHODEN AUS XML
	 * 
	 * 
	 */
	private void setNameFromXML() {
		this.name = xmlParser.getChild("name");
		return;
	}

	private void setKategorieFromXML() {
		kategorie = xmlParser.getChild("kategorie");
	}

	private void setEanFromXML() {
		ean = xmlParser.getChild("ean");
	}

	private void setPluFromXML() {
		plu = xmlParser.getChild("plu");
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
		System.out.println(name + ", " + ean + ", " + kategorie + ", " + einheit + ", " + plu + ", " + gewicht + ", "
				+ anzahl + ", " + preis + ", " + grundpreis);
	}
}