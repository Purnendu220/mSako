package com.wpits.mwalletsamba;


import java.util.ArrayList;

import com.wpits.data.TodoDAO;
import com.wpits.modelclass.SpinnerModel;
import com.wpits.parser.LoanAccountDetailParser;
import com.wpits.parser.LoanGauranterStatusList;
import com.wpits.parser.LoanTypeDetailParser;
import com.wpits.parser.LoanTypeList;
import com.wpits.parser.UserAccountDetailsParser;
import com.wpits.parser.UserLoginparser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Loanstatus extends Activity {
	UserLoginparser userobject;

	Button btnloan;
	TextView textViewamount,textviewloantype,loanstatus,loantenure,textViewtotal;
	LinearLayout gauranter1,gauranter2,gauranter3,gauranter4,gauranter5;

	TextView editTextgauranter1msisdn,editTextgauranter1reservevalue,editTextgauranter1status,
	editTextgauranter2msisdn,editTextgauranter2reservevalue,editTextgauranter2status,
	editTextgauranter3msisdn,editTextgauranter3reservevalue,editTextgauranter3status,
	editTextgauranter4msisdn,editTextgauranter4reservevalue,editTextgauranter4status,
	editTextgauranter5msisdn,editTextgauranter5reservevalue,editTextgauranter5status
	,ownreservevalue;
	LoanAccountDetailParser loanobject;
	SpinnerModel loantypeselctedmodel;
	TodoDAO dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan_status);
		textViewamount=(TextView)findViewById(R.id.textView1);
		textviewloantype=(TextView)findViewById(R.id.textviewloantype);
		loanstatus=(TextView)findViewById(R.id.loanstatus);
		loantenure=(TextView)findViewById(R.id.loantenure);
		textViewtotal=(TextView)findViewById(R.id.textViewtotal);
		gauranter5=(LinearLayout)findViewById(R.id.gauranter5);
		gauranter4=(LinearLayout)findViewById(R.id.gauranter4);
		gauranter3=(LinearLayout)findViewById(R.id.gauranter3);
		gauranter2=(LinearLayout)findViewById(R.id.gauranter2);
		gauranter1=(LinearLayout)findViewById(R.id.gauranter1);
		editTextgauranter1msisdn=(TextView)findViewById(R.id.editTextgauranter1msisdn);
		editTextgauranter1reservevalue=(TextView)findViewById(R.id.editTextgauranter1reservevalue);
		editTextgauranter1status=(TextView)findViewById(R.id.editTextgauranter1status);

		editTextgauranter2msisdn=(TextView)findViewById(R.id.editTextgauranter2msisdn);
		editTextgauranter2reservevalue=(TextView)findViewById(R.id.editTextgauranter2reservevalue);
		editTextgauranter2status=(TextView)findViewById(R.id.editTextgauranter2status);

		editTextgauranter3msisdn=(TextView)findViewById(R.id.editTextgauranter3msisdn);
		editTextgauranter3reservevalue=(TextView)findViewById(R.id.editTextgauranter3reservevalue);
		editTextgauranter3status=(TextView)findViewById(R.id.editTextgauranter3status);

		editTextgauranter4msisdn=(TextView)findViewById(R.id.editTextgauranter4msisdn);
		editTextgauranter4reservevalue=(TextView)findViewById(R.id.editTextgauranter4reservevalue);
		editTextgauranter4status=(TextView)findViewById(R.id.editTextgauranter4status);

		editTextgauranter5msisdn=(TextView)findViewById(R.id.editTextgauranter5msisdn);
		editTextgauranter5reservevalue=(TextView)findViewById(R.id.editTextgauranter5reservevalue);
		editTextgauranter5status=(TextView)findViewById(R.id.editTextgauranter5status);

		ownreservevalue=(TextView)findViewById(R.id.ownreservevalue);
		btnloan=(Button)findViewById(R.id.btnloan);
		dao=new TodoDAO(getApplicationContext());

		btnloan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(loanobject.getLoan_type_id());
				LoanTypeList loan=dao.getloantypedetail(loanobject.getLoan_type_id());
				loantypeselctedmodel=new SpinnerModel(loan.getLoan_type_name(), loan.getLoan_type_id());
				Intent i=new Intent(getApplicationContext(),Edit_loan_request.class);
				i.putExtra("user_obj", userobject);
				i.putExtra("loanobject", loanobject);
				i.putExtra("loantype", loantypeselctedmodel);
				startActivity(i);
				finish();

			}
		});


		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");

		}

		new GetLoanAccDetail().execute();
