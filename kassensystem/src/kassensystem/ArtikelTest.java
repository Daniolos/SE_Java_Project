import Parsing.*;
import Database.*;
import java.util.*;

public class ArtikelTest {
  
  public static void main(String[] args) {
    
    KategorieListe kategorien = new KategorieListe();
    kategorien.addKategorie("Obst");
    kategorien.addKategorie("Backwaren");
    
    // name, ean, kategorie, einheit, plu, gewicht, anzahl, preis, grundpreis
   	Artikel mehl = new Artikel("Mehl","1111111111113","Backwaren","g","33333","0","0","0","0");
   	Artikel apfel = new Artikel("Apfel", "0000000055555", "Obst", "kg", "55555", "1", "300", "1,20", "0,1");

    
    Einkaufsliste einkauf = new Einkaufsliste();
    einkauf.addArtikel(mehl);
    einkauf.addArtikel(apfel);
    einkauf.addArtikel(mehl);
    einkauf.addArtikel(mehl);
    einkauf.addArtikel(apfel);
    einkauf.listPrinter();
    System.out.println("\n\nAnzahl Äpfel: " + einkauf.getAnzahl(apfel));
    System.out.println("Anzahl Mehl: " + einkauf.getAnzahl(mehl));
    System.out.println("\n\n");
    
    String[] arr = apfel.toStringArray();
    System.out.println(Arrays.toString(arr));
    for (int i = 0; i < apfel.toStringArray().length; i++) {
    	System.out.println(apfel.toStringArray()[i]);
    }
    
    String[][] array = einkauf.toStringArray();
    System.out.println(Arrays.deepToString(array));
    
  }
}