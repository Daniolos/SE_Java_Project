import java.util.ArrayList;

public class Einkaufsliste extends ArrayList<Artikel> {

  private float zwischensumme;

  public Einkaufsliste() {
    this.zwischensumme = 0;
  }

  public void addArtikel(Artikel artikel) {
    this.add(artikel);
    this.zwischensumme = zwischensumme + Float.parseFloat(artikel.getPreis);
    return;
  }

  public void eraseArtikel(Artikel artikel) {
    this.remove(artikel);
    this.zwischensumme = zwischensumme - Float.parseFloat(artikel.getPreis);
    return;
  }

  public void einkaufBeenden(ArrayList<Artikel> artikelliste) {
    int index;
    for (Artikel s : this) {
      index = artikelliste.indexOf(s);
      artikelliste.get(index).setAnzahl(artikelliste.get(index)-1);
      this.remove(s);
    }
    return;
  }

  public float getZwischensumme() {
    return zwischensumme;
  }

}
