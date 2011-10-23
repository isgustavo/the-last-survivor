package br.com.thelastsurvivor.engine.simpleplayergame;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.game.simplemode.SimpleGameActivity;
import br.com.thelastsurvivor.engine.effect.EffectGameFactory;
import br.com.thelastsurvivor.engine.effect.TypeEffect;
import br.com.thelastsurvivor.engine.game.weapon.IWeaponBehavior;
import br.com.thelastsurvivor.engine.simpleplayergame.asteroid.Asteroid;
import br.com.thelastsurvivor.engine.simpleplayergame.message.MessageGame;
import br.com.thelastsurvivor.engine.simpleplayergame.powerup.PowerUp;
import br.com.thelastsurvivor.engine.simpleplayergame.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.util.IDrawBehavior;
import br.com.thelastsurvivor.engine.util.IEffect;
import br.com.thelastsurvivor.model.game.Game;
import br.com.thelastsurvivor.provider.trophies.TrophiesProvider;
import br.com.thelastsurvivor.util.DateTimeUtil;
import br.com.thelastsurvivor.util.Vector2D;
import br.com.thelastsurvivor.view.particle.Explosion;

public class EngineGame{

	protected Context context;
	protected SimpleGameActivity activity;
	protected Vibrator vibrator;
	private Display display;
	
	private Long startTime;
	private Long driftTime;
	private Long start; 
	private Long finish;
	protected static Vector2D camera;
	
	protected Spacecraft spacecraft;

	protected List<IEffect> shootsEffect;
	
	private List<IDrawBehavior> asteroids;
	protected List<IDrawBehavior> asteroidsDrawables;
	
	protected List<MessageGame> messages;
	protected List<MessageGame> messagesDrawables;
	
	protected List<IDrawBehavior> powerUps;
	
	protected Explosion explosion;
	
	private Integer colorWhite;
	private List<Integer> listTrophies;

	public EngineGame(SimpleGameActivity activity, Vibrator vibrator, Display display) {
		this.activity = activity;
		this.context = activity;
		this.vibrator = vibrator;
		this.display = display;
		
		init();
		
		this.addMessage(new MessageGame(context, context.getString(R.string.init_game),3, 1000));
		
		
	}
	
	public EngineGame(SimpleGameActivity activity, Vibrator vibrator, Display display, Game game) {
		this.activity = activity;
		this.context = activity;
		this.vibrator = vibrator;
		this.display = display;
		
		this.spacecraft = new Spacecraft(this.getContext(), this.display, game.getSpacecraft());
		
		if(game.getAsteroids().size() != 0){
			this.asteroids = new ArrayList<IDrawBehavior>();
			
			for(br.com.thelastsurvivor.model.game.Asteroid asteroid : game.getAsteroids()){
				this.asteroids.add(new Asteroid(this.context, asteroid.getPosition(), asteroid.getLife(),
						asteroid.getAngle(), asteroid.getRoute(), asteroid.getType()));
			}
		}
		
		if(game.getPowerUps().size() != 0){
			this.powerUps = new ArrayList<IDrawBehavior>();
			
			PowerUp.POWER_UP = game.getPowerUp();
			
			for(br.com.thelastsurvivor.model.game.PowerUp powerUp : game.getPowerUps()){
				this.powerUps.add(new PowerUp(context, powerUp.getPosition(), powerUp.getRoute()));
			}
		}
		
		init();
		
		this.addMessage(new MessageGame(context, context.getString(R.string.restart_game),3, 1000));
		
		
		
	}

