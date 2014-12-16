package com.project.hugdog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

public class AlertActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alert);

		final CalendarView calendarView = (CalendarView) AlertActivity.this
				.findViewById(R.id.calendarView);

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
				Intent i = new Intent(getBaseContext(),
						AlertDetailActivity.class);
				i.putExtra("month", month);
				i.putExtra("year", year);
				i.putExtra("day", dayOfMonth);
				i.putExtra("month", month);
				i.putExtra("year", year);
				i.putExtra("day", dayOfMonth);
				startActivity(i);
			}
		});

	}

}
