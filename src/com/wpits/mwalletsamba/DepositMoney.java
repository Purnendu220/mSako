package com.wpits.mwalletsamba;

import com.wpits.parser.UserLoginparser;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DepositMoney extends Activity {
	TextView tv;
	UserLoginparser userobject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deposit_money);
		tv=(TextView)findViewById(R.id.textView2);
		Intent iuser = getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
		}
		 Resources r = getResources();
		 String words = r.getString(R.string.deposite_money, userobject.getUserDetails().getUserName());
		 tv.setText(words);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deposit_money, menu);
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
	public void onBackPressed() {
		// TODO Auto-generated method stub

		Intent i=new Intent(getApplicationContext(),AndroidGridLayoutActivity.class);
		i.putExtra("user_obj", userobject);
		startActivity(i);
		finish();
	}
}
