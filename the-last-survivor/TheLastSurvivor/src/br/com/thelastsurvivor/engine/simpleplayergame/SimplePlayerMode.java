package br.com.thelastsurvivor.engine.simpleplayergame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.EngineGame;
import br.com.thelastsurvivor.engine.IDrawBehavior;
import br.com.thelastsurvivor.engine.game.asteroid.Asteroid;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.game.weapon.IWeaponBehavior;
import br.com.thelastsurvivor.util.Vector2D;

public class SimplePlayerMode extends EngineGame {
	
	private Integer points;

	public SimplePlayerMode(Context context, Vibrator vibrator, Display display) {
		super(context, vibrator, display);
		
		init();
	}


	@Override
	public void init() {
		super.init();
		
		this.spacecraft = new Spacecraft(this.getContext(), this.getDisplay(), new Vector2D(200,200));

	}
	

	@Override
	public void update(){
				

		this.spacecraft.update();
		this.verificationNewSpacecraftPositionScreen();
		
		super.update();
		
		this.verificationCollisionShoot();
		
		
		
	}
	
	public void verificationNewSpacecraftPositionScreen(){
		if(-10 > this.spacecraft.getPosition().getY()){
			this.spacecraft.getPosition().setY(this.camera.getY());
		}else if(this.spacecraft.getPosition().getY() > this.camera.getY()+10){
			this.spacecraft.getPosition().setY(0);
		}
	
		if(-10 > this.spacecraft.getPosition().getX()){
			this.spacecraft.getPosition().setX(this.camera.getX());
		}else if(this.spacecraft.getPosition().getX() > this.camera.getX()+10){
			this.spacecraft.getPosition().setX(0);
		}
	}
	
	public void verificationCollisionShoot(){
		
		for (IDrawBehavior shoot : this.getSpacecraft().getShootsDrawables()) {
			for(IDrawBehavior asteroid : this.asteroidsDrawables){
				if((asteroid.getPosition().getX() < shoot.getPosition().getX() &&
						shoot.getPosition().getX() < asteroid.getPosition().getX()+asteroid.getSizeWidth()) &&
						(asteroid.getPosition().getY() < shoot.getPosition().getY() &&
								shoot.getPosition().getY() < asteroid.getPosition().getY()+asteroid.getSizeHeight())){
					shoot.setAlive(false);
					if(this.isAsteroidDestroyed((Asteroid)asteroid,(IWeaponBehavior) shoot)){
						asteroid.setAlive(false);
					}
				}
			}
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


	public Integer getPoints() {
		return points;
	}


}

