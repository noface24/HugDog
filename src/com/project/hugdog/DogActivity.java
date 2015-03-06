package com.project.hugdog;


import java.util.ArrayList;
import java.util.List;





import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
//import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

//import org.apache.commons.io.FilenameUtils;
import com.project.hugdog.DBClass;
import com.project.hugdog.DBClass.sDog;

public class DogActivity extends Activity {
	private String path;
	private List<String> fileList = new ArrayList<String>();
	//ArrayList<HashMap<String, String>> dogList;
	//List<String> dogList;
	//List<sDog> list;
	List<String> dogList = new ArrayList<String>();
	int dogID; 

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dog);
		
		SimpleCursorAdapter adapter ;
		ListView lisView1 = (ListView)findViewById(R.id.listView1); 		
		DBClass myDb = new DBClass(this);
		final Cursor cursor = myDb.getDog();
		
		
			String[] columnNames = { "name" };
	        int[] resIds = {R.id.ColName};
	        adapter = new SimpleCursorAdapter(this, R.layout.activity_doglist, cursor, columnNames, resIds);
	        lisView1.setAdapter(adapter);
		
	       // OnClick Item
	        lisView1.setOnItemClickListener(new OnItemClickListener() {
			      public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
			    	   
			    	  int dogID = cursor.getInt(cursor.getColumnIndex("_id"));
			    
			    	  Intent i = new Intent(DogActivity.this, DogDetailActivity.class);
						//intent.putExtra("path", filename);
						//i.putExtra("path", filename);
			    	  i.putExtra("dogID", dogID);
						startActivity(i);
						finish();
			      }     
	        });
		 
		 
	        /*adapter = new SimpleCursorAdapter(this, R.layout.activity_doglist, cursor
	        		,new String[] {"name"}
	        		,new int[] {R.id.ColName}); */
	        
	       // setListAdapter(adapter);
		/*if (cursor != null) {
	        cursor.moveToFirst();
	    }

	    while(!cursor.isAfterLast()) {
	    	
	    	//dogID = cursor.getInt(0);
	    	dogList.add(
	    			cursor.getString(0) + " " +
		               cursor.getString(1) );
		               // cursor.getString(2));
		        cursor.moveToNext();
	    }
	    cursor.close();*/
    
		
	 // Put the data into the list
	 		/*ArrayAdapter adapter = new ArrayAdapter(this,
	 				android.R.layout.simple_list_item_1, android.R.id.text1, dogList);
	 		setListAdapter(adapter);*/
		//ArrayAdapter adapter = new ArrayAdapter(this,
		//		android.R.layout.simple_list_item_1, android.R.id.text1, );
		//setListAdapter(adapter);
		
		
		// Use the current directory as title
		/*path = "/sdcard/Hugdog/";
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
				
				
				 * if (!file.startsWith(".")) { values.add(file); }
				 
				
			//	file = FilenameUtils.removeExtension(file);
				if (!file.startsWith(".")&&file.endsWith(".txt")) {
					filename = file;
					
					int pos = filename.lastIndexOf(".");
					if (pos > 0) {
						filename = filename.substring(0, pos);
					}
					values.add(filename);
					
					
				}				
			}
			
		}
		Collections.sort(values);
		
		// Put the data into the list
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);
		setListAdapter(adapter);*/
		
	}

	/*@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//String dogID = dogList.get(position).get(DBClass.KEY_DOGID).toString();
		Toast.makeText(this, String.valueOf(dogID)+DBClass.KEY_DOGID, Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, DogDetailActivity.class);
		//intent.putExtra(DBClass.KEY_DOGID, dogID);
		i.putExtra(DBClass.KEY_DOGID, dogID);
		startActivity(i);
		finish();
		//String MemberID = MebmerList.get(position).get("MemberID").toString();
		//Log.d("Clicked item field", " "+ dogID);//item.getColumnIndex("dogID")); 
		//String dogname =(String) getListAdapter().getItem(position)+".txt";
		//String filename = (String) getListAdapter().getItem(position)+".txt";
		
		if (path.endsWith(File.separator)) {
			filename = path + filename;
		} else {
			filename = path + File.separator + filename;
		}
		
			Intent i = new Intent(this, DogDetailActivity.class);
			//intent.putExtra("path", filename);
			i.putExtra("path", filename);
			startActivity(i);
			finish();
			
		
	}*/

}
