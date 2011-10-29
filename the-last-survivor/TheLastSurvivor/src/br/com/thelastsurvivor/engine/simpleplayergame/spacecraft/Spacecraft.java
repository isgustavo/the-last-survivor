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
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.simpleplayergame.Orientation;
import br.com.thelastsurvivor.engine.simpleplayergame.weapon.ShootFactory;
import br.com.thelastsurvivor.engine.util.IDrawBehavior;
import br.com.thelastsurvivor.engine.util.IDrawControllable;
import br.com.thelastsurvivor.model.game.Shoot;
import br.com.thelastsurvivor.util.Vector2D;

public class Spacecraft implements IDrawControllable, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Context context;
	private Display display;
	
	private Bitmap image;
	private Integer width;
	private Integer height;
	
	private Bitmap resizedBitmap;
	private Drawable drawableImage;
	
	private Integer life;
	private Integer points;
	private Vector2D position;
	private Vector2D sensorPosition;

	private Boolean left; 
	private Boolean right; 
	private Boolean up;  
	private Boolean down; 

	private Double angle;
	private Matrix matrix;
	
	private Boolean newShoot = false;
	private List<IDrawBehavior> shoots = new ArrayList<IDrawBehavior>();
	private List<IDrawBehavior> shootsDrawables = new ArrayList<IDrawBehavior>();
	
	public Spacecraft(Context context, Display display){
		this.context = context;
		this.display = display;
		
		
		init();
	}
	
	public Spacecraft(Context context, Display display, br.com.thelastsurvivor.model.game.Spacecraft spacecraft){
		this.context = context;
		this.display = display;
		this.position = spacecraft.getPosition();
		this.angle = spacecraft.getAngle();
		this.points = spacecraft.getPoints();
		this.life = spacecraft.getLife();
		
		init();
		
		for(Shoot shoot : spacecraft.getShoots()){
			this.shoots.add(ShootFactory.restartShoot(context, shoot));
		}
		
	}
	
	@Override
	public void init() {
		
		
		this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spacecraft_image);
		
	
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		if(this.position == null){
			this.position = new Vector2D((display.getWidth()/2-width), 
					(display.getHeight()/2)-height);
		}
	
		
		if(this.life == null){
			this.life = 2;
		}
		
		if(this.points == null){
			this.points = 0;
		}
		
		if(this.angle == null){
			this.angle = 0.0;
		}
		
		this.matrix = new Matrix();
		this.matrix.setRotate(angle.floatValue());
		
		this.resizedBitmap = Bitmap.createBitmap(image, 0, 0,image.getWidth(), image.getHeight(), matrix, true);
		
		this.sensorPosition = new Vector2D(0,0);

		
		this.left = false;
		this.right = false;
		this.up = false;
		this.down = false;

    	this.shoots = new ArrayList<IDrawBehavior>();
    	this.shootsDrawables = new ArrayList<IDrawBehavior>();

    	
    
	}
	
	
	
	@Override
	public void update() {
		
		this.sensorControlUpdate();
		this.controlUpdate();
	
		BitmapDrawable newImage = new BitmapDrawable(this.resizedBitmap);
		this.drawableImage = newImage;
		
		this.controlShoots();
	}
	
	@Override
	public void draw(Canvas c) {

		//c.drawBitmap(this.resizedBitmapBackground, this.positionBackground.getFloatX(), 
		//		this.positionBackground.getFloatY(), null);
		c.drawBitmap(this.resizedBitmap, this.position.getFloatX(), 
				this.position.getFloatY(), null);
		

		//Log.d("DOUBLE","M"+(Math.tan(angle)));

	   if(!this.shootsDrawables.isEmpty()){
			for (IDrawBehavior shoot : this.shootsDrawables) {
				shoot.draw(c);
			}
	   }
  
	}
	
	@Override
	public void updateOrientation(Float orientationX, Float orientationY){
	
		this.sensorPosition.setX(((orientationX.intValue())*10));
		this.sensorPosition.setY(((orientationY.intValue())*10));

	}

	public void sensorControlUpdate(){
		
		this.left = false;
		this.right = false;
		this.up = false;
		this.down = false;
		
		if (this.sensorPosition.getX() > 8) {
	    	right = true;
	    }
		
	    if(this.sensorPosition.getX() < -8) {
	    	left = true;
	    }
	    
	    if(this.sensorPosition.getY() > 5){
			down = true;
		}
	}
	
	Double angleTemp = 0.0;
	Vector2D backing = new Vector2D(0,0);
	private void controlUpdate(){
			
		if(this.angle >= 360){
    		this.angle = 0.0;
    	}else if(this.angle <= 0){
    		this.angle = 360.0;
    	}
	 
		if (this.left) {
    		this.angle -= 5;
	    }else if(this.right){
	    	this.angle += 5;
	    }
		
		if (!this.down) {
			Vector2D vetor = new Vector2D();
			
			Orientation.getNewPosition(angle, vetor);
			
			this.position.addFloatX(vetor.getFloatX()/2);
			this.position.addFloatY(vetor.getFloatY()/2);
			
		}else{
			Vector2D vetor = new Vector2D();
			
			Orientation.getNewPosition(angle, vetor);
			
			this.position.addFloatX(vetor.getFloatX()/5);
			this.position.addFloatY(vetor.getFloatY()/5);
		}
		
		
		//angle = 200.0;
		
		//Vector2D vetor = new Vector2D();
		
		
		
		//this.position.addFloatX(vetor.getFloatX()*2);
		//this.position.addFloatY(vetor.getFloatY()*2);
		
		/*if (!this.down) {
	    	angleTemp = angle;
			Orientation.getNewPosition(this.angle, this.position);
			//backing = position;
	    }else if (this.left) {
    		this.angle -= 5;
    		Orientation.getNewPosition(angleTemp, this.backing);
    		//this.position.setX();
    		//this.position.setY();
	    }else if(this.right){
	    	this.angle += 5;
	    	Orientation.getNewPosition(angleTemp, this.position);
	    }else{
	    	Orientation.getNewPosition(angleTemp, this.position);
	    }*/
		
		 this.matrix.setRotate(angle.floatValue());
	   	 this.resizedBitmap = Bitmap.createBitmap(this.image, 0, 0,
	   					this.image.getWidth(), this.image.getHeight(), this.matrix, true);
	   	
	   	 //this.resizedBitmapBackground = Bitmap.createBitmap(this.imageBackground, 0, 0,
		//			this.imageBackground.getWidth(), this.imageBackground.getHeight(), this.matrix, true);
		
	}
	
	public void controlShoots(){
		if(this.shootsDrawables != null){
			
			this.shootsDrawables();
			
			this.shootsDrawables.addAll(shoots);
			
			for (IDrawBehavior shoot : shootsDrawables) {
				shoot.update();
			}
			
			this.shoots.clear();
		}
		
	}
	
	private void shootsDrawables(){
		List<IDrawBehavior> shoots = new ArrayList<IDrawBehavior>();
		for(IDrawBehavior shoot : this.shootsDrawables){
			if(shoot.isAlive()){
				shoots.add(shoot);
			}
		}
		
		this.shootsDrawables.clear();
		this.shootsDrawables.addAll(shoots);
		
	}

	public void newShoot(){
	
		shoots.addAll(ShootFactory.newShoot(this.context, new Vector2D(this.position.getX(),this.position.getY()), this.angle, this.image));	
	   
	}

	public Integer getPoints() {
		return points;
	}

	public void addPoint(Integer point){
		this.points += point;
	}
		
	public Integer getLife() {
		return life;
	}

	public void addLife(Integer life){
		this.life += life;
	}

	public Vector2D getPosition() {
		return position;
	}

	public List<IDrawBehavior> getShootsDrawables() {
		return shootsDrawables;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	public Boolean getUp() {
		return up;
	}

	public Double getAngle() {
		return angle;
	}

	
	
	
	
}


