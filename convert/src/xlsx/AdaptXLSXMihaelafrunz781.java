package xlsx;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.NamedGroup.All;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;

import utils.Utils;

public class AdaptXLSXMihaelafrunz781 {

	private int mRowNumber = 1;
	
	private DateFormat csvFormatter  = new SimpleDateFormat("yyyy/MM/dd");
	private DateFormat xlsxFormatter  = new SimpleDateFormat("d/M/YYYY");

	public static void main(String[] args) {
		AdaptXLSXMihaelafrunz781 instance = new AdaptXLSXMihaelafrunz781();
		instance.run();
	}

	private void run() {

		XSSFWorkbook workbook = Utils.readXLSXFile("C:\\Users\\Vlad\\eclipse-workspace\\convert\\resources\\Madrid.xlsx");

		dealWithCSVFiles(workbook, "C:\\Users\\Vlad\\eclipse-workspace\\convert\\resources\\Chestionar1.csv", "1");
		dealWithCSVFiles(workbook, "C:\\Users\\Vlad\\eclipse-workspace\\convert\\resources\\Untitledform.csv", "2");

		System.out.println("Done");
	}

	//"2017/12/07 2:54:18 pm GMT+3" 
	private String extractTimestamp(String completeTimestamp) throws ParseException {
		int blankPos = completeTimestamp.indexOf(" ");
		String timestamp = completeTimestamp.substring(0, blankPos);
		Date date = csvFormatter.parse(timestamp);
		
		String reportDate = xlsxFormatter.format(date);

		return reportDate;
	}

