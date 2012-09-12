package com.acedanger.viewpager.support;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "sample.db";
	private static final int DB_VERSION = 13;
	private static DbHelper mInstance;
	public SQLiteDatabase db;
	private Context context = null;
	
	// history table
	public static final String TABLE_HISTORY = "history";
	public static final String C_HS_ID = BaseColumns._ID;
	public static final String C_HS_CREATE_TIME = "create_time";
	public static final String C_HS_DESCRIPTION = "wod";
	public static final String C_HS_DATE = "date";
	public static final String C_HS_TIME = "time";
	public static final String C_HS_RX = "rx";
	public static final String C_HS_RESULTS = "results";
	public static final String C_HS_PR = "pr";
	public static final String C_HS_NOTES = "notes";
	public static final String C_WEB_SYNC_TIME = "web_sync_time";
	
	static final String[] FROM_ALL = { C_HS_ID, C_HS_CREATE_TIME,
		C_HS_DESCRIPTION, C_HS_DATE, C_HS_TIME, C_HS_RESULTS, C_HS_RX, 
		C_HS_PR, C_HS_NOTES };
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
		this.db = getWritableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		createHistoryTable(db);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public static DbHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DbHelper(context.getApplicationContext());
		}
		return mInstance;
	}
	
	private void createHistoryTable(SQLiteDatabase db) {
		dropTable(db, TABLE_HISTORY);
		String sql = "create table if not exists " + TABLE_HISTORY + " ("
				+ C_HS_ID + " integer primary key autoincrement, "
				+ C_HS_CREATE_TIME + " integer, " 
				+ C_HS_DESCRIPTION + " text, "
				+ C_HS_DATE + " integer, " 
				+ C_HS_TIME + " integer, "
				+ C_HS_RESULTS + " text, " 
				+ C_HS_RX + " text, " 
				+ C_HS_PR + " text, " 
				+ C_HS_NOTES + " text, " 
				+ C_WEB_SYNC_TIME + " text);";
		try {
			db.execSQL(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void dropTable(SQLiteDatabase db, String tableName) {
		db.execSQL("drop table if exists " + tableName);
	}
	
	public ArrayList<History> getHistory(String sortOrder) {
		String sortString = null;
		if (sortOrder.equalsIgnoreCase(Constants.SORT_BY_DATE)) {
			sortString = "substr(" + C_HS_DATE + ", 7, 4)||substr(" + C_HS_DATE
					+ ", 1, 2)||substr(" + C_HS_DATE + ", 4, 2) desc, "
					+ C_HS_TIME + " desc";
		} else if (sortOrder.equalsIgnoreCase(Constants.SORT_BY_DESCRIPTION)) {
			sortString = C_HS_DESCRIPTION + " asc";
		}

		ArrayList<History> wodHistory = new ArrayList<History>();

		Cursor cursor = db.query(TABLE_HISTORY, FROM_ALL, null, null, null, null, 
				sortString);
		if (cursor.moveToFirst()) {
			do {
				History historyItem = new History();
				historyItem.setDescription(cursor.getString(cursor.getColumnIndex(C_HS_DESCRIPTION)));
				historyItem.setDate(cursor.getString(cursor.getColumnIndex(C_HS_DATE)));
				historyItem.setTime(cursor.getString(cursor.getColumnIndex(C_HS_TIME)));
				historyItem.setResults(cursor.getString(cursor.getColumnIndex(C_HS_RESULTS)));
				historyItem.setRx(cursor.getString(cursor.getColumnIndex(C_HS_RX)));
				historyItem.setPr(cursor.getString(cursor.getColumnIndex(C_HS_PR)));
				historyItem.setNotes(cursor.getString(cursor.getColumnIndex(C_HS_NOTES)));
				historyItem.setId(cursor.getString(cursor.getColumnIndex(C_HS_ID)));

				wodHistory.add(historyItem);
			} while (cursor.moveToNext());
		}

		closeCursor(cursor);

		return wodHistory;
	}
	
	public ArrayList<History> getHistoryByMonth(String year, String month, String sortOrder) {
		String sortString = null;
		if (sortOrder.equalsIgnoreCase(Constants.SORT_BY_DATE)) {
			sortString = "substr(" + C_HS_DATE + ", 7, 4)||substr(" + C_HS_DATE
					+ ", 1, 2)||substr(" + C_HS_DATE + ", 4, 2) desc, "
					+ C_HS_TIME + " desc";
		} else if (sortOrder.equalsIgnoreCase(Constants.SORT_BY_DESCRIPTION)) {
			sortString = C_HS_DESCRIPTION + " asc";
		}

		ArrayList<History> wodHistory = new ArrayList<History>();

		Cursor cursor = db.query(TABLE_HISTORY, FROM_ALL, 
				"substr("+C_HS_DATE+",7,4) = ? and substr("+C_HS_DATE+",1,2) = ?", 
				new String[] { year, month}, null, null, sortString);
		if (cursor.moveToFirst()) {
			do {
				History historyItem = new History();
				historyItem.setDescription(cursor.getString(cursor.getColumnIndex(C_HS_DESCRIPTION)));
				historyItem.setDate(cursor.getString(cursor.getColumnIndex(C_HS_DATE)));
				historyItem.setTime(cursor.getString(cursor.getColumnIndex(C_HS_TIME)));
				historyItem.setResults(cursor.getString(cursor.getColumnIndex(C_HS_RESULTS)));
				historyItem.setRx(cursor.getString(cursor.getColumnIndex(C_HS_RX)));
				historyItem.setPr(cursor.getString(cursor.getColumnIndex(C_HS_PR)));
				historyItem.setNotes(cursor.getString(cursor.getColumnIndex(C_HS_NOTES)));
				historyItem.setId(cursor.getString(cursor.getColumnIndex(C_HS_ID)));

				wodHistory.add(historyItem);
			} while (cursor.moveToNext());
		}

		closeCursor(cursor);

		return wodHistory;
	}
	
	public ArrayList<DataSummary> getDataSummary(String table) {
		ArrayList<DataSummary> summary = new ArrayList<DataSummary>();
		String groupBy = "substr(" + C_HS_DATE + ",7,4)," + "substr(" + C_HS_DATE + ",1,2)";
		String orderBy = "substr(" + C_HS_DATE + ",7,4)||substr(" + C_HS_DATE + ",1,2) desc";
		String sql = 
				"select "+ groupBy + "," + "count(*) " +
				"from " + table + " " +
				"group by " + groupBy + " " +
				"order by " + orderBy;
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				DataSummary sumItem = new DataSummary();
				sumItem.setYear(cursor.getString(0));
				sumItem.setMonth(cursor.getString(1));
				sumItem.setCount(cursor.getString(2));
				summary.add(sumItem);
			} while (cursor.moveToNext());
		}
		closeCursor(cursor);
		return summary;
	}
	
	private void closeCursor(Cursor cursor) {
		if (cursor != null) {
			cursor.close();
		}
	}
}