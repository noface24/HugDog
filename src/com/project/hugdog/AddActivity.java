package com.project.hugdog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity {
	private TextView txtAge;
	private static AgeCalculation age = null;
	private RadioGroup radioGenderGroup;
	private String gender;

	// private RadioButton radioSexButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		final EditText txtName = (EditText) findViewById(R.id.editName);
		final EditText txtColor = (EditText) findViewById(R.id.editColor);
		final EditText txtBreed = (EditText) findViewById(R.id.editBreed);
		final EditText txtWeight = (EditText) findViewById(R.id.editWeight);
		final TextView txtBirth = (TextView) findViewById(R.id.txtBirthday);
		// gender= "ตัวผู้";
		final ImageButton btnSave = (ImageButton) findViewById(R.id.btnSave);
		age = new AgeCalculation();

		radioGenderGroup = (RadioGroup) findViewById(R.id.radioGenderGroup);
		radioGenderGroup.check(R.id.btnMale);
		gender = "ตัวผู้";
		// Create txtfile

		// Perform action on click

		btnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				btnSave.setColorFilter(Color.argb(80, 80, 80, 80));
				String ed_name = txtName.getText().toString().trim();
				String ed_birth = txtBirth.getText().toString().trim();
				String ed_color = txtColor.getText().toString().trim();
				String ed_breed = txtBreed.getText().toString().trim();
				String ed_weight = txtWeight.getText().toString().trim();
				if (ed_name.isEmpty()) {
					showDialogBox("กรอกชื่อสุนัข");
				} else if (ed_birth.isEmpty()) {
					showDialogBox("เลือกวันเกิด");
				} else if (ed_color.isEmpty()) {
					showDialogBox("กรอกสี");
				} else if (ed_weight.isEmpty()) {
					showDialogBox("กรอกน้ำหนัก");
				} else if (ed_breed.isEmpty()) {
					showDialogBox("กรอกพันธุ์");
				}
				// create a File object for the parent directory

				/*** Write Text File to SD Card ***/
				else {
					try {
						// String txtFileName = txtName.getText().toString() +
						// ".txt";
						File dr = new File("/sdcard/Hugdog/");
						dr.mkdirs();
						String path = txtName.getText().toString() + ".txt";
						;
						File file = new File(dr, path);
						// FileOutputStream outputStream;
						/*** if exists create text file ***/
						// file.createNewFile();

						if (!file.exists()) {
							file.createNewFile();
						}

						FileOutputStream fOut = new FileOutputStream(file);
						OutputStreamWriter outWrite = new OutputStreamWriter(
								fOut);

						String sss = txtName.getText() + "\n" + gender + "\n"
								+ txtBirth.getText() + "\n"
								+ txtColor.getText() + "\n"
								+ txtBreed.getText() + "\n"
								+ txtWeight.getText() + "\n";

						outWrite.append(sss);
						outWrite.close();
						fOut.close();

						/*** Write Text File ***/

						finish();
						Toast.makeText(AddActivity.this,
								"เพิ่มข้อมูล " + txtName.getText() + " แล้ว",
								Toast.LENGTH_LONG).show();
						/*
						 * Toast.makeText(AddActivity.this, "File Save!" +
						 * getFilesDir(), Toast.LENGTH_LONG) .show();
						 */

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(AddActivity.this,
								"Failed!" + e.getMessage(), Toast.LENGTH_LONG)
								.show();
					}
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
				gender = "ตัวผู้";// Pirates are the best
			break;
		case R.id.btnFemale:
			if (checked)
				gender = "ตัวเมีย";// Ninjas rule
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
			txtBirth.setText("วันที่ " + day + " เดือน " + (month + 1) + " ปี "
					+ year);

			txtAge.setText(resultAge); // เดือน "+ageDay +" วั น");

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
				.setTitle("ใส่ข้อมูลไม่ครบ")
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
		Calendar vaccineDay = Calendar.getInstance();             
		
		final Calendar c = Calendar.getInstance();		
		Date currentDate = c.getTime();
		c.setTime(currentDate);
		c.add(Calendar.DAY_OF_YEAR, 42);
		Date nextWeek = c.getTime();
		
		vaccineDay.setTime(nextWeek);
		//vaccineDay.set(, nextWeek.getMonth(), nextWeek.getDay());
		Toast.makeText(this, vaccineDay.toString(), Toast.LENGTH_LONG).show();
	}
	// /End Add Vaccine to Calendar
}
