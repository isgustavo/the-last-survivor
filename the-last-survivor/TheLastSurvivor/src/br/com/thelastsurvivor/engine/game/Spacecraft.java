package br.com.thelastsurvivor.engine.game;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.EngineGame;
import br.com.thelastsurvivor.engine.IDrawControllable;
import br.com.thelastsurvivor.engine.game.weapon.IWeaponBehavior;
import br.com.thelastsurvivor.engine.game.weapon.SimpleShoot;
import br.com.thelastsurvivor.engine.util.EOrientation;
import br.com.thelastsurvivor.util.Vector2D;

public class Spacecraft implements IDrawControllable {
	
	private static final int SPEED = 6;

	private Context context;
	
	private Bitmap image;
	private Bitmap resizedBitmap;
	private Drawable drawableImage;
	
	private Drawable alertImage;
	private Boolean alertFlag;
	
	private Vector2D position;
	private Vector2D sensorPosition;
	private EOrientation orientation;
	private Boolean orientationChange;

	Integer rotacao = 0;
	private Matrix matrix;
	
	private List<IWeaponBehavior> shoots;
	private List<IWeaponBehavior> shootsDrawables;
	
	public Spacecraft(Context context, Display display, Vector2D position){
		this.context = context;
		this.position = position;
		
		
		init();
	}

	@Override
	public void init() {
		
		//this.position = new Vector2D(200,200);
		this.sensorPosition = new Vector2D(0,0);
		this.orientation = EOrientation.up;
		this.orientationChange = false;
		
		this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spacecraft_image);
		
		this.alertImage = this.context.getResources().getDrawable(R.drawable.alert_image);
		this.alertFlag = false;
		
        this.matrix = new Matrix();
		this.matrix.setRotate(0);
		
    	this.resizedBitmap = Bitmap.createBitmap(image, 0, 0,image.getWidth(), image.getHeight(), matrix, true);
    	
