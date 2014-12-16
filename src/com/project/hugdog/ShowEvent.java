package com.project.hugdog;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.PowerManager.WakeLock;
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
	 		if(v.getId() == R.id.btnStop){
	 			this.finish();
	 		}			
		}
	 	
	 	@Override
	 	protected void onResume() {
	 	   
	 	    super.onResume();
	 	    wl.acquire();//must call this!
	 	   Uri notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
	        if(notif==null){ // check ว่า ได้ตั้งเสียงเรียกเข้าไว้หรือไม่
	        	notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
	        	if(notif==null){ //  check ว่า ได้ตั้งเสียงปลุกไว้หรือไม่
	        		notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	        	}
	        }
			r = RingtoneManager.getRingtone(getApplicationContext(), notif);
			r.play();
			// Vibrate the mobile phone
			Vibrator vibrator = (Vibrator) this.getApplicationContext()
					.getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(2000);
	 	    
	 	    
	 	}
	 	
	 	@Override
		public void onPause(){
			super.onPause();
			wl.release();
			if(r.isPlaying()){
				r.stop();
			}
		}

}
