package de.failbots.db.exam.ui;

import de.failbots.db.exam.R;
import de.failbots.db.exam.cp.ExamContentProvider;
import android.app.ActionBar;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

public class LoaderListActivity extends ListActivity implements 
				LoaderManager.LoaderCallbacks<Cursor>{

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SUMMARY = "summary";
	
	private SimpleCursorAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		this.getListView().setDividerHeight(2);
		fillList();
		registerForContextMenu(getListView());
	}
	
	private void fillList() {
		String[] from = new String[] { "AAA", "BBB", "CCC" };
		int[] to = new int[] { R.id.label };

		getLoaderManager().initLoader(0, null, this);
		adapter = new SimpleCursorAdapter(this, R.layout.list_row, null, from, to, 0);
		setListAdapter(adapter);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        case android.R.id.home:
            Intent intent = new Intent(this, ExamTestAppActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { LoaderListActivity.COLUMN_ID, LoaderListActivity.COLUMN_SUMMARY };
		CursorLoader cursorLoader = new CursorLoader(this,
				ExamContentProvider.CONTENT_URI, projection, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		adapter.swapCursor(null);
	}

}
