package com.wpits.data;

import java.util.ArrayList;
import java.util.List;

import com.wpits.modelclass.BenefciaryModelclass;
import com.wpits.modelclass.Transferhistory;
import com.wpits.modelclass.TwitterTransfer;
import com.wpits.modelclass.Twittermodelclass;
import com.wpits.parser.LoanTypeDetailParser;
import com.wpits.parser.LoanTypeList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



/**
 * TODOs DAO object.
 * 
 * @author itcuties
 *
 */
public class TodoDAO {

	private SQLiteDatabase db;
	private TodoSQLiteHelper dbHelper;

	public TodoDAO(Context context) {
		dbHelper = new TodoSQLiteHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	// Close the db
	public void close() {
		db.close();
	}

	/**
	 * Create new TODO object
	 * @param todoText
	 */
	//db.execSQL("CREATE TABLE transferhistory (Id INTEGER PRIMARY KEY  , reccustid TEXT ,reccustname TEXT,recbankname TEXT,
	//recbankifsccode TEXT,recaccno TEXT,amount TEXT,time TEXT);");


	public void insertloantypedetails(LoanTypeDetailParser parser)
	{

		for (int i = 0; i < parser.getLoan_typelist().size(); i++) {
			Cursor cursor = 	db.rawQuery("SELECT * FROM loantypedetail WHERE loantypeid="+parser.getLoan_typelist().get(i).getLoan_type_id(), null);
			if(cursor.moveToFirst())
			{
				ContentValues contentValues = new ContentValues();
				contentValues.put("loantypeid", parser.getLoan_typelist().get(i).getLoan_type_id());
				contentValues.put("loan_type_name", parser.getLoan_typelist().get(i).getLoan_type_name());
				contentValues.put("limit_formule", parser.getLoan_typelist().get(i).getLoan_limit());
				contentValues.put("intrest", parser.getLoan_typelist().get(i).getLoan_intrest());

				contentValues.put("tenure", parser.getLoan_typelist().get(i).getLoan_tenure());
				contentValues.put("own_reserve", parser.getLoan_typelist().get(i).getOwn_reserve_amount());
				contentValues.put("admin_fee", parser.getLoan_typelist().get(i).getAdmin_fee());
				db.update("loantypedetail", contentValues, "loantypeid="+parser.getLoan_typelist().get(i).getLoan_type_id(), null);

			}
			else{

				ContentValues contentValues = new ContentValues();
				contentValues.put("loantypeid", parser.getLoan_typelist().get(i).getLoan_type_id());
				contentValues.put("loan_type_name", parser.getLoan_typelist().get(i).getLoan_type_name());
				contentValues.put("limit_formule", parser.getLoan_typelist().get(i).getLoan_limit());
				contentValues.put("intrest", parser.getLoan_typelist().get(i).getLoan_intrest());

				contentValues.put("tenure", parser.getLoan_typelist().get(i).getLoan_tenure());
				contentValues.put("own_reserve", parser.getLoan_typelist().get(i).getOwn_reserve_amount());
				contentValues.put("admin_fee", parser.getLoan_typelist().get(i).getAdmin_fee());

				db.insert("loantypedetail", null, contentValues);
				System.out.println("Customer Data Saved for "+ parser.getLoan_typelist().get(i).getLoan_type_id());
			}



		}


	}

	public LoanTypeList getloantypedetail(String loantypeid)
	{
		Cursor cursor = 	db.rawQuery("SELECT * FROM loantypedetail WHERE loantypeid="+loantypeid, null);
		if (cursor.moveToNext()) {
			System.out.println("Data Found");
			return new LoanTypeList(cursor.getString(cursor.getColumnIndex("loantypeid")), cursor.getString(cursor.getColumnIndex("loan_type_name")), cursor.getString(cursor.getColumnIndex("limit_formule")), cursor.getString(cursor.getColumnIndex("intrest")), cursor.getString(cursor.getColumnIndex("tenure")), cursor.getString(cursor.getColumnIndex("own_reserve")), cursor.getString(cursor.getColumnIndex("admin_fee")));

		}
		else{
			System.out.println("Data not Found");


			return null;

		}


	}

	public void inserttransferhistory(String id,String receivercustid,String receivername,String receiverbankname,
			String receiverbankifsccode,String recevieraccno,String amount,String time)
	{
		Cursor cursor = 	db.rawQuery("SELECT * FROM transferhistory WHERE Id="+id, null);
		if(cursor.moveToFirst())
		{


		}
		else{

			ContentValues contentValues = new ContentValues();
			contentValues.put("Id", id);
			contentValues.put("reccustid", receivercustid);
			contentValues.put("reccustname", receivername);
			contentValues.put("recbankname", receiverbankname);
			contentValues.put("recbankifsccode", receiverbankifsccode);

			contentValues.put("recaccno", recevieraccno);
			contentValues.put("amount", amount);
			contentValues.put("time", time);

			db.insert("transferhistory", null, contentValues);
			System.out.println("Customer Data Saved for "+receivercustid);
		}


	}
	public void inserttwitterbenefinfo(String id,String benefname,String screenname,String twitterid,String time) {
		Cursor cursor = 	db.rawQuery("SELECT * FROM twitterbeneficiary WHERE twitterid="+twitterid, null);
		if(cursor.moveToFirst())
		{


		}
		else{

			ContentValues contentValues = new ContentValues();
			contentValues.put("Id", id);

			contentValues.put("benefname", benefname);
			contentValues.put("screenname", screenname);

			contentValues.put("twitterid", twitterid);
			contentValues.put("time", time);

			db.insert("twitterbeneficiary", null, contentValues);
			System.out.println("Customer Data Saved for "+twitterid);
		}


	}
	public void inserttwittertransferhistory(String id,String benefname,String screenname,String twitterid,String recammount,String time) {
		//	Cursor cursor = 	db.rawQuery("SELECT * FROM twittertransferhistory WHERE twitterid="+twitterid, null);
		//db.execSQL("CREATE TABLE twittertransferhistory (Id INTEGER PRIMARY KEY  , benefname TEXT,screenname TEXT ,twitterid TEXT,recammount,time TEXT);");


		ContentValues contentValues = new ContentValues();
		contentValues.put("Id", id);

		contentValues.put("benefname", benefname);
		contentValues.put("screenname", screenname);

		contentValues.put("twitterid", twitterid);
		contentValues.put("recammount", recammount);

		contentValues.put("time", time);

		db.insert("twittertransferhistory", null, contentValues);
		System.out.println("Customer Data Saved for "+twitterid);


	}
	public void insertbenefinfo(String id,String stringmobileno,String stringbenefname,String stringbenefnickname,String stringbenefemail,String type) {
		Cursor cursor = 	db.rawQuery("SELECT * FROM benficiarytable WHERE mobileno="+stringmobileno, null);
		if(cursor.moveToFirst())
		{


		}
		else{

			ContentValues contentValues = new ContentValues();
			contentValues.put("Id", id);

			contentValues.put("mobileno", stringmobileno);
			contentValues.put("benefname", stringbenefname);
			contentValues.put("benefnickname", stringbenefnickname);
			contentValues.put("benefemail", stringbenefemail);
			contentValues.put("beneftype", type);
			db.insert("benficiarytable", null, contentValues);
			System.out.println("Customer Data Saved for "+stringmobileno);
		}


	}

	public void insertlogininfo(String customerid,String password,String time,String name,String twitterauthorize) {
		Cursor cursor = 	db.rawQuery("SELECT * FROM customerlogin WHERE customerid="+customerid, null);
		if(cursor.moveToFirst())
		{
			ContentValues contentValues = new ContentValues();

			contentValues.put("twiterauthrize", twitterauthorize);
			db.update("customerlogin", contentValues, "customerid="+customerid, null);		


		}
		else{

			ContentValues contentValues = new ContentValues();
			contentValues.put("customerid", customerid);
			contentValues.put("password", password);
			contentValues.put("name", name);

			contentValues.put("time", time);
			contentValues.put("twiterauthrize", twitterauthorize);


			db.insert("customerlogin", null, contentValues);
			//db.update("imageinfo", contentValues, null, null);
			System.out.println("Customer Data Saved for "+time);
		}


	}
	public boolean istwitterauthorized()
	{
		String number=null;
		Cursor cursor = 	db.rawQuery("SELECT * FROM customerlogin", null);
		while (cursor.moveToNext()) {

			number=cursor.getString(5);

		}
		if(number.equals(0))
		{
			return false;
		}
		else{

			return true;
		}
	}
	public int getusercount() {
		Cursor cursor = 	db.rawQuery("SELECT * FROM customerlogin ", null);
		int count=cursor.getCount();

		return count;
	}
	public String getcustomerid()
	{
		String number=null;
		Cursor cursor = 	db.rawQuery("SELECT * FROM customerlogin", null);
		while (cursor.moveToNext()) {

			number=cursor.getString(1);

		}
		return number;

	}

	public String getpassword()
	{
		String number=null;
		Cursor cursor = 	db.rawQuery("SELECT * FROM customerlogin", null);
		while (cursor.moveToNext()) {

			number=cursor.getString(2);

		}
		return number;

	}

	public String getusername()
	{
		String number=null;
		Cursor cursor = 	db.rawQuery("SELECT * FROM customerlogin", null);
		while (cursor.moveToNext()) {

			number=cursor.getString(3);

		}
		return number;

	}
	public ArrayList<BenefciaryModelclass> getbeneficiarylist(String type)
	{
		ArrayList<BenefciaryModelclass> imageinfoList = new ArrayList<BenefciaryModelclass>();
		Cursor cursor = db.rawQuery("SELECT * FROM benficiarytable WHERE beneftype LIKE '"+type+"'",null);
		cursor.moveToFirst();

		// Iterate the results
		while (!cursor.isAfterLast()) {

			imageinfoList.add(getbeneficiaryedata(String.valueOf(cursor.getInt(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
			// Move to the next result
			cursor.moveToNext();
		}
		return imageinfoList;

	}
	public ArrayList<Transferhistory> gettransferhistorylist()
	{
		ArrayList<Transferhistory> transferinfoList = new ArrayList<Transferhistory>();
		Cursor cursor = db.rawQuery("SELECT * FROM transferhistory",null);
		cursor.moveToFirst();

		// Iterate the results
		while (!cursor.isAfterLast()) {

			transferinfoList.add(gettransferhistory(String.valueOf(cursor.getInt(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7)));
			// Move to the next result
			cursor.moveToNext();
		}
		return transferinfoList;

	}
	public ArrayList<Twittermodelclass> getwitterfollowerlist()
	{
		ArrayList<Twittermodelclass> transferinfoList = new ArrayList<Twittermodelclass>();
		Cursor cursor = db.rawQuery("SELECT * FROM twitterbeneficiary",null);
		cursor.moveToFirst();

		// Iterate the results
		while (!cursor.isAfterLast()) {

			transferinfoList.add(gettwitterbenflist(String.valueOf(cursor.getInt(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
			// Move to the next result
			cursor.moveToNext();
		}
		return transferinfoList;

	}
	public ArrayList<TwitterTransfer> gettwittertransferlist()
	{
		ArrayList<TwitterTransfer> transferinfoList = new ArrayList<TwitterTransfer>();
		Cursor cursor = db.rawQuery("SELECT * FROM twittertransferhistory",null);
		cursor.moveToFirst();

		// Iterate the results
		while (!cursor.isAfterLast()) {

			transferinfoList.add(gettransferhistorytwitter(String.valueOf(cursor.getInt(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
			// Move to the next result
			cursor.moveToNext();
		}
		return transferinfoList;

	}
	private Transferhistory gettransferhistory(String id,String receivercustid,String receivername,String receiverbankname,
			String receiverbankifsccode,String recevieraccno,String amount,String time)
	{
		return new Transferhistory(id, receivercustid, receivername, receiverbankname, receiverbankifsccode, recevieraccno, amount, time);


	}
	private Twittermodelclass gettwitterbenflist(String id,String benefname,String screenname,String twitterid,String time)
	{
		return new Twittermodelclass(id, benefname, screenname,twitterid, time);


	}
	private BenefciaryModelclass getbeneficiaryedata(String id,String mobilenumber,String benefname,String benefnickname,String benefemail,String type)
	{
		return new BenefciaryModelclass(id,mobilenumber,benefname,benefnickname,benefemail,type);


	}

	private TwitterTransfer gettransferhistorytwitter(String id, String benefname, String screenname,
			String twitterid, String recammount, String time)
	{
		return new TwitterTransfer(id, benefname, screenname,twitterid,recammount,time);


	}
}
