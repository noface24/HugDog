package com.project.hugdog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class PostActivity extends Activity {
	/*
	 * Bundle b = getIntent().getExtras(); String text =
	 * b.getString("notiText"); String title = b.getString("reminder");
	 */
	DBClass myDB = new DBClass(this);
	SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getIntent().getExtras();
		String text = b.getString("notiText");
		String title = b.getString("reminder");
		int eventID = b.getInt("eventID");
		setAlarmNext(text, title,eventID);
		
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(0);
		finish();

	}

	public void setAlarmNext(String title, String notiText,int eventID) {

		/*
		 * textAlarmPrompt.setText( "\n\n***\n" + "Set Alarm @ " +
		 * targetCal.getTime() + "\n" + "***\n");
		 */

		/*
		 * Toast.makeText(getBaseContext(),
		 * String.valueOf(targetCal.getTimeInMillis()),
		 * Toast.LENGTH_LONG).show();
		 */
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_MONTH, 1);

		Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
		intent.putExtra("notiText", notiText);
		intent.putExtra("reminder", title);
		intent.putExtra("eventID", eventID);
		// intent.putExtra("dogName", dogName);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				getBaseContext(), eventID, intent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		// alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+
		// (2 * 1000), pendingIntent);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				pendingIntent);

		Toast.makeText(getBaseContext(), "เลื่อนวันแล้ว " + calendar.getTime(),
				Toast.LENGTH_LONG).show();
		Log.d("alarm", String.valueOf(eventID));
		updateVaccine(eventID,calendar);

	}
	public void updateVaccine(int eventID,Calendar vacCal){
		String date = dt.format(vacCal.getTime());
		try{
			long flg = myDB.updateVac(eventID,null,null,date,null,0);			
			Log.d("alarm", String.valueOf(flg)+date);
		}
		catch (Exception e) {
			Log.d("GetFail",e.getMessage());					
	    
		}
	}
}