new GetLoanDetails().execute();

	}


	public class GetLoanAccDetail extends AsyncTask<String, Void, LoanAccountDetailParser> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Loanstatus.this, "Getting loan Account Detail",
					"Please Wait");
		}

		@Override
		protected LoanAccountDetailParser doInBackground(String... arg0) {SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 
		LoanAccountDetailParser parser = null;
		try {
			HttpGetLoanAccountStatus loanacc=new HttpGetLoanAccountStatus();
			parser=loanacc.httpuserloanaccount(getApplicationContext(), userobject.getUserDetails().getUserMobile(), userobject.getSessionid(), userobject.getUser_type());

		} catch (Exception e) {
			// TODO: handle exception
		}

		return parser;

		}

		@Override
		protected void onPostExecute(LoanAccountDetailParser strFromDoInBg) {
			pd.dismiss();
			if (strFromDoInBg!=null) {
				if (strFromDoInBg.getResult_code().equalsIgnoreCase("200")) {
					loanobject=strFromDoInBg;
					textViewamount.setText("Your requested loan amount is "+strFromDoInBg.getLoan_amount());
					textviewloantype.setText("Loan Type : "+strFromDoInBg.getLoan_type());
					loanstatus.setText("Loan Status : "+strFromDoInBg.getLoan_accountstatus());
					loantenure.setText("Loan Tenure : "+strFromDoInBg.getLoan_tenure());
					ownreservevalue.setText("Own Reserve amount: "+strFromDoInBg.getOwn_reserve());
					if (strFromDoInBg.getLoan_accountstatus().equalsIgnoreCase("APPROVED")) {
						btnloan.setVisibility(View.GONE);
					} else if(strFromDoInBg.getLoan_accountstatus().equalsIgnoreCase("DECLINED")) {
						btnloan.setVisibility(View.GONE);


					}

					double total=0;
					if (strFromDoInBg.getGauranterlist()!=null&&strFromDoInBg.getGauranterlist().size()>0) {
						for (int i = 0; i < strFromDoInBg.getGauranterlist().size(); i++) {
							LoanGauranterStatusList loangauranterstatuslist=strFromDoInBg.getGauranterlist().get(i);
							total=total+Double.parseDouble(loangauranterstatuslist.getGuarantorReserveValue());
							switch (i) {
							case 0:
								gauranter1.setVisibility(View.VISIBLE);
								editTextgauranter1msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
								editTextgauranter1reservevalue.setText(loangauranterstatuslist.getGuarantorReserveValue());
								editTextgauranter1status.setText(loangauranterstatuslist.getGauranterAcceptence());
								break;

							case 1:
								gauranter2.setVisibility(View.VISIBLE);
								editTextgauranter2msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
								editTextgauranter2reservevalue.setText(loangauranterstatuslist.getGuarantorReserveValue());
								editTextgauranter2status.setText(loangauranterstatuslist.getGauranterAcceptence());
								break;
							case 2:
								gauranter3.setVisibility(View.VISIBLE);
								editTextgauranter3msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
								editTextgauranter3reservevalue.setText(loangauranterstatuslist.getGuarantorReserveValue());
								editTextgauranter3status.setText(loangauranterstatuslist.getGauranterAcceptence());
								break;
							case 3:
								gauranter4.setVisibility(View.VISIBLE);
								editTextgauranter4msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
								editTextgauranter4reservevalue.setText(loangauranterstatuslist.getGuarantorReserveValue());
								editTextgauranter4status.setText(loangauranterstatuslist.getGauranterAcceptence());
								break;
							case 4:
								gauranter5.setVisibility(View.VISIBLE);
								editTextgauranter5msisdn.setText(loangauranterstatuslist.getGuarantorMsisdn());
								editTextgauranter5reservevalue.setText(loangauranterstatuslist.getGuarantorReserveValue());
								editTextgauranter5status.setText(loangauranterstatuslist.getGauranterAcceptence());
								break;

							default:
								break;
							}
						}
						try {
							total=total+Double.parseDouble(strFromDoInBg.getOwn_reserve());

						} catch (Exception e) {
							// TODO: handle exception
						}
						textViewtotal.setText("Total Gaurantee Amount "+total);

					}


				}
				else{
					textViewamount.setText(strFromDoInBg.getResult_status());

				}

			}
			else{
				textViewamount.setText("Unable to get loan request.");
			}



		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		Intent in = new Intent(Loanstatus.this, LoanOptionActivity.class);
		in.putExtra("user_obj", userobject);
		startActivity(in);


		finish();

	}
	public class GetLoanDetails extends AsyncTask<String, Void, LoanTypeDetailParser> {

		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Loanstatus.this, "Getting loan details.... ",
					"Please Wait");
		}

		@Override
		protected LoanTypeDetailParser doInBackground(String... arg0) {SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 

		HttpGetLoanTypeDetail loandetail=new HttpGetLoanTypeDetail();
		LoanTypeDetailParser loanobj=loandetail.httpusergetloandetail(getApplicationContext(), userobject.getSessionid());
		return loanobj;

		}

		@Override
		protected void onPostExecute(LoanTypeDetailParser strFromDoInBg) {
			pd.dismiss();
			if (strFromDoInBg!=null) {
				try {


					if (strFromDoInBg.getResult_code().equalsIgnoreCase("200")) {

						ArrayList<LoanTypeList> list=strFromDoInBg.getLoan_typelist();
						if (list.size()>0) {
							//ArrayAdapter<String> adapter = new ArrayAdapter<String>(loan_activity.this, android.R.layout.simple_spinner_item,list);
							dao.insertloantypedetails(strFromDoInBg);


						}
						else{

							showAlert("Unable to get loan deatail please try after some time", "loan");
						}
					} 
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}





		}
	}
	public void showAlert(String message,final String type)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(Loanstatus.this);

		//Setting Dialog Title
		alertDialog.setTitle("Alert");
		alertDialog.setCancelable(false);
		//Setting Dialog Message
		alertDialog.setMessage(message);
		//On Pressing Setting button
		alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() 
		{   
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				runOnUiThread( new Runnable() {
					public void run() {
						Intent i=new Intent(getApplicationContext(),LoanTypeOptions.class);
						i.putExtra("user_obj", userobject);
						startActivity(i);
						finish();

					}
				});


			}
		});


		alertDialog.show();
	}
}
