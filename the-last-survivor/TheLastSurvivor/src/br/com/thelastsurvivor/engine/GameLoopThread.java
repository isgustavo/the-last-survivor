package br.com.thelastsurvivor.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoopThread extends Thread{

	
	private SurfaceHolder holder;
	//private Handler handler;
	private Context context;
	private EngineGame engine;
	
	private Paint blackScreen;
	
	private long sleepTime;
	private long delay=70;
	
	public int state = 1;
	
	//public enum state {RUNNING, PAUSED};

	//public StateGame state;
	
	public final static int RUNNING = 1;
	public final static int PAUSED = 2;
	
	public GameLoopThread(SurfaceHolder holder, Context context, EngineGame engine){
		Log.d("GameLoopThread", "01");
		this.holder = holder;
		//this.handler = handler;
		this.context = context;
		this.engine = engine;
		
		blackScreen = new Paint();
		blackScreen.setARGB(255, 0, 0, 0);
		blackScreen.setAntiAlias(true);
		
		
	}
	
	@Override
	public void run() {
		
		
		//UPDATE
		while(state == RUNNING){
			Log.d("GameLoopThread", "UDPATE");
			long beforeTime = System.nanoTime();
			
			engine.update();
			
			
			
			//DRAW
			
			Canvas canvas = null;
			try{
				Log.d("GameLoopThread", "DRAW");
				canvas = holder.lockCanvas(null);
				Log.d("GameLoopThread", "canvas");
				synchronized (canvas) {
					Log.d("GameLoopThread", "synchronized");
					canvas.drawRect(0,0,canvas.getWidth(), canvas.getHeight(), this.blackScreen);
					
					engine.draw(canvas);
				}
			}finally{
				if(canvas != null){
					holder.unlockCanvasAndPost(canvas);
				}
			}
			
			
			//SLEEP
			
			this.sleepTime = delay -((System.nanoTime()-beforeTime)/1000000L);
			Log.d("GameLoopThread", "SLEEP");
			try{
				
				if(sleepTime > 0){
					this.sleep(sleepTime);
					
				}
			}catch(InterruptedException e){
				
			}
			
		}
		
	}
}
