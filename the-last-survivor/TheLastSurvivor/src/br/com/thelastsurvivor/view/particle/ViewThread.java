package br.com.thelastsurvivor.view.particle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import br.com.thelastsurvivor.view.IAnimationBehavior;

public class ViewThread extends Thread{

	
	private SurfaceHolder holder;
	private IAnimationBehavior view;
	
	private Paint blackScreen;
	
	private long sleepTime;
	private long delay=30;
	
	public int state = 1;
	

	public final static int RUNNING = 1;
	public final static int PAUSED = 2;
	
	public ViewThread(SurfaceHolder holder, IAnimationBehavior view){
		super();
		this.holder = holder;
		this.view = view;
	
		blackScreen = new Paint();
		blackScreen.setARGB(255, 0, 0, 0);
		blackScreen.setAntiAlias(true);
	
	}
	
	
	@Override
	public void run() {

		//UPDATE
		while(state == RUNNING){
			long beforeTime = System.nanoTime();
			
			//engine.update();
			view.update();
			
			
			//DRAW
			
			Canvas canvas = null;
			try{
				canvas = holder.lockCanvas(null);	
				synchronized (canvas) {
					canvas.drawRect(0,0,
							canvas.getWidth(), canvas.getHeight(), 
							this.blackScreen);
					
					view.draw(canvas);
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
