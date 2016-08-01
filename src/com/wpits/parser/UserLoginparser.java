
package com.wpits.parser;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.util.JsonReader;


public class UserLoginparser implements Serializable{

	private String resultCode;
	private String resultStatus;
	private String Sessionid;
	private String user_type;
	private String otp;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	private UserDetails userDetails;

	/**
	 * 
	 * @return
	 *     The resultCode
	 */


	public String getResultCode() {
		return resultCode;
	}

	public String getSessionid() {
		return Sessionid;
	}

	public void setSessionid(String sessionid) {
		Sessionid = sessionid;
	}

	public UserLoginparser(String strjsondata) {
		// TODO Auto-generated constructor stub
		strjsondata = strjsondata.trim();
		System.out.println(strjsondata);
		JSONParser jsonParser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(strjsondata);
			resultCode=(String)jsonObject.get("result_code");
			resultStatus=(String)jsonObject.get("result_status");
			Sessionid=(String)jsonObject.get("session_id");
			user_type=(String)jsonObject.get("user_type");
			if (resultCode.equalsIgnoreCase("200")) {
				otp=(String)jsonObject.get("otp");

				JSONObject jsonObjectuser=(JSONObject) jsonObject.get("user_details");
				UserDetails userdetails=new UserDetails();
				userdetails.setUserName((String)jsonObjectuser.get("user_name"));
				userdetails.setUserId((String)jsonObjectuser.get("user_id"));
				userdetails.setDistributerId((String)jsonObjectuser.get("distributer_id"));
				userdetails.setUserImage((String)jsonObjectuser.get("user_image"));
				userdetails.setUserDob((String)jsonObjectuser.get("user_dob"));
				userdetails.setUserGender((String)jsonObjectuser.get("user_gender"));
				userdetails.setUserMobile((String)jsonObjectuser.get("user_mobile"));
				userdetails.setUserWalletbalance((String)jsonObjectuser.get("user_walletbalance"));
				userdetails.setUseridNumber((String)jsonObjectuser.get("userid_number"));
				userdetails.setUserAddress((String)jsonObjectuser.get("user_address"));
				userdetails.setIsloanrequested((String)jsonObjectuser.get("isloanrequsested"));
				userdetails.setUnreadnotifications((String)jsonObjectuser.get("unreadnotifications"));

				userdetails.setIsloanapproved((String)jsonObjectuser.get("isloanapproved"));


				userdetails.setUser_email_id((String)jsonObjectuser.get("user_email_id"));

				userDetails=userdetails;
			}
			if (resultCode.equalsIgnoreCase("108")) {
				otp=(String)jsonObject.get("otp");

				JSONObject jsonObjectuser=(JSONObject) jsonObject.get("user_details");
				UserDetails userdetails=new UserDetails();
				userdetails.setUserId((String)jsonObjectuser.get("user_id"));
				userdetails.setUserMobile((String)jsonObjectuser.get("user_mobile"));

				userDetails=userdetails;

			}
			if(resultCode.equalsIgnoreCase("150"))
			{
				resultCode=(String)jsonObject.get("result_code");
				resultStatus=(String)jsonObject.get("result_status");
				Sessionid=(String)jsonObject.get("session_id");
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultCode="4444";
			resultStatus=e.getMessage();
		}

	}

	/**
	 * 
	 * @param resultCode
	 *     The result_code
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * 
	 * @return
	 *     The resultStatus
	 */
	public String getResultStatus() {
		return resultStatus;
	}

	/**
	 * 
	 * @param resultStatus
	 *     The result_status
	 */
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	/**
	 * 
	 * @return
	 *     The userDetails
	 */
	public UserDetails getUserDetails() {
		return userDetails;
	}

	/**
	 * 
	 * @param userDetails
	 *     The user_details
	 */
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
