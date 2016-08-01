package com.wpits.parser;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LoanRequstResponse  implements Serializable
{
	private String result_code;

	private Loan_detail loan_detail;

	private String result_status;






	public LoanRequstResponse(String strjsondata) {

		strjsondata = strjsondata.trim();
		JSONParser jsonParser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(strjsondata);
			result_code=(String)jsonObject.get("result_code");
			result_status=(String)jsonObject.get("result_status");
			if (result_code.equalsIgnoreCase("200")) {
				JSONObject jsonObjectloandetail=(JSONObject) jsonObject.get("loan_detail");
				Loan_detail detail=new Loan_detail();
				detail.setLoan_id((String)jsonObjectloandetail.get("loan_id"));
				detail.setLoan_acountstatus((String)jsonObjectloandetail.get("loan_acountstatus"));
				detail.setLoan_adminfee((String)jsonObjectloandetail.get("loan_adminfee"));
				detail.setLoan_gauranterdetail((String)jsonObjectloandetail.get("loan_gauranterdetail"));
				detail.setLoan_ownreservedamount((String)jsonObjectloandetail.get("loan_ownreservedamount"));
				if (jsonObjectloandetail.get("loan_gauranterdetail").toString().equalsIgnoreCase("Y")) {
					JSONArray gauranterlist=(JSONArray)jsonObjectloandetail.get("gauranter");
					ArrayList<Gauranter> gauranterarrlist=new ArrayList<Gauranter>();
					for (int i = 0; i < gauranterlist.length(); i++) {
						try {
							JSONObject obj=(JSONObject)gauranterlist.get(i);

							gauranterarrlist.add(new Gauranter((String)obj.get("gauranter_status"), (String)obj.get("gauranter_number"), (String)obj.get("gauranter_status_acc")));

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					detail.setGauranter(gauranterarrlist);

				}
				loan_detail=detail;


			}


		}
		catch(Exception e)
		{
			result_code="4444";
			result_status=e.getMessage();
		}

	}

	public String getResult_code ()
	{
		return result_code;
	}

	public void setResult_code (String result_code)
	{
		this.result_code = result_code;
	}

	public Loan_detail getLoan_detail ()
	{
		return loan_detail;
	}

	public void setLoan_detail (Loan_detail loan_detail)
	{
		this.loan_detail = loan_detail;
	}

	public String getResult_status ()
	{
		return result_status;
	}

	public void setResult_status (String result_status)
	{
		this.result_status = result_status;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [result_code = "+result_code+", loan_detail = "+loan_detail+", result_status = "+result_status+"]";
	}
}