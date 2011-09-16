package br.com.thelastsurvivor.engine.game.weapon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.IDrawBehavior;
import br.com.thelastsurvivor.engine.Orientation;
import br.com.thelastsurvivor.engine.util.Constant;
import br.com.thelastsurvivor.engine.util.EOrientation;
import br.com.thelastsurvivor.util.Vector2D;

public class SimpleShoot implements IDrawBehavior, IWeaponBehavior{
	
	private static final int SPEED_SHOOT = 6;
	
	private Context context;
	
	private Bitmap image;
	private Bitmap resizedBitmap;
	private Drawable drawableImage;
	
	private Bitmap spacecraft;
	
	private Vector2D position;
	private Double angle;
	
	private Matrix matrix;
	private Boolean isAlive;
	
	public SimpleShoot(Context context, Vector2D position, Double angle, Bitmap spacecraft){
		this.context = context;
		this.position = position;
		this.angle = angle;
		
		this.spacecraft = spacecraft;
		
		init();
	}
	
	
	@Override
	public void init() {
		this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.simple_shoot_image);
		//this.image = this.context.getResources().getDrawable(R.drawable.simple_shoot);
		
		this.firstPosition();	
		this.isAlive = true;
		
		this.matrix = new Matrix();
		this.matrix.setRotate(0);
		
    	this.resizedBitmap = Bitmap.createBitmap(image, 0, 0,image.getWidth(), image.getHeight(), matrix, true);
		
	}

	
	private void firstPosition(){
		
		Orientation.getNewPosition(this.angle, position);
		
		this.position.addX((this.spacecraft.getWidth()/2));
		this.position.addY((this.spacecraft.getHeight()/2));
		

	}
	
	
	@Override
	public void update() {
		
		controlUpdate();
		checkOutShootsOfTheGameSpace();
		
		
	}
	
	private void controlUpdate(){
		
		Orientation.getNewPosition(this.angle, this.position);
		
		
		
		
		
		this.matrix.setRotate(this.angle.floatValue());
    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
    	
		
	/*	switch (this.orientation.getOrientation()) {
		case 1:
			this.matrix.setRotate(0);
	    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
	            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
			this.position.addY(-SPEED_SHOOT);
		break;

		case 2:
			this.matrix.setRotate(45);
	    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
	            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
			this.position.addX(SPEED_SHOOT);
			this.position.addY(-SPEED_SHOOT);
			
		break;
		
		case 3:
			this.matrix.setRotate(90);
	    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
	            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
			this.position.addX(SPEED_SHOOT);
			
		break;	
		
		case 4:
			this.matrix.setRotate(135);
	    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
	            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
			this.position.addX(SPEED_SHOOT);
			this.position.addY(SPEED_SHOOT);
			
		break;
		
		case 5:
			this.matrix.setRotate(180);
	    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
	            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
			this.position.addY(SPEED_SHOOT);
		break;
		
		case 6:
			this.matrix.setRotate(225);
	    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
	            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
			this.position.addX(-SPEED_SHOOT);
			this.position.addY(SPEED_SHOOT);
		break;
		
		
		case 7:
			this.matrix.setRotate(270);
	    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
	            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
			this.position.addX(-SPEED_SHOOT);
		break;
		
		
		case 8:
			this.matrix.setRotate(315);
	    	this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
	            this.image.getWidth(), this.image.getHeight(), this.matrix, true);
			this.position.addX(-SPEED_SHOOT);
			this.position.addY(-SPEED_SHOOT);
		break;
		default:
			break;
		}*/
	
		
		BitmapDrawable newImage = new BitmapDrawable(this.resizedBitmap);
		this.drawableImage = newImage;
	}

	
	private void checkOutShootsOfTheGameSpace(){
		if(!(Constant.LIMIT_LEFT < this.position.getX() && this.position.getX() < Constant.LIMIT_RIGHT
				&& Constant.LIMIT_UP < this.position.getY() && this.position.getY() < Constant.LIMIT_DOWN)){
			this.isAlive = false;
		}
		
	}
	
	@Override
	public void draw(Canvas c) {
		
		this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
	    		this.position.getX()+this.resizedBitmap.getWidth(), 
	    			this.position.getY()+this.resizedBitmap.getHeight());
	    
	    
	    this.drawableImage.draw(c);

	}


	@Override
	public boolean isAlive() {
		return this.isAlive;
	}


	@Override
	public Vector2D getPosition() {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	

	
}