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

public class Httpnewtransfer {
	static String responseregcont;
	public static String createwallet(String srcmobile,String destmobile,String amount,Context context)
	{
		
		
		try {
			
			System.out.println("****************"+srcmobile+"*"+"*"+destmobile+"*"+amount);

			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK1+"transfer1");


			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
			nameValuePairs.add(new BasicNameValuePair("fromMobile", srcmobile));
			nameValuePairs.add(new BasicNameValuePair("toMobile", destmobile));
			nameValuePairs.add(new BasicNameValuePair("amount", amount));
			

			//nameValuePairs.add(new BasicNameValuePair("present_street_no", houseno));
			//nameValuePairs.add(new BasicNameValuePair("present_house_no", Streetno));
			

			
			


			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// httpclient.execute(httppost);
			HttpResponse response = httpclient.execute(httppost);
			//Thread.sleep(10000);
			HttpEntity rp = response.getEntity();
			responseregcont =  EntityUtils.toString(rp);
			
				System.out.println("@@@@@@@@@@@@@@@@@@@@"+responseregcont);
				if(responseregcont.length()>10)
				{
					return "Server Error";	//http 404 error from server
				}
				else
				{
					if(responseregcont.equalsIgnoreCase(String.valueOf(1)))
					{
						return "Transaction Sucessfull";

					}
					else if(responseregcont.equalsIgnoreCase(String.valueOf(2))){

						return "Insufficeint Balance";

					}
					else if(responseregcont.equalsIgnoreCase(String.valueOf(3))){

						return "Incorrect M PIN";

					}
					else if(responseregcont.equalsIgnoreCase(String.valueOf(5))){

						return "Receiver does not exist in the system";

					}
					else{
						return "Transaction Declined due to Some problem";

					}

				}


			
				
		}
		catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return "Connection Lost ";//connection refused

		}
		
	}
}
