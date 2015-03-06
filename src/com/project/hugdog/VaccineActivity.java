package com.project.hugdog;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.project.hugdog.DBClass.sHis;
import com.project.hugdog.DBClass.sVac;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class VaccineActivity extends Activity {
	final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
	final SimpleDateFormat tf = new SimpleDateFormat("HH:MM");
	final SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final DBClass myDb = new DBClass(this);
	private String imgName;
	String strDate;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vaccine);
		
		Bundle b = getIntent().getExtras();
		final String vacID = b.getString("vacID");
		String name = b.getString("name");
		
		TextView txtDate = (TextView)findViewById(R.id.txtDate);
		TextView txtVaccine = (TextView)findViewById(R.id.txtVaccine);
		//TextView txtTime = (TextView)findViewById(R.id.txtTime);
		final EditText edVet = (EditText)findViewById(R.id.editVet);
		final EditText edHospital = (EditText)findViewById(R.id.editNote);
		//ImageButton btnTime = (ImageButton)findViewById(R.id.btnTime);
		final ImageButton btnSave = (ImageButton)findViewById(R.id.btnSave);
		final CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_complete);
		Date d ;
		
		//Toast.makeText(getBaseContext(), vacID, Toast.LENGTH_SHORT).show();
		try {
			NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.cancel(0);
			sVac vac = myDb.getVacData(vacID);
			
		
			//hisID= his.gHisID();
			//String vet = vac.gVetName();
			//String hospital = vac.gPlace();
			String vacDate = vac.gDate();
			String vacName = vac.gName();
			String time = null,date = null  ;
		
			
				try {
					d = dt.parse(vacDate);
					time = tf.format(d);
					date = formatter.format(d);
					strDate = vacDate;
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		txtDate.setText(name+"\t"+date);
		txtVaccine.setText(vacName);
		//txtTime.setText(time);
		}catch(Exception e){
			Log.d("VAC", e.getMessage());
		}
   	 	
		
		// /cam
		mImageView = (ImageView) findViewById(R.id.imageView1);
		
		mImageBitmap = null;

		ImageButton picBtn = (ImageButton) findViewById(R.id.btnIntend);
		setBtnListenerOrDisable(picBtn, mTakePicOnClickListener,
				MediaStore.ACTION_IMAGE_CAPTURE);

		/*
		 * 
		 * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
		 * mAlbumStorageDirFactory = new FroyoAlbumDirFactory(); } else {
		 */
		mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		// }
// /end cam
		imgName = name+vacID;
		
		File imgFile = new  File(getAlbumDir()+"/IMG_"+imgName+".jpg");
		
		if(imgFile.exists()){
			
		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

		    //ImageView myImage = (ImageView) findViewById(R.id.imageView1);

		    mImageView.setImageBitmap(myBitmap);
		    mImageView.setVisibility(View.VISIBLE);
		    

		}
		
		/*Button disp = (Button) findViewById(R.id.dispbut);
        disp.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                disp();
            }
        });
		*/
																	
	
			// Perform action on click

			btnSave.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					final String vet=edVet.getText().toString();
			   	 	final String hospital = edHospital.getText().toString();
					if (checkBox.isChecked()) {
				    	 
				        // checkBox.setChecked(false);
				    	 long flg = myDb.updateVac(Integer.valueOf(vacID), null, hospital, strDate, vet, 1);
				    	 if(flg>0){
				    		 
				    		 Toast.makeText(getBaseContext(), "บันทึกแล้ว", Toast.LENGTH_LONG).show();
				    		 finish();
				    	 }
					}
					else {
						long flg1 = myDb.updateVac(Integer.valueOf(vacID), null, hospital, strDate, vet, 0);
								//flg1 = myDB.InsertDog(dogID,ed_name, ed_color, ed_breed, ed_weight, gender, ed_birth);
						if(flg1>0){
							finish();
							Toast.makeText(getBaseContext(), "บันทึกแล้ว", Toast.LENGTH_LONG).show();
							//setVaccine(birthDay);*/
						}
						
						
					
					
					}
				}
			});

			// end save button
	}
	////
	
	////
	// ///Camera/////
			private static final int ACTION_TAKE_PHOTO_B = 1;
			private static final int ACTION_TAKE_PHOTO_S = 2;

			private static final String BITMAP_STORAGE_KEY = "viewbitmap";
			private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
			private ImageView mImageView;
			private Bitmap mImageBitmap;

			private String mCurrentPhotoPath;

			private static final String JPEG_FILE_PREFIX = "IMG_";
			private static final String JPEG_FILE_SUFFIX = ".jpg";

			private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

			/* Photo album for this application */
			private String getAlbumName() {
				return getString(R.string.album_name);
			}

			private File getAlbumDir() {
				File storageDir = null;

				if (Environment.MEDIA_MOUNTED.equals(Environment
						.getExternalStorageState())) {

					storageDir = mAlbumStorageDirFactory
							.getAlbumVacStorageDir(getAlbumName());

					if (storageDir != null) {
						if (!storageDir.mkdirs()) {
							if (!storageDir.exists()) {
								Log.d("CameraSample", "failed to create directory");
								return null;
							}
						}
					}

				} else {
					Log.v(getString(R.string.app_name),
							"External storage is not mounted READ/WRITE.");
				}
			
				return storageDir;
			}

			private File createImageFile() throws IOException {
				// Create an image file name
				/*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
						.format(new Date());
						*/
				String imageFileName = JPEG_FILE_PREFIX + imgName+JPEG_FILE_SUFFIX ;
				File albumF = getAlbumDir();
				
				//File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,albumF);
				
				File imageF = new File(albumF,imageFileName);
				
				
				return imageF;
			}
			private File deleteImageFile() throws IOException {
				// Create an image file name
				/*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
						.format(new Date());
						*/
				String imageFileName = JPEG_FILE_PREFIX + imgName+JPEG_FILE_SUFFIX ;
				File albumF = getAlbumDir();
				//File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,albumF);
				
				File imageF = new File(albumF, imageFileName);
				imageF.delete();
				return imageF;
			}

			private File setUpPhotoFile() throws IOException {

				File f = createImageFile();
				mCurrentPhotoPath = f.getAbsolutePath();

				return f;
			}

			private void setPic() {

				/* There isn't enough memory to open up more than a couple camera photos */
				/* So pre-scale the target bitmap into which the file is decoded */

				/* Get the size of the ImageView */
				int targetW = mImageView.getWidth();
				int targetH = mImageView.getHeight();

				/* Get the size of the image */
				BitmapFactory.Options bmOptions = new BitmapFactory.Options();
				bmOptions.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
				int photoW = bmOptions.outWidth;
				int photoH = bmOptions.outHeight;

				/* Figure out which way needs to be reduced less */
				int scaleFactor = 1;
				if ((targetW > 0) || (targetH > 0)) {
					scaleFactor = Math.min(photoW / targetW, photoH / targetH);
				}

				/* Set bitmap options to scale the image decode target */
				bmOptions.inJustDecodeBounds = false;
				bmOptions.inSampleSize = scaleFactor;
				bmOptions.inPurgeable = true;

				/* Decode the JPEG file into a Bitmap */
				Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

				/* Associate the Bitmap to the ImageView */
				mImageView.setImageBitmap(bitmap);
				mImageView.setVisibility(View.VISIBLE);
				
				

			}

			private void galleryAddPic() {
				Intent mediaScanIntent = new Intent(
						"android.intent.action.MEDIA_SCANNER_SCAN_FILE");
				File f = new File(mCurrentPhotoPath);
				
				Uri contentUri = Uri.fromFile(f);
				mediaScanIntent.setData(contentUri);
				this.sendBroadcast(mediaScanIntent);
			}

			private void dispatchTakePictureIntent(int actionCode) {

				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				switch (actionCode) {
				case ACTION_TAKE_PHOTO_B:
					File f = null;

					try {
						f = setUpPhotoFile();
						mCurrentPhotoPath = f.getAbsolutePath();
						takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(f));
						
					} catch (IOException e) {
						e.printStackTrace();
						f = null;
						mCurrentPhotoPath = null;
					}
					break;

				default:
					break;
				} // switch

				startActivityForResult(takePictureIntent, actionCode);
			}

			private void handleSmallCameraPhoto(Intent intent) {
				Bundle extras = intent.getExtras();
				mImageBitmap = (Bitmap) extras.get("data");
				mImageView.setImageBitmap(mImageBitmap);
				mImageView.setVisibility(View.VISIBLE);

			}

			private void handleBigCameraPhoto() {

				if (mCurrentPhotoPath != null) {
					setPic();
					galleryAddPic();
					mCurrentPhotoPath = null;
				}

			}

			Button.OnClickListener mTakePicOnClickListener = new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
				}
			};

			Button.OnClickListener mTakePicSOnClickListener = new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					dispatchTakePictureIntent(ACTION_TAKE_PHOTO_S);
				}
			};

			@Override
			protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				switch (requestCode) {
				case ACTION_TAKE_PHOTO_B: {
					if (resultCode == RESULT_OK) {
						handleBigCameraPhoto();
					}
					break;
				} // ACTION_TAKE_PHOTO_B

				case ACTION_TAKE_PHOTO_S: {
					if (resultCode == RESULT_OK) {
						handleSmallCameraPhoto(data);
					}
					break;
				} // ACTION_TAKE_PHOTO_S

				} // switch
			}

			// Some lifecycle callbacks so that the image can survive orientation change
			@Override
			protected void onSaveInstanceState(Bundle outState) {
				outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);

				outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY,
						(mImageBitmap != null));

				super.onSaveInstanceState(outState);
			}

			@Override
			protected void onRestoreInstanceState(Bundle savedInstanceState) {
				super.onRestoreInstanceState(savedInstanceState);
				mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);

				mImageView.setImageBitmap(mImageBitmap);
				mImageView
						.setVisibility(savedInstanceState
								.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? ImageView.VISIBLE
								: ImageView.INVISIBLE);

			}

			/**
			 * Indicates whether the specified action can be used as an intent. This
			 * method queries the package manager for installed packages that can
			 * respond to an intent with the specified action. If no suitable package is
			 * found, this method returns false.
			 * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
			 * 
			 * @param context
			 *            The application's environment.
			 * @param action
			 *            The Intent action to check for availability.
			 * 
			 * @return True if an Intent with the specified action can be sent and
			 *         responded to, false otherwise.
			 */
			public static boolean isIntentAvailable(Context context, String action) {
				final PackageManager packageManager = context.getPackageManager();
				final Intent intent = new Intent(action);
				List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
						PackageManager.MATCH_DEFAULT_ONLY);
				return list.size() > 0;
			}

			private void setBtnListenerOrDisable(ImageButton picBtn,
					Button.OnClickListener onClickListener, String intentName) {
				if (isIntentAvailable(this, intentName)) {
					picBtn.setOnClickListener(onClickListener);
				} /*
				 * else { picBtn.setText( getText(R.string.cannot).toString() + " " +
				 * /*picBtn.getText()); picBtn.setClickable(false);
				 * 
				 	 , selection, selectionArgs, null);
			//end del event
			*/
			}
			
	
	public void disp() {
		          
		
		Calendar c = Calendar.getInstance();		
		
		
		//Toast.makeText(this, c.toString(), Toast.LENGTH_LONG).show();
		
        Uri uri = Uri.parse("content://com.android.calendar/events/"
        +String.valueOf(c.getTimeInMillis()));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        // Use the Calendar app to view the time.
        startActivity(intent);
        
    }
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	/////////
/*	 public class TimePickerFragment extends DialogFragment
	 implements TimePickerDialog.OnTimeSetListener {

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
	 		String strTime = String.valueOf(hourOfDay)+":"+String.valueOf(hourOfDay);
	 		txtTime.setText(strTime);
	 	}
	 }
	 public void showTimePickerDialog(View v) {
	     DialogFragment newFragment = new TimePickerFragment();
	     newFragment.show(getFragmentManager(), "timePicker");
	 }*/
}

