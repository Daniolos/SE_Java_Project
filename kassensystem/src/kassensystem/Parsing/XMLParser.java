package Parsing;

import java.util.regex.*;

public class XMLParser {
	private String xml;

	public XMLParser(String xml) {
		this.xml = xml.toLowerCase();
	}

	public String getXML() {
		return xml;
	}

	private Boolean isValidXML() {

		// checks if document stars with <?xml ... ?>

		Pattern pattern = Pattern.compile("^<\\?xml(.*)\\?>");
		Matcher matcher = pattern.matcher(xml);

		return matcher.find();
	}

	// Version des XML-Formats aus Dokument lesen

	public String getAttributeFromHeader(String a) {
		if (!hasVersion())
			return "";

		String header = getHeader();
		int indexOfOccurence = header.indexOf(a.toLowerCase() + "=\"") + a.length() + 2;
		return header.substring(indexOfOccurence, header.indexOf("\"", indexOfOccurence));
	}

	// Header aus XML holen

	private String getHeader() {
		return xml.substring(xml.indexOf("<?xml"), xml.indexOf("?>") + 2);
	}

	// check if has version attribute in header

	private Boolean hasVersion() {
		if (!isValidXML())
			return false;

		Pattern pattern = Pattern.compile("^<\\?xml(.)*version(.*)\\?>");
		Matcher matcher = pattern.matcher(xml);

		return matcher.find();
	}

	// Tag ist in der XML gespeichertes Attribut z.B. <ean>...</ean> -> "ean" ist tag
	// check if tag exists and is properly formatted -> es fehlen noch attribute in
	// der regex
	//TODO hat Bug
	private Boolean hasTag(String tag) {
		Pattern pattern = Pattern
				.compile("<" + tag.toLowerCase() + "s*>" + "(s*|.*)*" + "</" + tag.toLowerCase() + ">");
		Matcher matcher = pattern.matcher(xml);
		return matcher.find();
	}

	// get tag from name, start with OUTER
	// würde fehler auslösen, wenn gleicher tag in der Hierarchie tiefer auftauchen
	// würde
	// Signatur für XML

	// hier werden einzelne Attribute "tag" (zB Kategorie, Name, ...) ausgelesen
	// ausgelesen aus this.xml
	public String getChild(String tag) {
		//if (!hasTag(tag)) { return ""; }
		return xml.substring(xml.indexOf(tag) + tag.length() + 1, xml.indexOf("</" + tag)).trim();
	}

	// Signatur für jede Art von String
	// liest xml Daten an Stelle "tag" aus übergebenem String aus
	public static String getChild(String string, String tag) {

		return string.substring(string.indexOf(tag) + tag.length() + 1, string.indexOf("</" + tag)).trim();
	}

}