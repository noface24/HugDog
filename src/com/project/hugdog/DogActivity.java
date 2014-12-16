package com.project.hugdog;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//import org.apache.commons.io.FilenameUtils;


public class DogActivity extends ListActivity {
	private String path;
	private List<String> fileList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dog);
		// Use the current directory as title
		path = "/sdcard/Hugdog/";
		if (getIntent().hasExtra("path")) {
			path = getIntent().getStringExtra("path");
		}
		setTitle(path);

		// Read all files sorted into the values-array
		List values = new ArrayList();
		File dir = new File(path);
		String filename;
		if (!dir.canRead()) {
			setTitle(getTitle() + " (inaccessible)");
		}
		
		String[] list = dir.list();
		if (list != null) {
			for (String file : list) {
				
				/*
				 * if (!file.startsWith(".")) { values.add(file); }
				 */
				if (!file.startsWith(".")&&file.endsWith(".txt")) {
					//file = FilenameUtils.getName(file);
					
					values.add(file);
				}				
			}
			
		}
		Collections.sort(values);
		
		// Put the data into the list
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_2, android.R.id.text1, values);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String filename = (String) getListAdapter().getItem(position);
		
		if (path.endsWith(File.separator)) {
			filename = path + filename;
		} else {
			filename = path + File.separator + filename;
		}
		if (new File(filename).isDirectory()) {
			Intent intent = new Intent(this, DogActivity.class);
			intent.putExtra("path", filename);
			startActivity(intent);
		} else {
			Intent i = new Intent(this, DogDetailActivity.class);
			//intent.putExtra("path", filename);
			i.putExtra("path", filename);
			startActivity(i);
			/*Toast.makeText(this, filename + " is not a directory",
					Toast.LENGTH_LONG).show();*/
		}
	}

}
