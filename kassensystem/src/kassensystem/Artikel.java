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
    if (ean.matches("[0-9]+") = false) {
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
    if (ean.matches("[0-9]+") = false) {
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

    // regex erkennt floats und integers
    if (gewicht.matches("[0-9]*,?[0-9]+") == false) {
      return false;
    }
    
    if ((einheit == "Gramm" & 1 <= Float.parseFloat(gewicht) <= 100000) == false) {
      return false;
    }

    if ((einheit == "Kilo" & 0,1 <= Float.parseFloat(gewicht) <= 100) == false) {
      return false;
    }
    
    if ((einheit == "Liter" & 0,1 <= Float.parseFloat(gewicht) <= 100) == false) {
      return false;
    }
    
    if ((einheit == "ml" & 1 <= Float.parseFloat(gewicht) <= 10000) == false) {
      return false;
    }
    
    return true;
    
  }

  public boolean checkAnzahl(String anzahl) {
    
   if (anzahl.matches("[1-9]+") == false) {
     if (anzahl == "n") {
       return true;
     }
     return false;
   }
   
   if ( 1 <= Integer.parseInt(anzahl) <= 1000) == false {
     return false;
   }
   
   return true;
   
  }

  public boolean checkPreis(String preis) {
    // preis überprüfen
    
    if (preis.matches("[0-9]*,?[0-9]+") == false) {
      return false;
    }
    
    if (0,001 <= Float.parseFloat(preis) <= 100000) {
      return false;
    }
    
    return true;
    
  }

  public boolean checkGrundpreis(String grundpreis) {
    
    if (grundpreis.matches("[0-9]*,?[0-9]+") == false) {
      return false;
    }
    
    if (0,001 <= Float.parseFloat(grundpreis) <= 100000) {
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
      String anzahl, String preis) {

    if (checkName(name) == true) {
      this.name = name;
    } else {
      return false;
    }

    if (checkKategorie(kategorie) == true) {
      this.kategorie = kategorie;
    } else {
      return false;
    }

    if (checkEan(ean) == true) {
      this.ean = ean;
    } else {
      return false;
    }

    if (checkPlu(plu) == true) {
      this.plu = plu;
    } else {
      return false;
    }

    if (checkGewicht(gewicht) == true) {
      this.gewicht = gewicht;
    } else {
      return false;
    }

    if (checkAnzahl(anzahl) == true) {
      this.anzahl = anzahl;
    } else {
      return false;
    }

    if (checkPreis(preis) == true) {
      this.preis = preis;
    } else {
      return false;
    }

    if (checkGrundpreis(preis) == true) {
      this.grundpreis = grundpreis;
    } else {
      return false;
    }

    if (checkEinheit(einheit) == true) {
      this.einheit == einheit;
    } else {
      return false;
    }
    
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
    
    if(checkEinheit(einheit) == true) {
      this.einheit == einheit;
      return true;
    } else {
      return false;
    }
  }

}

