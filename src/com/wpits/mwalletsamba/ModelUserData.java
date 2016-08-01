package com.wpits.mwalletsamba;

public class ModelUserData {
	private String id;
	private String msidn_no;
	private String username;
	private String email;
	private String address;
	private String otp;
	private String pin;
	private String ammount;
	private String gcm_id;
	private String nrc;
	private String dob;
	private String alternativeno;
	private String type;
	private String createddate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsidn_no() {
		return msidn_no;
	}
	public void setMsidn_no(String msidn_no) {
		this.msidn_no = msidn_no;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getAmmount() {
		return ammount;
	}
	public void setAmmount(String ammount) {
		this.ammount = ammount;
	}
	public String getGcm_id() {
		return gcm_id;
	}
	public void setGcm_id(String gcm_id) {
		this.gcm_id = gcm_id;
	}
	public String getNrc() {
		return nrc;
	}
	public void setNrc(String nrc) {
		this.nrc = nrc;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAlternativeno() {
		return alternativeno;
	}
	public void setAlternativeno(String alternativeno) {
		this.alternativeno = alternativeno;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public ModelUserData(String id, String msidn_no, String username,
			String email, String address, String otp, String pin,
			String ammount, String gcm_id, String nrc, String dob,
			String alternativeno, String type, String createddate) {
		super();
		this.id = id;
		this.msidn_no = msidn_no;
		this.username = username;
		this.email = email;
		this.address = address;
		this.otp = otp;
		this.pin = pin;
		this.ammount = ammount;
		this.gcm_id = gcm_id;
		this.nrc = nrc;
		this.dob = dob;
		this.alternativeno = alternativeno;
		this.type = type;
		this.createddate = createddate;
	}


	

}
