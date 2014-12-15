package com.project.hugdog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TipActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);
		
	}
	
	public void tip1Clicked(View v) {
		Intent i = new Intent(getBaseContext(),DetailTipActivity.class);
		i.putExtra("tip","1");
		
    	startActivity(i);
    	;
    	
		
	}
	public void tip2Clicked(View v) {
		Intent i = new Intent(getBaseContext(),DetailTipActivity.class);
		i.putExtra("tip","2");
		
    	startActivity(i);
    	;
    	
		
	}
}
