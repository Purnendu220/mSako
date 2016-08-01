package com.wpits.mwalletsamba;

import com.wpits.parser.LoanApplicationRequestParser;
import com.wpits.parser.LoanRequstResponse;
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

public class MpinOtpVerificationLoan extends Activity {
	Button btnLogin,btnbacktoloan;
	EditText mPin,otp;
	TextView mpin_lable,otp_lable;
	UserLoginparser userobject;
	boolean ismpinverified=false,isotpverified=false;
	String yourotp;
	LoanApplicationObject loan_obj;
	TextView tpuuptext,textViewaccountstatusmsg,textownreserveamount;
	LinearLayout linearlayoutloanchild,linearLayout1,linearLayout2;
	LinearLayout llgauranter,llgauranter1,llgauranter2,
	llgauranter3,llgauranter4,llgauranter5;
	TextView textloangaurantermsisdn,textloanaccstatus,
	textloangaurantermsisdn1,textloanaccstatus1,
	textloangaurantermsisdn2,textloanaccstatus2, 
	textloangaurantermsisdn3,textloanaccstatus3,
	textloangaurantermsisdn4,textloanaccstatus4,
	textloangaurantermsisdn5,textloanaccstatus5
	;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mpin_otp_verification_loan);
		initview();
		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
			loan_obj=(LoanApplicationObject)iuser.getExtras().getSerializable("loan_obj");
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
						new SubmitLoanApplication().execute();				
					}
					else{
						if (otp.getText().toString().length()>3) {
							if (otp.getText().toString().equalsIgnoreCase(yourotp)) {
								otp.setEnabled(false);
								isotpverified=true;
								btnLogin.setText("Submit Loan Application");
								tpuuptext.setText("Submit Loan Application");


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
		textViewaccountstatusmsg=(TextView)findViewById(R.id.textViewaccountstatusmsg);
		linearLayout1=(LinearLayout)findViewById(R.id.linearLayout1);
		linearLayout2=(LinearLayout)findViewById(R.id.linearLayout2);

		linearlayoutloanchild=(LinearLayout)findViewById(R.id.linearlayoutloanchild);
		llgauranter=(LinearLayout)findViewById(R.id.llgauranter);
		llgauranter1=(LinearLayout)findViewById(R.id.llgauranter1);
		llgauranter2=(LinearLayout)findViewById(R.id.llgauranter2);
		llgauranter3=(LinearLayout)findViewById(R.id.llgauranter3);
		llgauranter4=(LinearLayout)findViewById(R.id.llgauranter4);
		llgauranter5=(LinearLayout)findViewById(R.id.llgauranter5);

		textownreserveamount=(TextView)findViewById(R.id.textownreserveamount);


		textloangaurantermsisdn=(TextView)findViewById(R.id.textloangaurantermsisdn);
		textloanaccstatus=(TextView)findViewById(R.id.textloanaccstatus);

		textloangaurantermsisdn1=(TextView)findViewById(R.id.textloangaurantermsisdn1);
		textloanaccstatus1=(TextView)findViewById(R.id.textloanaccstatus1);

		textloangaurantermsisdn2=(TextView)findViewById(R.id.textloangaurantermsisdn2);
		textloanaccstatus2=(TextView)findViewById(R.id.textloanaccstatus2);

		textloangaurantermsisdn3=(TextView)findViewById(R.id.textloangaurantermsisdn3);
		textloanaccstatus3=(TextView)findViewById(R.id.textloanaccstatus3);

		textloangaurantermsisdn4=(TextView)findViewById(R.id.textloangaurantermsisdn4);
		textloanaccstatus4=(TextView)findViewById(R.id.textloanaccstatus4);

		textloangaurantermsisdn5=(TextView)findViewById(R.id.textloangaurantermsisdn5);
		textloanaccstatus5=(TextView)findViewById(R.id.textloanaccstatus5);








	}

	public class Verifympin extends AsyncTask<String, Void, VerificationParser>{
		ProgressDialog pd;
		VerificationParser sent;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(MpinOtpVerificationLoan.this, "Verifying",
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

	public class SubmitLoanApplication extends AsyncTask<String, Void, LoanApplicationRequestParser> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(MpinOtpVerificationLoan.this, "Submitting your request ",
					"Please Wait");
		}

		@Override
		protected LoanApplicationRequestParser doInBackground(String... arg0) {
			//HttpgetMiniStatment ministatement=new HttpgetMiniStatment();
			//ministatementlist=	ministatement.httpuserministatment(getApplicationContext(), mobile_no);
			if (loan_obj!=null&&userobject!=null) {
				HttpRequestLoan requestloan=new HttpRequestLoan();
				LoanApplicationRequestParser response=requestloan.requstloan(loan_obj.getMobileno(), loan_obj.getLoanamount(), loan_obj.getMapgauranter(), loan_obj.getLoantype(), loan_obj.getLoantenure(), userobject.getSessionid(), userobject.getUser_type(),loan_obj.getOwnreserveamount(), getApplicationContext());
				return response;
			}
			else{

				return null;

			}
		}

		@Override
		protected void onPostExecute(LoanApplicationRequestParser strFromDoInBg) {
			if (strFromDoInBg!=null) {
				if (strFromDoInBg.getResult_code().equalsIgnoreCase("200")) {
					Intent i=new Intent(getApplicationContext(),AndroidGridLayoutActivity.class);
					i.putExtra("user_obj", userobject);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(i);
					finish();
					Toast.makeText(getApplicationContext(), "Loan Application submitted sucessfully.", Toast.LENGTH_LONG).show();

				}
				else if(strFromDoInBg.getResult_code().equalsIgnoreCase("1111"))//not gound
				{
					collapse(linearLayout1); 
					expand(linearLayout2);
					textownreserveamount.setText("Own Reserve amount"+loan_obj.getOwnreserveamount());
					if (strFromDoInBg.getListfaultygauranter()!=null&&strFromDoInBg.getListfaultygauranter().size()>0) {
						llgauranter.setVisibility(View.VISIBLE);
						for (int i = 0; i < strFromDoInBg.getListfaultygauranter().size(); i++) {
							switch (i) {
							case 0:
								llgauranter1.setVisibility(View.VISIBLE);
								textloangaurantermsisdn1.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus1.setText("Gauranter is not a subscriber");
								break;
							case 1:
								llgauranter2.setVisibility(View.VISIBLE);
								textloangaurantermsisdn2.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus2.setText("Gauranter is not a subscriber");
								break;
							case 2:
								llgauranter3.setVisibility(View.VISIBLE);
								textloangaurantermsisdn3.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus3.setText("Gauranter is not a subscriber");
								break;
							case 3:
								llgauranter4.setVisibility(View.VISIBLE);
								textloangaurantermsisdn4.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus4.setText("Gauranter is not a subscriber");
								break;
							case 4:
								llgauranter5.setVisibility(View.VISIBLE);
								textloangaurantermsisdn5.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus5.setText("Gauranter is not a subscriber");
								break;

							default:
								break;
							}
							expand(linearlayoutloanchild);
						}

					}

				}

				else if(strFromDoInBg.getResult_code().equalsIgnoreCase("2222"))//low balance
				{
					collapse(linearLayout1); 
					expand(linearLayout2);
					textownreserveamount.setText("Own Reserve amount"+loan_obj.getOwnreserveamount());

					if (strFromDoInBg.getListfaultygauranter()!=null&&strFromDoInBg.getListfaultygauranter().size()>0) {
						llgauranter.setVisibility(View.VISIBLE);
						for (int i = 0; i < strFromDoInBg.getListfaultygauranter().size(); i++) {
							switch (i) {
							case 0:
								llgauranter1.setVisibility(View.VISIBLE);
								textloangaurantermsisdn1.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus1.setText("Gauranter acc balance is low");
								break;
							case 1:
								llgauranter2.setVisibility(View.VISIBLE);
								textloangaurantermsisdn2.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus2.setText("Gauranter acc balance is low");
								break;
							case 2:
								llgauranter3.setVisibility(View.VISIBLE);
								textloangaurantermsisdn3.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus3.setText("Gauranter acc balance is low");
								break;
							case 3:
								llgauranter4.setVisibility(View.VISIBLE);
								textloangaurantermsisdn4.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus4.setText("Gauranter acc balance is low");
								break;
							case 4:
								llgauranter5.setVisibility(View.VISIBLE);
								textloangaurantermsisdn5.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus5.setText("Gauranter acc balance is low");
								break;

							default:
								break;
							}

						}
						expand(linearlayoutloanchild);

					}



				}

				else if(strFromDoInBg.getResult_code().equalsIgnoreCase("5555"))//wallet status is not ok

				{
					collapse(linearLayout1); 
					expand(linearLayout2);
					textownreserveamount.setText("Own Reserve amount"+loan_obj.getOwnreserveamount());


					if (strFromDoInBg.getListfaultygauranter()!=null&&strFromDoInBg.getListfaultygauranter().size()>0) {
						llgauranter.setVisibility(View.VISIBLE);
						for (int i = 0; i < strFromDoInBg.getListfaultygauranter().size(); i++) {
							switch (i) {
							case 0:
								llgauranter1.setVisibility(View.VISIBLE);
								textloangaurantermsisdn1.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus1.setText("Gauranter wallet status is not ok");
								break;
							case 1:
								llgauranter2.setVisibility(View.VISIBLE);
								textloangaurantermsisdn2.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus2.setText("Gauranter wallet status is not ok");
								break;
							case 2:
								llgauranter3.setVisibility(View.VISIBLE);
								textloangaurantermsisdn3.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus3.setText("Gauranter wallet status is not ok");
								break;
							case 3:
								llgauranter4.setVisibility(View.VISIBLE);
								textloangaurantermsisdn4.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus4.setText("Gauranter wallet status is not ok");
								break;
							case 4:
								llgauranter5.setVisibility(View.VISIBLE);
								textloangaurantermsisdn5.setText(strFromDoInBg.getListfaultygauranter().get(i).getGaurantermsisdn());
								textloanaccstatus5.setText("Gauranter wallet status is not ok");
								break;

							default:
								break;
							}

						}
						expand(linearlayoutloanchild);

					}





				}

				else{
					collapse(linearLayout1); 
					expand(linearLayout2);
					textownreserveamount.setVisibility(View.VISIBLE);
					textownreserveamount.setText(strFromDoInBg.getResult_status());
					//textownreserveamount.setText(loan_obj.getOwnreserveamount());

				}


			}
			else{
				Toast.makeText(getApplicationContext(), "Loan Application cannot be submited at this time.", Toast.LENGTH_LONG).show();

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
