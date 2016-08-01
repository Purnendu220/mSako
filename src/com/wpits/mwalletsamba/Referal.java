package com.wpits.mwalletsamba;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import java.util.Random;





import com.wpits.parser.UserLoginparser;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Referal extends Activity {
	ArrayList<ResolveInfo> applist;
	private PackageManager packageManager = null;
	private Appresinfo listadaptor = null;

	UserLoginparser userobject;

	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_referal);
		GridView ls=(GridView)findViewById(R.id.grid_appicon);
		tv=(TextView)findViewById(R.id.textView2);

		Intent iuser = getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
		}
		tv.setText("	"+userobject.getUserDetails().getUserMobile());
		//	generateRandomString(8);
		applist = new ArrayList<ResolveInfo>();
		packageManager = getPackageManager();
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT,"Hi, use this app & earn points for watching ads. I tried it & it’s awesome! Click link to download & use code "+ tv.getText().toString()+" . http://wpitservices.in/apps/Mobiscreen.apk");
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Register to M_ad");

		sendIntent.setType("text/plain");
		//startActivity(Intent.createChooser(sendIntent,"Share with ...."));
		//Intent.createChooser(sendIntent,"Share with ....");
		List<ResolveInfo> resInfo = getPackageManager() .queryIntentActivities(sendIntent, 0);
		if (!resInfo.isEmpty()) {
			System.out.println("If Called");
			for (ResolveInfo info : resInfo) {


				System.out.println(info.activityInfo.packageName);  

				try {
					if (null != packageManager.getLaunchIntentForPackage(info.activityInfo.packageName)) {


						applist.add(info);
					}
				}
				catch(Exception e){

				}
			}
			//  System.out.println(info.activityInfo.);
			/*  if (info.activityInfo.packageName.toLowerCase().contains(
	                        nameApp)
	                        || info.activityInfo.name.toLowerCase().contains(
	                                nameApp)) {
	                    targetedShare.putExtra(Intent.EXTRA_SUBJECT,
	                            "Sample Photo");
	                    targetedShare.putExtra(Intent.EXTRA_TEXT, message);
	                    targetedShare.putExtra(Intent.EXTRA_STREAM,
	                            Uri.fromFile(new File(imagePath)));
	                    targetedShare.setPackage(info.activityInfo.packageName*/
		}

		listadaptor = new Appresinfo(Referal.this,R.layout.snippet_list_row, applist);
		ls.setAdapter(listadaptor);
		ls.setOnItemClickListener(new OnItemClickListener() {



			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				ResolveInfo app = applist.get(arg2);
				try {
					//Intent intent = packageManager.getLaunchIntentForPackage(app.activityInfo.packageName);
					//packageManager.get
					Intent intent = new Intent();
					if (null != intent) {
						intent.setAction(Intent.ACTION_SEND);
						intent.setPackage(app.activityInfo.packageName);
						intent.putExtra(Intent.EXTRA_TEXT, "Hi, use this app & earn points for watching ads. I tried it & it’s awesome! Click link to download & use code "+ tv.getText().toString()+" . http://wpitservices.in/apps/Mobiscreen.apk");
						intent.setType("text/plain");
						startActivity(intent);
					}
				} catch (ActivityNotFoundException e) {
					Toast.makeText(Referal.this, e.getMessage(),
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(Referal.this, e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
			}

		});
	}
	public static String generateRandomString(int length) 
	{
		Random random = new Random((new Date()).getTime());
		char[] values = {'a','b','c','d','e','f','g','h','i','j',
				'k','l','m','n','o','p','q','r','s','t',
				'u','v','w','x','y','z','0','1','2','3',
				'4','5','6','7','8','9'};
		String out = "";
		for (int i=0;i<length;i++) {
			int idx=random.nextInt(values.length);
			out += values[idx];
		}
		return out;
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(), AndroidGridLayoutActivity.class);
		in.putExtra("user_obj", userobject);


		startActivity(in);
		finish();
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*//* private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
	ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
	for (ApplicationInfo info : list) {
		try {
			if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {

				applist.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	return applist;
}*/
	//startActivity(sendIntent);
	/* final Dialog	 rankDialog = new Dialog(MainActivity.this,0);
rankDialog.setContentView(R.layout.rateapp);
rankDialog.setCancelable(true);

RatingBar  ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);

final ImageView img=(ImageView)rankDialog.findViewById(R.id.imageView1);
final TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);

ratingBar.setRating((float) 0.0);
ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
public void onRatingChanged(RatingBar ratingBar, float rating,
	boolean fromUser) {
img.setBackgroundResource(R.drawable.em_1f600);
text.setVisibility(View.INVISIBLE);
	System.out.println("Rating From User is:  " +rating);

}
});
// text.setText(name);


Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
updateButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
    rankDialog.dismiss();
}
});
//now that the dialog is set up, it's time to show it    
rankDialog.show();*/
}
