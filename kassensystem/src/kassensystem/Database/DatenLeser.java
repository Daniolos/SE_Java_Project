package Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DatenLeser 
{
	private String data = "";
	
	public String getData()
	{
		return data;
	}
	
	public DatenLeser()
	{
		Lesen();
	}
	
	public void Lesen()
	{
		try
		{
			File dbData = new File(System.getProperty("user.dir") + "/src/Database/database.xml");
			Scanner myReader = new Scanner(new FileInputStream(dbData), StandardCharsets.UTF_8.name());
			
			while (myReader.hasNextLine())
			{
				data += myReader.nextLine();
			}
			myReader.close(); 
		}
		
		catch (FileNotFoundException e)
		{
			new DatenSchreiber().Schreiben();
			System.out.println("Neue Datenbank wurde erstellt");
		}	
	}
}
