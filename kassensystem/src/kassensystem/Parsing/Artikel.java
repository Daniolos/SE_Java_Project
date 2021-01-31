package Parsing;

import java.io.*;

/**
 * 
 * @author Felix Schulz, Philomena Moek
 * 
 */

public class Artikel {
	private XMLParser xmlParser;
	private String name;
	private String ean;
	private String preis;
	private String anzahl;
	private String grundpreis;
	private String peinheit; 
	private String gewicht;
	private String[] einheiten = { "kg", "g", "l", "ml", "p" };
	private String einheit;
	private String kategorie;

/**
 * 
 * @param	String	Grundpeinheit des Artikels
 * @return	Boolean	Ist der übergebene String eine zulässige Grundpeinheit?
 */
	public boolean checkpeinheit(String peinheit) {
		try {
			
			if (peinheit.equals("n")) { return true; }
			for (String s : einheiten) {
				if (peinheit.contains(s)) {
					return true;
				}
			}
			return false;
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.err.println("Ungültige Eingabe bei peinheit.");
			return false;
		}
	}
	/**
	 * 
	 * @param	String	Name des Artikels
	 * @return	Boolean	Ist der übergebene String ein zulässiger Name?
	 */

	public boolean checkName(String name) {
		return 2 <= name.length() && name.length() <= 32;
	}
	

