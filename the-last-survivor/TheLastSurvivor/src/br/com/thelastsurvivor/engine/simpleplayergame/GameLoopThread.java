package br.com.thelastsurvivor.engine.simpleplayergame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoopThread extends Thread{

	
	private SurfaceHolder holder;
	//private Handler handler;
	private Context context;
	private EngineGame engine;
	
	private Paint blackScreen;
	
	private long sleepTime;
	private long delay=50;
	
	public int state = 1;

	
	public final static int RUNNING = 1;
	public final static int PAUSED = 2;
	
	public GameLoopThread(SurfaceHolder holder, Context context, EngineGame engine){
		
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
			
			long beforeTime = System.nanoTime();
			
			engine.update();
			
			
			
			//DRAW
			
			Canvas canvas = null;
			try{
				
				canvas = holder.lockCanvas(null);
			
				synchronized (canvas) {
				
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
			
			try{
				
				if(sleepTime > 0){
					this.sleep(sleepTime);
					
				}
			}catch(InterruptedException e){
				
			}
			
		}
		
	}
}
