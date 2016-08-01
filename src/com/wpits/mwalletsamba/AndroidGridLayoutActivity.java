package com.wpits.mwalletsamba;
import java.io.IOException;
import com.squareup.picasso.Picasso;
import com.wpits.mwalletsamba.Edit_loan_request.SubmitLoanApplication;
import com.wpits.parser.UserLoginparser;
import com.wpits.parser.UserLogoutParser;
import com.wpits.utils.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidGridLayoutActivity extends Activity {
	TextView txtbalance,txtname;
	ImageButton update_profile;
	Button logout;
	UserLoginparser userobject;
	Animation animSideDown;
	LinearLayout linearlayoutacc;
	Button buttonaccountdetails;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_layout);
		update_profile=(ImageButton)findViewById(R.id.update_profile);
		logout=(Button)findViewById(R.id.imagebuttonlogout);
		linearlayoutacc=(LinearLayout)findViewById(R.id.linearlayoutacc);
		buttonaccountdetails=(Button)findViewById(R.id.buttonaccountdetails);
		animSideDown = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_down);
		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
		}
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//	System.exit(0);

				new Logout().execute();

			}
		});

		/* Intent in = getIntent();
        Bundle b = in.getExtras();
        final String mobile_no = b.getString("mobile_no");
        final String mPin = b.getString("mPin");*/
		/*--------------------------------------------------------------------------------------------------------------------------------
	 	if (userobject.getUserDetails().getUserImage()!=null&&userobject.getUserDetails().getUserImage().length()>0) {
			try {
				Bitmap bm=Utility.getbitmap(userobject.getUserDetails().getUserImage());
				int size=Utility.dp2px(getApplicationContext(), 75);
				update_profile.setImageBitmap(Utility.getResizedBitmap(bm, size, size));

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}

		update_profile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(AndroidGridLayoutActivity.this, Update_Profile.class);
				in.putExtra("user_obj", userobject);
				startActivity(in);	
			}
		});
----------------------------------------------------------------------------------------------------------------------------------------------*/

		txtname=(TextView)findViewById(R.id.textName);
		txtbalance=(TextView)findViewById(R.id.textBalance);

		/*		SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 
		final String mobile_no =pref.getString("mobile_no", "");
		final	String mPin =pref.getString("mPin", "");
		String name =pref.getString("name", "");
		final String type =pref.getString("type", "");
		final String image =pref.getString("imageurl", "");*/
		System.out.println("LOG:"+userobject.getUserDetails().getUserName());
		txtname.setText("Good to see you");
		txtbalance.setText(userobject.getUserDetails().getUserName());


		/*Picasso.with(getApplicationContext())
			  .load(image)
			  .centerCrop()
			  .into(update_profile);*/
		/* Picasso.with(this)
		        .load(image)
		        .placeholder(R.drawable.update_profile)
		        .error(R.drawable.update_profile)
		        .into(update_profile);*/
		GridView gridView = (GridView) findViewById(R.id.grid_view);

		// Instance of ImageAdapter Class
		Log.i("LOAN STATUS", String.valueOf(getisloanrequested()));
		if(userobject!=null){
			gridView.setAdapter(new ImageAdapter1(this));

		}

		/**
		 * On Click event for Single Gridview Item
		 * */
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				if(position==0){

					Intent in = new Intent(AndroidGridLayoutActivity.this, DepositMoney.class);
					in.putExtra("user_obj", userobject);

					startActivity(in);
					finish();
				}else if(position==1){

					Intent in = new Intent(AndroidGridLayoutActivity.this, TransferOptionActivity.class);
					in.putExtra("user_obj", userobject);

					startActivity(in);
					finish();
				}else if(position==2){

					Intent in = new Intent(AndroidGridLayoutActivity.this, LoanOptionActivity.class);
					in.putExtra("user_obj", userobject);
					startActivity(in);
					finish();

				}
				else if(position==3){

					Intent in = new Intent(AndroidGridLayoutActivity.this, MiniStatement.class);
					in.putExtra("user_obj", userobject);

					startActivity(in);	
					finish();

				}
				else if(position==4){

					Intent in = new Intent(AndroidGridLayoutActivity.this, Notificationmpin.class);
					in.putExtra("user_obj", userobject);

					startActivity(in);
					finish();

				}
				else if(position==5)
				{
					Intent in = new Intent(AndroidGridLayoutActivity.this, PinChange.class);
					in.putExtra("user_obj", userobject);

					startActivity(in);
					finish();

				}
				else if(position==6){

					//new UserStatment().execute();
				//	if (userobject.getUserDetails().getIsloanrequested().equalsIgnoreCase("Y")) {
					//	showAlert1("You Already Applied for a loan.You can check your loan status and edit your loan request.", "");
				//	}
					//else{
						Intent in = new Intent(AndroidGridLayoutActivity.this, Referal.class);
						in.putExtra("user_obj", userobject);

						startActivity(in);
						finish();
					//}


				}
				else if(position==7)
				{
					Intent in = new Intent(AndroidGridLayoutActivity.this, FAQs.class);
					in.putExtra("user_obj", userobject);
					startActivity(in);
					finish();


				}	
				else if(position==8)
				{
					Intent in = new Intent(AndroidGridLayoutActivity.this, Help.class);
					in.putExtra("user_obj", userobject);
					startActivity(in);
					finish();


				}	

			}
		});

		buttonaccountdetails.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),AskmPin.class);
				i.putExtra("user_obj", userobject);
				startActivity(i);


			}
		});
	}
	public class Logout extends AsyncTask<String, Void, UserLogoutParser> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(AndroidGridLayoutActivity.this, "Loging out... ",
					"Please Wait");
		}

		@Override
		protected UserLogoutParser doInBackground(String... arg0) {SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 

		HttpLogout logout=new HttpLogout();
		UserLogoutParser logoutobj=logout.httpuserlogout(getApplicationContext(), userobject.getUserDetails().getUserMobile(), userobject.getSessionid());

		return logoutobj;

		}

		@Override
		protected void onPostExecute(UserLogoutParser strFromDoInBg) {
			pd.dismiss();
			if (strFromDoInBg!=null) {
				if (strFromDoInBg.getResultCode().equalsIgnoreCase("200")) {
					Intent i=new Intent(getApplicationContext(),LoginActivity.class);
					startActivity(i);
					finish();

				}

			}



		}
	}
	private String getbalance() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		return pref.getString("balance", "");
	}
	private boolean getisloanrequested() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		return pref.getBoolean("is_loan_applied",false);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		showexitdialog();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//new refresh().execute();
	}
	public void showAlert1(String message,final String type)
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(AndroidGridLayoutActivity.this);

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
						Intent in = new Intent(AndroidGridLayoutActivity.this, Loanstatus.class);
						in.putExtra("user_obj", userobject);
						startActivity(in);
						finish();


					}
				});


			}
		});



		alertDialog.show();
	}
	void showexitdialog(){
		LayoutInflater factory = LayoutInflater.from(this);
		final View previewDialogView = factory.inflate( R.layout.exit_dialog, null);
		final AlertDialog exitDialog = new AlertDialog.Builder(AndroidGridLayoutActivity.this,R.style.DialogTheme).create();



		exitDialog.setView(previewDialogView);
		exitDialog.setCancelable(false);
		previewDialogView.findViewById(R.id.buttonno).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				exitDialog.dismiss();
			}
		});
		previewDialogView.findViewById(R.id.buttonyes).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Logout().execute();
			}
		});

		exitDialog.show();
	}
}