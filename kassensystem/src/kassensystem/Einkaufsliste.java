import java.util.ArrayList;
import Parsing.*;
import Database.*;

public class Einkaufsliste extends ArrayList<Artikel> {

  private float zwischensumme;

  public Einkaufsliste() {
    this.zwischensumme = 0;
  }

  public void addArtikel(Artikel artikel) {
    this.add(artikel);
    //this.zwischensumme = zwischensumme + Float.parseFloat(artikel.getPreis);
    return;
  }

  public void removeArtikel(Artikel artikel) {
    this.remove(artikel);
    //this.zwischensumme = zwischensumme - Float.parseFloat(artikel.getPreis);
    return;
  }

 /* public void einkaufBeenden(ArrayList<Artikel> artikelliste) {
    int index;
    for (Artikel s : this) {
      index = artikelliste.indexOf(s);
      artikelliste.get(index).setAnzahl(artikelliste.get(index)-1);
      this.remove(s);
    }
    return;
  }*/
  
  public int getAnzahl(Artikel artikel) {
    int counter = 0;
    for (Artikel s: this) {
      if (s.getEan().equals(artikel.getEan())) { counter++; }
    }
    return counter;
  }

  public float getZwischensumme() {
    return zwischensumme;
  }
  
  public void listPrinter() {
    for (Artikel s: this) {
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