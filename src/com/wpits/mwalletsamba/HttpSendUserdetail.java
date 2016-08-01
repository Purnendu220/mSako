package com.wpits.mwalletsamba;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class HttpSendUserdetail {
 Context context;
	static String responsestring;

	public static String senddetail(String first_name,String lastname,String dob,String fathername,String mothername,String middlename,String lanuage,String mobile,String idtype,String idnumber,String email
			,String gender,String houseno,String Streetno,String locality,String city,String district,String state,String country,Context context)
	{
		/*editor.putString("mobile_no", parse.getMobile());
 		editor.putString("otp", mpin);
 		editor.putString("name", parse.getAgentName());
 		editor.putString("mPin", mpin);
 		editor.putString("alternate_mobile",parse.getMobile());
 		editor.putString("dob", parse.getDob());
 		editor.putString("type", "Agent");
 		editor.putString("email", parse.getAgentMail());
 		editor.putString("agentid", parse.getAgentIdProof());
 		
 		editor.putString("roleid", parse.getRoleId());
 		editor.putString("imageurl", parse.getImage());
 		editor.putString("parentid", parse.getAgentParentId());
 		editor.putString("agentid", parse.getAgentId());
 		editor.putString("address", parse.getAddressDescription());*/
		
		try {
			  SharedPreferences pref =context.getSharedPreferences("MySettings", 0); 
				 String agentid =pref.getString("agentid", "");
					String userid =pref.getString("mobile_no", "");
					 String name =pref.getString("name", "");
				String status=pref.getString("status", "1");
			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK1+"create_agent");

			
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(10);
			nameValuePairs.add(new BasicNameValuePair("fname", first_name));
			nameValuePairs.add(new BasicNameValuePair("mname", middlename));
			nameValuePairs.add(new BasicNameValuePair("lname", lastname));
			nameValuePairs.add(new BasicNameValuePair("father_name", fathername));
			nameValuePairs.add(new BasicNameValuePair("mother_name", mothername));
			nameValuePairs.add(new BasicNameValuePair("gender", gender));
			nameValuePairs.add(new BasicNameValuePair("dob", dob));
			
			nameValuePairs.add(new BasicNameValuePair("present_street_no", houseno));
			nameValuePairs.add(new BasicNameValuePair("present_house_no", Streetno));
			nameValuePairs.add(new BasicNameValuePair("present_locality", locality));
			nameValuePairs.add(new BasicNameValuePair("present_city", city));
            nameValuePairs.add(new BasicNameValuePair("present_districts", district));
			nameValuePairs.add(new BasicNameValuePair("present_state", state));
			nameValuePairs.add(new BasicNameValuePair("present_country", country));
			                                                                                                                                                                                                              
			
			nameValuePairs.add(new BasicNameValuePair("email", email));
			nameValuePairs.add(new BasicNameValuePair("mobile1", mobile));
		
			
			nameValuePairs.add(new BasicNameValuePair("levels", "5"));
			nameValuePairs.add(new BasicNameValuePair("service_provider_id", "1"));
			nameValuePairs.add(new BasicNameValuePair("roles", "3"));             
			nameValuePairs.add(new BasicNameValuePair("status", "1"));
			
			
			nameValuePairs.add(new BasicNameValuePair("agent_proof_id", idtype));
			nameValuePairs.add(new BasicNameValuePair("parent_agent_id_proof_value", idnumber));
			
			
			nameValuePairs.add(new BasicNameValuePair("creator_agent_id", agentid));
			nameValuePairs.add(new BasicNameValuePair("creator_user_id", userid));
			nameValuePairs.add(new BasicNameValuePair("parent_agent_id", agentid));
			nameValuePairs.add(new BasicNameValuePair("parent_agent_status_id", status));

		


			
			System.out.println(status);


			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// httpclient.execute(httppost);
			HttpResponse response = httpclient.execute(httppost);
			//Thread.sleep(10000);
			HttpEntity rp = response.getEntity();
			 responsestring =  EntityUtils.toString(rp);
			
				System.out.println(responsestring);
				
		}
		catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return responsestring;
		
	}
}
