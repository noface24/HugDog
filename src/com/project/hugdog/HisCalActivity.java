package com.project.hugdog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HisCalActivity extends FragmentActivity {

	private boolean undo = false;
	private CaldroidFragment caldroidFragment;
	private CaldroidFragment dialogCaldroidFragment;
	int dogID;
	HashMap<String, String> map ;
	Date date;
	DBClass myDb = new DBClass(this);
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	String hisID="";
	String hisDate;
	List<String> dogList = new ArrayList<String>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_his_cal);
		
		
		
		//ListView lisView1 = (ListView)findViewById(R.id.listView1);
		//String[] columnNames = { "birth" };
		//int[] resIds = {R.id.ColName};
		Bundle b = getIntent().getExtras();	
		dogID =b.getInt("dogID");
		final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		final ListView lisView1 = (ListView)findViewById(R.id.listView1); 
		
		final DBClass myDb = new DBClass(this);		
		
/*	
       // OnClick Item
        lisView1.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
		    	   
		    	  int dogID = cursor.getInt(cursor.getColumnIndex("_id"));
		    
		    	  //Intent i = new Intent(HisCalActivity.this, HisCalActivity.class);
		    	 // GlobalVariables.dogID = dogID;
		    	 // GlobalVariables.dogName = cursor.getString(cursor.getColumnIndex("name"));
		    	 // i.putExtra("dogID", dogID);
				//	startActivity(i);
				//	finish();
		      }     
        });*/
		
		
		
		// Setup caldroid fragment
		// **** If you want normal CaldroidFragment, use below line ****
		//caldroidFragment = new CaldroidFragment();

		// //////////////////////////////////////////////////////////////////////
		// **** This is to show customized fragment. If you want customized
		// version, uncomment below line ****
		 caldroidFragment = new CaldroidSampleCustomFragment();

		// Setup arguments
		 caldroidFragment.refreshView();	
		// If Activity is created after rotation
		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState,
					"CALDROID_SAVED_STATE");
		}
		// If activity is created from fresh
		else {
			Bundle args = new Bundle();
			Calendar cal = Calendar.getInstance();
			args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

			// Uncomment this to customize startDayOfWeek
			// args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
			// CaldroidFragment.TUESDAY); // Tuesday
			//caldroidFragment.setArguments(args);
		}

		setCustomResourceForDates();
		caldroidFragment.refreshView();	

		// Attach to the activity
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();

		// Setup listener
		final CaldroidListener listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				//Toast.makeText(getApplicationContext(), formatter.format(date),Toast.LENGTH_SHORT).show();
				GlobalVariables.date1 =formatter.format(date);
				GlobalVariables.date = date;
				hisDate = df.format(date.getTime());
				
				final Cursor cursor = myDb.getHisDate(dogID, df.format(date));
				if(cursor.moveToFirst()){
					//String[] columnNames = { "name","detail" };
			       // int[] resIds = {R.id.ColName,R.id.ColDetail};
			        //final SimpleCursorAdapter adapter ;		
			        //adapter = new SimpleCursorAdapter(HisCalActivity.this, R.layout.listhis, cursor, columnNames, resIds);
			        //lisView1.setAdapter(adapter);
			        showDialogBox();
				}else{
					showNewDialogBox();
				}
				//showDialogBox();
				//.Toast.makeText(getBaseContext(), cursor.toString(), Toast.LENGTH_SHORT).show();
				
		        //Toast.makeText(getBaseContext(), hisDate, Toast.LENGTH_SHORT).show();
				//showDialogBox();
				
			}

			@Override
			public void onChangeMonth(int month, int year) {
				String text = "month: " + month + " year: " + year;
				//Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onLongClickDate(Date date, View view) {
				//Toast.makeText(getApplicationContext(),"Long click " + formatter.format(date),Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCaldroidViewCreated() {
				if (caldroidFragment.getLeftArrowButton() != null) {
				//	Toast.makeText(getApplicationContext(),"Caldroid view is created", Toast.LENGTH_SHORT).show();
					
				}
			}

		};

		// Setup Caldroid
		caldroidFragment.setCaldroidListener(listener);

		final TextView textView = (TextView) findViewById(R.id.textview);

		final Button customizeButton = (Button) findViewById(R.id.customize_button);
		
		// Customize the calendar
		////Add
		customizeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {				
					
				Intent newActivity = new Intent(HisCalActivity.this,HisAddActivity.class);
				newActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				newActivity.putExtra("dogID", dogID);
				startActivity(newActivity);
				finish();
				
				
				
				/*if (undo) {
					customizeButton.setText(getString(R.string.customize));
					textView.setText("");

					// Reset calendar
					caldroidFragment.clearDisableDates();
					caldroidFragment.clearSelectedDates();
					caldroidFragment.setMinDate(null);
					caldroidFragment.setMaxDate(null);
					caldroidFragment.setShowNavigationArrows(true);
					caldroidFragment.setEnableSwipe(true);
					caldroidFragment.refreshView();					
					undo = false;
					return;
				}

				// Else
				undo = true;
				customizeButton.setText(getString(R.string.undo));
				Calendar cal = Calendar.getInstance();

				// Min date is last 7 days
				cal.add(Calendar.DATE, -7);
				Date minDate = cal.getTime();

				// Max date is next 7 days
				cal = Calendar.getInstance();
				cal.add(Calendar.DATE, 14);
				Date maxDate = cal.getTime();

				// Set selected dates
				// From Date
				cal = Calendar.getInstance();
				cal.add(Calendar.DATE, 2);
				Date fromDate = cal.getTime();

				// To Date
				cal = Calendar.getInstance();
				cal.add(Calendar.DATE, 3);
				Date toDate = cal.getTime();

				// Set disabled dates
				ArrayList<Date> disabledDates = new ArrayList<Date>();
				for (int i = 5; i < 8; i++) {
					cal = Calendar.getInstance();
					cal.add(Calendar.DATE, i);
					disabledDates.add(cal.getTime());
				}

				// Customize
				caldroidFragment.setMinDate(minDate);
				caldroidFragment.setMaxDate(maxDate);
				caldroidFragment.setDisableDates(disabledDates);
				caldroidFragment.setSelectedDates(fromDate, toDate);
				caldroidFragment.setShowNavigationArrows(false);
				caldroidFragment.setEnableSwipe(false);

				caldroidFragment.refreshView();

				// Move to date
				// cal = Calendar.getInstance();
				// cal.add(Calendar.MONTH, 12);
				// caldroidFragment.moveToDate(cal.getTime());

				String text = "Today: " + formatter.format(new Date()) + "\n";
				text += "Min Date: " + formatter.format(minDate) + "\n";
				text += "Max Date: " + formatter.format(maxDate) + "\n";
				text += "Select From Date: " + formatter.format(fromDate)
						+ "\n";
				text += "Select To Date: " + formatter.format(toDate) + "\n";
				for (Date date : disabledDates) {
					text += "Disabled Date: " + formatter.format(date) + "\n";
				}

				textView.setText(text);*/
			}
		});

		Button showDialogButton = (Button) findViewById(R.id.show_dialog_button);

		final Bundle state = savedInstanceState;
		showDialogButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Setup caldroid to use as dialog
				dialogCaldroidFragment = new CaldroidFragment();
				dialogCaldroidFragment.setCaldroidListener(listener);

				// If activity is recovered from rotation
				final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
				if (state != null) {
					dialogCaldroidFragment.restoreDialogStatesFromKey(
							getSupportFragmentManager(), state,
							"DIALOG_CALDROID_SAVED_STATE", dialogTag);
					Bundle args = dialogCaldroidFragment.getArguments();
					if (args == null) {
						args = new Bundle();
						dialogCaldroidFragment.setArguments(args);
					}
					args.putString(CaldroidFragment.DIALOG_TITLE,
							"Select a date");
				} else {
					// Setup arguments
					Bundle bundle = new Bundle();
					// Setup dialogTitle
					bundle.putString(CaldroidFragment.DIALOG_TITLE,
							"Select a date");
					dialogCaldroidFragment.setArguments(bundle);
				}

				dialogCaldroidFragment.show(getSupportFragmentManager(),
						dialogTag);
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		caldroidFragment.refreshView();	
	}
	
	
	

	/**
	 * Save current states of the Caldroid here
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		caldroidFragment.refreshView();	
		if (caldroidFragment != null) {
			caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
		}

		if (dialogCaldroidFragment != null) {
			dialogCaldroidFragment.saveStatesToKey(outState,
					"DIALOG_CALDROID_SAVED_STATE");
		}
	}
	
	//Dialog box
	public void showDialogBox() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// AlertDialog.Builder builder = new AlertDialog.Builder(context);

		    builder.setTitle("วันที่ :"+GlobalVariables.date1);
		    //builder.setMessage("เวลา :" +GlobalVariables.formattedDate);
		    builder.setItems(new CharSequence[]
		            {"ดูประวัติการรักษา", "ยกเลิก",},
		            new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int which) {
		                    // The 'which' argument contains the index position
		                    // of the selected item
		                    switch (which) {
		                        case 0:
		                        	 Intent newActivity1 = new Intent(HisCalActivity.this,
        	                		HistoryActivity.class);
                        	  newActivity1.putExtra("dogID", dogID);
                        	  newActivity1.putExtra("hisDate", hisDate);
                        	  //Toast.makeText(getBaseContext(), "CAl "+hisDate, Toast.LENGTH_SHORT).show();
        						newActivity1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        						startActivity(newActivity1);
        						finish();
		                            break;
		                        case 1:
		                        	
		                        	/*  Intent newActivity1 = new Intent(HisCalActivity.this,
		        	                		HistoryActivity.class);
		                        	  newActivity1.putExtra("dogID", dogID);
		                        	  newActivity1.putExtra("hisDate", hisDate);
		                        	  //Toast.makeText(getBaseContext(), "CAl "+hisDate, Toast.LENGTH_SHORT).show();
		        						newActivity1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        						startActivity(newActivity1);*/
		                            break;
		                        /*case 2:
		                           // Toast.makeText(context, "clicked 3", 0).show();
		                            break;*/
		                       
		                    }
		                }
		            });
		    builder.create().show();
	}
	
	//Dialog box
		public void showNewDialogBox() {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// AlertDialog.Builder builder = new AlertDialog.Builder(context);

			    builder.setTitle("วันที่ :"+GlobalVariables.date1);
			    //builder.setMessage("เวลา :" +GlobalVariables.formattedDate);
			    builder.setItems(new CharSequence[]
			            {"เพิ่มประวัติการรักษา", "ยกเลิก",},
			            new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int which) {
			                    // The 'which' argument contains the index position
			                    // of the selected item
			                    switch (which) {
			                        case 0:
			                        	Intent newActivity = new Intent(HisCalActivity.this,HisAddActivity.class);
			      							newActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			      							newActivity.putExtra("dogID", dogID);		      									      							
			      							startActivity(newActivity);
			      							finish();
			                            break;
			                        case 1:
			                        	
			                        	/*  Intent newActivity1 = new Intent(HisCalActivity.this,
			        	                		HistoryActivity.class);
			                        	  newActivity1.putExtra("dogID", dogID);
			                        	  newActivity1.putExtra("hisDate", hisDate);
			                        	  //Toast.makeText(getBaseContext(), "CAl "+hisDate, Toast.LENGTH_SHORT).show();
			        						newActivity1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        						startActivity(newActivity1);*/
			                            break;
			                        /*case 2:
			                           // Toast.makeText(context, "clicked 3", 0).show();
			                            break;*/
			                       
			                    }
			                }
			            });
			    builder.create().show();
		}
	
	private void setCustomResourceForDates() {

		Date hisDate;
	
		
		Calendar cal = Calendar.getInstance();

		// Min date is last 7 days
		cal.add(Calendar.DATE, -7);
		Date blueDate = cal.getTime();

		// Max date is next 7 days
		/*cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 7);
		Date greenDate = cal.getTime();
		
*/
		if (caldroidFragment != null) {
			//caldroidFragment.setBackgroundResourceForDate(R.drawable.vaccine,blueDate);
			//caldroidFragment.setBackgroundResourceForDate(R.color.blue,blueDate);
			//		blueDate);
			//caldroidFragment.setBackgroundResourceForDate(R.color.green,greenDate);
			//caldroidFragment.setTextColorForDate(R.color.black, blueDate);
			//caldroidFragment.setTextColorForDate(R.color.white, greenDate);			
			try{
				final Cursor cursor = myDb.getHis(dogID);
				if(cursor != null)
			 	{
			 	    if (cursor.moveToFirst()) {
			 	        do {
			 	        	//map = new HashMap<String, String>(); 
			 	        	//map.put("date", cursor.getString(cursor.getColumnIndex("date")));
			 	        	String strDate;
			 	        	strDate = cursor.getString(cursor.getColumnIndex("date"));
			 	        	String spdate[] = strDate.split("-");
			 	        	cal.set(Integer.parseInt(spdate[0]), Integer.parseInt(spdate[1])-1, Integer.parseInt(spdate[2]));
			 	        	hisDate = cal.getTime();
			 	        	/*HashMap<String, Object> extraData = caldroidFragment.getExtraData();
			 	        	extraData.put("YOUR_CUSTOM_DATA_KEY1", hisDate);
			 	        	extraData.put("YOUR_CUSTOM_DATA_KEY2", hisDate);*/

			 	       	// Refresh view
			 	       	caldroidFragment.refreshView();
			 	        	caldroidFragment.setBackgroundResourceForDate(R.drawable.file,hisDate);
			 	        	
			 	        } while (cursor.moveToNext());
			 	    }
			 	}
			}catch(Exception e) {
				Toast.makeText(getBaseContext(), "Not success"+e.getMessage(), Toast.LENGTH_SHORT);
			}
		}
	}
	

}
