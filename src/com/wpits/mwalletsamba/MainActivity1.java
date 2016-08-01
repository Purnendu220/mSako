package com.wpits.mwalletsamba;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.wpits.data.TodoDAO;



import twitter4j.PagableResponseList;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity1 extends Activity implements OnClickListener {

	/* Shared preference keys */
	private static final String PREF_NAME = "sample_twitter_pref";
	private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";
	private static final String PREF_USER_NAME = "twitter_user_name";
	private static final String PREF_USERID = "twitter_userid";
	String verifier;

	/* Any number for uniquely distinguish your request */
	public static final int WEBVIEW_REQUEST_CODE = 100;

	private ProgressDialog pDialog;

	private static Twitter twitter;
	private static RequestToken requestToken;

	private static SharedPreferences mSharedPreferences;

	private EditText mShareEditText;
	private TextView userName,textsuccessfull;
	private View loginLayout;
	private View shareLayout;
	private View authorized;

	private String consumerKey = null;
	private String consumerSecret = null;
	private String callbackUrl = null;
	private String oAuthVerifier = null;
	TodoDAO dao;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* initializing twitter parameters from string.xml */
		initTwitterConfigs();

		/* Enabling strict mode */

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();      
		StrictMode.setThreadPolicy(policy);
		/* Setting activity layout file */
		setContentView(R.layout.activity_main1);
		dao=new TodoDAO(getApplicationContext());
		loginLayout = (RelativeLayout) findViewById(R.id.login_layout);
		shareLayout = (LinearLayout) findViewById(R.id.share_layout);
		mShareEditText = (EditText) findViewById(R.id.share_text);
		userName = (TextView) findViewById(R.id.textsucessfull);
        authorized=(LinearLayout)findViewById(R.id.authorized);
        textsuccessfull=(TextView)findViewById(R.id.textauthorized);
	
		findViewById(R.id.btn_login).setOnClickListener(this);
		findViewById(R.id.btn_share).setOnClickListener(this);

		/* Check if required twitter keys are set */
		if (TextUtils.isEmpty(consumerKey) || TextUtils.isEmpty(consumerSecret)) {
			Toast.makeText(this, "Twitter key and secret not configured",
					Toast.LENGTH_SHORT).show();
			return;
		}

		/* Initialize application preferences */
		mSharedPreferences = getSharedPreferences(PREF_NAME, 0);

		boolean isLoggedIn = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);

		/*  if already logged in, then hide login layout and show share layout */
		if (isLoggedIn) {
			loginLayout.setVisibility(View.GONE);
			shareLayout.setVisibility(View.GONE);
			textsuccessfull.setVisibility(View.VISIBLE);
			String username = mSharedPreferences.getString(PREF_USER_NAME, "");
			userName.setText("Authorized Succesfully");
			System.out.println(username);


		} else {
			loginLayout.setVisibility(View.VISIBLE);
			shareLayout.setVisibility(View.GONE);

			Uri uri = getIntent().getData();

			if (uri != null && uri.toString().startsWith(callbackUrl)) {

				String verifier = uri.getQueryParameter(oAuthVerifier);

				try {

					/* Getting oAuth authentication token */
					AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);

					/* Getting user id form access token */
					long userID = accessToken.getUserId();
					final User user = twitter.showUser(userID);
					final String username = user.getName();

					/* save updated token */
					saveTwitterInfo(accessToken);

					loginLayout.setVisibility(View.GONE);
					shareLayout.setVisibility(View.VISIBLE);
					userName.setText( username);

				} catch (Exception e) {
					Log.e("Failed to login Twitter!!", e.getMessage());
				}
			}

		}

	}


	/**
	 * Saving user information, after user is authenticated for the first time.
	 * You don't need to show user to login, until user has a valid access toen
	 */
	private void saveTwitterInfo(AccessToken accessToken) {

		long userID = accessToken.getUserId();

		User user;
		try {
			 SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 
				final String mobile_no =pref.getString("mobile_no", "");
			user = twitter.showUser(userID);

			String username = user.getName();

			/* Storing oAuth tokens to shared preferences */
			Editor e = mSharedPreferences.edit();
			e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
			e.putString(PREF_KEY_OAUTH_SECRET, accessToken.getTokenSecret());
			e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
			e.putString(PREF_USER_NAME, username);
			e.putLong(PREF_USERID, userID);
			e.putString("SCREEN_NAME", accessToken.getScreenName());
			e.commit();
			friendslist();
			HttpTwitterSaveKey save=new HttpTwitterSaveKey();
			save.httptwitter(mobile_no, accessToken.getToken(), accessToken.getTokenSecret(), accessToken.getScreenName(), getApplicationContext());

		} catch (TwitterException e1) {
			e1.printStackTrace();
		}
	}

	/* Reading twitter essential configuration parameters from strings.xml */
	private void initTwitterConfigs() {
		consumerKey = getString(R.string.twitter_consumer_key);
		consumerSecret = getString(R.string.twitter_consumer_secret);
		callbackUrl = getString(R.string.twitter_callback);
		oAuthVerifier = getString(R.string.twitter_oauth_verifier);
	}


	private void loginToTwitter() {
		boolean isLoggedIn = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);

		if (!isLoggedIn) {
			final ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(consumerKey);
			builder.setOAuthConsumerSecret(consumerSecret);

			final Configuration configuration = builder.build();
			final TwitterFactory factory = new TwitterFactory(configuration);
			twitter = factory.getInstance();

			try {
				requestToken = twitter.getOAuthRequestToken(callbackUrl);

				/**
				 *  Loading twitter login page on webview for authorization 
				 *  Once authorized, results are received at onActivityResult
				 *  */
				final Intent intent = new Intent(this, WebViewActivity.class);
				intent.putExtra(WebViewActivity.EXTRA_URL, requestToken.getAuthenticationURL());
				startActivityForResult(intent, WEBVIEW_REQUEST_CODE);

			} catch (TwitterException e) {
				e.printStackTrace();
			}
		} else {

			loginLayout.setVisibility(View.GONE);
			shareLayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {
			verifier = data.getExtras().getString(oAuthVerifier);
			System.out.println(verifier);
			try {

				loginLayout.setVisibility(View.GONE);
				shareLayout.setVisibility(View.VISIBLE);
				String otp=getCreateOtp();
				setotp(otp);
				generateNotification(getApplicationContext(), otp);

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("Twitter Login Failed","Login failed");
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void savetwitterinfo () {

		try {
			AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);

			long userID = accessToken.getUserId();
			final User user = twitter.showUser(userID);
			String username = user.getName();

			saveTwitterInfo(accessToken);


			
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Twitter Login Failed","Login failed");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			//loginToTwitter();
			new AsyncTaskLogin().execute();
			break;
		case R.id.btn_share:
			final String status = mShareEditText.getText().toString();

			if (status.trim().length() > 0) {
				new updateTwitterStatus().execute(status);
			} else {
				Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	public class AsyncTaskLogin extends AsyncTask<String, String, String> {

		String stringpassword,stringcustomerid,time;
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(MainActivity1.this, "Moving To Secure Authorization Page ",
					"Please Wait");

		}

		@Override
		protected String doInBackground(String... arg0) {


			boolean isLoggedIn = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);

			if (!isLoggedIn) {
				final ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setOAuthConsumerKey(consumerKey);
				builder.setOAuthConsumerSecret(consumerSecret);

				final Configuration configuration = builder.build();
				final TwitterFactory factory = new TwitterFactory(configuration);
				twitter = factory.getInstance();

				try {
					requestToken = twitter.getOAuthRequestToken(callbackUrl);

					/**
					 *  Loading twitter login page on webview for authorization 
					 *  Once authorized, results are received at onActivityResult
					 *  */
					final Intent intent = new Intent(MainActivity1.this, WebViewActivity.class);
					intent.putExtra(WebViewActivity.EXTRA_URL, requestToken.getAuthenticationURL());
					startActivityForResult(intent, WEBVIEW_REQUEST_CODE);

				} catch (TwitterException e) {
					e.printStackTrace();
				}
			} else {

				loginLayout.setVisibility(View.GONE);
				shareLayout.setVisibility(View.VISIBLE);
			}
			return null;

		}

		@Override
		protected void onPostExecute(String strFromDoInBg) {
try {
	pd.cancel();

} catch (Exception e) {
	// TODO: handle exception
}


		}
	}


	class updateTwitterStatus extends AsyncTask<String, String, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(MainActivity1.this);
			pDialog.setMessage("Getting Twitter Friends List....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected Void doInBackground(String... args) {

			String status = args[0];
			try {
				if(mShareEditText.getText().toString().equals(getotp()))
				{
					savetwitterinfo();
					friendslist();
					Intent i = new Intent(MainActivity1.this, TwitterBenefList.class);
					startActivity(i); 
					finish();
				}
				else{
					mShareEditText.setError("Please Enter Valid OTP");
				}
			} catch (Exception e) {
				Log.d("Failed to post!", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			/* Dismiss the progress dialog after sharing */
			try {
				pDialog.dismiss();

			} catch (Exception e) {
				// TODO: handle exception
			}


			// Clearing EditText field
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
				System.out.println("Name" + i + ":" + name);
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
		Intent in = new Intent(MainActivity1.this, AndroidGridLayoutActivity.class);

		startActivity(in);
		finish();
	}
	
	@SuppressWarnings("deprecation")
	private void generateNotification(Context context, String message) {

		  System.out.println(message+"++++++++++2");
	
		  DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm a");
		  String date = df.format(Calendar.getInstance().getTime());
		  int icon = R.drawable.ic_launcher;
		  long when = System.currentTimeMillis();
		  NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		  Notification notification = new Notification(icon, message, when);
		  String title = context.getString(R.string.app_name);
		  String subTitle =message;
	
		 // notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		  notification.setLatestEventInfo(context, title, subTitle, null);
		  //To play the default sound with your notification:
		  notification.defaults |= Notification.DEFAULT_SOUND;
		  notification.flags |= Notification.FLAG_AUTO_CANCEL;
		  notification.defaults |= Notification.DEFAULT_VIBRATE;
		  notificationManager.notify((int)System.currentTimeMillis(), notification);
		 // addShortcut(context);
	
    }
	 private void setotp(String value) {
		    SharedPreferences pref = PreferenceManager
		            .getDefaultSharedPreferences(getApplicationContext());
		    SharedPreferences.Editor editor = pref.edit();
		    editor.putString("OTP", value);
		    editor.commit();
		}
		private String getotp() {
		    SharedPreferences pref = PreferenceManager
		            .getDefaultSharedPreferences(getApplicationContext());
		    return pref.getString("OTP", "");
		}
		private static final String ALPHA_NUM = "01234567890125638927972562872635672";
	     
	       public static String getCreateOtp()

	       {       int len=6;
	               StringBuffer sb = new StringBuffer(len);
	               for (int i=0;  i<len;  i++) {
	                  int ndx = (int)(Math.random()*ALPHA_NUM.length());
	                  sb.append(ALPHA_NUM.charAt(ndx));
	               }
	               return sb.toString();

	      }

}