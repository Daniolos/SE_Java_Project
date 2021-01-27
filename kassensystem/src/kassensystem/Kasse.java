import java.io.File;

import java.io.FileNotFoundException;
import java.util.Scanner;

import Database.DatenSchreiber;
import Database.Lager;
import Parsing.Artikel;
import Parsing.XMLParser;

//liest Artikel aus XML

public class Kasse {

	public static void main(String[] args) {
		try {
			//TODO change file structure later
			File dbData = new File(System.getProperty("user.home") + "/Dokumente/Uni/SE/Projekt/SE_Java_Project/kassensystem/src/kassensystem/Database/database.xml");
			Scanner myReader = new Scanner(dbData);
			String data = "";

			while (myReader.hasNextLine()) {
				data += myReader.nextLine();
			}
			XMLParser xml = new XMLParser(data);		// data ist xml Datei als ein String, xml ist Instanz von XMLParser
			String article = xml.getChild("articles");	// entfernt Kopf der XML Datei

			
			//new Artikel() bekommt string EINES Artikels übergeben
			Artikel a = new Artikel(article);

			
			Lager b = new Lager(xml.getXML());
			b.print();
			System.out.println("Länge der Artikelliste: " + b.getArtikel().size());
			System.out.println("Länge der Kategorienliste: " + b.getKategorien().size());
			System.out.println("Hier kommen die Kategorien: ");
			b.getKategorien().printer();
			

			Artikel c = new Artikel("Schoko", "0000000000004", "S��es", "g", "4444", "200", "300", "1,49", "7,45");
			b.ArtikelHinzufuegen(c);
			b.print();
			DatenSchreiber Speicher = new DatenSchreiber(b);
			Speicher.Schreiben();
			myReader.close();

			System.out.println(Float.parseFloat("1.49"));

		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

}