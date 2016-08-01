package com.wpits.mwalletsamba;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;





import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class SplashActivity extends Activity {


	private static int SPLASH_TIME_OUT = 2000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		//Intent i = new Intent(SplashActivity.this, ReService.class);
		//startService(i);

		Thread thread = new Thread() {

			@Override
			public void run() {
				//fillData();

			}
		};
		thread.start();
		new Handler().postDelayed(new Runnable() {
			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 
				String mobile_no =pref.getString("mobile_no", "");

				// Start your app main activity

				Intent i = new Intent(SplashActivity.this, LoginActivity.class);
				startActivity(i);


				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);

	}



}