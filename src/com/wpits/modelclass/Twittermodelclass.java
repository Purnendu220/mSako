package com.wpits.modelclass;

public class Twittermodelclass {
	

	String id;
	String benefname;
	String twitterid;
	String time;
	String screenname;
	public Twittermodelclass(String id, String benefname,String screenname, String twitterid,
			String time) {
		this.id = id;
		this.benefname = benefname;
		this.twitterid = twitterid;
		this.time = time;
		this.screenname=screenname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBenefname() {
		return benefname;
	}
	public void setBenefname(String benefname) {
		this.benefname = benefname;
	}
	public String getTwitterid() {
		return twitterid;
	}
	public void setTwitterid(String twitterid) {
		this.twitterid = twitterid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getScreenname() {
		return screenname;
	}
	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}
	
}
