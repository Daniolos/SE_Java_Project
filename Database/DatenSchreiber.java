package Database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Formatter;

import Parsing.Artikel;

/**
 * 
 * @author Felix Schulz
 *
 */

public class DatenSchreiber {
	private Lager lager;
	private String ErsteZeile;
	private String articles;
	private String xml;

	/**
	 * Konstruktor für die Klasse. Alle Datenelemente - bis auf das Lager - werden
	 * initialisiert: ErsteZeile enthält den XML-Header, articles wird mit den
	 * Beginn- und Abschluss-Tags um das Lager in der XML-Datei initialisiert und
	 * xml konkateniert beides.
	 */
	public DatenSchreiber() {
		setErsteZeile();
		articles = "<articles></articles>";
		xml = ErsteZeile + articles;
	}

	/**
	 * überladener Konstruktor für diese Klasse. Alle Datenelemente dieser Klasse
	 * werden initialisiert, mittels des übergebenen Lagers. this.xml enthält die
	 * fertige XML-Datei (als String), die mit dem Aufruf der Methode Schreiben() in
	 * die Datei geschrieben werden kann.
	 * 
	 * @param lager enthält alle Artikel in Form einer LinkedList<Artikel>
	 */
	public DatenSchreiber(Lager lager) {
		this.lager = lager;
		setErsteZeile();
		setArticles();
		xml = ErsteZeile + articles;
	}

	/**
	 * Setter-Methode für this.ErsteZeile this.ErsteZeile wird der String für den
	 * XML-Header zugewiesen
	 */
	private void setErsteZeile() {
		ErsteZeile = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	}

	/**
	 * Setter-Methode für this.articles this.articles (String) wird jeder Artikel im
	 * XML-Format angefügt, sodass am Ende articles eine String im XML-Format
	 * speichert, der jeden Artikel im Lager beinhaltet.
	 */
	private void setArticles() {
		String Cut = "<articles>\n";

		for (Artikel article : lager.getArtikel()) {
			Cut += ArtikelXML(article);
		}

		Cut += "</articles>\n";
		articles = Cut;
	}

	/**
	 * Die Methode wandelt den Datentyp Artikel in einen String im XML-Format um und
	 * gibt diesen String zurück
	 * 
	 * @param article Ein Artikel wird übergeben
	 * @return Der Artikel in XML-Form wird zurückgegeben
	 */
	private String ArtikelXML(Artikel article) {
		String xmlString = "\t<article>\n";
		xmlString += "\t\t<name>" + article.getName() + "</name>\n";
		xmlString += "\t\t<ean>" + article.getEan() + "</ean>\n";
		xmlString += "\t\t<kategorie>" + article.getKategorie() + "</kategorie>\n";
		xmlString += "\t\t<einheit>" + article.getEinheit() + "</einheit>\n";
		xmlString += "\t\t<peinheit>" + article.getpeinheit() + "</peinheit>\n";
		xmlString += "\t\t<gewicht>" + article.getGewicht() + "</gewicht>\n";
		xmlString += "\t\t<anzahl>" + article.getAnzahl() + "</anzahl>\n";
		xmlString += "\t\t<preis>" + article.getPreis() + "</preis>\n";
		xmlString += "\t\t<grundpreis>" + article.getGrundpreis() + "</grundpreis>\n";
		xmlString += "\t</article>\n";
		return xmlString;
	}

	/**
	 * Der aus dem Lager erstellte String (der einen XML-Header, sowie alle Artikel
	 * im XML-Format speichert) wird in die XML-Datei geschrieben
	 */
	public void Schreiben() {
		try {
			BufferedWriter dbData = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(System.getProperty("user.dir") + "/src/Database/database.xml"), "UTF-8"));
			dbData.write(xml);
			dbData.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
