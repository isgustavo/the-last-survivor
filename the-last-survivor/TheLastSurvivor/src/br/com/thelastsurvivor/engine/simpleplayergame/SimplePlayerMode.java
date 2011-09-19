package br.com.thelastsurvivor.engine.simpleplayergame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.view.Display;
import br.com.thelastsurvivor.engine.EngineGame;
import br.com.thelastsurvivor.engine.game.asteroid.Asteroid;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.util.Vector2D;

public class SimplePlayerMode extends EngineGame {
	
	//private Vector2D camera;

	//private Drawable image;

	public SimplePlayerMode(Context context, Vibrator vibrator, Display display) {
		super(context, vibrator, display);
		
		init();
	}


	@Override
	public void init() {
		super.init();

		//this.image = this.context.getResources().getDrawable(R.drawable.spacecraft_image);
		
		this.spacecraft = new Spacecraft(this.getContext(), this.getDisplay(), new Vector2D(200,200));
		
		

	}
	

	@Override
	public void update(){
				

		this.spacecraft.update();
		this.verificationNewSpacecraftPositionScreen();
		
		super.update();
		
		
		
	}
	
	public void verificationNewSpacecraftPositionScreen(){
		if(-40 > this.spacecraft.getPosition().getY()){
			this.spacecraft.getPosition().setY(this.camera.getY());
		}else if(this.spacecraft.getPosition().getY() > this.camera.getY()+40){
			this.spacecraft.getPosition().setY(0);
		}
	
		if(-40 > this.spacecraft.getPosition().getX()){
			this.spacecraft.getPosition().setX(this.camera.getX());
		}else if(this.spacecraft.getPosition().getX() > this.camera.getX()+40){
			this.spacecraft.getPosition().setX(0);
		}
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


/*

if(this.spacecraft.getPosition().getY() < EngineGame.getSCREEN_SIZE_HEIGHT_UP()
		&& this.spacecraft.getPosition().getY() > EngineGame.getSCREEN_SIZE_HEIGHT_DOWN()){
	if(0 <  this.camera.getBeginningSizeHeight()){
		Vector2D newPosition = Orientation.getNewPosition(this.spacecraft.getAngle(), 
				new Vector2D(this.camera.) );
	}
}








switch (this.spacecraft.getOrientation().getOrientation()) {

case 1:
	
	if(this.spacecraft.getPosition().getY() < EngineGame.getSCREEN_SIZE_HEIGHT_UP()){
		
		if(0 <  this.camera.getBeginningSizeHeight()){
			this.camera.addBeginningSizeHeight(-1);
			this.camera.addFinalSizeHeight(-1);
		}else{
			
			this.vibrator.vibrate(300);

		}
	}

break;
case 2:
	
	if(this.spacecraft.getPosition().getY() < EngineGame.getSCREEN_SIZE_HEIGHT_UP()){
		
		if(0 < this.camera.getBeginningSizeHeight()){
			this.camera.addBeginningSizeHeight(-1);
			this.camera.addFinalSizeHeight(-1);
		}else{
			
			this.vibrator.vibrate(300);
		}
	}
	
	
	if(this.spacecraft.getPosition().getX() > EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
		
		if(this.camera.getFinalSizeWidth() < this.getSCREEN_SIZE_WIDTH()){
			this.camera.addFinalSizeWidth(1);
			this.camera.addBeginningSizeWidth(1);
		}else{
			
			this.vibrator.vibrate(300);
		}
	}
	
	
break;
	
case 3:

	if(this.spacecraft.getPosition().getX() >= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
		
		if(this.camera.getFinalSizeWidth() < this.getSCREEN_SIZE_WIDTH()){
			
			this.camera.addFinalSizeWidth(1);
			this.camera.addBeginningSizeWidth(1);
		}else{
			
			this.vibrator.vibrate(300);
		}
	}
	
break;

	
case 4:
	
	if(this.spacecraft.getPosition().getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN()){
		if(this.camera.getFinalSizeHeight() < this.getSCREEN_SIZE_HEIGHT()){
			this.camera.addFinalSizeHeight(1);
			this.camera.addBeginningSizeHeight(1);
		}else{
			
			this.vibrator.vibrate(300);
		}
	}
	
	if(this.spacecraft.getPosition().getX() >= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
		if(this.camera.getFinalSizeWidth() < this.getSCREEN_SIZE_WIDTH()){
			this.camera.addFinalSizeWidth(1);
			this.camera.addBeginningSizeWidth(1);
		}else{
			
			this.vibrator.vibrate(300);
		}
	
	}
	
break;


case 5:
	
	if(this.spacecraft.getPosition().getY() > EngineGame.getSCREEN_SIZE_HEIGHT_DOWN()){
		if(this.camera.getFinalSizeHeight() < this.getSCREEN_SIZE_HEIGHT()){
			this.camera.addFinalSizeHeight(1);
			this.camera.addBeginningSizeHeight(1);
		}else{
			
			this.vibrator.vibrate(300);
		}
	}
	
break;

case 6:
	if(this.spacecraft.getPosition().getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN()){
		
		if(this.camera.getFinalSizeHeight() < this.getSCREEN_SIZE_HEIGHT()){
			this.camera.addFinalSizeHeight(1);
			this.camera.addBeginningSizeHeight(1);
			
		}else{
			
			this.vibrator.vibrate(300);
		}
		
		
	
	}if(this.spacecraft.getPosition().getX() <= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
		if(0 < this.camera.getBeginningSizeWidth()){
			this.camera.addBeginningSizeWidth(-1);
			this.camera.addFinalSizeWidth(-1);
		}else{
			
			this.vibrator.vibrate(300);
		}
	}
	
	
break;

case 7:

	if(this.spacecraft.getPosition().getX() <= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
		if(0 < this.camera.getBeginningSizeWidth()){
			this.camera.addBeginningSizeWidth(-1);
			this.camera.addFinalSizeWidth(-1);
		}else{
			
			this.vibrator.vibrate(300);
		}
	}
	
	
break;

case 8:
	if(this.spacecraft.getPosition().getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_UP()){
		if(0 <  this.camera.getBeginningSizeHeight()){
			this.camera.addBeginningSizeHeight(-1);
			this.camera.addFinalSizeHeight(-1);
		}else{
			
			this.vibrator.vibrate(300);

		}
		
	}
	if(this.spacecraft.getPosition().getX() <= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
		if(0 < this.camera.getBeginningSizeWidth()){
			this.camera.addBeginningSizeWidth(-1);
			this.camera.addFinalSizeWidth(-1);
		}else{
			
			this.vibrator.vibrate(300);
		}
		
	}
break;
}*/