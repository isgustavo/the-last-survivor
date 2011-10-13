package br.com.thelastsurvivor.engine.multiplayergame.server;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.view.Display;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.game.weapon.EffectShoot;
import br.com.thelastsurvivor.engine.multiplayergame.asteroid.Asteroid;
import br.com.thelastsurvivor.engine.multiplayergame.protocol.ProtocolCommunication;
import br.com.thelastsurvivor.engine.util.IDrawBehavior;
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
	
	protected List<EffectShoot> effects;
	
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
		this.effects = new ArrayList<EffectShoot>();
		
	}
	
	
	@Override
	public void update() {
		if(start != 0L){
			finish = System.currentTimeMillis();	
			currentTime();	
		}
		start = System.currentTimeMillis(); 
		
		this.spacecraft.update();
		
		updateNewAsteroid();
		updateAsteroids();
		this.spacecraftsDrawables.addAll(this.spacecrafts);
		
		for (Spacecraft spacecraft : this.spacecraftsDrawables) {
			spacecraft.updateClient();
		}
		
		
		
		
		activity.sendToClientStatusGame(protocol.protocolSendToClientsStatusGame(spacecraft, spacecraftsDrawables, asteroidsDrawables, messages, effects));
		

	}
	
	private void currentTime(){
		this.startTime += finish - start;
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
		
		
		for (IDrawBehavior object : this.asteroidsDrawables) {

			object.draw(c);
		}
		
		this.spacecraft.draw(c);
		
		for (Spacecraft spacecraft : this.spacecraftsDrawables) {
			spacecraft.draw(c);
		}
		
		this.spacecraftsDrawables.clear();
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