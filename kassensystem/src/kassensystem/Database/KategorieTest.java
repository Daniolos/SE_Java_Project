package Database;

import java.util.Arrays;

import Parsing.XMLParser;

public class KategorieTest {

    public static void main(String[] args) {
        DatenLeser bla = new DatenLeser();
	    XMLParser xml = new XMLParser(bla.getData());
	    String article = xml.getChild("articles");
	    Lager lager = new Lager(xml.getXML());
        String[][] Array = lager.toStringArray();
        System.out.println(Arrays.deepToString(Array));
}
    }
        
    }
 
