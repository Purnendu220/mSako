package com.wpits.mwalletsamba;

import com.wpits.parser.LoanApplicationRequestParser;
import com.wpits.parser.LoanRequstResponse;
import com.wpits.parser.TransferResponseParser;
import com.wpits.parser.UserLoginparser;
import com.wpits.parser.VerificationParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class MpinOtpVerificationTransfer extends Activity {
	Button btnLogin,btnbacktoloan;
	EditText mPin,otp;
	TextView mpin_lable,otp_lable;
	UserLoginparser userobject;
	Transferobject tansferobject;
	boolean ismpinverified=false,isotpverified=false;
	String yourotp;
	TextView tpuuptext;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mpin_otp_verification_transfer);
		initview();
		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
			tansferobject=(Transferobject)iuser.getExtras().getSerializable("transfer_obj");

		}

		btnbacktoloan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ismpinverified) {

					if (isotpverified) {
						new TransferMoney().execute();				
					}
					else{
						if (otp.getText().toString().length()>3) {
							if (otp.getText().toString().equalsIgnoreCase(yourotp)) {
								otp.setEnabled(false);
								isotpverified=true;
								btnLogin.setText("Complete Your Transaction");
								tpuuptext.setText("Complete Your Transaction");


							}
							else{

								otp.setError("You have entered wrong otp.");
							}


						}
						else{
							otp.setError("Please provide valid otp");

						}
					}




				}
				else{
					if (mPin.getText().toString().length()>3) {
						new Verifympin().execute();

					}
					else{
						mPin.setError("Please provide valid mpin");

					}

				}

			}
		});
	}

	private void initview() {
		btnLogin=(Button)findViewById(R.id.btnLogin);
		btnbacktoloan=(Button)findViewById(R.id.btnbacktoloan);
		mPin=(EditText)findViewById(R.id.mPin);
		otp=(EditText)findViewById(R.id.otp);
		mpin_lable=(TextView)findViewById(R.id.mpin_lable);
		otp_lable=(TextView)findViewById(R.id.otp_lable);
		tpuuptext=(TextView)findViewById(R.id.tpuuptext);








	}

	public class Verifympin extends AsyncTask<String, Void, VerificationParser>{
		ProgressDialog pd;
		VerificationParser sent;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(MpinOtpVerificationTransfer.this, "Verifying",
					"Please Wait");
		}
		@Override
		protected VerificationParser doInBackground(String... arg0) {




			HttpVerifympin	verify=new HttpVerifympin();		

			sent = verify.httpverifympin(getApplicationContext(), userobject.getUserDetails().getUserMobile(), userobject.getSessionid(), mPin.getText().toString(), userobject.getUser_type());



			return sent;
		}
		@Override
		protected void onPostExecute(VerificationParser objectFromDoInBg) {

			pd.cancel();
			try {
				if (objectFromDoInBg!=null) {
					if (objectFromDoInBg.getResult_code().equalsIgnoreCase("200")) {
						yourotp=objectFromDoInBg.getOtp();
						ismpinverified=true;
						otp.setVisibility(View.VISIBLE);
						otp_lable.setVisibility(View.VISIBLE);
						mPin.setEnabled(false);
						btnLogin.setText("Verify OTP");
						tpuuptext.setText("Verify OTP");

					}
					else if(objectFromDoInBg.getResult_code().equalsIgnoreCase("109")){
						Toast.makeText(getApplicationContext(), "Wrong mPin", Toast.LENGTH_LONG).show();
						mPin.setError("Invalid mPin.");
					}

				}
				else{
					//Somthing went wrong check you internet connection try again
					Toast.makeText(getApplicationContext(), "Somthing went wrong check you internet connection try again", Toast.LENGTH_LONG).show();

				}

			} catch (Exception e) {


			}

		}

	}

	public class TransferMoney extends AsyncTask<String, Void, TransferResponseParser> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(MpinOtpVerificationTransfer.this, "Submitting your request ",
					"Please Wait");
		}

		@Override
		protected TransferResponseParser doInBackground(String... arg0) {

			if (tansferobject!=null&&userobject!=null) {
				HttpTransferMoney transfer=new HttpTransferMoney();
				TransferResponseParser response=	transfer.httpusertransfer(getApplicationContext(), userobject.getUserDetails().getUserMobile(), userobject.getSessionid(), tansferobject.getTomobile(), mPin.getText().toString(), tansferobject.getAmount(), tansferobject.getTransfertype(), tansferobject.getTransferaccount(), userobject.getUser_type());
				return response;
			}
			else{

				return null;

			}
		}

		@Override
		protected void onPostExecute(TransferResponseParser strFromDoInBg) {
			if (strFromDoInBg!=null) {
				if (strFromDoInBg.getResultCode().equalsIgnoreCase("200")) {
					Intent i=new Intent(getApplicationContext(),AndroidGridLayoutActivity.class);
					i.putExtra("user_obj", userobject);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(i);
					finish();
					Toast.makeText(getApplicationContext(), "Loan Application submitted sucessfully.", Toast.LENGTH_LONG).show();

				}
			}
			else{
				Toast.makeText(getApplicationContext(), "Transfer Cannot be submitted at this time.", Toast.LENGTH_LONG).show();

			}

			pd.cancel();


		}
	}

	public static void expand(final View v) {
		v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		final int targtetHeight = v.getMeasuredHeight();

		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);

		Animation a1=new Animation(){


		};
		Animation a = new Animation()
		{
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1
						? LayoutParams.WRAP_CONTENT
								: (int)(targtetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		a.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);
	}

	public static void collapse(final View v) {
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation()
		{
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if(interpolatedTime == 1){
					v.setVisibility(View.GONE);
				}else{
					v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		finish();
	}
}
