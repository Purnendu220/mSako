package com.wpits.mwalletsamba;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forgot_Reset_Password extends Activity {
	EditText mPin,re_mPin;
	Button btnLogin;
	boolean stat;
	String mobile_no,smPin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot__reset__password);
		mPin=(EditText)findViewById(R.id.mPin);
		re_mPin=(EditText)findViewById(R.id.re_mPin);
		btnLogin=(Button)findViewById(R.id.btnLogin);
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 
		mobile_no =pref.getString("mobile_no", "");
		smPin =pref.getString("mPin", "");

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mPin.getText().toString().length()<=0)
				{
					Toast.makeText(getApplicationContext(), "Please enter mPin", Toast.LENGTH_LONG).show();	
				}
				else if(re_mPin.getText().toString().length()<=0)
				{
					Toast.makeText(getApplicationContext(), "Please reenter mPin ", Toast.LENGTH_LONG).show();	

				}
				else if(!mPin.getText().toString().equals(re_mPin.getText().toString())){
					Toast.makeText(getApplicationContext(), "mPin not match please reenter", Toast.LENGTH_LONG).show();	

				}
				else{

					new SendMobile().execute();
				}
			}
		});



	}
	public class SendMobile extends AsyncTask<String, String, String> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Forgot_Reset_Password.this, "",
					"Please Wait");
		}

		@Override
		protected String doInBackground(String... arg0) {

			// TextView textmessage = (TextView) findViewById(R.id.textmessage);

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Httppinchange pinchange=new Httppinchange();
					//	stat=pinchange.httpsendmobile(getApplicationContext(), mobile_no, smPin, mPin.getText().toString());

				}
			});



			return null;
		}

		@Override
		protected void onPostExecute(String strFromDoInBg) {
			if(stat)
			{

				Toast.makeText(getApplicationContext(), "Pin Changed Successfully", Toast.LENGTH_LONG).show();
				Bundle b = new Bundle();
				b.putString("mobile_no", mobile_no);
				b.putString("otp", "");
				Intent in = new Intent(Forgot_Reset_Password.this, LoginActivity.class);
				in.putExtras(b);
				startActivity(in);
				finish();

			}
			else{

				Toast.makeText(getApplicationContext(), "Pin Change Failed Try after some time", Toast.LENGTH_LONG).show();
				Bundle b = new Bundle();
				b.putString("mobile_no", mobile_no);
				b.putString("otp", "");
				Intent in = new Intent(Forgot_Reset_Password.this, LoginActivity.class);
				in.putExtras(b);
				startActivity(in);
				finish();


			}
			pd.cancel();
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent in = new Intent(getApplicationContext(), AndroidGridLayoutActivity.class);

		startActivity(in);
		finish();
	}

}
