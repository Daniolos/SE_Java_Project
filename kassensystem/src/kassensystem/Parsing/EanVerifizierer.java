package Parsing;

public class EanVerifizierer 
{ // Extra Verifizierer für EAN, da EAN an mehreren Stellen benötigt wird: Beim Laden der XML, Anlegen von Artikeln, Konfiguration der PLU
	
	private String ean;
	private String formatierteEan;
	
	
	public EanVerifizierer (String ean)
	{
		setEan(ean);
		setformatierteEan();
	}
	
	public Boolean valideEan()
	{
		return (ean.length() == 8 || ean.length() == 13 || ean.length() == 4 || (ean.length() == 5 && String.valueOf(ean.charAt(0)).equals("9"))) && ean.matches("[0-9]+");
	}
		
	private void setEan(String ean)
	{
		this.ean = ean;
	}
	
	private void setformatierteEan()
	{
//		String Nullen = "00000000";
//		String Null = "";
//		Null = ean.length() == 4 ? "0" : "";
//		formatierteEan = valideEan() ? ean.length() < 8 ? Nullen + Null + ean : ean : "";
		
		if (valideEan())
			{
				String left = "";
				for (int i = 0; i < 13 - ean.length(); i++)
				{
					left += "0";
				}
				formatierteEan = left + ean;	
			}
			else
			{
				formatierteEan = "";
			}
	}
	
	public String getEan()
	{
		return ean;
	}
	
	public String getformatierteEan()
	{
		return formatierteEan;
	}
}
