package convert.xml;

import java.util.List;

import utils.Consts;
import utils.Utils;

public class AdaptXMLThikingFast {
	
	private static String STRING = "<g:price>";
	
	private static String STRING2 = "<g:sale_price>";
	
	public static void main(String[] args) {
		AdaptXMLThikingFast instance = new AdaptXMLThikingFast();
		instance.run();
	}

	private void run() {
		List<String> fileAsList = Utils.readListFromFile("C:\\My Stuffs\\java_develop\\file\\GoogleBaseFeedMexico.xml");

		for(int i = 0 ; i < fileAsList.size(); i++) {
			String line = fileAsList.get(i);

			if(line.contains(STRING) || line.contains(STRING2)) {

				String[] split = line.split(" ");

				String firstPart = split[0];

				int charPos = firstPart.lastIndexOf("[");

				String oldPrice = firstPart.substring(charPos + 1);
				System.out.println(oldPrice);

				String newPrice = "";
				if(oldPrice.contains(".")) {
					Float fullPriceAsFloat = Float.parseFloat(oldPrice);
					Float increase = Float.parseFloat("19");
					Float finalPrice = fullPriceAsFloat * increase;
					double test = Math.round(finalPrice * 100.0) / 100.0;

					newPrice = Double.toString(test);
				} else {
					int priceAsInt = Integer.parseInt(oldPrice);
					int finalPrice = priceAsInt * 19;
					newPrice =  Integer.toString(finalPrice);
				}

				System.out.println(newPrice); 
				String newLine = line.replace(oldPrice, newPrice);

				fileAsList.set(i, newLine);
			}
		}
		
		Utils.writeListToFile("C:\\My Stuffs\\java_develop\\file\\GoogleBaseFeedMexico_new.xml", "UTF-8", fileAsList, Consts.LINE_SEPARATOR_WINDOWS);
	}

}
