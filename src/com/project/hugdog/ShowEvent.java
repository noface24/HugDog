package com.project.hugdog;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.NotificationManager;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ShowEvent extends Activity implements OnClickListener {
	
	PowerManager pm;
	WakeLock wl;
	KeyguardManager km;
	KeyguardLock kl;
	Ringtone r;
	
	Button btnStop;
	 	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        Log.i("ShowEvent", "onCreate() in DismissLock");
	        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	        km=(KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
	        kl=km.newKeyguardLock("ShowEvent");
	        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP|PowerManager.ON_AFTER_RELEASE, "ShowEvent");
	        wl.acquire(); //wake up the screen
	        kl.disableKeyguard();
	        
	        setContentView(R.layout.sec);
	        
	        btnStop = (Button)findViewById(R.id.btnStop);
	        btnStop.setOnClickListener(this);
	        
	 }
	 	
	 	@Override
		public void onClick(View v) {
		}
	 	
	 	@Override
	 	protected void onResume() {
	 	   
	 	    super.onResume();
	 	    wl.acquire();//must call this!
	 	  /* Uri notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
	        if(notif==null){ // check ��� �������§���¡�������������
	        	notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
	        	if(notif==null){ //  check ��� �������§��ء����������
	        		notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	        	}
	        }
			r = RingtoneManager.getRingtone(getApplicationContext(), notif);
			r.play();
			// Vibrate the mobile phone
			Vibrator vibrator = (Vibrator) this.getApplicationContext()
					.getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(2000);*/
	 	    
	 	    
	 	}
	 	
	 	@Override
		public void onPause(){
			super.onPause();
			/*wl.release();
			if(r.isPlaying()){
				r.stop();
			}*/
		}
	 	public static void cancelNotification(Context ctx, int notifyId) {
	 	    String ns = Context.NOTIFICATION_SERVICE;
	 	    NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
	 	    nMgr.cancel(0);
	 	}

}
