package Database;

import java.util.LinkedList;
//import Parsing.EanVerifizierer;
import Parsing.XMLParser;
import Parsing.Artikel;


public class Lager 
{
	private String articleMarkup;
	private LinkedList<Artikel> articles;
	
	public Lager (String xml)
	{
		articles = new LinkedList<Artikel>();	
		setAllArticlesFromString(xml);
		fillLager();
		System.out.println(xml);
		System.out.println(articleMarkup);
	}
	
	// xml contains whole xml as String
	
	private void setAllArticlesFromString(String xml)
	{
		articleMarkup = XMLParser.getChild(xml, "articles");
	}
	
	private void fillLager ()
	{
		String placeholder = "yqzxdf";
		String replacedXMLString = articleMarkup.replaceAll("</article>\\s*<article>", "</article>" + placeholder + "<article>");
		String [] art = replacedXMLString.split(placeholder);
		for (String a : art)
		{
			if (!a.trim().equals(""))
			{				
				articles.add(new Artikel(a));
			}
		}
	}
	
	public LinkedList<Artikel> getArtikel()
	{
		return articles;
	}
		
	public void ArtikelHinzufuegen(Artikel article)
	{
		articles.add(article); //Was passiert, wenn Artikel leer sind, wenn Artikel doppelt sind usw. (noch hinzuf.)
	}
	
	public void print()
	{
		for (Artikel article : articles)
		{
			article.print();
		}
	}
}