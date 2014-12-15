package com.project.hugdog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DogActivity extends ListActivity {
	
	private List<String> fileList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dog);
		/*
		String dirPath;
		dirPath = "/sdcard/Hugdog/";
		File dir = new File(dirPath);
		File[] filelist = dir.listFiles();
		String[] theNamesOfFiles = new String[filelist.length];
		for (int i = 0; i < theNamesOfFiles.length; i++) {
		   theNamesOfFiles[i] = filelist[i].getName();
		}
		
		*/
		File root = new File(Environment
		         .getExternalStorageDirectory()
		         .getAbsolutePath());
		       ListDir(root);
	}
	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
	  // TODO Auto-generated method stub
	     File selected = new File(fileList.get(position));
	     if(selected.isDirectory()){
	      ListDir(selected);
	     }else {
	      Toast.makeText(DogActivity.this,
	        selected.toString() + " selected",
	        Toast.LENGTH_LONG).show();
	     }
	 }
	void ListDir(File f){
	    File[] files = f.listFiles();
	    fileList.clear();
	    for (File file : files){
	     fileList.add(file.getPath());
	    }
	    
	    ArrayAdapter<String> directoryList
	    = new ArrayAdapter<String>(this,
	      android.R.layout.simple_list_item_1, fileList);
	    setListAdapter(directoryList);
	   }
	
}
