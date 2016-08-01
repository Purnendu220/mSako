package com.wpits.parser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class VerificationParser {
	String result_code;
	String result_status;
	String session_id;
	String otp;


	public VerificationParser(String responseregcont) {
		responseregcont = responseregcont.trim();
		System.out.println(responseregcont);
		JSONParser jsonParser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(responseregcont);
			result_code=(String)jsonObject.get("result_code");
			result_status=(String)jsonObject.get("result_status");
			session_id=(String)jsonObject.get("session_id");
			otp=(String)jsonObject.get("otp");




		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result_status="4444";
			result_status="Logout Failed";
		}	}


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


	public String getSession_id() {
		return session_id;
	}


	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}


	public String getOtp() {
		return otp;
	}


	public void setOtp(String otp) {
		this.otp = otp;
	}

}
