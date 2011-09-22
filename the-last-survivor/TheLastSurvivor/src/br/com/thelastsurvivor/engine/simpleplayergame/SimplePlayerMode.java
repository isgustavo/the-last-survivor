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
	
	//private Vector2D camera;

	//private Drawable image;
	
	Paint paint;
	Typeface font; 
	 
	 String test;
	private Integer points;

	public SimplePlayerMode(Context context, Vibrator vibrator, Display display) {
		super(context, vibrator, display);
		
		init();
	}


	@Override
	public void init() {
		super.init();

		//this.image = this.context.getResources().getDrawable(R.drawable.spacecraft_image);
		
		this.spacecraft = new Spacecraft(this.getContext(), this.getDisplay(), new Vector2D(200,200));
		
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		font = Typeface.createFromAsset(getContext().getAssets(),"fonts/FT2FONT.TTF");
		
		paint.setTextSize(12);

		
		test = context.getString(R.string.cadastre_new_palyer_1);
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
		
		

		
		/*Paint titlePaint = new Paint();
		titlePaint.setTypeface(Typeface.DEFAULT_BOLD);
		titlePaint.setColor(Color.BLUE);
		*/
		
		
		//c.drawText("Pontos", 0, 15, titlePaint);
	
		
		
		this.spacecraft.draw(c);
	    
		//c.drawColor(Color.BLACK);
		paint.setColor(Color.WHITE);
        drawFont(c, 15 , test );
        
 		super.draw(c);
	 

	}
	
	void drawFont(Canvas c, int y, String name){

    	 paint.setTypeface(font);
         c.drawText(name, 300, y, paint);

    }


	public Spacecraft getSpacecraft() {
		return spacecraft;
	}


	public Integer getPoints() {
		return points;
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