	public void init(){
		
		this.startTime = 0L;
		this.driftTime = 0L;
		this.start = 0L;
		this.finish = 0L;
		
		this.camera = new Vector2D(display.getWidth(), display.getHeight());
		
		if(this.spacecraft == null){
			this.spacecraft = new Spacecraft(this.getContext(), this.display); 
		}
		
		if(this.asteroids == null){
			this.asteroids = new ArrayList<IDrawBehavior>();
		}
		this.asteroidsDrawables = new ArrayList<IDrawBehavior>();
		
		this.explosion = new Explosion(200);
	
		if(this.shootsEffect == null){
			this.shootsEffect = new ArrayList<IEffect>();
		}
		
		if(this.powerUps == null){
			this.powerUps = new ArrayList<IDrawBehavior>();

		}

		this.messages = new ArrayList<MessageGame>();
		this.messagesDrawables = new ArrayList<MessageGame>();
		
		this.colorWhite = 0;
		
		this.listTrophies = getListTrophiesNotAchieved();
	}
		
	public void update(){
		if(start != 0L){
			finish = System.currentTimeMillis();	
			currentTime();	
		}
		start = System.currentTimeMillis(); 
		
		if(stillAAlive()){
			
			verificationTrophies();
			
		    spacecraft.update();
			verificationNewSpacecraftPositionScreen();
			
			updateNewAsteroid();
			//verificationAsteroidCollisions();
			updateAsteroids();
			
			verificationSpacecraftCollisions();
			verificationCollisionShoot();
			verificationSpacecraftCollisionsPowerUp();
			
			//updatePowerUps();
			updateEffectShoots();
			
			for (MessageGame message : this.messages) {
				message.update();
			}
	
			if (this.explosion != null && this.explosion.isAlive) {
				this.explosion.update(this.getSpacecraft());
			}
			
		}else{
			activity.getAudioBackgraund().fechar();
			
			activity.getAudio().playSound(3, 0, 1);

		}
	
	}
	
	public void draw(Canvas c) {

		if(!stillAAlive()){
			c.drawRGB(colorWhite++, colorWhite++, colorWhite++);
			if(colorWhite > 200){
				c.drawRGB(0, 0, 0);
				activity.endGame();
				return;
			}
		}
		
		if (explosion != null) {
 			explosion.draw(c);
 		}

		for(IDrawBehavior object : this.powerUps){
			object.draw(c);
		}
		
		for (IDrawBehavior object : this.asteroidsDrawables) {

			object.draw(c);
		}
		
		for (MessageGame message : this.messages) {
			
			message.draw(c);
			
		}
		
		for (IEffect effect : this.shootsEffect) {
			effect.draw(c);
		}

		this.spacecraft.draw(c);

		
	}

	
	private void currentTime(){
		this.startTime += finish - start;
		this.driftTime += finish - start;
	}
	
	
	public boolean stillAAlive(){
		
		if(this.spacecraft.getLife() > 0){
			return true;
		}
		return false;
	}
	
	public void verificationTrophies(){
		
		for (Integer trophies : listTrophies) {
			switch (trophies) {
			case 2:
				if(this.spacecraft.getPoints() > 1000){
					saveTrophieAchieved(2);
					showTrophieAchieved(context.getString(R.string.t02));
				}
			break;
			case 3:
				if(this.spacecraft.getPoints() > 5000){
					saveTrophieAchieved(3);
					showTrophieAchieved(context.getString(R.string.t03));
				}
			break;
			case 4:
				if(PowerUp.POWER_UP == 3){
					saveTrophieAchieved(4);
					showTrophieAchieved(context.getString(R.string.t04));
				}
			break;
			case 5:
				if(getDriftTime() > 2){
					saveTrophieAchieved(5);
					showTrophieAchieved(context.getString(R.string.t06));
				}
			break;
			
			default:
				break;
			}
		}
		
	}
	
	private void showTrophieAchieved(String trophie){
		//sond
		
		String values = context.getString(R.string.achieved) + trophie;
		this.addMessage(new MessageGame(context, values, 3, 1000, "#FF3300"));
	}
	
	
	public void verificationNewSpacecraftPositionScreen(){
		if(-5 > this.spacecraft.getPosition().getY()){
			this.spacecraft.getPosition().setY(this.camera.getY());
		}else if(this.spacecraft.getPosition().getY() > this.camera.getY()+5){
			this.spacecraft.getPosition().setY(0);
		}
	
		if(-5 > this.spacecraft.getPosition().getX()){
			this.spacecraft.getPosition().setX(this.camera.getX());
		}else if(this.spacecraft.getPosition().getX() > this.camera.getX()+5){
			this.spacecraft.getPosition().setX(0);
		}
	}
	
