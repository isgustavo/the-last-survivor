package br.com.thelastsurvivor.engine.multiplayer;

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
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.multiplayergame.protocol.ProtocolCommunication;
import br.com.thelastsurvivor.engine.simple.IDrawBehavior;
import br.com.thelastsurvivor.engine.util.IServer;
import br.com.thelastsurvivor.engine.util.MessageGameUtil;
import br.com.thelastsurvivor.util.Vector2D;
import br.com.thelastsurvivor.view.particle.Explosion;

public class EngineGame implements IServer{
	
	protected Context context;
	protected ProtocolCommunication protocol;
	protected MultiGameActivity activity;
	protected Vibrator vibrator;
	private Display display;
	
	private Integer startTime;
	
	protected Spacecraft spacecraft;
	
	protected static Vector2D camera;
	
	protected Explosion explosion;
	
	protected Paint paint;
	protected Typeface font; 
	protected List<MessageGameUtil> messages;
	
	//protected HashMap<String, Spacecraft> spacecrafts;
	
	protected List<Spacecraft> spacecrafts;
	
	protected List<IDrawBehavior> components;
	protected List<IDrawBehavior> componentsDrawables;
	
	
	boolean flag = true;
	/*
	
	private List<IDrawBehavior> asteroids;
	protected List<IDrawBehavior> asteroidsDrawables;
	protected List<IWeaponBehavior> shootsEffect;
	
	public List<IDrawBehavior> updateList;
	private List<IDrawBehavior> drawableList;*/
	
	                                   
	public EngineGame(Context context, MultiGameActivity activity, Vibrator vibrator, Display display) {
		this.context = context;
		this.activity = activity;
		this.vibrator = vibrator;
		this.display = display;
		//this.spacecrafts = new HashMap<String, Spacecraft>();
		this.spacecrafts = new ArrayList<Spacecraft>();
		this.init();
	}
	
	public EngineGame(Context context, MultiGameActivity activity, Vibrator vibrator, Display display, 
			 Spacecraft spacecraft) {
		this.context = context;
		this.activity = activity;
		this.vibrator = vibrator;
		this.display = display;
		//this.spacecrafts = new HashMap<String, Spacecraft>();
		this.spacecrafts = new ArrayList<Spacecraft>();
		this.spacecraft = spacecraft;
		
		this.init();
	}
	
	public String initClient(String name){
		
		switch (spacecrafts.size()) {
		case 0:
			Log.d("ENGINE_INIT_CLITN", "."+name);
			spacecrafts.add(new Spacecraft(context, new Vector2D(200, 50), new Double(180.0),name));
			return name+"/200/50/180";
		case 1:
			Log.d("ENGINE_INIT_CLITN", "."+name);
			spacecrafts.add(new Spacecraft(context, new Vector2D(350, 100), new Double(270.0),name));
			return name+"/350/100/270";
		case 2:
			Log.d("ENGINE_INIT_CLITN", "."+name);
			spacecrafts.add(new Spacecraft(context, new Vector2D(50, 100), new Double(90.0),name));
			return name+"/50/100/90";
		}
		return null;
		
		

	}

	@Override
	public void init() {
		//this.spacecrafts = new HashMap<String, Spacecraft>();
		protocol = new ProtocolCommunication();
	}

	@Override
	public void update() {
		
		
		for (Spacecraft spacecraft : spacecrafts) {
			spacecraft.update();
		}
		
		

	}
	
	public synchronized void setServerUpdateSpacecraft(String[] values){
		for (Spacecraft spacecraft : this.spacecrafts) {
			if(spacecraft.getName().equals(values[1])){
				spacecraft.setPosition(new Vector2D(Integer.parseInt(values[2]), Integer.parseInt(values[3])));
				spacecraft.setAngle(Double.parseDouble(values[4]));
			}
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
		for (Spacecraft spacecraft : spacecrafts) {
			spacecraft.draw(c);
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

	public Spacecraft getSpacecraft() {
		return spacecraft;
	}

	@Override
	public ProtocolCommunication getProtocol() {
		return protocol;
	}

	@Override
	public void setSpacecraftClientToUpdate(String[] values) {
		// TODO Auto-generated method stub
		
	}


}
