public class Artikel {

  private String name;
  private String kategorie;
  private String[] einheiten = {"Gramm", "Kilo", "Liter", "ml", "Stück"};
  private String einheit;
  private String ean;
  private String plu;
  private String gewicht;
  private String anzahl;
  private String preis;
  private String grundpreis;
  // id?

  public Artikel() {

    this.name = "";
    this.kategorie = "";
    this.ean = "";
    this.plu = "";
    this.gewicht = "";
    this.anzahl = "";
    this.preis = "";
    this.einheit = "";
    this.grundpreis = "";

  }

  public boolean checkName(String name) {

    // name überprüfen
    if (2 <= name.length() & name.length() <= 32) {
      return true;
    } else {
      return false;
    }

  }

  public boolean checkKategorie(String kategorie) {

    // kategorie überprüfen
    if (kategorie.matches("[a-zA-Z]+") == false) {
      return false;
    }

    if (3 <= kategorie.length() & kategorie.length() <= 32) {
      return true;
    } else {
      return false;
    }

  }

  public boolean checkEan(String ean) {

    // EAN überprüfen
    if (!ean.matches("[0-9]+")) {
      return false;
    }

    if (ean.length() == 8 | ean.length() == 13) {
      return true;
    } else {
      return false;
    }
  }

  public boolean checkPlu(String plu) {

    // EAN überprüfen
    if (!ean.matches("[0-9]+")) {
      return false;
    }

    if (plu.length() == 4 | plu.length() == 5) {
      return true;
    } else {
      return false;
    }
  }

  public boolean checkGewicht(String gewicht) {

    // gewicht überprüfen
    try {
      
      if ((einheit == "Gramm") && (Float.parseFloat(gewicht) >= 1) && (Float.parseFloat(gewicht) <= 100000)) {
        return false;
      }

      if ((einheit == "Kilo") && (Float.parseFloat(gewicht) >= 0.1) && (Float.parseFloat(gewicht) <= 100)) {
        return false;
      }

      if ((einheit == "Liter") && (Float.parseFloat(gewicht) >= 0.1) && (Float.parseFloat(gewicht) <= 100)) {
        return false;
      }

      if ((einheit == "ml") && (Float.parseFloat(gewicht) >= 1) && (Float.parseFloat(gewicht) <= 10000)) {
        return false;
      }

    } catch (NumberFormatException e) {
      System.err.println("Ungültige Eingabe bei Gewicht.");
      return false;
    }

    return true;

  }

  public boolean checkAnzahl(String anzahl) {

    if (anzahl == null) { return false; }
    if (anzahl == "n") { return true; }
    
    try {
      if (!(Integer.parseInt(anzahl) >= 1) || !(Integer.parseInt(anzahl) <= 1000)) {
        return false;
      }
    } catch (NumberFormatException e) {
      System.err.println("Ungültige Eingabe bei Anzahl");
      return false;
    }

    return true;

  }

  public boolean checkPreis(String preis) {
    
    // preis überprüfen
    if (preis == null) { return false; }

    try {
     
      if (!(Float.parseFloat(preis) >= 0.001) || !(Float.parseFloat(preis) <= 100000)) {
        return false;
      }
    } catch (NumberFormatException e) {
      System.err.println("Ungültige Eingabe bei Preis.");
      return false;
    }

    return true;
  }

  public boolean checkGrundpreis(String grundpreis) {
    
    if (grundpreis == null) { return false; }
    try {
      
      if ( !(Float.parseFloat(grundpreis) >= 0.001) || !(Float.parseFloat(grundpreis) <= 100000) ) {
         return false;
      }
      
    } catch (NumberFormatException e) {
      System.err.println("Ungültige Eingabe bei Grundpreis.");
      return false;
    }
    
    return true;
  }

  public boolean checkEinheit(String einheit) {

    for (int i = 0; i < einheiten.length; i++) {
      if (einheiten[i] == einheit) {
        return true;
      }
    }
    return false;
  }


  public boolean Artikel(String einheit, String name, String kategorie, String ean, String gewicht,
      String anzahl, String preis, String grundpreis) {

    if (checkName(name) == true) {
      this.name = name;
    } else {
      System.out.println("name");
      return false;
    }

    if (checkKategorie(kategorie) == true) {
      this.kategorie = kategorie;
    } else {
      System.out.println("kategorie");
      return false;
    }

    if (checkEan(ean) == true) {
      this.ean = ean;
    } else {
      System.out.println("ean");
      return false;
    }

    if (checkPlu(plu) == true) {
      this.plu = plu;
    } else {
      System.out.println("plu");
      return false;
    }

    if (checkGewicht(gewicht) == true) {
      this.gewicht = gewicht;
    } else {
      System.out.println("gewicht");
      return false;
    }

    if (checkAnzahl(anzahl) == true) {
      this.anzahl = anzahl;
    } else {
      System.out.println("anzahl");
      return false;
    }

    if (checkPreis(preis) == true) {
      this.preis = preis;
    } else {
      System.out.println("preis");
      return false;
    }

    if (checkGrundpreis(grundpreis) == true) {
      this.grundpreis = grundpreis;
    } else {
      System.out.println("grundpreis");
      return false;
    }

    if (checkEinheit(einheit) == true) {
      this.einheit = einheit;
    } else {
      System.out.println("einheit");
      return false;
    }

    return true;
  }


  /*
   * GETTER - METHODEN
   * 
   * 
   */

  public String getName() {
    return name;
  }

  public String getKategorie() {
    return kategorie;
  }

  public String getEinheit() {
    return einheit;
  }

  public String getEan() {
    return ean;
  }

  public String getGewicht() {
    return gewicht;
  }

  public String getAnzahl() {
    return anzahl;
  }

  public String getPreis() {
    return preis;
  }

  public String getPlu() {
    return plu;
  }

  public String getGrundpreis() {
    return grundpreis;
  }


  /*
   * SETTER - METHODEN
   * 
   * 
   */

  public boolean setName(String name) {

    if (checkName(name) == true) {
      this.name = name;
      return true;
    } else {
      return false;
    }

  }

  public boolean setKategorie(String kategorie) {

    if (checkKategorie(kategorie) == true) {
      this.kategorie = kategorie;
      return true;
    } else {
      return false;
    }

  }

  public boolean setEan(String ean) {

    if (checkEan(ean) == true) {
      this.ean = ean;
      return true;
    } else {
      return false;
    }
  }

  public boolean setPlu(String plu) {

    if (checkPlu(plu) == true) {
      this.plu = plu;
      return true;
    } else {
      return false;
    }

  }

  public boolean setGewicht(String gewicht) {

    if (checkGewicht(gewicht) == true) {
      this.gewicht = gewicht;
      return true;
    } else {
      return false;
    }

  }

  public boolean setAnzahl(String anzahl) {

    if (checkAnzahl(anzahl) == true) {
      this.anzahl = anzahl;
      return true;
    } else {
      return false;
    }

  }

  public boolean setPreis(String preis) {

    if (checkPreis(preis) == true) {
      this.preis = preis;
      return true;
    } else {
      return false;
    }

  }

  public boolean setGrundpreis(String grundpreis) {

    if (checkGrundpreis(preis) == true) {
      this.grundpreis = grundpreis;
      return true;
    } else {
      return false;
    }
  }

  public boolean setEinheit(String einheit) {

    if (checkEinheit(einheit) == true) {
      this.einheit = einheit;
      return true;
    } else {
      return false;
    }
  }

}

