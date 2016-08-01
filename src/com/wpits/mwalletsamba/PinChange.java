package com.wpits.mwalletsamba;


import com.wpits.parser.UpdateProfileParser;
import com.wpits.parser.UserLoginparser;
import com.wpits.parser.UserLogoutParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PinChange extends Activity {
	EditText  mPin,newmPin,renewmpin;
	TextView textViewaccountstatus,textViewaccountstatusmsg;
	String mobile_no;
	boolean stat;
	Button btnLogin;
	UserLoginparser userobject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pin_change);
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 
		mobile_no =pref.getString("mobile_no", "");
		mPin=(EditText)findViewById(R.id.mPin);
		newmPin=(EditText)findViewById(R.id.newmPin);
		renewmpin=(EditText)findViewById(R.id.renewmPin);
		textViewaccountstatusmsg=(TextView)findViewById(R.id.textViewaccountstatusmsg);
		textViewaccountstatus=(TextView)findViewById(R.id.textViewaccountstatus);

		btnLogin=(Button)findViewById(R.id.btnLogin);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();      
		StrictMode.setThreadPolicy(policy);
		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
		}

		if (userobject!=null) {
			if (userobject.getResultCode().equalsIgnoreCase("108")) {
				textViewaccountstatus.setVisibility(View.VISIBLE);
				textViewaccountstatusmsg.setVisibility(View.VISIBLE);
				textViewaccountstatusmsg.setText(userobject.getResultStatus());
				textViewaccountstatus.setText("User Account InActive");

			}
		}
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mPin.getText().toString().length()<=0)
				{
					mPin.setError("Please provide mPin");
					Toast.makeText(getApplicationContext(), "Please provide mPin", Toast.LENGTH_LONG).show();
				}
				else if(newmPin.getText().toString().length()<=0){
					newmPin.setError("Please provide new mPin");

					Toast.makeText(getApplicationContext(), "Please provide newmPin", Toast.LENGTH_LONG).show();


				}

				else if(renewmpin.getText().toString().length()<=0){
					renewmpin.setError("Please re enter new mPin.");
					Toast.makeText(getApplicationContext(), "Please re enter new mPin.", Toast.LENGTH_LONG).show();


				}
				else if(!newmPin.getText().toString().equalsIgnoreCase(renewmpin.getText().toString())){
					Toast.makeText(getApplicationContext(), "New mPin and Re Enter New mPin must be same.", Toast.LENGTH_LONG).show();


				}
				else if(mPin.getText().toString().equalsIgnoreCase(newmPin.getText().toString()))
				{
					renewmpin.setError("New mPin Cannot be same as present mPin");
				}
				else{
					new changepin().execute();

				}

			}
		});

	}
	public class changepin extends AsyncTask<String, Void,  UpdateProfileParser> {
		ProgressDialog pd;
		String result;
		UpdateProfileParser response;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(PinChange.this, "Requesting mPin change",
					"Please Wait");
		}

		@Override
		protected  UpdateProfileParser doInBackground(String... arg0) {
			Httppinchange pinchange=new Httppinchange();
			response=pinchange.httpchangempin(getApplicationContext(), userobject.getUserDetails().getUserMobile(), userobject.getSessionid(), mPin.getText().toString(), newmPin.getText().toString(), userobject.getUser_type());

			return response;
		}

		@Override
		protected void onPostExecute( UpdateProfileParser objFromDoInBg) {
			// TODO Auto-generated method stub
			if (objFromDoInBg!=null) {
				if (objFromDoInBg.getResultCode().equalsIgnoreCase("200")) {
					showalert();

				}
				else{
					textViewaccountstatus.setVisibility(View.VISIBLE);
					textViewaccountstatusmsg.setVisibility(View.VISIBLE);
					textViewaccountstatus.setText("Pin Change Failed");
					textViewaccountstatusmsg.setText(objFromDoInBg.getResultStatus());
				}
			}
			else{
				
				textViewaccountstatus.setVisibility(View.VISIBLE);
				textViewaccountstatusmsg.setVisibility(View.VISIBLE);
				textViewaccountstatus.setText("Pin Change Failed");
				textViewaccountstatusmsg.setText("Try After Some time.");	
			}
			
			pd.cancel();


		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (userobject.getResultCode().equalsIgnoreCase("108")) {
			Intent in = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(in);
			finish();
		}
		else{
			Intent in = new Intent(getApplicationContext(), AndroidGridLayoutActivity.class);
			in.putExtra("user_obj", userobject);
			startActivity(in);
			finish();

		}

	}


	public void showalert()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(
				PinChange.this).create();

		// Setting Dialog Title
		alertDialog.setTitle("Pin Changed Successfully");

		// Setting Dialog Message
		alertDialog.setMessage("Please Logout and Login Again.");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setCancelable(false);
		// Setting OK Button
		alertDialog.setButton("Logout", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to execute after dialog closed
				new Logout().execute();
			}
		});

		// Showing Alert Message
		alertDialog.show();


	}
	public class Logout extends AsyncTask<String, Void, UserLogoutParser> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(PinChange.this, "Loging out... ",
					"Please Wait");
		}

		@Override
		protected UserLogoutParser doInBackground(String... arg0) {SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 

		HttpLogout logout=new HttpLogout();
		UserLogoutParser logoutobj=logout.httpuserlogout(getApplicationContext(), userobject.getUserDetails().getUserMobile(), userobject.getSessionid());

		return logoutobj;

		}

		@Override
		protected void onPostExecute(UserLogoutParser strFromDoInBg) {
			pd.dismiss();
			Intent in=new Intent(getApplicationContext(),LoginActivity.class);
			startActivity(in);
			finish();




		}
	}
}
