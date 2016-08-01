package com.wpits.mwalletsamba;
import java.util.Calendar;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
 
public class Register_Subscriber extends Activity {
	
	EditText dob_EditText ;
	 private Calendar cal;
	 private int day;
	 private int month;
	 private int year;
	 EditText alternate_mobile_EditText,mPin_EditText,nrc_EditText,name_EditText,re_mPin_EditText,email_EditText,address_EditText;
 	String name ,	alternate_mobile,    nrc,  dob,  mPin , re_mPin,mobile_no,otp_gen,email,address;       
String type="Subscriber";
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.activity_register__subscriber);
    	 alternate_mobile_EditText = (EditText) findViewById(R.id.alternate_mobile);
    	 name_EditText = (EditText) findViewById(R.id.name);
    	 nrc_EditText = (EditText) findViewById(R.id.nrc);
    	 mPin_EditText = (EditText) findViewById(R.id.mPin);
    	 re_mPin_EditText = (EditText) findViewById(R.id.re_mPin);
    	 email_EditText=(EditText)findViewById(R.id.email);
    	 address_EditText=(EditText)findViewById(R.id.address);

        Intent in = getIntent();
        Bundle b = in.getExtras();
       mobile_no = b.getString("mobile_no");
         otp_gen = b.getString("otp");
 
        dob_EditText = (EditText) findViewById(R.id.dob);
        ImageButton ib = (ImageButton) findViewById(R.id.imageButton1);
    	Calendar cal = Calendar.getInstance();
    	  day = cal.get(Calendar.DAY_OF_MONTH);
    	  month = cal.get(Calendar.MONTH);
    	  year = cal.get(Calendar.YEAR);
    	  ib.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
            	showDialog(0);
            }
        });
    	
        Button next = (Button)findViewById(R.id.btnLogin);
        // Listening to Login Screen link
        	next.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
            	 name = name_EditText.getText().toString();
            	 nrc = nrc_EditText.getText().toString();
            	 dob = dob_EditText.getText().toString();
            	 mPin = mPin_EditText.getText().toString();
            	 re_mPin = re_mPin_EditText.getText().toString();
            	 alternate_mobile = alternate_mobile_EditText.getText().toString();
            	 email=email_EditText.getText().toString();
            	 address=address_EditText.getText().toString();
            	if(name!=null && name.length()<=0 ){
                	Toast.makeText(getApplicationContext(), "	Please enter your name	", Toast.LENGTH_LONG).show();
                }else if(nrc!=null && nrc.length()<=0 ){
                	Toast.makeText(getApplicationContext(), "	Please enter NRC	", Toast.LENGTH_LONG).show();
                }else if(dob!=null && dob.length()<=0 && dob.indexOf("/")>0){
                	Toast.makeText(getApplicationContext(), "	Please enter DOB	", Toast.LENGTH_LONG).show();
                }else if(mPin!=null && mPin.length()<=0 ){
                	Toast.makeText(getApplicationContext(), "Please enter mPin", Toast.LENGTH_LONG).show();
                }else if(re_mPin!=null && (re_mPin.length()<=0 || !re_mPin.equals(mPin))){
                	Toast.makeText(getApplicationContext(), "Please re enter mPin", Toast.LENGTH_LONG).show();
                }            	
            	else{
            		
        		     new Transfer().execute();
        		    
        		   
        		    	
                }
            }
        });
    }
    
	public class Transfer extends AsyncTask<String, String, String> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Register_Subscriber.this, "Registering ",
					"Please Wait");
		}

		@Override
		protected String doInBackground(String... arg0) {

			// TextView textmessage = (TextView) findViewById(R.id.textmessage);

runOnUiThread(new Runnable() {
	
	@Override
	public void run() {
		 Httpsendregistration httpsendregistration = new Httpsendregistration();
		    boolean registered = httpsendregistration.sendregistration(mobile_no, otp_gen, name, mPin, nrc, alternate_mobile, dob, type,address,email,"Subscriber");
		    
 		if(registered){
 		    SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 
 		  /*  Editor editor = pref.edit();
     		editor.putString("mobile_no", mobile_no);
     		editor.putString("otp", otp_gen);
     		editor.putString("name", name);
     		editor.putString("nrc", nrc);
     		editor.putString("mPin", mPin);
     		editor.putString("alternate_mobile", alternate_mobile);
     		editor.putString("dob", dob);
     		editor.putString("type", type);
     		editor.putString("email", email);
     		editor.putString("address", address);

     		editor.commit();*/
     		
         	Bundle b = new Bundle();
         	b.putString("mobile_no", mobile_no);
         	Intent in = new Intent(Register_Subscriber.this, AndroidGridLayoutActivity.class);
         	in.putExtras(b);
         	startActivity(in);
        	finish();

 		    }else{
 		    	Toast.makeText(getApplicationContext(), "	Please check your internet connection.	", Toast.LENGTH_LONG).show();
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
    @Deprecated
    protected Dialog onCreateDialog(int id) {
     return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
     public void onDateSet(DatePicker view, int selectedYear,
       int selectedMonth, int selectedDay) {
       dob_EditText.setText(selectedDay + " / " + (selectedMonth + 1) + " / "+ selectedYear);
     }
    };
    @Override
  	public void onBackPressed() {
  		// TODO Auto-generated method stub
  		super.onBackPressed();
  		Intent in = new Intent(getApplicationContext(), AndroidGridLayoutActivity.class);

  		startActivity(in);
  		finish();
  	}
}