	/**
	 * 
	 * @param	String	Gewicht des Artikels
	 * @return	Boolean	Ist der übergebene String ein zulässiges Gewicht?
	 */
	public boolean checkGewicht(String gewicht) {

		if (!gewicht.matches("[0-9]*(,|.)?[0-9]+")) {
			if (gewicht.equals("n")) {
				return true;
			}
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

	/**
	 * 
	 * @param	String	Anzahl des Artikels (Wie oft ist er im Lager?)
	 * @return	Boolean	Ist der übergebene String eine zulässige Artikelanzahl?
	 */
	public boolean checkAnzahl(String anzahl) {

		try
		{
		if (!anzahl.matches("[0-9]*(,|.)?[0-9]+")) 
		{
			if (anzahl.equals("n")) 
			{
				return true;
			}
			return false;
		
		}
		
		Boolean check = (1 <= Float.parseFloat(anzahl) && Float.parseFloat(anzahl) <= 1000);
		return check;
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			return true;
		}		
	}

	/**
	 * 
	 * @param	String	Stückpreis des Artikels
	 * @return	Boolean	Ist der übergebene String ein zulässiger Stückpreis?
	 */
	public boolean checkPreis(String preis) {

		if (!preis.matches("[0-9]*(,|.)?[0-9]+")) 
		{
			if (preis.equals("n")) 
			{
				return true;
			}
				return false;
		}
		
		try {
			if (0.001 <= Float.parseFloat(preis.replace(",", ".")) && Float.parseFloat(preis.replace(",", ".")) <= 100000) {
				return true;
			}
		} 
		catch (NumberFormatException e) 
		{
			e.printStackTrace();
			System.err.println("Ungültige Eingabe bei Preis.");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param	String	Grundpreis des Artikels
	 * @return	Boolean	Ist der übergebene String ein zulässiger Grundpreis?
	 */
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

	/**
	 * 
	 * @param	String	Einheit in der die Menge des Artikels angegeben wird
	 * @return	Boolean	Ist der übergebene String eine zulässige Einheit?
	 */
	public boolean checkEinheit(String einheit) {

		for (int i = 0; i < einheiten.length; i++) {
			if (einheiten[i].equals(einheit)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Konstruktor für Artikel. Übernimmt Strings aller Artikeleigenschaften 
	 * (neun Strings) und weist sie einem Artikel zu. 
	 * @param	String	Name des Artikels
	 * @param	String	EAN oder PLU
	 * @param	String	Kategorie
	 * @param	String	Einheit
	 * @param	String	Grundpeinheit
	 * @param	String	Gewicht
	 * @param	String	Anzahl (Wie oft ist der Artikel im Lager?)
	 * @param	String	Preis
	 * @param	String	Grundpreis
	 */
	public Artikel(String name, String ean, String kategorie, String einheit, String peinheit, String gewicht, String anzahl,
			String preis, String grundpreis) {
		setName(name);
		setKategorie(kategorie);
		setEan(ean);
		setpeinheit(peinheit);
		setGewicht(gewicht);
		setAnzahl(anzahl);
		setPreis(preis);
		setGrundpreis(grundpreis);
		setEinheit(einheit);
	}

	/**
	 * Konstruktor für Artikel. Übernimmt einen String aller Artikeleigenschaften 
	 * (neun Strings) in XML-Form und weist sie einem Artikel zu. 
	 * @param	String	Der String zwischen zwei <article> Tags aus einer XML-Datei
	 */
	public Artikel(String xml) {
		xmlParser = new XMLParser(xml);
		setNameFromXML();
		setKategorieFromXML();
		setEanFromXML();
		setpeinheitFromXML();
		setGewichtFromXML();
		setAnzahlFromXML();
		setPreisFromXML();
		setEinheitFromXML();
		setGrundpreisFromXML();
	}
	/**
	 * Funktion, die alle Artikeleigenschaften neu zuweist, nachdem diese geändert wurden
	 * @param	String	Name des Artikels
	 * @param	String	EAN oder PLU
	 * @param	String	Kategorie
	 * @param	String	Einheit
	 * @param	String	Grundpeinheit
	 * @param	String	Gewicht
	 * @param	String	Anzahl (Wie oft ist der Artikel im Lager?)
	 * @param	String	Preis
	 * @param	String	Grundpreis
	 */
	public void update(String name, String ean, String kategorie, String einheit, String peinheit, String gewicht,
			String anzahl, String preis, String grundpreis) {
		setName(name);
		setKategorie(kategorie);
		setEan(ean);
		setpeinheit(peinheit);
		setAnzahl(anzahl);
		setGewicht(gewicht);
		setEinheit(einheit);
		setPreis(preis);
		setGrundpreis(grundpreis);
	}
	
	/**
	 * Die Funktion wandelt einen Artikel in ein String-Array um.
	 * @return	String[]	Array, dass die Artikeleigenschaften in folgender Reihenfolge enthält:
	 * 						1. Name des Artikels
	 * 						2. EAN/ PLU
	 * 						3. Stückpreis
	 * 						4. Anzahl (Wie oft ist der Artikel im Lager?)
	 * 						5. Grundpreis
	 * 						6. Grundpeinheit (€/kg, €/100g,...)
	 * 						7. Gewicht
	 * 						8. Einheit (kg, g, l, ml, p)
	 * 						9. Kategorie
	 */
	public String[] toStringArray() {
		String[] arr = new String[9];
		arr[0] = this.getName();
		arr[1] = this.getEan();
		arr[2] = this.getPreis();
		arr[3] = this.getAnzahl();
		arr[4] = this.getGrundpreis();
		arr[5] = this.getpeinheit();
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

	/**
	 * Getter-Methode für die Grundpeinheit
	 * @return	String	Grundpeinheit
	 */
	public String getpeinheit() {
		return peinheit;
	}
	
	/**
	 * Getter-Methode für den Namen dieses Artikels
	 * @return	String	Artikelnamen
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter-Methode für die Kategorie dieses Artikels
	 * @return	String	Kategorie
	 */
	public String getKategorie() {
		return kategorie;
	}

	/**
	 * Getter-Methode für die Mengeneinheit dieses Artikels
	 * @return	String	Mengeneinheit
	 */
	public String getEinheit() {
		return einheit;
	}
	
	/**
	 * Getter-Methode für die EAN dieses Artikels
	 * @return	String	EAN
	 */
	public String getEan() {
		return ean;
	}
	
	/**
	 * Getter-Methode für das Gewicht dieses Artikels
	 * @return	String	Gewicht
	 */
	public String getGewicht() {
		return gewicht;
	}

	/**
	 * Getter-Methode für die im Lager vorhandene Anzhal dieses Artikels
	 * @return	String	Anzahl
	 */
	public String getAnzahl() {
		return anzahl;
	}

	/**
	 * Getter-Methode für den Stückpreis dieses Artikels
	 * @return	String	Stückpreis
	 */
	public String getPreis() {
		return preis;
	}

	/**
	 * Getter-Methode für den Grundpreis dieses Artikels
	 * @return	String	Grundpreis
	 */
	public String getGrundpreis() {
		return grundpreis;
	}

	/*
	 * SETTER - METHODEN AUS GUI/USEREINGABE
	 * 
	 * 
	 */

	/**
	 * Setter-Methode für die Grundpeinheit dieses Artikels. 
	 * Überprüft vor der Zuweisung, ob die übergebene Grundpeinheit
	 * zulässig ist. Ist sie das nicht, wird diesem Artikel ein leerer String
	 * als Grundpeinheit zugewiesen, sonst der übergebene.
	 * @param	String	Grundpeinheit
	 */
	public void setpeinheit(String peinheit) {
		this.peinheit = checkpeinheit(peinheit) ? peinheit : "";
	}
	
	/**
	 * Setter-Methode für den Namen dieses Artikels. 
	 * Überprüft vor der Zuweisung, ob der übergebene Name
	 * zulässig ist. Ist er das nicht, wird diesem Artikel ein leerer String
	 * als Name zugewiesen, sonst der übergebene.
	 * @param	String	Name
	 */
	public void setName(String name) {
		this.name = checkName(name) ? name : "";
	}
	
	/**
	 * Setter-Methode für die Kategorie dieses Artikels. 
	 * @param	String	Kategorie
	 */
	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	/**
	 * Setter-Methode für die EAN dieses Artikels. 
	 * Überprüft vor der Zuweisung, ob die übergebene EAN
	 * zulässig ist. Ist sie das nicht, wird diesem Artikel ein leerer String
	 * als EAN zugewiesen, sonst die übergebene EAN.
	 * @param	String	EAN
	 */
	public void setEan(String ean) {
		this.ean = new EanVerifizierer(ean).getformatierteEan();
	}

	/**
	 * Setter-Methode für das Gewicht dieses Artikels. 
	 * Überprüft vor der Zuweisung, ob das übergebene Gewicht
	 * zulässig ist. Ist es das nicht, wird diesem Artikel ein leerer String
	 * als Gewicht zugewiesen, sonst das übergebene.
	 * @param	String	Gewicht
	 */
	public void setGewicht(String gewicht) {
		this.gewicht = checkGewicht(gewicht) ? gewicht.replace(",", ".") : "";
	}

	/**
	 * Setter-Methode für die im Lager vorhandene Anzahl dieses Artikels. 
	 * Überprüft vor der Zuweisung, ob die übergebene Anzahl
	 * zulässig ist. Ist sie das nicht, wird diesem Artikel ein leerer String
	 * als "Anzahl" zugewiesen, sonst die übergebene.
	 * @param	String	Anzahl (Wie oft ist dieser Artikel im Lager?)
	 */
	public void setAnzahl(String anzahl) {
		this.anzahl = checkAnzahl(anzahl) ? anzahl : "";
	}

	/**
	 * Setter-Methode für den Stückpreis dieses Artikels. 
	 * Überprüft vor der Zuweisung, ob der übergebene Stückpreis
	 * zulässig ist. Ist er das nicht, wird diesem Artikel ein leerer String
	 * als Stückpreis zugewiesen, sonst der übergebene.
	 * @param	String	Stückpreis
	 */
	public void setPreis(String preis) {
	    this.preis = checkPreis(preis.replace(",", ".")) ? preis.replace(",", ".") : "";
	}

	/**
	 * Setter-Methode für den Grundpreis dieses Artikels. 
	 * Überprüft vor der Zuweisung, ob der übergebene Grundpreis
	 * zulässig ist. Ist er das nicht, wird diesem Artikel ein leerer String
	 * als Grundpreis zugewiesen, sonst der übergebene.
	 * @param	String	Grundpreis
	 */
	public void setGrundpreis(String grundpreis) {
	    this.grundpreis = checkGrundpreis(grundpreis.replace(",", ".")) ? grundpreis.replace(",", ".") : "";
	}

	/**
	 * Setter-Methode für die Mengeneinheit dieses Artikels. 
	 * Überprüft vor der Zuweisung, ob die übergebene Mengeneinheit
	 * zulässig ist. Ist sie das nicht, wird diesem Artikel ein leerer String
	 * als Mengeneinheit zugewiesen, sonst die übergebene.
	 * @param	String	Mengeneinheit
	 */
	public void setEinheit(String einheit) {
		this.einheit = checkEinheit(einheit) ? einheit : "";
	}

	/*
	 * SETTER - METHODEN AUS XML
	 * 
	 * 
	 */

	/**
	 * Weist diesem Artikel seinen in der XML-Datei gespeicherten Namen zu.
	 */
	private void setNameFromXML() {
		name = xmlParser.getChild("name");
	}

	/**
	 * Weist diesem Artikel seine in der XML-Datei gespeicherte Kategorie zu.
	 */
	private void setKategorieFromXML() {
		kategorie = xmlParser.getChild("kategorie");
	}

	/**
	 * Weist diesem Artikel seine in der XML-Datei gespeicherte EAN zu.
	 */
	private void setEanFromXML() {
		ean = xmlParser.getChild("ean");
	}

	/**
	 * Weist diesem Artikel seine in der XML-Datei gespeicherte Grundpeinheit zu.
	 */
	private void setpeinheitFromXML() {
		peinheit = xmlParser.getChild("peinheit");
	}

	/**
	 * Weist diesem Artikel sein in der XML-Datei gespeichertes Gewicht zu.
	 */
	private void setGewichtFromXML() {
		gewicht = xmlParser.getChild("gewicht");
	}

	/**
	 * Weist diesem Artikel seine in der XML-Datei gespeicherte Anzahl 
	 * (Wie oft ist er im Lager vorhanden?) zu.
	 */
	private void setAnzahlFromXML() {
		anzahl = xmlParser.getChild("anzahl");
	}

	/**
	 * Weist diesem Artikel seinen in der XML-Datei gespeicherten Stückpreis zu.
	 */
	private void setPreisFromXML() {
		preis = xmlParser.getChild("preis");
	}

	/**
	 * Weist diesem Artikel seine in der XML-Datei gespeicherte Mengeneinheit zu.
	 */
	private void setEinheitFromXML() {
		einheit = xmlParser.getChild("einheit");
	}

	/**
	 * Weist diesem Artikel seinen in der XML-Datei gespeicherten Grundpreis zu.
	 */
	private void setGrundpreisFromXML() {
		grundpreis = xmlParser.getChild("grundpreis");
	}

	public void print() {
		System.out.println(name + ", " + ean + ", " + kategorie + ", " + einheit + ", " + peinheit + ", " + gewicht + ", "
				+ anzahl + ", " + preis + ", " + grundpreis);
	}
}
