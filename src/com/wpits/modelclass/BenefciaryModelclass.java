package com.wpits.modelclass;

public class BenefciaryModelclass {

	private	String id;
	private	String benefmobile;
	private String benefname;
	private  String benefnickname;
	private String benefemail ;
	private String type;

	public BenefciaryModelclass(String id,String benefmobile,String benefname,String benefnickname,String benefemail,String type) {
		this.id=id;
		this.benefmobile=benefmobile;
		this.benefname=benefname;
		this.benefnickname=benefnickname;
		this.benefemail=benefemail;
		this.type=type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBenefmobile() {
		return benefmobile;
	}

	public void setBenefmobile(String benefmobile) {
		this.benefmobile = benefmobile;
	}

	public String getBenefname() {
		return benefname;
	}

	public void setBenefname(String benefname) {
		this.benefname = benefname;
	}

	public String getBenefnickname() {
		return benefnickname;
	}

	public void setBenefnickname(String benefnickname) {
		this.benefnickname = benefnickname;
	}

	public String getBenefemail() {
		return benefemail;
	}

	public void setBenefemail(String benefemail) {
		this.benefemail = benefemail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	

}
