package convert.csv;

import java.util.ArrayList;
import java.util.List;

import utils.Consts;
import utils.Utils;

public class ConvertCSVTOCSVKevingators {

	public static void main(String[] args) {
		ConvertCSVTOCSVKevingators instance = new ConvertCSVTOCSVKevingators();
		instance.run();
	}
	
	private void run() {
		List<String> output = new ArrayList<String>();
		output.add("Name,Phone,Emails,Address,Website");
		
		List<String> csvFiles = Utils.getAllFilesFromADirectoryByExtenstion("c:\\My Stuffs\\java_develop\\file\\Kevingators\\",".csv");
		
		for(String csvFile: csvFiles) {
			List<String> fileContent = Utils.readListFromFile(csvFile);
			
			for(int i = 1; i < fileContent.size(); i++) {
				String line = fileContent.get(i);
				if(!output.contains(line)) {
					output.add(line);
				} else {
					System.out.println(line);
				}
			}
		}
		
		Utils.writeListToFile("c:\\My Stuffs\\java_develop\\file\\Kevingators\\result.csv", "UTF-8", output, Consts.LINE_SEPARATOR_LINUX);
	}
	

}
