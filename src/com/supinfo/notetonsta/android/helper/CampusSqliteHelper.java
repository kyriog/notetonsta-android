package com.supinfo.notetonsta.android.helper;

import java.util.ArrayList;
import java.util.List;

import com.supinfo.notetonsta.android.entity.Campus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CampusSqliteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "notetonsta.db";
	private static final int DATABASE_VERSION = 2;
	public static final String TABLE_NAME = "campus";
	private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_NAME + " ( " +
			"id INTEGER PRIMARY KEY, " +
			"name TEXT NOT NULL);";

	public CampusSqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public static void clearCampus(SQLiteDatabase db) {
		db.execSQL("DELETE FROM "+TABLE_NAME);
	}

	public void clearCampus() {
		SQLiteDatabase db = getWritableDatabase();
		clearCampus(db);
		db.close();
	}
	
	public static void insertCampus(Campus c, SQLiteDatabase db) {
		ContentValues values = new ContentValues();
		values.put("id", c.getId());
		values.put("name", c.getName());
		db.insert(TABLE_NAME, null, values);
	}
	
	public void insertCampus(Campus c) {
		SQLiteDatabase db = getWritableDatabase();
		insertCampus(c, db);
		db.close();
	}
	
	public static void insertListOfCampus(List<Campus> lc, SQLiteDatabase db) {
		for(Campus c : lc) {
			insertCampus(c, db);
		}
	}
	
	public void insertListOfCampus(List<Campus> lc) {
		SQLiteDatabase db = getWritableDatabase();
		insertListOfCampus(lc, db);
		db.close();
	}
	
	public static ArrayList<Campus> listAllCampus(SQLiteDatabase db) {
		ArrayList<Campus> campusList = new ArrayList<Campus>();
		Campus campus;
		
		Cursor results = db.query(TABLE_NAME, null, null, null, null, null, "name");
		results.moveToFirst();
    	while(!results.isAfterLast()) {
    		campus = new Campus();
    		campus.setId(results.getLong(0));
    		campus.setName(results.getString(1));
    		
    		campusList.add(campus);
    		
    		results.moveToNext();
    	}
    	results.close();
    	
    	return campusList;
	}
	
	public ArrayList<Campus> listAllCampus() {
		SQLiteDatabase db = getReadableDatabase();
		ArrayList<Campus> campusList = listAllCampus(db);
		db.close();
		return campusList;
	}
}
