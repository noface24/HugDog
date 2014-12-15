package com.project.hugdog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;






import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;




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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class AddActivity extends Activity {
	private TextView txtAge;
	private static AgeCalculation age = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		final EditText txtName = (EditText) findViewById(R.id.editName);
		final EditText txtColor = (EditText) findViewById(R.id.editColor);
		final EditText txtBreed = (EditText) findViewById(R.id.editBreed);
		final EditText txtWeight = (EditText) findViewById(R.id.editWeight);
		final TextView txtBirth = (TextView) findViewById(R.id.txtBirthday);
		
		final ImageButton btnSave = (ImageButton) findViewById(R.id.btnSave);
		age=new AgeCalculation();
		///cam
		mImageView = (ImageView) findViewById(R.id.imageView1);
		
		mImageBitmap = null;
		

		ImageButton picBtn = (ImageButton) findViewById(R.id.btnIntend);
		setBtnListenerOrDisable( 
				picBtn, 
				mTakePicOnClickListener,
				MediaStore.ACTION_IMAGE_CAPTURE
		);

		

		/*
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {*/
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
	//	}
		///end cam
		// Create txtfile

		

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
					//String txtFileName = txtName.getText().toString() + ".txt";
					File dr=new File("/sdcard/Hugdog/");
					dr.mkdirs();
					String path = txtName.getText().toString()+".txt";;
					File file = new File(dr,path);
					// FileOutputStream outputStream;
					/*** if exists create text file ***/
					//file.createNewFile();
					
					if (!file.exists()) {
						file.createNewFile();
					}
					
					FileOutputStream fOut=new FileOutputStream(file);
					OutputStreamWriter outWrite=new OutputStreamWriter(fOut);
					
					String sss="Name_"+txtName.getText()+"\nBirth_"+txtBirth.getText() + "\nColor_"+txtColor.getText() + "\nBreed_"+
					txtBreed.getText() + "\nWeight_"+txtWeight.getText() + "\n";
					
					outWrite.append(sss);
					outWrite.close();
					fOut.close();

					/*** Write Text File ***/
					//FileWriter writer = new FileWriter(file, true); // True =
																	// Append to
																	// file,
																	// false =
																	// Overwrite
					//writer.write(txtName.getText() + "\n");
					// writer.write(txtBirth.getText() + "\n");
					//writer.write(txtAge.getText() + "\n");
					//writer.write(txtColor.getText() + "\n");
					//writer.write(txtBreed.getText() + "\n");
					//writer.write(txtWeight.getText() + "\n");

					//writer.close();
					finish();
					Toast.makeText(AddActivity.this,
							"เพิ่มข้อมูล "+txtName.getText()+" แล้ว", Toast.LENGTH_LONG)
							.show();
					/*Toast.makeText(AddActivity.this,
							"File Save!" + getFilesDir(), Toast.LENGTH_LONG)
							.show();*/

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
			
			/*Toast.makeText(getActivity(), month, Toast.LENGTH_LONG)
					.show();*/

			// Create a new instance of DatePickerDialog and return it
			
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			Calendar cal =  Calendar.getInstance();
			//String age = getAge(year,month,day);
			int ageYear=0;
		      int ageMonth=0;
		      int ageDay=0;
			//////age////
		    age.getCurrentDate();
			age.setDateOfBirth(year, month, day);
			age.calcualteYear();
			age.calcualteMonth();
			age.calcualteDay();//cal.get(Calendar.DAY_OF_MONTH)-day;
			
			
			////end age/////
		
			String resultAge = age.getResult();
		
			TextView txtAge = (TextView) getActivity().getWindow().getDecorView().getRootView().findViewById(R.id.txtAge);
			TextView txtBirth = (TextView) getActivity().getWindow().getDecorView().getRootView().findViewById(R.id.txtBirthday);
		      txtBirth.setText( "วันที่ "+day + " เดือน " + (month+1) + " ปี " + year );
		      
		   
		      
		      txtAge.setText(resultAge); //เดือน "+ageDay +" วั น");
		     	      
		}
	}
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	

	//end datepick
	
	public void onBackPressed(){
	     // do something here and don't write super.onBackPressed()
		//android.os.Process.killProcess(android.os.Process.myPid());
    	finish();
	}
	
	/////Camera/////
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

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			
			storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (! storageDir.mkdirs()) {
					if (! storageDir.exists()){
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}
			
		} else {
			Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
		}
		
		return storageDir;
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
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
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
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
		    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
			File f = new File(mCurrentPhotoPath);
		    Uri contentUri = Uri.fromFile(f);
		    mediaScanIntent.setData(contentUri);
		    this.sendBroadcast(mediaScanIntent);
	}

	private void dispatchTakePictureIntent(int actionCode) {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		switch(actionCode) {
		case ACTION_TAKE_PHOTO_B:
			File f = null;
			
			try {
				f = setUpPhotoFile();
				mCurrentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
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

	

	Button.OnClickListener mTakePicOnClickListener = 
		new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
		}
	};

	Button.OnClickListener mTakePicSOnClickListener = 
		new Button.OnClickListener() {
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
	
		outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null) );
		
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
		
		mImageView.setImageBitmap(mImageBitmap);
		mImageView.setVisibility(
				savedInstanceState.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? 
						ImageView.VISIBLE : ImageView.INVISIBLE
		);
		
	}

	/**
	 * Indicates whether the specified action can be used as an intent. This
	 * method queries the package manager for installed packages that can
	 * respond to an intent with the specified action. If no suitable package is
	 * found, this method returns false.
	 * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
	 *
	 * @param context The application's environment.
	 * @param action The Intent action to check for availability.
	 *
	 * @return True if an Intent with the specified action can be sent and
	 *         responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list =
			packageManager.queryIntentActivities(intent,
					PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	private void setBtnListenerOrDisable( 
			ImageButton picBtn, 
			Button.OnClickListener onClickListener,
			String intentName
	) {
		if (isIntentAvailable(this, intentName)) {
			picBtn.setOnClickListener(onClickListener);        	
		} /*else {
			picBtn.setText( 
				getText(R.string.cannot).toString() + " " + /*picBtn.getText());
			picBtn.setClickable(false);
			
		}
		*/
	}

	/////end camera/////
	
}
