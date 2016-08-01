package com.wpits.mwalletsamba;

import java.io.File;
import java.text.DecimalFormat;
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
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;


public class Httpsendregistration {
	String responseregcont;
	private Context context;
	public boolean sendregistration(String mobileno,String otp,String name,String pin,String nrc,String altnumber,String dob,String type,String address,String email,String creattype){
		this.context = context;
		//dao=new TodoDAO(context);
		
		try {
			
				
			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK+"Register");

			
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(10);
			nameValuePairs.add(new BasicNameValuePair("misdn_no", mobileno));
			nameValuePairs.add(new BasicNameValuePair("otp", otp));
			nameValuePairs.add(new BasicNameValuePair("name", name));
			nameValuePairs.add(new BasicNameValuePair("pin", pin));
			nameValuePairs.add(new BasicNameValuePair("nrc", nrc));
			nameValuePairs.add(new BasicNameValuePair("alternateno", altnumber));
			nameValuePairs.add(new BasicNameValuePair("dob", dob));
			nameValuePairs.add(new BasicNameValuePair("type", type));
			nameValuePairs.add(new BasicNameValuePair("email", email));
			nameValuePairs.add(new BasicNameValuePair("address", address));
			nameValuePairs.add(new BasicNameValuePair("createtype", creattype));


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

	

	

