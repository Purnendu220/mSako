package com.wpits.mwalletsamba;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


public class HttpTwitterSaveKey {
	Context context;
	public boolean httptwitter(String customerid, String accesstocken,String accesstockensecret,String twitterid, Context context)
	{
		this.context = context;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		try {
			System.out.println(customerid+":"+accesstocken+":"+accesstockensecret+":"+twitterid);
				
			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK+"TwitterAuthenticate");
			//	HttpPost httppost = new HttpPost("http://182.74.113.55:2014/AgentLocation/register");

			
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
			nameValuePairs.add(new BasicNameValuePair("twitter_id", twitterid));
			nameValuePairs.add(new BasicNameValuePair("access_token", accesstocken));
			nameValuePairs.add(new BasicNameValuePair("access_token_secret", accesstockensecret));
			nameValuePairs.add(new BasicNameValuePair("mobile_no",customerid));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity rp = response.getEntity();
		    String	responseregcont =  EntityUtils.toString(rp);
		    System.out.println("!!!!!!!!!!@@@@@@@@@@@@@@@@@@@@@@###############$$$$$$$$$$$$$$$$$$%%%%%%%%%%%%: "+responseregcont);
			if(responseregcont.equalsIgnoreCase(String.valueOf(1)))
			{
				return true;
			}
			else{
				return false;
			}	
				
		}
		catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;
		}
	}

}
