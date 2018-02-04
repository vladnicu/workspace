package convert.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Consts;
import utils.Utils;

public class ConvertJSONChinelj {
	
	String SEPARATOR = "<>";

	public static void main(String[] args) {
		ConvertJSONChinelj instance = new ConvertJSONChinelj();
		instance.run();
	}
	
	
	private void run() {
		
		List<String> whiteList = Utils.readListFromFile("C:\\My Stuffs\\java_develop\\file\\Chinelj\\whitelist.txt");
		
		String firstLine = "";
		for(String line : whiteList) {
			firstLine = firstLine + SEPARATOR + line;
		}
		
		firstLine = firstLine.substring(SEPARATOR.length());
		
		List<String> outputList = new ArrayList<>();
		outputList.add(firstLine);


		List<String> completeFileAsList = Utils.readListFromFile("C:\\My Stuffs\\java_develop\\file\\Chinelj\\products - Copy.json");
		for(String line : completeFileAsList) {
			if(line.endsWith(",")) {
				line = line.substring(0, line.length() -1 );
			}
			
			Map<String, String> oneLineMap = new HashMap<>();

			JSONObject rootElement = new JSONObject(line);
			
			int sku = rootElement.getInt("sku");
			oneLineMap.put("sku", Integer.toString(sku));
			
			String name = "";
			try {
				name = rootElement.getString("name");
			} catch(JSONException e) {
				name = "";
				System.out.println("name missing");
			}
			oneLineMap.put("name", name);
			

			String type = rootElement.getString("type");
			if(type == null) {
				System.out.println("null type");
			}
			oneLineMap.put("type", type);

			float price = rootElement.getFloat("price");
			oneLineMap.put("price", Float.toString(price));

			String upc = rootElement.getString("upc");
			if(upc == null) {
				System.out.println("null upc");
			}
			oneLineMap.put("upc", upc);

			JSONArray category = rootElement.getJSONArray("category");
			int i = 1;
			for(Object categoryObject : category) {
				try {
					String categoryId = ((JSONObject) categoryObject).getString("id");
					oneLineMap.put("category" + i + "_id", categoryId);
				} catch(JSONException e) {
					oneLineMap.put("category" + i + "_id", "");
					System.out.println("categoryId missing");
				}
				
				try {
					String categoryName = ((JSONObject) categoryObject).getString("name");
					oneLineMap.put("category" + i + "_name", categoryName);
				} catch(JSONException e) {
					oneLineMap.put("category" + i + "_name", "");
					System.out.println("categoryId missing");
				}
				i++;
			}
			
			System.out.println(i);

			try {
				float shipping = rootElement.getFloat("shipping");
				oneLineMap.put("shipping", Float.toString(shipping));
			} catch(JSONException e) {
				oneLineMap.put("shipping", "");
				System.out.println("shipping missing");
			}

			String description = rootElement.getString("description");
			oneLineMap.put("description", description);
			
			String manufacturer= "";
			try {
				manufacturer = rootElement.getString("manufacturer");
			} catch(JSONException e) {
				manufacturer= "";
				System.out.println("shipping missing");
			}
			oneLineMap.put("manufacturer", manufacturer);

			String model = "";
			try {
				model = rootElement.getString("model");
			} catch(JSONException e) {
				model = "";
				System.out.println("model missing");
			}
			oneLineMap.put("model", model);

			String url = rootElement.getString("url");
			if(url == null) {
				System.out.println("null url");
			}
			oneLineMap.put("url", url);

			String image = rootElement.getString("image");
			if(image == null) {
				System.out.println("null image");
			}
			oneLineMap.put("image", image);

			String oneLine = "";
			for(String columnName : whiteList) {
				String value = oneLineMap.get(columnName);
				
				if(value == null) {
					value = "";
				}
				
//				if(value.contains(",")) {
//					value = value.replace(",", "");
//				}
				oneLine = oneLine + SEPARATOR + value;
			}
			
			oneLine = oneLine.substring(SEPARATOR.length());
			outputList.add(oneLine);
		}

		System.out.println("writting the file");
		Utils.writeListToFile("C:\\My Stuffs\\java_develop\\file\\Chinelj\\products_csv.csv", "UTF-8", outputList, Consts.LINE_SEPARATOR_LINUX);

	}

}
