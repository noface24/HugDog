package com.project.hugdog;

import java.io.File;

import android.os.Environment;

public final  class FroyoAlbumDirFactory extends AlbumStorageDirFactory {

	@Override
	public File getAlbumStorageDir(String albumName) {
		// TODO Auto-generated method stub
		return new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				albumName);
	}

	@Override
	public File getAlbumHisStorageDir(String albumName) {
		// TODO Auto-generated method stub
		 return new File(
				Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
		albumName);
	}
	@Override
	public File getAlbumVacStorageDir(String albumName) {
		// TODO Auto-generated method stub
		 return new File(
				Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
		albumName);
	}
}
