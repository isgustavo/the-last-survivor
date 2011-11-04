package br.com.thelastsurvivor.engine.multiplayergame.client;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Message;
import android.os.Vibrator;
import android.view.Display;
import android.view.SurfaceHolder;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.multiplayergame.protocol.ProtocolCommunication;
import br.com.thelastsurvivor.engine.simpleplayergame.message.MessageGameOver;
import br.com.thelastsurvivor.engine.util.IClient;
import br.com.thelastsurvivor.engine.util.IDraw;
import br.com.thelastsurvivor.view.particle.Explosion;

public class EngineGameClient implements IClient{

	protected Context context;
	private SurfaceHolder holder;
	protected ProtocolCommunication protocol;
	protected MultiGameActivity activity;
	protected Vibrator vibrator;
	private Display display;
	
	protected Integer pointsTeamRed;
	protected Integer pointsTeamBlue;
	protected Integer pointsTeamYellow;
	protected Integer pointsTeamGreen;
	
	protected Explosion explosion;
	
	protected Spacecraft spacecraft;
	protected List<IDraw> drawables;
	private Paint blackScreen;
	
	private MessageGameOver message;
	
	public EngineGameClient(Context context, MultiGameActivity activity, Vibrator vibrator, Display display, String name) {
		this.context = context;
		this.activity = activity;
		this.vibrator = vibrator;
		this.display = display;

		this.spacecraft = new Spacecraft(name, display);
		
		this.init();
	}
	
	@Override
	public void init() {
		this.protocol = new ProtocolCommunication();
		this.drawables = new ArrayList<IDraw>();
		this.spacecraft.initClient();

		this.explosion = new Explosion(200);
		
		blackScreen = new Paint();
		blackScreen.setARGB(255, 0, 0, 0);
		blackScreen.setAntiAlias(true);
		
		updateClient();

	}
	
	@Override
	public void update() {
		//this.spacecraft.updateSensor();
		
		//activity.sendsToServerClientSpacecraft(protocol.protocolSendToServerClientSpacecraft(this.spacecraft));
		
		
	}
	
	public void updateClient() {
		
		if (this.explosion != null && this.explosion.isAlive) {
			this.explosion.update(this.getSpacecraft());
		}
		
		this.spacecraft.updateSensor();
		
		activity.sendsToServerClientSpacecraft(protocol.protocolSendToServerClientSpacecraft(this.spacecraft));
		
		this.spacecraft.setNewShoot(false);
		
	}
	
	@Override
	public void drawGame(String[] values){
		//Log.d("DRAW", "GAME CLIENT");
		drawables.clear();
		
		//Log.d("DRAW", "drawGame"+message);

		this.drawables = this.protocol.protocolReceiveToServerStatusGame(EngineGameClient.this, values);

			Canvas canvas = null;
			try{
				
				if(holder != null){

					canvas = holder.lockCanvas(null);
					if(canvas != null){
						canvas.drawRect(0,0,canvas.getWidth(), canvas.getHeight(), this.blackScreen);
						for (IDraw object : drawables) {
							// Log.d("DRAW", "object");
							object.draw(canvas);
						}
					}
				//synchronized (canvas) {
					if(spacecraft.getIsDead()){
						
						if(message == null){
						
							message = new MessageGameOver(context, activity.getString(R.string.end_game_wait), "#EE1693");
						}
						
						canvas.drawText(message.getText(),20, 100, message.getPaint());
						
					}
					//engine.draw(canvas);
					if (explosion != null) {
			 			explosion.draw(canvas);
			 		}
				}
				//}
			}finally{

				
				
				if(canvas != null){
					holder.unlockCanvasAndPost(canvas);
				}
			}

			
			
			if(!spacecraft.getIsDead()){
				updateClient();
			}
			
		}

	
	

	@Override
	public void draw(Canvas c) {
		////for (IDraw object : drawables) {
		//	 Log.d("DRAW", "object");
		//	object.draw(c);
		//}
		
	}

	
	@Override
	public ProtocolCommunication getProtocol() {
		return this.getProtocol();
	}

	@Override
	public Spacecraft getSpacecraft() {
		return this.spacecraft;
	}

	public SurfaceHolder getHolder() {
		return holder;
	}

	public void setHolder(SurfaceHolder holder) {
		this.holder = holder;
	}

	public Context getContext() {
		return context;
	}

	public Integer getPointsTeamRed() {
		return pointsTeamRed;
	}

	public void setPointsTeamRed(Integer pointsTeamRed) {
		this.pointsTeamRed = pointsTeamRed;
	}

	public Integer getPointsTeamBlue() {
		return pointsTeamBlue;
	}

	public void setPointsTeamBlue(Integer pointsTeamBlue) {
		this.pointsTeamBlue = pointsTeamBlue;
	}

	public Integer getPointsTeamYellow() {
		return pointsTeamYellow;
	}

	public void setPointsTeamYellow(Integer pointsTeamYellow) {
		this.pointsTeamYellow = pointsTeamYellow;
	}

	public Integer getPointsTeamGreen() {
		return pointsTeamGreen;
	}

	public void setPointsTeamGreen(Integer pointsTeamGreen) {
		this.pointsTeamGreen = pointsTeamGreen;
	}

	public Display getDisplay() {
		return display;
	}





	

}


