package convert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.opencsv.CSVReader;


public class Convert {

	public static void main(String[] args) {
		Convert instance = new Convert();
		instance.run();
		System.out.println("[INFO] Done!");
	}
	
	private void run() {
		
		String inputDirName = System.getProperty("user.dir") + "/input";
		File inputDir =  new File(inputDirName);
		if(!inputDir.exists()) {
			inputDir.mkdir();
			System.out.println("[INFO] The input file was missing, I just created one, please add the csv files");
			return;
		}
		
		String outputDirName = System.getProperty("user.dir") + "/output";
		File outputDir =  new File(outputDirName);
		if(!outputDir.exists()) {
			System.out.println("[INFO] The output file was missing, I just created one");
			outputDir.mkdir();
		}

		System.out.println("[INFO] get the CSV files");
		List<String> csvFiles = Utils.getAllFilesFromADirectoryByExtenstion(inputDirName,".csv");

		for(String csvFile: csvFiles) {
			System.out.println("[INFO] dealing with file: " + csvFile);
			CSVReader csvReader;
			try {
				csvReader = new CSVReader(new FileReader(csvFile));
				List<String[]> allData = csvReader.readAll();

				for(int i = 1; i < allData.size(); i++){
					
					String[] split = allData.get(i);

					String MsgId = split[0];
					String CreDtTm = split[1];
					String NbOfTxs = split[2];
					
					String InitgPtyNm = split[3];
					
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

					Document doc = DocumentHelper.createDocument();

					//  xmlns="urn:iso:std:iso:20022:tech:xsd:pain.001.001.03" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:iso:std:iso:20022:tech:xsd:pain.001.001.03 pain.001.001.03.xsd">
					Element rootElement = doc.addElement("Document");
					rootElement.addAttribute("xmlns", "urn:iso:std:iso:20022:tech:xsd:pain.001.001.03");
					rootElement.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
					rootElement.addAttribute("xsi:schemaLocation", "urn:iso:std:iso:20022:tech:xsd:pain.001.001.03 pain.001.001.03.xsd");

					Element cstmrCdtTrfInitnElement = rootElement.addElement("CstmrCdtTrfInitn");

					Element GrpHdrElement = cstmrCdtTrfInitnElement.addElement("GrpHdr");
					Element MsgIdElement = GrpHdrElement.addElement("MsgId").addText(MsgId);
					Element CreDtTmElement = GrpHdrElement.addElement("CreDtTm").addText(CreDtTm);
					Element NbOfTxsElement = GrpHdrElement.addElement("NbOfTxs").addText(NbOfTxs);
					
					Element InitgPtyElement = GrpHdrElement.addElement("InitgPty");
					Element NmElement = InitgPtyElement.addElement("Nm").addText(InitgPtyNm);
					
					Element PmtInfElement = cstmrCdtTrfInitnElement.addElement("PmtInf");
					Element PmtInfIdElement = PmtInfElement.addElement("PmtInfId").addText(PmtInfId);
					Element PmtMtdElement = PmtInfElement.addElement("PmtMtd").addText(PmtMtd);
					Element NbOfTxs2Element = PmtInfElement.addElement("NbOfTxs").addText(NbOfTxs2);
					Element CtrlSumElement = PmtInfElement.addElement("CtrlSum").addText(CtrlSum);
					Element ReqdExctnDtElement = PmtInfElement.addElement("ReqdExctnDt").addText(ReqdExctnDt);
					
					Element DbtrElement = PmtInfElement.addElement("Dbtr");
					Element DbtrNmElement = DbtrElement.addElement("Nm").addText(DbtrNm);
					
					Element DbtrAcctElement = PmtInfElement.addElement("DbtrAcct");
					Element DbtrAcctIdElement = DbtrAcctElement.addElement("Id");
					Element DbtrAcctIdIBANElement = DbtrAcctIdElement.addElement("IBAN").addText(DbtrAcctIBAN);
					
					Element DbtrAgtElement = PmtInfElement.addElement("DbtrAgt");
					Element FinInstnIdElement = DbtrAgtElement.addElement("FinInstnId");
					Element BICElement = FinInstnIdElement.addElement("BIC").addText(DbtrAgtBIC);
					
					Element CdtTrfTxInfElement = PmtInfElement.addElement("CdtTrfTxInf");
					
					Element PmtIdElement = CdtTrfTxInfElement.addElement("PmtId");
					Element EndToEndIdElement = PmtIdElement.addElement("EndToEndId").addText(EndToEndId);
					
					Element AmtElement = CdtTrfTxInfElement.addElement("Amt");
					//TODO poate sa fie si EURO
					Element InstdAmtElement = AmtElement.addElement("InstdAmt").addAttribute("Ccy", "USD").addText(InstdAmt);
					
					Element CdtrAgtElement = CdtTrfTxInfElement.addElement("CdtrAgt");
					Element FinInstnI2dElement = CdtrAgtElement.addElement("FinInstnId");
					Element BIC2Element = FinInstnI2dElement.addElement("BIC").addText(FinInstnIdBIC);
					
					Element CdtrElement = CdtTrfTxInfElement.addElement("Cdtr");
					Element Nm2Element = CdtrElement.addElement("Nm").addText(CdtrNm);
					
					Element PstlAdrElement = CdtrElement.addElement("PstlAdr");
					Element CtryElement = PstlAdrElement.addElement("Ctry").addText(CdtrCtry);
					
					Element CdtrAcctElement = CdtTrfTxInfElement.addElement("CdtrAcct");
					Element IdElement = CdtrAcctElement.addElement("Id");
					Element IBANElement = IdElement.addElement("IBAN").addText(CdtrAcctIBAN);
					
					Element RmtInfElement = CdtTrfTxInfElement.addElement("RmtInf");
					Element UstrdElement = RmtInfElement.addElement("Ustrd").addText(RmtInf);
					

					try {
						System.out.println("[INFO] writing the XML file");
						makeFile(outputDirName, doc);
					} catch (IOException e) {
						System.out.println("[ERROR] failed to write the ");
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				System.out.println("[ERROR] failed to read the CSV file");
				e.printStackTrace();
			}
				
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
