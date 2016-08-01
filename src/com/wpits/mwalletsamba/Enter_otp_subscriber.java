package com.wpits.mwalletsamba;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class Enter_otp_subscriber extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.enter_otp);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        final String mobile_no = b.getString("mobile_no");
        final String otp_gen = getotp();
 
        Button next = (Button)findViewById(R.id.btnLogin);
        // Listening to Login Screen link
        	next.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                                
            	EditText otp_EditText = (EditText) findViewById(R.id.otp);
                String otp = otp_EditText.getText().toString();
                if(otp!=null && otp.length()<=0 && !otp_gen.equals(otp)){
                	Toast.makeText(getApplicationContext(), "Please enter valid OTP.", Toast.LENGTH_LONG).show();
                }else{
	            	Bundle b = new Bundle();
	            	b.putString("mobile_no", mobile_no);
	            	b.putString("otp", otp);
	            	Intent in = new Intent(Enter_otp_subscriber.this, Register_Subscriber.class);
	            	in.putExtras(b);
	            	startActivity(in);
	            	finish();

                }
            }
        });
    }
    
    private String getotp() {
	    SharedPreferences pref = PreferenceManager
	            .getDefaultSharedPreferences(getApplicationContext());
	    return pref.getString("OTP", "");
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