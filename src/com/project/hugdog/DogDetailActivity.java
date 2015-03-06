package com.project.hugdog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.hugdog.DBClass;
import com.project.hugdog.DBClass.sDog;


public class DogDetailActivity extends Activity {
	private String imgName,name,gender,color,breed,weight;
	String birth;
	private static AgeCalculation age = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dog_detail);
		
		final DBClass myDb = new DBClass(this);
		
		final TextView txtName = (TextView) findViewById(R.id.txtName);
		final TextView txtColor = (TextView) findViewById(R.id.txtColor);
		final TextView txtBreed = (TextView) findViewById(R.id.txtBreed);
		final TextView txtWeight = (TextView) findViewById(R.id.txtWeigth);
		final TextView txtBirth = (TextView) findViewById(R.id.txtBirth);
		final TextView txtGender = (TextView) findViewById(R.id.txtGender);
		final TextView txtAge = (TextView) findViewById(R.id.txtAge);
		ImageButton btnDel = (ImageButton) findViewById(R.id.btnDelete);
		ImageButton btnEdit = (ImageButton) findViewById(R.id.btnEdit);
		final int dogID ;
		Bundle b = getIntent().getExtras();
		
		dogID =b.getInt("dogID");
		//age = new AgeCalculation();
		//Cursor cursor = myDb.getDogDetail(dogID);
		sDog dog = myDb.getDogData(dogID);
		//Toast.makeText(this, String.valueOf(dogID), Toast.LENGTH_SHORT).show();
		
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
				
				////
	
			    	name = dog.gName();
			    	//name = cursor.getString(cursor.getColumnIndex(DBClass.KEY_NAME));
					gender = dog.gGender();
					birth = dog.gBirth();
					color =dog.gColor();
					breed = dog.gBreed();
					weight = dog.gWeight();
					txtName.setText(name);
					txtGender.setText(gender);
					txtBirth.setText(birth);
					txtColor.setText(color);
					txtBreed.setText(breed);
					txtWeight.setText(weight);
				    //cursor.moveToNext();
			    
			   // cursor.close();
				////
				
				
				
		/*		try {
	
					File file = new File(path);
					// FileOutputStream outputStream;
					*//*** if exists create text file ***//*
					
					
					FileInputStream fIn = new FileInputStream(file);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(fIn));
					name = reader.readLine();
					gender = reader.readLine();
					birth = reader.readLine();
					color = reader.readLine();
					breed = reader.readLine();
					weight = reader.readLine();
					txtName.setText(name);
					txtGender.setText(gender);
					txtBirth.setText(birth);
					txtColor.setText(color);
					txtBreed.setText(breed);
					txtWeight.setText(weight);
					
					reader.close();
					fIn.close();
								
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(DogDetailActivity.this,
							"Failed!" + e.getMessage(), Toast.LENGTH_LONG)
							.show();
				}*/
				
				
				
				imgName = name;
				
				File imgFile = new  File(getAlbumDir()+"/IMG_"+imgName+".jpg");
				
				if(imgFile.exists()){
					
				    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

				    //ImageView myImage = (ImageView) findViewById(R.id.imageView1);

				    mImageView.setImageBitmap(myBitmap);
				    mImageView.setVisibility(View.VISIBLE);
				    

				}
				//calculate age
							
				/*String[] splitBirth =birth.split("-");
				
				int year = Integer.parseInt(splitBirth[0]);
				int month = Integer.parseInt(splitBirth[1]);
				int day = Integer.parseInt(splitBirth[2]);
				//Toast.makeText(this, year, Toast.LENGTH_SHORT).show();
				//age.getCurrentDate();
				age.setDateOfBirth(year, month, day);
				age.calcualteYear();
				age.calcualteMonth();
				age.calcualteDay();
				String resultAge = age.getResult();
				txtAge.setText(resultAge);
				*/
				//end calculate age
				
// dialog box
				
				final AlertDialog.Builder box = new AlertDialog.Builder(this);
				box.setTitle("ลบ");
				box.setMessage("ต้องการลบข้อมูลทั้งหมดของ "+ name +" ?");
				box.setPositiveButton("ใช่",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								long flg = myDb.deleteDog(dogID);
					           	if(flg > 0)
					           	{
					           	 Toast.makeText(DogDetailActivity.this,"ลบข้อมูลสำเร็จ",
					           			 	Toast.LENGTH_SHORT).show(); 
					           	}
					           	else
					           	{
					              	 Toast.makeText(DogDetailActivity.this,"ลบข้อมูลไม่สำเร็จ",
					        			 	Toast.LENGTH_SHORT).show(); 
					           	}
								/*File file = new File(path);
								boolean deleted = file.delete();
								Toast.makeText(DogDetailActivity.this,
								"ลบ  "+name+" แล้ว", Toast.LENGTH_SHORT).show();
								try {
									deleteImageFile();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}*/
								finish();
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
						Intent i = new Intent(DogDetailActivity.this, EditActivity.class);
						i.putExtra("name", name);
						i.putExtra("birth", birth);
						i.putExtra("gender", gender);
						i.putExtra("breed", breed);
						i.putExtra("weight", weight);
						i.putExtra("color", color);
						i.putExtra("dogID", dogID);
						startActivity(i);
						finish();
					}
		 
				});
				
				
		
				
	}
	
	
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
						.getAlbumStorageDir(getAlbumName());

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
		
		
}
