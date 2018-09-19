package convert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	
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
	

}
