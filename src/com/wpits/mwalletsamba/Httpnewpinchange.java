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
import android.content.SharedPreferences.Editor;

public class Httpnewpinchange {
	static String responseregcont;
	private static Context context;
	public static boolean httpsendmobile(Context context1,String mobileno,String mpin,String newmpin)
	{
		context = context1;
		//dao=new TodoDAO(context);
		
		try {
			
				
			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK1+"change_pin");

			
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair("mobile_no", mobileno));
			nameValuePairs.add(new BasicNameValuePair("mpin", mpin));
			nameValuePairs.add(new BasicNameValuePair("new_mpin", newmpin));


			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// httpclient.execute(httppost);
			HttpResponse response = httpclient.execute(httppost);
			//Thread.sleep(10000);
			HttpEntity rp = response.getEntity();
			responseregcont =  EntityUtils.toString(rp);
			if(responseregcont.length()>10)
			{
			return false;	
			}
			else
			{
				if(responseregcont.equalsIgnoreCase(String.valueOf(1)))
				{
					SharedPreferences pref = context.getSharedPreferences("MySettings", 0); 
        		    Editor editor = pref.edit();
            	
            		editor.putString("mPin", newmpin);
            	
            		editor.commit();
					return true;

				}
				else{
					
					return false;

				}
	
			}
				
				
		}
		catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;
		}
	}
}
