package com.wpits.utils;

import java.text.SimpleDateFormat;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.catalina.util.Base64;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;
import android.util.TypedValue;

/**/

public class Utility {
	// private static final String PROPERTY_REG_ID = "registration_id";
	// private static final String PROPERTY_APP_VERSION = "appVersion";
	/*public static boolean isNetworkConnected() {
		NetworkInfo ni = BaseApplication.cm.getActiveNetworkInfo();
		if (ni == null) {
			return false;
		} else
			return true;
	}*/

	public static String TRANSFER_MSAKO="msako";
	public static String TRANSFER_MPESA="mpesa";
	public static String ACCOUNT_WALLET="wallet";
	public static String ACCOUNT_LOAN="loan";

	public static String GetIsoDateTime(Date dateToFormat) {
		/**
		 * This function is used in gpslogger.loggers.* and for most of them the
		 * default locale should be fine, but in the case of HttpUrlLogger we
		 * want machine-readable output, thus Locale.US.
		 * 
		 * Be wary of the default locale
		 * http://developer.android.com/reference/java
		 * /util/Locale.html#default_locale
		 */

		// GPX specs say that time given should be in UTC, no local time.
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
				Locale.US);
		// sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

		return sdf.format(dateToFormat);
	}

	public static String getDateTimePlan(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm aaa",
				Locale.US);
		return sdf.format(date);
	}

	public static String getChatTime(long date) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(Long.parseLong(String.valueOf(date)));
		Date d = (Date) c.getTime();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(d);

	}

	public static String getDateTimePlan(String format, String dateStr) {

		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

		// 2015-04-13T19:49:12.000Z

		SimpleDateFormat sdfParser = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		Date date = null;
		try {
			date = sdfParser.parse(dateStr);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return dateStr;
		}

		return sdf.format(date);
	}

	public static Date getDateTimePlan(String dateStr) {

		// 2015-04-13T19:49:12.000Z

		SimpleDateFormat sdfParser = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
		Date date = null;
		try {
			date = sdfParser.parse(dateStr);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return new Date();
		}
		return date;
	}

	public static Calendar DateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/*	public static String getNationalNumber(String phoneNumber) {
		String nationNumber = "";
		if (phoneNumber.length() == 0)
			return "";
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {
			PhoneNumber numberProto = phoneUtil.parse(phoneNumber, "");
			if (numberProto.hasNationalNumber())
				nationNumber = "" + numberProto.getNationalNumber();
		} catch (NumberParseException e) {

			phoneNumber = phoneNumber.replaceAll("[^0-9]+", "").trim();
			nationNumber = phoneNumber;
		}
		return nationNumber;
	}*/

	/*	public static String getCountryCode(String phoneNumber) {
		String nationCountryCode = "";
		if (phoneNumber.length() == 0)
			return "";
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {
			PhoneNumber numberProto = phoneUtil.parse(phoneNumber, "");
			if (numberProto.hasNationalNumber()) {
				nationCountryCode = "" + numberProto.getCountryCode();
				nationCountryCode = "+" + nationCountryCode;
			}
		} catch (NumberParseException e) {
			nationCountryCode = "";
		}
		return nationCountryCode;
	}*/

	/*public static String MinuteInSeconds(String date) {

		String strLastTime = null;
		String strLastDate = null;
		String strLastDateTime = null;
		SimpleDateFormat dateFormatter_time = new SimpleDateFormat(
				"hh:mm:ss aa");
		SimpleDateFormat dateFormatter_date = new SimpleDateFormat("dd-MM-yyyy");
		Date strDate1 = null;
		try {
			try {
				strDate1 = ISO8601DateParser.parse(date);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			strLastTime = dateFormatter_time.format(strDate1);
			strLastDate = dateFormatter_date.format(strDate1);

	 * if (strLastDate.equals(formattedDate)) { strLastDateTime =
	 * strLastTime; } else {

			strLastDateTime = strLastDate + " " + strLastTime;
			// }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strLastDateTime;
	}*/

	/*public static String getContactNameByUserId(Context context, String userId) {
		String contactName = null;
		Friend friend = DaoHandler.getInstance(context).getFriendByUserID(
				userId);
		if (friend != null) {
			contactName = friend.getContact_name();
		}
		return contactName;
	}
	 */
	public static String getContactNameUsingCountrycode(ContentResolver cr,
			String phoneNumber, String countrycode) {
		String contactName = null;
		contactName = getContactName(cr, phoneNumber);
		if (contactName == null) {
			contactName = getContactName(cr, "0" + phoneNumber);
		}
		if (contactName == null) {
			if (countrycode != null) {
				contactName = getContactName(cr, "+" + countrycode
						+ phoneNumber);
			}
		}
		return contactName;
	}

	public static String getContactName(ContentResolver cr, String phoneNumber) {
		String contactName = null;
		try {
			if (phoneNumber == null)
				return null;
			if (cr == null) {
				return null;
			}
			Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
					Uri.encode(phoneNumber));
			Cursor cursor = cr
					.query(uri, new String[] { PhoneLookup.DISPLAY_NAME },
							null, null, null);
			if (cursor == null) {
				return null;
			}
			if (cursor.moveToFirst()) {
				contactName = cursor.getString(cursor
						.getColumnIndex(PhoneLookup.DISPLAY_NAME));
			}

			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		} catch (Exception e) {

		}
		return contactName;
	}

	public static String getAppVersionName(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	public static int getAppVersionCode(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	public static int dp2px(Context context, float dp) {
		Resources r = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				r.getDisplayMetrics());
		return Math.round(px);
	}

	public static String encrypt(String message) throws Exception {
		final MessageDigest md = MessageDigest.getInstance("md5");
		final byte[] digestOfPassword = md.digest("HG58YZ3CR9"
				.getBytes("utf-8"));
		final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		for (int j = 0, k = 16; j < 8;) {
			keyBytes[k++] = keyBytes[j++];
		}

		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);

		final byte[] plainTextBytes = message.getBytes("utf-8");
		final byte[] cipherText = cipher.doFinal(plainTextBytes);
		final String encodedCipherText = new BASE64Encoder()
		.encode(cipherText);

		return encodedCipherText;
	}

	public static String decrypt(byte[] message) throws Exception {

		System.out.println(new String(message));
		final MessageDigest md = MessageDigest.getInstance("md5");
		final byte[] digestOfPassword = md.digest("HG58YZ3CR9"
				.getBytes("utf-8"));
		final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		for (int j = 0, k = 16; j < 8;) {
			keyBytes[k++] = keyBytes[j++];
		}

		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		decipher.init(Cipher.DECRYPT_MODE, key, iv);

		final byte[] encData = new
				BASE64Decoder().decodeBuffer(new String(message, "utf-8"));
		final byte[] plainText = decipher.doFinal(encData);

		return new String(plainText, "UTF-8");
	}
	public static Bitmap getbitmap(String imagestr)
	{
		try {
			byte[] decodedString = android.util.Base64.decode(imagestr.getBytes(),android.util.Base64.DEFAULT);
			Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); 
			return decodedByte;


		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}

	}
	public static String getimagestr(Bitmap bmp)
	{
		try {

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
			bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
			byte[] byteArray = byteArrayOutputStream .toByteArray();

			String encoded = android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);
			return encoded;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
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
		//bm.recycle();
		return resizedBitmap;
	}

	public static Bitmap drawableToBitmap (Drawable drawable) {
		Bitmap bitmap = null;

		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			if(bitmapDrawable.getBitmap() != null) {
				return bitmapDrawable.getBitmap();
			}
		}

		if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
			bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
		} else {
			bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}
}