	private void dealWithCSVFiles(XSSFWorkbook workbook, String filename, String number) {
		XSSFSheet mySheet = workbook.getSheetAt(1);

		CSVReader csvReader;
		try {
			csvReader = new CSVReader(new FileReader(filename));
			List<String[]> allData = csvReader.readAll();

			for(int i = 1; i < allData.size(); i++){
				
				String[] dataRow = allData.get(i);
				// Create a Row
				Row newRow = mySheet.createRow(mRowNumber);
				mRowNumber++;
			
				for(int z = 0; z < dataRow.length; z++){

					Cell numberCell = newRow.createCell(0);
					numberCell.setCellValue(mRowNumber - 1);

					Cell ciudatCell = newRow.createCell(1);
					ciudatCell.setCellValue("RO");

					String timestamp;
					try {
						timestamp = extractTimestamp(Utils.stripQuotes(dataRow[0]));
					} catch (ParseException e) {
						e.printStackTrace();
						timestamp = null;
					}
					Cell timestampCell = newRow.createCell(2);
					timestampCell.setCellValue(timestamp);

					Cell condicionCell = newRow.createCell(3);
					condicionCell.setCellValue(number);

					List<String> listaMagica = new ArrayList<>();
					String P11 = Utils.stripQuotes(dataRow[1]);
					if(P11.equals("Doctorul N a pus capăt vieții Pacientului O")) {
						P11 = "1";
					} else {
						P11 = "0";
					}
					listaMagica.add(P11);

					String P12 = Utils.stripQuotes(dataRow[2]);
					if(P12.equals("Doctorul N l-a împiedicat pe pacientul O să rămână în viață")) {
						P12 = "1";
					} else {
						P12 = "0";
					}
					listaMagica.add(P12);

					String P13 = Utils.stripQuotes(dataRow[3]);
					if(P13.equals("Doctorul N a provocat  moartea pacientului O, nu boala pacientului")) {
						P13 = "1";
					} else {
						P13 = "0";
					}
					listaMagica.add(P13);

					String P14 = Utils.stripQuotes(dataRow[4]);
					if(P14.equals("Doctorul N l-a omorât pe pacientul O")) {
						P14 = "1";
					} else if(P14.equals("Doctorul  N l-a lăsat să moară pe pacientul O")) {
						P14 = "0";
					} else {
						P14 = "2";
					}
					listaMagica.add(P14);

					String P21 = Utils.stripQuotes(dataRow[5]);
					if(P21.equals("Doctorul R a pus capăt vieții Pacientului S")) {
						P21 = "1";
					} else {
						P21 = "0";
					}
					listaMagica.add(P11);

					String P22 = Utils.stripQuotes(dataRow[6]);
					if(P22.equals("Doctorul R l-a împiedicat pe pacientul S să rămână în viață")) {
						P22 = "1";
					} else {
						P22 = "0";
					}
					listaMagica.add(P11);

					String P23 = Utils.stripQuotes(dataRow[7]);
					if(P23.equals("Doctorul R a provocat  moartea pacientului S,  nu boala pacientului")) {
						P23 = "1";
					} else {
						P23 = "0";
					}
					listaMagica.add(P11);

					String P24 = Utils.stripQuotes(dataRow[8]);
					if(P24.equals("Doctorul R l-a omorât pe pacientul S.")) {
						P24 = "1";
					} else if(P24.equals("Doctorul  R l-a lăsat să moară pe pacientul S")) {
						P24 = "0";
					} else {
						P24 = "2";
					}
					listaMagica.add(P11);

					String P31 = Utils.stripQuotes(dataRow[9]);
					if(P31.equals("Doctorul E a pus capăt vieții Pacientului F.")) {
						P31 = "1";
					} else {
						P31 = "0";
					}
					listaMagica.add(P31);

					String P32 = Utils.stripQuotes(dataRow[10]);
					if(P32.equals("Doctorul E l-a împiedicat pe pacientul F să rămână în viață")) {
						P32 = "1";
					} else {
						P32 = "0";
					}
					listaMagica.add(P32);

					String P33 = Utils.stripQuotes(dataRow[11]);
					if(P33.equals("Doctorul E a provocat  moartea pacientului F,  nu boala pacientului.")) {
						P33 = "1";
					} else {
						P33 = "0";
					}
					listaMagica.add(P33);

					String P34 = Utils.stripQuotes(dataRow[12]);
					if(P34.equals("Doctorul R l-a omorât pe pacientul S.")) {
						P34 = "1";
					} else if(P34.equals("Doctorul  R l-a lăsat să moară pe pacientul S")) {
						P34 = "0";
					} else {
						P34 = "2";
					}
					listaMagica.add(P34);

					String P41 = Utils.stripQuotes(dataRow[13]);
					if(P41.equals("Doctorul T a pus capăt vieții Pacientului U")) {
						P41 = "1";
					} else {
						P41 = "0";
					}
					listaMagica.add(P41);

					String P42 = Utils.stripQuotes(dataRow[14]);
					if(P42.equals("Doctorul T l-a împiedicat pe pacientul U să rămână în viață")) {
						P42 = "1";
					} else {
						P42 = "0";
					}
					listaMagica.add(P42);

					String P43 = Utils.stripQuotes(dataRow[15]);
					if(P43.equals("Doctorul T a provocat  moartea pacientului U, nu boala pacientului")) {
						P43 = "1";
					} else {
						P43 = "0";
					}
					listaMagica.add(P43);

					String P44 = Utils.stripQuotes(dataRow[16]);
					if(P44.equals("Doctorul T l-a omorât pe pacientul U")) {
						P44 = "1";
					} else if(P44.equals("Doctorul  T l-a lăsat să moară pe pacientul U.")) {
						P44 = "0";
					} else {
						P44 = "2";
					}
					listaMagica.add(P44);

					String A1 = Utils.stripQuotes(dataRow[17]);
					if(A1.equals("Ceea ce face acest doctor mi se pare inacceptabil din punct de vedere moral")) {
						A1 = "0";
					} else {
						A1 = "1";
					}
					listaMagica.add(A1);

					String A2 = Utils.stripQuotes(dataRow[18]);
					if(A2.equals("Ceea ce face acest doctor este legal în România")) {
						A2 = "1";
					} else {
						A2 = "0";
					}
					listaMagica.add(A2);

					String A3 = Utils.stripQuotes(dataRow[19]);
					if(A3.equals("Legea ar trebui să îi permită doctorului realizarea unor astfel de acțiuni.")) {
						A3 = "1";
					} else {
						A3 = "0";
					}
					listaMagica.add(A3);

					String A4 = Utils.stripQuotes(dataRow[20]);
					if(A4.equals("Ceea ce face acest doctor se numește eutanasie")) {
						A4 = "0";
					} else {
						A4 = "1";
					}
					listaMagica.add(A4);

					String A5 = Utils.stripQuotes(dataRow[21]);
					if(A5.equals("Cred că eutanasia este inacceptabilă din punct de vedere moral")) {
						A5 = "0";
					} else {
						A5 = "1";
					}
					listaMagica.add(A5);

					String A6 = Utils.stripQuotes(dataRow[22]);
					if(A6.equals("Legea ar trebui să permită eutanasia.")) {
						A6 = "1";
					} else {
						A6 = "0";
					}
					listaMagica.add(A6);

					String Edad = Utils.stripQuotes(dataRow[23]);
					listaMagica.add(Edad);

					String Genero = Utils.stripQuotes(dataRow[24]);
					if(Genero.equals("Feminin")) { //TODO ii ok asa?
						Genero = "0";
					} else {
						Genero = "1";
					}
					listaMagica.add(Genero);

					String Educacion = Utils.stripQuotes(dataRow[25]);
					if(Educacion.equals("Școală gimnazială")) {
						Educacion = "1";
					} else if(Educacion.equals("Liceu")) {
						Educacion = "2";
					} else {
						Educacion = "3";
					}
					listaMagica.add(Educacion);

					String Carrera = Utils.stripQuotes(dataRow[26]);
					listaMagica.add(Carrera);

					String Religion = Utils.stripQuotes(dataRow[27]);
					if(Religion.equals("Nu")) { //TODO ii ok asa?
						Religion = "0";
					} else {
						Religion = "1";
					}
					listaMagica.add(Religion);

					String Religión_Otra = Utils.stripQuotes(dataRow[28]);
					if(Religión_Otra.equals("Creștin-ortodox")) {
						Religión_Otra = "1";
					} else if(Religión_Otra.equals("Romano-catolic")) {
						Religión_Otra = "2";
					}  else if(Religión_Otra.equals("Greco-catolic")) {
						Religión_Otra = "3";
					} else if(Religión_Otra.equals("Reformat")) {
						Religión_Otra = "4";
					}else {
						Religión_Otra = "5";
					}
					listaMagica.add(Religión_Otra);

					String Pol = dataRow[29];
					listaMagica.add(Pol);

					String B = dataRow[30];
					if(B.equals("o dată pe an")) {
						B = "1";
					} else if(B.equals("o dată pe lună")) {
						B = "2";
					} else if(B.equals("o dată pe săptămână")) {
						B = "3";
					} else {
						B = "4";
					}
					listaMagica.add(B);

					String N = dataRow[31];
					if(N.equals("Problemele morale și nevoile individului")) {
						N = "1";
					} else if(B.equals("Problemele vieții de familie")) {
						N = "2";
					} else if(B.equals("Nevoile spirituale ale oamenilor")) {
						N = "3";
					} else {
						N = "4";
					}
					listaMagica.add(N);

					int colNumber = 4;
					for(String text: listaMagica) {
						Cell cell = newRow.createCell(colNumber);
						cell.setCellValue(text);
						colNumber++;
					}

					listaMagica.clear();
				}
			}

			csvReader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int rows = mySheet.getPhysicalNumberOfRows();
		System.out.println("Rows now: " + rows);

		// Write the output to a file
		FileOutputStream fileOut;
		try {
			System.out.println("Writing XLSX");
			fileOut = new FileOutputStream("C:\\\\Users\\\\Vlad\\\\eclipse-workspace\\\\convert\\\\resources\\\\Madrid_output.xlsx");
			workbook.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
