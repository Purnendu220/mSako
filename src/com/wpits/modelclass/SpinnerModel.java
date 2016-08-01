package com.wpits.modelclass;

import java.io.Serializable;

public class SpinnerModel implements Serializable {

	private  String loan_desc;
	private  String other_desc;


	public SpinnerModel(String loan_desc, String other_desc) {
		super();
		this.loan_desc = loan_desc;
		this.other_desc = other_desc;
	}
	public String getLoan_desc() {
		return loan_desc;
	}
	public void setLoan_desc(String loan_desc) {
		this.loan_desc = loan_desc;
	}
	public String getOther_desc() {
		return other_desc;
	}
	public void setOther_desc(String other_desc) {
		this.other_desc = other_desc;
	}


}