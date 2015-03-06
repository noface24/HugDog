package com.project.hugdog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity {
	private TextView txtAge;
	private static AgeCalculation age = null;
	private RadioGroup radioGenderGroup;
	private String gender, dogName;
	private static Calendar birthDay;
	SQLiteDatabase db;
	DBClass myDB = new DBClass(this);
	static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	int dogID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		final EditText txtName = (EditText) findViewById(R.id.editName);
		final EditText txtColor = (EditText) findViewById(R.id.editColor);
		final AutoCompleteTextView txtBreed = (AutoCompleteTextView) findViewById(R.id.editBreed);
		final EditText txtWeight = (EditText) findViewById(R.id.editWeight);
		final TextView txtBirth = (TextView) findViewById(R.id.txtBirthday);
		birthDay = Calendar.getInstance();
		
		final ImageButton btnSave = (ImageButton) findViewById(R.id.btnSave);
		age = new AgeCalculation();
		// birthDay.getInstance();
		radioGenderGroup = (RadioGroup) findViewById(R.id.radioGenderGroup);
		radioGenderGroup.check(R.id.btnMale);
		gender = "µ—«ºŸÈ";

		// Auto Complete
		// Get a reference to the AutoCompleteTextView in the layout
		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.editBreed);
		// Get the string array
		String[] breeds = getResources().getStringArray(R.array.breed_array);
		// Create the adapter and set it to the AutoCompleteTextView
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, breeds);
		textView.setAdapter(adapter);
		// endAuto Complete
		// Create txtfile
		dogID = myDB.getNextID("dog");
		//Toast.makeText(getBaseContext(), String.valueOf(dogID),Toast.LENGTH_SHORT).show();

		// Perform action on click

		btnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				btnSave.setColorFilter(Color.argb(80, 80, 80, 80));
				String ed_name = txtName.getText().toString().trim();
				String ed_birth = df.format(birthDay.getTime());//txtBirth.getText().toString().trim();
				String ed_color = txtColor.getText().toString().trim();
				String ed_breed = txtBreed.getText().toString().trim();
				String ed_weight = txtWeight.getText().toString().trim();
				dogName = txtName.getText().toString();
				if (ed_name.isEmpty()) {
					showDialogBox("°√Õ°™◊ËÕ ÿπ—¢");
				} else if (ed_birth.isEmpty()) {
					showDialogBox("‡≈◊Õ°«—π‡°‘¥");
				} else if (ed_color.isEmpty()) {
					showDialogBox("°√Õ° ’");
				} else if (ed_weight.isEmpty()) {
					showDialogBox("°√Õ°πÈ”Àπ—°");
				} else if (ed_breed.isEmpty()) {
					showDialogBox("°√Õ°æ—π∏ÿÏ");
				}
				// create a File object for the parent directory

				/*** Write Text File to SD Card ***/
				else {
					long flg1 = myDB.InsertDog(dogID,ed_name, ed_color, ed_breed, ed_weight, gender, ed_birth);
					if(flg1>0){
						finish();
						Toast.makeText(
								AddActivity.this,
								"‡æ‘Ë¡¢ÈÕ¡Ÿ≈ " + txtName.getText()
										+ " ·≈È«", Toast.LENGTH_LONG)
								.show();
						setVaccine(birthDay);
					}
					
					
				
					/*try {
						// String txtFileName = txtName.getText().toString() +
						// ".txt";
						File dr = new File("/sdcard/Hugdog/");
						dr.mkdirs();
						String path = txtName.getText().toString() + ".txt";
						File file = new File(dr, path);

						// FileOutputStream outputStream;
						*//*** if exists create text file ***//*
						// file.createNewFile();

						if (!file.exists()) {
							file.createNewFile();
							FileOutputStream fOut = new FileOutputStream(file);
							OutputStreamWriter outWrite = new OutputStreamWriter(
									fOut);

							String sss = txtName.getText() + "\n" + gender
									+ "\n" + txtBirth.getText() + "\n"
									+ txtColor.getText() + "\n"
									+ txtBreed.getText() + "\n"
									+ txtWeight.getText() + "\n";

							outWrite.append(sss);
							outWrite.close();
							fOut.close();

							*//*** Write Text File ***//*
							
							 * Toast.makeText(AddActivity.this,
							 * birthDay.toString(), Toast.LENGTH_LONG).show();
							 
							dogName = txtName.getText().toString();
							setVaccine(birthDay);

							finish();
							Toast.makeText(
									AddActivity.this,
									"‡æ‘Ë¡¢ÈÕ¡Ÿ≈ " + txtName.getText()
											+ " ·≈È«", Toast.LENGTH_LONG)
									.show();
						} else {
							Toast.makeText(
									AddActivity.this,
									"¡’ —µ«Ï‡≈’È¬ß™◊ËÕ " + txtName.getText()
											+ " ·≈È«", Toast.LENGTH_LONG)
									.show();
						}

						
						 * Toast.makeText(AddActivity.this, "File Save!" +
						 * getFilesDir(), Toast.LENGTH_LONG) .show();
						 

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(AddActivity.this,
								"Failed!" + e.getMessage(), Toast.LENGTH_LONG)
								.show();
					}*/
				}
			}
		});

		// end save button

	}

	// Select gender
	public void onRadioButtonClicked(View view) {

		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.btnMale:
			if (checked)
				gender = "µ—«ºŸÈ";// Pirates are the best
			break;
		case R.id.btnFemale:
			if (checked)
				gender = "µ—«‡¡’¬";// Ninjas rule
			break;
		}
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
			/*
			 * Calendar cal = Calendar.getInstance(); // String age =
			 * getAge(year,month,day); int ageYear = 0; int ageMonth = 0; int
			 * ageDay = 0;
			 */
			
			AddActivity.birthDay.set(Calendar.DAY_OF_MONTH, day);
			AddActivity.birthDay.set(Calendar.MONTH, month);
			AddActivity.birthDay.set(Calendar.YEAR, year);
			
			
			// ////age////
			age.getCurrentDate();
			age.setDateOfBirth(year, month, day);
			age.calcualteYear();
			age.calcualteMonth();
			age.calcualteDay();// cal.get(Calendar.DAY_OF_MONTH)-day;

			// //end age/////

			String resultAge = age.getResult();

			TextView txtAge = (TextView) getActivity().getWindow()
					.getDecorView().getRootView().findViewById(R.id.txtAge);
			TextView txtBirth = (TextView) getActivity().getWindow()
					.getDecorView().getRootView()
					.findViewById(R.id.txtBirthday);
			//Toast.makeText(AddActivity.this, df.format(birthDay.getTime()),
			//		 Toast.LENGTH_LONG).show();
			txtBirth.setText(df.format(birthDay.getTime()));
			//txtBirth.setText("«—π∑’Ë " + day + " ‡¥◊Õπ " + (month + 1) + " ª’ "
			//		+ year);

			txtAge.setText(resultAge); // ‡¥◊Õπ "+ageDay +" «— π");

		}
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	// end datepick

	public void onBackPressed() {
		// do something here and don't write super.onBackPressed()
		// android.os.Process.killProcess(android.os.Process.myPid());
		finish();
	}

	// dialog box
	private void showDialogBox(String msg) {
		new AlertDialog.Builder(this)
				.setTitle("„ Ë¢ÈÕ¡Ÿ≈‰¡Ë§√∫")
				.setMessage(msg)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								dialog.dismiss();
							}
						})
				/*
				 * .setNegativeButton(android.R.string.no, new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface dialog, int which) { // do nothing }
				 * })
				 */
				.setIcon(android.R.drawable.ic_dialog_alert).show();

	}

	// /Add Vaccine to Calendar
	public void setVaccine(Calendar birth) {
		// Toast.makeText(this, birth.toString(), Toast.LENGTH_LONG).show();
		Calendar vaccineDay = Calendar.getInstance();
		Calendar c = Calendar.getInstance();
		Date birthDate = birth.getTime();
		c.setTime(birthDate);
		// c.set(Calendar.HOUR_OF_DAY, 9);
		// c.set(Calendar.MINUTE, 0);
		c.add(Calendar.MINUTE, 1);

		c.add(Calendar.DAY_OF_YEAR, 42); // week
		Date setDate = c.getTime();
		vaccineDay.setTime(setDate);
		addVaccine(vaccineDay,"©’¥«—§´’π‰¢ÈÀ—¥ ÿπ—¢/≈”‰ ÈÕ—°‡ ∫");
		//Toast.makeText(this, vaccineDay.toString(),Toast.LENGTH_LONG).show();
		// addEvent(dogName+"©’¥«—§´’π‰¢ÈÀ—¥ ÿπ—¢/≈”‰ ÈÕ—°‡ ∫","", vaccineDay);
		/*setAlarm(vaccineDay, GlobalVariables.eventID, "‰¢ÈÀ—¥ ÿπ—¢/≈”‰ ÈÕ—°‡ ∫");
		GlobalVariables.eventID++;
		// Log.d("AlarmReceiver",Integer.valueOf(GlobalVariables.eventID).toString());
		// Toast.makeText(this,
		// Integer.valueOf(GlobalVariables.eventID).toString(),
		// Toast.LENGTH_LONG).show();
		*/

		c.setTime(birthDate);
		//c.set(Calendar.HOUR_OF_DAY, 9);
		// c.set(Calendar.MINUTE, 0);
		// c.add(Calendar.MINUTE, 2);
		c.add(Calendar.DAY_OF_YEAR, 56); // week
		setDate = c.getTime();
		vaccineDay.setTime(setDate);
		addVaccine(vaccineDay,"©’¥«—§´’π‰¢ÈÀ—¥ ÿπ—¢/≈”‰ ÈÕ—°‡ ∫");
		// addEvent(dogName+"©’¥«—§´’π‰¢ÈÀ—¥ ÿπ—¢/≈”‰ ÈÕ—°‡ ∫","", vaccineDay);
		//setAlarm(vaccineDay, GlobalVariables.eventID, "‰¢ÈÀ—¥ ÿπ—¢/≈”‰ ÈÕ—°‡ ∫");
		//GlobalVariables.eventID++;
		// Log.d("AlarmReceiver",Integer.valueOf(GlobalVariables.eventID).toString());
		// Toast.makeText(this, vaccineDay.toString(),
		// Toast.LENGTH_LONG).show();

		c.setTime(birthDate);
		//c.set(Calendar.HOUR_OF_DAY, 9);
		//c.set(Calendar.MINUTE, 0);
		c.add(Calendar.DAY_OF_YEAR, 70); // week
		setDate = c.getTime();
		vaccineDay.setTime(setDate);
		addVaccine(vaccineDay,"©’¥«—§´’π‰¢ÈÀ—¥ ÿπ—¢/≈”‰ ÈÕ—°‡ ∫/µ—∫Õ—°‡ ∫/‡≈ª‚µ ‰ª‚√´’ /À«—¥/À≈Õ¥≈¡Õ—°‡ ∫µ‘¥µËÕ  ");
		// addEvent(dogName+"©’¥«—§´’π‰¢ÈÀ—¥ ÿπ—¢/≈”‰ ÈÕ—°‡ ∫","µ—∫Õ—°‡ ∫/‡≈ª‚µ ‰ª‚√´’ /À«—¥/À≈Õ¥≈¡Õ—°‡ ∫µ‘¥µËÕ  ",
		// vaccineDay);
		//setAlarm(vaccineDay, GlobalVariables.eventID,"‰¢ÈÀ—¥ ÿπ—¢/≈”‰ ÈÕ—°‡ ∫µ—∫Õ—°‡ ∫/‡≈ª‚µ ‰ª‚√´’ /À«—¥/À≈Õ¥≈¡Õ—°‡ ∫µ‘¥µËÕ  ");
		//GlobalVariables.eventID++;

		c.setTime(birthDate);
		//c.set(Calendar.HOUR_OF_DAY, 9);
		//c.set(Calendar.MINUTE, 0);
		c.add(Calendar.DAY_OF_YEAR, 84); // week
		setDate = c.getTime();
		vaccineDay.setTime(setDate);
		addVaccine(vaccineDay,"©’¥«—§´’πªÈÕß°—π‚√§æ‘… ÿπ—¢∫È“");
		// addEvent(dogName+"©’¥«—§´’πªÈÕß°—π‚√§æ‘… ÿπ—¢∫È“","", vaccineDay);
		//setAlarm(vaccineDay, GlobalVariables.eventID, "‚√§æ‘… ÿπ—¢∫È“");
		//GlobalVariables.eventID++;

		c.setTime(birthDate);
		//c.set(Calendar.HOUR_OF_DAY, 9);
		//c.set(Calendar.MINUTE, 0);
		c.add(Calendar.DAY_OF_YEAR, 112); // week
		setDate = c.getTime();
		vaccineDay.setTime(setDate);
		addVaccine(vaccineDay,"©’¥«—§´’πªÈÕß°—π‚√§≈”‰ ÈÕ—°‡ ∫");
		// addEvent(dogName+"©’¥«—§´’πªÈÕß°—π‚√§≈”‰ ÈÕ—°‡ ∫","", vaccineDay);
		//setAlarm(vaccineDay, GlobalVariables.eventID, "‚√§≈”‰ ÈÕ—°‡ ∫");
		//GlobalVariables.eventID++;

		c.setTime(birthDate);
		//c.set(Calendar.HOUR_OF_DAY, 9);
		//c.set(Calendar.MINUTE, 0);
		c.add(Calendar.DAY_OF_YEAR, 180); // week
		setDate = c.getTime();
		vaccineDay.setTime(setDate);
		addVaccine(vaccineDay,"©’¥«—§´’πªÈÕß°—π‚√§æ‘… ÿπ—¢∫È“");
		// addEvent(dogName+"©’¥«—§´’πªÈÕß°—π‚√§æ‘… ÿπ—¢∫È“","", vaccineDay);
		//setAlarm(vaccineDay, GlobalVariables.eventID, "‚√§æ‘… ÿπ—¢∫È“");
		//GlobalVariables.eventID++;

		// Toast.makeText(this, vaccineDay.toString(),
		// Toast.LENGTH_LONG).show();

	}

	public void addEvent(String title, String comment, Calendar date) {

		ContentValues values = new ContentValues();
		values.put(CalendarContract.Events.DTSTART, date.getTimeInMillis());
		values.put(CalendarContract.Events.TITLE, title);
		values.put(CalendarContract.Events.DESCRIPTION, comment);

		TimeZone timeZone = TimeZone.getDefault();
		values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());

		// default calendar
		values.put(CalendarContract.Events.CALENDAR_ID, 1);
		values.put(CalendarContract.Events.DURATION, "+P1H");
		values.put(CalendarContract.Events.HAS_ALARM, 1);

		// values.put(CalendarContract.Events.RRULE, "FREQ=DAILY;UNTIL="+
		// dtUntill);
		// for one hour

		// insert event to calendar
		// Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
		// Toast.makeText(this, date.toString(), Toast.LENGTH_LONG).show();
		Uri eventsUri = Uri.parse("content://com.android.calendar/events");
		getContentResolver().insert(eventsUri, values);

	}

	// /End Add Vaccine to Calendar

	// Alert
	public void setAlarm(Calendar targetCal, int eventID, String notiText) {

		/*
		 * textAlarmPrompt.setText( "\n\n***\n" + "Set Alarm @ " +
		 * targetCal.getTime() + "\n" + "***\n");
		 */

		/*
		 * Toast.makeText(getBaseContext(),
		 * String.valueOf(targetCal.getTimeInMillis()),
		 * Toast.LENGTH_LONG).show();
		 */

		Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
		intent.putExtra("notiText", notiText);
		intent.putExtra("reminder", dogName + " ©’¥«—§´’π");
		intent.putExtra("eventID", eventID);
		intent.putExtra("name", dogName);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				getBaseContext(), eventID, intent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		// alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+
		// (2 * 1000), pendingIntent);
		alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
				pendingIntent);

	}
	public void addVaccine(Calendar vacCal,String name) {
		String date = dt.format(vacCal.getTime());
		int vacID = myDB.getNextID("vaccine");
		
		long flg = myDB.insertVaccine(vacID,dogID, date,name);
		if(flg>0){			
			//Toast.makeText(AddActivity.this,"‡æ‘Ë¡¢ÈÕ¡Ÿ≈ «—§´’π", Toast.LENGTH_LONG).show();
			setAlarm(vacCal, vacID, name);
		}
	}

	//
	/*// vaccine file
	public void createVaccine() {
	*/
	//
}
