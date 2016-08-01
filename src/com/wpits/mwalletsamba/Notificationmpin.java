package com.wpits.mwalletsamba;

import com.wpits.mwalletsamba.AskmPin.Logintask;
import com.wpits.parser.UserAccountDetailsParser;
import com.wpits.parser.UserLoginparser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Notificationmpin extends Activity {
	Button btnLogin;
	EditText mPin;
	UserLoginparser userobject;
	TextView textViewaccountstatus,textViewaccountstatusmsg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notificationmpin);
		mPin=(EditText)findViewById(R.id.mPin);
		btnLogin=(Button)findViewById(R.id.btnLogin);
		textViewaccountstatusmsg=(TextView)findViewById(R.id.textViewaccountstatusmsg);
		textViewaccountstatus=(TextView)findViewById(R.id.textViewaccountstatus);

		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
		}
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mPin.getText().toString().length()>0) {
					new Logintask().execute();

				}
				else{

					mPin.setError("Please Provide mPin");
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notificationmpin, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class Logintask extends AsyncTask<String, Void, NotificationResponse>{
		ProgressDialog pd;
		NotificationResponse sent;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Notificationmpin.this, "",
					"Please Wait");
		}
		@Override
		protected NotificationResponse doInBackground(String... arg0) {


			String mPinstr = mPin.getText().toString();
			//Toast.makeText(getApplicationContext(), "OTP has been sent to the entered mobile number.", Toast.LENGTH_LONG).show();
			String otp = "";

			/*Bundle b = new Bundle();
			b.putString("mobile_no", mobile_no);*/

			HttpGetNotification httpnotification = new HttpGetNotification();
			sent = httpnotification.httpusernotification(getApplicationContext(), userobject.getUserDetails().getUserMobile(), mPinstr, userobject.getUser_type(), userobject.getSessionid());



			// TODO Auto-generated method stub
			return sent;
		}
		@Override
		protected void onPostExecute(NotificationResponse objectFromDoInBg) {

			pd.cancel();
			try {
				if (objectFromDoInBg!=null) {
					if (objectFromDoInBg.getResultCode().equalsIgnoreCase("200")) {
						textViewaccountstatus.setText(objectFromDoInBg.getResultCode());
						Intent i=new Intent(getApplicationContext(),Notification.class);
						i.putExtra("user_obj", userobject);
						i.putExtra("noti_obj", objectFromDoInBg);
						startActivity(i);
						finish();


					}
					else{
						textViewaccountstatus.setVisibility(View.VISIBLE);
						textViewaccountstatus.setText(objectFromDoInBg.getResultStatus());
					}

				}
				else{
					textViewaccountstatus.setText("Unable to get detail.");


				}



			} catch (Exception e) {

				e.printStackTrace();
			}

		}

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		Intent i=new Intent(getApplicationContext(),AndroidGridLayoutActivity.class);
		i.putExtra("user_obj", userobject);
		startActivity(i);
		finish();
	}
}
