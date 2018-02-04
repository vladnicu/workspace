package convert.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.dom4j.Element;

import utils.Consts;
import utils.Utils;

public class ConvertXMLToCSVDavidkingham {

	String SEPARATOR = "|";

	public static void main(String[] args) {
		ConvertXMLToCSVDavidkingham instance = new ConvertXMLToCSVDavidkingham();
		instance.run();
	}

	private void run() {
		List<String> output = new ArrayList<>();
		output.add("comment_author" + SEPARATOR + "comment_author_email" + SEPARATOR + "comment_author_IP"
				+ SEPARATOR + "comment_date" + SEPARATOR + "comment_content");


		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyy HH:mm");

		Element rootElement = Utils.readXmlDocument("C:\\My Stuffs\\java_develop\\file\\davidkingham\\disqus.export.davidkingham.xml");

		List<Element> elements = rootElement.elements();
		for(Element element : elements) {
			if(!element.getName().contentEquals("post")) {
				continue;
			}

			String nameValue = "";
			String emailValue = "";
			String ipValue = "";
			String createdAtValue = "";
			String messageValue = "";
			String threadId = "0";


			//String postId = element.attributeValue("id");

			Element authorElement = element.element("author");

			Element nameELement = authorElement.element("name");
			if(nameELement != null) {
				nameValue =  nameELement.getText();
			}

			Element emailElement = authorElement.element("email");
			if(emailElement != null) {
				emailValue = emailElement.getText();
			}

			Element ipElement = element.element("ipAddress");
			if(ipElement != null) {
				ipValue = ipElement.getText();
			}

			Element createdAtElement = element.element("createdAt");
			if(createdAtElement != null) {
				Calendar cal = Calendar.getInstance();

				createdAtValue = createdAtElement.getText();
				String[] split = createdAtValue.split("T");
				createdAtValue = split[0] + " " + split[1];
				createdAtValue = createdAtValue.substring(0, createdAtValue.length() -1);
				try {
					cal.setTime(sdf.parse(createdAtValue));
					createdAtValue = format1.format(cal.getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			Element messageElement = element.element("message");
			if(messageElement != null) {
				messageValue = messageElement.getText();
				//messageValue = messageValue.replace("<p>", "");
				//messageValue = messageValue.replace("</p>", "");
			}

			Element threadElement = element.element("thread");
			if(threadElement != null) {
				threadId = threadElement.attributeValue("id");
			}

			output.add(nameValue + SEPARATOR + emailValue + SEPARATOR + ipValue
					+ SEPARATOR + createdAtValue + SEPARATOR + messageValue);
		}

		Utils.writeListToFile("C:\\\\My Stuffs\\\\java_develop\\\\file\\\\davidkingham\\\\disqus.export.davidkingham_v3.csv", Consts.ENCODING_UTF8, output, Consts.LINE_SEPARATOR_LINUX);
	}
}
