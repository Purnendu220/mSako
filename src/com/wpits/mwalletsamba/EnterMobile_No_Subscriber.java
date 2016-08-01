package com.wpits.mwalletsamba;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class EnterMobile_No_Subscriber extends Activity {
	EditText mobile_no_EditText ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.activity_enter_mobile__no__subscriber);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();      
		StrictMode.setThreadPolicy(policy);
        	Button next = (Button)findViewById(R.id.btnLogin);
        	 mobile_no_EditText = (EditText) findViewById(R.id.mobile_no);

        // Listening to Login Screen link
        	next.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
            	new SendMobile().execute();
            }
        });
        
    }
    public class SendMobile extends AsyncTask<String, String, String> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(EnterMobile_No_Subscriber.this, "",
					"Please Wait");
		}

		@Override
		protected String doInBackground(String... arg0) {

			// TextView textmessage = (TextView) findViewById(R.id.textmessage);

runOnUiThread(new Runnable() {
	
	@Override
	public void run() {

        
        String mobile_no = mobile_no_EditText.getText().toString();
        if(mobile_no!=null && mobile_no.length()<10){
        	Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_LONG).show();
        }else{
        	
        	
        	
    	Bundle b = new Bundle();
    	b.putString("mobile_no", mobile_no);
    	b.putString("gcmId", getgcmid());
    	
    	Httpsendmobile httpsendmobile = new Httpsendmobile();
    	String sent = httpsendmobile.httpsendmobile(EnterMobile_No_Subscriber.this, mobile_no, getgcmid(),"Subscriber");
    	if(sent.equalsIgnoreCase("1")){
        	Intent in = new Intent(EnterMobile_No_Subscriber.this, Enter_otp_subscriber.class);
        	
        	in.putExtras(b);
        	startActivity(in);
        	Toast.makeText(getApplicationContext(), "OTP has been sent to the entered mobile number.", Toast.LENGTH_LONG).show();
        	String otp = "";
        	finish();

    	}else if(sent.equalsIgnoreCase("2")){
    		Intent in = new Intent(EnterMobile_No_Subscriber.this, AndroidGridLayoutActivity.class);
    		in.putExtras(b);
        	startActivity(in);
        	Toast.makeText(getApplicationContext(), "Subscriber already Registered ", Toast.LENGTH_LONG).show();
        	finish();

    	}
    	else{
        	Toast.makeText(getApplicationContext(), "Problem in creating Subscriber try after sometime.", Toast.LENGTH_LONG).show();

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
    private String getgcmid() {
	    SharedPreferences pref = PreferenceManager
	            .getDefaultSharedPreferences(getApplicationContext());
	    return pref.getString("GCMID", "");
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