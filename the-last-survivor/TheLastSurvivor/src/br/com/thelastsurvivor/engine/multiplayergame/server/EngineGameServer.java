package br.com.thelastsurvivor.engine.multiplayergame.server;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;
import br.com.thelastsurvivor.engine.effect.EffectGameFactory;
import br.com.thelastsurvivor.engine.effect.EffectShoot;
import br.com.thelastsurvivor.engine.effect.TypeEffect;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.multiplayergame.asteroid.Asteroid;
import br.com.thelastsurvivor.engine.multiplayergame.protocol.ProtocolCommunication;
import br.com.thelastsurvivor.engine.util.IDrawBehavior;
import br.com.thelastsurvivor.engine.util.IEffect;
import br.com.thelastsurvivor.engine.util.IServer;
import br.com.thelastsurvivor.engine.util.MessageGameUtil;
import br.com.thelastsurvivor.util.Vector2D;
import br.com.thelastsurvivor.view.particle.Explosion;

public class EngineGameServer implements IServer{
	
	protected Context context;
	protected ProtocolCommunication protocol;
	protected MultiGameActivity activity;
	protected Vibrator vibrator;
	private Display display;
	
	protected Spacecraft spacecraft;
	protected List<Spacecraft> spacecrafts;
	protected List<Spacecraft> spacecraftsDrawables;
	
	private List<IDrawBehavior> asteroids;
	protected List<IDrawBehavior> asteroidsDrawables;
	
	protected List<MessageGameUtil> messages;
	
	protected List<IEffect> shootsEffect;
	
	protected static Vector2D camera;
	
	protected Explosion explosion;
	
	protected Paint paint;
	protected Typeface font; 
	
	
	//protected HashMap<String, Spacecraft> spacecrafts;
	
	
	
	protected List<IDrawBehavior> components;
	protected List<IDrawBehavior> componentsDrawables;
	
	
	boolean flag = true;
	
	private Long startTime;
	private Long start; 
	private Long finish;
	
	
	                                   
	public EngineGameServer(Context context, MultiGameActivity activity, Vibrator vibrator, 
			Display display, Integer color, List<Spacecraft> spacecrafts) {
		this.context = context;
		this.activity = activity;
		this.vibrator = vibrator;
		this.display = display;
		
		this.spacecrafts = spacecrafts;
		
		Log.d("EngineGameServer","."+color);
		this.spacecraft = new Spacecraft(this.getContext(), new Vector2D(200,200), color);
		
		this.init();
		
	}
	
