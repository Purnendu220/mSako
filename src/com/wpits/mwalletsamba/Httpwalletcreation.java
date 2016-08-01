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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;

public class Httpwalletcreation {
	static String responsestring;
	public static String createwallet(String wallet_type,String wallet_value,String wallet_allocated_value,String wallet_min_value,String wallet_max_value,String wallet_status,String notifymsisdn,String notifyemail,String password,String mobile,Context context)
	{
		
		
		try {
			  SharedPreferences pref =context.getSharedPreferences("MySettings", 0); 
				 String agentid =pref.getString("agentid", "");
					String userid =pref.getString("mobile_no", "");
					 String name =pref.getString("name", "");
				
			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK1+"add_wallet");

			
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
			nameValuePairs.add(new BasicNameValuePair("wallet_type", wallet_type));
			nameValuePairs.add(new BasicNameValuePair("wallet_value", wallet_value));
			nameValuePairs.add(new BasicNameValuePair("wallet_allocated_value", wallet_allocated_value));
			nameValuePairs.add(new BasicNameValuePair("wallet_min_value", wallet_min_value));
			nameValuePairs.add(new BasicNameValuePair("wallet_max_value", wallet_max_value));
			nameValuePairs.add(new BasicNameValuePair("wallet_status", wallet_status));
			nameValuePairs.add(new BasicNameValuePair("notifymsisdn", notifymsisdn));
			nameValuePairs.add(new BasicNameValuePair("notifyemail", notifyemail));

			//nameValuePairs.add(new BasicNameValuePair("present_street_no", houseno));
			//nameValuePairs.add(new BasicNameValuePair("present_house_no", Streetno));
			

			
			


			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// httpclient.execute(httppost);
			HttpResponse response = httpclient.execute(httppost);
			//Thread.sleep(10000);
			HttpEntity rp = response.getEntity();
			 responsestring =  EntityUtils.toString(rp);
			
				System.out.println(responsestring);
				try {
					String[] responsearr=responsestring.split(",");
					
					
					String walletidimgid=responsearr[0];
					String walletstatus=responsearr[1];
					Integer.parseInt(walletidimgid);
					SharedPreferences pref1 = context.getSharedPreferences("MySettings", 0);
				    Editor editor = pref1.edit();
					editor.putString("walletidimgid", walletidimgid);
					editor.putString("walletstatus", walletstatus);
					editor.commit(); 
                   returnall(context,mobile,password);					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
		}
		catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return responsestring;
		
	}
	public static void returnall(Context context,String phone,String password)
	{
	    final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		SharedPreferences pref = context.getSharedPreferences("MySettings", 0); 
			final String subspid =pref.getString("subspid", "");
			final	String subsgentid =pref.getString("subsgentid", "");
				 String sunlevelid =pref.getString("sunlevelid", "");
				final String walletidimgid =pref.getString("walletidimgid", "");
		/*editor.putString("subsgentid", subsgentid);
		editor.putString("subspid", subspid);
		editor.putString("sunlevelid", sunlevelid);
		editor.putString("agimgid", agimgid);
		editor.putString("agstatus", agstatus);
		editor.putString("agstat", agstat);
		editor.putString("walletidimgid", walletidimgid);
		editor.putString("walletstatus", walletstatus);*/
		
		Httpreturnallid.returnallid(subspid, sunlevelid, subsgentid, walletidimgid,phone,tm.getDeviceId(),password,context);
		
	}
	

}
