package com.wpits.mwalletsamba;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class EnterOTP_RegisterActivity extends Activity {
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
	            	Intent in = new Intent(EnterOTP_RegisterActivity.this, RegisterActivity.class);
	            	in.putExtras(b);
	            	startActivity(in);
	            	finish();

                }
            }
        });
    }
    /*private BroadcastReceiver gcmReceiver = new BroadcastReceiver() {

    	@Override
    	public void onReceive(Context context, Intent intent) {

    		String broadcastMessage = intent.getExtras().getString("gcm");
    		StringBuffer sb = new StringBuffer();
    	      sb.append(broadcastMessage+ "\n");
    //Toast.makeText(getApplicationContext(), broadcastMessage, Toast.LENGTH_LONG).show();
    		if (broadcastMessage != null) {
    			// display our received message
    			runOnUiThread(new Runnable() {
    				
    				@Override
    				public void run() {
    					
    				}
    			});
    			//tvBroadcastMessage.setText(broadcastMessage);
    		}
    	
    	}
    };*/
    private String getotp() {
	    SharedPreferences pref = PreferenceManager
	            .getDefaultSharedPreferences(getApplicationContext());
	    return pref.getString("OTP", "");
	}
}