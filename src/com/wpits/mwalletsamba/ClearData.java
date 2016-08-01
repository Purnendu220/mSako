package com.wpits.mwalletsamba;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ClearData extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clear_data);
	}
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	Intent in = new Intent(ClearData.this, LoginActivity.class);
	startActivity(in);
	

	finish();

}

}
