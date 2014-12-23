package com.project.hugdog;

import java.io.File;

import android.os.Environment;

public final class BaseAlbumDirFactory extends AlbumStorageDirFactory {

	// Standard storage location for digital camera files
	private static final String CAMERA_DIR = "/sdcard/Hugdog/";

	@Override
	public File getAlbumStorageDir(String albumName) {
		/*return new File(Environment.getExternalStorageDirectory() + CAMERA_DIR
				+ albumName);
				*/
		return new File(CAMERA_DIR
				+ albumName);
	}
}
