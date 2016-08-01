package com.wpits.parser;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LoanTypeDetailParser {
	private String result_code;
	private String result_status;
	private ArrayList<LoanTypeList> loan_typelist;


	public LoanTypeDetailParser(String jsonstr)
	{
		jsonstr = jsonstr.trim();
		JSONParser jsonParser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(jsonstr);

			result_code=(String)jsonObject.get("result_code");
			result_status=(String)jsonObject.get("result_status");
			if (result_code.equalsIgnoreCase("200")) {
				JSONArray jsonObjectloandetail=(JSONArray) jsonObject.get("loantypearr");
				ArrayList<LoanTypeList> listloantype=new ArrayList<LoanTypeList>();
				if (jsonObjectloandetail.size()>0) {
					for (int i = 0; i < jsonObjectloandetail.size(); i++) {
						JSONObject obj=(JSONObject) jsonObjectloandetail.get(i);

						listloantype.add(new LoanTypeList((String)obj.get("loan_type_id"), (String)obj.get("loan_type_name"), (String)obj.get("loan_limit"), (String)obj.get("loan_intrest"), (String)obj.get("loan_tenure"), (String)obj.get("own_reserve_amount"), (String)obj.get("admin_fee")));


					}

				}
				loan_typelist=listloantype;
			}

		}
		catch(Exception e){
			e.printStackTrace();
			result_code="400";
			result_status="Failed";

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


	public ArrayList<LoanTypeList> getLoan_typelist() {
		return loan_typelist;
	}


	public void setLoan_typelist(ArrayList<LoanTypeList> loan_typelist) {
		this.loan_typelist = loan_typelist;
	}




}
