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

public class HttpgetMiniStatment {
	String responseregcont;
	private Context context;
	String[] splitval ;
	public ArrayList<ModelMinistatment> httpuserministatment(Context context,String mobileno)
	{
		ArrayList<ModelMinistatment> ministatement = new ArrayList<ModelMinistatment>();

		this.context = context;
		//dao=new TodoDAO(context);
		
		try {
			
				
			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK+"Summary");

			
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("mobile_no", mobileno));
		

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// httpclient.execute(httppost);
			HttpResponse response = httpclient.execute(httppost);
			//Thread.sleep(10000);
			HttpEntity rp = response.getEntity();
			responseregcont =  EntityUtils.toString(rp);
			System.out.println(responseregcont);
			if(responseregcont.contains("jsonArray"))
			{
				Minisummaryparser parser=new Minisummaryparser();
				ministatement=parser.parsedata(responseregcont);
				return ministatement;
			}
			else{
				
				return ministatement; 
			}
			
				
				
		}
		catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return null;
		}
	}
}
