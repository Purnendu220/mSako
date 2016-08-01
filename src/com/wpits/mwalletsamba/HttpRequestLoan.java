package com.wpits.mwalletsamba;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.wpits.parser.LoanApplicationRequestParser;
import com.wpits.parser.LoanRequstResponse;
import com.wpits.parser.UserLoginparser;
import com.wpits.utils.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

public class HttpRequestLoan {
	static String responsestring;
	public static LoanApplicationRequestParser requstloan(String mobileno,String loanamount,HashMap<String, String> mapgauranter,String loantype,String loantenure,String session_id,String usertype,String ownreserveamount,Context context)
	{
		LoanApplicationRequestParser parser = null;
		try {

			TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			telephonyManager.getDeviceId();
			JSONObject obj=new JSONObject();
			obj.put("msisdn", mobileno);
			obj.put("session_id", session_id);
			obj.put("device_type", "Android");
			obj.put("deviceid",telephonyManager.getDeviceId());
			obj.put("user_type", usertype);
			obj.put("type", "loanrequest");
			obj.put("loanamount", loanamount);
			obj.put("loantype", loantype);
			obj.put("loantenure", loantenure);
			obj.put("ownreserveamount", ownreserveamount);
			JSONArray arr=new JSONArray();
			Iterator it = mapgauranter.entrySet().iterator();
			while (it.hasNext()) {
				JSONObject obj1=new JSONObject();
				Map.Entry pair = (Map.Entry)it.next();
				System.out.println(pair.getKey() + " = " + pair.getValue());
				obj1.put("gaurantermsisdn", pair.getKey());
				obj1.put("gauranteramount", pair.getValue());
				arr.add(obj1);
				it.remove(); 
			} 
			obj.put("gauranterlist", arr);





			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK_MSAKO+"user_request_loan");


			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
			nameValuePairs.add(new BasicNameValuePair("post_data", Utility.encrypt(obj.toString())));







			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// httpclient.execute(httppost);
			HttpResponse response = httpclient.execute(httppost);
			//Thread.sleep(10000);
			HttpEntity rp = response.getEntity();
			responsestring =  EntityUtils.toString(rp);
			responsestring=Utility.decrypt(responsestring.getBytes());

			System.out.println(responsestring);

			if(responsestring.contains(GlobalIpConfigration.URL_BANK1))
			{
				return parser;	
			}
			else
			{
				parser=new LoanApplicationRequestParser(responsestring);
				return parser;
			}

		}
		catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			responsestring=null;
			return parser;
		}

	}
}
