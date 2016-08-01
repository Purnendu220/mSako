package com.wpits.parser;

public class LoanTypeList {

	String loan_type_id;
	String loan_type_name;
	String loan_limit;
	String loan_intrest;
	String loan_tenure;
	String own_reserve_amount;
	String admin_fee;


	public LoanTypeList(String loan_type_id, String loan_type_name,
			String loan_limit, String loan_intrest, String loan_tenure,
			String own_reserve_amount, String admin_fee) {
		super();
		this.loan_type_id = loan_type_id;
		this.loan_type_name = loan_type_name;
		this.loan_limit = loan_limit;
		this.loan_intrest = loan_intrest;
		this.loan_tenure = loan_tenure;
		this.own_reserve_amount = own_reserve_amount;
		this.admin_fee = admin_fee;
	}
	public String getLoan_type_id() {
		return loan_type_id;
	}
	public void setLoan_type_id(String loan_type_id) {
		this.loan_type_id = loan_type_id;
	}
	public String getLoan_type_name() {
		return loan_type_name;
	}
	public void setLoan_type_name(String loan_type_name) {
		this.loan_type_name = loan_type_name;
	}
	public String getLoan_limit() {
		return loan_limit;
	}
	public void setLoan_limit(String loan_limit) {
		this.loan_limit = loan_limit;
	}
	public String getLoan_intrest() {
		return loan_intrest;
	}
	public void setLoan_intrest(String loan_intrest) {
		this.loan_intrest = loan_intrest;
	}
	public String getLoan_tenure() {
		return loan_tenure;
	}
	public void setLoan_tenure(String loan_tenure) {
		this.loan_tenure = loan_tenure;
	}
	public String getOwn_reserve_amount() {
		return own_reserve_amount;
	}
	public void setOwn_reserve_amount(String own_reserve_amount) {
		this.own_reserve_amount = own_reserve_amount;
	}
	public String getAdmin_fee() {
		return admin_fee;
	}
	public void setAdmin_fee(String admin_fee) {
		this.admin_fee = admin_fee;
	}

}
