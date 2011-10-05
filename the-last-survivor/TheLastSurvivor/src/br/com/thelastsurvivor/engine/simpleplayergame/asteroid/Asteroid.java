package br.com.thelastsurvivor.engine.simpleplayergame.asteroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.simple.EngineGame;
import br.com.thelastsurvivor.engine.simple.IDrawBehavior;
import br.com.thelastsurvivor.engine.simpleplayergame.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.util.IDraw;
import br.com.thelastsurvivor.util.Vector2D;

public class Asteroid implements  IDraw, IDrawBehavior{

	private Context context;
	
	private Drawable drawableImage;
	private Integer sizeWidth;
	private Integer sizeHeight;
	
	private Integer life;
	
	private Boolean isAlive;
	private Integer typeImage;
	private Integer power;
	
	private Vector2D position;
	private Integer route;
	private Integer speed = 1;
	//private Integer MAX_SPEED = 6;
	
	Spacecraft spacecraft;
	
	
	public Asteroid(Context context, Display display){
		this.context = context;
		
		
		init();
	}
	
	public Asteroid(Context context, Spacecraft spacecraft){
		this.context = context;
		this.spacecraft = spacecraft;
		
		init();
	}
	
	public Asteroid(Context context, Vector2D origin, Integer type, Integer route){
		this.context = context;
		this.position = origin;
		this.typeImage = type;
		this.route = route;
		
		init2();
	}
	
	public Asteroid(Context context, Vector2D origin, Integer type){
		this.context = context;
		this.position = origin;
		this.typeImage = type;
		
		asteroidClient();
	}
	
	public void asteroidClient(){
		switch(this.typeImage){
		
		case 0:
			this.drawableImage = new BitmapDrawable(BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_1_image));
		break;
		
		case 1:
		case 12:
			this.drawableImage = new BitmapDrawable(BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_2_image));
		break;
		
		case 2:
			this.drawableImage = new BitmapDrawable(BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_3_image));
		break;
		
		case 3:
		case 6:
		case 9:
			this.drawableImage = new BitmapDrawable(BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_4_image));
		break;
		
		case 4:
		case 7:
		case 10:
			this.drawableImage = new BitmapDrawable(BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_5_image));
		break;
		case 5:
		case 8:
		case 11:
			this.drawableImage = new BitmapDrawable(BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_6_image));
		break;
		}
		
	}
	
	@Override
	public void init() {
		
		
		
		this.position = ramdonOrigin();
		this.isAlive = true;
		
		this.drawableImage = ramdomImageAteroid();
		this.route = (int) (Math.random()*4);
			
	}
	
	
	public void init2() {
		
		
		
		//this.position = ramdonOrigin();
		this.isAlive = true;
		
		this.drawableImage = this.imageAsteroid(this.typeImage);
		
		//this.route = (int) (Math.random()*4);
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
		int position = 0;
		switch (origin) {
		case 0:
			position = (int)(Math.random()*EngineGame.getCamera().getX());
			return new Vector2D(position,-40);
		case 1:
			position = (int)(Math.random()*EngineGame.getCamera().getY());
			return new Vector2D(-40,position);
		case 2:
			position = (int)(Math.random()*EngineGame.getCamera().getX());
			return new Vector2D(position,EngineGame.getCamera().getY()+40);
		case 3:
			position = (int)(Math.random()*EngineGame.getCamera().getY());
			return new Vector2D(EngineGame.getCamera().getX()+40,position);
		
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
		}
		
	}

	private Drawable imageAsteroid(Integer type){
		switch(typeImage){
		case 0:
			Bitmap image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_1_image);
			this.sizeHeight = image.getHeight();
			this.sizeWidth = image.getWidth();
			
			this.life = 1;
			power = 1;
			return new BitmapDrawable(image);//this.context.getResources().getDrawable(R.drawable.asteroids_1_image); 
		
		case 1:
		case 12:
			Bitmap image2 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_2_image);
			this.sizeHeight = image2.getHeight();
			this.sizeWidth = image2.getWidth();
			
			this.life = 1;
			power = 1;
			return new BitmapDrawable(image2);
		
		case 2:
			Bitmap image3 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_3_image);
			this.sizeHeight = image3.getHeight();
			this.sizeWidth = image3.getWidth();
			
			this.life = 1;
			power = 1;
			return new BitmapDrawable(image3); 
		
		case 3:
		case 6:
		case 9:
			Bitmap image4 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_4_image);
			this.sizeHeight = image4.getHeight();
			this.sizeWidth = image4.getWidth();
			
			this.life = 2;
			power = 2;
			return new BitmapDrawable(image4); 
		
		case 4:
		case 7:
		case 10:
			Bitmap image5 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_5_image);
			this.sizeHeight = image5.getHeight();
			this.sizeWidth = image5.getWidth();
			
			this.life = 3;
			power = 3;
			return new BitmapDrawable(image5);
		
		case 5:
		case 8:
		case 11:
			Bitmap image6 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.asteroids_6_image);
			this.sizeHeight = image6.getHeight();
			this.sizeWidth = image6.getWidth();
			
			this.life = 3;
			power = 3;
			return new BitmapDrawable(image6);
		
		default: 
			return null;

		}		
	}
	private Drawable ramdomImageAteroid() {
		typeImage = (int) (Math.random()*13);
		return imageAsteroid(typeImage);
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
		    		this.position.getX()+15, 
		    			this.position.getY()+12); 
		break;
		case 1:
		case 12:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
		    		this.position.getX()+26, 
		    			this.position.getY()+23);
		break;
		case 2:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
		    		this.position.getX()+13, 
		    			this.position.getY()+15);
		break;
		case 3:
		case 6:
		case 9:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
		    		this.position.getX()+49, 
		    			this.position.getY()+47);
		break;
		case 4:
		case 7:
		case 10:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
		   		this.position.getX()+62, 
		    			this.position.getY()+56);
		break;
		
		case 5:
		case 8:
		case 11:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
		    		this.position.getX()+49, 
		    			this.position.getY()+43);
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
