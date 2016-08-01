package com.wpits.mwalletsamba;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;



















import com.wpits.mwalletsamba.AlertDialogRadio.AlertPositiveListener;
import com.wpits.parser.UpdateProfileParser;
import com.wpits.parser.UserLoginparser;
import com.wpits.utils.Utility;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Update_Profile extends Activity implements AlertPositiveListener {
	EditText editTextemail;
	Button buttonupdate;
	ImageView imgprofile;
	UserLoginparser userobject;
	final int CAMERA_CAPTURE = 1;
	//keep track of cropping intent
	final int PIC_CROP = 2;
	private Uri avatarUri;
	private Uri picUri;
	boolean isprofilechanged=false,isemailaddresschanged=false;
	Bitmap changedimage;
	private static final String TEMP_PHOTO_FILE = "temp_profile.jpg";  


	private static final int REQUEST_CHOOSE_FILE = 0xac23;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_profile_1);

		editTextemail=(EditText)findViewById(R.id.editTextemail);
		buttonupdate=(Button)findViewById(R.id.buttonupdate);
		imgprofile=(ImageView)findViewById(R.id.imageView1);
		Intent iuser=getIntent();
		if (iuser!=null) {
			userobject=(UserLoginparser)iuser.getExtras().getSerializable("user_obj");
		}
		editTextemail.setText(userobject.getUserDetails().getUser_email_id());
		if (userobject.getUserDetails().getUserImage()!=null&&userobject.getUserDetails().getUserImage().length()>0) {
			try {
				imgprofile.setImageBitmap(Utility.getbitmap(userobject.getUserDetails().getUserImage()));

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		editTextemail.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().equalsIgnoreCase(userobject.getUserDetails().getUser_email_id())) {
					isemailaddresschanged=false;
				}
				else{
					isemailaddresschanged=true;					
				}
				// TODO Auto-generated method stub

			}
		});
		buttonupdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (editTextemail.getText().toString().length()>0) {
					if (isemailaddresschanged||isprofilechanged) {
						new Update().execute();

					}

				}
				else{
					editTextemail.setError("Email Cannot be empty");

				}


			}
		});

		imgprofile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/** Instantiating the DialogFragment class */
				FragmentManager manager = getFragmentManager();

				AlertDialogRadio alert = new AlertDialogRadio();

				/** Creating a bundle object to store the selected item's index */
				Bundle b  = new Bundle();

				/** Storing the selected item's index in the bundle object */
				b.putInt("position", 0);

				/** Setting the bundle object to the dialog fragment object */
				alert.setArguments(b);

				/** Creating the dialog fragment object, which will in turn open the alert dialog window */
				alert.show(manager, "alert_dialog_radio");		



			}
		});

	}
	public class Update extends AsyncTask<String, Void, UpdateProfileParser> {
		ProgressDialog pd;
		String result;
		String imagestr="NA",emailstr;
		UpdateProfileParser response;
		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(Update_Profile.this, "Updating Profile ",
					"Please Wait");
		}

		@Override
		protected UpdateProfileParser doInBackground(String... arg0) {
			if (isprofilechanged) {
				if (changedimage!=null) {
					imagestr=Utility.getimagestr(changedimage);
				}
			}
			emailstr=editTextemail.getText().toString();

			if (userobject!=null) {
				Httpupdate update=new Httpupdate();
				response=update.httpuserupdate(getApplicationContext(), userobject.getUserDetails().getUserMobile(), userobject.getSessionid(), imagestr, emailstr, userobject.getUser_type());

			}
			return response;


		}

		@Override
		protected void onPostExecute(UpdateProfileParser strFromDoInBg) {
			pd.dismiss();
			if (strFromDoInBg!=null) {
				if (strFromDoInBg.getResultCode().equalsIgnoreCase("200")) {
					Toast.makeText(getApplicationContext(), "Profile Update Successfull.Please Login Again to See the effects", Toast.LENGTH_LONG).show();

				}
				else{
					Toast.makeText(getApplicationContext(), strFromDoInBg.getResultStatus(), Toast.LENGTH_LONG).show();


				}
			}
			else{
				Toast.makeText(getApplicationContext(), "Update Failed Try After some time.", Toast.LENGTH_LONG).show();

			}
		}
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent in = new Intent(getApplicationContext(), AndroidGridLayoutActivity.class);
		in.putExtra("user_obj", userobject);
		startActivity(in);
		finish();
	}


	@Override
	public void onPositiveClick(int position) {
		// TODO Auto-generated method stub
		if(position==0){
			//startActivity(new Intent(getApplicationContext(),ShootAndCropActivity.class));
			try {
				//use standard intent to capture an image
				Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//we will handle the returned data in onActivityResult
				startActivityForResult(captureIntent, CAMERA_CAPTURE);
			}
			catch(ActivityNotFoundException anfe){
				//display an error message
				String errorMessage = "Whoops - your device doesn't support capturing images!";
				Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
				toast.show();
			}
		}if(position == 1){
			Intent attachFileIntent = new Intent();
			attachFileIntent.setType("image/*");
			attachFileIntent.setAction(Intent.ACTION_GET_CONTENT);
			Intent chooser = Intent.createChooser(attachFileIntent,"Pic");
			startActivityForResult(chooser, REQUEST_CHOOSE_FILE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CHOOSE_FILE) {
				this.avatarUri = data.getData();
				performCrop1();
				//loadImageIntoPreview(this.avatarUri);

			}


			//user is returning from capturing an image using the camera
			else if(requestCode == CAMERA_CAPTURE){
				//get the Uri for the captured image
				picUri = data.getData();
				//carry out the crop operation
				performCrop();
			}
			//user is returning from cropping the image
			else if(requestCode == PIC_CROP){
				/*//get the returned data
    			Bundle extras = data.getExtras();
    			//get the cropped bitmap
    			Bitmap thePic = extras.getParcelable("data");
    			//retrieve a reference to the ImageView
    			ImageView picView = (ImageView)findViewById(R.id.picture);
    			//display the returned cropped image
    			picView.setImageBitmap(thePic);*/
				String filePath= Environment.getExternalStorageDirectory()
						+"/"+TEMP_PHOTO_FILE;
				Bitmap selectedImage =  BitmapFactory.decodeFile(filePath);

				this.avatarUri = data.getData();
				loadImageIntoPreview(selectedImage);
				/*XmppConnectionService xmppConnectionService.publishAvatar(account, avatarUri,
						avatarPublication);*/
			}

		}

	}

	public void loadImageIntoPreview(Bitmap bitmap)
	{
		if (bitmap!=null) {
			try {
				//Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),picuri);

				imgprofile.setImageBitmap(bitmap);
				isprofilechanged=true;
				changedimage=bitmap;



			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		}

	}
	private void performCrop(){
		//take care of exceptions
		try {
			//call the standard crop action intent (the user device may not support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP"); 
			//indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
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
			cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());

			//start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, PIC_CROP);  
		}
		//respond to users whose devices do not support the crop action
		catch(ActivityNotFoundException anfe){
			//display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private void performCrop1(){
		//take care of exceptions
		try {
			//call the standard crop action intent (the user device may not support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP"); 
			//indicate image type and Uri
			cropIntent.setDataAndType(avatarUri, "image/*");
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
			cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());

			//start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, PIC_CROP);  
		}
		//respond to users whose devices do not support the crop action
		catch(ActivityNotFoundException anfe){
			//display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private Uri getTempUri() {
		return Uri.fromFile(getTempFile());
	}

	private File getTempFile() {

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

			File file = new File(Environment.getExternalStorageDirectory(),TEMP_PHOTO_FILE);
			try {
				file.createNewFile();
			} catch (IOException e) {}

			return file;
		} else {

			return null;
		}
	}
}
