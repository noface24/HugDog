package com.project.hugdog;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class VaccineActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vaccine);
		
		Button disp = (Button) findViewById(R.id.dispbut);
        disp.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                disp();
            }
        });
		
		
		
		
		
		
		
		
		/*
		final CalendarView calendarView = (CalendarView) VaccineActivity.this
				.findViewById(R.id.calendarView1);

		calendarView.setOnDateChangeListener(new OnDateChangeListener() {

			public void onSelectedDayChange(CalendarView view, int year,
					int month, int dayOfMonth) {
				// TODO Auto-generated method stub
				long date;
				date = calendarView.getDate();
				month = month + 1;
				Toast.makeText(
						view.getContext(),
						"Year=" + year + " Month=" + (month) + " Day="
								+ dayOfMonth, Toast.LENGTH_LONG).show();
			/*
				Intent i = new Intent(getBaseContext(),
						AlertDetailActivity.class);
				i.putExtra("month", month);
				i.putExtra("year", year);
				i.putExtra("day", dayOfMonth);
				startActivity(i);
				
			}
		});
		*/
	}
	
	public void disp() {
		          
		
		Calendar c = Calendar.getInstance();		
		
		//vaccineDay.set(, nextWeek.getMonth(), nextWeek.getDay());
		Toast.makeText(this, c.toString(), Toast.LENGTH_LONG).show();
		
        Uri uri = Uri.parse("content://com.android.calendar/time/"
                + String.valueOf(c.getTimeInMillis()));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        // Use the Calendar app to view the time.
        startActivity(intent);
        
    }
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	
}
