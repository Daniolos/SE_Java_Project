package Database;

import java.util.HashSet;
import java.util.LinkedList;

import Parsing.Artikel;

/**
 * 
 * @author Philomena Moek
 *
 */

public class KategorieListe extends HashSet<String> {


	/**
	 * Methode um zu überprüfen, ob eine Kategorie zulässig ist, also in diesem
	 * HashSet drinsteht.
	 * 
	 * @param String Die Kategorie, die überprüft werden soll als String
	 * @return Boolean true, wenn dieses HashSet die Kategorie enthält, sonst
	 *         falsch.
	 */
	public boolean checkKategorie(String kategorie) {
		return this.contains(kategorie);
	}

	/**
	 * Methode um eine neue Kategorie hinzuzufügen. Erfüllt sie die richtigen
	 * Anforderungen, wird sie hinzugefügt.
	 * 
	 * @param String Kategorie, die hinzugefügt werden soll
	 */
	public void addKategorie(String kategorie) {
		if (kategorie.matches("[a-zäöüßA-ZÄÖÜ]+") && (3 <= kategorie.length() && kategorie.length() <= 32)) {
			this.add(kategorie);
		}
		return;
	}

	/**
	 * überladene Methode, die neue Kategorie von einem übergebenen Artikel
	 * hinzufügt.
	 * 
	 * @param Artikel Artikel, dessen Kategorie neu hinzugefügt werden soll.
	 */
	public void addKategorie(Artikel article) {
		if ((article.getKategorie().matches("[a-zäöüßA-ZÄÖÜ]+")) && (3 <= article.getKategorie().length())
				&& (article.getKategorie().length() <= 32)) {
			this.add(article.getKategorie());
		}
		return;
	}

	/**
	 * Methode, um Kategorie zu entfernen. Überprüft, ob in der übergebenen Liste
	 * ein Artikel vorkommt, der diese Kategorie verwendet, dann passiert nichts.
	 * Kommt die Kategorie nirgendwo vor, wird sie aus diesem HashSet gelöscht.
	 * 
	 * @param String              Die Kategorie, die entfernt werden soll.
	 * @param LinkedList<Artikel> Das Lager, das nach Artikeln die die Kategorie
	 *                            enthalten durchsucht werden muss
	 */
	public void removeKategorie(String kategorie, LinkedList<Artikel> articles) {
		for (Artikel a : articles) {
			if (a.getKategorie().equals(kategorie)) {
				return;
			}
			this.remove(kategorie);
		}
		return;
	}

	/**
	 * Methode, die das HashSet in ein String Array umwandelt
	 * 
	 * @return String[] Array, das alle hier gespeicherten Kategorien enthält
	 */
	public String[] toStringArray() {
		String[] arr = new String[this.size()];
		int count = 0;
		for (String s : this) {
			arr[count] = s;
		}
		return arr;
	}
}
