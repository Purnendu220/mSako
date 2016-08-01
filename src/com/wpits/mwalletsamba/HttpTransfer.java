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

public class HttpTransfer {
	String responseregcont;
	private Context context;
	public String httptransfer(String srcmobile,String destmobile,String mpin,String amount,String type,String remark,String oprater ){
		this.context = context;
		//dao=new TodoDAO(context);

		try {
System.out.println(oprater);

			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK+"Transaction");


			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
			nameValuePairs.add(new BasicNameValuePair("src_mob_number", srcmobile));
			nameValuePairs.add(new BasicNameValuePair("dest_mob_number", destmobile));
			nameValuePairs.add(new BasicNameValuePair("mpin", mpin));
			nameValuePairs.add(new BasicNameValuePair("amount", amount));
			nameValuePairs.add(new BasicNameValuePair("type", type));
			nameValuePairs.add(new BasicNameValuePair("remark", remark));
			nameValuePairs.add(new BasicNameValuePair("operator", oprater));



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
