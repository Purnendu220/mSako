package com.wpits.mwalletsamba;

import java.io.Serializable;
import java.util.HashMap;

public class LoanApplicationObject implements Serializable {
	String mobileno;
	String loanamount;
	HashMap<String, String> mapgauranter;
	String loantype;
	String loantenure;
	String session_id;
	String usertype;
	String ownreserveamount;


	public LoanApplicationObject(String mobileno, String loanamount,
			HashMap<String, String> mapgauranter, String loantype,
			String loantenure, String session_id, String usertype,String ownreserveamount) {
		super();
		this.mobileno = mobileno;
		this.loanamount = loanamount;
		this.mapgauranter = mapgauranter;
		this.loantype = loantype;
		this.loantenure = loantenure;
		this.session_id = session_id;
		this.usertype = usertype;
		this.ownreserveamount=ownreserveamount;
	}


	public String getOwnreserveamount() {
		return ownreserveamount;
	}


	public void setOwnreserveamount(String ownreserveamount) {
		this.ownreserveamount = ownreserveamount;
	}


	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getLoanamount() {
		return loanamount;
	}
	public void setLoanamount(String loanamount) {
		this.loanamount = loanamount;
	}
	public HashMap<String, String> getMapgauranter() {
		return mapgauranter;
	}
	public void setMapgauranter(HashMap<String, String> mapgauranter) {
		this.mapgauranter = mapgauranter;
	}
	public String getLoantype() {
		return loantype;
	}
	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}
	public String getLoantenure() {
		return loantenure;
	}
	public void setLoantenure(String loantenure) {
		this.loantenure = loantenure;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

}
