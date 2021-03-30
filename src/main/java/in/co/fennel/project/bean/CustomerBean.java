package in.co.fennel.project.bean;

import java.util.Date;

public class CustomerBean extends BaseBean{

	private String userName;
	private String firstName;
	private String surName;
	private Date Dob;
	private String professionalSocialCategory;
	private String address;
	private String phoneNo;
	private String emailID;
	private String commercialCategories;
	private String password;
	private int roleid;
	private String category;
	
	
	
	
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public Date getDob() {
		return Dob;
	}
	public void setDob(Date dob) {
		Dob = dob;
	}
	public String getProfessionalSocialCategory() {
		return professionalSocialCategory;
	}
	public void setProfessionalSocialCategory(String professionalSocialCategory) {
		this.professionalSocialCategory = professionalSocialCategory;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getCommercialCategories() {
		return commercialCategories;
	}
	public void setCommercialCategories(String commercialCategories) {
		this.commercialCategories = commercialCategories;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	
	
}
