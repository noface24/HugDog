package com.project.hugdog;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import com.project.hugdog.DBClass.sVac;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class VacComActivity extends Activity {
	final DBClass myDb = new DBClass(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_vac_com);
		
		Bundle b = getIntent().getExtras();
		final String vacID = b.getString("vacID");
		String name = b.getString("name");
		TextView txtDate = (TextView)findViewById(R.id.txtDate);
		TextView txtVaccine = (TextView)findViewById(R.id.txtVaccine);
		TextView txtHospital = (TextView)findViewById(R.id.txtHospital);
		TextView txtVet = (TextView)findViewById(R.id.txtVet);
		ImageView imgview = (ImageView)findViewById(R.id.imageView1);
		try{
		sVac vac = myDb.getVacData(vacID);
		String vacDate = vac.gDate();
		String vacName = vac.gName();
		String vetName = vac.gVetName();
		String hospital = vac.gPlace();
		
		txtDate.setText(vacDate);
		txtVaccine.setText(vacName);
		txtHospital.setText(hospital);
		txtVet.setText(vetName);
		}catch(Exception e){
			Log.d("vac", e.getMessage());
		}
		 FileInputStream in;
	        BufferedInputStream buf;
	        try {
	            in = new FileInputStream("/sdcard/Hugdog/Image/Vaccine/IMG_"+name+vacID+".jpg");
	            buf = new BufferedInputStream(in);
	            Bitmap bMap = BitmapFactory.decodeStream(buf);
	            imgview.setImageBitmap(bMap);
	            if (in != null) {
	            in.close();
	            }
	            if (buf != null) {
	            buf.close();
	            }
	        } catch (Exception e) {
	            Log.e("Error reading file", e.toString());
	        }
		//imgview.setImageURI("/sdcard/Hugdog/Image/Vaccine/IMG_"+name+vacID+".jpg");
	}
}
