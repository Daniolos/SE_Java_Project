package Controller;

abstract public class KonvertierController 
{
	protected int stringZuInt(String value) 
	{
		int intWert = 0;
		try
		{
			intWert = Integer.parseInt(value);
		}
		
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		return intWert;
	}
	
	protected float stringZuFloat(String value) 
	{
		float floatWert = 0;
		try
		{
			floatWert = Float.parseFloat(value.replace(",", "."));
		}
		
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		return floatWert;
	}
}
