package com.project.hugdog;



import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void btnAddClicked(View v) {
    	Intent i = new Intent(this, AddActivity.class);
    	startActivity(i);
    	
		
	}
	public void btnAlertClicked(View v) {
    	Intent i = new Intent(this, AlertActivity.class);
    	startActivity(i);
    	
		
	}
	public void btnDogClicked(View v) {
    	Intent i = new Intent(this, DogActivity.class);
    	startActivity(i);
    	
		
	}
	public void btnHistoryClicked(View v) {
    	Intent i = new Intent(this, HistoryActivity.class);
    	startActivity(i);
    	
		
	}
	public void btnTipClicked(View v) {
    	Intent i = new Intent(this, TipActivity.class);
    	startActivity(i);
    	
		
	}
	public void btnVaccineClicked(View v) {
    	Intent i = new Intent(this, VaccineActivity.class);
    	startActivity(i);
    	
		
	}
}
