package br.com.thelastsurvivor.engine.multiplayergame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.multiplayer.EngineGame;

public class MultiPlayerModeClient extends EngineGame {

	
	private Integer points;
	private Integer life;
	
	
	

	
	public MultiPlayerModeClient(Context context, MultiGameActivity activity, 
			Vibrator vibrator, Display display, Spacecraft spacecraft ) {
		super(context, activity, vibrator, display, spacecraft);
		
		init();
	}

	@Override
	public void init() {
		super.init();
		
		
		this.spacecraft.init();

	}

	@Override
	public void update() {
		
		this.spacecraft.update();
		
		//activity.sendsServerSpacecraft(this.spacecraft.getName()+"/"+this.spacecraft.getPosition().getX()+"/"+
		//		this.spacecraft.getPosition().getX()+"/"+
		//		this.spacecraft.getAngle());
		
		
		
		super.update();
	}

	@Override
	public void draw(Canvas c) {
		Log.d("DRAW", ".CLINT");
		//this.spacecraft.draw(c);
		super.draw(c);
	}
	
}

