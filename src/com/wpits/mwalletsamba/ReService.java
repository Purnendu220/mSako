package com.wpits.mwalletsamba;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.android.gcm.GCMRegistrar;

import android.app.Service;
import android.os.IBinder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class ReService extends Service {
   
   /** indicates how to behave if the service is killed */
   int mStartMode;
   
   /** interface for clients that bind */
   IBinder mBinder;     
   
   /** indicates whether onRebind should be used */
   boolean mAllowRebind;
   private static final String PROJECT_ID = "257696593707";
	 String regId,registrationStatus;
	 String TAG="Aircel";
	 boolean threadstatus=true;
   /** Called when the service is being created. */
   @Override
   public void onCreate() {
     
   }
   public String registerClient() {

		try {
			// Check that the device supports GCM (should be in a try / catch)
			GCMRegistrar.checkDevice(this);

			// Check the manifest to be sure this app has all the required
			// permissions.
			GCMRegistrar.checkManifest(this);

			// Get the existing registration id, if it exists.
			regId = GCMRegistrar.getRegistrationId(this);

			if (regId.equals("")) {

				registrationStatus = "Registering...";


				// register this device for this project
				GCMRegistrar.register(this, PROJECT_ID);
				regId = GCMRegistrar.getRegistrationId(this);

				registrationStatus = "Registration Acquired";

				// This is actually a dummy function.  At this point, one
				// would send the registration id, and other identifying
				// information to your server, which should save the id
				// for use when broadcasting messages.
				
			/*	 boolean stat=sendRegistrationToServer("AIRCEL",regId);
					if(stat)
					{

					}
			*/
				//sendRegistrationToServer();

			} else {

				registrationStatus = "Already registered";
				/* boolean stat=sendRegistrationToServer("AIRCEL",regId);
					if(stat)
					{

					}*/
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			registrationStatus = e.getMessage();
			
		}
		

		Log.d(TAG, registrationStatus);
		//edtgcmregid.setText(regId);
		System.out.println(regId);
		
		// This is part of our CHEAT.  For this demo, you'll need to
		// capture this registration id so it can be used in our demo web
		// service.
		Log.d(TAG, regId);
		setgcmid(regId);
	return regId;
	}
   private boolean sendRegistrationToServer(String user,String gcmid) {
		try {
			System.out.println("(((((((((((((())))))))))))))))))))))"+gcmid.length());
			TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			user=telephonyManager.getDeviceId();
			if(gcmid!=null&gcmid.length()>0){
			HttpClient httpclient = new DefaultHttpClient();
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+GlobalIpConfigration.URL_CHAT.toString()); 
			HttpPost httppost = new HttpPost("http://192.168.10.65:8080/GCMService/Registrationservlet");
			//	HttpPost httppost = new HttpPost("http://182.74.113.55:2014/AgentLocation/register");

			
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("UserId", user));
			nameValuePairs.add(new BasicNameValuePair("Gcmid", gcmid));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// httpclient.execute(httppost);
			HttpResponse response = httpclient.execute(httppost);
			//Thread.sleep(10000);
			HttpEntity rp = response.getEntity();
			String responseregcont =  EntityUtils.toString(rp);
			System.out.println(responseregcont);
			if(responseregcont.length()>20)
			{
			return false;	
			}
			else
			{
				if(responseregcont.equalsIgnoreCase(String.valueOf(1)))
				{
					SharedPreferences sharedpreferences = getSharedPreferences("aircel", Context.MODE_PRIVATE);

					SharedPreferences.Editor editor = sharedpreferences.edit();
			          
			          editor.putBoolean("isregisterd", true);
			         

			          editor.commit();
					return true;

				}
				else{

	                   return false;
				}

			}
			}
			else{
				return false;
			}
				
		}
		catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;
		}
		// This is an empty placeholder for an asynchronous task to post the
		// registration
		// id and any other identifying information to your server.
		
	}
   /** The service is starting, due to a call to startService() */
   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
	   
	   Thread t;
	    ForThread ft = new ForThread();
	    t = new Thread(ft);
	    t.start();

      return mStartMode;
   }
   private class ForThread implements Runnable{
	    public void run() {
	        while (threadstatus) {
	            try {
	                Log.v("ThreadSleeping","5 sec");
	                SharedPreferences sharedpreferences = getSharedPreferences("aircel", Context.MODE_PRIVATE);
	    			if(getgcmid().length()<=0){
	    				String gcmid=getgcmid();
	    				registerClient();

                  //sendRegistrationToServer("nodeviceid", gcmid);
                  }
	    			else{
	    				//Stopme();
	    				stopSelf();
	    				threadstatus=false;
	    			}
	                Thread.sleep(5000);
	            } catch (InterruptedException e) {
	            }finally{
	                Log.v("Finally called","Finally called");
	            }   
	        }
	    }
	}
   /** A client is binding to the service with bindService() */
   public void Stopme()
   {
	   Thread t;
	    ForThread ft = new ForThread();
	    t = new Thread(ft);
	    t.stop();
	   
   }
   @Override
   public IBinder onBind(Intent intent) {
      return mBinder;
   }

   /** Called when all clients have unbound with unbindService() */
   @Override
   public boolean onUnbind(Intent intent) {
      return mAllowRebind;
   }

   /** Called when a client is binding to the service with bindService()*/
   @Override
   public void onRebind(Intent intent) {

   }

   /** Called when The service is no longer used and is being destroyed */
   @Override
   public void onDestroy() {

   }
   private void setgcmid(String value) {
	    SharedPreferences pref = PreferenceManager
	            .getDefaultSharedPreferences(getApplicationContext());
	    SharedPreferences.Editor editor = pref.edit();
	    editor.putString("GCMID", value);
	    editor.commit();
	}
private String getgcmid() {
	    SharedPreferences pref = PreferenceManager
	            .getDefaultSharedPreferences(getApplicationContext());
	    return pref.getString("GCMID", "");
	}
}