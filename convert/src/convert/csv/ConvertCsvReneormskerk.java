package convert.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.Utils;

public class ConvertCsvReneormskerk {
	
	int mIdCounter = 1;
	
	private static final int PRETTY_PRINT_INDENT_FACTOR = 4;

	private Map<String, Map<String, List<String>>> mEventsMap = new HashMap<>();
	
	private Map<String, String> mUserLineMap = new HashMap<>();
	
	private Map<String, String> mUniqueEventsMap = new HashMap<>();
	
	public static void main(String[] args) {
		ConvertCsvReneormskerk instance = new ConvertCsvReneormskerk();
		instance.run();
	}
	
	private String strip(String oldString) {
		
		oldString = oldString.trim();
		if(oldString.startsWith("\"")) {
			oldString = oldString.substring(1);
		}
		
		if(oldString.endsWith("\"")) {
			oldString = oldString.substring(0, oldString.length() -1 );
		}
		
		return oldString.trim();
	}
	
	private void readEventsFile() { 
		List<String> fileAsList = Utils.readListFromFile("C:\\My Stuffs\\java_develop\\file\\reneormskerk\\med4allbookingsexport.csv");
		for(int i=1; i < fileAsList.size(); i++) {
			String line = fileAsList.get(i);

			String[] split = line.split(",");

			String eventName = strip(split[3]);
			String eventDate = strip(split[7]);
			String studentName = split[0];
			studentName = strip(studentName);
			if(mEventsMap.containsKey(eventName)) {
				Map<String, List<String>> dateAndStudentsMap = mEventsMap.get(eventName);
				if(dateAndStudentsMap.containsKey(eventDate)) {
					List<String> studentsList = dateAndStudentsMap.get(eventDate);
					studentsList.add(studentName);
				} else {
					List<String> studentsList = new ArrayList<>();
					studentsList.add(studentName);
					dateAndStudentsMap.put(eventDate, studentsList);
				}

			} else {
				Map<String, List<String>> dateAndStudentsMap = new HashMap<String, List<String>>();
				List<String> studentsList = new ArrayList<>();
				studentsList.add(studentName);
				dateAndStudentsMap.put(eventDate, studentsList);
				mEventsMap.put(eventName, dateAndStudentsMap);
			}
		}
	}
	
	
	private void readUniqueCoursesFile() {
		List<String> fileAsList = Utils.readListFromFile("C:\\My Stuffs\\java_develop\\file\\reneormskerk\\eventsdb\\zfddqn_em_events.csv");
		
		for(int i=1; i < fileAsList.size(); i++) {
			String line = fileAsList.get(i);
			
			String[] split = line.split(",");
			mUniqueEventsMap.put(strip(split[2]), strip(split[5]));
		}
		
		System.out.println(mUniqueEventsMap.size());
	}
	
