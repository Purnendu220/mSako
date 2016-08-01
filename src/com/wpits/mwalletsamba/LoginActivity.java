package com.wpits.mwalletsamba;



import java.util.ArrayList;

import com.wpits.mwalletsamba.AndroidGridLayoutActivity.Logout;
import com.wpits.parser.UserLoginparser;
import com.wpits.parser.UserLogoutParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText mMobile,mPin_EditText,otp;
	//TextView otp_lable,mpin_lable;
	//TextView mobile_lable;
	TextView textViewaccountstatus,textViewaccountstatusmsg;
	Animation animSideDown;
	String otpstr="1234";
	Button next;
	boolean ismpinverified=false;
	UserLoginparser userobject;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setting default screen to login.xml
		setContentView(R.layout.login);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();      
		StrictMode.setThreadPolicy(policy);
		mMobile=(EditText)findViewById(R.id.mMobile);
		mPin_EditText = (EditText) findViewById(R.id.mPin);
		otp=(EditText)findViewById(R.id.otp);
		//otp_lable=(TextView)findViewById(R.id.otp_lable);
		//mobile_lable=(TextView)findViewById(R.id.mobile_lable);
		//mpin_lable=(TextView)findViewById(R.id.mpin_lable);
		textViewaccountstatus=(TextView)findViewById(R.id.textViewaccountstatus);
		textViewaccountstatusmsg=(TextView)findViewById(R.id.textViewaccountstatusmsg);
		animSideDown = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_down);


		next = (Button)findViewById(R.id.btnLogin);
		Button forgotbutton = (Button)findViewById(R.id.btnforgetpassword);
		forgotbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LoginActivity.this, ForgotPassword.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();


			}
		});
		// Listening to Login Screen link
		next.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				if(isNetworkAvailable())
				{
					if (ismpinverified) {
						if (!VerifyOTP()) {
							otp.setError("Please enter valid otp");
						}

					}
					else{
						hideKeybord(next);
						new Logintask().execute();
					}




				}
				else{

					Toast.makeText(getApplicationContext(), "Please connect to internet before login", Toast.LENGTH_LONG).show();

				}
			}

		});

		mMobile.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.toString().length()>9) {
					//mpin_lable.setVisibility(View.VISIBLE);
					//mpin_lable.setAnimation(animSideDown);
					mPin_EditText.setVisibility(View.VISIBLE);
					mPin_EditText.setAnimation(animSideDown);					
					mPin_EditText.requestFocus();



				}

			}
		});

		otp.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub


			}
		});

		mPin_EditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.toString().length()>=4) {
					next.setVisibility(View.VISIBLE);
					next.setAnimation(animSideDown);
				}

			}
		});
	}

	public class Logintask extends AsyncTask<String, Void, UserLoginparser>{
		ProgressDialog pd;
		UserLoginparser sent;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(LoginActivity.this, "Loging in",
					"Please Wait");
		}
		@Override
		protected UserLoginparser doInBackground(String... arg0) {


			String mPin = mPin_EditText.getText().toString();
			String mobile_no=mMobile.getText().toString();
			//Toast.makeText(getApplicationContext(), "OTP has been sent to the entered mobile number.", Toast.LENGTH_LONG).show();
			String otp = "";

			/*Bundle b = new Bundle();
			b.putString("mobile_no", mobile_no);*/

			HttpLogin httpLogin = new HttpLogin();
			sent = httpLogin.httpuserlogin(LoginActivity.this, mobile_no, mPin);



			// TODO Auto-generated method stub
			return sent;
		}
		@Override
		protected void onPostExecute(UserLoginparser objectFromDoInBg) {

			pd.cancel();
			try {
				if (objectFromDoInBg!=null) {
					System.out.println("object is not null");
					if (objectFromDoInBg.getResultCode().equalsIgnoreCase("200")) {
						ismpinverified=true;
						userobject=objectFromDoInBg;
						mPin_EditText.setEnabled(false);

						textViewaccountstatus.setVisibility(View.VISIBLE);
						textViewaccountstatusmsg.setVisibility(View.VISIBLE);
						textViewaccountstatus.setText("Verify OTP ");
						textViewaccountstatusmsg.setText("Please verify OTP we have sent to your mobile.");
						//otp_lable.setVisibility(View.VISIBLE);
						//otp_lable.setAnimation(animSideDown);
						otp.setVisibility(View.VISIBLE);
						otp.setAnimation(animSideDown);
						otp.requestFocus();

					}

					else if (objectFromDoInBg.getResultCode().equalsIgnoreCase("108")){
						userobject=objectFromDoInBg;
						ismpinverified=true;
						Intent i=new Intent(getApplicationContext(),Changempinfirst.class);
						i.putExtra("user_obj", userobject);
						i.putExtra("mpin", mPin_EditText.getText().toString());
						startActivity(i);
						finish();
						/*mPin_EditText.setEnabled(false);
						textViewaccountstatus.setVisibility(View.VISIBLE);
						textViewaccountstatusmsg.setVisibility(View.VISIBLE);
						textViewaccountstatus.setText("Account InActive");
						textViewaccountstatusmsg.setText("Please verify OTP we have sent to your mobile.");
						otp_lable.setVisibility(View.VISIBLE);
						otp_lable.setAnimation(animSideDown);
						otp.setVisibility(View.VISIBLE);
						otp.setAnimation(animSideDown);
						otp.requestFocus();*/

					}
					else if(objectFromDoInBg.getResultCode().equalsIgnoreCase("150")){
						showAlert1("User Already Logged in Please Logout before you login.", "logout",objectFromDoInBg.getSessionid());
					}

					else{
						textViewaccountstatus.setVisibility(View.VISIBLE);
						textViewaccountstatusmsg.setVisibility(View.VISIBLE);
						textViewaccountstatus.setText("Login Failed");
						textViewaccountstatusmsg.setText(objectFromDoInBg.getResultCode()+":"+objectFromDoInBg.getResultStatus());

						Toast.makeText(getApplicationContext(), objectFromDoInBg.getResultStatus(), Toast.LENGTH_LONG);

					}


				}
				else{
					System.out.println("object is  null");
					textViewaccountstatus.setVisibility(View.VISIBLE);
					textViewaccountstatusmsg.setVisibility(View.VISIBLE);
					textViewaccountstatus.setText("Login Failed");
					textViewaccountstatusmsg.setText("Internet connection issue.Please check your internet Connection.");


				}
				/*Intent i = new Intent(LoginActivity.this, AndroidGridLayoutActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
				 */

			} catch (Exception e) {
				// TODO: handle exception
				textViewaccountstatus.setVisibility(View.VISIBLE);
				textViewaccountstatusmsg.setVisibility(View.VISIBLE);
				textViewaccountstatus.setText("Login Failed"+e.getMessage());
				textViewaccountstatusmsg.setText("Somthing went wrong.Try again.");
				e.printStackTrace();
			}

		}

	}
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		showexitdialog();	
	}

	public void hideKeybord(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);}

	public boolean VerifyOTP()
	{
		if (userobject!=null) {
			if (userobject.getResultCode().equalsIgnoreCase("108")) {
				if (otp.getText().toString().equalsIgnoreCase(userobject.getOtp())) {
					Intent i=new Intent(getApplicationContext(),PinChange.class);
					i.putExtra("user_obj", userobject);
					startActivity(i);
					finish();
					return true;
				}
				else{
					return false;
				}
			}
			else if(userobject.getResultCode().equalsIgnoreCase("200")){

				if (otp.getText().toString().equalsIgnoreCase(userobject.getOtp())) {
					Intent i=new Intent(getApplicationContext(),AndroidGridLayoutActivity.class);
					i.putExtra("user_obj", userobject);
					startActivity(i);
					finish();
					return true;
				}
				else{
					return false;
				}


			}
			else{
				return false;
			}
		}
		else{

			return false;
		}

	}

	void showexitdialog(){
		LayoutInflater factory = LayoutInflater.from(this);
		final View previewDialogView = factory.inflate( R.layout.exit_dialog, null);
		final AlertDialog exitDialog = new AlertDialog.Builder(LoginActivity.this,R.style.DialogTheme).create();
		TextView exittext=(TextView)previewDialogView.findViewById(R.id.textView3);

		exittext.setText("Do you want to Exit?");


		exitDialog.setView(previewDialogView);
		exitDialog.setCancelable(false);
		previewDialogView.findViewById(R.id.buttonno).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				exitDialog.dismiss();
			}
		});
		previewDialogView.findViewById(R.id.buttonyes).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		exitDialog.show();
	}

	public void showAlert1(String message,final String type,final String sessionid)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);

		//Setting Dialog Title
		alertDialog.setTitle("Alert");
		alertDialog.setCancelable(false);
		//Setting Dialog Message
		alertDialog.setMessage(message);
		//On Pressing Setting button


		alertDialog.setNegativeButton("Logout", new DialogInterface.OnClickListener() 
		{   
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				runOnUiThread( new Runnable() {
					public void run() {
						new Logout(sessionid).execute();

					}
				});


			}
		});



		alertDialog.show();
	}

	public class Logout extends AsyncTask<String, Void, UserLogoutParser> {
		ProgressDialog pd;
		String result;
		String sessionid;

		public Logout(String sessionid)
		{
			this.sessionid=sessionid;
		}
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(LoginActivity.this, "Loging out... ",
					"Please Wait");
		}

		@Override
		protected UserLogoutParser doInBackground(String... arg0) {SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 

		HttpLogout logout=new HttpLogout();
		UserLogoutParser logoutobj=logout.httpuserlogout(getApplicationContext(), mMobile.getText().toString(), sessionid);

		return logoutobj;

		}

		@Override
		protected void onPostExecute(UserLogoutParser strFromDoInBg) {
			pd.dismiss();
			if (strFromDoInBg!=null) {
				if (strFromDoInBg.getResultCode().equalsIgnoreCase("200")) {
					Intent i=new Intent(getApplicationContext(),LoginActivity.class);
					startActivity(i);
					finish();

				}
				else{

					Toast.makeText(getApplicationContext(), "Logout Failed Network Error. ", Toast.LENGTH_LONG);	
				}

			}



		}
	}
}