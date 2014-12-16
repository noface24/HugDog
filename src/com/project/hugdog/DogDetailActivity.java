package com.project.hugdog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DogDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dog_detail);
		final TextView txtName = (TextView) findViewById(R.id.txtName);
		final TextView txtColor = (TextView) findViewById(R.id.txtColor);
		final TextView txtBreed = (TextView) findViewById(R.id.txtBreed);
		final TextView txtWeight = (TextView) findViewById(R.id.txtWeigth);
		final TextView txtBirth = (TextView) findViewById(R.id.txtBirth);
		final TextView txtGender = (TextView) findViewById(R.id.txtGender);
		String path ;
		Bundle b = getIntent().getExtras();
		path = b.getString("path");
		try {
			
			
			
			
			File file = new File(path);
			// FileOutputStream outputStream;
			/*** if exists create text file ***/
			
			
			FileInputStream fIn = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(fIn));
			//String line = reader.readLine();
			txtName.setText(reader.readLine());
			txtBirth.setText(reader.readLine());
			txtColor.setText(reader.readLine());
			txtBreed.setText(reader.readLine());
			txtWeight.setText(reader.readLine());
/*			
			String sss = "Name_" + txtName.getText() + "\nBirth_"
					+ txtBirth.getText() + "\nColor_"
					+ txtColor.getText() + "\nBreed_"
					+ txtBreed.getText() + "\nWeight_"
					+ txtWeight.getText() + "\n";

			outWrite.append(sss);
			outWrite.close();
			fOut.close();
*/
			/*** Write Text File ***/
			
			
			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(DogDetailActivity.this,
					"Failed!" + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}
		
		
	}
}
