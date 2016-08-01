package com.wpits.mwalletsamba;


import com.wpits.parser.UserLoginparser;
import com.wpits.utils.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class PayToWithoutIdActivity extends Activity {
	EditText dest_mobile_no_EditText,amount_EditText,remark_edit;
	String dest_mobile_no,amount,mobile_no,mPin,remark,accounttype;
	TextView mPin_TextView;
	UserLoginparser userobject;
	Spinner spinneracctype;
	TextView textacctype;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set View to register.xml
		setContentView(R.layout.pay_to_withoutid);

		Intent iuser = getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
		}
		dest_mobile_no_EditText = (EditText) findViewById(R.id.dest_mobile_no);

		amount_EditText = (EditText) findViewById(R.id.amount);


		mPin_TextView = (TextView) findViewById(R.id.mPin_text);
		remark_edit = (EditText) findViewById(R.id.remark_edit);
		spinneracctype=(Spinner)findViewById(R.id.spinneracctype);
		textacctype=(TextView)findViewById(R.id.textacctype);
		final Button next = (Button)findViewById(R.id.pay_submit);
		// Listening to Login Screen link
		// Listening to Login Screen link

		if (userobject.getUserDetails().getIsloanapproved().equalsIgnoreCase("APPROVED")) {
			spinneracctype.setVisibility(View.VISIBLE);
			textacctype.setVisibility(View.VISIBLE);
		} 
		else {
			spinneracctype.setVisibility(View.GONE);
			textacctype.setVisibility(View.GONE);
			accounttype=Utility.ACCOUNT_WALLET;

		}
		spinneracctype.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (spinneracctype.getSelectedItemPosition()>0) {
					switch (spinneracctype.getSelectedItemPosition()) {
					case 1:
						accounttype=Utility.ACCOUNT_WALLET;

						break;
					case 2:
						accounttype=Utility.ACCOUNT_LOAN;

						break;
					default:
						break;
					}

				} else {
					accounttype="";
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		next.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				amount = amount_EditText.getText().toString();
				dest_mobile_no = dest_mobile_no_EditText.getText().toString();
				remark=remark_edit.getText().toString();
				if(dest_mobile_no!=null && dest_mobile_no.length()<10){
					Toast.makeText(getApplicationContext(), "Please enter valid destination mobile number", Toast.LENGTH_LONG).show();
				}

				else{
					if(amount!=null && amount.length()<=0){
						Toast.makeText(getApplicationContext(), "Please enter valid amount", Toast.LENGTH_LONG).show();
					}	
					else{
						if (remark!=null&&remark.length()<=0) {
							Toast.makeText(getApplicationContext(), "Please enter remark.", Toast.LENGTH_LONG).show();

						} else {

							new Transfer().execute();

						}

					}

				}
			}
		});
	}

	public class Transfer extends AsyncTask<String, Void, Boolean> {
		ProgressDialog pd;
		String result;
		Boolean str=true;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(PayToWithoutIdActivity.this, "Transferring  ",
					"Please Wait");
		}

		@Override
		protected Boolean doInBackground(String... arg0) {



			// TODO Auto-generated method stub






			return str;
		}

		@Override
		protected void onPostExecute(Boolean strFromDoInBg) {
			pd.dismiss();
			if (strFromDoInBg) {
				Transferobject object=new Transferobject(userobject.getUserDetails().getUserMobile(), dest_mobile_no, amount, remark, System.currentTimeMillis()+"", Utility.TRANSFER_MPESA, accounttype);
				Intent in = new Intent(getApplicationContext(), MpinOtpVerificationTransfer.class);
				in.putExtra("user_obj", userobject);
				in.putExtra("transfer_obj", object);

				startActivity(in);
				finish();
			}
			else{
				Toast.makeText(getApplicationContext(), "Something went wrong try again.", Toast.LENGTH_LONG).show();


			}


		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(), TransferOptionActivity.class);
		in.putExtra("user_obj", userobject);
		startActivity(in);
		finish();
	}
}