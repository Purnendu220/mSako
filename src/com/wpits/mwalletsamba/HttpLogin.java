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
import org.json.simple.JSONObject;

import com.wpits.mwalletsamba.LoginActivity.Logintask;
import com.wpits.parser.LoginParser;
import com.wpits.parser.UserLoginparser;
import com.wpits.utils.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

public class HttpLogin {
	String responseregcont;
	private Context context;
	String[] splitval ;
	public UserLoginparser httpuserlogin(Context context,String mobileno,String mpin)
	{
		this.context = context;
		//dao=new TodoDAO(context);
		UserLoginparser parser = null;
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.getDeviceId();
		try {
			JSONObject obj=new JSONObject();
			obj.put("msisdn", mobileno);
			obj.put("password", mpin);
			obj.put("device_type", "Android");
			obj.put("deviceid",telephonyManager.getDeviceId());

			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK_MSAKO+"user_login");


			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("post_data", Utility.encrypt(obj.toString())));
			nameValuePairs.add(new BasicNameValuePair("password", mpin));



			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// httpclient.execute(httppost);
			HttpResponse response = httpclient.execute(httppost);
			//Thread.sleep(10000);
			HttpEntity rp = response.getEntity();
			responseregcont =  EntityUtils.toString(rp);
			System.out.println(responseregcont);
			responseregcont=Utility.decrypt(responseregcont.getBytes());
			if(responseregcont.contains(GlobalIpConfigration.URL_BANK1))
			{
				return parser;	
			}
			else
			{
				parser=new UserLoginparser(responseregcont);

				return parser;
			}


		}
		catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return parser;
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
