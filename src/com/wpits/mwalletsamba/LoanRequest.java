package com.wpits.mwalletsamba;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoanRequest extends Activity {

	Button btnloan;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loan_request);
		btnloan=(Button)findViewById(R.id.btnloanaccept);
		btnloan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();


		finish();

	}

}
