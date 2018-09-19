package convert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Convert {

	public static void main(String[] args) {
		Convert instance = new Convert();
		instance.run();
	}
	
	private void run() {
		
		String inputDirName = System.getProperty("user.dir") + "/input";
		File inputDir =  new File(inputDirName);
		if(!inputDir.exists()) {
			inputDir.mkdir();
			System.out.println("[INFO] The input file was missing, I just created one, please add the json files");
			return;
		}
		
		String outputDirName = System.getProperty("user.dir") + "/output";
		File outputDir =  new File(outputDirName);
		if(!outputDir.exists()) {
			outputDir.mkdir();
		}

		List<String> csvFiles = Utils.getAllFilesFromADirectoryByExtenstion(inputDirName,".csv");

		for(String csvFile: csvFiles) {
			List<String> fileContent = Utils.readListFromFile(csvFile);

			for(int i=1; i < fileContent.size(); i++) {
				String line = fileContent.get(i);

				String[] split = line.split(",");
				if(split.length == 19) {
					
					String MsgId = split[0];
					String CreDtTm = split[1];
					
					String NbOfTxs = split[2];
					String InitgPty = split[3];
					
					String PmtInfId = split[4];
					String PmtMtd = split[5];
					String NbOfTxs2 = split[6];
					String CtrlSum = split[7];
					
					String ReqdExctnDt = split[8];
					
					String DbtrNm = split[9];
					String DbtrAcctIBAN = split[10];
					String DbtrAgtBIC = split[11];
					
					// EndToEnd reference
					String EndToEndId = split[12];
					//Amount
					String InstdAmt = split[13];
					String FinInstnIdBIC = split[14];
					
					String CdtrNm = split[15];
					String CdtrCtry = split[16];
					String CdtrAcctIBAN = split[17];
					
					String RmtInf = split[18];
					
					

				}
			}

		}

	    Document doc = DocumentHelper.createDocument();
		
		// Document xmlns="urn:iso:std:iso:20022:tech:xsd:pain.001.001.03" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:pain.001.001.03 pain.001.001.03.xsd">
		Element rootElement = doc.addElement("Document");
		rootElement.addAttribute("xmlns", "urn:iso:std:iso:20022:tech:xsd:pain.001.001.03");
		rootElement.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		rootElement.addAttribute("xsi:schemaLocation", "urn:iso:std:iso:20022:tech:xsd:pain.001.001.03 pain.001.001.03.xsd");
		
    	Element cstmrCdtTrfInitnElement = rootElement.addElement("CstmrCdtTrfInitn");
    	
    	Element GrpHdrElement = cstmrCdtTrfInitnElement.addElement("GrpHdr");
    	
    	Element PmtInfElement = cstmrCdtTrfInitnElement.addElement("GrpHdr");
    	
    	try {
			makeFile(outputDirName, doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void makeFile(String outputDirName, Document doc) throws IOException {
		FileOutputStream fos = new FileOutputStream(outputDirName + "/person.xml");
		// Pretty print the document to System.out
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer;
		writer = new XMLWriter(fos, format );
		writer.write( doc );
	}

}
