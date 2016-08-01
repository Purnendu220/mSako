package com.wpits.mwalletsamba;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;












import com.wpits.utils.CustomMultiPartEntity;
import com.wpits.utils.CustomMultiPartEntity.ProgressListener;
import com.wpits.utils.GetimagePart;
import com.wpits.utils.SingleChoiceDialogFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Agent_Registration extends Activity  {
	RadioGroup type;
	RadioButton selectedtype;
	final ArrayList<Integer>    mSelectedItems = new ArrayList<Integer>();  
	boolean[] citem ={false,false,false,false};
	int NameSubmitted=0;
	int DetailSubmitted=1;
	int ProfilepicSubmitted=2;
	int Addresssubmitted=3;
	int walletsubmitted=4;
	int Process=NameSubmitted;
	EditText selectlanguage,edt_firstname,edt_lastname,edt_middlename,edt_email,edt_idnumber
	, edt_dateofbirth,edt_fathername,edt_mothername,edt_mobile;
	EditText edt_house_no, edt_street_no,edt_locality,edt_city,edt_district,edt_state,edt_country;
	EditText edt_walletvalue,edt_walletminvalue,edt_walletmaxvalue,edt_notifymsisdn,edt_notifyemail,edt_password;
	TextView text_message;
	Spinner id_type;
	LinearLayout address_layout, name_layout,profile_layout,detail_layout,wallet_layout;
	Button registration;
	ImageView profilepic;
	Uri   cameraImagePath;
	int REQUEST_IMAGE_CAPTURE=1;
	int PIC_CROP=2;
	InputStream stream;
	String ip;
	String imeinumber,parent_agent_id;
	String fname,mname,lname,agent_proof_id,parent_agent_id_proof_value,gender="Male",mobile1;
	String stroffile;
	String first_name,lastname,dob,fathername,mothername,middlename,lanuage,idtype,idnumber,email
	,houseno,Streetno,locality,city,district,state,country,mobile;
	File file;
	ByteArrayBody bab;
	int selctedcountrycode,selectedstatecode,selectedditrictcode,selectedcitycode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agent__registration);
		registration=(Button)findViewById(R.id.btnregister);
		//button_back=(Button)findViewById(R.id.button_back);

		text_message=(TextView)findViewById(R.id.text_message);

		type=(RadioGroup)findViewById(R.id.radioType);

		selectlanguage =(EditText)findViewById(R.id.edt_selectlanguage);
		id_type=(Spinner)findViewById(R.id.id_type);
		edt_idnumber =(EditText)findViewById(R.id.edt_idnumber);
		edt_email =(EditText)findViewById(R.id.edt_email);
		edt_mobile=(EditText)findViewById(R.id.edt_mobile);

		edt_firstname =(EditText)findViewById(R.id.edt_firstname);
		edt_lastname =(EditText)findViewById(R.id.edt_lastname);
		edt_middlename =(EditText)findViewById(R.id.edt_middlename);
		edt_dateofbirth =(EditText)findViewById(R.id.edt_dateofbirth);
		edt_fathername =(EditText)findViewById(R.id.edt_fathername);
		edt_mothername =(EditText)findViewById(R.id.edt_mothername);


		edt_house_no=(EditText)findViewById(R.id.edt_house_no);
		edt_street_no=(EditText)findViewById(R.id.edt_street_no);
		edt_locality=(EditText)findViewById(R.id.edt_locality);
		edt_city=(EditText)findViewById(R.id.edt_city);
		edt_district=(EditText)findViewById(R.id.edt_district);
		edt_state =(EditText)findViewById(R.id.edt_state);
		edt_country=(EditText)findViewById(R.id.edt_country);

		edt_walletvalue=(EditText)findViewById(R.id.edt_walletvalue);
		edt_walletminvalue=(EditText)findViewById(R.id.edt_walletminvalue);
		edt_walletmaxvalue=(EditText)findViewById(R.id.edt_walletmaxvalue);
		edt_notifymsisdn=(EditText)findViewById(R.id.edt_notifymsisdn);
		edt_notifyemail=(EditText)findViewById(R.id.edt_notifyemail);
		edt_password=(EditText)findViewById(R.id.edt_password);

		detail_layout=(LinearLayout)findViewById(R.id.detail_layout);
		name_layout=(LinearLayout)findViewById(R.id.name_layout);
		profile_layout=(LinearLayout)findViewById(R.id.profile_layout);
		wallet_layout=(LinearLayout)findViewById(R.id.wallet_layout);
		address_layout=(LinearLayout)findViewById(R.id.address_layout);

		profilepic=(ImageView)findViewById(R.id.image_profilepic);
		if(Process==NameSubmitted){
			name_layout.setVisibility(View.VISIBLE); 
		}
		new Getinfo().execute();
		selectlanguage.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){


					alertMultipleChoiceItems();
				}

				else {

				}
			}
		});
		selectlanguage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alertMultipleChoiceItems();

			}
		});
		edt_dateofbirth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDatePickerDialog();

			}
		});
		edt_dateofbirth.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub

				if(hasFocus){


					showDatePickerDialog();
				}

				else {

				}

			}
		});
		profilepic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				ContentValues values = new ContentValues(3);

				values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
				cameraImagePath = getContentResolver().insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						cameraImagePath);
				if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
					startActivityForResult(takePictureIntent,
							REQUEST_IMAGE_CAPTURE);
				}
			}
		});
		type.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int selectedId = type.getCheckedRadioButtonId();

				// find the radiobutton by returned id
				selectedtype = (RadioButton) findViewById(selectedId);
				gender =selectedtype.getText().toString();

			}
		});
		
		registration.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(Process==NameSubmitted){
					first_name=edt_firstname.getText().toString();
					lastname=edt_lastname.getText().toString();
					dob=edt_dateofbirth.getText().toString();
					fathername=edt_fathername.getText().toString();
					mothername=edt_mothername.getText().toString();
					middlename=edt_middlename.getText().toString();
					if(first_name.length()<=0||
							lastname.length()<=0|| 
							gender.length()<=0||
							dob.length()<=0|| 
							fathername.length()<=0|| 
							mothername.length()<=0) {
						Toast.makeText(getApplicationContext(), "Please Fill All Feilds", Toast.LENGTH_LONG);
						text_message.setText("Please Fill All Feilds");
					}
					else{
						text_message.setText("");
						detail_layout.setVisibility(View.VISIBLE); 
						name_layout.setVisibility(View.INVISIBLE); 
						Process=DetailSubmitted;
						System.out.println(Process);
					}
				}
				else if(Process==DetailSubmitted){

					lanuage=	selectlanguage.getText().toString();
					idtype= id_type.getSelectedItem().toString();
					idnumber=edt_idnumber.getText().toString();
					email=edt_email.getText().toString();
					mobile=edt_mobile.getText().toString();
					if (lanuage.length()<=0||idtype.length()<=0||idnumber.length()<=0||email.length()<=0||mobile.length()<=0) {
						Toast.makeText(getApplicationContext(), "Please Fill All Feilds", Toast.LENGTH_LONG);
						text_message.setText("Please Fill All Feilds");

					} else {
						text_message.setText("");

						address_layout.setVisibility(View.VISIBLE); 
						detail_layout.setVisibility(View.INVISIBLE); 
						Process=Addresssubmitted;
						System.out.println("*************"+Process);
					}


				}
				else if (Process==Addresssubmitted) {


					houseno= edt_house_no.getText().toString();
					Streetno=edt_street_no.getText().toString();
					locality=edt_locality.getText().toString();
					city=edt_city.getText().toString();
					district=edt_district.getText().toString();
					state=edt_state.getText().toString();
					country=edt_country.getText().toString();
					if (houseno.length()<=0||Streetno.length()<=0||locality.length()<=0||
							city.length()<=0||district.length()<=0||state.length()<=0||country.length()<=0) {
						Toast.makeText(getApplicationContext(), "Please Fill All Feilds", Toast.LENGTH_LONG);
						text_message.setText("Please Fill All Feilds");

					} else {
						new Senduserdata().execute();
						/*text_message.setText("");
						address_layout.setVisibility(View.INVISIBLE); 
						profile_layout.setVisibility(View.VISIBLE); 
						Process=ProfilepicSubmitted;*/
					}


				}
				else if(Process==walletsubmitted) {



					String walletvalue=edt_walletvalue.getText().toString();
					String walletminval=edt_walletminvalue.getText().toString();
					String walletmaxval= edt_walletmaxvalue.getText().toString();
					String notifymsisdn=edt_notifymsisdn.getText().toString();
					String notifyemail=edt_notifyemail.getText().toString();
					String mpin=edt_password.getText().toString();
					if (walletvalue.length()<=0||walletminval.length()<=0||walletmaxval.length()<=0||
							notifymsisdn.length()<=0||notifyemail.length()<=0||mpin.length()<=0) {

						Toast.makeText(getApplicationContext(), "Please Fill All Feilds", Toast.LENGTH_LONG);
						text_message.setText("Please Fill All Feilds");

					} else {
						new CreateWallet(walletvalue,walletminval,walletmaxval,notifymsisdn,notifyemail,mpin).execute();
						/*text_message.setText("");

						wallet_layout.setVisibility(View.INVISIBLE); 
						profile_layout.setVisibility(View.VISIBLE); 
						registration.setText("Register");
						Process=ProfilepicSubmitted;*/
					}

				}
				else if(Process==ProfilepicSubmitted){
					/*	profile_layout.setVisibility(View.INVISIBLE); 
					wallet_layout.setVisibility(View.VISIBLE); 
										registration.setText("Register");
					 */

					new AsyncTaskSendFile().execute();


				}

			}
		});
		/*	button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});*/
		edittextclicklistener();
	}
