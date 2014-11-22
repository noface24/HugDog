package com.project.hugdog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		// Create txtfile
		final EditText txtName = (EditText) findViewById(R.id.editName);
		final EditText txtColor = (EditText) findViewById(R.id.editColor);
		final EditText txtBreed = (EditText) findViewById(R.id.editBreed);
		final EditText txtWeight = (EditText) findViewById(R.id.editWeight);
		//final TextView txtAge = (TextView) findViewById(R.id.txtAge);
		final ImageButton btnSave = (ImageButton) findViewById(R.id.btnSave);
		

		// Perform action on click
		btnSave.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// create a File object for the parent directory
				// File hugDogDirectory = new File("/sdcard/Hugdog/");
				// have the object build the directory structure, if needed.
				// /hugDogDirectory.mkdirs();
				// create a File object for the output file
				// File outputFile = new File(wallpaperDirectory, filename);
				// now attach the OutputStream to the file object, instead of a
				// String representation
				// FileOutputStream fos = new FileOutputStream(outputFile);
				/*** Write Text File to SD Card ***/
				try {
					String txtFileName = txtName.getText().toString() + ".txt";
					// String path = "/sdcard/Hugdog/dogs/"+txtFileName+".txt";;
					File file = new File(getFilesDir(), txtFileName);
					// FileOutputStream outputStream;
					/*** if exists create text file ***/
					if (!file.exists()) {
						file.createNewFile();
					}

					/*** Write Text File ***/
					FileWriter writer = new FileWriter(file, true); // True =
																	// Append to
																	// file,
																	// false =
																	// Overwrite
					writer.write(txtName.getText() + "\n");
					// writer.write(txtBirth.getText() + "\n");
					//writer.write(txtAge.getText() + "\n");
					writer.write(txtColor.getText() + "\n");
					writer.write(txtBreed.getText() + "\n");
					writer.write(txtWeight.getText() + "\n");

					writer.close();

					Toast.makeText(AddActivity.this,
							"File Save!" + getFilesDir(), Toast.LENGTH_LONG)
							.show();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(AddActivity.this,
							"Failed!" + e.getMessage(), Toast.LENGTH_LONG)
							.show();
				}

			}
		});
		//end save button
		

	}

	public void onRadioButtonClicked(View view) { // Select gender
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.btnMale:
			if (checked)
				// Pirates are the best
				break;
		case R.id.btnFemale:
			if (checked)
				// Ninjas rule
				break;
		}
	}

	//datepick
	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			TextView txtAge = (TextView) getActivity().getWindow().getDecorView().getRootView().findViewById(R.id.txtAge);
			TextView txtBirth = (TextView) getActivity().getWindow().getDecorView().getRootView().findViewById(R.id.txtBirthday);
		      txtBirth.setText( "วัน"+day + " เดือน " + month + "ปี " + year );
		      
		      txtAge.setText("");
		      
		}
	}
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	//end datepick
}