    	this.shoots = new ArrayList<IWeaponBehavior>();
    	this.shootsDrawables = new ArrayList<IWeaponBehavior>();
    	
    	
	}
	
	
	boolean left = false;
	boolean right = false;
	
	@Override
	public void updateOrientation(Float orientationX, Float orientationY){
	
		this.sensorPosition.setX(((orientationX.intValue())*10));
		this.sensorPosition.setY(((orientationY.intValue())*10));
		
		
	      
	}
	
	boolean down = false;
	boolean up = false; 
	
	@Override
	public void update() {
		
		this.alertFlag = false;
		
		//controlUpdate();
		
		if (this.sensorPosition.getX() > 20 ) {
			Log.d("LEFT", "LEFT");
	    	
	    	right = true;
	 
	    }else if(this.sensorPosition.getX() < -20 ) {
	    	Log.d("right", "right");
	    	left = true;
	    }
		
		
		if(this.sensorPosition.getY() > 20){
			down = true;
		}else if(this.sensorPosition.getY() < -20){
			up = true;
		}
		
		left = true;
		up = true;
		newControlUpdate();
	
		left = false;
		right = false;
		down = false;
		up = false;
		
		BitmapDrawable newImage = new BitmapDrawable(this.resizedBitmap);
		this.drawableImage = newImage;

		if(this.shootsDrawables != null){
			
			this.getShootsDrawables();
			
			this.shootsDrawables.addAll(shoots);
			
			for (IWeaponBehavior shoot : shootsDrawables) {
				shoot.update();
			}
			
			//clean list new shoots
			this.shoots.clear();
		}
		
	}
	
	private void getShootsDrawables(){
		List<IWeaponBehavior> shoots = new ArrayList<IWeaponBehavior>();
		for(IWeaponBehavior shoot : this.shootsDrawables){
			if(shoot.isAlive()){
				shoots.add(shoot);
			}
		}
		
		this.shootsDrawables.clear();
		this.shootsDrawables.addAll(shoots);
		
	}
	
	 static int width = 320;          // Dimensions of the graphics area.
	  static int height =  480;

	        
	
	  Double  angle = 0.0;             
	 
	  Double  x = 200.0, y= 200.0;             
	  Double  deltaX =  200.0, deltaY = 200.0;    
	    
	  static final int DELAY = 20;    
	  static final int FPS   =                 // the resulting frame rate.
			    Math.round(100/ DELAY);
	
	  static final double SHIP_ANGLE_STEP = Math.PI / FPS;

	  private Double getPositiveNumber(Double number){
		  if(number < 0){
			  return number *(-1.0);
		  }
		  return number;
	  }
	  
	  private Double getNegativeNumber(Double number){
		  if(number > 0){
			  return number * (-1.0);
		  }
		  return number;
	  }
	  
	  public void advance() {


		    // Update the rotation and position of the sprite based on the delta
		    // values. If the sprite moves off the edge of the screen, it is wrapped
		    // around to the other side and TRUE is returnd.

		   //this.angle += this.deltaAngle;
		    if (this.angle < 0)
		      this.angle += 2 * Math.PI;
		    if (this.angle > 2 * Math.PI)
		      this.angle -= 2 * Math.PI;
		  
		    this.x += this.deltaX;
		   
		    this.y += this.deltaY;
		    
		    
		  }
	  
	private void newControlUpdate(){
		
		double dx = 0, dy = 0, speed;
		
	    if (left) {
	    	if(this.angle == 350){
	    		this.angle = 0.0;
	    	}else{
	    		this.angle += 5;
	    	}
	    		

	    }else if(right) {
	    	this.angle = 0.0;
    	}else{
    		this.angle += 5 ;
    	}
    		
	    
	  if(angle == 0){
		  dx = 5 * Math.cos(angle);
		  dy = 5 * Math.sin(angle);
	  }else if(angle > 0 && angle < 90){
		  dx = 5 * Math.cos(angle);
		  dy = 5 * Math.sin(angle);
	  }else if(angle == 90){
		  dx = 5 * Math.cos(angle);
		  dy = 5 * Math.sin(angle);
	  }else if(angle > 90 && angle < 180){
		  dx = 5 * Math.cos(angle);
		  dy = 5 * Math.sin(angle);
	  }else if(angle == 180){
		  dx = 5 * Math.sin(angle);
		  dy = 5 * Math.cos(angle);
	  }else if(angle > 180 && angle < 270){
		  dx = 5 * Math.sin(angle);
		  dy = 5 * Math.cos(angle);
	  }else if(angle == 270){
		  dx = 5 * Math.sin(angle);
		  dy = 5 * Math.cos(angle);
	  }else if(angle > 270 && angle < 360){
		  dx = 5 * Math.sin(angle);
		  dy = 5 * Math.cos(angle);
	  }
	    
	  /* dx = 5 * -Math.sin(angle);
	    dy = 5 * Math.cos(angle);*/
	/*    if (down) {
	      deltaX += dx;
	      deltaY += dy;
	      
	    }*/
	   
	   if (up) {
		   if(angle == 0 ){
			   deltaY -= getPositiveNumber(dy);
		   }
	       if(angle > 0 && angle < 90){
	    		 deltaX += getPositiveNumber(dx);
	 	         deltaY -= getPositiveNumber(dy);
	 	   }else if(angle > 90 && angle < 180){
	 	    	 deltaX += getPositiveNumber(dx);
	 	         deltaY += getPositiveNumber(dy);
	 	   }else if(angle == 90){
	 		  deltaX += getPositiveNumber(dx);
	 	   }else if(angle > 180 && angle < 270){
	 	    	 deltaX -= getPositiveNumber(dx);
	 	         deltaY += getPositiveNumber(dy);
	 	   }else if(angle == 180){
	 		  deltaY += getPositiveNumber(dy);
		    }else if(angle > 270 && angle < 360){
		    	 deltaX -= getPositiveNumber(dx);
	 	         deltaY -= getPositiveNumber(dy);
		    }else if(angle == 270){
		    	deltaX -= getPositiveNumber(dx);
		    }
	    }
	   
	   
	    this.x = this.deltaX;
		   
	    this.y = this.deltaY;

	   
	    
	    
		
		
	      this.matrix.setRotate(angle.floatValue());
	    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
	            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
	    
	    	
	    		this.position.setX((int) Math.round(x));
		    	this.position.setY((int) Math.round(y));
		
		Log.d("X", ""+this.position.getX());
		Log.d("Y", ""+this.position.getY());
		

		
/*			Double dx, dy, speed;
			
		if (this.sensorPosition.getX() < 0 && (this.sensorPosition.getY() < 3 && this.sensorPosition.getY() > -3)) {
	      this.rotacao--;
	      
	      if(angle == 360 ){
	    	  angle = 0.0;
	      }else{
	    	  angle += 1;
	      }
	      if (this.angle > 2 * Math.PI)
	    	  this.angle -= (int)(2 * Math.PI);
	      
	      this.matrix.setRotate( this.angle.floatValue());
	    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		}
	    if (this.sensorPosition.getX() > 0 && (this.sensorPosition.getY() < 10 && this.sensorPosition.getY() > -3)) {
	    	 this.rotacao++;
	    	 if(angle == 360 ){
		    	  angle = 0.0;
		      }else{
		    	  angle -= 1;
		      }
	      if (this.angle < 0)
	    	  this.angle += (int)(2 * Math.PI);
	      
	      this.matrix.setRotate( this.angle.floatValue());
	    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
	    }

		// Fire thrusters if up or down cursor key is down.

	    dx = 10 * -Math.sin(angle);
	    dy = 10 * Math.cos(angle);
	    
	    //advance();
	    
	    if (((this.sensorPosition.getX() < 3 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() < 0)) {
	     deltaX += dx;
	     deltaY += dy;
	    	
	    	this.position.addX(deltaX.intValue());
	     this.position.addY(deltaY.intValue());
	    }
	    
	   //advance();
	    
	    
	    
	   /* if (down) {
	        ship.deltaX -= dx;
	        ship.deltaY -= dy;
	    }
		*/	    
			    
			
		/*	if (this.sensorPosition.getX() < 0 && (this.sensorPosition.getY() < 3 && this.sensorPosition.getY() > -3)) {
				
				if(this.rotacao-10 == 360){
					this.rotacao = 0;
				}else{
					this.rotacao = 23;
				}
				angle = this.rotacao.doubleValue();
				this.matrix.setRotate(this.rotacao);
				this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
			            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
			    	
				
			    angle += SHIP_ANGLE_STEP;
			    if (angle > 2 * Math.PI)
			       angle -= 2 * Math.PI;
			}
			if (this.sensorPosition.getX() > 0 && (this.sensorPosition.getY() < 10 && this.sensorPosition.getY() > -3)) {
				if(this.rotacao+10 == 360){
					this.rotacao = 0;
				}else{
					this.rotacao = 23;
				}
			    	angle = this.rotacao.doubleValue();
			    	this.matrix.setRotate(this.rotacao);
			    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
				            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
				    	
			    	
			    	angle -= SHIP_ANGLE_STEP;
			      if (angle < 0)
			        angle += 2 * Math.PI;
	         }

			    // Fire thrusters if up or down cursor key is down.
			Log.d("AnGLE", "."+angle);
			    dx = SHIP_SPEED_STEP * -(Math.sin(angle)*10);
			    dy = SHIP_SPEED_STEP *  (Math.cos(angle)*10);
			    if((this.sensorPosition.getX() < 3 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() < 0) {
			    	
			    	
			    	if(angle == 0){
			    		 this.position.addY(-dy.intValue());
			    	}else if(angle > 0 && angle < 90){
			    		 this.position.addX(dx.intValue());
				    	 this.position.addY(dy.intValue());
			    	}else if(angle == 90){
			    		this.position.addX(-dx.intValue());
			    	}else if(angle > 90 && angle < 180){
			    		 this.position.addX(-dx.intValue());
				    	 this.position.addY(-dy.intValue());
			    	}else if(angle == 180){
			    		this.position.addY(-dy.intValue());
			    	}else if(angle > 180 && angle < 270){
			    		this.position.addX(dx.intValue());
				    	this.position.addY(-dy.intValue());
			    	}else if( angle == 270){
			    		this.position.addX(dx.intValue());
			    	}else{
			    		this.position.addX(dx.intValue());
				    	this.position.addY(dy.intValue());
			    	}
			    	
			    	
			    	/* this.position.addX(dx.intValue());
			    	 this.position.addY(dy.intValue());
			    	 Log.d("UPX", "."+dx.intValue());
			    	 Log.d("UPY", "."+dy.intValue());
			    }
			 //   if ((this.sensorPosition.getX() < 3 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() > 0) {
			 //   	 this.position.addX(-dx.intValue());
			 //   	 this.position.addY(-dy.intValue());
			//    	 Log.d("DOWN", ".");
			 //   }

			    // Don't let ship go past the speed limit.
			   
			    if (((this.sensorPosition.getX() < 10 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() < 0)
			    	|| ((this.sensorPosition.getX() < 3 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() > 0)) {
			      
			     speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
			      if (speed > MAX_SHIP_SPEED) {
			        dx = MAX_SHIP_SPEED * -Math.sin(angle);
			        dy = MAX_SHIP_SPEED *  Math.cos(angle);
			        if (((this.sensorPosition.getX() < 10 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() < 0))
			          this.position.setX(dx.intValue());
			        else
			        	this.position.setX(-dx.intValue());
			        if (((this.sensorPosition.getX() < 10 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() < 0))
			        	this.position.setY(dy.intValue());
			        else
			        	this.position.setY(-dy.intValue());
			      }
			    }
*/
			
/*			this.angle += this.rotacao;
			    if (this.angle < 0)
			      this.angle += 2 * Math.PI;
			    if (this.angle > 2 * Math.PI)
			      this.angle -= 2 * Math.PI;
			   
			    this.position.addX(this.deltaX.intValue());
			//    if (this.position.getX() < -width / 2) {
			//    	this.position.addX(width);
			    
			//    }
			//    if (this.position.getX() > width / 2) {
			//    	this.position.addX(-width);
			      
			//    }
			    this.position.addY(-this.deltaY.intValue());
			//    if (this.position.getY() < -height / 2) {
			//    	this.position.addY(height);
			    
			//    }
			//    if (this.position.getY() > height / 2) {
			//    	this.position.addY(-height);
			     
			//    }
			    
			  
			
			*/
			
			
			
	/*	if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP()){
	    		
			if(this.rotacao == 0){
				this.position.addY(-SPEED);
			}else if(this.rotacao > 0 && this.rotacao <= 15){	
				this.position.addX(SPEED+2);
				this.position.addY(-SPEED-12);
			}else if(this.rotacao > 15 && this.rotacao <= 30){
				this.position.addX(SPEED);
				this.position.addY(-SPEED-6);
			}else if(this.rotacao > 45 && this.rotacao < 90){
				this.position.addX(SPEED+2);
				this.position.addY(-SPEED);
			}else if(this.rotacao == 90){
				this.position.addX(SPEED);	
			}else if(this.rotacao > 90 && this.rotacao < 135){
				this.position.addX(SPEED);
				this.position.addY(SPEED+2);
			}else if(this.rotacao > 135 && this.rotacao < 190){
				this.position.addX(SPEED+2);
				this.position.addY(SPEED);
			}
	    }
	    */
		
		

	/*		
		}else if(this.sensorPosition.getX() > 0 && (this.sensorPosition.getY() < 10 && this.sensorPosition.getY() > -3)){
			
			this.rotacao++;
			Log.d("rotacao", "."+this.rotacao);
			this.matrix.setRotate(this.rotacao);
			this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
			
			
		}*/
	}
	
	private void controlUpdate(){
		
		switch (this.orientation.getOrientation()) {
		case 1:
			if((this.sensorPosition.getX() < 10 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() < 0){
				
				this.matrix.setRotate(0);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	
		    	if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP()){
		    		this.position.addY(-SPEED);
		    	}
		    	
		    	this.orientation = EOrientation.up;
			
			}else if(this.sensorPosition.getY() < 0 && this.sensorPosition.getX() > 0){
				
				this.matrix.setRotate(++rotacao);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    
		    	if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP() &&
		    			this.position.getX() <= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
		    		this.position.addX(SPEED);
			    	this.position.addY(-SPEED);
		    	}
		    	
		    	this.orientation = EOrientation.up_right;
		    	
			}else if(this.sensorPosition.getY() < 0 && this.sensorPosition.getX() < 0){
				
				this.matrix.setRotate(315);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP() &&
		    			this.position.getX() >= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
		    		
		    		this.position.addX(-SPEED);
		    		this.position.addY(-SPEED);
		    	}
		    	
		    	this.orientation = EOrientation.up_left;
			}else{
				 if((this.sensorPosition.getX() < 3 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() > 0){
						
					this.matrix.setRotate(180);
			    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
			        this.image.getWidth(), this.image.getHeight(), this.matrix, true);
			    	
			    	if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN()){
			    		this.position.addY(SPEED);
			    	}

			    	this.orientation = EOrientation.down;
				 }
				/*if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP()){
		    		this.position.addY(-SPEED);
		    	}
				this.alertFlag = true;*/
			}
		break;

		case 2:
			if((this.sensorPosition.getX() < 3 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() < 0){
				this.matrix.setRotate(--rotacao);
				//this.matrix.setRotate(0);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP()){
		    		this.position.addY(-SPEED);
		    	}
		    	
		    	this.orientation = EOrientation.up;
		    	
			}else if(this.sensorPosition.getY() < 0 && this.sensorPosition.getX() > 0){
				this.matrix.setRotate(++rotacao);
				//this.matrix.setRotate(45);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP() &&
		    			this.position.getX() <= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
		    		this.position.addX(SPEED);
			    	this.position.addY(-SPEED);
		    	}
		    	
		    	this.orientation = EOrientation.up_right;
		    	
			}else if(this.sensorPosition.getX() > 0 && (this.sensorPosition.getY() < 3 && this.sensorPosition.getY() > -3)){
				this.matrix.setRotate(++rotacao);
				//this.matrix.setRotate(90);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getX() <= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
		    		this.position.addX(SPEED);
		    	}


		    	this.orientation = EOrientation.right;
			}else{
				if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP() &&
		    			this.position.getX() <= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
		    		this.position.addX(SPEED);
			    	this.position.addY(-SPEED);
		    	}
				this.alertFlag = true;
			}
			
		break;
		
		case 3:
			if(this.sensorPosition.getY() < 0 && this.sensorPosition.getX() > 0){
				this.matrix.setRotate(--rotacao);
				//this.matrix.setRotate(45);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);

		    	if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP() &&
		    			this.position.getX() <= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
		    		this.position.addX(SPEED);
			    	this.position.addY(-SPEED);
		    	}


		    	this.orientation = EOrientation.up_right;
		    	
			}else if(this.sensorPosition.getX() > 0 && (this.sensorPosition.getY() < 10 && this.sensorPosition.getY() > -3)){
				this.matrix.setRotate(++rotacao);
				//this.matrix.setRotate(90);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getX() <= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
		    		this.position.addX(SPEED);
		    	}
		    	
		    	this.orientation = EOrientation.right;
			
			}else if(this.sensorPosition.getY() > 0 && this.sensorPosition.getX() > 0){
				
				this.matrix.setRotate(135);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN() &&
		    			this.position.getX() <= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
		    		this.position.addX(SPEED);
		    		this.position.addY(SPEED);
		    	}
		    	
		    	this.orientation = EOrientation.down_right;
			}else {
				if(this.position.getX() <= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
					this.position.addX(SPEED);
				}
	    		this.alertFlag = true;
	    	}
			
		break;	
		
		case 4:
			if(this.sensorPosition.getX() > 0 && (this.sensorPosition.getY() < 10 && this.sensorPosition.getY() > -3)){
				
				this.matrix.setRotate(90);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getX() <= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
		    		this.position.addX(SPEED);
		    	}


		    	this.orientation = EOrientation.right;
		    	
			}else if(this.sensorPosition.getY() > 0 && this.sensorPosition.getX() > 0){
				
				this.matrix.setRotate(135);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN() &&
		    			this.position.getX() <= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
		    		this.position.addX(SPEED);
		    		this.position.addY(SPEED);
		    	}
		    	this.orientation = EOrientation.down_right;
			
			}else if((this.sensorPosition.getX() < 3 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() > 0){
					
				this.matrix.setRotate(180);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
	            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
			    	
		    	if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN()){
		    		this.position.addY(SPEED);
		    		
		    	}
		    	
		    	this.orientation = EOrientation.down;
			}else{
				if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN() &&
	    			this.position.getX() <= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
					this.position.addX(SPEED);
					this.position.addY(SPEED);
				}
	    		this.alertFlag = true;
	    	}
			 
			 
			
		break;
		
		case 5:
			if(this.sensorPosition.getY() > 0 && this.sensorPosition.getX() > 0){
				
				this.matrix.setRotate(135);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN() &&
		    			this.position.getX() <= EngineGame.getSCREEN_SIZE_WIDTH_RIGHT()){
		    		this.position.addX(SPEED);
		    		this.position.addY(SPEED);
		    	}
		    	
		    	this.orientation = EOrientation.down_right;
		    	
			}else  if((this.sensorPosition.getX() < 3 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() > 0){
				
				this.matrix.setRotate(180);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		        this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN()){
		    		this.position.addY(SPEED);
		    	}

		    	this.orientation = EOrientation.down;
		    	
			 }else if(this.sensorPosition.getY() > 0 && this.sensorPosition.getX() < 0){
				
				this.matrix.setRotate(225);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN() &&
		    			this.position.getX() >= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
		    		this.position.addX(-SPEED);
		    		this.position.addY(SPEED);
		    	}
		    	
		    	this.orientation = EOrientation.down_left;
		    	
			}else{
				if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN()){
					this.position.addY(SPEED);
				}
	    		this.alertFlag = true;
	    	}
			
		break;
		
		case 6:
			if((this.sensorPosition.getX() < 3 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() > 0){
				
				this.matrix.setRotate(180);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		        this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN()){
		    		this.position.addY(SPEED);
		    	}


		    	this.orientation = EOrientation.down;
		    	
		 }else if(this.sensorPosition.getY() > 0 && this.sensorPosition.getX() < 0){
			 
				this.matrix.setRotate(225);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN() &&
		    			this.position.getX() >= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
		    		this.position.addX(-SPEED);
		    		this.position.addY(SPEED);
		    	}
		    	
		    	this.orientation = EOrientation.down_left;
		    	
			}else if(this.sensorPosition.getX() < 0 && (this.sensorPosition.getY() < 3 && this.sensorPosition.getY() > -3)){
				
				this.matrix.setRotate(270);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getX() >= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
		    		this.position.addX(-SPEED);
		    	}
		    	
		    	
		    	this.orientation = EOrientation.left;
		    	
			}else{
				if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN() &&
	    			this.position.getX() >= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
					this.position.addX(-SPEED);
					this.position.addY(SPEED);
				}
	    		this.alertFlag = true;
	    	}

		break;
		
		
		case 7:
			if(this.sensorPosition.getY() > 0 && this.sensorPosition.getX() < 0){
				
				this.matrix.setRotate(225);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN() &&
		    			this.position.getX() >= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
		    		this.position.addX(-SPEED);
		    		this.position.addY(SPEED);
		    	}
		    	
		    	this.orientation = EOrientation.down_left;
		    	
			}else if(this.sensorPosition.getX() < 0 && (this.sensorPosition.getY() < 3 && this.sensorPosition.getY() > -3)){
				
				this.matrix.setRotate(270);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getX() >= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
		    		this.position.addX(-SPEED);
		    	}
		    	this.orientation = EOrientation.left;
			
			}else if(this.sensorPosition.getY() < 0 && this.sensorPosition.getX() < 0){
				
				this.matrix.setRotate(315);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	
		    	if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP() &&
		    			this.position.getX() >= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
		    		
		    		this.position.addX(-SPEED);
		    		this.position.addY(-SPEED);
		    	}
		    	
		    	this.orientation = EOrientation.up_left;
		    	
			}else{
				if(this.position.getX() >= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
					this.position.addX(-SPEED);
				}
	    		this.alertFlag = true;
	    	}
		break;
		
		
		case 8:
			if(this.sensorPosition.getX() < 0 && (this.sensorPosition.getY() < 3 && this.sensorPosition.getY() > -3)){
				
				this.matrix.setRotate(270);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getX() >= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
		    		this.position.addX(-SPEED);
		    	}

		    	this.orientation = EOrientation.left;
			
			}else if(this.sensorPosition.getY() < 0 && this.sensorPosition.getX() < 0){
				
				this.matrix.setRotate(315);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP() &&
		    			this.position.getX() >= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
		    		
		    		this.position.addX(-SPEED);
		    		this.position.addY(-SPEED);
		    	}
		    	this.orientation = EOrientation.up_left;
		    	
			}else if((this.sensorPosition.getX() < 3 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() < 0){
				
				this.matrix.setRotate(0);
		    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
		            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		    	
		    	if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP()){
		    		this.position.addY(-SPEED);
		    	}
		    	this.orientation = EOrientation.up;
			}else{
				if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP() &&
	    			this.position.getX() >= EngineGame.getSCREEN_SIZE_WIDTH_LEFT()){
					this.position.addX(-SPEED);
					this.position.addY(-SPEED);
				}
	    		this.alertFlag = true;
	    	}
		break;
		
		default:
		break;
		}
		
	}
	
	
	public void newShoot(){
		
		SimpleShoot shoot = new SimpleShoot(this.context,new Vector2D(this.position.getX(), this.position.getY()), this.orientation);
	
		
	    shoots.add(shoot);
	  
		
	}


	@Override
	public void draw(Canvas c) {

		this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
	    		this.position.getX()+this.resizedBitmap.getWidth(), 
	    			this.position.getY()+this.resizedBitmap.getHeight());
	
	    this.drawableImage.draw(c);
	 
	    if(!this.shootsDrawables.isEmpty()){
			for (IWeaponBehavior shoot : this.shootsDrawables) {
				shoot.draw(c);
			}
		}
	    
	    if(this.alertFlag){
	    	this.alertImage.setBounds(100, 100, 400, 200 );
	    	
	    	this.alertImage.draw(c);
	    }
	    
	}
	
	public void drawDrif(Canvas c){
		this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
	    		this.position.getX()+this.resizedBitmap.getWidth(), 
	    			this.position.getY()+this.resizedBitmap.getHeight());
		
		 this.drawableImage.draw(c);
		 
	}

	public Vector2D getPosition() {
		return position;
	}

	public EOrientation getOrientation() {
		return orientation;
	}

	public Boolean getOrientationChange() {
		return orientationChange;
	}

	
	
}


