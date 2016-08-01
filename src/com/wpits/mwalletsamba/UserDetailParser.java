package com.wpits.mwalletsamba;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import com.wpits.mwalletsamba.ModelMinistatment;

public class UserDetailParser {
	public ArrayList<ModelUserData>  parsedata(String jsonData){
		try {
			System.out.println(jsonData);
			ArrayList<ModelUserData> ministatement = new ArrayList<ModelUserData>();

			//org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
			//JSONObject jsonObject = (JSONObject)jsonParser.parse(jsonData);
			JSONObject jsonObject = new JSONObject(jsonData);
			org.json.JSONArray cast = jsonObject.getJSONArray("jsonArray");
			for (int i=0; i<cast.length(); i++) {
				JSONObject value = cast.getJSONObject(i);
				String 	id = (String)value.get("id");
				String msidn_no = (String)value.get("msidn_no");
				String username = (String)value.get("username");
				String email = (String)value.get("email");
				String address = (String)value.get("address");
				String otp = (String)value.get("otp");
				String pin = (String)value.get("pin");
				String ammount = (String)value.get("ammount");
				String gcm_id = (String)value.get("gcm_id");
				String nrc = (String)value.get("nrc");
				String dob = (String)value.get("dob");
				String alternativeno = (String)value.get("alternativeno");
				String type = (String)value.get("type");
				String createddate = (String)value.get("createddate");

				ministatement.add(getuserdetail(id, msidn_no, username, email, address, otp, pin, ammount, gcm_id, nrc, dob, alternativeno, type, createddate));

			}

			return ministatement;

		} catch (Exception e) {

			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	private ModelUserData getuserdetail(String id, String msidn_no, String username,
			String email, String address, String otp, String pin,
			String ammount, String gcm_id, String nrc, String dob,
			String alternativeno, String type, String createddate)
	{
		return new ModelUserData(id, msidn_no, username, email, address, otp, pin, ammount, gcm_id, nrc, dob, alternativeno, type, createddate);


	}
}
