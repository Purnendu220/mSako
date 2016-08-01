package com.wpits.mwalletsamba;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class Ticket extends Activity {
	EditText dest_mobile_no_EditText,amount_EditText,mPin_EditText;
	String dest_mobile_no,amount,mobile_no,mPin;
	TextView mPin_TextView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set View to register.xml
		setContentView(R.layout.activity_ticket);

		Intent in = getIntent();
		Bundle b = in.getExtras();
		 mobile_no = b.getString("mobile_no");
		 mPin = b.getString("mPin");
		dest_mobile_no_EditText = (EditText) findViewById(R.id.dest_mobile_no);

		amount_EditText = (EditText) findViewById(R.id.amount);
		

		mPin_TextView = (TextView) findViewById(R.id.mPin_text);
		mPin_EditText = (EditText) findViewById(R.id.mPin_edit);
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
				new Bookticket().execute();
			}
		});
	}
	public class Bookticket extends AsyncTask<String, String, String> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Ticket.this, "Processing  ",
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
		Toast.makeText(getApplicationContext(), "Please enter mpin booking amount is greater than 500", Toast.LENGTH_LONG).show();

	}
	else{
	HttpTransfer httpTransfer = new HttpTransfer();
	String str = httpTransfer.httptransfer(mobile_no, dest_mobile_no, mPin_EditText.getText().toString(), amount, "ticket", "Ticket", "");
    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
	String otp = "";
	Bundle b = new Bundle();
	b.putString("mobile_no", mobile_no);
	b.putString("otp", otp);
	Intent in = new Intent(Ticket.this, AndroidGridLayoutActivity.class);
	in.putExtras(b);
	startActivity(in);
	finish();

	}
}
else{
	HttpTransfer httpTransfer = new HttpTransfer();
	String str = httpTransfer.httptransfer(mobile_no, dest_mobile_no, mPin, amount, "ticket", "Ticket", "");
    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
	String otp = "";
	Bundle b = new Bundle();
	b.putString("mobile_no", mobile_no);
	b.putString("otp", otp);
	Intent in = new Intent(Ticket.this, AndroidGridLayoutActivity.class);
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
			Intent in = new Intent(getApplicationContext(), AndroidGridLayoutActivity.class);

			startActivity(in);
			finish();
		}
}