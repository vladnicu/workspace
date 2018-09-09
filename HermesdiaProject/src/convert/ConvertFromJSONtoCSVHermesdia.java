package convert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class ConvertFromJSONtoCSVHermesdia {
	
	private static final String SEPARATOR = ",";
	
	public static void main(String[] args) {
		ConvertFromJSONtoCSVHermesdia instance = new ConvertFromJSONtoCSVHermesdia();
		instance.run();
		System.out.println("Done!");
	}

	private void run() {

		File inputDir =  new File(System.getProperty("user.dir") + "/input");
		if(!inputDir.exists()) {
			inputDir.mkdir();
			System.out.println("[ERROR] The input file was missing, I just created one, please add the json files");
			return;
		}
		
		File outputDir =  new File(System.getProperty("user.dir") + "/output");
		if(!outputDir.exists()) {
			outputDir.mkdir();
		}
		
		List<String> jsonFiles = Utils.getAllFilesFromADirectoryByExtenstion( System.getProperty("user.dir") + "/input",".json");
		for(String jsonFile: jsonFiles) {
			List<String> output = new ArrayList<>();
			output.add("threatlevel_human" + SEPARATOR + "submitname" + SEPARATOR + "type" + SEPARATOR + "vxfamily" + SEPARATOR + "domains");
		
			String fileAsString = "";
			try {
				fileAsString = Utils.readFile(jsonFile);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("[ERROR] The input file: " + jsonFile + " could not be read!");
				continue;
			}
			
			System.out.println("Dealing with the file: " + jsonFile);

			JSONObject rootJsonObject = null;
			try {
				rootJsonObject = new JSONObject(fileAsString);
			} catch(JSONException e) {
				System.out.println("[ERROR] The JSON structure for the file: " + jsonFile + " is corrupted!");
				continue;
			}

			JSONArray dataArray  = rootJsonObject.getJSONArray("data");
			for (int i = 0; i < dataArray.length(); ++i) {
				JSONObject object = dataArray.getJSONObject(i);

				String threatlevel_human = "";
				try {
					threatlevel_human = object.getString("threatlevel_human");
					if(threatlevel_human.contains(",")){
						threatlevel_human = threatlevel_human.replace(",", "");
					}
				} catch(JSONException e) {
					threatlevel_human = "";
				}
				
				String submitname = "";
				try {
					submitname = object.getString("submitname");
					if(submitname.contains(",")){
						submitname = submitname.replace(",", "");
					}
				} catch(JSONException e) {
					submitname = "";
				}
				
				String type = "";
				try {
					type = object.getString("type");
					if(type.contains(",")){
						type = type.replace(",", "");
					}
				} catch(JSONException e) {
					type = "";
				}
				
				String vxfamily = "";
				try {
					vxfamily = object.getString("vxfamily");
					if(vxfamily.contains(",")){
						vxfamily = vxfamily.replace(",", "");
					}
				} catch(JSONException e) {
					vxfamily = "";
				}
				
				String domains = "";
				JSONArray domainsArray = null;
				try {
					List<String> test = new ArrayList<>();
					domainsArray = object.getJSONArray("domains");
					for (int y = 0; y < domainsArray.length(); ++y) {
						test.add(domainsArray.optString(y));
					}
					domains = String.join(";", test);
				} catch(JSONException e) {
					domains = "";
				}
				
				output.add(threatlevel_human + SEPARATOR + submitname + SEPARATOR + type + SEPARATOR + vxfamily + SEPARATOR + domains);
			}
			
			String outputFilename = new File(jsonFile).getName().replace(".json", ".csv");
			
			Utils.writeListToFile(outputDir + "/" + outputFilename, "UTF-8", output, "\n");
		}
	}

}
