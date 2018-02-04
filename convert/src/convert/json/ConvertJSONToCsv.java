package convert.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Consts;
import utils.Utils;

public class ConvertJSONToCsv {

	private static final String ATTRIBUTE_SEPARATOR = ",";
	private static final String SEPARATOR = ";";

	public static void main(String[] args) {
		ConvertJSONToCsv instance = new ConvertJSONToCsv();
		instance.run();
	}
	
	private void run() {
		JSONObject output;
		try {
			List<String> list = readFileAsList();
			parseTheLines(list);
		} catch (JSONException e) {
			e.printStackTrace();
		}        
	}
	
	private void parseTheLines(List<String> list) {
		List<String> output = new ArrayList<>();
		output.add("Category Id" + SEPARATOR + "Category Name" + SEPARATOR + "Category Path" + SEPARATOR + "Attribute Group Id" + SEPARATOR + "Attribute Group Name" + SEPARATOR + "Attributes");
		
		for(int i = 1; i <= list.size() -1; i++) {
    		String line = list.get(i);
    		
    		line = line.substring(line.indexOf(":") + 1);
    		if(line.endsWith(ATTRIBUTE_SEPARATOR)) {
    			line = line.substring(0, line.length() - 1);
    		}
    		JSONObject rootJsonObject = null;
    		try {
    		 rootJsonObject = new JSONObject(line);
    		 } catch(JSONException e) {
    			 System.out.println("oops");
    			 continue;
    		 }
    		 String id = rootJsonObject.getString("id");
    		 String categoryName = rootJsonObject.getString("name");
    		 
    		 
    		 JSONArray pathFromRootArray = rootJsonObject.getJSONArray("path_from_root");
    		 
    		 String pathFromRoot = "";
    		 for(Object jsonObject : pathFromRootArray) {
    			 String pathName = ((JSONObject) jsonObject).getString("name");
    			 pathFromRoot = pathFromRoot + pathName + "/";
    		 }
    		 pathFromRoot = pathFromRoot.substring(0, pathFromRoot.length() -1);
    		 
    		 String attributeGroupId = "";
    		 String attributeGroupName= "";
    		 String attributes = "";
    		 try {
    			 JSONArray attributesArray = rootJsonObject.getJSONArray("attributes");
    			 attributeGroupId = ((JSONObject) attributesArray.get(0)).getString("attribute_group_id");
    			 attributeGroupName = ((JSONObject) attributesArray.get(0)).getString("attribute_group_name");
    			 
    			 
    			 for(Object jsonObject : attributesArray) {
        			 String attributeName = ((JSONObject) jsonObject).getString("name");
        			 attributes = attributes + attributeName + ATTRIBUTE_SEPARATOR;
        		 }
    			 attributes = attributes.substring(0, attributes.length() -1);
    			 
    		 } catch(JSONException e) {
    			 //System.out.println("this category doesn't have attributes");
    		 }
    		 
    		 output.add(id + SEPARATOR + categoryName + SEPARATOR + pathFromRoot + SEPARATOR +  attributeGroupId + SEPARATOR + attributeGroupName + SEPARATOR + attributes );
    	}
		
		Utils.writeListToFile("c:/My Stuffs/java_develop/file/csv_categories.csv", Consts.ENCODING_UTF8, output, Consts.LINE_SEPARATOR_LINUX);
	}
	
	private List<String> readFileAsList() {
		BufferedReader in = null;
		List<String> myList = new ArrayList<String>();
		try {   
		    in = new BufferedReader(new FileReader("C:/My Stuffs/java_develop/file/categorias.json"));
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

}
