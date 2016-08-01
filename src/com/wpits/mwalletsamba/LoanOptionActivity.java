package com.wpits.mwalletsamba;

import com.wpits.parser.UserLoginparser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoanOptionActivity extends Activity implements OnClickListener {
	Button buttonapplyloan,buttonloanstatus;
	UserLoginparser userobject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan_option);
		buttonloanstatus=(Button)findViewById(R.id.buttonloanstatus);
		buttonapplyloan=(Button)findViewById(R.id.buttonapplyloan);
		buttonloanstatus.setOnClickListener(this);
		buttonapplyloan.setOnClickListener(this);
		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
		}
		if (userobject.getUserDetails().getIsloanrequested().equalsIgnoreCase("Y")) {
			buttonloanstatus.setVisibility(View.VISIBLE);
		} else {
			buttonloanstatus.setVisibility(View.VISIBLE);

		}


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loan_option, menu);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buttonapplyloan:
			if (userobject.getUserDetails().getIsloanrequested().equalsIgnoreCase("Y")) {
			showAlert1("You Already Applied for a loan.You can check your loan status and edit your loan request.", "");
		} else {
				Intent i=new Intent(getApplicationContext(),LoanTypeOptions.class);
				i.putExtra("user_obj", userobject);
				startActivity(i);
				finish();

			}
			break;

		case R.id.buttonloanstatus:
			Intent i1=new Intent(getApplicationContext(),Loanstatus.class);
			i1.putExtra("user_obj", userobject);
			startActivity(i1);
			finish();
			break;

		default:
			break;
		}

	}

	public void showAlert1(String message,final String type)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoanOptionActivity.this);

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



					}
				});


			}
		});

		alertDialog.setNegativeButton("Loan Status", new DialogInterface.OnClickListener() 
		{   
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				runOnUiThread( new Runnable() {
					public void run() {
						Intent in = new Intent(LoanOptionActivity.this, Loanstatus.class);
						in.putExtra("user_obj", userobject);
						startActivity(in);
						finish();


					}
				});


			}
		});



		alertDialog.show();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(), AndroidGridLayoutActivity.class);
		in.putExtra("user_obj", userobject);


		startActivity(in);
		finish();
	}
}
