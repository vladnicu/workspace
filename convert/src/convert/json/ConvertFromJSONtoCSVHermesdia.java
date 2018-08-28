package convert.json;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Utils;

class ConvertFromJSONtoCSVHermesdia {

	public static void main(String[] args) {
		ConvertFromJSONtoCSVHermesdia instance = new ConvertFromJSONtoCSVHermesdia();
		instance.run();
	}
	
	private void run() {
		String fileAsString = "";
		try {
			String filename = System.getProperty("user.dir") + "/file.json";
			fileAsString = Utils.readFile(filename);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JSONObject rootJsonObject = null;
		try {
			rootJsonObject = new JSONObject(fileAsString);
		} catch(JSONException e) {
			System.out.println("oops");
		}
		
		JSONArray dataArray  = rootJsonObject.getJSONArray("data");
		for (int i = 0; i < dataArray.length(); ++i) {
			JSONObject object = dataArray.getJSONObject(i);
			 String md5 = object.getString("md5");
			 String sha1 = object.getString("sha1");
			 String sha256 = object.getString("sha256");
			 
			 boolean isinteresting = object.getBoolean("isinteresting");
			 String analysis_start_time = object.getString("analysis_start_time");
			 
			 int threatscore = object.getInt("threatscore");
			 int threatlevel = object.getInt("threatlevel");
			 
			 String threatlevel_human = object.getString("threatlevel_human");
			 
			 int avdetect = object.getInt("avdetect");
			 
			 boolean isunknown = object.getBoolean("isunknown");
			 String submitname = object.getString("submitname");
			 boolean isurlanalysis = object.getBoolean("isurlanalysis");
			 
			 int size = object.getInt("size");
			 
			 String type = object.getString("type");
			 String environmentId = object.getString("environmentId");
			 String environmentDescription = object.getString("environmentDescription");
			 
			 boolean sharedanalysis = object.getBoolean("sharedanalysis");
			 boolean isreliable = object.getBoolean("isreliable");
			 String reporturl = object.getString("reporturl");
			 
			 int vt_detect = object.getInt("vt_detect");
			 int ms_detect = object.getInt("ms_detect");
			 
			 JSONArray domains = rootJsonObject.getJSONArray("domains");
			 
			 JSONArray hosts = rootJsonObject.getJSONArray("hosts");
			
			
			
//		    int id = rec.getInt("id");
//		    String loc = rec.getString("loc");
		}
	
		System.out.println("test");
	}

}
