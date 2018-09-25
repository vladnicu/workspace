package convert.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import utils.Consts;
import utils.Utils;

public class ConvertCSVToCSVChucktallstar {
	
	private int count = 1;

	Map<String, String> mImagesMap = new HashMap<String, String>();

	public static void main(String[] args) {
		ConvertCSVToCSVChucktallstar instance = new ConvertCSVToCSVChucktallstar();
		instance.run();
	}

	//	private void renameTheImages() {
	//		File myFile = new File("c:\\My Stuffs\\java_develop\\file\\chucktallstar\\xFile_1.xlsx");
	//		FileInputStream fis = null;
	//		try {
	//			fis = new FileInputStream(myFile);
	//		} catch (FileNotFoundException e1) {
	//			// TODO Auto-generated catch block
	//			e1.printStackTrace();
	//		}
	//		
	//		
	//		// Finds the workbook instance for XLSX file 
	//		XSSFWorkbook myWorkBook = null;
	//		try {
	//			myWorkBook = new XSSFWorkbook (fis);
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		
	//		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
	//		
	//		XSSFDrawing drawing = mySheet.getDrawingPatriarch();
	//		List<XSSFShape> shapes = drawing.getShapes();
	//		
	//		for(XSSFShape shape : shapes) {
	//			XSSFPicture inpPic = (XSSFPicture)shape;
	//			XSSFPictureData picData = inpPic.getPictureData();
	//			String ext = picData.suggestFileExtension();
	//			byte[] data = picData.getData();
	//			
	//			XSSFClientAnchor clientAnchor = inpPic.getClientAnchor();
	//			int row1 = clientAnchor.getRow1();
	//			int row2 = clientAnchor.getRow2();
	//			System.out.println("row1: " + row1 + ", row2: " + row2);
	//			
	//			FileOutputStream out = null;
	//			try {
	//				Row row = mySheet.getRow(clientAnchor.getRow1());
	//				Cell cell = row.getCell(1);
	//				out = new FileOutputStream("c:\\\\My Stuffs\\\\java_develop\\\\file\\\\chucktallstar\\\\" + cell.getStringCellValue() + "." + ext);
	//				out.write(data);
	//				out.close();
	//			} catch (Exception e1) {
	//				e1.printStackTrace();
	//			}
	//		}
	//	}

	private void addTheURls() {
		List<String> output = new ArrayList<String>();
		output.add("Name,sku,Description,Price,Image");

		List<String> fileContent = Utils.readListFromFile("C:\\My Stuffs\\java_develop\\file\\chucktallstar\\xFile_1.csv");

		for(int i = 1; i <fileContent.size(); i++) {
			String line = fileContent.get(i);
			String[] split = line.split(",");

			String sku = split[1];

			String url = "http://www.hillsideglass.net/wp-content/uploads/2017/09/" + sku + ".png";

			line = line + url;
			output.add(line);
		}

		Utils.writeListToFile("C:\\My Stuffs\\java_develop\\file\\chucktallstar\\xFile__with_URL_1.csv", "UTF-8", output, Consts.LINE_SEPARATOR_LINUX);

	}

	private void run() {
		//renameTheImages();

		//addTheURls();

		addTheUrls2();
	}

	private void getOnlyFilenames(List<String> inputList, String extension) {
		for(String completeFilename : inputList) {
			String shortFilename = new File(completeFilename).getName();
			mImagesMap.put(shortFilename.toLowerCase().replace(extension, ""), shortFilename);
		}
	}

	private void getAllImageNames() {
		List<String> allPngImageFiles = Utils.getAllFilesFromADirectoryByExtenstion(System.getProperty("user.dir") + "/resources/images", ".png");
		List<String> allJpgImageFiles = Utils.getAllFilesFromADirectoryByExtenstion(System.getProperty("user.dir") + "/resources/images", ".jpg");

		getOnlyFilenames(allJpgImageFiles, ".jpg");
		getOnlyFilenames(allPngImageFiles, ".png");
	}

	private void addTheUrls2() {

		String baseURL = "http://www.hillsideglass.net/wp-content/uploads/";
		String newURL  = "2018/09/";

		getAllImageNames();

		Writer writer = null;
		try {
			writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir") + "/resources/" + "output.csv"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		CSVWriter csvWriter = new CSVWriter(writer,
				CSVWriter.DEFAULT_SEPARATOR,
				CSVWriter.NO_QUOTE_CHARACTER,
				CSVWriter.DEFAULT_ESCAPE_CHARACTER,
				CSVWriter.DEFAULT_LINE_END);
		//String[] headerRecord = {"Name", "Email", "Phone", "Country"};
	


		CSVReader csvReader;
		List<String[]> allData = null;
		try {
			csvReader = new CSVReader(new FileReader(System.getProperty("user.dir") + "/resources/" + "wc-product-export-14-9-2018-1536957049733.csv"));
			allData = csvReader.readAll();
			
			csvWriter.writeNext(allData.get(0));

			for(int i = 1; i < allData.size(); i++){

				String[] dataRow = allData.get(i);

				String sku = dataRow[2];
				if(mImagesMap.containsKey(sku.toLowerCase())) {
					System.out.println("Sku: " + sku);
					System.out.println(dataRow[28]);
					String newUrl = baseURL + newURL + mImagesMap.get(sku.toLowerCase());

					dataRow[28] = newUrl;
					System.out.println(dataRow[28]);
					System.out.println("+");
					count++;
				}
				
				csvWriter.writeNext(dataRow);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			csvWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Total: " + count);
	}

}
