package com.wpits.modelclass;

public class TwitterTransfer {
	private String id;
	private String benefname;
private	String screenname;
	private String twitterid;
private	String recammount;
private	String time;
public TwitterTransfer(String id, String benefname, String screenname,String twitterid, String recammount, String time) {
	this.id = id;
	this.benefname = benefname;
	this.screenname = screenname;
	this.twitterid = twitterid;
	this.recammount = recammount;
	this.time = time;
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
public String getScreenname() {
	return screenname;
}
public void setScreenname(String screenname) {
	this.screenname = screenname;
}
public String getTwitterid() {
	return twitterid;
}
public void setTwitterid(String twitterid) {
	this.twitterid = twitterid;
}
public String getRecammount() {
	return recammount;
}
public void setRecammount(String recammount) {
	this.recammount = recammount;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
}
