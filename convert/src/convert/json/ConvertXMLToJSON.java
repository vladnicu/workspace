package convert.json;

import java.io.IOException;

import org.json.JSONObject;
import org.json.XML;

import utils.Utils;

public class ConvertXMLToJSON {
	
	private static final int PRETTY_PRINT_INDENT_FACTOR = 4;

	public static void main(String[] args) {
		ConvertXMLToJSON instance = new ConvertXMLToJSON();
		instance.run();
	}

	private void run() {
		String path = "c:\\\\My Stuffs\\\\java_develop\\\\file\\\\remax_d2d-3.xml";
		String fileAsString = "";
		try {
			fileAsString = Utils.readFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject xmlJSONObj = XML.toJSONObject(fileAsString);
		String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);

		Utils.writeFile("c:/My Stuffs/java_develop/file/remax_d2d-3.json", jsonPrettyPrintString);
	}
	


}
