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

import twitter4j.DirectMessage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.content.SharedPreferences;


public class Httptransferviatwitter {

	String responseregcont;
	private Context context;
	String[] splitval ;
	private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	private static SharedPreferences mSharedPreferences;
	private static final String PREF_NAME = "sample_twitter_pref";

	public String httptransfertwitter(String mobile_no, String password,String twitter_id, String amount,Context context)
	{
		this.context = context;
		mSharedPreferences = context.getSharedPreferences(PREF_NAME, 0);


		try {


			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost(GlobalIpConfigration.URL_BANK+"TwitterTransferAction");



			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
			nameValuePairs.add(new BasicNameValuePair("mobile_no", mobile_no));
			nameValuePairs.add(new BasicNameValuePair("mpin", password));
			nameValuePairs.add(new BasicNameValuePair("twitter_id", twitter_id));
			nameValuePairs.add(new BasicNameValuePair("amount", amount));


			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// httpclient.execute(httppost);
			HttpResponse response = httpclient.execute(httppost);
			//Thread.sleep(10000);
			HttpEntity rp = response.getEntity();
			responseregcont =  EntityUtils.toString(rp);
			System.out.println(mobile_no+":"+password+":"+twitter_id+":"+amount+":"+responseregcont);
			if(responseregcont.length()>10)
			{
				String responseret="Server Error Occured";	
				return responseret;
			}
			else
			{
				if(responseregcont.equalsIgnoreCase(String.valueOf(1)))
				{

					String responseret="Insufficient Balance Transaction Failed";
					return responseret;
				}
				else if(responseregcont.equalsIgnoreCase(String.valueOf(2)))
				{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = sdf.format(new Date());
					String responseret=" Transaction Successfull";
					//dao.inserttwittertransferhistory(String.valueOf(System.currentTimeMillis()), benefname, twitter_id, twitter_id, amount, time);
					/*String access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
					// Access Token Secret
					String access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");
					String consumerKey = context.getString(R.string.twitter_consumer_key);
					String consumerSecret = context.getString(R.string.twitter_consumer_secret);
					ConfigurationBuilder cb = new ConfigurationBuilder();
					cb.setDebugEnabled(true)
					.setOAuthConsumerKey("consumerKey")
					.setOAuthConsumerSecret("consumerSecret")
					.setOAuthAccessToken("access_token")
					.setOAuthAccessTokenSecret("access_token_secret");
					TwitterFactory tf = new TwitterFactory(cb.build());
					Twitter twitter = tf.getInstance();
					try {
					
						DirectMessage message = twitter.sendDirectMessage(twitter_id, "Hello test ");
					} catch (TwitterException e) {
						e.printStackTrace();
					}*/
					//dao.inserttransferhistory(String.valueOf(System.currentTimeMillis()), receiver_id, receiver_name, bank_name, ifsc_code, receiver_ac, amount, time);
					return responseret;

				}
				else if(responseregcont.equalsIgnoreCase(String.valueOf(0)))
				{     
					String responseret=" Password Incorrect";
					return responseret;

				}
				else if(responseregcont.equalsIgnoreCase(String.valueOf(3)))
				{					String responseret="Twitter Id Not Found";

				return responseret;


				}
				else{
					String responseret=" Try After some time";
					return responseret;


				}

			}


		}
		catch (Exception e) {

			e.printStackTrace();
			String responseret="Try again";
			return responseret;

		}
	}




}