	private void updateNewAsteroid(){
		
		int isAsteroid = 0;
		
		switch ((int)(this.startTime/60000)) {
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
		default:
			isAsteroid = (int) (Math.random()*20);
		}
		
		if(isAsteroid == 1){
			this.asteroids.add(new Asteroid(this.context));
		}
		
	}
	
	private void verificationAsteroidCollisions(){

		List<IDrawBehavior> asteroid = new ArrayList<IDrawBehavior>();
		
		for(int x= 0; x < this.asteroidsDrawables.size(); x++){
			Asteroid asteroid1 = (Asteroid)this.asteroidsDrawables.get(x);
			
			for(int y = x; y < this.asteroidsDrawables.size(); y++){
				Asteroid asteroid2 = (Asteroid)this.asteroidsDrawables.get(y);
				
				if(asteroid1.isAlive() && asteroid2.isAlive() && !asteroid1.equals(asteroid2)){

					if(asteroid1.getPosition().getX()+(asteroid1.getSizeWidth()-5) > asteroid2.getPosition().getX() &&
					   asteroid1.getPosition().getX() < asteroid2.getPosition().getX()+(asteroid2.getSizeWidth()-5)&&
					   asteroid1.getPosition().getY()+(asteroid1.getSizeHeight()-5) > asteroid2.getPosition().getY() &&
					   asteroid1.getPosition().getY() < asteroid2.getPosition().getY()+(asteroid2.getSizeHeight()-5)){

						this.asteroidsDrawables.get(x).setAlive(false);
						this.asteroidsDrawables.get(y).setAlive(false);
						asteroid.addAll(verificationAsteroidDestruction((Asteroid)this.asteroidsDrawables.get(x),(Asteroid)this.asteroidsDrawables.get(y)));
					}
				}
				
			}
			
		}
		
		asteroidsDrawables.addAll(asteroid);
	}
	
