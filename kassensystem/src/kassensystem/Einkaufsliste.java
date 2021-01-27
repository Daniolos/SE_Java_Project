
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import Parsing.*;
import Database.*;

public class Einkaufsliste extends ArrayList<Artikel> {

	private float zwischensumme;

	public Einkaufsliste() {
		this.zwischensumme = 0;
	}

	public void addArtikel(Artikel artikel) {
		this.add(artikel);
		this.zwischensumme = zwischensumme + Float.parseFloat(artikel.getPreis());
		return;
	}

	public void removeArtikel(Artikel artikel) {
		this.remove(artikel);
		this.zwischensumme = zwischensumme - Float.parseFloat(artikel.getPreis());
		return;
	}

	public void einkaufBeenden(LinkedList<Artikel> artikelliste) {
		int index, anzahl;
		for (Artikel s : this) {
			index = artikelliste.indexOf(s);
			anzahl = Integer.parseInt(artikelliste.get(index).getAnzahl());
			artikelliste.get(index).setAnzahl(Integer.toString(anzahl - 1));
			this.remove(s);
		}
		return;
	}
	
	private int eanInArray(String[][] arr, String ean) {
		for (int i = 0; i < arr.length; i++) {		//Mit while-Schleife um abzufangen, dass i null ist?
			if (arr[i][1] == null) { 
				return -1; 
			}
			if (arr[i][1].equals(ean)) {
				return i;
			}
		}
		return -1;
	}
	
	//name, ean, kategorie, einheit, plu, gewicht, anzahl,
	//preis, grundpreis
	
	private String[][] appendArtikel(int index, int anzahl, Artikel article, String[][] arr) {
		arr[index][0] = article.getName();
		arr[index][1] = article.getEan();
		arr[index][2] = article.getKategorie();
		arr[index][3] = article.getEinheit();
		arr[index][4] = article.getPlu();
		arr[index][5] = article.getGewicht();
		arr[index][6] = article.getAnzahl();
		arr[index][7] = article.getPreis();
		arr[index][8] = article.getGrundpreis();
		arr[index][9] = Integer.toString(anzahl);
		return arr;
	}
	
	private String[][] toStringArrayNULL() {			// Array ist genauso lang wie Einkaufsliste, letzte Arrayteile nur = null
		String[][] arr = new String[this.size()][10];	// Index 9 im Array gibt an, wie oft der Artikel in Einkaufsliste ist
		int anzahl, index;
		int count = 0;
		for (Artikel a : this) {
			if (count == 0) {
				arr = appendArtikel(0, 1, a, arr);
				count++;
				continue;
			}
			index = eanInArray(arr, a.getEan());
			if (index < 0) {									// Wenn der Artikel noch nicht in dem Array ist
				arr = appendArtikel(count, 1, a, arr);			// wird er angehängt
			}
			if (index >= 0) {									// Ist der Artikel schon in dem Array gespeichert,
				anzahl = Integer.parseInt(arr[index][9]);		// wird die Anzahl um eins erhöht.
				arr[index][9] = Integer.toString(anzahl + 1);
			}
			count++;
		}
		return arr;
	}
	
	public String[][] toStringArray() {				// entfernt die "null" Teile von toStringArrayNULL()
		String[][] arr = this.toStringArrayNULL();
		int merke = 0; int i = 0;
		while (merke != arr.length) {
			merke += Integer.parseInt(arr[i][9]);
			i++;
		}
		String[][] erg = new String[i][10];
		for (int count = 0; count < i; count++) {
			erg[count] = arr[count];
		}
		return erg;
	}

	public int getAnzahl(Artikel artikel) {
		int counter = 0;
		for (Artikel s : this) {
			if (s.getEan().equals(artikel.getEan())) {
				counter++;
			}
		}
		return counter;
	}

	public float getZwischensumme() {
		return zwischensumme;
	}

	public void listPrinter() {
		for (Artikel s : this) {
			System.out.println("Name: " + s.getName());
			System.out.println("Kategorie: " + s.getKategorie());
			System.out.println("Einheit: " + s.getEinheit());
			System.out.println("EAN/ PLU: " + s.getEan());
			System.out.println("Gewicht: " + s.getGewicht());
			System.out.println("Anzahl: " + s.getAnzahl());
			System.out.println("Preis: " + s.getPreis());
			System.out.println("Grundpreis: " + s.getGrundpreis());
			System.out.println();
		}
		System.out.println("Zwischensumme: " + this.zwischensumme);
	}

}