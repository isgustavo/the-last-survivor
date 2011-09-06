package br.com.thelastsurvivor.engine.game;

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
	
	@Override
	public void updateOrientation(Float orientationX, Float orientationY){
	
		this.sensorPosition.setX(((orientationX.intValue())*10));
		this.sensorPosition.setY(((orientationY.intValue())*10));
	}
	
	@Override
	public void update() {
		
		this.alertFlag = false;
		
		//controlUpdate();
		
		newControlUpdate();
	
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
	
	private void newControlUpdate(){
		if((this.sensorPosition.getX() < 10 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() < 0){
			if(this.position.getY() >= EngineGame.getSCREEN_SIZE_HEIGHT_UP()){
	    		this.position.addY(-SPEED);
	    	}
		}else if(this.sensorPosition.getX() > 0 && (this.sensorPosition.getY() < 3 && this.sensorPosition.getY() > -3)){
			this.matrix.setRotate(++rotacao);
		}else if((this.sensorPosition.getX() < 3 && this.sensorPosition.getX() > -3) && this.sensorPosition.getY() > 0){
			if(this.position.getY() <= EngineGame.getSCREEN_SIZE_HEIGHT_DOWN()){
	    		this.position.addY(SPEED);
	    	}
		}else if(this.sensorPosition.getX() < 0 && (this.sensorPosition.getY() < 3 && this.sensorPosition.getY() > -3)){
			this.matrix.setRotate(rotacao);
		}
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


