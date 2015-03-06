package com.project.hugdog;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class postPone extends Activity {
	 Bundle b = getIntent().getExtras();	
	

	public void main(String [ ] args)
	{
			
		String text = b.getString("notiText");
		String title = b.getString("reminder");
		String vacID = b.getString("vacID");
		setAlarmNext(title, text); 
	}
		
		
		
		
	public  void setAlarmNext(String title, String notiText) {
		
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
		//intent.putExtra("eventID", eventID1);
		//intent.putExtra("dogName", dogName);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				getBaseContext(), GlobalVariables.eventID, intent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		// alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+
		// (2 * 1000), pendingIntent);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
				pendingIntent);
		
		 Toast.makeText(getBaseContext(),
		 "เลื่อนวันแล้ว "+calendar.getTime(),
		 Toast.LENGTH_LONG).show();
		

	}

	//
}
