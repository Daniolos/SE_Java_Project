import Parsing.*;
import Database.*;

public class ArtikelTest {
  
  public static void main(String[] args) {
    
    KategorieListe kategorien = new KategorieListe();
    kategorien.addKategorie("Obst");
    kategorien.addKategorie("Backwaren");
    
    // name, ean, kategorie, einheit, plu, gewicht, anzahl, preis, grundpreis
    Artikel mehl = new Artikel("Mehl","1111111111113","Backwaren","ml","33333","0","0","0","0");
    Artikel apfel = new Artikel("Apfel", "0000000055555", "Obst", "n", "55555", "1", "300", "1,20", "0,1");
    

    
    Einkaufsliste einkauf = new Einkaufsliste();
    einkauf.add(mehl);
    einkauf.add(apfel);
    einkauf.add(mehl);
    einkauf.add(mehl);
    einkauf.add(apfel);
    einkauf.listPrinter();
    System.out.println("\n\nAnzahl Äpfel: " + einkauf.getAnzahl(apfel));
    System.out.println("Anzahl Mehl: " + einkauf.getAnzahl(mehl));
    System.out.println("\n\n");
    
  }
}