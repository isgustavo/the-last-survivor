package br.com.thelastsurvivor.engine.simpleplayergame.asteroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.simple.EngineGame;
import br.com.thelastsurvivor.engine.simple.IDrawBehavior;
import br.com.thelastsurvivor.engine.util.IDraw;
import br.com.thelastsurvivor.util.Vector2D;

public class Asteroid implements  IDraw, IDrawBehavior{

	private Context context;
	
	private Bitmap image;
	private Drawable drawableImage;
	private Integer sizeWidth;
	private Integer sizeHeight;
	
	private Integer life;
	
	private Boolean isAlive;
	private Integer typeImage;
	private Integer power;
	
	private Vector2D position;
	private Integer route;
	private Integer angle;
	private Integer speed = 1;
	
	public Integer color = Color.argb(255, 255, 255, 255);
	public Integer timeEffect;
	//private Integer MAX_SPEED = 6;
	
	
	public Asteroid(Context context, Display display){
		this.context = context;
		
		
		init();
	}
	
	public Asteroid(Context context){
		this.context = context;
		
		init();
	}
	
	public Asteroid(Context context, Vector2D origin, Integer type, Boolean isAlive){
		this.context = context;
		this.position = origin;
		this.typeImage = type;
		this.isAlive = isAlive;
		
		init();
		
	}
	
	@Override
	public void init() {
		
		
		if(this.position == null){
			this.position = ramdonOrigin();
		}
		
		if(this.isAlive == null){
			this.isAlive = true;
		}
		
		if(typeImage == null){
			ramdomImageAteroid();
		}else{
			imageAsteroid(typeImage);
		}
		
		this.route = (int) (Math.random()*4);
		this.angle = (int) (Math.random()*360);
		this.timeEffect = 1000;
		
		Matrix matrix = new Matrix();
		matrix.setRotate(angle.floatValue());
		
		//BitmapDrawable b = 
		
   		this.drawableImage = new BitmapDrawable(Bitmap.createBitmap(this.image, 0, 0,
   				this.image.getWidth(), this.image.getHeight(), matrix, true));
		
		
	}
	
	@Override
	public void update() {

		this.ramdomRoute();
		//this.checkOutAsteroidsOfTheGameSpace();
	}

	
	/**       0
	 *      ------
	 *    1 |    |3
	 *      ------
	 *        2
	 * @return
	 */
	private Vector2D ramdonOrigin(){
		
		
		int origin = (int) (Math.random()*4);
		int position = 1;
		switch (origin) {
		case 0:
			position = (int)(Math.random()*EngineGame.getCamera().getX());
			return new Vector2D(position,-200);
			
		case 1:
			position = (int)(Math.random()*EngineGame.getCamera().getY());
			return new Vector2D(-200,position);
			
		case 2:
			position = (int)(Math.random()*EngineGame.getCamera().getX());
			return new Vector2D(position, EngineGame.getCamera().getY());
			
		case 3:
			position = (int)(Math.random()*EngineGame.getCamera().getY());
			return new Vector2D(EngineGame.getCamera().getX(),position);
		}
		return null;
		
	}

	private void ramdomRoute() {
		switch(0){
		case 0:
			this.position.addX(speed); 
			this.position.addY(speed);
		break;
		case 1:
			this.position.addX(speed); 
			this.position.addY(-speed);
		break;
		case 2:
			this.position.addX(-speed); 
			this.position.addY(speed);
		break;
		case 3:
			this.position.addX(-speed); 
			this.position.addY(-speed);
		break; 
		}
		
	}

	private void imageAsteroid(Integer type){
		
		switch(typeImage){
		case 0:
			this.life = 3;
			power = 1;
			
			image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_1_image);
			
			this.sizeWidth = image.getWidth();
			this.sizeHeight = image.getHeight();
			
		break;
		
		case 1:
		case 3:
		case 5:
			this.life =5;
			power = 1;
			
			image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_2_image);
			
			this.sizeWidth = image.getWidth();
			this.sizeHeight = image.getHeight();
		break;
		case 2:
		case 4:
			this.life = 3;
			power = 1;
			image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_3_image);
			
			this.sizeWidth = image.getWidth();
			this.sizeHeight = image.getHeight();	
		
		break;
		case 6:
		case 8:
			
			this.life = 8;
			power = 3;
			image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_5_image);
			
			this.sizeWidth = image.getWidth();
			this.sizeHeight = image.getHeight();
		
		break;
		case 7:
		case 9:
			
			this.life = 10;
			power = 3;
			image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_6_image);
			
			this.sizeWidth = image.getWidth();
			this.sizeHeight = image.getHeight();
		break;
		default: 
		break;	

		}		
	}
	
	private void ramdomImageAteroid() {
		typeImage = (int) (Math.random()*10);
		imageAsteroid(typeImage);
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
	public void draw(Canvas c) {
		
		switch(typeImage){
		case 0:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
					this.position.getX()+this.sizeWidth, 
	    			this.position.getY()+this.sizeHeight);
		break;
		
		case 1:
		case 3:
		case 5:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
					this.position.getX()+this.sizeWidth, 
	    			this.position.getY()+this.sizeHeight);
		break;
		
		case 2:
		case 4:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
					this.position.getX()+this.sizeWidth, 
	    			this.position.getY()+this.sizeHeight);
		break;
		
		case 6:
		case 8:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
					this.position.getX()+this.sizeWidth, 
	    			this.position.getY()+this.sizeHeight);
		break;
		
		case 7:
		case 9:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
		    		this.position.getX()+this.sizeWidth, 
		    			this.position.getY()+this.sizeHeight);
		break;
			
		case 14:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
					this.position.getX()+this.sizeWidth, 
		    			this.position.getY()+this.sizeHeight);
			
		break;
	

		}		
		
	
	    this.drawableImage.draw(c);
		
	}

	public Vector2D getPosition() {
		return position;
	}

	public Integer getSizeWidth() {
		return sizeWidth;
	}

	public Integer getSizeHeight() {
		return sizeHeight;
	}

	public Integer getLife() {
		return life;
	}

	public void setLife(Integer life) {
		this.life = life;
	}
	
	public void addLife(Integer life) {
		this.life += life;
	}

	public Integer getTypeImage() {
		return typeImage;
	}

	public Integer getRoute() {
		return route;
	}

	@Override
	public Double getAngle() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getPower() {
		return power;
	}

	
	
	
	
}
