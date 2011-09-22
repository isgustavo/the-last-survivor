package br.com.thelastsurvivor.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.game.asteroid.Asteroid;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.game.weapon.IWeaponBehavior;
import br.com.thelastsurvivor.engine.util.MessageGameUtil;
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

	protected Paint paint;
	protected Typeface font; 
	protected List<MessageGameUtil> messages;
	
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
		
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.WHITE);
		this.paint.setTextSize(10);
		
		this.font = Typeface.createFromAsset(getContext().getAssets(),"fonts/FT2FONT.TTF");
		
		this.messages = new ArrayList<MessageGameUtil>();
		this.addMessage(new MessageGameUtil(this.paint, context.getString(R.string.init_game),3, 1000));
		

	}
	
	private void addMessage(MessageGameUtil newMessage){
		
		List<MessageGameUtil> newListMessage = new ArrayList<MessageGameUtil>();
		
		for (MessageGameUtil message : this.messages) {
			if(message.getPosition() != 1 || !message.isAlive()){
				message.setPosition(message.getPosition()-1);
				newListMessage.add(message);
			}
		}
			
		this.messages.clear();
		this.messages.addAll(newListMessage);
		
		this.messages.add(newMessage);
		

	}

	public void update(){
		
		currentTime();
		updateNewAsteroid();
		updateAsteroids();
		updateMessages();
		
		if (this.explosion != null && this.explosion.isAlive) {
			this.explosion.update(this.getSpacecraft());
		}
		
		
	
	}
	
	private void currentTime(){
		long millis = System.currentTimeMillis() - this.startTime;
	    
		int seconds = (int) (millis / 1000);
	    
		this.startTime = seconds / 60;
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
	
	private void updateAsteroids(){
		if(this.asteroidsDrawables != null){
			
			this.asteroidsDrawables();
			
			this.asteroidsDrawables.addAll(asteroids);
			
			for (IDrawBehavior asteroid : asteroidsDrawables) {
				asteroid.update();
				this.verificationNewPositionScreen(asteroid);
			}
			
			this.asteroids.clear();
			
			this.verificationAsteroidCollisions();
		}
		
		
	}
	
	private void asteroidSituation(List<IDrawBehavior> newAsteroid, Asteroid asteroid, Asteroid asteroid2){
		switch (asteroid.getTypeImage()) {
		case 0:
			asteroid.setAlive(false);
		break;
		case 1:
			asteroid.setAlive(false);
		break;
		case 2:
			asteroid.setAlive(false);
		break;
		case 3:
			((Asteroid)asteroid).addLife(-asteroid2.getPower());
			if(asteroid.getLife() == 0){
				asteroid.setAlive(false);
				newAsteroid.add(new Asteroid(this.context, asteroid.getPosition()));
				newAsteroid.add(new Asteroid(this.context, asteroid.getPosition()));
			}
		break;
		case 4:
			((Asteroid)asteroid).addLife(-asteroid2.getPower());
			if(asteroid.getLife() == 0){
				asteroid.setAlive(false);
				newAsteroid.add(new Asteroid(this.context, asteroid.getPosition()));
				newAsteroid.add(new Asteroid(this.context, asteroid.getPosition()));
			}
		break;
		case 5:
			((Asteroid)asteroid).addLife(-asteroid2.getPower());
			if(asteroid.getLife() == 0){
				asteroid.setAlive(false);
				newAsteroid.add(new Asteroid(this.context, asteroid.getPosition()));
				newAsteroid.add(new Asteroid(this.context, asteroid.getPosition()));
			}
		break;

		default:
			break;
		}
	}
	
	private void verificationAsteroidCollisions(){
		
		List<IDrawBehavior> newAsteroid = new ArrayList<IDrawBehavior>();
		
		for (IDrawBehavior asteroid : this.asteroidsDrawables) {
			for(IDrawBehavior asteroid2 : this.asteroidsDrawables){
				if(!asteroid.equals(asteroid2)){
					if((asteroid.getPosition().getX() < asteroid2.getPosition().getX() &&
							asteroid2.getPosition().getX() < asteroid.getPosition().getX()+asteroid.getSizeWidth()) &&
							(asteroid.getPosition().getY() < asteroid2.getPosition().getY() &&
									asteroid2.getPosition().getY() < asteroid.getPosition().getY()+asteroid.getSizeHeight())){
						
						asteroidSituation(newAsteroid, (Asteroid)asteroid, (Asteroid)asteroid2);
						asteroidSituation(newAsteroid, (Asteroid)asteroid2, (Asteroid)asteroid);
						
					
					}
					newAsteroid.add(asteroid);
				}
				
			}
		}
		
		this.asteroidsDrawables.clear();
		this.asteroidsDrawables.addAll(newAsteroid);
	
	}
	
	private void updateMessages(){	
		//List<MessageGameUtil> newListMessage = new ArrayList<MessageGameUtil>();
		
		
		//Integer alpha = color >>> 24;
		for (MessageGameUtil message : this.messages) {
		//alpha -= randomSizedimension(0,4).intValue();								// fade by 5
			
			Integer alpha = message.color >>> 24;
			alpha -= randomSizedimension(0,4).intValue();
			if (alpha <= 0) {	
				message.setAlive(false);
			}else{
				message.color = (message.color & 0x00ffffff) + (alpha << 24);		// set the new alpha
				//paint.setAlpha(alpha);
				message.setAlfa(alpha);
			}
			
		}
		
		//for (MessageGameUtil message : this.messages) {
			
			//Integer alpha = this.color >>> 24;
	    	
			
			//if(message.getAlfa() > 100){
			//	newListMessage.add(message);
			//}
			
		
		//this.messages.clear();
		//this.messages.addAll(newListMessage);
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
			this.addMessage(new MessageGameUtil(this.paint, context.getString(R.string.destroyed_asteroid), 3, 1000));
			return true;
		}
		return false;
		
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
		
		for (IDrawBehavior object : asteroidsDrawables) {

			object.draw(c);
		}
		
		if (explosion != null) {
 			explosion.draw(c);
 		}
		
		for (MessageGameUtil message : this.messages) {
			drawFont(c, message);
		}


	}
	
	void drawFont(Canvas c, MessageGameUtil message){

   	 	message.getPaint().setTypeface(this.font);
   	 	message.
   	 	getPaint().
   	 	setAlpha(
   	 			message.
   	 			getAlfa());
   	 	
   	 	switch (message.getPosition()) {
		case 1:
			c.drawText(message.getText(), 40, 280, message.getPaint());
		break;
		case 2:
			c.drawText(message.getText(), 40, 290, message.getPaint());
		break;
		case 3:
			c.drawText(message.getText(), 40, 300, message.getPaint());
		break;
		
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
