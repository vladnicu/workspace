package convert.xml;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import utils.Consts;
import utils.Utils;

public class ConvertXMLToCSVDwitzel {

	private Element mRootElement = null;

	List<String> outputList = new ArrayList<>();

	List<String> mWhiteListColumns = new ArrayList<>();

	String SEPARATOR = ",";

	public static void main(String[] args) {
		ConvertXMLToCSVDwitzel instance = new ConvertXMLToCSVDwitzel();
		instance.run();
	}

	private void readWhiteList() {
		mWhiteListColumns = Utils.readListFromFile("C:\\My Stuffs\\java_develop\\file\\dwitzel\\new_white_list.csv");

	}


	private void run() {
		System.out.println("Starting...");
		mRootElement = Utils.readXmlDocument("C:\\My Stuffs\\java_develop\\file\\dwitzel\\homersvp-feb-02-1530.xml");

		readWhiteList();

		List<String> inputFileAsString = Utils.readListFromFile("C:\\My Stuffs\\java_develop\\file\\dwitzel\\homersvptemplate.csv");
		String line0 = inputFileAsString.get(0);
		String line1 = inputFileAsString.get(1);

		Map<String, String> map = new HashMap<>();

		String[] splitLine0 = line0.split(",");
		String[] splitLine1 = line1.split(",");

		//List<String> newWhite = new ArrayList<>();
		for(int x = 0 ; x < splitLine0.length; x++) {
			map.put(splitLine0[x], splitLine1[x]);
		}

		String columnsLine = "";
		for(String columnName : mWhiteListColumns) {
			String newColumnName = map.get(columnName);
			if(newColumnName == null) {
				newColumnName = columnName;
			}
			columnsLine = columnsLine + SEPARATOR + "\"" + newColumnName + "\"";
		}
		columnsLine = columnsLine.substring(1);
		outputList.add(columnsLine);

		List<Element> listings = mRootElement.elements();
		for(Element listing : listings) {

			Map<String, String> columnNamesValuesMap  = new HashMap<String, String>();
			List<String> columnsNamesList = new ArrayList<>();

			List<Element> listingChilds = listing.elements();
			int i = 1;

			for(Element listingDirectChild : listingChilds) {
				String listingDirectChildName = listingDirectChild.getName();
				List<Element> subChilds = listingDirectChild.elements();
				int y = 1;
				if(subChilds.size() > 0) {
					for(Element subChild : subChilds) {
						String subChildName = subChild.getName();
						String subChildColumnName = listingDirectChildName + "_" + subChildName;
						List<Element> finalChilds = subChild.elements();
						if(finalChilds.size() > 0) {
							for(Element finalChild : finalChilds) {
								String finalChildName = finalChild.getName();
								String finalChildColumnName = subChildColumnName + "_" + finalChildName;
								if(columnsNamesList.contains(finalChildColumnName)) {
									finalChildColumnName = finalChildColumnName  + "_" + y;
									y++;
								}

								columnsNamesList.add(finalChildColumnName);
								String finalChildValue = finalChild.getStringValue();
								columnNamesValuesMap.put(finalChildColumnName, removeChar(finalChildValue));
							}
						} else {
							if(columnsNamesList.contains(subChildColumnName)) {
								subChildColumnName = listingDirectChildName + "_" +subChildColumnName + "_" + i;
								i++;
							}

							columnsNamesList.add(subChildColumnName);
							String subChildValue = subChild.getStringValue();
							columnNamesValuesMap.put(subChildColumnName,  removeChar(subChildValue));
						}
					}
				} else {
					columnsNamesList.add(listingDirectChildName);
					String value = listingDirectChild.getText();
					columnNamesValuesMap.put(listingDirectChildName,  removeChar(value));
				}
			}

			System.out.println(columnNamesValuesMap.size());

			String oneLine = "";
			boolean addLine = true;
			for(String columnName : mWhiteListColumns) {
				String value = columnNamesValuesMap.get(columnName);

				if(columnName.equals("OpenHouses_OpenHouse_EndTime") || columnName.equals("OpenHouses_OpenHouse_StartTime")) {
					if(value == null) {
						addLine = false;
						break;
					}
				}

				if(columnName.equals("Rent")
						|| columnName.equals("Deposit")
						|| columnName.equals("Dogs")
						|| columnName.equals("Cats")
						|| columnName.equals("Available_Date")
						|| columnName.equals("Pool")
						|| columnName.equals("Spa")
						|| columnName.equals("User_M_License_Number")
						|| columnName.equals("User_M_License_State")
						|| columnName.equals("User_M_License_Expiration")) {
					value = "";
				}

				if(columnName.equals("cb_user_action")
						|| columnName.equals("cb_prop_action")
						|| columnName.equals("cb_prop_event_action")) {
					value = "U";
				}

				if(columnName.equals("User_MLS_Number")
						|| columnName.equals("Agent_MLSNumber")) {
					value = columnNamesValuesMap.get("ListingParticipants_Participant_ParticipantId");
				}

				if(columnName.equals("ListingCategory")) {
					if(value.contains("Purchase")) {
						value = "FOR SALE";
					} else if(value.contains("Rent")) {
						value = "FOR RENT";
					}
				}

				if(value == null) {
					value = "";
				}

				oneLine = oneLine + SEPARATOR + "\"" + value + "\"";
			}
			if(addLine) {
				oneLine = oneLine.substring(1);
				outputList.add(oneLine); 
			}
		}

		System.out.println("writting output");
		Utils.writeListToFile("C:\\My Stuffs\\java_develop\\file\\dwitzel\\homersvp_v7.csv", "UTF-8", outputList, Consts.LINE_SEPARATOR_LINUX);
	}

	public String removeChar(String oldString) {
		String newString = oldString.replace("\"", "");
		return newString;
	}

}
