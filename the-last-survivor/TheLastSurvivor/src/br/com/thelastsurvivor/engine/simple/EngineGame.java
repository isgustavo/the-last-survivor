package br.com.thelastsurvivor.engine.simple;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.game.weapon.EffectShoot;
import br.com.thelastsurvivor.engine.game.weapon.IWeaponBehavior;
import br.com.thelastsurvivor.engine.simpleplayergame.asteroid.Asteroid;
import br.com.thelastsurvivor.engine.simpleplayergame.message.MessageGame;
import br.com.thelastsurvivor.engine.simpleplayergame.message.MessageLife;
import br.com.thelastsurvivor.engine.simpleplayergame.spacecraft.Spacecraft;
import br.com.thelastsurvivor.util.Vector2D;
import br.com.thelastsurvivor.view.particle.Explosion;

public class EngineGame{

	protected Context context;
	protected Vibrator vibrator;
	private Display display;
	
	private Integer startTime;
	
	protected Spacecraft spacecraft;
	
	protected static Vector2D camera;
	
	protected Explosion explosion;

	protected List<MessageGame> messages;
	protected List<MessageGame> messagesDrawables;
	
	private List<IDrawBehavior> asteroids;
	protected List<IDrawBehavior> asteroidsDrawables;
	
	protected List<IWeaponBehavior> shootsEffect;
	
	public List<IDrawBehavior> updateList;
	private List<IDrawBehavior> drawableList;

	
	
	public EngineGame(Context context, Vibrator vibrator, Display display) {
		this.context = context;
		this.vibrator = vibrator;
		this.display = display;
		
		init();
	}


	public void init(){
		
		this.spacecraft = new Spacecraft(this.getContext(), new Vector2D(200,200));
		
		this.camera = new Vector2D(display.getWidth(), display.getHeight());
		this.startTime = 0;
		
		this.explosion = new Explosion(200);
	
		this.updateList = new ArrayList<IDrawBehavior>();
		this.drawableList = new ArrayList<IDrawBehavior>();
		this.shootsEffect = new ArrayList<IWeaponBehavior>();
		
		this.asteroids = new ArrayList<IDrawBehavior>();
		this.asteroidsDrawables = new ArrayList<IDrawBehavior>();
		
		this.messages = new ArrayList<MessageGame>();
		this.messagesDrawables = new ArrayList<MessageGame>();
		this.addMessage(new MessageGame(context, context.getString(R.string.init_game),3, 1000));

	}
	
	public void update(){
		
		currentTime();
		spacecraft.update();
		verificationNewSpacecraftPositionScreen();
		
		updateNewAsteroid();
		verificationAsteroidCollisions();
		updateAsteroids();
		verificationSpacecraftCollisions();
		verificationCollisionShoot();
		
		for (MessageGame message : this.messages) {
			message.update();
		}
		
		
		
		
		updateEffectShoots();
		
		if (this.explosion != null && this.explosion.isAlive) {
			this.explosion.update(this.getSpacecraft());
		}
		
		
	
	}
	
	private void currentTime(){
		long millis = System.currentTimeMillis() - this.startTime;
	    
		int seconds = (int) (millis / 1000);
	    
		this.startTime = (seconds / 60);
		//Log.d("TIME","."+this.startTime);
	}
	
	
	public void verificationNewSpacecraftPositionScreen(){
		if(-10 > this.spacecraft.getPosition().getY()){
			this.spacecraft.getPosition().setY(this.camera.getY());
		}else if(this.spacecraft.getPosition().getY() > this.camera.getY()+10){
			this.spacecraft.getPosition().setY(0);
		}
	
		if(-10 > this.spacecraft.getPosition().getX()){
			this.spacecraft.getPosition().setX(this.camera.getX());
		}else if(this.spacecraft.getPosition().getX() > this.camera.getX()+10){
			this.spacecraft.getPosition().setX(0);
		}
	}
	
