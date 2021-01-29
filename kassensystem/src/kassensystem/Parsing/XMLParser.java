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

public class XMLParser
{
	private String xml;
	
	/**
	 * Konstruktor für diese Klasse. 
	 * Initialisiert das Attribut xml
	 * @param	String	xml
	 */
	public XMLParser (String xml)
	{
		this.xml = xml;
	}
	
	public String getXML ()
	{
		return xml;
	}
	
	/**
	 * Überprüft, ob das String Attribut xml dieser Klasse wirklich aus einer gültigen XML Datei stammt,
	 * indem es nach einem XML-Header sucht.
	 * @return	Boolean	Ist das String Attribut xml dieser Klasse eine gültige XML Datei?
	 */
	private Boolean isValidXML ()
	{
		
		// checks if document stars with <?xml ... ?>
		
		Pattern pattern = Pattern.compile("^<\\?xml(.*)\\?>");
		Matcher matcher = pattern.matcher(xml);
		
		return matcher.find();
	}
	
	// Version des XML-Formats aus Dokument lesen
	/**
	 * Version des XML-Formats aus Dokument lesen
	 * @param	String	//TODO
	 * @return	String	Wert, den Attribut im Header hat
	 */
	public String getAttributeFromHeader (String a)
	{
		if (!hasVersion()) return "";
		
		String header = getHeader();
		int indexOfOccurence = header.indexOf(a.toLowerCase() + "=\"") + a.length() + 2;
		return header.substring(indexOfOccurence, header.indexOf("\"", indexOfOccurence));
	}
	
	// Header aus XML holen
	/**
	 * Getter-Funktion für den Header der XML-Datei
	 * @return	String	Header der XML-Datei
	 */
	private String getHeader ()
	{
		return xml.substring(xml.indexOf("<?xml"), xml.indexOf("?>") + 2);
	}
	
	// check if has version attribute in header
	/**
	 * Überprüft, ob die XML-Datei eine Angabe zu ihrer Version im Header hat
	 * @return	Boolean	true, wenn der String "version" in dem XML-Header vorkommt, sonst false
	 */
	private Boolean hasVersion ()
	{
		if (!isValidXML()) return false;
		
		Pattern pattern = Pattern.compile("^<\\?xml(.)*version(.*)\\?>");
		Matcher matcher = pattern.matcher(xml);
		
		return matcher.find();
	}
	
	// check if tag exists and is properly formatted -> es fehlen noch attribute in der regex
	/**
	 * Diese Funktion überprüft, ob das Attribut dieser Klasse, das eine XML-Datei als String enthält,
	 * einen übergebenen tag enthält.
	 * @param	String	XML-tag (was zwischen < und > steht)
	 * @return	Boolean	true, wenn Attribut xml dieser Klasse den tag enthält, sonst false
	 */
	private Boolean hasTag (String tag)
	{
		Pattern pattern = Pattern.compile("<" + tag.toLowerCase() + "s*>" + "(s*|.*)*" + "</" + tag.toLowerCase() + ">");
		Matcher matcher = pattern.matcher(xml);
		
		return matcher.find();
	}
	
	/**
	 * Diese Funktion untersucht, ob ein übergebener String im XML-Format einen
	 * übergebenen tag enthält.
	 * @param	String	Der String im XML-Format, der untersucht werden soll
	 * @param	String	Der tag, nachdem gesucht werden soll
	 * @return	Boolean true, wenn der tag im string enthalten ist, sonst false
	 */
	private static Boolean hasTag (String string, String tag)
	{
		Pattern pattern = Pattern.compile("<" + tag.toLowerCase() + "s*>" + "(s*|.*)*" + "</" + tag.toLowerCase() + ">");
		Matcher matcher = pattern.matcher(string);
		
		return matcher.find();
	}
	
	//get tag from name, start with OUTER
	// würde fehler auslösen, wenn gleicher tag in der Hierarchie tiefer auftauchen würde
	// Signatur für XML
	/**
	 * Holt aus dem Attribut dieser Klasse "xml" heraus, was zwischen den XML-tags 
	 * steht, die es übergeben bekommt (Beispiel: this.xml = "<tag>hallo</tag>", übergebener tag = "tag", 
	 * Rückgabewert: "hallo")
	 * @param	String	XML-tag, nach dem gesucht werden soll.
	 * @return	String	Inhalt zwischen den tags in this.xml
	 */
	public String getChild (String tag)
	{
		if (!hasTag(tag)) return "";
		
		return xml.substring(xml.indexOf(tag) + tag.length() + 1, xml.indexOf("</" + tag)).trim();
	}
	
	// Signatur für jede Art von String
	/**
	 * Sucht in dem übergebenen String (im XML-Format) nach dem übergebenen XML-tag
	 * und gibt den Inhalt zwischen den tags als String zurück
	 * @param	String	im XML-Format; hier drin soll nach tag gesucht werden
	 * @param	String	tag, nach dem gesucht werden soll
	 * @return	String	Inhalt des Strings zwischen den tags
	 */
	public static String getChild (String string, String tag)
	{
		if (!hasTag(string, tag)) return "";
		
		return string.substring(string.indexOf(tag) + tag.length() + 1, string.indexOf("</" + tag)).trim();
	}
	
}