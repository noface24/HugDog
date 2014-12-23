package com.project.hugdog;

import java.util.Calendar;



import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

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
		/*
		Intent i = new Intent(this, AlertActivity.class);
		startActivity(i);
		*/
	
		Intent calendarIntent = new Intent(Intent.ACTION_INSERT, Events.CONTENT_URI);
		//Calendar beginTime = Calendar.getInstance().set(2012, 0, 19, 7, 30);
		//Calendar endTime = Calendar.getInstance().set(2012, 0, 19, 10, 30);
		//calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
		//calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
		//calendarIntent.putExtra(Events.TITLE, "Ninja class");
		//calendarIntent.putExtra(Events.EVENT_LOCATION, "Secret dojo");
		startActivity(calendarIntent);
		
	}

	public void btnDogClicked(View v) {
		Intent i = new Intent(this, DogActivity.class);
		startActivity(i);

	}

	public void btnHistoryClicked(View v) {
		Intent calendarIntent = new Intent(Intent.ACTION_INSERT, Events.CONTENT_URI);
		startActivity(calendarIntent);
		/*Intent i = new Intent(this, HistoryActivity.class);
		startActivity(i);
		*/
	}

	public void btnTipClicked(View v) {
		Intent i = new Intent(this, TipActivity.class);
		startActivity(i);

	}

	public void btnVaccineClicked(View v) {
		Intent i = new Intent(this, VaccineActivity.class);
		startActivity(i);

	}
	// datepick
		public static class DatePickerFragment extends DialogFragment implements
				DatePickerDialog.OnDateSetListener {

			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				// Use the current date as the default date in the picker
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);

				/*
				 * Toast.makeText(getActivity(), month, Toast.LENGTH_LONG) .show();
				 */

				// Create a new instance of DatePickerDialog and return it

				return new DatePickerDialog(getActivity(), this, year, month, day);
			}

			public void onDateSet(DatePicker view, int year, int month, int day) {
				// Do something with the date chosen by the user
				
				

			}
		}

		public void showDatePickerDialog(View v) {
			DialogFragment newFragment = new DatePickerFragment();
			newFragment.show(getFragmentManager(), "datePicker");
		}
		
		///end datepick
}
