package br.com.thelastsurvivor.engine.multiplayergame.client;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import br.com.thelastsurvivor.engine.multiplayer.GameLoopThread;
import br.com.thelastsurvivor.engine.multiplayergame.client.EngineGameClient;
import br.com.thelastsurvivor.engine.util.IInitUpdateDraw;

public class EngineMultiGameView extends SurfaceView implements SurfaceHolder.Callback {

	private Long lastUpdate;
    private Long sleepTime;

    private SurfaceHolder surfaceHolder;
    private Context context;
    
    private IInitUpdateDraw engine;
    private GameLoopThread gameLoop;
	
	public EngineMultiGameView(Context context, IInitUpdateDraw engine) {
		super(context);
		
		this.context = context;
		this.engine = engine;
		
		
		init();
	}
	
	/*public EngineMultiGameView(IInitUpdateDraw engine) {
		super(engine.getContext());
		
		//this.context = context;
		this.engine = engine;
		init();
	}*/
	
	
	public void init(){
		
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		
		if(engine instanceof EngineGameClient){
			((EngineGameClient)engine).setHolder(surfaceHolder);
		}
		
		//gameLoop = new GameLoopThread(surfaceHolder, engine);
		
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		//if(this.gameLoop.state == GameLoopThread.PAUSED){
          
		//	this.gameLoop = new GameLoopThread(getHolder(), engine);
		//	this.gameLoop.start();
        //}else{
            
        //	this.gameLoop.start();
       // }
    
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		 Boolean retry = true;
	    
		 
	     //this.gameLoop.state = gameLoop.PAUSED;
	    // while (retry) {
          //  try {
          //  	this.gameLoop.join();
          //      retry = false;
          //  } catch (InterruptedException e) {
          //  }
	     //}
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

	public GameLoopThread getGameLoop() {
		return gameLoop;
	}
	
}
