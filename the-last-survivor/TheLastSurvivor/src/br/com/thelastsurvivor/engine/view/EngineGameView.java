package br.com.thelastsurvivor.engine.view;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.EngineGame;
import br.com.thelastsurvivor.engine.GameLoopThread;

public class EngineGameView extends SurfaceView implements SurfaceHolder.Callback {

	private Long lastUpdate;
    private Long sleepTime;

    private SurfaceHolder surfaceHolder;
    private Context context;
    

    
    private EngineGame engine;
    private GameLoopThread gameLoop;
	
	public EngineGameView(Context context, EngineGame engine) {
		super(context);
		
		this.context = context;
		this.engine = engine;
		init();
	}
	
	

	
	
	public void init(){
		
		
		
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		
		
		gameLoop = new GameLoopThread(surfaceHolder, context, engine);
		
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if(this.gameLoop.state == GameLoopThread.PAUSED){
          
			this.gameLoop = new GameLoopThread(getHolder(), context, engine);
			this.gameLoop.start();
        }else{
            
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
