public class ArtikelTest {
  
  public static void main(String[] args) {
    
    Artikel mehl = new Artikel();
    Artikel apfel = new Artikel();
    
    //einheit, name, kategorie, ean, gewicht, anzahl, preis, grundpreis, plu
    boolean check = mehl.Artikel("ml","Mehl","Backwaren","1111111111113","30","10","3.20","1.20","33333");
    System.out.println(check);
    
    check = apfel.Artikel("Kilo","Apfel","Obst","0000000055555","300","n","1.0","1.20","55555");
    System.out.println(check);
    
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