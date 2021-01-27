package Database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Formatter;

import Parsing.Artikel;

public class DatenSchreiber 
{
	private Lager lager;
	private String ErsteZeile;
	private String articles;
	private String xml;
	private String categories;
	
	
	public DatenSchreiber()
	{
		setErsteZeile();
		articles = "<articles></articles>";
		xml = ErsteZeile + articles;
	}
	
	public DatenSchreiber(Lager lager)
	{
		this.lager = lager;
		setErsteZeile();
		setArticles();
		xml = ErsteZeile + articles;
	}
	
	private void setErsteZeile()
	{
		ErsteZeile = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	}
	
	private void setArticles()
	{
		String Cut = "<articles>\n";
		
		for (Artikel article : lager.getArtikel())
		{
			Cut += ArtikelXML(article);
		}
		
		Cut += "</articles>\n";
		articles = Cut;
	}
	
	private String ArtikelXML(Artikel article)
	{
		String xmlString = "\t<article>\n";
		xmlString += "\t\t<name>" + article.getName() + "</name>\n";
		xmlString += "\t\t<ean>" + article.getEan() + "</ean>\n";
		xmlString += "\t\t<kategorie>" + article.getKategorie() + "</kategorie>\n";
		xmlString += "\t\t<einheit>" + article.getEinheit() + "</einheit>\n";
		xmlString += "\t\t<plu>" + article.getPlu() + "</plu>\n";
		xmlString += "\t\t<gewicht>" + article.getGewicht() + "</gewicht>\n";
		xmlString += "\t\t<anzahl>" + article.getAnzahl() + "</anzahl>\n";
		xmlString += "\t\t<preis>" + article.getPreis() + "</preis>\n";
		xmlString += "\t\t<grundpreis>" + article.getGrundpreis() + "</grundpreis>\n";
		xmlString += "\t</article>\n";
		return xmlString;
	}
	
	public void Schreiben()
	{
		try
		{
			BufferedWriter dbData = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "/src/Database/database.xml"), "UTF-8"));
			dbData.write(xml);
			dbData.close();
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
