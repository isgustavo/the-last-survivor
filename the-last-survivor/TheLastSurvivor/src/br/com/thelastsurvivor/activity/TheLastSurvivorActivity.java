package br.com.thelastsurvivor.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.player.PlayerActivity;

public class TheLastSurvivorActivity extends Activity {

	private static final Integer SPLASH_DURATION = 1000;
	private WakeLock wakeLock;
 
	//private TheLastSurvivorView view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.init();

		this.setContentView(R.layout.the_last_survivor_view);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				
				Intent i = new Intent(TheLastSurvivorActivity.this,
						PlayerActivity.class);
				TheLastSurvivorActivity.this.startActivity(i);

				TheLastSurvivorActivity.this.finish();

			}
		}, SPLASH_DURATION);

	}
	
	public void init(){
	
		final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	    this.wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "");
	    this.wakeLock.acquire(); 
	        
    }
	
	
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	
    	this.wakeLock.release();
    }
    
    
    
  
}