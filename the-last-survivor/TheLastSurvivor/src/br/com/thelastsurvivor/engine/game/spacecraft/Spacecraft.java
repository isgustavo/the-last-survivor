package br.com.thelastsurvivor.engine.game.spacecraft;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.CameraGame;
import br.com.thelastsurvivor.engine.EngineGame;
import br.com.thelastsurvivor.engine.IDrawControllable;
import br.com.thelastsurvivor.engine.Orientation;
import br.com.thelastsurvivor.engine.game.weapon.IWeaponBehavior;
import br.com.thelastsurvivor.engine.game.weapon.SimpleShoot;
import br.com.thelastsurvivor.util.Vector2D;

public class Spacecraft implements IDrawControllable {
	
	private Context context;
	
	private Bitmap image;
	private Bitmap resizedBitmap;
	private Drawable drawableImage;
	
	private Vector2D position;
	private Vector2D sensorPosition;
	
	private Boolean orientationChange;

	private Boolean left;
	private Boolean right;
	
	private Boolean down;
	private Boolean up; 
	
	
	private Double angle = 0.0;
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
		
		this.sensorPosition = new Vector2D(0,0);
		this.orientationChange = false;
		
		this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spacecraft_image);
		
		
		this.left = false;
		this.right = false;
		this.up = false;
		this.down = false;
		
        this.matrix = new Matrix();
		this.matrix.setRotate(0);
		
    	this.resizedBitmap = Bitmap.createBitmap(image, 0, 0,image.getWidth(), image.getHeight(), matrix, true);
    	
    	this.shoots = new ArrayList<IWeaponBehavior>();
    	this.shootsDrawables = new ArrayList<IWeaponBehavior>();
    	
	}
	
	@Override
	public void updateOrientation(Float orientationX, Float orientationY){
	
		this.sensorPosition.setX(((orientationX.intValue())*10));
		this.sensorPosition.setY(((orientationY.intValue())*10));

	}
	

	@Override
	public void update() {
		
		
		this.sensorControlUpdate();
		this.controlUpdate();
			
		BitmapDrawable newImage = new BitmapDrawable(this.resizedBitmap);
		this.drawableImage = newImage;
		
		this.controlShoots();

		
	}
	
	public void sensorControlUpdate(){
		this.left = false;
		this.right = false;
		this.down = false;
		this.up = false;
		
		
		if (this.sensorPosition.getX() > 20 ) {
	    	right = true;
	    }else if(this.sensorPosition.getX() < -20) {
	    	left = true;
	    }else if(this.sensorPosition.getY() > 15){
			down = true;
		}else if(this.sensorPosition.getY() < -10){
			up = true;
		}
		
		
	}
	
	private void controlUpdate(){
			
	 
		if (left) {
	    	if(this.angle == 360){
	    		this.angle = 0.0;
	    	}else{
	    		if(this.angle == 0){
	    			this.angle = 360.0;
	    		}
	    		this.angle -= 5;
	    	}
	    }else if(right) {
	    	if(this.angle == 350){
	    		this.angle = 0.0;
	    	}else{
	    		this.angle += 5;
	    	}
	    		
	    }else if (this.up) {
			   if(this.angle >= 270 || this.angle <= 90){
				   Orientation.getNewPosition(this.angle, this.position);
			   }else{
				   Orientation.getNewPosition(angle, this.position);
			   }
			   
			/*   else{
				   Double invertAngle;
			   
				   if(this.angle <= 180){
					   invertAngle = this.angle+180;
				   }else{
					   invertAngle = 180 - (360 - this.angle);
				   }
			   
				   Orientation.getNewPosition(invertAngle, this.position);
			   }*/
	     }else if(this.down){
		   if(this.angle >= 270 || this.angle <= 90){
			  
			   Double invertAngle;
			   
			   if(this.angle >= 270){
				   invertAngle = (360 - this.angle) + 90;
				   
			   }else{
				   invertAngle = this.angle+180;
			   }
			   
			   Orientation.getNewPosition(invertAngle, this.position); 
			   
		   }else{
			   Orientation.getNewPosition(angle, this.position);
		   }
	    }
	   
	    
	    
	    
	    this.matrix.setRotate(angle.floatValue());
   		this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
        this.image.getWidth(), this.image.getHeight(), this.matrix, true);
		   
		
	}
	
	
	
	public void controlShoots(){
		if(this.shootsDrawables != null){
			
			this.shootsDrawables();
			
			this.shootsDrawables.addAll(shoots);
			
			for (IWeaponBehavior shoot : shootsDrawables) {
				shoot.update();
			}
			
			this.shoots.clear();
		}
		
	}
	
	private void shootsDrawables(){
		List<IWeaponBehavior> shoots = new ArrayList<IWeaponBehavior>();
		for(IWeaponBehavior shoot : this.shootsDrawables){
			if(shoot.isAlive()){
				shoots.add(shoot);
			}
		}
		
		this.shootsDrawables.clear();
		this.shootsDrawables.addAll(shoots);
		
	}

	public void newShoot(){
		
		SimpleShoot shoot = new SimpleShoot(this.context,new Vector2D(this.position.getX(), this.position.getY()), this.angle, this.image);	
	    shoots.add(shoot);
	}

	@Override
	public void draw(Canvas c) {

		c.drawBitmap(this.resizedBitmap, this.position.getX() , this.position.getY(),null);
		 
	/*		  this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
			    		this.position.getX()+this.resizedBitmap.getWidth(), 
			    			this.position.getY()+this.resizedBitmap.getHeight());
				  
			 
		   this.drawableImage.draw(c);*/
	    
		   if(!this.shootsDrawables.isEmpty()){
			for (IWeaponBehavior shoot : this.shootsDrawables) {
				shoot.draw(c);
			}
		}
	    
	   
	    
	    
	}
	
	@Override
	public boolean isAlive() {
		return true;
	}
	
	public Vector2D getPosition() {
		return position;
	}

	public Double getAngle() {
		return angle;
	}

	public Boolean getDown() {
		return down;
	}

	public Boolean getUp() {
		return up;
	}

	public List<IWeaponBehavior> getShootsDrawables() {
		return shootsDrawables;
	}

	@Override
	public Integer getSizeWidth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getSizeHeight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAlive(boolean alive) {
		// TODO Auto-generated method stub
		
	}

	

	
	
}


