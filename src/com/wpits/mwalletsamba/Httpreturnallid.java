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

public class Httpreturnallid {
	static String responsestring;
	public static String returnallid(String spId,String levelId,String agentId,String walletId,String phone_no,String device_id,String password ,Context context)
	{
		
		
		try {
			  SharedPreferences pref =context.getSharedPreferences("MySettings", 0); 
				 String agentid =pref.getString("agentid", "");
					String userid =pref.getString("mobile_no", "");
					 String name =pref.getString("name", "");
				
			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK1+"add_bank");

			
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
			nameValuePairs.add(new BasicNameValuePair("spId", spId));
			nameValuePairs.add(new BasicNameValuePair("levelId", levelId));
			nameValuePairs.add(new BasicNameValuePair("agentId", agentId));
			nameValuePairs.add(new BasicNameValuePair("walletId", walletId));
			nameValuePairs.add(new BasicNameValuePair("phone_no", phone_no));
			nameValuePairs.add(new BasicNameValuePair("device_id", device_id));
			nameValuePairs.add(new BasicNameValuePair("password", password));
			
		
			//nameValuePairs.add(new BasicNameValuePair("present_street_no", houseno));
			//nameValuePairs.add(new BasicNameValuePair("present_house_no", Streetno));
			

			
			


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
