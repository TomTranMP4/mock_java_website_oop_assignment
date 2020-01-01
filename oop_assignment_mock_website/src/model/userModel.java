package model;

public class userModel {
	private int userID;
	private String firstName;
	private String lastName;
	private String contactNumber;
	private String email;
	private String about;
	private boolean userEditFlag;
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public boolean isUserEditFlag() {
		return userEditFlag;
	}
	public void setUserEditFlag(boolean userEditFlag) {
		this.userEditFlag = userEditFlag;
	}
	
}
