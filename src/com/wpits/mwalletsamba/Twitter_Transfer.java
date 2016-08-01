package com.wpits.mwalletsamba;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Twitter_Transfer extends Activity {
	EditText dest_mobile_no_EditText,amount_EditText,mPin_EditText;
	String dest_mobile_no,amount,mobile_no,mPin;
	TextView mPin_TextView;
	String Twitterbenefname,Twitterbenefid;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set View to register.xml
		setContentView(R.layout.activity_twitter__transfer);
		 SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 
		 mobile_no =pref.getString("mobile_no", "");
		 mPin =pref.getString("mPin", "");
		Intent in = getIntent();
		Bundle b = in.getExtras();
		Twitterbenefname = b.getString("benefname");
		Twitterbenefid = b.getString("twitterid");
		dest_mobile_no_EditText = (EditText) findViewById(R.id.dest_mobile_no);

		amount_EditText = (EditText) findViewById(R.id.amount);
		

		mPin_TextView = (TextView) findViewById(R.id.mPin_text);
		mPin_EditText = (EditText) findViewById(R.id.mPin_edit);
		dest_mobile_no_EditText.setText(Twitterbenefname);
		amount_EditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				// TODO Auto-generated method stub
				System.out.println("ONTEXT TEXT CHANGE:-"+s.toString());            

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				// TODO Auto-generated method stub
				System.out.println("BEFORE TEXT CHANGE:-"+s.toString());            
			}

			@Override
			public void afterTextChanged(Editable s) {
				try {
				int amt=	Integer.parseInt(s.toString());
				amount=s.toString();
				if(amt>500)
				{
					mPin_EditText.setVisibility(View.VISIBLE);
					mPin_TextView.setVisibility(View.VISIBLE);
				}
				else{
					mPin_EditText.setVisibility(View.INVISIBLE);
					mPin_TextView.setVisibility(View.INVISIBLE);
				}
					
					
				} catch (Exception e) {
					amount_EditText.setText("");

Toast.makeText(getApplicationContext(), "Please enter a valid amount", Toast.LENGTH_LONG).show();					// TODO: handle exception
				}
				
			}


		});
		final Button next = (Button)findViewById(R.id.pay_submit);
		// Listening to Login Screen link
		next.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				amount = amount_EditText.getText().toString();
				dest_mobile_no = dest_mobile_no_EditText.getText().toString();
				new Cashout().execute();
			}
		});
	}
	public class Cashout extends AsyncTask<String, String, String> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Twitter_Transfer.this, "Twitter Transfer in progress  ",
					"Please Wait");
		}

		@Override
		protected String doInBackground(String... arg0) {

			// TextView textmessage = (TextView) findViewById(R.id.textmessage);

runOnUiThread(new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(dest_mobile_no!=null && dest_mobile_no.length()<10){
			Toast.makeText(getApplicationContext(), "Please enter valid destination mobile number", Toast.LENGTH_LONG).show();
		}else if(amount!=null && amount.length()<0){
			Toast.makeText(getApplicationContext(), "Please enter valid amount", Toast.LENGTH_LONG).show();
		}/*else if(amount!=null && Integer.parseInt(amount)>500 && mPin_TextView.getVisibility()==View.INVISIBLE){
mPin_EditText.setVisibility(View.VISIBLE);
mPin_TextView.setVisibility(View.VISIBLE);
next.setText("Submit");
//Toast.makeText(getApplicationContext(), "Please enter valid amount", Toast.LENGTH_LONG).show();
}*/else 
/*if((mPin!=null && mPin_EditText!=null && mPin_EditText.getText()!=null && mPin.equals(mPin_EditText.getText().toString())) || (amount!=null && Integer.parseInt(amount)<500))*/{
if(mPin_EditText.getVisibility()==View.VISIBLE)
{
	if(mPin_EditText.getText().toString().length()<=0)
	{
		Toast.makeText(getApplicationContext(), "Please enter mpin amount is greater than 500", Toast.LENGTH_LONG).show();

	}
	else{
	Httptransferviatwitter httpTransfer = new Httptransferviatwitter();
	String str = httpTransfer.httptransfertwitter(mobile_no, mPin_EditText.getText().toString(), Twitterbenefid, amount, getApplicationContext());
    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
	String otp = "";
	Bundle b = new Bundle();
	b.putString("mobile_no", mobile_no);
	b.putString("otp", otp);
	Intent in = new Intent(Twitter_Transfer.this, AndroidGridLayoutActivity.class);

	in.putExtras(b);
	startActivity(in);
	finish();

	}
}
else{
	Httptransferviatwitter httpTransfer = new Httptransferviatwitter();
	String str = httpTransfer.httptransfertwitter(mobile_no, mPin, Twitterbenefid, amount, getApplicationContext());
   Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
	String otp = "";
	Bundle b = new Bundle();
	b.putString("mobile_no", mobile_no);
	b.putString("otp", otp);
	Intent in = new Intent(Twitter_Transfer.this, TwitterBenefList.class);

	in.putExtras(b);
	startActivity(in);
	finish();

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
		Intent in = new Intent(Twitter_Transfer.this, TwitterBenefList.class);

		startActivity(in);
		finish();
	}
}