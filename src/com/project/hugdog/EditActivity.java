package com.project.hugdog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.project.hugdog.AddActivity.DatePickerFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class EditActivity extends Activity {

	private TextView txtAge;
	private static AgeCalculation age = null;
	private RadioGroup radioGenderGroup;
	private String gender, dogName, name, weight, color, breed, birth;
	int dogID;
	private static Calendar birthDay;
	DBClass myDB = new DBClass(this);
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		final EditText txtName = (EditText) findViewById(R.id.editName);
		final EditText txtColor = (EditText) findViewById(R.id.editColor);
		final AutoCompleteTextView txtBreed = (AutoCompleteTextView) findViewById(R.id.editBreed);
		final EditText txtWeight = (EditText) findViewById(R.id.editWeight);
		final TextView txtBirth = (TextView) findViewById(R.id.txtBirthday);
		txtAge = (TextView) findViewById(R.id.txtAge);
		birthDay = Calendar.getInstance();
		final ImageButton btnSave = (ImageButton) findViewById(R.id.btnSave);
		//age = new AgeCalculation();
		radioGenderGroup = (RadioGroup) findViewById(R.id.radioGenderGroup);
		Bundle b = getIntent().getExtras();
		gender = b.getString("gender");
		name = b.getString("name");
		birth = b.getString("birth");
		weight = b.getString("weight");
		color = b.getString("color");
		breed = b.getString("breed");
		final int dogID = b.getInt("dogID");

		txtName.setText(name);
		txtColor.setText(color);
		txtBreed.setText(breed);
		txtWeight.setText(weight);
		txtBirth.setText(birth);
		if (gender.equals("ตัวผู้")) {
			radioGenderGroup.check(R.id.btnMale);
			gender = "ตัวผู้";
		} else {
			radioGenderGroup.check(R.id.btnFemale);
			gender = "ตัวเมีย";
		}
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

		// ////age////
		String[] birthsp = birth.split("-");
		//String[] birthsp = birth.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
		//int day = Integer.parseInt(birthsp[2]);
		//int month = Integer.parseInt(birthsp[1]);
		//int year = Integer.parseInt(birthsp[0]);
		// Toast.makeText(this, birthsp[1]+birthsp[3]+birthsp[5],
		// Toast.LENGTH_LONG).show();
		//age.getCurrentDate();
		//age.setDateOfBirth(year, month - 1, day);
		//age.calcualteYear();
		//age.calcualteMonth();
		//age.calcualteDay();// cal.get(Calendar.DAY_OF_MONTH)-day;

		//String resultAge = age.getResult();
		//txtAge.setText(resultAge);
		// //end age/////

		// Perform action on click

		btnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				btnSave.setColorFilter(Color.argb(80, 80, 80, 80));
				String ed_name = txtName.getText().toString().trim();
				String ed_birth = df.format(birthDay.getTime());
				//String ed_birth = txtBirth.getText().toString().trim();
				String ed_color = txtColor.getText().toString().trim();
				String ed_breed = txtBreed.getText().toString().trim();
				String ed_weight = txtWeight.getText().toString().trim();
				dogName = txtName.getText().toString();
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
				//Update data
				else{
					long flg1 = myDB.updateDog(dogID,ed_name, ed_color, ed_breed, ed_weight, gender, ed_birth);
					if(flg1>0){
						finish();
						Toast.makeText(
								EditActivity.this,
								"แก้ไขข้อมูล " + txtName.getText()
										+ " แล้ว", Toast.LENGTH_LONG)
								.show();
					}
					else{
						Toast.makeText(EditActivity.this, "แก้ไขไม่สำเร็จ", Toast.LENGTH_SHORT);
					}
				}
				
				//END Updat data
				
				
				// create a File object for the parent directory

				/*** Write Text File to SD Card ***/
				/*else {
					try {
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

						*//*** Write Text File ***//*
						
						 * Toast.makeText(AddActivity.this, birthDay.toString(),
						 * Toast.LENGTH_LONG).show();
						 
						dogName = txtName.getText().toString();
						// setVaccine(birthDay);

						finish();
						Toast.makeText(EditActivity.this,
								"บันทึกข้อมูล " + txtName.getText() + " แล้ว",
								Toast.LENGTH_LONG).show();

						
						 * Toast.makeText(AddActivity.this, "File Save!" +
						 * getFilesDir(), Toast.LENGTH_LONG) .show();
						 

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(EditActivity.this,
								"Failed!" + e.getMessage(), Toast.LENGTH_LONG)
								.show();
					}
				}*/
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
			EditActivity.birthDay.set(Calendar.DAY_OF_MONTH, day);
			EditActivity.birthDay.set(Calendar.MONTH, month);
			EditActivity.birthDay.set(Calendar.YEAR, year);
			/*try{
				// ////age////
				String ages = age.getCurrentDate();
				age.setDateOfBirth(year, month, day);
				age.calcualteYear();
				age.calcualteMonth();
				age.calcualteDay();// cal.get(Calendar.DAY_OF_MONTH)-day;
				String resultAge = age.getResult();

				TextView txtAge = (TextView) getActivity().getWindow()
						.getDecorView().getRootView().findViewById(R.id.txtAge);
				txtAge.setText(resultAge); // เดือน "+ageDay +" วั น");
				// //end age/////
			}
			catch(Exception e){
			    // do something else here if the statement under the try failed.
			    // most likely log the error message and see what it is
				
			}*/
			

			TextView txtBirth = (TextView) getActivity().getWindow()
					.getDecorView().getRootView()
					.findViewById(R.id.txtBirthday);
			txtBirth.setText("วันที่ " + day + " เดือน " + (month + 1) + " ปี "
					+ year);

			

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
}
