package br.com.thelastsurvivor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.simple.GameLoopThread;
import br.com.thelastsurvivor.view.particle.Explosion;
import br.com.thelastsurvivor.view.particle.ViewThread;

public class TheLastSurvivorView extends SurfaceView implements SurfaceHolder.Callback, IAnimationBehavior {


	private static final int EXPLOSION_SIZE = 200;
	
	private ViewThread viewThread;
	private Explosion explosion;
	
	private Drawable image;
	
	public TheLastSurvivorView(Context context) {
		super(context);
		
		
		this.init();
	}
	
	@Override
	public void init(){
		
		this.getHolder().addCallback(this);
		this.setFocusable(true);
		
		this.image = this.getResources().getDrawable(R.drawable.the_last_survivor_image);

		this.viewThread = new ViewThread(getHolder(), this);
		this.explosion = new Explosion(EXPLOSION_SIZE);
	}
	
	@Override
	public void update() {
		
		if (explosion != null && explosion.isAlive) {
			explosion.update();
		}
	}

	@Override
	public void draw(Canvas c) {
		
		
		
		c.drawColor(Color.BLACK);
		this.image.setBounds(0,0, c.getWidth(), c.getHeight());
		
		
		
		// render explosions
		if (explosion != null) {
			explosion.draw(c);
		}
		
		image.draw(c);

	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		
		if(this.viewThread.state == GameLoopThread.PAUSED){
	        //When game is opened again in the Android OS
			this.viewThread = new ViewThread(getHolder(), this);
			this.viewThread.start();
	    }else{
	        //creating the game Thread for the first time
	    	this.viewThread.start();
	    }
    
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		Boolean retry = true;
		 
		this.viewThread.state = this.viewThread.PAUSED;
	     while (retry) {
           try {
        	   this.viewThread.join();
               retry = false;
           } catch (InterruptedException e) {
           }
	     }
		
	}


}
