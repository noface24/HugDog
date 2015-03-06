package com.project.hugdog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class HisListActivity extends Activity {
	List<String> dogList = new ArrayList<String>();
	int dogID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_his_list);
		
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
		    
		    	  Intent i = new Intent(HisListActivity.this, HisCalActivity.class);
		    	  GlobalVariables.dogID = dogID;
		    	  GlobalVariables.dogName = cursor.getString(cursor.getColumnIndex("name"));
		    	  i.putExtra("dogID", dogID);
					startActivity(i);
					finish();
		      }     
        });
	}
}
