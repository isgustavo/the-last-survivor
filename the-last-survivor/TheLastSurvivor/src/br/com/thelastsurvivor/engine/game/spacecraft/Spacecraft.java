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
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;
import br.com.thelastsurvivor.engine.game.weapon.SimpleShoot;
import br.com.thelastsurvivor.engine.simple.IDrawControllable;
import br.com.thelastsurvivor.engine.simpleplayergame.Orientation;
import br.com.thelastsurvivor.engine.util.IDraw;
import br.com.thelastsurvivor.engine.util.IDrawBehavior;
import br.com.thelastsurvivor.util.Vector2D;

public class Spacecraft implements IDraw, IDrawControllable, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Context context;
	
	private String name;
	private Integer life;
	private Boolean isDead;
	private Integer points;
	private Integer color;
	private Vector2D position;
	private Vector2D sensorPosition = new Vector2D(0,0);
	private Display display;
	
	private static  Bitmap image;
	private static  Bitmap image2;
	private static  Bitmap image3;
	private static  Bitmap image4;
	
	private Integer sizeWidht;
	private Integer sizeHeight;
	
	private Bitmap resizedBitmap;
	private Drawable drawableImage;

	private Boolean left = false;
	private Boolean right = false;
	
	
	private Boolean down = false;
	private Boolean up = false; 

	private Double angle = 0.0;
	private Matrix matrix;
	
	private Boolean newShoot = false;
	private List<IDrawBehavior> shoots = new ArrayList<IDrawBehavior>();
	private List<IDrawBehavior> shootsDrawables = new ArrayList<IDrawBehavior>();
	
	public Spacecraft(String name, Display display){
		
		this.name = name;
		this.display = display;
		this.isDead = false;
	}
	
	public Spacecraft(Vector2D position, Double angle, String name, Display display){
		this.position = position;
		this.angle = angle;
		this.name = name;
		this.display = display;
	}
	
	
	/*inicia nave no cliente draw */
	public Spacecraft(Context context, Vector2D position, Double angle, Integer color, Display display){
		this.context = context;
		this.position = position;
		this.angle = angle;
		this.color = color;
		this.newShoot = false;
		this.display = display;
		
		
		init();
		
		initImageSpacecraft();
		
	}
	
	public Spacecraft(Context context, Vector2D position,String name, Integer color, Display display){
		this.context = context;
		this.position = position;
		this.color = color;
		this.name = name;
		this.display = display;
		init();
		
		initImageSpacecraft();
		
	}
	
	
	/*Inicar nave do cliente no servidor*/
	public Spacecraft(Context context, String name, Integer numberClient, Integer color, Display display){
		this.context = context;
		this.name = name;
		getStartingPositionClient(numberClient);
		this.color = color;
		this.newShoot = false;
		this.display = display;
		init();
		
		initImageSpacecraft();
	}
	
	public void initImageSpacecraft(){
		
		this.matrix = new Matrix();
		this.matrix.setRotate(angle.floatValue());
		
		switch(this.color){
		case 1:
			if(this.image == null)
				this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spacecraft_red_image);
			
			sizeWidht = image.getWidth();
			sizeHeight = image.getHeight();
			this.resizedBitmap = Bitmap.createBitmap(image, 0, 0,image.getWidth(), image.getHeight(), matrix, true);
		break;
		case 2:
			if(this.image2 == null)
				this.image2 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spacecraft_image_v2);
			
			sizeWidht = image2.getWidth();
			sizeHeight = image2.getHeight();
			this.resizedBitmap = Bitmap.createBitmap(image2, 0, 0,image2.getWidth(), image2.getHeight(), matrix, true);
		break;
		case 3:
			if(this.image3 == null)
				this.image3 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spacecraft_yellow_image);
			
			sizeWidht = image3.getWidth();
			sizeHeight = image3.getHeight();
			this.resizedBitmap = Bitmap.createBitmap(image3, 0, 0,image3.getWidth(), image3.getHeight(), matrix, true);
		break;
		case 4:
			if(this.image4 == null)
				this.image4 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.spacecraft_green_image);
			
			sizeWidht = image4.getWidth();
			sizeHeight = image4.getHeight();
			this.resizedBitmap = Bitmap.createBitmap(image4, 0, 0,image4.getWidth(), image4.getHeight(), matrix, true);
		break;
		}

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
		
		if(this.life == null){
			this.life = 5;
		}
		
		if(this.points == null){
			this.points = 0;
		}
		
		if(this.angle == null){
			this.angle = 0.0;
		}
		
		this.sensorPosition = new Vector2D(0,0);

		this.isDead = false;
		
		this.left = false;
		this.right = false;
		this.up = false;
		this.down = false;

    	this.shoots = new ArrayList<IDrawBehavior>();
    	this.shootsDrawables = new ArrayList<IDrawBehavior>();

    	
    
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
	
		//Log.d("UPDATE ORIENTATION", "X"+orientationX+"Y"+orientationY);
		
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
	Float back = 1.0f;
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
			back = 1.0f;
		}else{
			back += 0.5f;
			Vector2D vetor = new Vector2D();
			
			Orientation.getNewPosition(angle, vetor);
			
			this.position.addFloatX((vetor.getFloatX()/back) < 0 ? 0 : vetor.getFloatX()/back);
			this.position.addFloatY((vetor.getFloatY()/back) < 0 ? 0 : vetor.getFloatY()/back);
		}
	
	    initImageSpacecraft();
	}
	
	
	
	public void controlShoots(){
		if(this.shootsDrawables != null){
			
			this.shootsDrawables();
			
			this.shootsDrawables.addAll(shoots);
			Log.d("shootsDrawables","."+this.shootsDrawables.size());
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
		newShoot = true;
		Log.d("shoot","shoot");
		shoots.add(new SimpleShoot(this.context,new Vector2D(this.position.getX(), this.position.getY()), this.angle, this.color, display));
		Log.d("shootSize","."+shoots.size());
	}
	
	public void newShootClient(){
		newShoot = true;
	}

	
	
	@Override
	public void draw(Canvas c) {

		
		if(display.getWidth() > MultiGameActivity.DISPLAY_WIDHT){
			c.drawBitmap(this.resizedBitmap, (this.position.getFloatX()*1.5f) , (this.position.getFloatY()*1.5f),null);
		}else{
			c.drawBitmap(this.resizedBitmap, this.position.getFloatX() , this.position.getFloatY(),null);
		}
		
		 
	
	    
		if(!this.shootsDrawables.isEmpty()){
			for (IDrawBehavior shoot : this.shootsDrawables) {
				shoot.draw(c);
			}
	   }   
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(this.position.getFloatX().equals(((Spacecraft)o).getPosition().getFloatX())
				&& this.position.getFloatY().equals(((Spacecraft)o).getPosition().getFloatY()))
			return true;
		else 
			return false;
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
	@Override
	public Integer getSizeWidth() {
		return this.sizeWidht;
	}

	@Override
	public Integer getSizeHeight() {
		return sizeHeight;
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
		return newShoot;
	}
	
	public void setNewShoot(Boolean b) {		
		newShoot = b;
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

	public List<IDrawBehavior> getShoots() {
		return shoots;
	}

	public void setShoots(List<IDrawBehavior> shoots) {
		this.shoots = shoots;
	}

	public List<IDrawBehavior> getShootsDrawables() {
		return shootsDrawables;
	}

	public void setShootsDrawables(List<IDrawBehavior> shootsDrawables) {
		this.shootsDrawables = shootsDrawables;
	}

	public Integer getPoints() {
		return points;
	}

	public void addPoint(Integer point){
		this.points += point;
	}
	

	public void addLife(Integer life){
		this.life += life;
	}

	public Boolean getIsDead() {
		return isDead;
	}

	public void setIsDead(Boolean isDead) {
		this.isDead = isDead;
	}

	
	
}





