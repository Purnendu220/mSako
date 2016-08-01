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


public class Httpsendmobile {
	String responseregcont;
	private Context context;
	String[] splitval ;
	public String httpsendmobile(Context context,String mobileno,String gcmid,String createtype)
	{
		this.context = context;
		//dao=new TodoDAO(context);

		try {


			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK+"Verification");
			//	http://192.168.137.127:8080/m_banking/Verification

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("misdn_no", mobileno));
			nameValuePairs.add(new BasicNameValuePair("gcm", gcmid));


			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// httpclient.execute(httppost);
			HttpResponse response = httpclient.execute(httppost);
			//Thread.sleep(10000);
			HttpEntity rp = response.getEntity();
			responseregcont =  EntityUtils.toString(rp);
			if(responseregcont.length()>10)
			{
				return "0";	
			}
			else
			{
				if(responseregcont.equalsIgnoreCase(String.valueOf(1)))
				{
					return "1";

				}
				
				if(responseregcont.equalsIgnoreCase(String.valueOf(2)))
				{
					if(createtype.length()==0)
					{
						Httpgetuserdetail user=new Httpgetuserdetail();
						ArrayList<ModelUserData> userdata = new ArrayList<ModelUserData>();

						userdata=	user.httpgetuserdetail(context, mobileno);
						if(userdata!=null||userdata.size()>0){
							SharedPreferences pref = context.getSharedPreferences("MySettings", 0); 
				 		    Editor editor = pref.edit();
				     		editor.putString("mobile_no", userdata.get(0).getMsidn_no());
				     		editor.putString("otp", userdata.get(0).getOtp());
				     		editor.putString("name", userdata.get(0).getUsername());
				     		editor.putString("nrc", userdata.get(0).getNrc());
				     		editor.putString("mPin", userdata.get(0).getPin());
				     		editor.putString("alternate_mobile", userdata.get(0).getAlternativeno());
				     		editor.putString("dob", userdata.get(0).getDob());
				     		editor.putString("type", userdata.get(0).getType());
				     		editor.putString("email", userdata.get(0).getEmail());
				     		editor.putString("address", userdata.get(0).getAddress());

				     		editor.commit();
							return "2";

						}
						else{
							return "4";

						}
					}
					else{
						return "2";

					}
				

				}
				else{

					return "3";

				}

			}


		}
		catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return "0";
		}
	}
}
