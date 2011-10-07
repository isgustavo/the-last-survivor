package br.com.thelastsurvivor.engine.simpleplayergame.spacecraft;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.game.weapon.IWeaponBehavior;
import br.com.thelastsurvivor.engine.game.weapon.SimpleShoot;
import br.com.thelastsurvivor.engine.simple.IDrawControllable;
import br.com.thelastsurvivor.engine.simple.Orientation;
import br.com.thelastsurvivor.engine.util.IDraw;
import br.com.thelastsurvivor.util.Vector2D;

public class Spacecraft implements IDraw, IDrawControllable, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Context context;
	
	private Integer life;
	private Integer points;
	private Vector2D position;
	private Vector2D sensorPosition = new Vector2D(0,0);
	
	private Bitmap image;
	private Bitmap resizedBitmap;
	private Integer width;
	private Integer height;
	private Drawable drawableImage;
	
	private Boolean left = false;
	private Boolean right = false;
	private Boolean up = false; 

	private Double angle = 0.0;
	private Matrix matrix;
	
	private Boolean newShoot = false;
	private List<IWeaponBehavior> shoots = new ArrayList<IWeaponBehavior>();
	private List<IWeaponBehavior> shootsDrawables = new ArrayList<IWeaponBehavior>();
	
	public Spacecraft(Context context, Vector2D position){
		this.context = context;
		this.position = position;
		
		
		init();
	}
	
	@Override
	public void init() {
		
		this.sensorPosition = new Vector2D(0,0);
		this.life = 500;
		this.points = 0;
		
		this.left = false;
		this.right = false;
		this.up = false;
		
		this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spacecraft_image);
		
		this.matrix = new Matrix();
		this.matrix.setRotate(angle.floatValue());
		
		this.width = image.getWidth();
		this.height = image.getHeight();
		
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
		this.up = false;
		
		if (this.sensorPosition.getX() > 15 ) {
	    	right = true;
	    }else if(this.sensorPosition.getX() < -15) {
	    	left = true;
	    }else if(this.sensorPosition.getY() < -10){
			up = true;
		}
	}
	
	private void controlUpdate(){
			
		if(this.angle >= 360){
    		this.angle = 0.0;
    	}else if(this.angle <= 0){
    		this.angle = 360.0;
    	}
	 
		
		if (this.left) {
    		this.angle -= 10;
	    }else if(this.right){
	    	this.angle += 10;
	    }
		
		if (this.up) {
			Orientation.getNewPosition(this.angle, this.position);
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

	   if(!this.shootsDrawables.isEmpty()){
			for (IWeaponBehavior shoot : this.shootsDrawables) {
				shoot.draw(c);
			}
	   }
  
	}
	
	
	
	public void addPoint(Integer point){
		this.points += point;
	}
	
	public void addLife(Integer life){
		this.life += life;
	}
	
	public Integer getPoints() {
		return points;
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

	public Boolean getUp() {
		return up;
	}

	public List<IWeaponBehavior> getShootsDrawables() {
		return shootsDrawables;
	}

	@Override
	public Integer getSizeWidth() {
		return this.width;
	}

	@Override
	public Integer getSizeHeight() {
		return this.height;
	}

	@Override
	public void setAlive(boolean alive) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getLife() {
		return this.life;
	}

	@Override
	public Integer getTypeImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getRoute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getPower() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public void setAngle(Double angle) {
		this.angle = angle;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	
	
	public Boolean getLeft() {
		return left;
	}

	public Boolean getRight() {
		return right;
	}

	public Boolean getNewShoot() {
		this.newShoot = !newShoot;
		
		return !newShoot;
	}

	public List<IWeaponBehavior> getShoots() {
		return shoots;
	}

	public void setLeft(Boolean left) {
		this.left = left;
	}

	public void setRight(Boolean right) {
		this.right = right;
	}



	public void setUp(Boolean up) {
		this.up = up;
	}

	

	
	
}


