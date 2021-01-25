package Parsing;

import java.util.HashSet;
import java.util.LinkedList;

import Database.*;

public class KategorieListe extends HashSet<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5598320182398998496L;

	public boolean isEmpty() {
		return this.isEmpty();
	}

	public boolean checkKategorie(String kategorie) {

		return kategorie.matches("[a-zA-Z]+") ? (3 <= kategorie.length() && kategorie.length() <= 32) : false;
	}

	public void addKategorie(String kategorie) {
		if (checkKategorie(kategorie)) {
			this.add(kategorie);
		}
		
		return;
	}

	// für Kategorien, die nicht vergeben sind
	public void removeKategorie(String kategorie) {
		
		return;
	}

	public void readKategorie(LinkedList<Artikel> artikelliste) {
		for (Artikel a : artikelliste) {
			this.addKategorie(a.getKategorie());
		}
		return;
	}

	// TODO Kategorien aus XML einlesen??

}
