package de.failbots.db.exam.ui;

import de.failbots.db.exam.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity {
	String textFromIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
		if(getIntent().getExtras() != null) {
			textFromIntent = getIntent().getStringExtra("EXPLICIT_EXTRA");
		}
			
		Fragment fragment = new FragmentTest();
		FragmentManager fm = getFragmentManager();		
		FragmentTransaction fmt = fm.beginTransaction();
		fmt.add(R.id.fragmentPlaceholder, fragment);
		fmt.commit();

		Button button = (Button)findViewById(R.id.ChangeText);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
	    		TextView tv = (TextView) findViewById(R.id.fragment_text);
	    		tv.setText(textFromIntent);	    		
				
			}
		});
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
}
