package br.com.thelastsurvivor.engine.game.weapon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.EngineGame;
import br.com.thelastsurvivor.engine.IDrawBehavior;
import br.com.thelastsurvivor.engine.Orientation;
import br.com.thelastsurvivor.engine.util.Constant;
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
    	
	
		BitmapDrawable newImage = new BitmapDrawable(this.resizedBitmap);
		this.drawableImage = newImage;
	}

	
	private void checkOutShootsOfTheGameSpace(){
		if(-40 > position.getX() &&
				position.getX() > EngineGame.getCamera().getX()+40){
			this.isAlive = false;
		}else if(-40 > position.getY() &&
				position.getY() > EngineGame.getCamera().getY()+40){
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