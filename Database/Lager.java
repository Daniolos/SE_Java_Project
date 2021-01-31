package Database;

import java.util.LinkedList;
import Parsing.XMLParser;
import Parsing.Artikel;
import Parsing.EanVerifizierer;

/**
 * 
 * @author Felix Schulz, Philomena Moek
 *
 */

public class Lager {
	private String articleMarkup;
	private LinkedList<Artikel> articles;
	private KategorieListe kategorien;

	/**
	 * Konstruktor für diese Klasse Bekommt einen String übergeben, der die gesamte
	 * XML-Datei (ohne Header) enthält und liest daraus alle Artikel in das Lager
	 * und erstellt eine KategorieListe
	 * 
	 * @param xml XML-Datei (ohne Header) als String
	 */
	public Lager(String xml) {
		articles = new LinkedList<Artikel>();
		kategorien = new KategorieListe();
		setAllArticlesFromString(xml);
		fillLager();
		kategorien.add("Keine Kategorie");
		for (Artikel a : articles) {
			kategorien.addKategorie(a);
		}
	}

	/**
	 * Speichert die Artikel aus der XML-Datei (ohne den Header und den Beginn-
	 * beziehungsweise End-Tag <articles> </articles> der alle Artikel umschließt)
	 * in this.articleMarkup
	 * 
	 * @param xml XML-Datei als String, ohne Header
	 */
	private void setAllArticlesFromString(String xml) {
		articleMarkup = XMLParser.getChild(xml, "articles");
	}

	/**
	 * Füllt mittels des Strings in this.articleMarkup die Artikelliste
	 * (this.articles) auf. Alle Artikel aus der XML-Datei werden in die
	 * Artikelliste "articles" gelesen.
	 */
	private void fillLager() {
		String placeholder = "yqzxdf";
		String replacedXMLString = articleMarkup.replaceAll("</article>\\s*<article>",
				"</article>" + placeholder + "<article>");
		String[] art = replacedXMLString.split(placeholder);
		for (String a : art) {
			if (!a.trim().equals("")) {
				articles.add(new Artikel(a));
			}
		}
	}

	/**
	 * Getter-Methode für die Artikelliste
	 * 
	 * @return Liste mit allen Artikeln
	 */
	public LinkedList<Artikel> getArtikel() {
		return articles;
	}

	/**
	 * Getter-Methode für die Kategorieliste
	 * 
	 * @return HashSet mit allen Kategorien als Strings
	 */
	public KategorieListe getKategorien() {
		return kategorien;
	}

	/**
	 * Fügt Artikel der Artikelliste hinzu, wenn dessen Name und EAN nicht leere
	 * Strings sind
	 * 
	 * @param article Der Artikel der der Liste hinzugefügt werden soll.
	 */
	public void ArtikelHinzufuegen(Artikel article) {
		if (!article.getName().equals("") && !article.getEan().equals(""))
			articles.add(article);
	}

	/**
	 * Methode um Artikel zur Artikelliste hinzuzufügen. Es wird überprüft, ob der
	 * Artikel schon in der Liste ist. Ist das der Fall, gibt die Methode den
	 * Fehlercode -10 zurück. Außerdem wird überprüft, ob die Artikeleigenschaften
	 * zulässig sind. Ist eine das nicht, wird ein entsprechender Fehlercode
	 * zurückgegeben.
	 * 
	 * @param name         Name (Fehlercode -1)
	 * @param ean          EAN (Fehlercode -2)
	 * @param preis        Stückpreis (Fehlercode -3)
	 * @param anzahl       Anzahl im Lager (Fehlercode -4)
	 * @param grundpreis   Grundpreis (Fehlercode -5)
	 * @param preiseinheit Grundpreiseinheit (Fehlercode -6)
	 * @param gewicht      Menge, zum Beispiel Gewicht, Volumen (Fehlercode -7)
	 * @param einheit      Mengeneinheit (Fehlercode -8)
	 * @param kategorie    Kategorie (Fehlercode -9)
	 * @return int Fehlercode < 0, oder wenn alles glattgelaufen ist 0
	 */
	public int ArtikelHinzufuegen(String name, String ean, String preis, String anzahl, String grundpreis,
			String preiseinheit, String gewicht, String einheit, String kategorie) {

		Artikel article = new Artikel(name, ean, kategorie, einheit, preiseinheit, gewicht, anzahl, preis, grundpreis);
		String[] arr = article.toStringArray();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals("")) {
				return (i + 1) * (-1); // gibt (index der Eigenschaft + 1) * -1 zurück, für Fehler bei Eigenschaft
			}
		}

		for (Artikel a : articles) {
			if (new EanVerifizierer(ean).getformatierteEan().equals(a.getEan())) {
				return -10; // gibt -10 zurück, wenn Artikel schon in Liste ist
			}
		}

		articles.add(new Artikel(name, ean, kategorie, einheit, preiseinheit, gewicht, anzahl, preis, grundpreis));
		return 0;
	}

	/**
	 * Diese Methode bekommt einen Artikelnamen oder eine EAN übergeben, nach
	 * welchem/welcher dann im Lager/der Artikelliste gesucht wird.
	 * 
	 * @param nameOrEan - Übergebener String, nach dem in der Artikelliste gesucht
	 *                  werden soll.
	 * @return
	 */

	public Artikel search(String nameOrEan) {
		EanVerifizierer eanVerifizierer = new EanVerifizierer(nameOrEan);

		for (Artikel article : articles) {
			if (eanVerifizierer.valideEan() ? article.getEan().equals(eanVerifizierer.getformatierteEan())
					: article.getName().toLowerCase().equals(nameOrEan.toLowerCase())) {
				return article;
			}
		}

		return new Artikel("");
	}

	/**
	 * Löscht einen Artikel über eine EAN oder PLU aus der Artikelliste. Ist die EAN
	 * oder PLU ungültig, passiert gar nichts.
	 * 
	 * @param ean EAN, die aus der Artikelliste gelöscht werden soll.
	 */
	public void delete(String ean) {

		EanVerifizierer eanVerifizierer = new EanVerifizierer(ean);
		String verifiedEan = eanVerifizierer.getformatierteEan();

		if (verifiedEan.equals("")) {
			return;
		}

		for (Artikel article : articles) {
			if (verifiedEan.equals(article.getEan())) {
				articles.remove(article);
				break;
			}
		}
	}

	/**
	 * Löscht einen übergebenen Artikel aus der Artikelliste. Ist die EAN des
	 * Artikels ungültig, passiert gar nichts.
	 * 
	 * @param erase Der Artikel, der gelöscht werden soll
	 */
	public void delete(Artikel erase) {

		EanVerifizierer eanVerifizierer = new EanVerifizierer(erase.getEan());
		String verifiedEan = eanVerifizierer.getformatierteEan();

		if (verifiedEan.equals("")) {
			return;
		}

		for (Artikel article : articles) {
			if (verifiedEan.equals(article.getEan())) {
				articles.remove(article);
				break;
			}
		}
	}

	/**
	 * Methode, die Artikelliste in ein zweidimensionales Array umwandelt. Jeder
	 * Index in dem Array ist ein Artikel (siehe auch: Artikel.toStringArray())
	 * 
	 * @return zweidimensionales Array in dem jeder Index ein Artikel ist
	 */
	public String[][] toStringArray() {
		String[][] arr = new String[articles.size()][9];
		int i = 0;
		for (Artikel a : this.articles) {
			arr[i] = a.toStringArray();
			i++;
		}
		return arr;
	}
}
