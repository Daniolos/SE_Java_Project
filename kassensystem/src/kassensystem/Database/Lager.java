package Database;

import java.util.LinkedList;
//import Parsing.EanVerifizierer;
import Parsing.XMLParser;
import Parsing.Artikel;
import Parsing.EanVerifizierer;

public class Lager {
	private String articleMarkup;
	private LinkedList<Artikel> articles;
	private KategorieListe kategorien;

	public Lager(String xml) {
		articles = new LinkedList<Artikel>();
		kategorien = new KategorieListe();
		setAllArticlesFromString(xml);
		fillLager();
		for (Artikel a : articles) {
			kategorien.addKategorie(a);
		}
		//TODO Print-Anweisungen entfernen
		System.out.println(xml);
		System.out.println(articleMarkup);
	}

	// xml contains whole xml as String

	private void setAllArticlesFromString(String xml) {
		articleMarkup = XMLParser.getChild(xml, "articles");
	}

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

	public LinkedList<Artikel> getArtikel() {
		return articles;
	}

	public KategorieListe getKategorien() {
		return kategorien;
	}

	public void ArtikelHinzufuegen(Artikel article) {
		if (!article.getName().equals("") && !article.getEan().equals(""))
			articles.add(article); // Was passiert, wenn Artikel leer sind, wenn Artikel doppelt sind
		// TODO Muss hier die Kategorie hinzugefügt werden? Oder werden die nur separat
		// hinzugefügt?
	}

	public Boolean addAndCheck(Artikel article) {
		ArtikelHinzufuegen(article);
		for (Artikel art : articles) {
			if (article.getName().equals(art.getName()) || article.getEan().equals(art.getEan()))
				return true;
		}
		return false;

	}

	public void ArtikelHinzufuegen(String name, String ean, String kategorie, String einheit, String preiseinheit,
			String gewicht, String anzahl, String preis, String grundpreis) {
		// �berpr�fen, ob name oder ean schon da ist / caps werden ignoriert

		Boolean dublicate = false;

		for (Artikel article : articles) {
			if (new EanVerifizierer(ean).getformatierteEan().equals(article.getEan())) {
				dublicate = true;
				break;
			}
		}

		if (!dublicate) {
			// stock-parsing kann Fehler hervorrufen, hier in try-catch abfangen
			articles.add(new Artikel(name, ean, kategorie, einheit, preiseinheit, gewicht, anzahl, preis, grundpreis));
			// (name, ean, kategorie, einheit, plu, gewicht, Integer.parseInt(anzahl),
			// Float.parseFloat(preis), Float.parseFloat(grundpreis))
		}
		// else -> was soll sonst passieren?
		// man k�nnte success- oder fail-meldung ausgeben (bool)
	}
//	
//	// �berladene Version f�r Article
//	
//	public void addArticle (Article art)
//	{
//		Boolean dublicate = false;
//		for (Article article : articles)
//		{
//			if (new EanValidator(art.getEan()).getFormattedEan().equals(article.getEan())) || // ean gibts schon
//				art.getName().equals("")) // name leer
//			{
//				dublicate = true;
//				break;
//			}
//		}
//		
//		art.setId(getNewId());
//		
//		if (!dublicate)
//		{
//			// stock-parsing kann Fehler hervorrufen, hier in try-catch abfangen
//			articles.add(art);
//		}
//	}

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

	public void delete(String ean) {
		EanVerifizierer eanVerifizierer = new EanVerifizierer(ean);
		int index = 0;
		for (Artikel article : articles) {
			if (article.getEan() == eanVerifizierer.getformatierteEan()) {
				articles.remove(index);
				break;
			}
			index++;
		}
	}
	
	public String[][] toStringArray() {
		String[][] arr = new String[articles.size()][9];
		int i = 0;
		for (Artikel a : this.articles) {
			arr[i] = a.toStringArray();
			i++;
		}
		return arr;
	}

	public void print() {
		for (Artikel article : articles) {
			article.print();
		}
	}
}
