package convert.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Consts;
import utils.Utils;

public class ConvertFromXMLToCSVDslone {
	private static final String ATTRIBUTE_SEPARATOR = ",";
	private static final String SEPARATOR = ";";

	public static void main(String[] args) {
		ConvertFromXMLToCSVDslone instance = new ConvertFromXMLToCSVDslone();
		instance.run();

	}
	
	private void run() {
		
		List<String> output = new ArrayList<>();
		
		String fileAsString = "";
		try {
			fileAsString = Utils.readFile("c:\\Users\\Vlad\\eclipse-workspace\\resources\\dslone\\203570.json");
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
		
		Set<String> keys = rootJsonObject.keySet();
		String firstLine = "";
		for(String key : keys) {
			firstLine = firstLine + key + ",";
		}
		output.add(firstLine);

		String resource_uri = "";
		String absolute_url = "";
		String cluster = "";
		String author = "";
		String joined_by;
		String author_str = "";
		boolean per_curiam;
		String date_created = "";
		String date_modified = "";
		String type = "";
		String sha1 = "";
		int page_count = 0;
		String download_url = "";
		String local_path = "";
		String plain_text= "";
		String html = "";
		String html_lawbox = "";
		String html_columbia = "";
		String html_with_citations = "";
		boolean extracted_by_ocr;
		JSONArray opinions_cited;
		
		resource_uri = rootJsonObject.getString("resource_uri");
		absolute_url = rootJsonObject.getString("absolute_url");
		cluster = rootJsonObject.getString("cluster");
		author = rootJsonObject.getString("author");
		
		JSONArray joined_byArray  = rootJsonObject.getJSONArray("joined_by");
		if(joined_byArray.length() == 0) {
			joined_by = "";
		}
		author_str = rootJsonObject.getString("author_str");
		per_curiam = rootJsonObject.getBoolean("per_curiam");
		date_created = rootJsonObject.getString("date_created");
		date_modified = rootJsonObject.getString("date_modified");
		type = rootJsonObject.getString("type");
		sha1 = rootJsonObject.getString("sha1");
		page_count = rootJsonObject.getInt("page_count");
		download_url = rootJsonObject.getString("download_url");
		local_path = rootJsonObject.getString("local_path");
		plain_text = rootJsonObject.getString("plain_text");
		if(plain_text.contains(SEPARATOR)) {
			plain_text = plain_text.replaceAll(SEPARATOR, "");
		}
		html = rootJsonObject.getString("html");
		html_lawbox = rootJsonObject.getString("html_lawbox");
		if(html_lawbox.contains(SEPARATOR)) {
			html_lawbox = plain_text.replaceAll(SEPARATOR, "");
		}
		html_columbia = rootJsonObject.getString("html_columbia");
		html_with_citations = rootJsonObject.getString("html_with_citations");
		if(html_with_citations.contains(SEPARATOR)) {
			html_with_citations = plain_text.replaceAll(SEPARATOR, "");
		}
		extracted_by_ocr = rootJsonObject.getBoolean("extracted_by_ocr");
		opinions_cited = rootJsonObject.getJSONArray("opinions_cited");
		
		output.add(resource_uri + SEPARATOR + absolute_url + SEPARATOR + cluster + SEPARATOR + author + SEPARATOR + joined_by + SEPARATOR +
				author_str + SEPARATOR + absolute_url + SEPARATOR + cluster + SEPARATOR + author + SEPARATOR + joined_by + SEPARATOR);
		
		
		
		Utils.writeListToFile("c:\\Users\\Vlad\\eclipse-workspace\\resources\\dslone\\output.csv", Consts.ENCODING_UTF8, output, Consts.LINE_SEPARATOR_LINUX);
		System.out.println("oops 2");
	}

}
