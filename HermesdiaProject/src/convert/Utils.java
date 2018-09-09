package convert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	
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


}
