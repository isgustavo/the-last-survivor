package br.com.thelastsurvivor.engine.simpleplayergame.weapon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.game.weapon.IWeaponBehavior;
import br.com.thelastsurvivor.engine.simpleplayergame.EngineGame;
import br.com.thelastsurvivor.engine.simpleplayergame.Orientation;
import br.com.thelastsurvivor.engine.util.IDraw;
import br.com.thelastsurvivor.engine.util.IDrawBehavior;
import br.com.thelastsurvivor.util.Vector2D;

public class SimpleShoot  implements  IDraw, IDrawBehavior, IWeaponBehavior{
	
	private final int power = 1;
	
	private Context context;
	private Display display;
	
	private Bitmap image;
	private Bitmap resizedBitmap;
	private Drawable drawableImage;
	private Integer sizeWidth;
	private Integer sizeHeight;
	
	private Bitmap spacecraft;
	
	private Vector2D position;
	private Double angle;
	
	private Matrix matrix;
	private Boolean isAlive;
	private Integer color;
	
	//public SimpleShoot(Context context, Vector2D position, Double angle){
	//	this.context = context;
	//	this.position = position;
	//	this.angle = angle;
		
		//this.spacecraft = spacecraft;
	//	
	//	init();
	//}
	
	public SimpleShoot(Context context, Vector2D position, Double angle, Display display){
		this.context = context;
		this.position = position;
		this.angle = angle;
		this.display = display;
		
		init();
	}
	
	@Override
	public void init() {
		this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.simple_shoot_blue_image);
		
		this.sizeHeight = image.getHeight();
		this.sizeWidth = image.getWidth();
		
	
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
		
		if(-10 > this.getPosition().getY()){
			this.isAlive = false;
		}else if(this.getPosition().getY() > EngineGame.getCamera().getY()+10){
			this.isAlive = false;
		}
	
		if(-10 > this.getPosition().getX()){
			this.isAlive = false;
		}else if(this.getPosition().getX() > EngineGame.getCamera().getX()+10){
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
	public void setAlive(boolean alive){
		this.isAlive = alive;
	}

	@Override
	public Vector2D getPosition() {
		// TODO Auto-generated method stub
		return this.position;
	}


	@Override
	public Integer getSizeWidth() {
		return this.sizeWidth;
	}


	@Override
	public Integer getSizeHeight() {
		return this.sizeHeight;
	}


	public Integer getPower() {
		return power;
	}


	@Override
	public Integer getLife() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer getTypeImage() {
		return null;
	}


	@Override
	public Integer getRoute() {
		// TODO Auto-generated method stub
		return null;
	}


	public Double getAngle() {
		return angle;
	}

	@Override
	public Integer getColor() {
		return this.color;
	}


}