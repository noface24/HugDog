package com.project.hugdog;

import java.io.File;

public final class BaseAlbumDirFactory extends AlbumStorageDirFactory {

	// Standard storage location for digital camera files
	private static String CAMERA_DIR = "/sdcard/Hugdog/";

	@Override
	public File getAlbumStorageDir(String albumName) {
		/*return new File(Environment.getExternalStorageDirectory() + CAMERA_DIR
				+ albumName);
				*/
		return new File(CAMERA_DIR
				+ albumName);
	}
	
	public File getAlbumHisStorageDir(String albumName) {
		CAMERA_DIR = "/sdcard/Hugdog/History/";
		/*return new File(Environment.getExternalStorageDirectory() + CAMERA_DIR
				+ albumName);
				*/
		return new File(CAMERA_DIR
				+ albumName);
	}
	public File getAlbumVacStorageDir(String albumName) {
		/*return new File(Environment.getExternalStorageDirectory() + CAMERA_DIR
				+ albumName);
				*/
		return new File(CAMERA_DIR
				+ albumName+"/Vaccine");
	}
}
