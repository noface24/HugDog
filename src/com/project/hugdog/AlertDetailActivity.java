package com.project.hugdog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;



import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlertDetailActivity extends Activity {
	// The app's AlarmManager, which provides access to the system alarm
	// services.
	private AlarmManager alarmMgr;
	// The pending intent that is triggered when the alarm fires.
	private PendingIntent alarmIntent;
	final static int RQS_2 = 2;
	private static int month;
	private static int day;
	private static int year;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alert_detail);

		TextView txtDate = (TextView) findViewById(R.id.txtDate);
		EditText editTitle = (EditText) findViewById(R.id.editTitle);
		EditText editPlace = (EditText) findViewById(R.id.editPlace);

		Bundle b = getIntent().getExtras();
		month = b.getInt("month");
		day = b.getInt("day");
		year = b.getInt("year");
		
		final ImageButton btnSave = (ImageButton) findViewById(R.id.btnSave);

		txtDate.setText(Integer.toString(day) + "/" + Integer.toString(month)
				+ "/" + Integer.toString(year));
		
		// Perform action on click
					btnSave.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
								finish();

						}
					});
					// end save button

	}
	
	

	public class TimePickerFragment extends DialogFragment implements 
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			TextView txtTime = (TextView) getActivity().getWindow()
					.getDecorView().getRootView().findViewById(R.id.txtTime);

			txtTime.setText(hourOfDay + ":" + minute);
			// SampleAlarmReceiver.setAlarm();
			////set alert 
			
			Calendar calNow = Calendar.getInstance();
			Calendar calSet = (Calendar) calNow.clone();
			calSet.set(Calendar.MONTH,month-1);
			calSet.set(Calendar.DAY_OF_MONTH,day);
			calSet.set(Calendar.YEAR,year);
			calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calSet.set(Calendar.MINUTE, minute);
			calSet.set(Calendar.SECOND, 0);
			calSet.set(Calendar.MILLISECOND, 0);
			/*
			Toast.makeText(
					view.getContext(),
					calSet.toString(), Toast.LENGTH_LONG).show();
			/*
			Toast.makeText(
					view.getContext(),
					"Year=" + year + " Month=" + (month) + " Day="
							+ day, Toast.LENGTH_LONG).show();
			*/
			///end set alert
			setAlarm(calSet);
		}
		public void setAlarm(Calendar targetCal) {

			/*
			 * textAlarmPrompt.setText( "\n\n***\n" + "Set Alarm @ " +
			 * targetCal.getTime() + "\n" + "***\n");
			 */
			
			Toast.makeText(getBaseContext(),
					String.valueOf(targetCal.getTimeInMillis()), Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_2, intent, 0);
			AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
			//alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ (2 * 1000), pendingIntent);
			alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

		}

	}

	public void txtTimeClicked(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");

	}
///////Time
	
	

}
