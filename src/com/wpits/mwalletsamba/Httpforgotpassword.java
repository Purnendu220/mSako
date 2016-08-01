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
import android.preference.PreferenceManager;

public class Httpforgotpassword {
	String responseregcont;
	private Context context;
	String[] splitval ;
	public boolean httpuserforgotpassword(Context context,String mobileno)
	{
		this.context = context;
		//dao=new TodoDAO(context);
		
		try {
			
				
			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK+"ForgotPassword");

			
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("mobile_no", mobileno));
		

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// httpclient.execute(httppost);
			HttpResponse response = httpclient.execute(httppost);
			//Thread.sleep(10000);
			HttpEntity rp = response.getEntity();
			responseregcont =  EntityUtils.toString(rp);
			System.out.println(responseregcont);
			if(responseregcont.length()>20)
			{
			return false;	
			}
			else
			{
				if(responseregcont.equalsIgnoreCase("1")){
					
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
	private void setbalance(String value) {
	    SharedPreferences pref = PreferenceManager
	            .getDefaultSharedPreferences(context);
	    SharedPreferences.Editor editor = pref.edit();
	    editor.putString("balance", value);
	    editor.commit();
	}
private String getbalance() {
	    SharedPreferences pref = PreferenceManager
	            .getDefaultSharedPreferences(context);
	    return pref.getString("balance", "");
	}
}
