package com.project.hugdog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Format;
import java.util.List;

import com.project.hugdog.DBClass.sDog;
import com.project.hugdog.DBClass.sHis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryActivity extends Activity {
	
	private String path,fileName,imagefile;
	private String detail,hospital,imgName,date,hisID;
	TextView txtDetail,txtHospital,txtDate;
	ImageView mImageView;
	File imgFile;
	final DBClass myDb = new DBClass(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		txtDetail = (TextView)findViewById(R.id.txtDetail);
		txtHospital = (TextView)findViewById(R.id.txtHospital);
		txtDate = (TextView)findViewById(R.id.date);
		mImageView = (ImageView) findViewById(R.id.imageView1);
		ImageButton btnDel = (ImageButton) findViewById(R.id.btnDelete);
		ImageButton btnEdit = (ImageButton) findViewById(R.id.btnEdit);
		
		
		
		fileName = GlobalVariables.date1+".txt";
		path = "/sdcard/Hugdog/History/";
		
		Bundle b = getIntent().getExtras();		
		final int dogID =b.getInt("dogID");
		String hisDate = b.getString("hisDate");		
		sHis his = myDb.getHisData(dogID,hisDate);
		if (his != null){
			hisID= his.gHisID();
			detail = his.gDetail();
			hospital = his.gPlace();
			date = his.gDate();
		}
		else{
			Toast.makeText(HistoryActivity.this,
					"วันที่ :"+GlobalVariables.date1+"ไม่มีประวัติการรักษา", Toast.LENGTH_LONG)
					.show();
			
					
					
			finish();
		}
		/*try {
			
			File file = new File(path+fileName);
			// FileOutputStream outputStream;
			*//*** if exists create text file ***//*
			
			
			FileInputStream fIn = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(fIn));
			detail = reader.readLine();
			hospital = reader.readLine();
			
			
			reader.close();
			fIn.close();
						
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
			Toast.makeText(HistoryActivity.this,
					"วันที่ :"+GlobalVariables.date1+"ไม่มีประวัติการรักษา", Toast.LENGTH_LONG)
					.show();
			Toast.makeText(HistoryActivity.this,
					"Failed!" + e.getMessage(), Toast.LENGTH_LONG)
					.show();
					
			finish();
		}
		*/
		
		///
		txtDetail.setText(detail);
		txtHospital.setText(hospital);
		txtDate.setText(GlobalVariables.date1);
		/// imageview
		imgName = GlobalVariables.date1;
	
		 imgFile = new  File(path+"Image"+"/IMG_"+imgName+".jpg");
		
		if(imgFile.exists()){
			
		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

		    //ImageView myImage = (ImageView) findViewById(R.id.imageView1);

		    mImageView.setImageBitmap(myBitmap);
		    mImageView.setVisibility(View.VISIBLE);
		    

		}
		///
		
		// dialog box
		
		final AlertDialog.Builder box = new AlertDialog.Builder(this);
		box.setTitle("ลบ");
		box.setMessage("ต้องการลบประวัติการรักษา  ?");
		box.setPositiveButton("ใช่",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {

						long flg = myDb.deleteHis(hisID);
			           	if(flg > 0)
			           	{
			           	 Toast.makeText(getBaseContext(),"ลบประวัติการรักษาแล้ว",
			           			 	Toast.LENGTH_SHORT).show(); 
			           	 finish();
			           	
			           	}
			           	else
			           	{
			              	 Toast.makeText(getBaseContext(),"ลบข้อมูลไม่สำเร็จ",
			        			 	Toast.LENGTH_SHORT).show(); 
			           	}
				/*		File file = new File(path+fileName);
						boolean deleted = file.delete();
						Toast.makeText(HistoryActivity.this,
						"ลบประวัติการรักษาแล้ว", Toast.LENGTH_SHORT).show();
						try {
							deleteImageFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						finish();*/
					}
				});
		
		  box.setNegativeButton("ยกเลิก", new
		  DialogInterface.OnClickListener() { public void
		  onClick(DialogInterface dialog, int which) { 
			  // do nothing 
			  dialog.cancel();
		  }
		  });
		 
		box.setIcon(R.drawable.ic_delete);
					

		///
		//Delete button
		btnDel.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
				box.show();
				
				
				
			}
 
		});
		//Edit button
		btnEdit.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {										
				Intent i = new Intent(HistoryActivity.this, HisEditActivity.class);
				i.putExtra("detail", detail);
				i.putExtra("hospital", hospital);
				i.putExtra("dogID", dogID);
				i.putExtra("hisID", hisID);
				startActivity(i);
				finish();
			}
 
		});

	}
	
	//del pic
	private File deleteImageFile() throws IOException {
		// Create an image file name
		/*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
				*/
		String imageFileName = "IMG_"+ imgName+".jpg" ;
		
		//File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,albumF);
		
		File imageF = new File(path+"Image/"+imageFileName);
		imageF.delete();
		return imageF;
	}
	

}
