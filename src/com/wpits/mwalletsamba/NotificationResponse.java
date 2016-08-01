package com.wpits.mwalletsamba;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.wpits.modelclass.NotificationModel;

public class NotificationResponse implements Serializable {
	private String resultCode;
	private String resultStatus;
	private ArrayList<NotificationModel> model; 

	public NotificationResponse(String strjsondata)
	{

		// TODO Auto-generated constructor stub
		strjsondata = strjsondata.trim();
		System.out.println(strjsondata);
		JSONParser jsonParser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(strjsondata);
			resultCode=(String)jsonObject.get("result_code");
			resultStatus=(String)jsonObject.get("result_status");
			if (resultCode.equalsIgnoreCase("200")) {
				ArrayList<NotificationModel> notification=new ArrayList<NotificationModel>();
				JSONArray arr=(JSONArray)jsonObject.get("notificationlist");
				for (int i = 0; i < arr.size(); i++) {
					JSONObject obj=(JSONObject)arr.get(i);
					NotificationModel notificationmodel=new NotificationModel((String)obj.get("notificationid"), (String)obj.get("notificationsubject"), (String)obj.get("notification"), (String)obj.get("notificationdate"), "");
					notification.add(notificationmodel);
				}
				model=notification;
			}



		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultCode="4444";
			resultStatus="Something went wrong.";
		}


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

	public ArrayList<NotificationModel> getModel() {
		return model;
	}

	public void setModel(ArrayList<NotificationModel> model) {
		this.model = model;
	}


}
