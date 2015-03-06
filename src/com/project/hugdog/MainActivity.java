package com.project.hugdog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class MainActivity extends Activity {

	SQLiteDatabase db;
	DBClass myDB = new DBClass(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		boolean checkDB;
		myDB.getWritableDatabase();
		checkDB = myDB.chkDB();
		if(checkDB == false){
			showDialogBox();
		}
		else{
			setContentView(R.layout.activity_main);
		}
		
		/*
		File directory = new File("/sdcard/Hugdog");
		File[] contents = directory.listFiles();
		// the directory file is not really a directory..
		if (contents == null) {
			showDialogBox();
			//setContentView(R.layout.activity_add);
		}
		// Folder is empty
		else if (contents.length == 0) {
			//setContentView(R.layout.activity_add);
		}
		// Folder contains files
		else {
			setContentView(R.layout.activity_main);
		}
*/	
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
		 * Intent i = new Intent(this, AlertActivity.class); startActivity(i);
		 */

		Intent calendarIntent = new Intent(Intent.ACTION_INSERT,
				Events.CONTENT_URI);
		// Calendar beginTime = Calendar.getInstance().set(2012, 0, 19, 7, 30);
		// Calendar endTime = Calendar.getInstance().set(2012, 0, 19, 10, 30);
		// calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
		// beginTime.getTimeInMillis());
		// calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
		// endTime.getTimeInMillis());
		// calendarIntent.putExtra(Events.TITLE, "Ninja class");
		// calendarIntent.putExtra(Events.EVENT_LOCATION, "Secret dojo");
		startActivity(calendarIntent);

	}

	public void btnDogClicked(View v) {
		Intent i = new Intent(this, DogActivity.class);
		startActivity(i);

	}
	
	public void btnHistoryClicked(View v) {
		Intent i = new Intent(this, HisListActivity.class);
		//Intent calendarIntent = new Intent(Intent.ACTION_INSERT,Events.CONTENT_URI);
		startActivity(i);
		/*
		 * Intent i = new Intent(this, HistoryActivity.class); startActivity(i);
		 */
	}

	public void btnTipClicked(View v) {
		Intent i = new Intent(this, TipActivity.class);
		startActivity(i);

	}

	public void btnVaccineClicked(View v) {

	//	disp();
		
	 Intent i = new Intent(this, VacCalActivity.class); startActivity(i);
		 

	}
	public void btnDBM(View v){
		
		
		      Intent dbmanager = new Intent(this,AndroidDatabaseManager.class);
					startActivity(dbmanager);
		        
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

	// /end datepick

	public void disp() {

		Calendar c = Calendar.getInstance();

		// vaccineDay.set(, nextWeek.getMonth(), nextWeek.getDay());
		// Toast.makeText(this, c.toString(), Toast.LENGTH_LONG).show();

		Uri uri = Uri.parse("content://com.android.calendar/time/"
				+ String.valueOf(c.getTimeInMillis()));
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		// Use the Calendar app to view the time.
		startActivity(intent);

	}
	// dialog box
	private void showDialogBox() {
		new AlertDialog.Builder(this)
				.setTitle("ยังไม่มีสัตว์เลี้ยง")
				.setMessage("เพิ่มสัตว์เลี้ยง")
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent i = new Intent(MainActivity.this, AddActivity.class);
								startActivity(i);
								dialog.dismiss();
							}
						})
				
				.setIcon(android.R.drawable.ic_dialog_info).show();
	
		  
	}
	
	
	
}
