package br.com.thelastsurvivor.activity.game.simplemode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.MainMenuActivity;
import br.com.thelastsurvivor.activity.TheLastSurvivorActivity;
import br.com.thelastsurvivor.activity.player.PlayerActivity;
import br.com.thelastsurvivor.activity.rank.RankActivity;
import br.com.thelastsurvivor.engine.IDrawBehavior;
import br.com.thelastsurvivor.engine.game.weapon.IWeaponBehavior;
import br.com.thelastsurvivor.engine.simpleplayergame.SimplePlayerMode;
import br.com.thelastsurvivor.engine.view.EngineGameView;
import br.com.thelastsurvivor.model.game.Asteroid;
import br.com.thelastsurvivor.model.game.Game;
import br.com.thelastsurvivor.model.game.Shoot;
import br.com.thelastsurvivor.model.game.Spacecraft;
import br.com.thelastsurvivor.provider.player.PlayerProvider;
import br.com.thelastsurvivor.util.MyAudioPlayer;
import br.com.thelastsurvivor.util.Vector2D;

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
    Context context;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        this.init();
	    
        this.setContentView(view);
	  
	}
	
	public void init(){
		
		context = this.getApplicationContext();
		
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
	
	Dialog dialog;
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {

	    	this.view.getGameLoop().state = 2;
	    	
	    	dialog = new Dialog(this, R.style.PauseGameDialogTheme);
			dialog.setContentView(R.layout.pause_game_view);
			
			Button buttonBack = (Button)dialog.findViewById(R.id.buttonBack);
			buttonBack.setOnClickListener(buttonBackListener);  
			
			Button exitGame = (Button)dialog.findViewById(R.id.buttonExit);
			exitGame.setOnClickListener(buttonExitListener);  
			
			dialog.show();
	    	
			
            return true;
	    }
	    return false;
	}
	
	private OnClickListener buttonBackListener = new OnClickListener() {  
		public void onClick(View v) {  
			
			dialog.cancel();
			
			view.getGameLoop().state = 1;
			view.getGameLoop().run();
			
			dialog.cancel();
		}
	};  
	
	private OnClickListener buttonExitListener = new OnClickListener() {  
		public void onClick(View v) {  
			
			//Intent i = new Intent(SimpleGameActivity.this,
			//		MainMenuActivity.class);
			SimpleGameActivity.this.setResult(SavedGameActivity.EXIT_GAME);         

			SimpleGameActivity.this.finish();
		}
	};  
	
	
	public Game preparesGameToSave(){
		Spacecraft spacecraft = getSpacecraftGame();
		List<Asteroid> asteroids = getAsteroidsGame();
			
		return new Game(new Date(), this.view.getEngine().getStartTime(), 
				((SimplePlayerMode)this.view.getEngine()).getPoints(),
				spacecraft, asteroids);
		
	}
	

	private Spacecraft getSpacecraftGame(){

		Vector2D position = this.view.getEngine().getSpacecraft().getPosition();
		Integer life = this.view.getEngine().getSpacecraft().getLife();
		Double angle = this.view.getEngine().getSpacecraft().getAngle();
		List<Shoot> shoots = getShootsGame(); 
		
		return new Spacecraft(position, life, angle, shoots);
		
	}
	
	private List<Shoot> getShootsGame(){
		
		List<Shoot> shoots = new ArrayList<Shoot>();
		
		for(IDrawBehavior shoot : this.view.getEngine().getSpacecraft().getShootsDrawables()){
			shoots.add(new Shoot(shoot.getPosition(), shoot.getAngle(), shoot.getTypeImage()));
		}
		
		return shoots;

	}
	
	private List<Asteroid> getAsteroidsGame(){
		
		List<Asteroid> asteroids = new ArrayList<Asteroid>();
		
		
		for(IDrawBehavior asteroid : this.view.getEngine().getAsteroidsDrawables()){
			asteroids.add(new Asteroid(asteroid.getPosition(), asteroid.getRoute(), asteroid.getLife(), asteroid.getTypeImage()));
		}
		
		return asteroids;
		
	}
	
	private void save(Game game){
		
		
	}
	
	
	

}
