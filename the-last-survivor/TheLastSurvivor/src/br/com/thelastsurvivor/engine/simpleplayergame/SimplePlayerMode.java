package br.com.thelastsurvivor.engine.simpleplayergame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.EngineGame;
import br.com.thelastsurvivor.engine.game.Spacecraft;
import br.com.thelastsurvivor.util.Vector2D;
import br.com.thelastsurvivor.view.particle.Explosion;

public class SimplePlayerMode extends EngineGame {
	
	//private Vector2D camera;

	private Drawable image;
	
	private Spacecraft spacecraft;
	private Explosion explosion;
	
	
	public SimplePlayerMode(Context context, Vibrator vibrator, Display display) {
		super(context, vibrator, display);
		Log.d("init","init");
		init();
	}


	@Override
	public void init() {
		
		//this.camera = new Vector2D(0,0);
		
		this.image = this.context.getResources().getDrawable(R.drawable.spacecraft_image);
		
		this.spacecraft = new Spacecraft(this.getContext(), this.getDisplay());
		
		this.explosion = new Explosion(200);
	}
	
	boolean particleFlag = true;
	
	@Override
	public void update(){
		
		
		
		spacecraft.update();
		
		switch (this.spacecraft.getOrientation().getOrientation()) {
		case 1:
			
			if(this.spacecraft.getPosition().getY() < EngineGame.getSCREEN_SIZE_HEIGHT_UP()){
				Log.d("CAMERA",""+camera.getY());
				//camera.addY(320);
				if(camera.getY() < 40 && particleFlag){
					camera.addY(1);
				}else{
					particleFlag = false;
					camera.setY(0);
				}
			
	    		
	    	}
			
		break;
		}

		if (this.explosion != null && this.explosion.isAlive) {
			this.explosion.updateInGame(camera);
		}
		
		super.update();
	}
	
	@Override
	public void draw(Canvas c) {
		
		this.spacecraft.draw(c);
	    
	    //c.translate(camera.getX(), camera.getY());
	    
	    
	   // Log.d("CAMERA", ""+camera.getX());
	    //Log.d("CAMERA", ""+camera.getY());
	    
		if(this.camera.getY() 
				< -100 && 
				-100 < 
				EngineGame.getSCREEN_SIZE_HEIGHT_DOWN()){
			this.image.setBounds(40, -100, 40+44, -100+48);
			
		    this.image.draw(c);
		}
		
	    
	
	    // render explosions
 		if (explosion != null) {
 			explosion.draw(c);
 		}
	    
	    
	    //Log.d("direcao", ""+camera.getY());
		super.draw(c);
		
		
	
	}


	public Spacecraft getSpacecraft() {
		return spacecraft;
	}


}
