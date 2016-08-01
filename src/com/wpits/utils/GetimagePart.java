package com.wpits.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Base64;

public class GetimagePart {
	public static List<String> splitFile(InputStream input) throws IOException {
		int partCounter = 1;
	//	Bitmap original = BitmapFactory.decodeStream(input);
	
		int sizeOfFiles =1024*1024/8;// 1MB
		byte[] buffer = new byte[sizeOfFiles];
		List<String> listOfbos = new ArrayList<String>();
		try {

			int tmp = 0;
			int i=0;
			while ((tmp = input.read(buffer)) > 0) {
				ByteArrayOutputStream output = new ByteArrayOutputStream();

				output.write(buffer, 0, tmp);
				
				listOfbos.add(getbase64(output));
				System.out.println(i+":-"+ output.size());
				i++;


			}

			return listOfbos;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public static String getbase64(ByteArrayOutputStream baos)
	{
		 byte[] b = baos.toByteArray(); 

	        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
			return encodedImage;
		
	}
	public static Bitmap getBitmap(String path)
	{
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = BitmapFactory.decodeFile(path,bmOptions);
		//bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
		//System.out.println(bitmap.getHeight());
		return bitmap;
	}
	
	  public static Bitmap decodeFile(String path,int w) {
		    Bitmap b = null;
		    Bitmap bmp=null;
		    File f = new File(path);
		    // Decode image size
		    BitmapFactory.Options o = new BitmapFactory.Options();
		    o.inJustDecodeBounds = true;

		    FileInputStream fis = null;
		    try {
		        fis = new FileInputStream(f);
		        BitmapFactory.decodeStream(fis, null, o);
		        fis.close();

		        int IMAGE_MAX_SIZE = 1024; // maximum dimension limit
		        int scale = 1;
		        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
		            scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
		        }

		        // Decode with inSampleSize
		        BitmapFactory.Options o2 = new BitmapFactory.Options();
		        o2.inSampleSize = scale;

		        fis = new FileInputStream(f);
		        b = BitmapFactory.decodeStream(fis, null, o2);
		        fis.close();
		     bmp=    getResizedBitmap(b,w,w);
		    } catch (Exception e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }

		    return bmp;
		}
	  
	  public static Bitmap decodeprofileImage(String path) {
		    Bitmap b = null;
		    Bitmap bmp=null;
		    File f = new File(path);
		    // Decode image size
		    BitmapFactory.Options o = new BitmapFactory.Options();
		    o.inJustDecodeBounds = true;

		    FileInputStream fis = null;
		    try {
		        fis = new FileInputStream(f);
		        BitmapFactory.decodeStream(fis, null, o);
		        fis.close();

		        int IMAGE_MAX_SIZE = 1024; // maximum dimension limit
		        int scale = 1;
		        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
		            scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
		        }

		        // Decode with inSampleSize
		        BitmapFactory.Options o2 = new BitmapFactory.Options();
		        o2.inSampleSize = scale;

		        fis = new FileInputStream(f);
		        b = BitmapFactory.decodeStream(fis, null, o2);
		        fis.close();
		     bmp=    getResizedBitmap(b,50,50);
		    } catch (Exception e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }

		    return bmp;
		}
	  public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
		    int width = bm.getWidth();
		    int height = bm.getHeight();
		    float scaleWidth = ((float) newWidth) / width;
		    float scaleHeight = ((float) newHeight) / height;
		    // CREATE A MATRIX FOR THE MANIPULATION
		    Matrix matrix = new Matrix();
		    // RESIZE THE BIT MAP
		    matrix.postScale(scaleWidth, scaleHeight);

		    // "RECREATE" THE NEW BITMAP
		    Bitmap resizedBitmap = Bitmap.createBitmap(
		        bm, 0, 0, width, height, matrix, false);
		    bm.recycle();
		    return resizedBitmap;
		}
	  public static File Savethimbnail(byte[] thumbnail,String name,String mime) {
              String [] part=mime.split("/");
		    String root = Environment.getExternalStorageDirectory().toString();
		    File myDir = new File(root + "/saved_images");   
		    if(!myDir.exists())
		    {
			    myDir.mkdirs();

		    }
		   
		    String fname = "Image-"+ name +"."+part[1];
		    File file = new File (myDir, fname);
		    if (file.exists ()) file.delete (); 
		    try {
		    	FileOutputStream out = new FileOutputStream(file);
		    	  out.write(thumbnail);
		    	  out.close();

		    } catch (Exception e) {
		           e.printStackTrace();
		    }
		    	return file;
		}
public static String filesize(String path)
{
	
	URL url;
	int file_size = 0 ;
	try {
		url = new URL(path);
	
	URLConnection urlConnection;
		urlConnection = url.openConnection();
	
	urlConnection.connect();
	 file_size = urlConnection.getContentLength();
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	return String.valueOf(file_size );

}

}
