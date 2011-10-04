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
import br.com.thelastsurvivor.engine.game.asteroid.Asteroid;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.game.weapon.EffectShoot;
import br.com.thelastsurvivor.engine.multiplayergame.protocol.ProtocolCommunication;
import br.com.thelastsurvivor.engine.simple.IDrawBehavior;
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
	
	private Integer startTime;
	
	protected Spacecraft spacecraft;
	protected List<Spacecraft> spacecrafts;
	protected List<Spacecraft> spacecraftsDrawables;
	
	protected List<Asteroid> asteroids;
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
	/*
	
	private List<IDrawBehavior> asteroids;
	protected List<IDrawBehavior> asteroidsDrawables;
	protected List<IWeaponBehavior> shootsEffect;
	
	public List<IDrawBehavior> updateList;
	private List<IDrawBehavior> drawableList;*/
	
	                                   
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
		protocol = new ProtocolCommunication();
		
		this.spacecraftsDrawables = new ArrayList<Spacecraft>();
		this.asteroids = new ArrayList<Asteroid>();
		this.messages = new ArrayList<MessageGameUtil>();
		this.effects = new ArrayList<EffectShoot>();
		
	}
	
	
	@Override
	public void update() {
		
		this.spacecraft.update();
		
		this.spacecraftsDrawables.addAll(this.spacecrafts);
		
		for (Spacecraft spacecraft : this.spacecraftsDrawables) {
			spacecraft.updateClient();
		}
		
		
		
		
		activity.sendToClientStatusGame(protocol.protocolSendToClientsStatusGame(spacecraft, spacecraftsDrawables, asteroids, messages, effects));
		

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

	@Override
	public Spacecraft getSpacecraft() {
		return spacecraft;
	}

	@Override
	public ProtocolCommunication getProtocol() {
		return this.protocol;
	}
}