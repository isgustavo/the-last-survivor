package br.com.thelastsurvivor.engine.simpleplayergame.asteroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.simpleplayergame.EngineGame;
import br.com.thelastsurvivor.engine.util.IDraw;
import br.com.thelastsurvivor.engine.util.IDrawBehavior;
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

	
	public Asteroid(Context context, Vector2D position, Integer life,
			Double angle, Integer route, Integer type) {
		
		this.context = context;
		this.position = position;
		this.life = life;
		this.angle = angle.intValue();
		this.route = route;
		this.typeImage = type;
		
		this.isAlive = true;
		
		init();
	}

	@Override
	public void init() {
		
		if(typeImage == null){
			ramdomImageAteroid();
		}else{
			imageAsteroid(typeImage);
		}
		
		if(this.position == null){
			this.position = ramdonOrigin();
		}
		
		if(this.isAlive == null){
			this.isAlive = true;
		}
		
		if(this.route == null){
			this.route = (int) (Math.random()*4);
		}
		
		if(this.angle == null){
			this.angle = (int) (Math.random()*360);
		}
		
		Matrix matrix = new Matrix();
		matrix.setRotate(angle.floatValue());
		
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
		
		
		Integer variant = ((typeImage == 4)||(typeImage == 3)) ? 240 : 85;
		
		
		int origin = (int) (Math.random()*4);
		int position = 0;
		switch (origin) {
		case 0:
			position = (int)(Math.random()*EngineGame.getCamera().getX());
			
			//if(this.route == null){
				//this.route = 2;//(int) (Math.random()*2);
			//}
			
			return new Vector2D(position,-variant);
			
		case 1:
			position = (int)(Math.random()*EngineGame.getCamera().getY());
			
			//if(this.route == null){
				//this.route =  1;//(int) (Math.random()*2);
			//}
			
			return new Vector2D(-variant,position);
			
		case 2:
			position = (int)(Math.random()*EngineGame.getCamera().getX());
			
			//if(this.route == null){
				//this.route =  0;//(int) (Math.random()*2);
			//}
			
			return new Vector2D(position, EngineGame.getCamera().getY()+variant);
			
		case 3:
			position = (int)(Math.random()*EngineGame.getCamera().getY());
			
			//if(this.route == null){
				//this.route =  4;//(int) (Math.random()*2);
			//}
			
			return new Vector2D(EngineGame.getCamera().getX()+variant,position);
		}
		return null;
		
	}

	private void ramdomRoute() {
		switch(route){
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
		
		case 4:
			this.position.addX(+speed); 
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
			this.life =5;
			power = 1;
			
			image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_2_image);
			
			this.sizeWidth = image.getWidth();
			this.sizeHeight = image.getHeight();
		break;
		case 2:
			this.life = 3;
			power = 1;
			image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_3_image);
			
			this.sizeWidth = image.getWidth();
			this.sizeHeight = image.getHeight();	
		
		break;
		case 3:

			this.life = 8;
			power = 3;
			image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_5_image);
			
			this.sizeWidth = image.getWidth();
			this.sizeHeight = image.getHeight();
		
		break;
		case 4:
			
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
		typeImage = (int) (Math.random()*5);
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
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
					this.position.getX()+this.sizeWidth, 
	    			this.position.getY()+this.sizeHeight);
		break;
		
		case 2:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
					this.position.getX()+this.sizeWidth, 
	    			this.position.getY()+this.sizeHeight);
		break;
		
		case 3:

			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
					this.position.getX()+this.sizeWidth, 
	    			this.position.getY()+this.sizeHeight);
		break;
		
		case 4:
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
		return this.angle.doubleValue();
	}

	public Integer getPower() {
		return power;
	}

	@Override
	public boolean equals(Object o) {
		return this.position.getX().equals(((Asteroid)o).getPosition().getX())  &&
				this.position.getY().equals(((Asteroid)o).getPosition().getY());
	}
	
	
	
}
