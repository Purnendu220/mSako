package com.wpits.mwalletsamba;

import java.util.ArrayList;

import com.wpits.data.TodoDAO;
import com.wpits.modelclass.SpinnerModel;
import com.wpits.parser.LoanTypeDetailParser;
import com.wpits.parser.LoanTypeList;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LoanTypeOptions extends Activity {
	ListView listView1;
	UserLoginparser userobject;
	TodoDAO dao;
	public ArrayList<SpinnerModel> CustomListViewValuesArr = new ArrayList<SpinnerModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan_type_options);
		listView1=(ListView)findViewById(R.id.listView1);
		dao=new TodoDAO(getApplicationContext());
		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
		}

		try {
			new GetLoanDetails().execute();

		} catch (Exception e) {
			// TODO: handle exception
			showAlert("Unable to get loan details please try after some time.", "loan");
		}

		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i=new Intent(getApplicationContext(),loan_activity.class);
				i.putExtra("user_obj", userobject);
				i.putExtra("loantype", CustomListViewValuesArr.get(arg2));
				startActivity(i);


			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loan_type_options, menu);
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

	public class GetLoanDetails extends AsyncTask<String, Void, LoanTypeDetailParser> {

		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(LoanTypeOptions.this, "Getting loan details.... ",
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
							for (int i = 0; i < list.size(); i++) {
								CustomListViewValuesArr.add(new SpinnerModel(list.get(i).getLoan_type_name(),list.get(i).getLoan_type_id()));

							}
							LoanTypeAdapter	adapter = new LoanTypeAdapter(getApplicationContext(),CustomListViewValuesArr);
							listView1.setAdapter(adapter);

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
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoanTypeOptions.this);

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

						if (type.equalsIgnoreCase("me")) {

						}

					}
				});


			}
		});


		alertDialog.show();
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