	private List<IDrawBehavior> verificationAsteroidDestruction(Asteroid asteroid, Asteroid asteroid2){
		
		
		List<IDrawBehavior> asteroidList = new ArrayList<IDrawBehavior>();
		
		asteroid.addLife(-asteroid2.getPower());
		asteroid2.addLife(-asteroid.getPower());
		
		if(asteroid.getLife() <= 0){
			this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.spacecraft, this.context, 
					new Vector2D(asteroid.getPosition().getX()+asteroid.getSizeWidth()/2,
								 asteroid.getPosition().getY()+asteroid.getSizeHeight()/2)));
			//asteroid.setAlive(false);
		}else{
			this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.spacecraft, this.context, 
					new Vector2D(asteroid.getPosition().getX()+asteroid.getSizeWidth()/2,
								 asteroid.getPosition().getY()+asteroid.getSizeHeight()/2)));
			if(asteroid.getTypeImage() != 0){
				asteroidList.add(new Asteroid(context, 
						new Vector2D(asteroid.getPosition().getX(),
								     asteroid.getPosition().getY()),
								     asteroid.getTypeImage()-1, true));
			}else{
				asteroid.setAlive(false);
			}
			
			//asteroid.setAlive(false);
		}
		
		if(asteroid2.getLife() <= 0){
			
			this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.spacecraft, this.context, 
					new Vector2D(asteroid.getPosition().getX()+asteroid2.getSizeWidth()/2,
								 asteroid.getPosition().getY()+asteroid2.getSizeHeight()/2)));
			//asteroid2.setAlive(false);
		}else{
			this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.spacecraft,this.context, 
					new Vector2D(asteroid.getPosition().getX()+asteroid.getSizeWidth()/2,
								 asteroid.getPosition().getY()+asteroid.getSizeHeight()/2)));
			if(asteroid.getTypeImage() != 0){
				asteroidList.add(new Asteroid(context, 
						new Vector2D(asteroid.getPosition().getX(),
								     asteroid.getPosition().getY()),
								     asteroid.getTypeImage()-1, true));
			}else{
				asteroid.setAlive(false);
			}
			//asteroid.setAlive(false);
			
		}
		
		return asteroidList;
	}
	
	public void verificationCollisionShoot(){
		
		List<IDrawBehavior> collisionAsteroid = new ArrayList<IDrawBehavior>();
		
		for (IDrawBehavior shoot : this.getSpacecraft().getShootsDrawables()) {
			for(IDrawBehavior asteroid : this.asteroidsDrawables){
				if((asteroid.getPosition().getX() < shoot.getPosition().getX() &&
						shoot.getPosition().getX() < asteroid.getPosition().getX()+asteroid.getSizeWidth()) &&
						(asteroid.getPosition().getY() < shoot.getPosition().getY() &&
								shoot.getPosition().getY() < asteroid.getPosition().getY()+asteroid.getSizeHeight())){
					shoot.setAlive(false);
					this.spacecraft.addPoint(asteroid.getPower());
					Log.d("EFFECT", "FECTURE");
					this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.shoot, this.context, shoot.getPosition()));
					
					if(this.isAsteroidDestroyed((Asteroid)asteroid,(IWeaponBehavior) shoot)){
						activity.getAudio().playSound(3, 0, 1);
						if(asteroid.getTypeImage() != 0){
							collisionAsteroid.add(new Asteroid(context, 
									new Vector2D(asteroid.getPosition().getX(),
											     asteroid.getPosition().getY()),
											     asteroid.getTypeImage()-1, true));
						}else{
							asteroid.setAlive(false);
						}
						
						verificationPowerUp((Asteroid)asteroid);
						asteroid.setAlive(false);
					}
				}
			}
		}
		
		this.asteroidsDrawables.addAll(collisionAsteroid);

	}
	
	private void verificationSpacecraftCollisions(){
		
		for(int x= 0; x < this.asteroidsDrawables.size(); x++){
			Asteroid asteroid = (Asteroid)this.asteroidsDrawables.get(x);
					
			if(asteroid.getPosition().getX()+(asteroid.getSizeWidth()-10) > spacecraft.getPosition().getX() &&
			   asteroid.getPosition().getX() < spacecraft.getPosition().getX()+(spacecraft.getWidth()-10)&&
			   asteroid.getPosition().getY()+(asteroid.getSizeHeight()-10) > spacecraft.getPosition().getY() &&
			   asteroid.getPosition().getY() < spacecraft.getPosition().getY()+(spacecraft.getHeight()-10)){
			
				//asteroidSituation(asteroid);
				spacecraft.addLife(-asteroid.getLife());
				this.vibrator.vibrate(100);
				String values = context.getString(R.string.life)+" "+spacecraft.getLife()+" pt";
				this.addMessage(new MessageGame(context, values, 3, 1000, "#FF3300"));
				
				//PowerUp.POWER_UP -= 1;
				
				createEffectCollision(asteroid);
				this.asteroidsDrawables.get(x).setAlive(false);
				
				this.driftTime = 0L;
			}
		}

	}

	public void createEffectCollision(Asteroid asteroid, Asteroid asteroid2){
		Integer x = 0; 
		Integer y = 0;
		
		//Log.d("EFFECT", "COLLISION");
		//Log.d("SPACECRAFT", "X"+asteroid.getPosition().getX()+"Y"+asteroid.getPosition().getY());
		//Log.d("ASTEROID", "X"+asteroid2.getPosition().getX()+"Y"+asteroid2.getPosition().getY());
		
		for (int i = asteroid.getPosition().getX(); 
				i < asteroid.getPosition().getX()+asteroid.getSizeWidth(); i++) {
			if(i == asteroid2.getPosition().getX()){
				x = i;
				//Log.d("EFFECT", "X");
				break;
			}else if(i == asteroid2.getPosition().getX()+asteroid2.getSizeWidth()){
				x = i;
				break;
			}
		}
		
		for (int i = asteroid.getPosition().getY(); 
				i < asteroid.getPosition().getY()+asteroid.getSizeHeight(); i++) {
			if(i == asteroid2.getPosition().getY()){
				y = i;
				//Log.d("EFFECT", "Y");
				break;
			}else if(i == asteroid2.getPosition().getY()+asteroid2.getSizeWidth()){
				y = i;
				break;
			}
		}
		
		//Log.d("EFFECT", "X"+x+"Y"+y);
		
		this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.asteroid, this.context, new Vector2D(x,y)));

	}
	public void createEffectCollision(Asteroid asteroid){
		
		Integer x = 0; 
		Integer y = 0;
		
		//Log.d("EFFECT", "COLLISION");
		//Log.d("SPACECRAFT", "X"+spacecraft.getPosition().getX()+"Y"+spacecraft.getPosition().getY());
		//Log.d("ASTEROID", "X"+asteroid.getPosition().getX()+"Y"+asteroid.getPosition().getY());
		
		for (int i = spacecraft.getPosition().getX(); 
				i < spacecraft.getPosition().getX()+spacecraft.getWidth(); i++) {
			if(i == asteroid.getPosition().getX()){
				x = i;
				//Log.d("EFFECT", "X");
				break;
			}else if(i == asteroid.getPosition().getX()+asteroid.getSizeWidth()){
				x = i;
				break;
			}
		}
		
		for (int i = spacecraft.getPosition().getY(); 
				i < spacecraft.getPosition().getY()+spacecraft.getHeight(); i++) {
			if(i == asteroid.getPosition().getY()){
				y = i;
				//Log.d("EFFECT", "Y");
				break;
			}else if(i == asteroid.getPosition().getY()+asteroid.getSizeWidth()){
				y = i;
				break;
			}
		}
		
		//Log.d("EFFECT", "X"+x+"Y"+y);
		
		this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.spacecraft ,this.context, new Vector2D(x,spacecraft.getPosition().getY())));
		
	}
	
	private void verificationSpacecraftCollisionsPowerUp(){
		
		for(int x= 0; x < this.powerUps.size(); x++){
			PowerUp powerUp = (PowerUp)this.powerUps.get(x);
					
			if(powerUp.getPosition().getX()+(powerUp.getSizeWidth()-5) > spacecraft.getPosition().getX() &&
				powerUp.getPosition().getX() < spacecraft.getPosition().getX()+(spacecraft.getWidth()-5)&&
				powerUp.getPosition().getY()+(powerUp.getSizeHeight()-5) > spacecraft.getPosition().getY() &&
				powerUp.getPosition().getY() < spacecraft.getPosition().getY()+(spacecraft.getHeight()-5)){
			
				PowerUp.POWER_UP += 1;
				//asteroidSituation(asteroid);
				//spacecraft.addLife(-asteroid.getLife());
				this.vibrator.vibrate(100);
				String values = context.getString(R.string.power_up);
				this.addMessage(new MessageGame(context, values, 3, 1000, "#FFFF00"));
				
				
				
				this.powerUps.get(x).setAlive(false);
			}
		}

	}
	
	
	public void updatePowerUps(){
		
		List<IDrawBehavior> powers = new ArrayList<IDrawBehavior>();
		
		for (IDrawBehavior power : powerUps) {
			if(power.isAlive() && verificationPositionOnScreen(power)){
				power.update();
				
				powers.add(power);
			}
		}
		
		powerUps.clear();
		powerUps.addAll(powers);
		
		
		
	}
	
	public void verificationPowerUp(Asteroid asteroid){
		
		int up = 0;
		
		
		up = 1;//(int) (Math.random()*2);	
		
		if(up == 1){
			this.powerUps.add(new PowerUp(context, asteroid.getPosition()));
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
			//Log.d("SIZEupdateAsteroids", "."+this.asteroidsDrawables.size());
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
		//Log.d("SIZEasteroidsDrawables", "."+this.asteroidsDrawables.size());
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
		List<IEffect> effects = new ArrayList<IEffect>();
		for(IEffect shoot : this.shootsEffect){
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
			
			this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.asteroid, this.context, new Vector2D(asteroid.getPosition().getX(),asteroid.getPosition().getY())));
			this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.asteroid, this.context, new Vector2D(asteroid.getPosition().getX()+asteroid.getSizeHeight()/2,asteroid.getPosition().getY())));
			this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.asteroid, this.context, new Vector2D(asteroid.getPosition().getX()+asteroid.getSizeWidth()/2,asteroid.getPosition().getY())));
			
			String values = context.getString(R.string.score)+" "+spacecraft.getPoints()+" pt";
			
			this.addMessage(new MessageGame(context, values, 3, 1000, "#00889C"));
			return true;
		}
		return false;
		
	}
	
	private void addMessage(MessageGame newMessage){
		
		
		
		List<MessageGame> newListMessage = new ArrayList<MessageGame>();
		
		for (MessageGame message : this.messages) {
			if(message.getPosition() != 1 || !message.isAlive()){
				message.setPosition(message.getPosition()-1);
				newListMessage.add(message);
			}
		}
		
		
		this.messages.clear();
		this.messages.add(newMessage);
		this.messages.addAll(newListMessage);
	}	
	
	public void verificationNewPositionScreen(IDrawBehavior object){
		
		int tolerance = 20;
		
		if(object instanceof Asteroid){
			tolerance = ((Asteroid)object).getTypeImage() < 8 ? 20 : 200;
		}
		
		if(-tolerance > object.getPosition().getY()){
			object.getPosition().setY(this.camera.getY()+tolerance);
		}else if(object.getPosition().getY() > this.camera.getY()+tolerance){
			object.getPosition().setY(0);
		}
	
		if(-tolerance > object.getPosition().getX()+tolerance){
			object.getPosition().setX(this.camera.getX());
		}else if(object.getPosition().getX() > this.camera.getX()+tolerance){
			object.getPosition().setX(0);
		}
	}
	
	
	public boolean verificationPositionOnScreen(IDrawBehavior object){
		if(-40 > object.getPosition().getY()){
			return false;
		}else if(object.getPosition().getY() > this.camera.getY()+40){
			return false;
		}
	
		if(-40 > object.getPosition().getX()){
			return false;
		}else if(object.getPosition().getX() > this.camera.getX()+40){
			return false;
		}
		
		return true;
	}
	
	private List<Integer> getListTrophiesNotAchieved(){
		
		List<Integer> listTrophies = new ArrayList<Integer>();
		
		Cursor c = activity.getContentResolver().query(TrophiesProvider.CONTENT_URI, 
				null, TrophiesProvider.DATE_ACHIEVED +" IS NULL " , null, null);
		
		while(c.moveToNext()){
			listTrophies.add(c.getInt(0));
		}
		
		return listTrophies;
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
	
	private void saveTrophieAchieved(Integer id){
		
		ContentValues values = new ContentValues();

		values.put(TrophiesProvider.DATE_ACHIEVED, DateTimeUtil.DateToString(new Date()));	
		
		activity.getContentResolver().update(TrophiesProvider.CONTENT_URI, values, TrophiesProvider.ID +" = "+ id, null);
			
		
	}

	
	public Display getDisplay() {
		return display;
	}
	
	public List<IDrawBehavior> getAsteroidsDrawables() {
		return asteroidsDrawables;
	}


	public List<IDrawBehavior> getPowerUps() {
		return powerUps;
	}

	public List<IEffect> getShootsEffect() {
		return shootsEffect;
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

	public static Double randomSizedimension(Integer min, Integer max) {
		return (min + Math.random() * (max - min + 1));
	}
	
	public Long getTimeGame(){
		return this.startTime/60000;
	}
	
	public Long getDriftTime(){
		return this.driftTime/60000;
	}
	
	public Long getRealTimeGame(){
		return this.startTime;
	}
	
	
	
}