	@Override
	public void init() {
		this.startTime = 0L;
		this.start = 0L;
		this.finish = 0L;
		
		protocol = new ProtocolCommunication();
		
		this.camera = new Vector2D(display.getWidth(), display.getHeight());
		
		this.spacecraftsDrawables = new ArrayList<Spacecraft>();
		
		this.asteroids = new ArrayList<IDrawBehavior>();
		this.asteroidsDrawables = new ArrayList<IDrawBehavior>();
		
		this.messages = new ArrayList<MessageGameUtil>();
		
		if(this.shootsEffect == null){
			this.shootsEffect = new ArrayList<IEffect>();
		}
		
	}
	
	
	@Override
	public void update() {
		if(start != 0L){
			finish = System.currentTimeMillis();	
			currentTime();	
		}
		start = System.currentTimeMillis(); 
		
		this.spacecraft.update();
		verificationNewSpacecraftPositionScreen();
		verificationCollisionShoot();
		
		updateEffectShoots();
		
		//updateNewAsteroid();
		//updateAsteroids();
		
		
		for (Spacecraft spacecraft : this.spacecrafts) {
			spacecraft.updateClient();
			verificationNewSpacecraftPositionScreen(spacecraft);
			verificationCollisionShoot(spacecraft);
		}
		
		this.spacecraftsDrawables.addAll(this.spacecrafts);
		
		
		activity.sendToClientStatusGame(protocol.protocolSendToClientsStatusGame(spacecraft, spacecraftsDrawables, asteroidsDrawables, messages, shootsEffect));
		

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
	
	public void verificationCollisionShoot(){
		
		//List<IDrawBehavior> collisionAsteroid = new ArrayList<IDrawBehavior>();
		
	
		
		for (IDrawBehavior shoot : this.getSpacecraft().getShootsDrawables()) {
			for(Spacecraft spacecraft : this.spacecrafts){
				
				if(shoot.getPosition().getX()+(shoot.getSizeWidth()-10) > spacecraft.getPosition().getX() &&
							shoot.getPosition().getX() < spacecraft.getPosition().getX()+(spacecraft.getSizeWidth()-10)&&
							shoot.getPosition().getY()+(shoot.getSizeHeight()-10) > spacecraft.getPosition().getY() &&
							shoot.getPosition().getY() < spacecraft.getPosition().getY()+(spacecraft.getSizeHeight()-10)){
					
					shoot.setAlive(false);
					
					this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.shoot, this.context, shoot.getPosition()));
					
					//this.spacecraft.addPoint(asteroid.getPower());
					//Log.d("EFFECT", "FECTURE");
					
					//this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.shoot, this.context, shoot.getPosition()));
					
					//if(this.isAsteroidDestroyed((Asteroid)asteroid,(IWeaponBehavior) shoot)){
					//	activity.getAudio().playSound(3, 0, 1);
					//	if(asteroid.getTypeImage() != 0){
					//		collisionAsteroid.add(new Asteroid(context, 
					//				new Vector2D(asteroid.getPosition().getX(),
					//						     asteroid.getPosition().getY()),
					//						     asteroid.getTypeImage()-1, true));
					//	}else{
					//		asteroid.setAlive(false);
					//	}
						
					//	verificationPowerUp((Asteroid)asteroid);
					//	asteroid.setAlive(false);
					}
				}
			}
		}
		
		//this.asteroidsDrawables.addAll(collisionAsteroid);

	public void verificationCollisionShoot(Spacecraft spacecraftShoot){
		
		//List<IDrawBehavior> collisionAsteroid = new ArrayList<IDrawBehavior>();
		
		List<Spacecraft> spacecrafts = new ArrayList<Spacecraft>(this.spacecrafts);
		spacecrafts.remove(spacecraftShoot);
		spacecrafts.add(this.spacecraft);
		
		
		for (IDrawBehavior shoot : spacecraftShoot.getShootsDrawables()) {
			for(Spacecraft spacecraft : spacecrafts){
				
				if(shoot.getPosition().getX()+(shoot.getSizeWidth()-10) > spacecraft.getPosition().getX() &&
						shoot.getPosition().getX() < spacecraft.getPosition().getX()+(spacecraft.getSizeWidth()-10)&&
						shoot.getPosition().getY()+(shoot.getSizeHeight()-10) > spacecraft.getPosition().getY() &&
						shoot.getPosition().getY() < spacecraft.getPosition().getY()+(spacecraft.getSizeHeight()-10)){
					
					shoot.setAlive(false);
					
					this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.shoot, this.context, shoot.getPosition()));
					
					//this.spacecraft.addPoint(asteroid.getPower());
					//Log.d("EFFECT", "FECTURE");
					
					//this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.shoot, this.context, shoot.getPosition()));
					
					//if(this.isAsteroidDestroyed((Asteroid)asteroid,(IWeaponBehavior) shoot)){
					//	activity.getAudio().playSound(3, 0, 1);
					//	if(asteroid.getTypeImage() != 0){
					//		collisionAsteroid.add(new Asteroid(context, 
					//				new Vector2D(asteroid.getPosition().getX(),
					//						     asteroid.getPosition().getY()),
					//						     asteroid.getTypeImage()-1, true));
					//	}else{
					//		asteroid.setAlive(false);
					//	}
						
					//	verificationPowerUp((Asteroid)asteroid);
					//	asteroid.setAlive(false);
					}
				}
			}
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
	
	public void verificationNewSpacecraftPositionScreen(Spacecraft spacecraft){
		if(-5 > spacecraft.getPosition().getY()){
			spacecraft.getPosition().setY(this.camera.getY());
		}else if(spacecraft.getPosition().getY() > this.camera.getY()+5){
			spacecraft.getPosition().setY(0);
		}
	
		if(-5 > spacecraft.getPosition().getX()){
			spacecraft.getPosition().setX(this.camera.getX());
		}else if(spacecraft.getPosition().getX() > this.camera.getX()+5){
			spacecraft.getPosition().setX(0);
		}
	}
	
	private void currentTime(){
		this.startTime += finish - start;
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
	
	@Override
	public void setSpacecraftClientToUpdate(String[] values){
		
		synchronized(this){
			        
			protocol.protocolResponseClientSpacecraft(values, this.spacecrafts);
			
			
		}
	}
	
	public void setClientDrawSpacecraft(String[] values){
		if(flag){		
			List<Spacecraft> newSpacecrafts = protocol.protocolResponseAllNewSpacecrafts(values);
			for (Spacecraft spacecraft : newSpacecrafts) {
				spacecraft.setContext(context);
				spacecraft.init();
				
			}
			spacecrafts = newSpacecrafts;
			flag = false;
		}
		spacecrafts = protocol.protocolResponseAllSpacecrafts(values, spacecrafts);

	}

	@Override
	public void draw(Canvas c) {
		
		this.spacecraft.draw(c);
		
		for (Spacecraft spacecraft : this.spacecraftsDrawables) {
			spacecraft.draw(c);
		}
		
		for (IEffect effect : this.shootsEffect) {
			effect.draw(c);
		}
		
		this.spacecraftsDrawables.clear();
	}
	
	
	public Context getContext() {
		return context;
	}

	public MultiGameActivity getMultiplayerModeServer(){
		return activity;
	}

	public Display getDisplay() {
		return display;
	}

	
	public static Vector2D getCamera() {
		return camera;
	}

	@Override
	public Spacecraft getSpacecraft() {
		return spacecraft;
	}

	@Override
	public ProtocolCommunication getProtocol() {
		return this.protocol;
	}
}