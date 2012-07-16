package de.failbots.db.exam.data;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ExamContentProvider extends ContentProvider {

	private ExamDatabaseHelper database;

	private static final int EXAMS = 10;
	private static final int EXAM_ID = 20;
	private static final String AUTHORITY = "de.failbots.db.exam.data";
	private static final String BASE_PATH = "exam";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/exam";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/exam";

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH, EXAMS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", EXAM_ID);
	}

	@Override
	public boolean onCreate() {
		database = new ExamDatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		checkColumns(projection);
		queryBuilder.setTables(ExamTable.TABLE_EXAM);
		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case EXAMS:
			break;
		case EXAM_ID:
			queryBuilder.appendWhere(ExamTable.COLUMN_ID + "=" + uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("ERROR: " + uri);
		}
		SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		long id = 0;
		switch (uriType) {
		case EXAMS:
			id = sqlDB.insert(ExamTable.TABLE_EXAM, null, values);
			break;
		default:
			throw new IllegalArgumentException("ERROR: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case EXAMS:
			rowsDeleted = sqlDB.delete(ExamTable.TABLE_EXAM, selection, selectionArgs);
			break;
		case EXAM_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(ExamTable.TABLE_EXAM, ExamTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(ExamTable.TABLE_EXAM, ExamTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("ERROR: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsUpdated = 0;
		switch (uriType) {
		case EXAMS:
			rowsUpdated = sqlDB.update(ExamTable.TABLE_EXAM, values, selection, selectionArgs);
			break;
		case EXAM_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(ExamTable.TABLE_EXAM, values, ExamTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(ExamTable.TABLE_EXAM, values, ExamTable.COLUMN_ID + "=" + id  + " and " + selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("ERROR: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	private void checkColumns(String[] projection) {
		String[] available = { ExamTable.COLUMN_ID, ExamTable.COLUMN_TEXT };
		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("Column unknown");
			}
		}
	}

}
