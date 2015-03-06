package com.project.hugdog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TipActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);

	}

	public void tip1Clicked(View v) {
		Intent i = new Intent(getBaseContext(), DetailTipActivity.class);
		i.putExtra("tip", "1");
		startActivity(i);
		;

	}

	public void tip2Clicked(View v) {
		Intent i = new Intent(getBaseContext(), DetailTipActivity.class);
		i.putExtra("tip", "2");
		startActivity(i);
		;

	}
	public void tip3Clicked(View v) {
		Intent i = new Intent(getBaseContext(), DetailTipActivity.class);
		i.putExtra("tip", "3");
		startActivity(i);
		;

	}
	public void tip4Clicked(View v) {
		Intent i = new Intent(getBaseContext(), DetailTipActivity.class);
		i.putExtra("tip", "4");
		startActivity(i);
		;

	}
	public void tip5Clicked(View v) {
		Intent i = new Intent(getBaseContext(), DetailTipActivity.class);
		i.putExtra("tip", "5");
		startActivity(i);
		;

	}
}
