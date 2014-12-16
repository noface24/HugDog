package com.project.hugdog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;

public class HistoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		final CalendarView calendarView = (CalendarView) HistoryActivity.this
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
				*/
			}
		});
	}
}
