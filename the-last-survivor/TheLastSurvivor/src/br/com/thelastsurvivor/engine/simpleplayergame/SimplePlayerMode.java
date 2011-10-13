package br.com.thelastsurvivor.engine.simpleplayergame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.view.Display;
import br.com.thelastsurvivor.engine.simpleplayergame.spacecraft.Spacecraft;

public class SimplePlayerMode extends EngineGame {
	
	

	public SimplePlayerMode(Context context, Vibrator vibrator, Display display) {
		super(context, vibrator, display);
		
		init();
	}


	@Override
	public void init() {
		super.init();
		
		//this.spacecraft = new Spacecraft(this.getContext(), new Vector2D(200,200));

	}
	

	@Override
	public void update(){
				

		super.update();
		
		//this.verificationCollisionShoot();
		
		
		
	}
	
	
	
	@Override
	public void draw(Canvas c) {

		
		this.spacecraft.draw(c);
	    
		
 		super.draw(c);
	 

	}
	
	

	public Spacecraft getSpacecraft() {
		return spacecraft;
	}




}

