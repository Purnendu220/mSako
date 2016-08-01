package com.wpits.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Creates application database.
 * 
 * @author itcuties
 *
 */
public class TodoSQLiteHelper extends SQLiteOpenHelper {

	public TodoSQLiteHelper(Context context) {
		// Databse: todos_db, Version: 1
		super(context, "Customer_info", null, 5);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// Execute create table SQL
		db.execSQL("CREATE TABLE customerlogin (Id INTEGER PRIMARY KEY , customerid TEXT ,password TEXT,name TEXT,time TEXT,twiterauthrize TEXT);");
		db.execSQL("CREATE TABLE benficiarytable (Id INTEGER PRIMARY KEY  , mobileno TEXT ,benefname TEXT,benefnickname TEXT,benefemail TEXT,beneftype TEXT);");
		db.execSQL("CREATE TABLE transferhistory (Id INTEGER PRIMARY KEY  , reccustid TEXT ,reccustname TEXT,recbankname TEXT,recbankifsccode TEXT,recaccno TEXT,amount TEXT,time TEXT);");
		db.execSQL("CREATE TABLE rechargehistory (Id INTEGER PRIMARY KEY  , reccustid TEXT ,reccustname TEXT,recbankname TEXT,recbankifsccode TEXT,recaccno TEXT,amount TEXT,time TEXT);");
		db.execSQL("CREATE TABLE twitterbeneficiary (Id INTEGER PRIMARY KEY  , benefname TEXT,screenname TEXT ,twitterid TEXT,time TEXT);");
		db.execSQL("CREATE TABLE twittertransferhistory (Id INTEGER PRIMARY KEY  , benefname TEXT,screenname TEXT ,twitterid TEXT,recammount,time TEXT);");
		db.execSQL("CREATE TABLE loantypedetail (Id INTEGER PRIMARY KEY AUTOINCREMENT  , loantypeid TEXT,loan_type_name TEXT ,limit_formule TEXT,intrest TEXT,tenure TEXT,own_reserve TEXT,admin_fee TEXT);");
		System.out.println("HEllo user table created");
	}

	/**
	 * Recreates table
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		// DROP table
		db.execSQL("DROP TABLE IF EXISTS customerlogin");
		db.execSQL("DROP TABLE IF EXISTS benficiarytable");
		db.execSQL("DROP TABLE IF EXISTS transferhistory");
		db.execSQL("DROP TABLE IF EXISTS rechargehistory");
		db.execSQL("DROP TABLE IF EXISTS twitterbeneficiary");
		db.execSQL("DROP TABLE IF EXISTS twittertransferhistory");
		db.execSQL("DROP TABLE IF EXISTS loantypedetail");

		// Recreate table
		onCreate(db);
	}

}
