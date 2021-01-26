package Database;

import java.util.HashSet;
import java.util.LinkedList;

import Database.*;
import Parsing.Artikel;

public class KategorieListe extends HashSet<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5598320182398998496L;

	public boolean isEmpty() {
		return this.isEmpty();
	}

	public boolean checkKategorie(String kategorie) {
		return this.contains(kategorie) ? true : false;
	}

	public void addKategorie(String kategorie) {
		if (kategorie.matches("[a-zA-Z]+") && (3 <= kategorie.length() && kategorie.length() <= 32)) {
			this.add(kategorie);
		}
		return;
	}

	public void addKategorie(Artikel article) {
		if (article.getKategorie().matches("[a-zA-Z]+") && (3 <= article.getKategorie().length() && article.getKategorie().length() <= 32)) {
			this.add(article.getKategorie());
		}
		return;
	}

	// für Kategorien, die nicht vergeben sind
	public void removeKategorie(String kategorie) {

		return;
	}

	public void readKategorien(LinkedList<Artikel> artikelliste) {
		for (Artikel a : artikelliste) {
			this.addKategorie(a.getKategorie());
		}
		return;
	}

	public void printer() {
		for (String k : this) {
			System.out.println(k);
		}
	}
}
