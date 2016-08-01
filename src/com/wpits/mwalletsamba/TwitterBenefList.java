package com.wpits.mwalletsamba;

import java.util.ArrayList;

import twitter4j.DirectMessage;
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import com.wpits.data.TodoDAO;
import com.wpits.modelclass.BenefciaryModelclass;
import com.wpits.modelclass.Twittermodelclass;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TwitterBenefList extends Activity {
TodoDAO dao;
ListView listview;
TextView textView1;
Button refresh;
ArrayList<Twittermodelclass> list;
ArrayAdapter<Twittermodelclass> adapter;
private String consumerKey = null;
private String consumerSecret = null;
private static SharedPreferences mSharedPreferences;
private static final String PREF_NAME = "sample_twitter_pref";
private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";
private static final String PREF_USER_NAME = "twitter_user_name";
private static final String PREF_USERID = "twitter_userid";
private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter_benef_list);
		listview=(ListView)findViewById(R.id.twitterlistView1);
		textView1	=(TextView)findViewById(R.id.textView1);
		refresh=(Button)findViewById(R.id.refresh);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		dao=new TodoDAO(getApplicationContext());
		initTwitterConfigs();
		mSharedPreferences = getSharedPreferences(PREF_NAME, 0);

		new Asyncgetbeneficiary().execute();
	
		refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Asyncrefreshfriendslist().execute();
				new Asyncgetbeneficiary().execute();
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) 
				{
				Bundle b = new Bundle();
				b.putString("benefname", adapter.getItem(position).getBenefname());
				b.putString("twitterid", adapter.getItem(position).getScreenname());
           System.out.println(adapter.getItem(position).getBenefname()+":- "+adapter.getItem(position).getTwitterid());
       	Intent in = new Intent(TwitterBenefList.this, Twitter_Transfer.class);
               in.putExtras(b);
		startActivity(in);
				}
				// TODO Auto-generated method stub
			
		});
		
	}
	private void initTwitterConfigs() {
		consumerKey = getString(R.string.twitter_consumer_key);
		consumerSecret = getString(R.string.twitter_consumer_secret);
		
	}


	public class Asyncgetbeneficiary extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {


		}

		@Override
		protected String doInBackground(String... arg0) {

		list=new ArrayList<Twittermodelclass>();
			
            list=  dao.getwitterfollowerlist();
			System.out.println(list.size());
			if(list.size()<1)
			{
				runOnUiThread( new Runnable() {
					public void run() {
						listview.setVisibility(View.INVISIBLE);

					}
				});
			}
			else{
				runOnUiThread( new Runnable() {
					public void run() {
						//textView1.setVisibility(View.INVISIBLE);
					}
				});
			}
			return null;


		}

		@Override
		protected void onPostExecute(String strFromDoInBg) {
			adapter = new CustomAdapterTwitterBenef(TwitterBenefList.this, list);

			listview.setAdapter(adapter);


		}
	}
	
	public class Asyncrefreshfriendslist extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {

			pDialog = new ProgressDialog(TwitterBenefList.this);
			pDialog.setMessage("Getting Twitter Friends List....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			
			friendslist();
			return null;
		}

		@Override
		protected void onPostExecute(String strFromDoInBg) {
			
	 pDialog.dismiss();

		}
	}
	public void friendslist()
	{
		String access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
		// Access Token Secret
		String access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");
		long screenName=mSharedPreferences.getLong(PREF_USERID, 0);
		ConfigurationBuilder confbuilder = new ConfigurationBuilder();
		confbuilder.setOAuthAccessToken(access_token)
		.setOAuthAccessTokenSecret(access_token_secret)
		.setOAuthConsumerKey(consumerKey)
		.setOAuthConsumerSecret(consumerSecret);
		Twitter twitter = new TwitterFactory(confbuilder.build()).getInstance();
System.out.println(access_token+":"+access_token_secret+":"+screenName+":"+consumerKey+":"+consumerSecret);
		PagableResponseList<User> followersList;

		ArrayList<String> list=new ArrayList<String>();
		try
		{
			followersList = twitter.getFollowersList(screenName, -1);
			// IDs followersIDs = twitter.getFollowersIDs(userId,
			// cursor);
			System.out.println(followersList.size());
			for (int i = 0; i < followersList.size(); i++)
			{
				User user = followersList.get(i);
				String name = user.getName();
				long id=user.getId();
				String screenname=user.getScreenName();
				list.add(name);
				dao.inserttwitterbenefinfo(String.valueOf(System.currentTimeMillis()), name,screenname, String.valueOf(id), String.valueOf(System.currentTimeMillis()));
				System.out.println("Name" + i + ":" + name+"ScreenName:- "+user.getScreenName()+"Description:-"+user.getDescription()+"Status:-"+user.getStatus());
			}



		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent in = new Intent(TwitterBenefList.this, AndroidGridLayoutActivity.class);

		startActivity(in);
		finish();
	}
}
