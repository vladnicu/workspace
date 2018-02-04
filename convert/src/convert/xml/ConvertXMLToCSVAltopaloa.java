 package convert.xml;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import utils.Consts;
import utils.Utils;

public class ConvertXMLToCSVAltopaloa {
	
	   
    String SEPARATOR = "<";
    
    private Document mDocument = null;
    private Element mRootElement = null;
    
    private List<String> ouputList = new ArrayList<String>();

	public static void main(String[] args) {
		ConvertXMLToCSVAltopaloa instance = new ConvertXMLToCSVAltopaloa() ;
		instance.run();

	}
	
	private boolean readDocument() {
		try {

			String newNode = Utils.readFile("C:\\My Stuffs\\java_develop\\file\\dwitzel\\homersvp.xml");
			SAXReader reader = new SAXReader();
			mDocument  = reader.read(new StringReader(newNode));
			mRootElement = mDocument.getRootElement();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	    
	
	
	private void run() {
		ouputList.add("Score" + SEPARATOR + "Applicant" + SEPARATOR + "Applicant Sort" + SEPARATOR + "Brief" + SEPARATOR + "City" + SEPARATOR + "Date Rcpt" + SEPARATOR + "Disseminated" + SEPARATOR + "ExParte" + SEPARATOR + "Id"
				+ SEPARATOR + "Modified" + SEPARATOR + "Pages" + SEPARATOR + "Proceeding" + SEPARATOR + "Reg Flex Analysis" + SEPARATOR + "Small Business Impact" + SEPARATOR + "State Cd" + SEPARATOR + "Submission Type" + SEPARATOR + "Text" 
				+ SEPARATOR + "Viewing Status" + SEPARATOR + "Zip");
		readDocument();

		Element firstElement = mRootElement.element("result");
		List<Element> docs = firstElement.elements();

		for(Element elem : docs) {
			if(!elem.getName().equals("doc")) {
				System.out.println("Nu se numeste doc");
				continue;
			}

			List<Element> docsChilds = elem.elements();

			String score = null;
			String applicant = null;
			String applicant_sort = null;
			String brief = null;
			String city = null;
			String dateRcpt = null;
			String disseminated = null;
			String exParte = null;
			String id = null;
			String modified = null;
			String pages = null;
			String proceeding = null;
			String regFlexAnalysis = null;
			String smallBusinessImpact = null;
			String stateCd = null;
			String submissionType = null;
			String text = null;
			String viewingStatus = null;
			String zip = null;
			
			for(Element child : docsChilds) {
				String nameValue = child.attributeValue("name");
				if(nameValue.equals("score")) {
					score = child.getText();
					continue;
				}

				if(nameValue.equals("applicant")) {
					Element subChildElement = (Element) child.elements().get(0);
					applicant = subChildElement.getText();
					continue;
				} else if(nameValue.equals("applicant_sort")) {
					Element subChildElement = (Element) child.elements().get(0);
					applicant_sort = subChildElement.getText();
					continue;
				} else if(nameValue.equals("brief")) {
					Element subChildElement = (Element) child.elements().get(0);
					brief = subChildElement.getText();
					continue;
				} else if(nameValue.equals("city")) {
					Element subChildElement = (Element) child.elements().get(0);
					city = subChildElement.getText();
					continue;
				} else if(nameValue.equals("dateRcpt")) {
					Element subChildElement = (Element) child.elements().get(0);
					dateRcpt = subChildElement.getText();
					continue;
				} else if(nameValue.equals("disseminated")) {
					Element subChildElement = (Element) child.elements().get(0);
					disseminated = subChildElement.getText();
					continue;
				} else if(nameValue.equals("exParte")) {
					Element subChildElement = (Element) child.elements().get(0);
					exParte = subChildElement.getText();
					continue;
				} else if(nameValue.equals("id")) {
					Element subChildElement = (Element) child.elements().get(0);
					id = subChildElement.getText();
					continue;
				} else if(nameValue.equals("modified")) {
					Element subChildElement = (Element) child.elements().get(0);
					modified = subChildElement.getText();
					continue;
				} else if(nameValue.equals("pages")) {
					Element subChildElement = (Element) child.elements().get(0);
					pages = subChildElement.getText();
					continue;
				} else if(nameValue.equals("proceeding")) {
					Element subChildElement = (Element) child.elements().get(0);
					proceeding = subChildElement.getText();
					continue;
				} else if(nameValue.equals("regFlexAnalysis")) {
					Element subChildElement = (Element) child.elements().get(0);
					regFlexAnalysis = subChildElement.getText();
					continue;
				} else if(nameValue.equals("smallBusinessImpact")) {
					Element subChildElement = (Element) child.elements().get(0);
					smallBusinessImpact = subChildElement.getText();
					continue;
				} else if(nameValue.equals("stateCd")) {
					Element subChildElement = (Element) child.elements().get(0);
					stateCd = subChildElement.getText();
					continue;
				} else if(nameValue.equals("submissionType")) {
					Element subChildElement = (Element) child.elements().get(0);
					submissionType = subChildElement.getText();
					continue;
				} else if(nameValue.equals("text")) {
					Element subChildElement = (Element) child.elements().get(0);
					text = subChildElement.getText();
					if(text.contains("\r")) {
						text = text.replace("\r", "");
					}
					continue;
				} else if(nameValue.equals("viewingStatus")) {
					Element subChildElement = (Element) child.elements().get(0);
					viewingStatus = subChildElement.getText();
					continue;
				} else if(nameValue.equals("zip")) {
					Element subChildElement = (Element) child.elements().get(0);
					zip = subChildElement.getText();
					continue;
				}
			}
			ouputList.add(score + SEPARATOR + applicant + SEPARATOR + applicant_sort + SEPARATOR + brief + SEPARATOR + city + SEPARATOR + dateRcpt + SEPARATOR + disseminated + SEPARATOR + exParte + SEPARATOR + id
					+ SEPARATOR + modified + SEPARATOR + pages + SEPARATOR + proceeding + SEPARATOR + regFlexAnalysis + SEPARATOR + smallBusinessImpact + SEPARATOR + stateCd + SEPARATOR + submissionType + SEPARATOR + text 
					+ SEPARATOR + viewingStatus + SEPARATOR + zip);
		}

		Utils.writeListToFile("C:\\My Stuffs\\java_develop\\file\\altopaloa\\14-28-RAW-Solr-5.csv", Consts.ENCODING_UTF8, ouputList, Consts.LINE_SEPARATOR_LINUX);


	}

}
