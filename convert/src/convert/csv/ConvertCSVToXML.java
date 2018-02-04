package convert.csv;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import utils.Utils;

public class ConvertCSVToXML {
	
	List<String> fileAsList = Utils.readListFromFile("c:\\My Stuffs\\java_develop\\file\\import_issues_ojs.csv");

	public static void main(String[] args) {
		ConvertCSVToXML instance = new ConvertCSVToXML();
		instance.run();
	}
	
	private void run() {
		
		makeFile();
	}
	
	public void makeFile() {
		try {

		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		    Document doc = docBuilder.newDocument();
		    
		    Element rootElement = doc.createElement("issues");
		    doc.appendChild(rootElement);
		    
		    for(int i = 1; i < fileAsList.size(); i++) {
		    	String line = fileAsList.get(i);
		    	
		    	String[] split = line.split(";");
		    	Element issueElement = doc.createElement("issue");
		    	issueElement.setAttribute("current", "true");
		    	issueElement.setAttribute("identification", "num_vol_year_title");
		    	issueElement.setAttribute("published", "true");
		    	rootElement.appendChild(issueElement);
		    	
		    	Element titleElement = doc.createElement("title");
		    	titleElement.setAttribute("locale", "da_DK");
		    	titleElement.appendChild(doc.createTextNode(split[5]));
		    	issueElement.appendChild(titleElement);
		    	
		    	Element descriptionElement = doc.createElement("description");
		    	descriptionElement.setAttribute("locale", "da_DK");
		    	descriptionElement.appendChild(doc.createTextNode(""));
		    	issueElement.appendChild(descriptionElement);
		    	
		    	Element volumeElement = doc.createElement("volume");
		    	volumeElement.appendChild(doc.createTextNode(split[2]));
		    	issueElement.appendChild(volumeElement);
		    	
		    	Element numberElement = doc.createElement("number");
		    	numberElement.appendChild(doc.createTextNode(split[3]));
		    	issueElement.appendChild(numberElement);
		    	
		    	Element yearElement = doc.createElement("year");
		    	yearElement.appendChild(doc.createTextNode(split[1]));
		    	issueElement.appendChild(yearElement);
		    	
		    	Element coverElement = doc.createElement("cover");
		    	coverElement.setAttribute("locale", "da_DK");
		    	coverElement.appendChild(doc.createTextNode(""));
		    	issueElement.appendChild(coverElement);
		    	
		    	Element dateElement = doc.createElement("date_published");
		    	dateElement.appendChild(doc.createTextNode(split[4]));
		    	issueElement.appendChild(dateElement);
		    	
		    	Element openElement = doc.createElement("open_access");
		    	issueElement.appendChild(openElement);
		    }
		    
		    TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer();
		    DOMSource source = new DOMSource(doc);
		    StreamResult result = new StreamResult(new File("c:\\\\My Stuffs\\\\java_develop\\\\file\\\\issues_new.xml"));
		    transformer.transform(source, result);
		    System.out.println("File saved!");
		    
		  } catch (ParserConfigurationException pce) {
		    pce.printStackTrace();
		  } catch (TransformerException tfe) {
		    tfe.printStackTrace();}
    }

}
