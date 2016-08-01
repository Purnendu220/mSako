package com.wpits.mwalletsamba;


import java.util.ArrayList;







import com.wpits.modelclass.NotificationModel;
import com.wpits.modelclass.TwitterTransfer;
import com.wpits.parser.UserLoginparser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class Notification extends Activity {
	NotificationAdapter adapter;
	ArrayList<NotificationModel> notificationlist ;
	String mobile_no;
	ListView lv;
	UserLoginparser userobject;
	NotificationResponse response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification);
		System.out.println("HEllo iam called ");
		lv=(ListView)findViewById(R.id.listView1);
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 
		mobile_no =pref.getString("mobile_no", "");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();      
		StrictMode.setThreadPolicy(policy);
		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
			response	=(NotificationResponse)iuser.getExtras().getSerializable("noti_obj");

		}
		notificationlist=response.getModel();


		if(notificationlist.size()>0){
			adapter = new NotificationAdapter(getApplicationContext(), notificationlist);
			lv.setAdapter(adapter);
		}

		//new UserNotification().execute();

	}
	public class UserNotification extends AsyncTask<String, String, String> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Notification.this, "Loading Notifications ",
					"Please Wait");
		}

		@Override
		protected String doInBackground(String... arg0) {
			//HttpgetMiniStatment ministatement=new HttpgetMiniStatment();
			//ministatementlist=	ministatement.httpuserministatment(getApplicationContext(), mobile_no);


			return null;
		}

		@Override
		protected void onPostExecute(String strFromDoInBg) {
			// TODO Auto-generated method stub
			if(notificationlist.size()>0){
				adapter = new NotificationAdapter(getApplicationContext(), notificationlist);
				lv.setAdapter(adapter);
			}
			pd.cancel();


		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(), AndroidGridLayoutActivity.class);
		in.putExtra("user_obj", userobject);

		startActivity(in);
		finish();
	}

	private String getmobileno() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		return pref.getString("MobileNo", "");
	}

	private NotificationModel getnotificationmodel(String id, String notificationsubject,
			String notificationdescription, String notificationdate,
			String notificationsender)
	{
		return new NotificationModel(id, notificationsubject, notificationdescription, notificationdate, notificationsender);


	}
}
