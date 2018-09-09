package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Utils {

	public static void writeFile(String yourfilename, String yourstring) {
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter( new FileWriter( yourfilename));
			writer.write( yourstring);

		}
		catch ( IOException e){
			e.printStackTrace();
		}
		finally{
			try{
				if( writer != null)
					writer.close( );
			}
			catch ( IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static String readFile(String path) throws IOException{
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			String sCurrentLine = "";
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}
		}

		return sb.toString();
	}
	
	public static List<String> readListFromFile(String filename) {
		BufferedReader in = null;
		List<String> myList = new ArrayList<String>();
		try {   
		    in = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
		    String str;
		    while ((str = in.readLine()) != null) {
		        myList.add(str);
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    if (in != null) {
		        try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}
		return myList;
		
	}
	
	
    /**
     * write a list of strings to a file
     * @param filename
     * @param encoding
     * @param content
     * @param lineEnding 
     */
    public static void writeListToFile (String filename, String encoding, List<String> content, String lineEnding) {
    	System.out.println("Writting the file: " + filename);
    	
        Writer out = null; 
        try {
            out = new OutputStreamWriter(new FileOutputStream(filename), encoding);
            for (String tmp: content) {            
                out.write(tmp + lineEnding);
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        } finally {
            try {
                out.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
    }
    
    public static Element readXmlDocument(String completeFilename) {
    	Element root = null;
		try {
			System.out.println("Reading the file: " + completeFilename);
			SAXReader reader = new SAXReader();
			Document mDocument  = reader.read(new InputStreamReader(new FileInputStream(completeFilename), "UTF-8"));
			root = mDocument.getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  root;
	}

    public static String stripQuotes(String oldString) {
    	oldString = oldString.trim();
    	if(oldString.startsWith("\"")) {
    		oldString = oldString.substring(1);
    	}

    	if(oldString.endsWith("\"")) {
    		oldString = oldString.substring(0, oldString.length() -1 );
    	}

    	return oldString.trim();
    }
    
    /**
     * 
     * @param directory
     * @param extension
     * @return
     */
    public static List<String> getAllFilesFromADirectoryByExtenstion(String directory, String extension){
    	List<String> filesList = new ArrayList<>();
    	File[] files = new File(directory).listFiles();
    
    	for(File file : files) {
    		String fileName = file.getAbsolutePath();
    		if(fileName.endsWith(extension)) {
    			filesList.add(fileName);
    		}
    	}
    	return filesList;
    }
    
    public static XSSFWorkbook readXLSXFile(String filename) {
    	File myFile = new File(filename);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(myFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
		
		// Finds the workbook instance for XLSX file 
		try {
			return new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
    }

}
