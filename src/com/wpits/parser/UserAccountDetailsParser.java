package com.wpits.parser;

import java.io.Serializable;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UserAccountDetailsParser implements Serializable {
	String result_code;
	String result_status;
	String wallet_ammount;
	String have_loan_account;
	String loan_acc_no;
	String loan_ammount;
	String loan_type;
	String reserve_amount;




	public UserAccountDetailsParser(String result_code, String result_status,
			String wallet_ammount, String have_loan_account,
			String loan_acc_no, String loan_ammount, String loan_type,
			String reserve_amount) {
		super();
		this.result_code = result_code;
		this.result_status = result_status;
		this.wallet_ammount = wallet_ammount;
		this.have_loan_account = have_loan_account;
		this.loan_acc_no = loan_acc_no;
		this.loan_ammount = loan_ammount;
		this.loan_type = loan_type;
		this.reserve_amount = reserve_amount;
	}
	public UserAccountDetailsParser(String jsonresponse)
	{
		JSONParser jsonParser = new JSONParser();

		try {


			JSONObject jsonObject = (JSONObject)jsonParser.parse(jsonresponse);
			result_code=(String)jsonObject.get("result_code");
			result_status=(String)jsonObject.get("result_status");
			if (result_code.equalsIgnoreCase("200")) {
				JSONObject obj=(JSONObject)jsonObject.get("user_acc_details");


				wallet_ammount=(String)obj.get("wallet_ammount");
				have_loan_account=(String)obj.get("have_loan_account");
				if (have_loan_account.equalsIgnoreCase("Y")) {
					loan_acc_no=(String)obj.get("loan_acc_no");
					loan_ammount=(String)obj.get("loan_ammount");
					loan_type=(String)obj.get("loan_type");
				}
				reserve_amount=(String)obj.get("reserve_amount");




			} 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result_code="4444";
			result_status=e.getMessage();
		}
	}

	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getResult_status() {
		return result_status;
	}
	public void setResult_status(String result_status) {
		this.result_status = result_status;
	}
	public String getWallet_ammount() {
		return wallet_ammount;
	}
	public void setWallet_ammount(String wallet_ammount) {
		this.wallet_ammount = wallet_ammount;
	}
	public String getHave_loan_account() {
		return have_loan_account;
	}
	public void setHave_loan_account(String have_loan_account) {
		this.have_loan_account = have_loan_account;
	}
	public String getLoan_acc_no() {
		return loan_acc_no;
	}
	public void setLoan_acc_no(String loan_acc_no) {
		this.loan_acc_no = loan_acc_no;
	}
	public String getLoan_ammount() {
		return loan_ammount;
	}
	public void setLoan_ammount(String loan_ammount) {
		this.loan_ammount = loan_ammount;
	}
	public String getLoan_type() {
		return loan_type;
	}
	public void setLoan_type(String loan_type) {
		this.loan_type = loan_type;
	}
	public String getReserve_amount() {
		return reserve_amount;
	}
	public void setReserve_amount(String reserve_amount) {
		this.reserve_amount = reserve_amount;
	}



}