	private void updateNewAsteroid(){
		
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
	
	private void verificationAsteroidCollisions(){

		for(int x= 0; x < this.asteroidsDrawables.size(); x++){
			Asteroid asteroid1 = (Asteroid)this.asteroidsDrawables.get(x);
			
			for(int y = x; y < this.asteroidsDrawables.size(); y++){
				Asteroid asteroid2 = (Asteroid)this.asteroidsDrawables.get(y);
				
				if(this.asteroidsDrawables.get(x) != this.asteroidsDrawables.get(y)){
					
					if(asteroid1.getPosition().getX()+(asteroid1.getSizeWidth()-5) > asteroid2.getPosition().getX() &&
					   asteroid1.getPosition().getX() < asteroid2.getPosition().getX()+(asteroid2.getSizeWidth()-5)&&
					   asteroid1.getPosition().getY()+(asteroid1.getSizeHeight()-5) > asteroid2.getPosition().getY() &&
					   asteroid1.getPosition().getY() < asteroid2.getPosition().getY()+(asteroid2.getSizeHeight()-5)){
					
						asteroidSituation(asteroid1);
						asteroidSituation(asteroid2);

						this.asteroidsDrawables.get(x).setAlive(false);
						this.asteroidsDrawables.get(y).setAlive(false);
					}
				}
				
			}
			
		}

	}
	
	public void verificationCollisionShoot(){
		
		
		for (IDrawBehavior shoot : this.getSpacecraft().getShootsDrawables()) {
			for(IDrawBehavior asteroid : this.asteroidsDrawables){
				if((asteroid.getPosition().getX() < shoot.getPosition().getX() &&
						shoot.getPosition().getX() < asteroid.getPosition().getX()+asteroid.getSizeWidth()) &&
						(asteroid.getPosition().getY() < shoot.getPosition().getY() &&
								shoot.getPosition().getY() < asteroid.getPosition().getY()+asteroid.getSizeHeight())){
					shoot.setAlive(false);
					this.spacecraft.addPoint(asteroid.getPower());
					this.shootsEffect.add(new EffectShoot(this.context, shoot.getPosition()));
					if(this.isAsteroidDestroyed((Asteroid)asteroid,(IWeaponBehavior) shoot)){
						asteroid.setAlive(false);
					}
				}
			}
		}

	}
	
	private void verificationSpacecraftCollisions(){
		
		for(int x= 0; x < this.asteroidsDrawables.size(); x++){
			Asteroid asteroid = (Asteroid)this.asteroidsDrawables.get(x);
					
			if(asteroid.getPosition().getX()+(asteroid.getSizeWidth()-5) > spacecraft.getPosition().getX() &&
			   asteroid.getPosition().getX() < spacecraft.getPosition().getX()+(spacecraft.getSizeWidth()-5)&&
			   asteroid.getPosition().getY()+(asteroid.getSizeHeight()-5) > spacecraft.getPosition().getY() &&
			   asteroid.getPosition().getY() < spacecraft.getPosition().getY()+(spacecraft.getSizeHeight()-5)){
			
				asteroidSituation(asteroid);
				spacecraft.addLife(-asteroid.getLife());
				this.vibrator.vibrate(100);
				String values = context.getString(R.string.life)+" : "+spacecraft.getLife()+" pt";
				this.addMessage(new MessageGame(context, values, 3, 1000, "#FF3300"));
				
				
				this.asteroidsDrawables.get(x).setAlive(false);
			}
		}

	}
	
	private void asteroidSituation(Asteroid asteroid){
		switch (asteroid.getTypeImage()) {
		
		case 1:
		case 12:
			asteroids.add(new Asteroid(this.context, asteroid.getPosition(),1,this.getInvertRoute(asteroid.getRoute())));
		break;
		
		case 3:
		case 6:
		case 9:
			asteroids.add(new Asteroid(this.context, asteroid.getPosition(),2,this.getInvertRoute(asteroid.getRoute())));
		break;
		
		case 4:
		case 7:
		case 10:
			asteroids.add(new Asteroid(this.context, asteroid.getPosition(),2,this.getInvertRoute(asteroid.getRoute())));
		break;
		
		case 5:
		case 8:
		case 11:
			asteroids.add(new Asteroid(this.context, asteroid.getPosition(),4,this.getInvertRoute(asteroid.getRoute())));
			
		default:
		break;
		
		}
	}
	
	private void updateAsteroids(){
		if(this.asteroidsDrawables != null){
			
			this.asteroidsDrawables();
			
			this.asteroidsDrawables.addAll(asteroids);
			
			for (IDrawBehavior asteroid : this.asteroidsDrawables) {
				asteroid.update();
				verificationNewPositionScreen(asteroid);
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
	
	private Integer getInvertRoute(Integer route){
		
		switch (route) {
			case 0:
			return 3;
			case 1:
			return 2;
			case 2:
			return 1;
			case 3:
			return 0;
		}
		
		return null;
	}
	
	private void updateEffectShoots(){
		List<IWeaponBehavior> effects = new ArrayList<IWeaponBehavior>();
		for(IWeaponBehavior shoot : this.shootsEffect){
			if(shoot.isAlive()){
				shoot.update();
				effects.add(shoot);
			}
		}
		
		this.shootsEffect.clear();
		this.shootsEffect.addAll(effects);
	}
	
	public boolean isAsteroidDestroyed(Asteroid asteroid, IWeaponBehavior shoot){
		
		asteroid.addLife(-shoot.getPower());
		
		if(asteroid.getLife() == 0){
			String values = context.getString(R.string.score)+" : "+spacecraft.getPoints()+" pt";
			
			this.addMessage(new MessageGame(context, values, 3, 1000, "#00889C"));
			return true;
		}
		return false;
		
	}
	
	private void addMessage(MessageGame newMessage){
		this.messages.add(newMessage);
		
		List<MessageGame> newListMessage = new ArrayList<MessageGame>();
		
		for (MessageGame message : this.messages) {
			if(message.getPosition() != 1 || !message.isAlive()){
				message.setPosition(message.getPosition()-1);
				newListMessage.add(message);
			}
		}
		
		
		this.messages.clear();
		this.messages.addAll(newListMessage);
	}	
	
	public void verificationNewPositionScreen(IDrawBehavior object){
		if(-40 > object.getPosition().getY()){
			object.getPosition().setY(this.camera.getY());
		}else if(object.getPosition().getY() > this.camera.getY()+40){
			object.getPosition().setY(0);
		}
	
		if(-40 > object.getPosition().getX()){
			object.getPosition().setX(this.camera.getX());
		}else if(object.getPosition().getX() > this.camera.getX()+40){
			object.getPosition().setX(0);
		}
	}
	
	public void draw(Canvas c) {
		
		this.spacecraft.draw(c);
		
		if (explosion != null) {
 			explosion.draw(c);
 		}
		
		for (IDrawBehavior object : this.asteroidsDrawables) {

			object.draw(c);
		}
		
		for (MessageGame message : this.messages) {
			
			message.draw(c);
			
		}
		
		for (IWeaponBehavior effect : this.shootsEffect) {
			effect.draw(c);
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


	public static Double randomSizedimension(Integer min, Integer max) {
		return (min + Math.random() * (max - min + 1));
	}
	
	
}
