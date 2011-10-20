package br.com.thelastsurvivor.activity.game.simplemode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.MainMenuActivity;
import br.com.thelastsurvivor.engine.game.weapon.EffectShoot;
import br.com.thelastsurvivor.engine.game.weapon.IWeaponBehavior;
import br.com.thelastsurvivor.engine.simpleplayergame.EngineGame;
import br.com.thelastsurvivor.engine.simpleplayergame.GameLoopThread;
import br.com.thelastsurvivor.engine.util.IDrawBehavior;
import br.com.thelastsurvivor.engine.view.EngineGameView;
import br.com.thelastsurvivor.model.game.Asteroid;
import br.com.thelastsurvivor.model.game.Effect;
import br.com.thelastsurvivor.model.game.Game;
import br.com.thelastsurvivor.model.game.PowerUp;
import br.com.thelastsurvivor.model.game.Shoot;
import br.com.thelastsurvivor.model.game.Spacecraft;
import br.com.thelastsurvivor.provider.game.AsteroidProvider;
import br.com.thelastsurvivor.provider.game.EffectProvider;
import br.com.thelastsurvivor.provider.game.GameProvider;
import br.com.thelastsurvivor.provider.game.PowerUpProvider;
import br.com.thelastsurvivor.provider.game.ShootProvider;
import br.com.thelastsurvivor.provider.game.SpacecraftProvider;
import br.com.thelastsurvivor.util.DateTimeUtil;
import br.com.thelastsurvivor.util.FT2FontTextView;
import br.com.thelastsurvivor.util.MyAudioPlayer;
import br.com.thelastsurvivor.util.Vector2D;

public class SimpleGameActivity extends Activity implements SensorEventListener, OnGestureListener{
	
	private SensorManager manager;
    private Sensor accelerometer;
    private GestureDetector gestureScanner;
    private Vibrator vibrator;
    private MyAudioPlayer audioPlayer;
    
    private EngineGameView view;
    private EngineGame engine;
    
    private Integer player;
    
    private WakeLock wakeLock;
    private Long beforeTime;
    Context context;
    private Dialog dialog;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle s = this.getIntent().getExtras().getBundle("idPlayerGame");
		this.player = s.getInt("id_player");
		
		//Log.d("ID PLAYER","."+player);
		
        this.init();
	    
