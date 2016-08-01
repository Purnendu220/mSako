package com.wpits.mwalletsamba;

import org.w3c.dom.Text;

import com.wpits.parser.LoanAccountDetailParser;
import com.wpits.parser.LoanGauranterStatusList;
import com.wpits.parser.UserAccountDetailsParser;
import com.wpits.parser.UserLoginparser;
import com.wpits.parser.UserLogoutParser;
import com.wpits.utils.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class AccountDetail extends Activity {
	UserLoginparser userobject;
	UserAccountDetailsParser accobject;
	ImageButton update_profile;
	TextView textName,textBalance;

	TextView textwalletbal,textwalletbalvalue;
	LinearLayout linearlayoutwallet;

	TextView textloanbal,textloanbalvalue;
	LinearLayout linearlayoutloan,linearlayoutloanchild;

	TextView textreservebal,textreservebalvalue;
	LinearLayout linearlayoutreserveamount;
	boolean iscollapsed=false;
	TextView textloanaccstatus,textloanaccno,textloanaccrecerve;
	TextView textViewacctype;

	LinearLayout llgauranter,llgauranter1,llgauranter2,llgauranter3,llgauranter4,llgauranter5;
	TextView textloangauranter1,textloangaurantermsisdn1,textloanaccstatus1,
	textloangauranter2,textloangaurantermsisdn2,textloanaccstatus2,
	textloangauranter3,textloangaurantermsisdn3,textloanaccstatus3,
	textloangauranter4,textloangaurantermsisdn4,textloanaccstatus4,
	textloangauranter5,textloangaurantermsisdn5,textloanaccstatus5;
	TextView textloanaccadminfee,textloanaccbalance,textloanacclastemi;
	Button buttonmoreacc,buttonmoreaccloan,buttonmoreaccreserve;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_detail);
		update_profile=(ImageButton)findViewById(R.id.update_profile);
		textName=(TextView)findViewById(R.id.textName);
		textBalance=(TextView)findViewById(R.id.textBalance);

		textViewacctype=(TextView)findViewById(R.id.textViewacctype);

		linearlayoutwallet=(LinearLayout)findViewById(R.id.linearlayoutwallet);
		textwalletbal=(TextView)findViewById(R.id.textwalletbal);
		textwalletbalvalue=(TextView)findViewById(R.id.textwalletbalvalue);

		linearlayoutloan=(LinearLayout)findViewById(R.id.linearlayoutloan);
		linearlayoutloanchild=(LinearLayout)findViewById(R.id.linearlayoutloanchild);
		textloanbal=(TextView)findViewById(R.id.textloanbal);
		textloanbalvalue=(TextView)findViewById(R.id.textloanbalvalue);


		linearlayoutreserveamount=(LinearLayout)findViewById(R.id.linearlayoutreserveamount);
		textreservebal=(TextView)findViewById(R.id.textreservebal);
		textreservebalvalue=(TextView)findViewById(R.id.textreservebalvalue);

		textloanaccstatus=(TextView)findViewById(R.id.textloanaccstatus);
		textloanaccno=(TextView)findViewById(R.id.textloanaccno);
		textloanaccrecerve=(TextView)findViewById(R.id.textloanaccrecerve);
		llgauranter=(LinearLayout)findViewById(R.id.llgauranter);
		llgauranter1=(LinearLayout)findViewById(R.id.llgauranter1);
		llgauranter2=(LinearLayout)findViewById(R.id.llgauranter2);
		llgauranter3=(LinearLayout)findViewById(R.id.llgauranter3);
		llgauranter4=(LinearLayout)findViewById(R.id.llgauranter4);
		llgauranter5=(LinearLayout)findViewById(R.id.llgauranter5);

		textloangauranter1=(TextView)findViewById(R.id.textloangauranter1);
		textloangaurantermsisdn1=(TextView)findViewById(R.id.textloangaurantermsisdn1);
		textloanaccstatus1=(TextView)findViewById(R.id.textloanaccstatus1);

		textloangauranter2=(TextView)findViewById(R.id.textloangauranter2);
		textloangaurantermsisdn2=(TextView)findViewById(R.id.textloangaurantermsisdn2);
		textloanaccstatus2=(TextView)findViewById(R.id.textloanaccstatus2);

		textloangauranter3=(TextView)findViewById(R.id.textloangauranter3);
		textloangaurantermsisdn3=(TextView)findViewById(R.id.textloangaurantermsisdn3);
		textloanaccstatus3=(TextView)findViewById(R.id.textloanaccstatus3);

		textloangauranter4=(TextView)findViewById(R.id.textloangauranter4);
		textloangaurantermsisdn4=(TextView)findViewById(R.id.textloangaurantermsisdn4);
		textloanaccstatus4=(TextView)findViewById(R.id.textloanaccstatus4);

		textloangauranter5=(TextView)findViewById(R.id.textloangauranter5);
		textloangaurantermsisdn5=(TextView)findViewById(R.id.textloangaurantermsisdn5);
		textloanaccstatus5=(TextView)findViewById(R.id.textloanaccstatus5);

		textloanaccadminfee=(TextView)findViewById(R.id.textloanaccadminfee);
		textloanaccbalance=(TextView)findViewById(R.id.textloanaccbalance);
		textloanacclastemi=(TextView)findViewById(R.id.textloanacclastemi);

		buttonmoreacc=(Button)findViewById(R.id.buttonmoreacc);
		buttonmoreaccloan=(Button)findViewById(R.id.buttonmoreaccloan);
		buttonmoreaccreserve=(Button)findViewById(R.id.buttonmoreaccreserve);

		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
			accobject=(UserAccountDetailsParser)iuser.getExtras().getSerializable("acc_obj");

		}

		/*------------------------------------------------------------------------------------------------------------------
		 * if (userobject.getUserDetails().getUserImage()!=null&&userobject.getUserDetails().getUserImage().length()>0) {
			try {
				update_profile.setImageBitmap(Utility.getbitmap(userobject.getUserDetails().getUserImage()));

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	=------------------------------------------------------------------------------------------------------------------------------------	*/
		if (userobject!=null&&userobject.getUserDetails()!=null) {
			textName.setText(userobject.getUserDetails().getUserName());
			textBalance.setText(userobject.getUserDetails().getUserMobile());


		}
		if (accobject!=null) {
			textwalletbalvalue.setText(accobject.getWallet_ammount()+" KES");
			if (accobject.getHave_loan_account().equalsIgnoreCase("Y")) {
				linearlayoutloan.setVisibility(View.VISIBLE);
				textloanbalvalue.setText(accobject.getLoan_ammount()+" KES");
				textViewacctype.setText("Loan Type:"+accobject.getLoan_type());
			} 
			else {
				linearlayoutloan.setVisibility(View.GONE);
			}

			if (accobject.getReserve_amount()!=null) {
				try {
					double reserve=Double.parseDouble(accobject.getReserve_amount());
					if (reserve>0) {
						linearlayoutreserveamount.setVisibility(View.VISIBLE);
						textreservebalvalue.setText(""+reserve);
						//reserve layout
					}
					else{
						linearlayoutreserveamount.setVisibility(View.GONE);


					}
				} catch (Exception e) {
					// TODO: handle exception
					linearlayoutreserveamount.setVisibility(View.GONE);

				}

			} 
			else {
				linearlayoutreserveamount.setVisibility(View.GONE);
			}
		}


		buttonmoreaccloan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				// TODO Auto-generated method stub
				if (iscollapsed) {
					//expand(ll);
					collapse(linearlayoutloanchild);
					iscollapsed=false;
					buttonmoreaccloan.setText("More");
					
				} 

				else {
					new GetLoanAccDetail().execute();

					iscollapsed=true;
				}


			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class GetLoanAccDetail extends AsyncTask<String, Void, LoanAccountDetailParser> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(AccountDetail.this, "Getting loan Account Detail",
					"Please Wait");
		}

		@Override
		protected LoanAccountDetailParser doInBackground(String... arg0) {SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 

		LoanAccountDetailParser parser;
		try {
			HttpGetLoanAccountDetails loanacc=new HttpGetLoanAccountDetails();
			parser=loanacc.httpuserloanaccount(getApplicationContext(), userobject.getUserDetails().getUserMobile(), userobject.getSessionid(), accobject.getLoan_acc_no(), userobject.getUser_type());

			return parser;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}


		}

		@Override
		protected void onPostExecute(LoanAccountDetailParser strFromDoInBg) {
			pd.dismiss();
			if (strFromDoInBg!=null) {
				if (strFromDoInBg.getResult_code().equalsIgnoreCase("200")) {
					expand(linearlayoutloanchild);
					buttonmoreaccloan.setText("Less");

					textloanaccstatus.setText("Status:"+strFromDoInBg.getLoan_accountstatus());

					textloanaccno.setText("Account No:"+strFromDoInBg.getLoan_acc_no());
					textloanaccrecerve.setText("Own Reserve:"+strFromDoInBg.getOwn_reserve());
					if (strFromDoInBg.getLoan_accountstatus().equalsIgnoreCase("APPROVED")) {
						textloanaccadminfee.setVisibility(View.VISIBLE);
						textloanaccbalance.setVisibility(View.VISIBLE);
						textloanacclastemi.setVisibility(View.VISIBLE);


					} 
					if (strFromDoInBg.getLoan_type().equalsIgnoreCase("Emergency")) {
						llgauranter.setVisibility(View.GONE);
					}
					for (int i = 0; i <strFromDoInBg.getGauranterlist().size(); i++) {
						LoanGauranterStatusList parse=strFromDoInBg.getGauranterlist().get(i);
						switch (i) {
						case 0:
							llgauranter1.setVisibility(View.VISIBLE);
							textloangauranter1.setText(parse.getGuarantorMsisdn());
							textloangaurantermsisdn1.setText(parse.getGuarantorReserveValue()+" KES");
							textloanaccstatus1.setText(parse.getGauranterAcceptence());
							break;
						case 1:
							llgauranter2.setVisibility(View.VISIBLE);
							textloangauranter2.setText(parse.getGuarantorMsisdn());
							textloangaurantermsisdn2.setText(parse.getGuarantorReserveValue()+" KES");
							textloanaccstatus2.setText(parse.getGauranterAcceptence());
							break;
						case 2:
							llgauranter3.setVisibility(View.VISIBLE);
							textloangauranter3.setText(parse.getGuarantorMsisdn());
							textloangaurantermsisdn3.setText(parse.getGuarantorReserveValue()+" KES");
							textloanaccstatus3.setText(parse.getGauranterAcceptence());
							break;
						case 3:
							llgauranter4.setVisibility(View.VISIBLE);
							textloangauranter4.setText(parse.getGuarantorMsisdn());
							textloangaurantermsisdn4.setText(parse.getGuarantorReserveValue()+" KES");
							textloanaccstatus4.setText(parse.getGauranterAcceptence());
							break;
						case 4:
							llgauranter5.setVisibility(View.VISIBLE);
							textloangauranter5.setText(parse.getGuarantorMsisdn());
							textloangaurantermsisdn5.setText(parse.getGuarantorReserveValue()+" KES");
							textloanaccstatus5.setText(parse.getGauranterAcceptence());
							break;
						default:
							break;
						}
					}




				}

			}
			else{
				//linearlayoutloanchild
			}



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

		Intent i=new Intent(getApplicationContext(),AndroidGridLayoutActivity.class);
		i.putExtra("user_obj", userobject);
		startActivity(i);
		finish();
	}
}
