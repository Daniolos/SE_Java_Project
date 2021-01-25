package Parsing;

import java.util.HashSet;

public class KategorieListe extends HashSet<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5598320182398998496L;

	public boolean isEmpty() {
		return this.isEmpty();
	}

	public void addKategorie(String kategorie) {
		this.add(kategorie);
		return;
	}

	// TODO Kategorien aus XML einlesen??

}