        this.setContentView(view);

	}
	
	public void init(){
		
		context = this.getApplicationContext();
		
		this.audioPlayer = new MyAudioPlayer(this, R.raw.singleplayer_soundtrack);
		this.audioPlayer.start();
		
		this.manager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
		
		//this.accelerometer = this.manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		this.accelerometer = this.manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    this.manager.registerListener (this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
	      
	    this.gestureScanner = new GestureDetector(this);
	    	    
	    final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "");
        this.wakeLock.acquire();      
        
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        
        final Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        
        this.beforeTime = 0L;
		
		this.engine = new EngineGame(this, vibrator, display);
		
    	this.view = new EngineGameView(this,engine);
    	
    	WindowManager.LayoutParams lp = getWindow().getAttributes();
    	lp.screenBrightness = 100 / 100.0f;
    	getWindow().setAttributes(lp);
	}
	
	
	public void endGame(){
		
		this.view.getGameLoop().state = 2;
    	//Looper.prepare();
    	
    	//dialog = new Dialog(this, R.style.PauseGameDialogTheme){
    		
    		//public boolean onKeyDown(int keyCode, KeyEvent event){
    		//		return false;
    		//}

    	//};
    	
		//dialog.setContentView(R.layout.result_game_view);
		
		//final FT2FontTextView scoreGame = (FT2FontTextView)dialog.findViewById(R.id.points_game);
		//scoreGame.setText(" "+this.engine.getSpacecraft().getPoints()+" pt");
	
		//final FT2FontTextView timeGame = (FT2FontTextView)dialog.findViewById(R.id.time_game);
		//timeGame.setText(" "+this.engine.getTimeGame()+" min");
		//Log.d("LOG","ENDGAME");
		//dialog.show();
		
		
		
		
		//save result
		
		Intent i = new Intent(SimpleGameActivity.this, ResultGameActivity.class);
		
		Bundle s = new Bundle();
	    s.putInt("id_player", player);
	    s.putInt("points", this.engine.getSpacecraft().getPoints());
	    s.putLong("time", this.engine.getTimeGame());
	    
	    i.putExtra("playerBundle",s);
		
		startActivity(i);
		
		SimpleGameActivity.this.finish();
		
		
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
    	//this.view.getGameLoop().destroy();
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
	public void onLongPress(MotionEvent arg0) {
		
			//new AudioGame(context, R.raw.laser_single, AudioGame.NOT_REPEATS).start();
			this.engine.getSpacecraft().newShoot();		
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		
		return false;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
	
		//new AudioGame(context, R.raw.laser_single, AudioGame.NOT_REPEATS).start();
		this.engine.getSpacecraft().newShoot();		
		
		return true;
	}
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	    	
	    	this.view.getGameLoop().state = 2;
	    	
	    	dialog = new Dialog(this, R.style.PauseGameDialogTheme){
	    		
	    		public boolean onKeyDown(int keyCode, KeyEvent event){
	    				return false;
	    		}

	    	};
	    	
			dialog.setContentView(R.layout.pause_game_view);
			
			final FT2FontTextView scoreGame = (FT2FontTextView)dialog.findViewById(R.id.score_pause_game);
			scoreGame.setText(context.getString(R.string.score)+" "+this.engine.getSpacecraft().getPoints()+" pt");
			
			final FT2FontTextView lifeGame = (FT2FontTextView)dialog.findViewById(R.id.life_pause_game);
			lifeGame.setText(context.getString(R.string.life)+" "+this.engine.getSpacecraft().getLife()+" hp");
			
			final FT2FontTextView timeGame = (FT2FontTextView)dialog.findViewById(R.id.time_pause_game);
			timeGame.setText(context.getString(R.string.time)+" "+this.engine.getTimeGame()+" min");
			
			
			ImageView image = (ImageView)dialog.findViewById(R.id.imagebackgraund);
			image.setAlpha(50);
			
			Button backGame = (Button)dialog.findViewById(R.id.buttonBack);
			backGame.setOnClickListener(buttonBackListener);  
			
			Button saveGame = (Button)dialog.findViewById(R.id.buttonSave);
			saveGame.setOnClickListener(buttonSaveListener);  
			
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
			dialog.dismiss();
			
			view.gameLoop = new GameLoopThread(view.getHolder(), context, engine);
			view.gameLoop.start();  //setGameLoop().state = 1;
		}
	};  
	
	private OnClickListener buttonSaveListener = new OnClickListener() {  
		public void onClick(View v) {  

			dialog = new Dialog(SimpleGameActivity.this, R.style.PauseGameDialogTheme);
			dialog.setContentView(R.layout.features_wait_player_view);
			   
			dialog.show();
			save(preparesGameToSave());
			
			dialog.cancel();
			dialog.dismiss();
			
			Intent i = new Intent(SimpleGameActivity.this, MainMenuActivity.class);
			startActivity(i);
			
			SimpleGameActivity.this.finish();
		}
	};  
	
	private OnClickListener buttonExitListener = new OnClickListener() {  
		public void onClick(View v) {  
			
			Intent i = new Intent(SimpleGameActivity.this, MainMenuActivity.class);
			startActivity(i);
			
			SimpleGameActivity.this.finish();
			
			//SimpleGameActivity.this.setResult(SavedGameActivity.EXIT_GAME);         

			//SimpleGameActivity.this.finish();
		}
	};  
	
	
	public Game preparesGameToSave(){
		
		return new Game(player, new Date(), engine.getRealTimeGame(),
				getSpacecraftGame(), getAsteroidsGame(),getPowerUpsGame(), getEffectGame());
		
	}


	private Spacecraft getSpacecraftGame(){

		Vector2D position = this.engine.getSpacecraft().getPosition();
		Double angle = this.engine.getSpacecraft().getAngle();
		Integer life = this.engine.getSpacecraft().getLife();
		Integer points = this.engine.getSpacecraft().getPoints();
	
		return new Spacecraft(position, angle, life, points, getShootsGame());
		
	}
	
	private List<Shoot> getShootsGame(){
		
		List<Shoot> shoots = new ArrayList<Shoot>();
		
		for(IDrawBehavior shoot : this.engine.getSpacecraft().getShootsDrawables()){
			if(shoot.isAlive()){
				shoots.add(new Shoot(shoot.getPosition(), shoot.getAngle()));
			}		
		}
		
		return shoots;

	}
	
	private List<Asteroid> getAsteroidsGame(){
		
		List<Asteroid> asteroids = new ArrayList<Asteroid>();
		
		for(IDrawBehavior asteroid : this.engine.getAsteroidsDrawables()){
			asteroids.add(new Asteroid(asteroid.getPosition(), asteroid.getAngle(), asteroid.getLife(), 
					asteroid.getRoute(),asteroid.getTypeImage()));
		}
		
		return asteroids;
		
	}
	
	private List<PowerUp> getPowerUpsGame(){
		
		List<PowerUp> powerUps = new ArrayList<PowerUp>();
		
		for(IDrawBehavior powerUp : this.engine.getPowerUps()){
			powerUps.add(new PowerUp(powerUp.getPosition(), powerUp.getRoute()));
		}
		
		return powerUps;
		
	}
	
	private List<Effect> getEffectGame(){
		
		List<Effect> effecs = new ArrayList<Effect>();
		
		for(IWeaponBehavior  effect : this.engine.getShootsEffect()){
			 effecs.add(new Effect( effect.getPosition(), ((EffectShoot)effect).getStartTime()));
		}
		
		return effecs;
		
	}
	
	private boolean save(Game game){
		
		ContentValues values = new ContentValues();

		values.put(GameProvider.ID_PLAYER, player);
		values.put(GameProvider.DATE_PAUSE, DateTimeUtil.DateToString(game.getDate()));
		values.put(GameProvider.TIME_PAUSE, game.getRunTime());
		
		getContentResolver().insert(GameProvider.CONTENT_URI, values);
		
		setIdGame(game);
		
		values = new ContentValues();
		
		Log.d("SPACECRAFT GAME ID", "."+ game.getId());
		
		values.put(SpacecraftProvider.ID_GAME, game.getId());
		values.put(SpacecraftProvider.POS_X, game.getSpacecraft().getPosition().getX());
		values.put(SpacecraftProvider.POS_Y, game.getSpacecraft().getPosition().getY());				
		values.put(SpacecraftProvider.ANGLE,  game.getSpacecraft().getAngle());
		values.put(SpacecraftProvider.LIFE,	 game.getSpacecraft().getLife());	
		values.put(SpacecraftProvider.POINTS,  game.getSpacecraft().getPoints());
		
		getContentResolver().insert(SpacecraftProvider.CONTENT_URI, values);
		
		setIdSpacecraft(game);
		
		for (Shoot shoot : game.getSpacecraft().getShoots()) {
			
			values = new ContentValues();
			
			values.put(ShootProvider.ID_SPACECRAFT, game.getSpacecraft().getId());
			values.put(ShootProvider.POS_X, shoot.getPosition().getX());
			values.put(ShootProvider.POS_Y, shoot.getPosition().getY());
			values.put(ShootProvider.ANGLE, shoot.getAngle());
			
			getContentResolver().insert(ShootProvider.CONTENT_URI, values);
		}
		
		for(Asteroid asteroid : game.getAsteroids()){
			
			values = new ContentValues();
			
			values.put(AsteroidProvider.ID_GAME, game.getId());
			values.put(AsteroidProvider.POS_X, asteroid.getPosition().getX());
			values.put(AsteroidProvider.POS_Y, asteroid.getPosition().getY());
			values.put(AsteroidProvider.ANGLE, asteroid.getAngle());
			values.put(AsteroidProvider.LIFE, asteroid.getLife());
			values.put(AsteroidProvider.ROUTE, asteroid.getRoute());
			values.put(AsteroidProvider.TYPE, asteroid.getType());
					
			getContentResolver().insert(AsteroidProvider.CONTENT_URI, values);
		}
		
		
		for(PowerUp power : game.getPowerUps()){
			values = new ContentValues();
			
			values.put(PowerUpProvider.ID_GAME, game.getId());
			values.put(PowerUpProvider.POS_X, power.getPosition().getX());
			values.put(PowerUpProvider.POS_Y, power.getPosition().getY());
			values.put(PowerUpProvider.ROUTE, power.getRoute());
					
			getContentResolver().insert(PowerUpProvider.CONTENT_URI, values);
		}
		
		for(Effect effect : game.getEffects()){
			values = new ContentValues();
			
			values.put(EffectProvider.ID_GAME, game.getId());
			values.put(EffectProvider.POS_X, effect.getPosition().getX());
			values.put(EffectProvider.POS_Y, effect.getPosition().getY());
			values.put(EffectProvider.TIME, effect.getTime());
					
			getContentResolver().insert(EffectProvider.CONTENT_URI, values);
		}
		
		
		return true;
	}
	
	private void setIdGame(Game game){
		
		Cursor c = getContentResolver().query(GameProvider.CONTENT_URI, 
				null,GameProvider.ID_PLAYER +" = " + player 
					+" AND "+ GameProvider.DATE_PAUSE+ " = '" +DateTimeUtil.DateToString(game.getDate())+"' " 
					+ " AND "+ GameProvider.TIME_PAUSE+ " = " +game.getRunTime(), null, null);
		Log.d("GAME ID","WHILE");
			while(c.moveToNext()){
				Log.d("GAME ID", "."+c.getInt(0));
				game.setId(c.getInt(0));
			}
			
			Log.d("RETURN GAME ID", "."+ game.getId());
	}
	
	private void setIdSpacecraft(Game game){
		Cursor c = getContentResolver().query(SpacecraftProvider.CONTENT_URI, 
				null,SpacecraftProvider.ID_GAME + "= " +game.getId() , null, null);
		
			while(c.moveToNext()){
				game.getSpacecraft().setId(c.getInt(0));
			}
		
	}

}
