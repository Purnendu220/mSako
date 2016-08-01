package com.wpits.mwalletsamba;

import com.wpits.mwalletsamba.PinChange.Logout;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Changempinfirst extends Activity {
	EditText editTextotp,editTextnewmpin,editTextrenewmpin;
	Button buttonchange;
	UserLoginparser userobject;
	String mpin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changempinfirst);
		editTextotp=(EditText)findViewById(R.id.editTextotp);
		editTextnewmpin=(EditText)findViewById(R.id.editTextnewmpin);
		editTextrenewmpin=(EditText)findViewById(R.id.editTextrenewmpin);
		buttonchange=(Button)findViewById(R.id.buttonchange);
		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
			mpin=(String)iuser.getExtras().getString("mpin");
		}

		buttonchange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String mpin=editTextnewmpin.getText().toString();
				String rempin=editTextrenewmpin.getText().toString();
				String otp=editTextnewmpin.getText().toString();

				if (otp.length()>0) {
					if (mpin.length()>0) {
						if (rempin.length()>0) {
							if (mpin.equalsIgnoreCase(rempin)) {
								if (otp.equalsIgnoreCase(userobject.getOtp())) {
									new changepin().execute();
								} else {
									editTextotp.setError("Please Enter Correct OTP.");
								}


							} 
							else {
								editTextrenewmpin.setError("Confirm MPIN must be same as new MPIN");
							}

						} else {
							editTextrenewmpin.setError("Please provide Confirm mpin");
						}

					} else {
						editTextnewmpin.setError("Please provide new mpin");
					}

				} else {
					editTextotp.setError("Please rpvide OTP.");

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

			pd = ProgressDialog.show(Changempinfirst.this, "Requesting mPin change",
					"Please Wait");
		}

		@Override
		protected  UpdateProfileParser doInBackground(String... arg0) {
			Httppinchange pinchange=new Httppinchange();
			response=pinchange.httpchangempin(getApplicationContext(), userobject.getUserDetails().getUserMobile(), userobject.getSessionid(), mpin, editTextnewmpin.getText().toString(), userobject.getUser_type());

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
					showalert1(objFromDoInBg.getResultStatus());
				}
			}
			else{

				showalert1("Pin Change Failed");	
			}

			pd.cancel();


		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.changempinfirst, menu);
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


	public void showalert()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(
				Changempinfirst.this).create();

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

	public void showalert1(String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(
				Changempinfirst.this).create();

		// Setting Dialog Title
		alertDialog.setTitle("Error");

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setCancelable(false);
		// Setting OK Button
		alertDialog.setButton("Try Again", new DialogInterface.OnClickListener() {
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

			pd = ProgressDialog.show(Changempinfirst.this, "Loging out... ",
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		AlertDialog alertDialog = new AlertDialog.Builder(
				Changempinfirst.this).create();

		// Setting Dialog Title
		alertDialog.setTitle("Alert");

		// Setting Dialog Message
		alertDialog.setMessage("Are you sure want to logout");

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
}
