package com.wpits.mwalletsamba;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;



public class GCMIntentService extends GCMBaseIntentService {

    private static final String PROJECT_ID = "257696593707";
    
	private static final String TAG = "GCMIntentService";
	 SharedPreferences sharedpreferences;
	//TodoDAO dao;
	public GCMIntentService()
	{
		super(PROJECT_ID);
		Log.d(TAG, "GCMIntentService init");
	}
	

	@Override
	protected void onError(Context ctx, String sError) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Error: " + sError);
		
	}

	@Override
	protected void onMessage(Context ctx, Intent intent) {
		int count=0;
		
		Log.d(TAG, "Message Received");
		//dao=new TodoDAO(ctx);
		String message = intent.getStringExtra("message");
	
	sendGCMIntent(ctx,message);
	try {
		Integer.parseInt(message);
		setotp(message);
		generateNotification(ctx,message);

	} catch (Exception e) {
		// TODO: handle exception
		generateNotification1(ctx,message);

	}
	}

	
	private void sendGCMIntent(Context ctx, String message) {
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("GCM_RECEIVED_ACTION");
		
		broadcastIntent.putExtra("gcm", message);
		
		ctx.sendBroadcast(broadcastIntent);
		
	}
	
	
	@Override
	protected void onRegistered(Context ctx, String regId) {
		// TODO Auto-generated method stub
		// send regId to your server
		Log.d(TAG, regId);
		
	}

	@Override
	protected void onUnregistered(Context ctx, String regId) {
		// TODO Auto-generated method stub
		// send notification to your server to remove that regId
		
	}
	@SuppressWarnings("deprecation")
	private void generateNotification(Context context, String message) {

		  System.out.println(message+"++++++++++2");
	
		  DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm a");
		  String date = df.format(Calendar.getInstance().getTime());
		  int icon = R.drawable.ic_launcher;
		  long when = System.currentTimeMillis();
		  NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		  Notification notification = new Notification(icon, message, when);
		  String title = context.getString(R.string.app_name);
		  String subTitle =message;
		  Intent notificationIntent = new Intent(context, EnterOTP_RegisterActivity.class);
	
		  PendingIntent intent = PendingIntent.getActivity(context, 0,notificationIntent, 0);
		 // notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		  notification.setLatestEventInfo(context, title, subTitle, intent);
		  //To play the default sound with your notification:
		  notification.defaults |= Notification.DEFAULT_SOUND;
		  notification.flags |= Notification.FLAG_AUTO_CANCEL;
		  notification.defaults |= Notification.DEFAULT_VIBRATE;
		  notificationManager.notify((int)System.currentTimeMillis(), notification);
		 // addShortcut(context);
	
    }
	@SuppressWarnings("deprecation")
	private void generateNotification1(Context context, String message) {

		  System.out.println(message+"++++++++++2");
	
		  DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm a");
		  String date = df.format(Calendar.getInstance().getTime());
		  int icon = R.drawable.ic_launcher;
		  long when = System.currentTimeMillis();
		  NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		  Notification notification = new Notification(icon, message, when);
		  String title = context.getString(R.string.app_name);
		  String subTitle =message;
		  Intent notificationIntent = new Intent(context, MiniStatement.class);
	
		  PendingIntent intent = PendingIntent.getActivity(context, 0,notificationIntent, 0);
		 // notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		  notification.setLatestEventInfo(context, title, subTitle, intent);
		  //To play the default sound with your notification:
		  notification.defaults |= Notification.DEFAULT_SOUND;
		  notification.flags |= Notification.FLAG_AUTO_CANCEL;
		  notification.defaults |= Notification.DEFAULT_VIBRATE;
		  notificationManager.notify((int)System.currentTimeMillis(), notification);
		 // addShortcut(context);
	
    }
	/* private void addShortcut(Context context) {
		// removeShortcut(context);
	    	int unread=dao.getunreadnotification();
		 Intent shortcutIntent = new Intent(context, MainActivity.class);
			    shortcutIntent.setAction(Intent.ACTION_MAIN);
			
		   
		        Bitmap bmp =drawTextToBitmap(context,R.drawable.icon,String.valueOf(unread));
		        
			    Intent addIntent = new Intent();
			    addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
			    addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "AircelApplication");
			    addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, bmp);
			    
			    // Inform launcher to create shortcut
			    addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
			    context.sendBroadcast(addIntent);
	    }	
	 private void removeShortcut(Context context) {
	    	
	    	//Deleting shortcut for MainActivity 
	    	//on Home screen
			Intent shortcutIntent = new Intent(context,
					MainActivity.class);
			shortcutIntent.setAction(Intent.ACTION_MAIN);
			
			Intent addIntent = new Intent();
			addIntent
					.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "AircelApplication");

			addIntent
					.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
			context.sendBroadcast(addIntent);
	    }
	 public Bitmap drawTextToBitmap(Context mContext,  int resourceId,  String mText) {
	        try {
	             Resources resources = mContext.getResources();
	                float scale = resources.getDisplayMetrics().density;
	                Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);

	                android.graphics.Bitmap.Config bitmapConfig =   bitmap.getConfig();
	                // set default bitmap config if none
	                if(bitmapConfig == null) {
	                  bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
	                }
	                // resource bitmaps are imutable,
	                // so we need to convert it to mutable one
	                bitmap = bitmap.copy(bitmapConfig, true);

	                Canvas canvas = new Canvas(bitmap);
	                // new antialised Paint
	                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	                Paint  circlePaint = new Paint();

	                // text color - #3D3D3D
	                paint.setColor(Color.RED);
	                // text size in pixels
	                paint.setTextSize((int) (18 * scale));
	                // text shadow
	                paint.setStrokeWidth(1.0f);
	                paint.setStyle(Paint.Style.STROKE);
	                // draw text to the Canvas center
	                Rect bounds = new Rect();
	                paint.getTextBounds(mText, 0, mText.length(), bounds);
	                int x = 7;//(bitmap.getWidth() - bounds.width())/6;
	                int y = 18;//(bitmap.getHeight() + bounds.height())/5;
	             
	                System.out.println("X:-"+x+" Y:-"+y);
	                
	                circlePaint.setColor(Color.GREEN);
	                circlePaint.setAntiAlias(true);
	               
	                canvas.drawCircle(x * scale+12*scale, y * scale, 15.0f*scale, circlePaint);
	                canvas.drawText(mText,  x * scale, y * scale+5*scale, paint);
	              //canvas.drawText(mText, x * scale, y * scale, paint);

	                return bitmap;
	        } catch (Exception e) {
	            // TODO: handle exception



	            return null;
	        }

	      }
	 public void notificationbasge()
	 {
		 Random r = new Random();
	    	int value=dao.getunreadnotification();
	     Log.i("DEMO", "Changing : " + value);

	     PackageManager pm = getApplicationContext().getPackageManager();

	     String lastEnabled = getLastEnabled(); //Getting last enabled from shared preference

	     if (TextUtils.isEmpty(lastEnabled)) {
	         lastEnabled = "com.example.aircelapplication.MainActivity";
	     }
	try {
		Thread.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	     ComponentName componentName = new ComponentName(
	             "com.example.aircelapplication", lastEnabled);
	     pm.setComponentEnabledSetting(componentName,
	             PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
	             PackageManager.DONT_KILL_APP);

	     Log.i("DEMO", "Removing : " + lastEnabled);

	     if (value <= 0) {
	         lastEnabled = "com.example.aircelapplication.MainActivity";
	     }
	     else {
	         lastEnabled = "com.example.aircelapplication.a1";
	     }

	     componentName = new ComponentName("com.example.aircelapplication",
	             lastEnabled);
	     pm.setComponentEnabledSetting(componentName,
	             PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
	             PackageManager.DONT_KILL_APP);
	     Log.i("DEMO", "Adding : " + lastEnabled);
	     setLastEnabled(lastEnabled);
		 
	 }*/
	 private void setotp(String value) {
		    SharedPreferences pref = PreferenceManager
		            .getDefaultSharedPreferences(getApplicationContext());
		    SharedPreferences.Editor editor = pref.edit();
		    editor.putString("OTP", value);
		    editor.commit();
		}
		private String getotp() {
		    SharedPreferences pref = PreferenceManager
		            .getDefaultSharedPreferences(getApplicationContext());
		    return pref.getString("OTP", "");
		}
		
}