public void edittextclicklistener()
{
	edt_country.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			selectcountry();
             
		}
	});
	edt_state.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(edt_country.getText().toString().length()<=0)
			{
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "Please select country first", Toast.LENGTH_LONG).show();
					}
				});
			}
			else{
				selectstate(selctedcountrycode);
			}
			
			
		}
	});
	edt_district.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(edt_country.getText().toString().length()<=0)
		{
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Please select country first", Toast.LENGTH_LONG).show();
				}
			});
		}
		else if(edt_state.getText().toString().length()<=0)
		{
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Please select state first", Toast.LENGTH_LONG).show();
				}
			});
		}
		else {
			
			selectdistrict(selectedstatecode);
		}
	}
});
	edt_city.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(edt_country.getText().toString().length()<=0)
		{
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Please select country first", Toast.LENGTH_LONG).show();
				}
			});
		}
		else if(edt_state.getText().toString().length()<=0)
		{
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Please select state first", Toast.LENGTH_LONG).show();
				}
			});
		}
		else if(edt_district.getText().toString().length()<=0)
		{
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Please select district first", Toast.LENGTH_LONG).show();
				}
			});
		}
		else {
			selectcity(selectedditrictcode);
		}
	}
});


	
	
}

public void selectcountry()
{
	final CharSequence[] items = {" INDIA "," United Kingdom "," United States of America "," Srilanka ","South Africa"};
    AlertDialog.Builder builder = new AlertDialog.Builder(Agent_Registration.this);

	builder.setSingleChoiceItems(items, 0, null)
    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();

            int selectedcountry = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
            selctedcountrycode=selectedcountry+1;
            System.out.println(selctedcountrycode);
            // Do something useful withe the position of the selected radio button
        edt_country.setText(items[selectedcountry]);
        }
    })
    .show();
	
}
public void selectstate(int selectedcountry)
{
	if(selectedcountry==1){
	final CharSequence[] items = {" Delhi "," Karnataka"," Haryana "};
    AlertDialog.Builder builder = new AlertDialog.Builder(Agent_Registration.this);

	builder.setSingleChoiceItems(items, 0, null)
    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
            int selectedstate = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
            selectedstatecode=selectedstate+1;
            // Do something useful withe the position of the selected radio button
        edt_state.setText(items[selectedstate]);
        }
    })
    .show();
	}
	else{
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "No State data Found", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	
}
public void selectdistrict(int selectedstate)
{
	if(selectedstate==1){
	final CharSequence[] items = {"New Delhi "," Central"," North ","North West"};
    AlertDialog.Builder builder = new AlertDialog.Builder(Agent_Registration.this);

	builder.setSingleChoiceItems(items, 0, null)
    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
            int selecteddistrict = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
            selectedditrictcode=selecteddistrict+1;
            // Do something useful withe the position of the selected radio button
        edt_district.setText(items[selecteddistrict]);
        }
    })
    .show();
	}
	else{
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "No District data Found", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	
}
public void selectcity(int selecteddistrict)
{
	if(selecteddistrict==1){
	final CharSequence[] items = {"Lal kot"," Mehrauli"," siri ","Tuglkabad"};
    AlertDialog.Builder builder = new AlertDialog.Builder(Agent_Registration.this);

	builder.setSingleChoiceItems(items, 0, null)
    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
            int selectedcity = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
            selectedcitycode=selectedcity+1;
            // Do something useful withe the position of the selected radio button
        edt_city.setText(items[selectedcity]);
        }
    })
    .show();
	}
	else if(selecteddistrict==2){
		final CharSequence[] items = {"Firozabad ","Shergarh"," sahejahabanad ","Rastrapati Bhawan"};
	    AlertDialog.Builder builder = new AlertDialog.Builder(Agent_Registration.this);

		builder.setSingleChoiceItems(items, 0, null)
	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            dialog.dismiss();
	            int selectedcity = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
	            selectedcitycode=selectedcity+1;
	            // Do something useful withe the position of the selected radio button
	        edt_city.setText(items[selectedcity]);
	        }
	    })
	    .show();
		}
	else {
runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "No city data Found", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	
}
public class CreateWallet extends AsyncTask<String, String, String>{
		InputStream stream;
		private ProgressDialog dialog;
		String walletvalue,walletminval,walletmaxval,notifymsisdn,notifyemail,mpin;

