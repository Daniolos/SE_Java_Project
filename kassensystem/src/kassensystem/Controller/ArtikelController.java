package Controller;

import Parsing.Artikel;

public class ArtikelController extends KonvertierController
{
	private Artikel article;
	
	public ArtikelController(Artikel article)
	{	
		this.article = article;
	}
	
	public ArtikelController(String name, String ean, String kategorie, String einheit, String preiseinheit, String gewicht, String anzahl, String preis, String grundpreis)
	{	
		article = new Artikel(name, ean, kategorie, einheit, preiseinheit, gewicht, anzahl, preis, grundpreis);
	}
	
	public void update(String name, String ean, String kategorie, String einheit, String preiseinheit, String gewicht, String anzahl, String preis, String grundpreis)
	{
		article.update(name, ean, kategorie, einheit, preiseinheit, gewicht, anzahl, preis, grundpreis);
	}
	
	public Artikel getArtikel()
	{
		return article;
	}
	
	public void print() {
		System.out.println(article.getName() + ", " + article.getEan() + ", " + article.getKategorie() + ", " + article.getEinheit() 
				+ ", " + article.getPreiseinheit() + ", " + article.getGewicht() + ", " + article.getAnzahl() + ", " + article.getPreis() 
				+ ", " + article.getGrundpreis());
	}
}
