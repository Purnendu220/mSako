package com.wpits.parser;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LoanAccountDetailParser  implements Serializable
{



	private String result_code;


	private String result_status;
	private String have_loan_account;
	private String loan_acc_no;
	private String loan_amount;
	private String loan_type;
	private String last_emi_received;
	private String loan_account_ammount;
	private String loan_tenure;
	private String loan_accountstatus;
	private String own_reserve;
	private String admin_fee;
	private String loan_type_id;
	private ArrayList<LoanGauranterStatusList> gauranterlist;




	public LoanAccountDetailParser(String strjsondata) {

		strjsondata = strjsondata.trim();
		JSONParser jsonParser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(strjsondata);
			result_code=(String)jsonObject.get("result_code");
			result_status=(String)jsonObject.get("result_status");
			if (result_code.equalsIgnoreCase("200"))//ok
			{
				have_loan_account=(String)jsonObject.get("have_loan_account");
				loan_acc_no=(String)jsonObject.get("loan_acc_no");
				loan_amount=(String)jsonObject.get("loan_ammount");
				loan_type=(String)jsonObject.get("loan_type");
				last_emi_received=(String)jsonObject.get("last_emi_received");
				loan_account_ammount=(String)jsonObject.get("loan_account_ammount");
				loan_tenure=(String)jsonObject.get("loan_tenure");
				loan_accountstatus=(String)jsonObject.get("loan_accountstatus");
				own_reserve=(String)jsonObject.get("own_reserve");
				admin_fee=(String)jsonObject.get("admin_fee");
				loan_type_id=(String)jsonObject.get("loan_type_id");
				
				JSONArray arr=(JSONArray)jsonObject.get("gauranterlist");
				
				ArrayList<LoanGauranterStatusList> list=new ArrayList<LoanGauranterStatusList>();
				for (int i = 0; i < arr.size(); i++) {
					JSONObject obj=(JSONObject)arr.get(i);
					list.add(new LoanGauranterStatusList((String)obj.get("GuarantorReserveValue"), (String)obj.get("GuarantorMsisdn"), (String)obj.get("GuarantorFirstName"), (String)obj.get("GauranterAcceptence")));

				}

				gauranterlist=list;



			}



		}
		catch(Exception e)
		{
			e.printStackTrace();
			result_code="4444";
			result_status=e.getMessage();
		}

	}
	



	public String getLoan_type_id() {
		return loan_type_id;
	}




	public void setLoan_type_id(String loan_type_id) {
		this.loan_type_id = loan_type_id;
	}




	public String getAdmin_fee() {
		return admin_fee;
	}




	public void setAdmin_fee(String admin_fee) {
		this.admin_fee = admin_fee;
	}




	public String getOwn_reserve() {
		return own_reserve;
	}



	public void setOwn_reserve(String own_reserve) {
		this.own_reserve = own_reserve;
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



	public String getLoan_amount() {
		return loan_amount;
	}



	public void setLoan_amount(String loan_amount) {
		this.loan_amount = loan_amount;
	}



	public String getLoan_type() {
		return loan_type;
	}



	public void setLoan_type(String loan_type) {
		this.loan_type = loan_type;
	}



	public String getLast_emi_received() {
		return last_emi_received;
	}



	public void setLast_emi_received(String last_emi_received) {
		this.last_emi_received = last_emi_received;
	}



	public String getLoan_account_ammount() {
		return loan_account_ammount;
	}



	public void setLoan_account_ammount(String loan_account_ammount) {
		this.loan_account_ammount = loan_account_ammount;
	}



	public String getLoan_tenure() {
		return loan_tenure;
	}



	public void setLoan_tenure(String loan_tenure) {
		this.loan_tenure = loan_tenure;
	}



	public String getLoan_accountstatus() {
		return loan_accountstatus;
	}



	public void setLoan_accountstatus(String loan_accountstatus) {
		this.loan_accountstatus = loan_accountstatus;
	}



	public ArrayList<LoanGauranterStatusList> getGauranterlist() {
		return gauranterlist;
	}



	public void setGauranterlist(ArrayList<LoanGauranterStatusList> gauranterlist) {
		this.gauranterlist = gauranterlist;
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




}