package Parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.regex.*;

public class XMLParser
{
	private String xml;
		
	public XMLParser (String xml)
	{
		this.xml = xml;
	}
	
	public String getXML ()
	{
		return xml;
	}
	
	private Boolean isValidXML ()
	{
		
		// checks if document stars with <?xml ... ?>
		
		Pattern pattern = Pattern.compile("^<\\?xml(.*)\\?>");
		Matcher matcher = pattern.matcher(xml);
		
		return matcher.find();
	}
	
	// Version des XML-Formats aus Dokument lesen
	
	public String getAttributeFromHeader (String a)
	{
		if (!hasVersion()) return "";
		
		String header = getHeader();
		int indexOfOccurence = header.indexOf(a.toLowerCase() + "=\"") + a.length() + 2;
		return header.substring(indexOfOccurence, header.indexOf("\"", indexOfOccurence));
	}
	
	// Header aus XML holen
	
	private String getHeader ()
	{
		return xml.substring(xml.indexOf("<?xml"), xml.indexOf("?>") + 2);
	}
	
	// check if has version attribute in header
	
	private Boolean hasVersion ()
	{
		if (!isValidXML()) return false;
		
		Pattern pattern = Pattern.compile("^<\\?xml(.)*version(.*)\\?>");
		Matcher matcher = pattern.matcher(xml);
		
		return matcher.find();
	}
	
	// check if tag exists and is properly formatted -> es fehlen noch attribute in der regex
	
	private Boolean hasTag (String tag)
	{
		Pattern pattern = Pattern.compile("<" + tag.toLowerCase() + "s*>" + "(s*|.*)*" + "</" + tag.toLowerCase() + ">");
		Matcher matcher = pattern.matcher(xml);
		
		return matcher.find();
	}
	
	private static Boolean hasTag (String string, String tag)
	{
		Pattern pattern = Pattern.compile("<" + tag.toLowerCase() + "s*>" + "(s*|.*)*" + "</" + tag.toLowerCase() + ">");
		Matcher matcher = pattern.matcher(string);
		
		return matcher.find();
	}
	
	//get tag from name, start with OUTER
	// w�rde fehler ausl�sen, wenn gleicher tag in der Hierarchie tiefer auftauchen w�rde
	// Signatur f�r XML
	
	public String getChild (String tag)
	{
		if (!hasTag(tag)) return "";
		
		return xml.substring(xml.indexOf(tag) + tag.length() + 1, xml.indexOf("</" + tag)).trim();
	}
	
	// Signatur f�r jede Art von String
	
	public static String getChild (String string, String tag)
	{
		if (!hasTag(string, tag)) return "";
		
		return string.substring(string.indexOf(tag) + tag.length() + 1, string.indexOf("</" + tag)).trim();
	}
	
}