package convert.csv;

public class Contacts {
	
	private String mEmail = null;
	
	public Contacts(String email) {
		mEmail = email;
		
	}
	public String getmEmail() {
		return mEmail;
	}

	private String mName = null;
	public String getmName() {
		return mName;
	}
	public void setmLastName(String mName) {
		this.mName = mName;
	}
	private String mPrename = null;
	public String getmPrename() {
		return mPrename;
	}
	public void setmPrename(String mPrename) {
		this.mPrename = mPrename;
	}
	private String mPhone = null;
	public String getmPhone() {
		return mPhone;
	}
	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}
	
	
	

}
