package convert.csv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Consts;
import utils.Utils;

public class ConvertCSVToCSV {
	private List<String> mOutput = new ArrayList<>();
	
	private List<String> mOutputWithMissing = new ArrayList<>();
	
	private Map<String, Contacts> mEMailContactMap = new HashMap<String, Contacts>();
	

	public static void main(String[] args) {
		ConvertCSVToCSV instance = new ConvertCSVToCSV();
		instance.run();
	}

	private void readREIContacts() {
		List<String> output = new ArrayList<>();
		output.add("first,last,email,phone");

		List<String> fileAsList = Utils.readListFromFile("c:\\My Stuffs\\java_develop\\file\\REI_Contacts.csv");

		for(int i=1; i < fileAsList.size(); i++) {
			String line = fileAsList.get(i);

			String[] split = line.split(",");
			if(split.length == 4) {
				
				String email = split[2];
				String lastName = split[0];
				String preName = split[1];
				String phone = split[3];
				phone = correctPhone(phone);
				
				addInfoToContacts(email, lastName, preName, phone);
			}
		}
	}
	
	private void addInfoToContacts(String email, String lastName, String preName, String phone) {
		Contacts contact = null;
		if(!(email.length() <= 1)) {
			if(email.equals("\"alehman@lehmanlawpllc.com>\\")) {
				return;
			}
			if(!email.contains("@")) {
				return;
			}
			if(mEMailContactMap.containsKey(email)) {
				contact = mEMailContactMap.get(email);
			} else {
				contact = new Contacts(email);
				mEMailContactMap.put(email, contact);
			}
		} else {
			//System.out.println(email);
			return;
		}
		
		if(contact.getmName() == null) {
			if(!(lastName.length() <= 1)) {
				contact.setmLastName(lastName);
			}
		}
		
		if(contact.getmPrename() == null) {
			if(!(preName.length() <= 1)) {
				contact.setmPrename(preName);
			}
		}
		
		if(contact.getmPhone() == null) {
			if(!(phone.length() <= 1)) {
				contact.setmPhone(phone);
			}
		}
		
		if(contact.getmPhone() != null && contact.getmPhone().length() != 10) {
			if(phone.length() == 10) {
				contact.setmPhone(phone);
			}
		}
		
	}
	
	private void writeOutputFile() {
		for(Contacts contact : mEMailContactMap.values()) {
			String email = contact.getmEmail();
			String prename = contact.getmPrename();
			String lastName = contact.getmName();
			String phone = contact.getmPhone();
			
			if(prename == null || lastName == null || phone == null) {
				if(prename == null) {
					prename = "";
				}
				if(lastName == null) {
					lastName = "";
				}
				if(phone == null) {
					phone = "";
				} else {
					if(phone.length() == 10) {
						phone = "(" + phone.charAt(0) + phone.charAt(1) + phone.charAt(2) + ")" +
								phone.charAt(3) + phone.charAt(4) + phone.charAt(5) + "-" +
								phone.charAt(6) + phone.charAt(7) + phone.charAt(8) +  phone.charAt(9);
					}
				}
				mOutputWithMissing.add(prename + "," + lastName + "," + email + "," + phone);
				continue;
			}
			
			if(phone.length() != 10) {
				System.out.println("phone error: " + phone + " length: " + phone.length() + " email: " + email);
				mOutputWithMissing.add(prename + "," + lastName + "," + email + "," + "");
				continue;
			}
			
			phone = "(" + phone.charAt(0) + phone.charAt(1) + phone.charAt(2) + ")" +
					phone.charAt(3) + phone.charAt(4) + phone.charAt(5) + "-" +
					phone.charAt(6) + phone.charAt(7) + phone.charAt(8) +  phone.charAt(9);
					
			
			mOutput.add(prename + "," + lastName + "," + email + "," + phone);
		}
		
		Utils.writeListToFile("c:\\My Stuffs\\java_develop\\file\\new_contacts.csv", Consts.ENCODING_UTF8, mOutput, Consts.LINE_SEPARATOR_LINUX);
		Utils.writeListToFile("c:\\My Stuffs\\java_develop\\file\\new_contacts_missing.csv", Consts.ENCODING_UTF8, mOutputWithMissing, Consts.ENCODING_UTF8);
	}
	
	private String correctPhone (String oldPhone) {
		String phone = oldPhone.replaceAll("[^0-9]", "");
		
		if(phone.length() > 10 && phone.startsWith("1")) {
			phone = phone.substring(1);
		}
		
		return phone;
	}


	private void readAppleContacts() {
		List<String> fileAsList = Utils.readListFromFile("c:\\My Stuffs\\java_develop\\file\\iCloud_Contacts_csv_file.csv");

		for(int i=1; i < fileAsList.size(); i++) {
			String line = fileAsList.get(i);

			String[] split = line.split(",");
			if(split.length > 23) {
				String email = split[17];
				String lastName = split[5];
				String preName = split[6];
				
				String phone = split[21];
				phone = correctPhone(phone);
				
				if(phone.length() != 10) {
					phone = correctPhone(split[20]);
				}
				
				if(phone.length() != 10) {
					phone = correctPhone(split[22]);
				}
				
				if(phone.length() != 10) {
					phone = correctPhone(split[23]);
				}
				
				addInfoToContacts(email, lastName, preName, phone);
			}
		}
	}
	
	
	private void run() {
		mOutput.add("first,last,email,phone");
		mOutputWithMissing.add("first,last,email,phone");

		readREIContacts();
		readGoogleContacts();
		readAppleContacts();
		
		readREIContacts();
		readGoogleContacts();
		readAppleContacts();
		
		
		writeOutputFile();

	}

	private void readGoogleContacts(){

		List<String> fileAsList = Utils.readListFromFile("c:\\My Stuffs\\java_develop\\file\\Google_Contacts.csv");

		for(int i=1; i < fileAsList.size(); i++) {
			String line = fileAsList.get(i);

			String[] split = line.split(",");
			if(split.length == 88) {
				String email = split[14];
				String lastName = split[2];
				String preName = split[0];
				String phone = correctPhone(split[20]);
				
				if(phone.length() != 10) {
					phone = correctPhone(split[17]);
				}
				
				if(phone.length() != 10) {
					phone = correctPhone(split[18]);
				}
				
				if(phone.length() != 10) {
					phone = correctPhone(split[19]);
				}
				
				addInfoToContacts(email, lastName, preName, phone);
			}
		}
	}

}
