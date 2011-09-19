package br.com.thelastsurvivor.activity.game.simplemode;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.simpleplayergame.SimplePlayerMode;
import br.com.thelastsurvivor.engine.view.EngineGameView;
import br.com.thelastsurvivor.util.MyAudioPlayer;

public class SimpleGameActivity extends Activity implements SensorEventListener, OnGestureListener{
	
	private SensorManager manager;
    private Sensor accelerometer;
    private GestureDetector gestureScanner;
    private Vibrator vibrator;
    private MyAudioPlayer audioPlayer;
    
    private EngineGameView view;
    private SimplePlayerMode engine;
    
    private WakeLock wakeLock;
    private Long beforeTime;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        this.init();
	    
        this.setContentView(view);
	  
	}
	
	public void init(){
		
		this.audioPlayer = new MyAudioPlayer(this, R.raw.singleplayer_soundtrack);
		this.audioPlayer.start();
		
		this.manager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
		
	    this.accelerometer = this.manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
	    this.manager.registerListener (this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
	      
	    this.gestureScanner = new GestureDetector(this);
	    	    
	    final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "");
        this.wakeLock.acquire();      
        
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        
        final Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        
        this.beforeTime = 0L;
		
		this.engine = new SimplePlayerMode(this, vibrator, display);
		
    	this.view = new EngineGameView(this,engine);
	}

	
	@Override
	public void onPause() {
        super.onPause();
        
        this.manager.unregisterListener(this); 
	}

    @Override
    public void onResume() {
        super.onResume();
        
        this.manager.registerListener(this, 
            accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	this.audioPlayer.fechar();
    	
    	this.wakeLock.release();
    }
	    

	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;	
        
		this.engine.getSpacecraft().updateOrientation((event.values[1]),(event.values[0]));   
	}
	
	
    @Override
    public boolean onTouchEvent(MotionEvent event){
    	
    	return this.gestureScanner.onTouchEvent(event);
    	
    }
	    
    @Override
	public void onShowPress(MotionEvent arg0) {}
	    
	@Override
	public boolean onDown(MotionEvent arg0) {
		
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg3,
			float arg4) {
		
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		
		return false;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
	
			this.engine.getSpacecraft().newShoot();
			
			
		
		return true;
	}

	

}
