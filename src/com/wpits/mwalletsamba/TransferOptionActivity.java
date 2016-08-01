package com.wpits.mwalletsamba;

import com.wpits.parser.UserLoginparser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TransferOptionActivity extends Activity implements OnClickListener {
	Button buttonmpesatranfer,buttonmsakotransfer;  
	UserLoginparser userobject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer_option);
		buttonmpesatranfer=(Button)findViewById(R.id.buttonmpesatranfer);
		buttonmsakotransfer=(Button)findViewById(R.id.buttonmsakotransfer);
		buttonmpesatranfer.setOnClickListener(this);
		buttonmsakotransfer.setOnClickListener(this);
		Intent iuser = getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transfer_option, menu);
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
		Intent in = new Intent(getApplicationContext(), AndroidGridLayoutActivity.class);
		in.putExtra("user_obj", userobject);


		startActivity(in);
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buttonmpesatranfer:
			Intent i=new Intent(getApplicationContext(),PayToWithoutIdActivity.class);
			i.putExtra("user_obj", userobject);
			startActivity(i);
			finish();
			break;
		case R.id.buttonmsakotransfer:
			Intent in=new Intent(getApplicationContext(),PayToActivity.class);
			in.putExtra("user_obj", userobject);
			startActivity(in);
			finish();
			break;
		default:
			break;
		}

	}
}
