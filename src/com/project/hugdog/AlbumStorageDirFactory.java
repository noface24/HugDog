package com.project.hugdog;

import java.io.File;

abstract class AlbumStorageDirFactory {
	public abstract File getAlbumStorageDir(String albumName);

	public abstract File getAlbumHisStorageDir(String albumName);
	
	public abstract File getAlbumVacStorageDir(String albumName);
}
