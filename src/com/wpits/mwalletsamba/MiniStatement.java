package com.wpits.mwalletsamba;


import java.util.ArrayList;







import com.wpits.parser.UserLoginparser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class MiniStatement extends Activity {
	MiniStatementAdapter adapter;
	ArrayList<ModelMinistatment> ministatementlist ;
	String mobile_no;
	ListView lv;
	UserLoginparser userobject;
	TextView textViewacc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mini_statement);
		System.out.println("HEllo iam called ");
		lv=(ListView)findViewById(R.id.listView1);
		textViewacc=(TextView)findViewById(R.id.textViewacc);
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 
		mobile_no =pref.getString("mobile_no", "");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();      
		StrictMode.setThreadPolicy(policy);
		ministatementlist = new ArrayList<ModelMinistatment>();
		ministatementlist.add(getministatement("2000", "Dr", "18-03-2016 18:35", "m SAKO", "2000 has been debited from m sako account", "","81938293923","8130835223","8130835223","Brijesh","Purnendu"));
		ministatementlist.add(getministatement("1599", "Cr", "20-03-2016 10:17", "m SAKO", "1599 has been added to your m sako wallet", "","8130835223","81938293923","8130835223","Purnendu","Brijesh"));
		ministatementlist.add(getministatement("1000", "Cr", "02-04-2016 9:40", "m SAKO", "1000 has been addded to msako wallet", "","8783782734","8130835223","8130835223","Purnendu","Ankit"));
		ministatementlist.add(getministatement("5000", "Dr", "11-04-2016 11:11", "m SAKO", "5000 has been debited from m sako account", "","81938293923","8130835223","8130835223","Abhay","Purnendu"));
		ministatementlist.add(getministatement("1299", "Dr", "12-04-2016 11:22", "m SAKO", "1299 has been debited from m sako account", "","81938293923","8130835223","8130835223","Brijesh","Purnendu"));
		ministatementlist.add(getministatement("799", "Dr", "17-04-2016 09:32", "m SAKO", "799 has been debited from m sako account", "","81938293923","8130835223","8130835223","Rohit","Purnendu"));

		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");

		}
		if(ministatementlist.size()>0){
			adapter = new MiniStatementAdapter(getApplicationContext(), ministatementlist);
			lv.setAdapter(adapter);
		}
		//new UserStatment().execute();
		textViewacc.setText("Current Wallet Account Balance "+userobject.getUserDetails().getUserWalletbalance()+" KES");

	}
	public class UserStatment extends AsyncTask<String, String, String> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(MiniStatement.this, "Loading MiniStatment ",
					"Please Wait");
		}

		@Override
		protected String doInBackground(String... arg0) {
			HttpgetMiniStatment ministatement=new HttpgetMiniStatment();
			ministatementlist=	ministatement.httpuserministatment(getApplicationContext(), mobile_no);
			return null;
		}

		@Override
		protected void onPostExecute(String strFromDoInBg) {
			// TODO Auto-generated method stub
			if(ministatementlist.size()>0){
				adapter = new MiniStatementAdapter(getApplicationContext(), ministatementlist);
				lv.setAdapter(adapter);
			}
			pd.cancel();


		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(), AndroidGridLayoutActivity.class);
		in.putExtra("user_obj", userobject);
		startActivity(in);
		finish();
	}

	private String getmobileno() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		return pref.getString("MobileNo", "");
	}
	private ModelMinistatment getministatement(String amount,String trans_type,String created_date,String type,String remark,String operater,String dest_mob,String src_mob,String mob,String destname,String srcname)
	{
		return new ModelMinistatment(amount, trans_type, created_date, type, remark, operater, dest_mob, src_mob, mob, destname, srcname);


	}
}
