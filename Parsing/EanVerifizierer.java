package Parsing;

/**
 * 
 * @author Felix Schulz, Philomena Moek
 *
 */

public class EanVerifizierer {

	private String ean;
	private String formatierteEan;

	/**
	 * Konstruktor für den EanVerifizierer Der übergebene String wird this.ean
	 * zugewiesen. Dann wird er formatiert zu EAN-13 und unter this.formatierteEan
	 * gespeichert.
	 * 
	 * @param ean bekommt String mit EAN oder PLU übergeben
	 */
	public EanVerifizierer(String ean) {
		setEan(ean);
		setformatierteEan();
	}

	/**
	 * Überprüft, ob eine EAN oder PLU zulässig ist.
	 * 
	 * @return true, wenn this.ean eine valide EAN (oder PLU) ist, sonst falsch
	 */
	public Boolean valideEan() {
		return (ean.length() == 8 || ean.length() == 13 || ean.length() == 4
				|| (ean.length() == 5 && String.valueOf(ean.charAt(0)).equals("9"))) && ean.matches("[0-9]+");
	}

	/**
	 * Setter-Funktion für this.ean
	 * 
	 * @param ean EAN die this.ean zugewiesen werden soll
	 */
	private void setEan(String ean) {
		this.ean = ean;
	}

	/**
	 * Überprüft, ob this.ean eine gültige EAN oder PLU ist. Wenn ja, wird sie
	 * standartisiert this.formatierteEan zugewiesen, wenn nein, wird
	 * this.formatierteEan der leere String zugewiesen.
	 */
	private void setformatierteEan() {

		if (valideEan()) {
			String left = "";
			for (int i = 0; i < 13 - ean.length(); i++) {
				left += "0";
			}
			formatierteEan = left + ean;
		} else {
			formatierteEan = "";
		}
	}

	/**
	 * Getter-Funktion für this.ean
	 * 
	 * @return unformatierte, nicht geprüfte EAN
	 */
	public String getEan() {
		return ean;
	}

	/**
	 * Getter-Funktion für this.formatierteEan
	 * 
	 * @return formatierte, valide EAN
	 */
	public String getformatierteEan() {
		return formatierteEan;
	}
}
