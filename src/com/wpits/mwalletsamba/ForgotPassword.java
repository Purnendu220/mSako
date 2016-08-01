package com.wpits.mwalletsamba;


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
import android.widget.Toast;

public class ForgotPassword extends Activity {
EditText mobile_no;
Button btnLogin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		mobile_no=(EditText)findViewById(R.id.mobile_no);
		btnLogin=(Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new SendMobile().execute();
			}
		});
		
		
		
	}
	 public class SendMobile extends AsyncTask<String, String, String> {
			ProgressDialog pd;
			String result;
			@Override
			protected void onPreExecute() {

				pd = ProgressDialog.show(ForgotPassword.this, "",
						"Please Wait");
			}

			@Override
			protected String doInBackground(String... arg0) {

				// TextView textmessage = (TextView) findViewById(R.id.textmessage);

	runOnUiThread(new Runnable() {
		
		@Override
		public void run() {
			if(mobile_no.getText().toString().length()<10)
			{
				Toast.makeText(getApplicationContext(), "Please Enter a valid Mobile No", Toast.LENGTH_LONG).show();
			}
			else{
				Httpforgotpassword forgot=new Httpforgotpassword();
			boolean stat=	forgot.httpuserforgotpassword(getApplicationContext(), mobile_no.getText().toString());
           if(stat)
           {Bundle b = new Bundle();
       	     b.putString("mobile_no", mobile_no.getText().toString());
       	     b.putString("otp", "");

       
        	   Intent in=new Intent(ForgotPassword.this,Forgot_Otp.class);
        	   in.putExtras(b);

        	   startActivity(in);
           	finish();

        	    
           }
           else{
				Toast.makeText(getApplicationContext(), "Something went wrong try after some time ", Toast.LENGTH_LONG).show();
 
           }
			}
					}
	});
			
		

				return null;
			}

			@Override
			protected void onPostExecute(String strFromDoInBg) {
				pd.dismiss();
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
