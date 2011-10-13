package br.com.thelastsurvivor.engine.game.spacecraft;

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
import android.util.Log;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.game.weapon.IWeaponBehavior;
import br.com.thelastsurvivor.engine.game.weapon.SimpleShoot;
import br.com.thelastsurvivor.engine.simple.IDrawControllable;
import br.com.thelastsurvivor.engine.simpleplayergame.Orientation;
import br.com.thelastsurvivor.engine.util.IDraw;
import br.com.thelastsurvivor.util.Vector2D;

public class Spacecraft implements IDraw, IDrawControllable, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Context context;
	
	private String name;
	private Integer life;
	private Integer color;
	private Vector2D position;
	private Vector2D sensorPosition = new Vector2D(0,0);
	
	private Bitmap image;
	private Bitmap resizedBitmap;
	private Drawable drawableImage;
	
	//private Boolean orientationChange;

	private Boolean left = false;
	//private Boolean flagLeft;
	private Boolean right = false;
	//private Boolean flagRight;
	
	private Boolean down = false;
	private Boolean up = false; 

	private Double angle = 0.0;
	private Matrix matrix;
	
	private Boolean newShoot = false;
	private List<IWeaponBehavior> shoots = new ArrayList<IWeaponBehavior>();
	private List<IWeaponBehavior> shootsDrawables = new ArrayList<IWeaponBehavior>();
	
	public Spacecraft(String name){
		
		this.name = name;
	}
	
	public Spacecraft(Context context, Vector2D position){
		this.context = context;
		this.position = position;
		
		
		init();
	}
	
	public Spacecraft(Context context, Vector2D position, Double angle, String name){
		this.context = context;
		this.position = position;
		this.angle = angle;
		this.name = name;
		init();
	}
	
	public Spacecraft(Vector2D position, Double angle, String name){
		this.position = position;
		this.angle = angle;
		this.name = name;
		
	}
	
	
	
	/*inicia nave no cliente draw */
	public Spacecraft(Context context, Vector2D position, Double angle, Integer color){
		this.context = context;
		this.position = position;
		this.angle = angle;
		this.color = color;
		this.newShoot = false;
		
		initImageSpacecraft();
		
	}
	
	public Spacecraft(Context context, Vector2D position, Integer color){
		this.context = context;
		this.position = position;
		this.color = color;
		
		initImageSpacecraft();
		
	}
	
	
	/*Inicar nave do cliente no servidor*/
	public Spacecraft(Context context, String name, Integer numberClient, Integer color){
		this.context = context;
		this.name = name;
		getStartingPositionClient(numberClient);
		this.color = color;
		this.newShoot = false;
		
		initImageSpacecraft();
	}
	
	public void initImageSpacecraft(){
		
		switch(this.color){
		case 1:
			this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spacecraft_image);
		break;
		case 2:
			this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spacecraft_image);
		break;
		case 3:
			this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spacecraft_image);
		break;
		case 4:
			this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spacecraft_image);
		break;
		}
		
		this.matrix = new Matrix();
		this.matrix.setRotate(angle.floatValue());
		
		this.resizedBitmap = Bitmap.createBitmap(image, 0, 0,image.getWidth(), image.getHeight(), matrix, true);
		
	}
	
	public void getStartingPositionClient(Integer number){
		switch (number) {
		case 0:
			//spacecrafts.add(new Spacecraft(context, new Vector2D(200, 50), new Double(180.0), name, colorClient));
			//return protocol.protocolSendsInitSpacecraftClient(name, colorClient);
			this.position = new Vector2D(200,50);
			this.angle = new Double(180.0);
		break;
		case 1:
			//spacecrafts.add(new Spacecraft(context, new Vector2D(350, 100), new Double(270.0), name, colorClient));
			//return protocol.protocolSendsInitSpacecraftClient(name, colorClient);
			this.position = new Vector2D(350,100);
			this.angle = new Double(270.0);
			break;
		case 2:
			//spacecrafts.add(new Spacecraft(context, new Vector2D(50, 100), new Double(90.0), name, colorClient));
			//return protocol.protocolSendsInitSpacecraftClient(name, colorClient);
			this.position = new Vector2D(50,100);
			this.angle = new Double(90.0);
		break;
		}
	}
	
	@Override
	public void init() {
		
		this.sensorPosition = new Vector2D(0,0);
		//this.orientationChange = false;
		
		this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spacecraft_image);
		
		
		this.left = false;
		//this.flagLeft = false;
		this.right = false;
		//this.flagRight = false;
		this.up = false;
		this.down = false;
		
        this.matrix = new Matrix();
		this.matrix.setRotate(angle.floatValue());
		
    	this.resizedBitmap = Bitmap.createBitmap(image, 0, 0,image.getWidth(), image.getHeight(), matrix, true);
    	
    	this.shoots = new ArrayList<IWeaponBehavior>();
    	this.shootsDrawables = new ArrayList<IWeaponBehavior>();
    	
	}
	
	public void initClient(){
		this.sensorPosition = new Vector2D(0,0);
		//this.orientationChange = false;
		
		this.left = false;
		this.right = false;
		this.up = false;
		this.down = false;
		
	}
	
	@Override
	public void updateOrientation(Float orientationX, Float orientationY){
	
		Log.d("UPDATE ORIENTATION", "X"+orientationX+"Y"+orientationY);
		
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
	
	public void updateSensor(){
		this.sensorControlUpdate();
	}
	
	public void updateClient(){
		this.controlUpdate();
		
		BitmapDrawable newImage = new BitmapDrawable(this.resizedBitmap);
		this.drawableImage = newImage;
		
		this.controlShoots();
		
		this.left = false;
		this.right = false;
		this.down = false;
		this.up = false;
	}
	
	public boolean flagUp = false;
	public void sensorControlUpdate(){
		this.left = false;
		this.right = false;
		this.down = false;
		this.up = false;
		flagUp = false;
		
		if (this.sensorPosition.getX() > 15 ) {
	    	right = true;
	    }else if(this.sensorPosition.getX() < -15) {
	    	left = true;
	    }else if(this.sensorPosition.getY() < -10){
			up = true;
		}
		
		Log.d("sensorCOltrol", ""+this.sensorPosition.getY()+""+this.sensorPosition.getX()+""+this.left+""+this.right+""+this.down+""+this.up);
	}
	
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
		
		
	/*	if(!flagUp){
			
		}else{
			
		}
		if(this.angle <= 270 && this.angle >= 90){
			boolean temp = this.left;
			this.left = this.right;
			this.right = temp;
		}
		*/
		
		
		if (this.up) {
			Orientation.getNewPosition(this.angle, this.position);
	    }
		
	
		/*
		 if(this.angle >= 270 || this.angle <= 90){
			   Orientation.getNewPosition(this.angle, this.position);
			   
			   
		   }else{
			   Orientation.getNewPosition(angle, this.position);
			   
		   }
		
	    }else if(right) {
	    	if(this.angle == 350){
	    		this.angle = 0.0;
	    	}else{
	    		if(flagLeft != null && flagLeft){
	    			this.angle -= 5;
	    		}else{
	    			this.angle += 5;
	    		}
	    		
	    	}
	    		
	    }else if (this.up) {
			   if(this.angle >= 270 || this.angle <= 90){
				   Orientation.getNewPosition(this.angle, this.position);
				   
				   
			   }else{
				   Orientation.getNewPosition(angle, this.position);
				   
			   }
	    }
			 */ 
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
		newShoot = true;
		SimpleShoot shoot = new SimpleShoot(this.context,new Vector2D(this.position.getX(), this.position.getY()), this.angle, this.image);	
	    shoots.add(shoot);
	}

	@Override
	public void draw(Canvas c) {

		 Log.d("DRAW", "Spqcefraft");

		
		c.drawBitmap(this.resizedBitmap, this.position.getX() , this.position.getY(),null);
		 
	
	    
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getColor() {
		return color;
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

	public void setDown(Boolean down) {
		this.down = down;
	}

	public void setUp(Boolean up) {
		this.up = up;
	}

	

	
	
}


