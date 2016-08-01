package com.wpits.parser;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LoanApplicationRequestParser  implements Serializable
{
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



	public ArrayList<listoffaultygauranter> getListfaultygauranter() {
		return listfaultygauranter;
	}



	public void setListfaultygauranter(
			ArrayList<listoffaultygauranter> listfaultygauranter) {
		this.listfaultygauranter = listfaultygauranter;
	}



	private String result_code;


	private String result_status;


	private ArrayList<listoffaultygauranter> listfaultygauranter;



	public LoanApplicationRequestParser(String strjsondata) {

		strjsondata = strjsondata.trim();
		JSONParser jsonParser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(strjsondata);
			result_code=(String)jsonObject.get("result_code");
			result_status=(String)jsonObject.get("result_status");
			if (result_code.equalsIgnoreCase("200"))//ok
			{


			}
			else if(result_code.equalsIgnoreCase("1111"))//784873487 is  not a subscriber of msako 
			{
				JSONArray arr=(JSONArray)jsonObject.get("notfoundlist");
				ArrayList<listoffaultygauranter> gauaranterlist=new ArrayList<listoffaultygauranter>();
				for (int i = 0; i < arr.size(); i++) {
					JSONObject obj=(JSONObject) arr.get(i);
					gauaranterlist.add(new listoffaultygauranter((String)obj.get("gauranterstatus")));
				}
				listfaultygauranter=gauaranterlist;
			}
			else if(result_code.equalsIgnoreCase("2222"))//784873487 have not enough balance
			{

				JSONArray arr=(JSONArray)jsonObject.get("notfoundlist");
				ArrayList<listoffaultygauranter> gauaranterlist=new ArrayList<listoffaultygauranter>();
				for (int i = 0; i < arr.size(); i++) {
					JSONObject obj=(JSONObject) arr.get(i);
					gauaranterlist.add(new listoffaultygauranter((String)obj.get("gauranterstatus")));
				}

				listfaultygauranter=gauaranterlist;


			}
			else if(result_code.equalsIgnoreCase("5555"))//784873487 is not approved yet
			{

				JSONArray arr=(JSONArray)jsonObject.get("notfoundlist");
				ArrayList<listoffaultygauranter> gauaranterlist=new ArrayList<listoffaultygauranter>();
				for (int i = 0; i < arr.size(); i++) {
					JSONObject obj=(JSONObject) arr.get(i);
					gauaranterlist.add(new listoffaultygauranter((String)obj.get("gauranterstatus")));
				}
				listfaultygauranter=gauaranterlist;



			}
			else if(result_code.equalsIgnoreCase("3333"))//something went wrong
			{


			}
			else if(result_code.equalsIgnoreCase("6666"))//already applied for loan
			{


			}


		}
		catch(Exception e)
		{
			result_code="4444";
			result_status=e.getMessage();
		}

	}


}