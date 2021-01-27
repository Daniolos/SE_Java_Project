package Database;

import java.util.LinkedList;
//import Parsing.EanVerifizierer;
import Parsing.XMLParser;
import Parsing.Artikel;

public class Lager {
	private String articleMarkup;
	private LinkedList<Artikel> articles;
	private KategorieListe kategorien;

	//liest jetzt auch automatisch die Kategorien aus der XML Datei in KategorieListe ein.
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
	// Header von xml wird hier entfernt
	private void setAllArticlesFromString(String xml) {
		articleMarkup = XMLParser.getChild(xml, "articles");
	}

	private void fillLager() {
		String placeholder = "yqzxdf";
		String replacedXMLString = articleMarkup.replaceAll("</article>\\s*<article>",
				"</article>" + placeholder + "<article>"); 									// erstetzt die whitespaces zw den Artikeln durch "yqzxdf"
		String[] art = replacedXMLString.split(placeholder); 								// erzeugt String Array mit Artikeln
		for (String a : art) {
			if (!a.trim().equals("")) { 													// wenn dieses Attribut nicht leer ist
				articles.add(new Artikel(a)); 												// wird daraus ein neuer Artikel gelesen
			}
		}
	}

	public LinkedList<Artikel> getArtikel() {
		return articles;
	}
	
	public KategorieListe getKategorien() {
		return kategorien;
	}

	public void ArtikelHinzufuegen(Artikel article) {
		articles.add(article); // Was passiert, wenn Artikel leer sind, wenn Artikel doppelt sind usw. (noch hinzuf.)
		if (!kategorien.contains(article.getKategorie())) {	// Braucht man das überhaupt? Kategorien werden ja extra dazugefügt... oder?
			kategorien.addKategorie(article);
		}
	}

	public void print() {
		for (Artikel article : articles) {
			article.print();
		}
	}
}