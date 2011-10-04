package br.com.thelastsurvivor.engine.multiplayergame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.multiplayer.EngineGame;
import br.com.thelastsurvivor.engine.multiplayergame.protocol.ProtocolCommunication;
import br.com.thelastsurvivor.util.Vector2D;

public class MultiPlayerModeServer extends EngineGame {

	
	private Integer points;
	private Integer life;
	
	
	

	
	public MultiPlayerModeServer(Context context, MultiGameActivity activity, Vibrator vibrator, Display display) {
		super(context, activity, vibrator, display);
		
		init();
	}
	
	public MultiPlayerModeServer(Context context, MultiGameActivity activity, Vibrator vibrator, 
			Display display, Spacecraft spacecraft) {
		super(context, activity, vibrator, display);
		
		this.spacecraft = spacecraft;
		
		initClient();
	}

	@Override
	public void init() {
		super.init();
		
		this.spacecraft = new Spacecraft(this.getContext(), new Vector2D(200,200));
		
	
	}
	
	public void initClient(){
		super.init();
		
		this.spacecraft.init();
		
	}
	
	@Override
	public void update() {
		
		this.spacecraft.update();
		super.update();
		
		activity.sendsClientSpacecrafts(protocol.protocolSendsAllSpacecraft(spacecrafts));
		
		
	}

	@Override
	public void draw(Canvas c) {
		
		this.spacecraft.draw(c);
		
		
		super.draw(c);
		
	}
	
}
