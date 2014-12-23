package com.project.hugdog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class DetailTipActivity extends Activity {
	private String tipNumber;
	private ImageView imgTip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_tip);
		tipNumber = getIntent().getStringExtra("tip");
		imgTip = (ImageView) findViewById(R.id.imgTip);
		chooseTip();
		/*Toast.makeText(getApplicationContext(), tipNumber, Toast.LENGTH_LONG)
				.show();
				*/
	}

	private void chooseTip() {
		if (tipNumber.equals("1")) {
			imgTip.setImageResource(R.drawable.nosebleed);
		} else if (tipNumber.equals("2")) {
			imgTip.setImageResource(R.drawable.feed);
		}
		else if (tipNumber.equals("3")) {
			imgTip.setImageResource(R.drawable.dont);
		}
	}

}
