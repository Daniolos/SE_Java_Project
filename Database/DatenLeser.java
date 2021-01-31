package Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 
 * @author Felix Schulz
 *
 */

public class DatenLeser {
	private String data = "";

	/**
	 * Getter-Funktion für this.data
	 * 
	 * @return this.data
	 */
	public String getData() {
		return data;
	}

	/**
	 * Konstruktor für die Klasse Die XML-Datei wird nach this.data (als ein String)
	 * eingelesen. Existiert noch keine Datei, wird eine erstellt. this.data hat
	 * dann den Wert "" (leerer String)
	 */
	public DatenLeser() {
		Lesen();
	}

	/**
	 * Liest gesamte XML-Datei "database.xml" als einen einzigen String ein und
	 * speichert sie in this.data. Existiert noch keine XML-Datei mit diesem Namen,
	 * wird eine erstellt.
	 */
	public void Lesen() {
		try {
			File dbData = new File(System.getProperty("user.dir") + "/src/Database/database.xml");
			Scanner myReader = new Scanner(new FileInputStream(dbData), StandardCharsets.UTF_8.name());

			while (myReader.hasNextLine()) {
				data += myReader.nextLine();
			}
			myReader.close();
		}

		catch (FileNotFoundException e) {
			new DatenSchreiber().Schreiben();
		}
	}
}
