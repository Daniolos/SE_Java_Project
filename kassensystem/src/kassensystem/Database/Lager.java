package Database;

import java.util.LinkedList;
//import Parsing.EanVerifizierer;
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
	 * @param String XML-Datei (ohne Header) als String
	 */
	public Lager(String xml) {
		articles = new LinkedList<Artikel>();
		kategorien = new KategorieListe();
		setAllArticlesFromString(xml);
		fillLager();
		for (Artikel a : articles) {
			kategorien.addKategorie(a);
		}
	}

	// xml contains whole xml as String

	/**
	 * Speichert die Artikel aus der XML-Datei (ohne den Header und den Beginn-
	 * beziehungsweise End-Tag <articles> </articles> der alle Artikel umschließt)
	 * in this.articleMarkup
	 * 
	 * @param String XML-Datei als String, ohne Header
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
	 * @return LinkedList<Artikel> Liste mit allen Artikeln
	 */
	public LinkedList<Artikel> getArtikel() {
		return articles;
	}

	/**
	 * Getter-Methode für die Kategorieliste
	 * 
	 * @return KategorieListe HashSet mit allen Kategorien als Strings
	 */
	public KategorieListe getKategorien() {
		return kategorien;
	}

	/**
	 * Fügt Artikel der Artikelliste hinzu, wenn dessen Name und EAN nicht leere
	 * Strings sind
	 * 
	 * @param Artikel Der Artikel der der Liste hinzugefügt werden soll.
	 */
	public void ArtikelHinzufuegen(Artikel article) {
		if (!article.getName().equals("") && !article.getEan().equals(""))
			articles.add(article);
	}

	/**
	 * Fügt einen Artikel zur Artikelliste hinzu und überprüft, ob dieser schon
	 * enthalten ist. Gibt false zurück, sollte der Artikel schon in der Liste
	 * stehen, sonst false.
	 * 
	 * @param Artikel Der Artikel, der der Liste hinzugefügt werden soll.
	 * @return Boolean false, wenn der Artikel schon in der Liste stand, true sonst
	 */
	public Boolean addAndCheck(Artikel article) {
		boolean check = false;
		for (Artikel art : articles) {
			if (article.getName().equals(art.getName()) || article.getEan().equals(art.getEan()))
				check = true;
		}
		ArtikelHinzufuegen(article);
		return check;

	}

	/**
	 * Methode um Artikel zur Artikelliste hinzuzufügen. Es wird überprüft, ob der
	 * Artikel schon in der Liste ist. Ist das der Fall, gibt die Methode den
	 * Fehlercode -10 zurück. Außerdem wird überprüft, ob die Artikeleigenschaften
	 * zulässig sind. Ist eine das nicht, wird ein entsprechender Fehlercode
	 * zurückgegeben.
	 * 
	 * @param String Name (Fehlercode -1)
	 * @param String EAN (Fehlercode -2)
	 * @param String Stückpreis (Fehlercode -3)
	 * @param String Anzahl im Lager (Fehlercode -4)
	 * @param String Grundpreis (Fehlercode -5)
	 * @param String Grundpreiseinheit (Fehlercode -6)
	 * @param String Menge, zum Beispiel Gewicht, Volumen (Fehlercode -7)
	 * @param String Mengeneinheit (Fehlercode -8)
	 * @param String Kategorie (Fehlercode -9)
	 * 
	 * @return int Fehlercode < 0, oder wenn alles glattgelaufen ist 0
	 */
	public int ArtikelHinzufuegen(String name, String ean, String preis, String anzahl, String grundpreis,
			String preiseinheit, String gewicht, String einheit, String kategorie) {

		// überprüfen, ob EAN schon in Liste ist
		for (Artikel article : articles) {
			if (new EanVerifizierer(ean).getformatierteEan().equals(article.getEan())) {
				return -10; // gibt -10 zurück, wenn Artikel schon in Liste ist
			}
		}

		Artikel article = new Artikel(name, ean, kategorie, einheit, preiseinheit, gewicht, anzahl, preis, grundpreis);
		String[] arr = article.toStringArray();
		for (int i = 0; i <= arr.length; i++) {
			if (arr[i].equals("")) {
				return (i + 1) * (-1); // gibt (index der Eigenschaft + 1) * -1 zurück, für Fehler bei Eigenschaft
			}
		}

		// stock-parsing kann Fehler hervorrufen, hier in try-catch abfangen
		articles.add(new Artikel(name, ean, kategorie, einheit, preiseinheit, gewicht, anzahl, preis, grundpreis));
		return 0;
	}

	// TODO Brauchen wir das noch?
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
	 * @param String EAN, die aus der Artikelliste gelöscht werden soll.
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
	 * @param Artikel Der Artikel, der gelöscht werden soll
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
	 * @return String[][] zweidimensionales Array in dem jeder Index ein Artikel ist
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

	// TODO Brauchen wir das noch?
	public void print() {
		for (Artikel article : articles) {
			article.print();
		}
	}
}