		public CreateWallet(String walletvalue, String walletminval, String walletmaxval, String notifymsisdn, String notifyemail,String mpin) {
			// TODO Auto-generated constructor stub
			//this.stream=stream;
			this.walletvalue=walletvalue;
			this.walletminval=walletminval;
			this.walletmaxval=walletmaxval;
			this.notifymsisdn=notifymsisdn;
			this.notifyemail=notifyemail;
			this.mpin=mpin;
			dialog = new ProgressDialog(Agent_Registration.this);
		}
		@Override
		protected void onPreExecute() {
			dialog.setMessage("please wait.");
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

		String response=	Httpwalletcreation.createwallet("1", walletvalue, "", walletminval, walletmaxval, "1",notifymsisdn,notifyemail,mpin,mobile, Agent_Registration.this);
		
		
		try {
			String[] responsearr=response.split(",");
			
			
			String walletidimgid=responsearr[0];
			String walletstatus=responsearr[1];
			Integer.parseInt(walletidimgid);
		
			SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MySettings", 0);
			    Editor editor = pref1.edit();
				editor.putString("walletidimgid", walletidimgid);
				editor.putString("walletstatus", walletstatus);
				editor.commit(); 
				
				runOnUiThread( new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(), "Subscriber created Successfully", Toast.LENGTH_LONG).show();
						Intent in = new Intent(Agent_Registration.this, AndroidGridLayoutActivity.class);
						startActivity(in);
						finish();
					}
				});


		} catch (Exception e) {
			// TODO: handle exception
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					text_message.setText("Failed to create wallet");

				}
			});
		}
	
		return null;
		}
		@Override
		protected void onPostExecute(String result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			
		}

	}
	
	
	public class AsyncTaskSendFile extends AsyncTask<String, String, String> {

		final String TAG = "AsyncTaskParseJson.java";
		long totalSize;

		// set your json string url here
		String yourJsonStringUrl = "http://182.74.113.51:8080/BzeebApp/ImageLikeCount";

		// contacts JSONArray
		NotificationCompat.Builder		mBuilder;
		NotificationManager	mNotifyManager ;
		long id=System.currentTimeMillis();
		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			mNotifyManager =
					(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mBuilder = new NotificationCompat.Builder(Agent_Registration.this);
			mBuilder.setContentTitle("Upload Download")
			.setContentText("Upload in progress")
			.setSmallIcon(R.drawable.ic_launcher);

			/*pd = ProgressDialog.show(Agent_Registration.this, "Registering ",
					"Please Wait");*/
			pd=new ProgressDialog(Agent_Registration.this);
			pd.setMessage("Uploading.... ");
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setProgress(0);
			pd.setMax(100);
			pd.show();


			// Starts the thread by calling the run() method in its Runnable



		}

		@Override
		protected String doInBackground(String... arg0) {


			

				HttpEntity resEntity;
				try {
				
					SharedPreferences pref =getApplicationContext().getSharedPreferences("MySettings", 0); 
					 String subsgentid =pref.getString("subsgentid", "");
						String subspid =pref.getString("subspid", "");
						 String sunlevelid =pref.getString("sunlevelid", "");
					FileBody bin = new FileBody(file);
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost post = new HttpPost(GlobalIpConfigration.URL_BANK1+"upload");
					CustomMultiPartEntity reqEntity = new CustomMultiPartEntity(new ProgressListener()
					{
						@Override
						public void transferred(long num)
						{
							System.out.println((int) ((num / (float) totalSize) * 100));
							publishProgress(String.valueOf((int) ((num / (float) totalSize) * 100)));

						}
					});

					reqEntity.addPart("agentId", new StringBody(subsgentid));
					reqEntity.addPart("spId", new StringBody(subspid));

					reqEntity.addPart("file", bab);
					totalSize = reqEntity.getContentLength();
					post.setEntity(reqEntity);
					HttpResponse response = httpClient.execute(post);
					HttpEntity rp = response.getEntity();
					String responsestring =  EntityUtils.toString(rp);
					String[] responsearr=responsestring.split(",");
					
					
				String agimgid=responsearr[0];
				String agstatus=responsearr[1];
				String agstat=responsearr[2];
				Integer.parseInt(agimgid);
			
				SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MySettings", 0);
				    Editor editor = pref.edit();
					editor.putString("agimgid", agimgid);
					editor.putString("agstatus", agstatus);
					editor.putString("agstat", agstat);
					editor.commit(); 
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							profile_layout.setVisibility(View.INVISIBLE); 
							wallet_layout.setVisibility(View.VISIBLE); 
							Process=walletsubmitted;
							registration.setText("Register");
						}
					});
				        	
					//   stopSelf();
				
			}
			catch(Exception e)
			{
              runOnUiThread( new Runnable() {
				public void run() {
					text_message.setText("Uploading Failed ");

				}
			});
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onProgressUpdate(String... progress)
		{
			//pd.setProgress(Integer.parseInt((progress[0])));
			mBuilder.setProgress(100, Integer.parseInt((progress[0])), false);
			// Displays the progress bar for the first time.
			mNotifyManager.notify((int) id, mBuilder.build());
			pd.setProgress(Integer.parseInt((progress[0])));

		}

		@Override
		protected void onPostExecute(String strFromDoInBg) {
			pd.cancel();

			mBuilder.setContentText("Upload complete")
			.setProgress(0,0,false);
			mNotifyManager.notify((int) id, mBuilder.build());	
		
		}
	}


	public class Getinfo extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			ip=getip();
			imeinumber=getimeinumber();
			return null;
		}


	}
	public class Transfer extends AsyncTask<String, String, String> {
		ProgressDialog pd;
		String result;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Agent_Registration.this, "Registering ",
					"Please Wait");
		}

		@Override
		protected String doInBackground(String... arg0) {

			// TextView textmessage = (TextView) findViewById(R.id.textmessage);

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Httpsendregistration httpsendregistration = new Httpsendregistration();
					boolean registered = true;//httpsendregistration.sendregistration(mobile_no, otp_gen, name, mPin, nrc, alternate_mobile, dob, type,address,email,"Agent");

					if(registered){
						SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", MODE_PRIVATE); 
						/*    Editor editor = pref.edit();
     		editor.putString("mobile_no", mobile_no);
     		editor.putString("otp", otp_gen);
     		editor.putString("name", name);
     		editor.putString("nrc", nrc);
     		editor.putString("mPin", mPin);
     		editor.putString("alternate_mobile", alternate_mobile);
     		editor.putString("dob", dob);
     		editor.putString("type", type);
     		editor.putString("email", email);
     		editor.putString("address", address);

     		editor.commit();
						 */
						/*	Bundle b = new Bundle();
         	b.putString("mobile_no", mobile_no);
						 */ 	Intent in = new Intent(Agent_Registration.this, AndroidGridLayoutActivity.class);
						 //	in.putExtras(b);
						 startActivity(in);
						 finish();

					}else{
						Toast.makeText(getApplicationContext(), "	Please check your internet connection.	", Toast.LENGTH_LONG).show();
					}
				}
			});



			return null;
		}

		@Override
		protected void onPostExecute(String strFromDoInBg) {
			pd.dismiss();
		}
	}
	public void alertMultipleChoiceItems(){

		// where we will store or remove selected items

		final CharSequence[] items = {" Hindi "," English "," Punjabi "," Gujrati "};
		AlertDialog.Builder builder = new AlertDialog.Builder(Agent_Registration.this);
		for(Integer i : mSelectedItems){
			System.out.println(items[i]);
			citem[i]=true;
		}
		// set the dialog title
		builder.setTitle("Choose One or More")

		// specify the list array, the items to be selected by default (null for none),
		// and the listener through which to receive call backs when items are selected
		// R.array.choices were set in the resources res/values/strings.xml
		.setMultiChoiceItems(items, citem, new DialogInterface.OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {

				if (isChecked) {
					// if the user checked the item, add it to the selected items
					mSelectedItems.add(which);
				} 

				else if (mSelectedItems.contains(which)) {
					// else if the item is already in the array, remove it 
					mSelectedItems.remove(Integer.valueOf(which));
				}

				// you can also add other codes here, 
				// for example a tool tip that gives user an idea of what he is selecting
				// showToast("Just an example description.");
			}

		})

		// Set the action buttons
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {

				// user clicked OK, so save the mSelectedItems results somewhere
				// here we are trying to retrieve the selected items indices
				String selectedIndex = "";
				selectlanguage.setText("");
				for(Integer i : mSelectedItems){
					selectedIndex += i + ", ";
					System.out.println(items[i]);

					if(selectlanguage.getText().toString().contains(items[i]))
					{
						System.out.println(items[i]+"Already Exists " );

					}
					else
					{
						selectlanguage.append(items[i]+",");

					}
				}
				System.out.println("Selected index: " + selectedIndex);

				selectlanguage.clearFocus();
			}
		})

		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// removes the AlertDialog in the screen
			}
		})

		.show();

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_IMAGE_CAPTURE) {
				performCrop();

			} 
			if(requestCode==PIC_CROP)
			{
				try {
					InputStream in = getContentResolver().openInputStream(cameraImagePath);
					stream=in;
					Uri selectedImageURI = cameraImagePath;
					file = new File(getRealPathFromURI(selectedImageURI));
					profilepic.setImageURI(cameraImagePath);
					byte[] data1=resizeImage(in);
					  bab = new ByteArrayBody(data1, "profilePic.png");

					//new Getfilepart(stream).execute();

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(cameraImagePath.toString());
				
			}

		}

	}

	public class Senduserdata extends AsyncTask<String, String, String>{
		InputStream stream;
		private ProgressDialog dialog;
		public Senduserdata() {
			// TODO Auto-generated constructor stub
			this.stream=stream;
			dialog = new ProgressDialog(Agent_Registration.this);
		}
		@Override
		protected void onPreExecute() {
			dialog.setMessage("please wait.");
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String strresponse=HttpSendUserdetail.senddetail(first_name,lastname,dob,fathername,mothername,middlename,lanuage,idtype,idnumber,email,gender,mobile
					,houseno,Streetno,locality,String.valueOf(selectedcitycode),String.valueOf(selectedditrictcode),String.valueOf(selectedstatecode),String.valueOf(selctedcountrycode),Agent_Registration.this);
String[] response=strresponse.split(",");
        try {
        	
	String subsgentid=response[0];
	String subspid=response[1];
	String sunlevelid=response[2];
	Integer.parseInt(subsgentid);
	Integer.parseInt(subspid);
	Integer.parseInt(sunlevelid);
	SharedPreferences pref = getApplicationContext().getSharedPreferences("MySettings", 0);
	    Editor editor = pref.edit();
		editor.putString("subsgentid", subsgentid);
		editor.putString("subspid", subspid);
		editor.putString("sunlevelid", sunlevelid);
		
		

		

		editor.commit(); 
	runOnUiThread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			text_message.setText("");
			address_layout.setVisibility(View.INVISIBLE); 
			profile_layout.setVisibility(View.VISIBLE); 
			Process=ProfilepicSubmitted;
		}
	});
        	
           } catch (Exception e) {
        	   runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
		   			text_message.setText("Registration Failed try Again");
		
				}
			});

              }
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			
		}

	}
	private void performCrop(){
		//take care of exceptions
		try {
			//call the standard crop action intent (the user device may not support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP"); 
			//indicate image type and Uri
			cropIntent.setDataAndType(cameraImagePath, "image/*");
			//set crop properties
			cropIntent.putExtra("crop", "true");
			//indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			//indicate output X and Y
			cropIntent.putExtra("outputX", 256);
			cropIntent.putExtra("outputY", 256);
			//retrieve data on return
			cropIntent.putExtra("return-data", true);
			cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImagePath);
			//start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, PIC_CROP);  
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ ......................Crop Image  ");

		}
		//respond to users whose devices do not support the crop action
		catch(ActivityNotFoundException anfe){
			//display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	private String getRealPathFromURI(Uri contentURI) {
		String result;
		Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
		if (cursor == null) { // Source is Dropbox or other similar local file path
			result = contentURI.getPath();
		} else { 
			cursor.moveToFirst(); 
			int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
			result = cursor.getString(idx);
			cursor.close();
		}
		return result;
	}
	public String getip()
	{
		WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
		String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
		return ip;
	}

	public String getimeinumber()
	{
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String imei=telephonyManager.getDeviceId();
		return imei;
	}
	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	private void showDatePickerDialog() {/*//fragment.show(getSupportFragmentManager(), null);
		Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(false);
        dpd.vibrate(true);
        dpd.dismissOnPause(true);
        dpd.showYearPickerFirst(true);
        if (true) {
            dpd.setAccentColor(Color.parseColor("#9C27B0"));
        }
        if(true) {
            dpd.setTitle("Date Of Birth");
        }
        dpd.show(getFragmentManager(), "Datepickerdialog");*/}
	public static String getDateTimePlan(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",
				Locale.US);
		return sdf.format(date);
	}
	
	byte[] resizeImage(InputStream is) {
	try{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
		  buffer.write(data, 0, nRead);
		}

		buffer.flush();

		byte[] input= buffer.toByteArray();
	    Bitmap original = BitmapFactory.decodeByteArray(input , 0, input.length);
	    Bitmap resized = Bitmap.createScaledBitmap(original, 100, 100, true);
	         
	    ByteArrayOutputStream blob = new ByteArrayOutputStream();
	    resized.compress(Bitmap.CompressFormat.JPEG, 50, blob);
	    return blob.toByteArray();

	}
	catch(Exception e)
	{
		e.printStackTrace();
		return null;
	}
	}
	
	 private ArrayList<String> getcountrylist()
	    {
	        ArrayList<String> ret_val = new ArrayList<String>();
	         
	        ret_val.add("INDIA");
	        ret_val.add("United Kingdom");
	        ret_val.add("United States of America");
	        ret_val.add("Srilanka");
	        ret_val.add("South Africa");
	        return ret_val;
	    }
/*	@Override
	public void onDateSet(DatePickerDialog view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		 String date = +dayOfMonth+"/"+(++monthOfYear)+"/"+year;
		 edt_dateofbirth.setText(date);
	}*/
	
}
