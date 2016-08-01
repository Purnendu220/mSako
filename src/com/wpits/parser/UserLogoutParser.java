package com.wpits.parser;

import java.io.Serializable;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UserLogoutParser  implements Serializable {

	private String resultCode;
	private String resultStatus;
	private String Sessionid;







	public UserLogoutParser(String strjsondata) {
		// TODO Auto-generated constructor stub
		strjsondata = strjsondata.trim();
		System.out.println(strjsondata);
		JSONParser jsonParser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(strjsondata);
			resultCode=(String)jsonObject.get("result_code");
			resultStatus=(String)jsonObject.get("result_status");
			Sessionid=(String)jsonObject.get("session_id");
		


		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultCode="4444";
			resultStatus="Logout Failed";
		}

	}





	public UserLogoutParser(String resultCode, String resultStatus,
			String sessionid) {
		super();
		this.resultCode = resultCode;
		this.resultStatus = resultStatus;
		Sessionid = sessionid;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getSessionid() {
		return Sessionid;
	}
	public void setSessionid(String sessionid) {
		Sessionid = sessionid;
	}


}
