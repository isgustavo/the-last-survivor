package br.com.thelastsurvivor.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import br.com.thelastsurvivor.engine.game.asteroid.Asteroid;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.game.weapon.IWeaponBehavior;
import br.com.thelastsurvivor.util.Vector2D;
import br.com.thelastsurvivor.view.particle.Explosion;

public abstract class EngineGame{

	protected Context context;
	protected Vibrator vibrator;
	private Display display;
	
	private Integer startTime;
	
	protected Spacecraft spacecraft;
	
	protected static Vector2D camera;
	
	protected Explosion explosion;
	
	
	private List<IDrawBehavior> asteroids;
	protected List<IDrawBehavior> asteroidsDrawables;
	
	public List<IDrawBehavior> updateList;
	private List<IDrawBehavior> drawableList;

	public EngineGame(Context context, Vibrator vibrator, Display display) {
		this.context = context;
		this.vibrator = vibrator;
		this.display = display;
		
		this.init();
	}


	public void init(){
		
		this.camera = new Vector2D(display.getWidth(), display.getHeight());
		this.startTime = 0;
		
		this.explosion = new Explosion(200);
	
		this.updateList = new ArrayList<IDrawBehavior>();
		this.drawableList = new ArrayList<IDrawBehavior>();
		
		this.asteroids = new ArrayList<IDrawBehavior>();
		this.asteroidsDrawables = new ArrayList<IDrawBehavior>();

	}
	
	

	public void update(){
		
		currentTime();
		controlNewAsteroid();
		controlAsteroids();
		
		
		if (this.explosion != null && this.explosion.isAlive) {
			this.explosion.update(this.getSpacecraft());
		}
	
	}
	
	private void currentTime(){
		long millis = System.currentTimeMillis() - this.startTime;
	    
		int seconds = (int) (millis / 1000);
	    
		this.startTime = seconds / 60;
	}
	
	private void controlNewAsteroid(){
		
		int isAsteroid = 0;
		
		switch (this.startTime) {
		case 0:
			isAsteroid = (int) (Math.random()*100);			
		break;
		case 1:
			isAsteroid = (int) (Math.random()*80);			
		break;
		case 2:
			isAsteroid = (int) (Math.random()*60);			
		break;
		case 3:
			isAsteroid = (int) (Math.random()*40);			
		break;
		case 4:
			isAsteroid = (int) (Math.random()*20);			
		break;
		default:
			isAsteroid = (int) (Math.random()*20);
		}
		
		if(isAsteroid == 1){
			this.asteroids.add(new Asteroid(this.context, this.getSpacecraft()));
		}
		
	}
	
	private void controlAsteroids(){
		if(this.asteroidsDrawables != null){
			
			this.asteroidsDrawables();
			
			this.asteroidsDrawables.addAll(asteroids);
			
			for (IDrawBehavior asteroid : asteroidsDrawables) {
				asteroid.update();
			}
			
			this.asteroids.clear();
		}
	}
	
	private void asteroidsDrawables(){
		List<IDrawBehavior> asteroids = new ArrayList<IDrawBehavior>();
		for(IDrawBehavior asteroid : this.asteroidsDrawables){
			if(asteroid.isAlive()){
				asteroids.add(asteroid);
			}
		}
		
		this.asteroidsDrawables.clear();
		this.asteroidsDrawables.addAll(asteroids);
		
	}
	
	public boolean isAsteroidDestroyed(Asteroid asteroid, IWeaponBehavior shoot){
		
		asteroid.addLife(-shoot.getPower());
		
		if(asteroid.getLife() == 0){
			return true;
		}
		return false;
		
	}
	
	public void draw(Canvas c) {

		//Log.d("NUMERO", "."+asteroidsDrawables.size());
		for (IDrawBehavior object : asteroidsDrawables) {

			object.draw(c);
		}
		
		if (explosion != null) {
 			explosion.draw(c);
 		}

	}

	
	protected boolean isDrawable(Vector2D position){
		
		if(-40 < position.getX() &&
				position.getX() < this.camera.getX()+40){
			return true;
		}else if(-40 < position.getY() &&
				position.getY() < this.camera.getY()+40){
			return true;
		}
		
		return false;
	}
	
	public Display getDisplay() {
		return display;
	}


	public List<IDrawBehavior> getDrawableList() {
		return drawableList;
	}


	public void setDrawableList(List<IDrawBehavior> drawableList) {
		this.drawableList = drawableList;
	}

	

	public List<IDrawBehavior> getAsteroidsDrawables() {
		return asteroidsDrawables;
	}


	public Context getContext() {
		return context;
	}
	
	public static Vector2D getCamera() {
		return camera;
	}


	public Spacecraft getSpacecraft() {
		return spacecraft;
	}


	public Integer getStartTime() {
		return startTime;
	}


	
	
	
}
