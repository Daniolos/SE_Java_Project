package Parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.regex.*;

/**
 * 
 * @author Felix Schulz
 *
 */

public class XMLParser {
	private String xml;

	/**
	 * Konstruktor für diese Klasse. Initialisiert das Attribut xml
	 * 
	 * @param xml übergebener String im xml.Format
	 */
	public XMLParser(String xml) {
		this.xml = xml;
	}

	public String getXML() {
		return xml;
	}

	/**
	 * Diese Funktion überprüft, ob das Attribut dieser Klasse, das eine XML-Datei
	 * als String enthält, einen übergebenen tag enthält.
	 * 
	 * @param tag XML-tag (was zwischen < und > steht)
	 * @return true, wenn Attribut xml dieser Klasse den tag enthält, sonst false
	 */
	private Boolean hasTag(String tag) {
		Pattern pattern = Pattern
				.compile("<" + tag.toLowerCase() + "s*>" + "(s*|.*)*" + "</" + tag.toLowerCase() + ">");
		Matcher matcher = pattern.matcher(xml);

		return matcher.find();
	}

	/**
	 * Diese Funktion untersucht, ob ein übergebener String im XML-Format einen
	 * übergebenen tag enthält.
	 * 
	 * @param string Der String im XML-Format, der untersucht werden soll
	 * @param tag    Der tag, nachdem gesucht werden soll
	 * @return true, wenn der tag im string enthalten ist, sonst false
	 */
	private static Boolean hasTag(String string, String tag) {
		Pattern pattern = Pattern
				.compile("<" + tag.toLowerCase() + "s*>" + "(s*|.*)*" + "</" + tag.toLowerCase() + ">");
		Matcher matcher = pattern.matcher(string);

		return matcher.find();
	}

	/**
	 * Holt aus dem Attribut dieser Klasse "xml" heraus, was zwischen den XML-tags
	 * steht, die es übergeben bekommt (Beispiel: this.xml = "<tag>hallo</tag>",
	 * übergebener tag = "tag", Rückgabewert: "hallo")
	 * 
	 * @param tag XML-tag, nach dem gesucht werden soll.
	 * @return Inhalt zwischen den tags in this.xml
	 */
	public String getChild(String tag) {
		if (!hasTag(tag))
			return "";

		return xml.substring(xml.indexOf(tag) + tag.length() + 1, xml.indexOf("</" + tag)).trim();
	}

	/**
	 * Sucht in dem übergebenen String (im XML-Format) nach dem übergebenen XML-tag
	 * und gibt den Inhalt zwischen den tags als String zurück
	 * 
	 * @param string String im XML-Format; hier drin soll nach tag gesucht werden
	 * @param tag    String, nach dem gesucht werden soll
	 * @return Inhalt des Strings zwischen den tags
	 */
	public static String getChild(String string, String tag) {
		if (!hasTag(string, tag))
			return "";

		return string.substring(string.indexOf(tag) + tag.length() + 1, string.indexOf("</" + tag)).trim();
	}

}
