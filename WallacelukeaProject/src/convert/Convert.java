package convert;

import java.io.File;

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

public class Convert {

	public static void main(String[] args) {
		Convert instance = new Convert();
		instance.run();
	}
	
	private void run() {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Document doc = docBuilder.newDocument();
		
		// Document xmlns="urn:iso:std:iso:20022:tech:xsd:pain.001.001.03" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:pain.001.001.03 pain.001.001.03.xsd">
		Element rootElement = doc.createElement("Document");
		rootElement.setAttribute("xmlns", "urn:iso:std:iso:20022:tech:xsd:pain.001.001.03");
		rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		rootElement.setAttribute("xsi:schemaLocation", "urn:iso:std:iso:20022:tech:xsd:pain.001.001.03 pain.001.001.03.xsd");
		doc.appendChild(rootElement);
		
    	Element cstmrCdtTrfInitnElement = doc.createElement("CstmrCdtTrfInitn");
    	rootElement.appendChild(cstmrCdtTrfInitnElement);


	}

	public void makeFile() {
		try {
		  
		    
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
