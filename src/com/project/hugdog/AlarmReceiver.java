package com.project.hugdog;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

public class AlarmReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		int eventID = (Integer) intent.getSerializableExtra("eventID");
		String notificationTitle =  (String) intent.getSerializableExtra("reminder");
        String notificationText =  (String) intent.getSerializableExtra("notiText");
        String name =  (String) intent.getSerializableExtra("name");
		Log.d("AlarmReceiver", Integer.valueOf(eventID).toString()+"Called context.startService from AlarmReceiver.onReceive ");
		//Toast.makeText(context, "Time is up!!!!.",
		//		Toast.LENGTH_LONG).show();
		// Vibrate the mobile phone
		//Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		//vibrator.vibrate(2000);
		
		//Intent i = new Intent(context, ShowEvent.class);
		//i.putExtra("eventID", eventID);
		//i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//context.startActivity(i);
		//PendingIntent detailIntent = PendingIntent.getActivity(context, 0, i, 0);
		
		Intent intentNoti = new Intent(context,VaccineActivity.class);
		intentNoti.putExtra("eventID", eventID);	
		intentNoti.putExtra("name", name);
		intentNoti.putExtra("vacID", String.valueOf(eventID));
		PendingIntent detailIntent = PendingIntent.getActivity(context, eventID, intentNoti, 0);
		
		Intent intentPost = new Intent(context,PostActivity.class);
		intentPost.putExtra("eventID", eventID);
		intentPost.putExtra("notiText", notificationText);
		intentPost.putExtra("reminder", notificationTitle);
		intentPost.putExtra("name", name);
		intentPost.putExtra("vacID", String.valueOf(eventID));
		
		//intentPost.putExtra("dogName", dogName);
		PendingIntent postIntent = PendingIntent.getActivity(context, eventID, intentPost, 0);
		
		
		
		//////Notification/////
		Uri notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if(notif==null){ // check ว่า ได้ตั้งเสียงเรียกเข้าไว้หรือไม่
        	notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        	if(notif==null){ //  check ว่า ได้ตั้งเสียงปลุกไว้หรือไม่
        		notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        	}
        }
		NotificationManager notificationManager;
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
               
        //Toast.makeText(context, notificationTitle+notificationText,Toast.LENGTH_LONG).show();
        Notification notification = new NotificationCompat.Builder(context)
        		.setSmallIcon(R.drawable.vaccine)
        		.setContentTitle(notificationTitle)
        		.setContentText(notificationText)
        		//.setAutoCancel(true)
        		.addAction(R.drawable.vaccine, "ฉีดแล้ว", detailIntent)
        		.addAction(android.R.drawable.ic_delete , "เลื่อนวัน", postIntent)        		
        		.setContentIntent(detailIntent)
        		.setLights(Color.WHITE, 3000, 3000)
        		.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
        		.setSound(notif)
        		.build();
       // notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        notificationManager.notify(0, notification);
        
        
		
	}

}
