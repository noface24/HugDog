package com.project.hugdog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HisAddActivity extends Activity {
	EditText editDetail,editHospital;
	TextView txtDate;
	ImageView imgView;
	ImageButton btnSave;
	String imgName;
	int dogID;
	DBClass myDB = new DBClass(this);
	static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_his_add);
		editDetail = (EditText) findViewById(R.id.editDetail);
		editHospital = (EditText) findViewById(R.id.editHospital);
		txtDate = (TextView) findViewById(R.id.date);
		
		btnSave = (ImageButton) findViewById(R.id.btnSave);
		
		dogID = GlobalVariables.dogID;
		txtDate.setText(GlobalVariables.date1);
		//Toast.makeText(getBaseContext(), GlobalVariables.date, Toast.LENGTH_SHORT).show();
		final TextView txtName = (TextView) findViewById(R.id.txtName);
		txtName.setText(GlobalVariables.dogName);
		
		// Perform action on click

				btnSave.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						btnSave.setColorFilter(Color.argb(80, 80, 80, 80));
						String ed_detail = editDetail.getText().toString().trim();
						String ed_hospital = editHospital.getText().toString().trim();
						String ed_birth = df.format(GlobalVariables.date);
						
					//	dogName = txtName.getText().toString();
						if (ed_detail.isEmpty()) {
							showDialogBox("กรอกรายละเอียด/อาการ");
						} else if (ed_hospital.isEmpty()) {
							showDialogBox("กรอกโรงพยาบาล");
						} 
						else{
						//////insetrt HIstory
							long flg1 = myDB.insertHis(ed_detail, ed_birth, ed_hospital, GlobalVariables.dogID);
								if(flg1>0){
									finish();
									Toast.makeText(HisAddActivity.this,"เพิ่มประวัติการรักษาของ " + txtName.getText()+ " แล้ว", Toast.LENGTH_LONG)
									.show();
							}
						}
						
						// create a File object for the parent directory

						/*** Write Text File to SD Card ***//*
						else {
							try {
								// String txtFileName = txtName.getText().toString() +
								// ".txt";
								File dr = new File("/sdcard/Hugdog/History/");
								dr.mkdirs();
								String path = GlobalVariables.date1 + ".txt";
								File file = new File(dr, path);
								
								// FileOutputStream outputStream;
								*//*** if exists create text file ***//*
								// file.createNewFile();

								//if (!file.exists()) {							
									file.createNewFile();
									FileOutputStream fOut = new FileOutputStream(file);
									OutputStreamWriter outWrite = new OutputStreamWriter(
											fOut);

									String sss = editDetail.getText() + "\n" 											
											+ editHospital.getText() + "\n";

									outWrite.append(sss);
									outWrite.close();
									fOut.close();

									*//*** Write Text File ***//*
									Toast.makeText(AddActivity.this,
											birthDay.toString(),
											Toast.LENGTH_LONG).show();
											
									
									
									finish();
									Toast.makeText(HisAddActivity.this,
											"เพิ่มข้อมูล ประวัติการรักษา แล้ว",
											Toast.LENGTH_LONG).show();
							//	}
								else{
									Toast.makeText(HisAddActivity.this,
											"มีสัตว์เลี้ยงชื่อ " + txtName.getText() + " แล้ว",
											Toast.LENGTH_LONG).show();
								}
								
								
								
								 * Toast.makeText(AddActivity.this, "File Save!" +
								 * getFilesDir(), Toast.LENGTH_LONG) .show();
								 

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Toast.makeText(HisAddActivity.this,
										"Failed!" + e.getMessage(), Toast.LENGTH_LONG)
										.show();
							}
						}*/
					}
				});

				// end save button
				
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
								.getAlbumHisStorageDir(getAlbumName());

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
					
					imgName = GlobalVariables.date1;
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
