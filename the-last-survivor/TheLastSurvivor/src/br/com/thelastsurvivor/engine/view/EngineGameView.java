package br.com.thelastsurvivor.engine.view;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import br.com.thelastsurvivor.engine.EngineGame;
import br.com.thelastsurvivor.engine.GameLoopThread;

public class EngineGameView extends SurfaceView implements SurfaceHolder.Callback {

	private Long lastUpdate;
    private Long sleepTime;
	
    //engineGame
    
    private SurfaceHolder surfaceHolder;
    private Context context;
    
    private EngineGame engine;
    private GameLoopThread gameLoop;
	
	public EngineGameView(Context context, EngineGame engine) {
		super(context);
		// TODO Auto-generated constructor stub
		Log.d("EngineGameView", "01");
		
		this.context = context;
		this.engine = engine;
		init();
	}
	
	

	
	
	public void init(){
		
		
		
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		
		Log.d("EngineGameView", "02");
		//this.surfaceHolder.addCallback(this);
		gameLoop = new GameLoopThread(surfaceHolder, context, engine);
		//gameLoop = new GameLoopThread(this);
		
		//Thread thread = new Thread(gameLoop);
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		Log.d("EngineGameView", "03");
		if(this.gameLoop.state == GameLoopThread.PAUSED){
            //When game is opened again in the Android OS
			this.gameLoop = new GameLoopThread(getHolder(), context, engine);
			this.gameLoop.start();
        }else{
            //creating the game Thread for the first time
        	this.gameLoop.start();
        }
    
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		 Boolean retry = true;
	    
		 
	     this.gameLoop.state = gameLoop.PAUSED;
	     while (retry) {
            try {
            	this.gameLoop.join();
                retry = false;
            } catch (InterruptedException e) {
            }
	     }
	}
	
	

	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(Long sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	
	
	

}
