package de.failbots.db.exam.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ExamTable {

	public static final String TABLE_EXAM = "exam";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TEXT = "text";

	private static final String DATABASE_CREATE = "create table " 
		+ TABLE_EXAM
		+ "(" 
		+ COLUMN_ID + " integer primary key autoincrement, " 
		+ COLUMN_TEXT + " text not null " 
		+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(ExamTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAM);
		onCreate(database);
	}
}
