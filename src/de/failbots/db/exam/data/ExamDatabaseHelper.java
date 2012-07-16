package de.failbots.db.exam.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExamDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "exam.db";
	private static final int DATABASE_VERSION = 1;

	public ExamDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		ExamTable.onCreate(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		ExamTable.onUpgrade(database, oldVersion, newVersion);
	}

}