	private void readUsersFile() {
		List<String> fileAsList = Utils.readListFromFile("C:\\My Stuffs\\java_develop\\file\\reneormskerk\\CustomerExport2018_01_10_16_27_47.csv");
		
		for(int i=1; i < fileAsList.size(); i++) {
			String line = fileAsList.get(i);
			
			String[] split = line.split(",");
			
			String fullName = split[8] + " " +split[9];
			if(fullName.contains("\"")) {
				fullName = fullName.replace("\"", "");
			}
			mUserLineMap.put(fullName.toLowerCase(), line);
		}
	}
	
	
	private void run() {

		String jsonTemplateString = null;
		try {
			jsonTemplateString = Utils.readFile("C:\\My Stuffs\\java_develop\\file\\reneormskerk\\json_template.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject metaJsonObject2 =  null;
		JSONObject authorJsonObject2 = null;
		JSONObject instructorsJsonObject2 = null;
		JSONArray facilitatorsJsonObject2 = null;
		JSONObject unitsObject = null;

		JSONObject templateObject = new JSONObject(jsonTemplateString);
		for(String courseId : templateObject.keySet()) {

			JSONObject courseJsonObject = templateObject.getJSONObject(courseId);
			metaJsonObject2 = courseJsonObject.getJSONObject("meta");
			authorJsonObject2 = courseJsonObject.getJSONObject("author");
			instructorsJsonObject2 = courseJsonObject.getJSONObject("instructors");
			facilitatorsJsonObject2 = courseJsonObject.getJSONArray("facilitators");
			unitsObject = courseJsonObject.getJSONObject("units");
			break;

		}
		
		readUniqueCoursesFile();
		
		readEventsFile();

		readUsersFile();
		
		JSONObject rootJsonObject = new JSONObject();

		int x = 1;
		for(String courseName : mEventsMap.keySet()) {
			Map<String, List<String>> uniqueEventsMap = mEventsMap.get(courseName);
			for(String uniqueEvent :  uniqueEventsMap.keySet()){
				JSONObject uniqueCourseObject = new JSONObject();

				JSONObject courseElement = new JSONObject();
				courseElement.put("ID",  Integer.toString(x));
				
				courseElement.put("post_title", courseName);
				courseElement.put("post_name", courseName);
				
				//courseElement.put("post_title", mUniqueEventsMap.get(uniqueEvent));
				//courseElement.put("post_name",  mUniqueEventsMap.get(uniqueEvent));
				
				courseElement.put("post_author", "4");
				courseElement.put("post_date_gmt", "0000-00-00 00:00:00");
				courseElement.put("post_date", "2018-01-10 18:33:28");
				courseElement.put("post_content", "full description");
				courseElement.put("post_excerpt", "this is description");
				courseElement.put("post_status", "draft");
				courseElement.put("comment_status", "closed");
				courseElement.put("ping_status", "closed");
				courseElement.put("post_password", "");
				
				courseElement.put("to_ping", "");
				courseElement.put("pinged", "");
				courseElement.put("post_modified", "2018-01-10 18:33:28");
				courseElement.put("post_modified_gmt", "2018-01-10 17:33:28");
				courseElement.put("post_content_filtered", "");
				courseElement.put("post_parent", 0);
				courseElement.put("guid", "https:\\/\\/www.bluecasters.eu\\/?post_type=course&#038;p=1111");
				courseElement.put("menu_order", 0);
				courseElement.put("post_type", "course");
				courseElement.put("post_mime_type", "");
				courseElement.put("comment_count", "0");
				courseElement.put("filter", "raw");
		            
				uniqueCourseObject.put("course", courseElement);

				JSONObject allStudentsObject =  new JSONObject();
				
				List<String> studentsList = uniqueEventsMap.get(uniqueEvent);
				for(String student : studentsList) {

					String line = mUserLineMap.get(student.toLowerCase());
					if(line == null) {
						System.out.println("no contact found: " + student);
						continue;
					}

					String[] split = line.split(",");

					String id = strip(split[0]);
					String user_login = strip(split[1]);
					String user_pass = strip(split[2]);
					String user_nicename = strip(split[3]);
					String user_email = strip(split[1]);
					String user_registered = strip(split[6]);
					String display_name = strip(split[7]);

					JSONObject oneStudent = new JSONObject();

					oneStudent.put("user_login", user_login);
					oneStudent.put("user_pass", user_pass);
					oneStudent.put("user_nicename", user_nicename);
					oneStudent.put("user_email", user_email);
					oneStudent.put("user_url", "");
					oneStudent.put("user_registered", user_registered);
					oneStudent.put("user_activation_key", "");
					oneStudent.put("user_status", "0");
					oneStudent.put("display_name", display_name);

					allStudentsObject.put(id, oneStudent);
				}
				
				uniqueCourseObject.put("students", allStudentsObject);
				uniqueCourseObject.put("meta", metaJsonObject2);
				uniqueCourseObject.put("author", authorJsonObject2);
				uniqueCourseObject.put("instructors", instructorsJsonObject2);
				uniqueCourseObject.put("facilitators", facilitatorsJsonObject2);
				uniqueCourseObject.put("units", unitsObject);
				
				rootJsonObject.put(Integer.toString(x), uniqueCourseObject);
				
				x++;
			}
		}
		
		System.out.println(x);
		
		String jsonPrettyPrintString = rootJsonObject.toString(PRETTY_PRINT_INDENT_FACTOR);
	    Utils.writeFile("C:\\\\My Stuffs\\\\java_develop\\\\file\\\\reneormskerk\\\\out_40_v2.json", jsonPrettyPrintString);
		
	}
	
}
