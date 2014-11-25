package com.project.hugdog;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.location.GpsStatus.Listener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

public class AlertActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alert);
		
		final CalendarView calendarView = ( CalendarView ) AlertActivity.this.findViewById ( R.id.calendarView );
		
		calendarView.setOnDateChangeListener(new OnDateChangeListener() {
						
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				long date;
				date = calendarView.getDate();
				Toast.makeText(view.getContext(), "Year=" + year + " Month=" + (month+1) + " Day=" + dayOfMonth, Toast.LENGTH_LONG).show();
			}
		});
			
			
	}
	
}
