package convert.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utils.Consts;
import utils.Utils;

public class ConvertCSVToCSVChucktallstar {

	public static void main(String[] args) {
		ConvertCSVToCSVChucktallstar instance = new ConvertCSVToCSVChucktallstar();
		instance.run();
	}

	private void renameTheImages() {
		File myFile = new File("c:\\My Stuffs\\java_develop\\file\\chucktallstar\\xFile_1.xlsx");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(myFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// Finds the workbook instance for XLSX file 
		XSSFWorkbook myWorkBook = null;
		try {
			myWorkBook = new XSSFWorkbook (fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		
		XSSFDrawing drawing = mySheet.getDrawingPatriarch();
		List<XSSFShape> shapes = drawing.getShapes();
		
		for(XSSFShape shape : shapes) {
			XSSFPicture inpPic = (XSSFPicture)shape;
			XSSFPictureData picData = inpPic.getPictureData();
			String ext = picData.suggestFileExtension();
			byte[] data = picData.getData();
			
			XSSFClientAnchor clientAnchor = inpPic.getClientAnchor();
			int row1 = clientAnchor.getRow1();
			int row2 = clientAnchor.getRow2();
			System.out.println("row1: " + row1 + ", row2: " + row2);
			
			FileOutputStream out = null;
			try {
				Row row = mySheet.getRow(clientAnchor.getRow1());
				Cell cell = row.getCell(1);
				out = new FileOutputStream("c:\\\\My Stuffs\\\\java_develop\\\\file\\\\chucktallstar\\\\" + cell.getStringCellValue() + "." + ext);
				out.write(data);
				out.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
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
		
		addTheURls();
	}
}
