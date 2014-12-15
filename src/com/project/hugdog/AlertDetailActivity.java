package com.project.hugdog;

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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;



public class AlertDetailActivity extends Activity {
	// The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;
	final static int RQS_1 = 1;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alert_detail);

		TextView txtDate = (TextView) findViewById(R.id.txtDate);
		EditText editTitle = (EditText) findViewById(R.id.editTitle);
		EditText editPlace = (EditText) findViewById(R.id.editPlace);
		
		Bundle b = getIntent().getExtras();
		int month = b.getInt("month");
		int day = b.getInt("day");
		int year = b.getInt("year");

		txtDate.setText(Integer.toString(day) + "/" + Integer.toString(month)
				+ "/" + Integer.toString(year));

	}

	public static class TimePickerFragment extends DialogFragment implements
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
			TextView txtTime = (TextView) getActivity().getWindow().getDecorView().getRootView().findViewById(R.id.txtTime);
			
			txtTime.setText(hourOfDay+":"+minute);
			//SampleAlarmReceiver.setAlarm();
			
			
			
			
			
		}
		
	}

	public void txtTimeClicked(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
		
	}
	
	public void setAlarm(Calendar targetCal){

		/*textAlarmPrompt.setText(
				"\n\n***\n"
				+ "Set Alarm @ " + targetCal.getTime() + "\n"
				+ "***\n");
		*/
		
		Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
		
	}
	///
	
	
}
