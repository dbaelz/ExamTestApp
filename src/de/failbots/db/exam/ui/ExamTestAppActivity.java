package de.failbots.db.exam.ui;

import java.util.List;

import org.apache.http.protocol.HTTP;

import de.failbots.db.exam.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class ExamTestAppActivity extends Activity {
	
	private Fragment fragment;
	private FragmentManager fm;
	private ShareActionProvider shareActionProvider;
	
	private final String[] receiver = { "examTestApp@failbots.de" };
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        fm = getFragmentManager();
		fragment = new FragmentTest();

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();    
    	inflater.inflate(R.menu.mainmenu, menu);
    	MenuItem shareItem = menu.findItem(R.id.menu_share);
    	shareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
    	shareActionProvider.setShareIntent(shareIntent());
    	return true;
    }
    
    private Intent shareIntent() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType(HTTP.PLAIN_TEXT_TYPE);
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ShareIntent: Send from my ExamTestApp");
		shareIntent.putExtra(Intent.EXTRA_EMAIL, receiver);
		shareIntent.putExtra(Intent.EXTRA_TEXT, "This is just a test");
	
		return shareIntent;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.menu_fragment:
			if(fm.findFragmentById(R.id.fragmentPlaceholder) == null) {
				FragmentTransaction fmt = fm.beginTransaction();
		        fmt.add(R.id.fragmentPlaceholder, fragment);
		        fmt.commit();
			} else {
				FragmentTransaction fmt = fm.beginTransaction();
				fmt.remove(fragment);
				fmt.commit();
			}			
			return true;
		case R.id.menu_loader:
			Intent intentLoader = new Intent(this, LoaderListActivity.class);
			startActivity(intentLoader);
			return true;
		case R.id.menu_explicit_intent:
			Intent intent = new Intent(this, SecondActivity.class);
			intent.putExtra("EXPLICIT_EXTRA", "Activity Extra String");
			startActivity(intent);
			return true;
		case R.id.menu_implicit_intent:
			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Intent: Send from my ExamTestApp");
			emailIntent.putExtra(Intent.EXTRA_EMAIL, receiver);
			emailIntent.putExtra(Intent.EXTRA_TEXT, "This is just a test");

			PackageManager pm = getPackageManager();
			List<ResolveInfo> activities = pm.queryIntentActivities(emailIntent, 0);
			if(activities.size() > 0) {				
				startActivity(Intent.createChooser(emailIntent, "Share this information:"));
			} else {
				Toast.makeText(this, "No App to share this content available", Toast.LENGTH_SHORT).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    	
    }
}