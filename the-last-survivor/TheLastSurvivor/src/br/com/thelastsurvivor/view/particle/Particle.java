package br.com.thelastsurvivor.view.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.simple.Orientation;
import br.com.thelastsurvivor.util.Vector2D;

public class Particle {

	
	public Boolean isAlive;
	
	public static final int MAX_DIMENSION = 5;	
	public static final int MAX_SPEED = 6;	
	
	protected Vector2D position;
	
	protected Double widht;		
	protected Double height;		
	
	protected Integer color;			
	protected Paint paint;	
	
	public Particle(){
		
		
		
		this.init();
	}
	
	public void init(){
		
		this.isAlive = true;
		
		this.position = new Vector2D(randomSizedimension(0,800).intValue(),
				randomSizedimension(0,800).intValue());
		
		this.widht = randomSizedimension(1, MAX_DIMENSION);
		this.height = this.widht;
		
		this.color = Color.argb(255, 255,255,
						randomSizedimension(255,200).intValue());
		this.paint = new Paint(this.color);
		
	}
	
	public void update(br.com.thelastsurvivor.engine.simpleplayergame.spacecraft.Spacecraft spacecraft){
		
		
		 if (spacecraft.getUp()) {
			 if(spacecraft.getAngle() >= 270 || spacecraft.getAngle() <= 90){
				  
				   Double invertAngle;
				   
				   if(spacecraft.getAngle() >= 270){
					   invertAngle = (360 - spacecraft.getAngle()) + 90;
					   
				   }else{
					   invertAngle = spacecraft.getAngle()+180;
				   }
				   
				   Orientation.getNewPosition(invertAngle, this.position); 
				   
			   }else{
				   Orientation.getNewPosition(spacecraft.getAngle(), this.position);
			   }
			   
	     }
	     
		this.update();
	}
	
	public void update(Spacecraft spacecraft){
		
		
		 if (spacecraft.getUp()) {
			 if(spacecraft.getAngle() >= 270 || spacecraft.getAngle() <= 90){
				  
				   Double invertAngle;
				   
				   if(spacecraft.getAngle() >= 270){
					   invertAngle = (360 - spacecraft.getAngle()) + 90;
					   
				   }else{
					   invertAngle = spacecraft.getAngle()+180;
				   }
				   
				   Orientation.getNewPosition(invertAngle, this.position); 
				   
			   }else{
				   Orientation.getNewPosition(spacecraft.getAngle(), this.position);
			   }
			   
	     }else if(spacecraft.getDown()){
	    	 if(spacecraft.getAngle() >= 270 || spacecraft.getAngle() <= 90){
				   Orientation.getNewPosition(spacecraft.getAngle(), this.position);
			   }else{
				   Double invertAngle;
			   
				   if(spacecraft.getAngle() <= 180){
					   invertAngle = spacecraft.getAngle()+180;
				   }else{
					   invertAngle = 180 - (360 - spacecraft.getAngle());
				   }
			   
				   Orientation.getNewPosition(invertAngle, this.position);
			   }
	     }
	     
		this.update();
	}
	
	public void update() {
		
		if (this.isAlive) {
			
			// extract alpha 
			Integer alpha = this.color >>> 24;
	    	
			alpha -= randomSizedimension(0,4).intValue();								// fade by 5
			if (alpha <= 0) {						// if reached transparency kill the particle
				this.isAlive = false;
			} else {
				this.color = (this.color & 0x00ffffff) + (alpha << 24);		// set the new alpha
				this.paint.setAlpha(alpha);
			}
			
		}
	}
	
	
	public void draw(Canvas canvas) {

		paint.setColor(this.color);
		canvas.drawRect((float) this.position.getX(), (float) this.position.getY(), 
				(float) (this.position.getX() + this.widht),(float) (this.position.getY() + this.height), paint);
		
	}
	

	
	public static Double randomSizedimension(Integer min, Integer max) {
		return (min + Math.random() * (max - min + 1));
	}

	public Boolean isAlive() {
		return isAlive;
	}
	
	
	
}
