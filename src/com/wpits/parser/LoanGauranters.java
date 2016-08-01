package com.wpits.parser;

import java.io.Serializable;

public class LoanGauranters  implements Serializable {
String gauranter_msisdn;
String gauranter_ammount;


public LoanGauranters(String gauranter_msisdn, String gauranter_ammount) {
	super();
	this.gauranter_msisdn = gauranter_msisdn;
	this.gauranter_ammount = gauranter_ammount;
}

public LoanGauranters() {
	// TODO Auto-generated constructor stub
}

public String getGauranter_msisdn() {
	return gauranter_msisdn;
}
public void setGauranter_msisdn(String gauranter_msisdn) {
	this.gauranter_msisdn = gauranter_msisdn;
}
public String getGauranter_ammount() {
	return gauranter_ammount;
}
public void setGauranter_ammount(String gauranter_ammount) {
	this.gauranter_ammount = gauranter_ammount;
}

}
