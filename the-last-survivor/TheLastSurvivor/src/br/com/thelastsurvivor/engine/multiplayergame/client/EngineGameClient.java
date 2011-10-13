package br.com.thelastsurvivor.engine.multiplayergame.client;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.multiplayergame.protocol.ProtocolCommunication;
import br.com.thelastsurvivor.engine.util.IClient;
import br.com.thelastsurvivor.engine.util.IDraw;

public class EngineGameClient implements IClient{

	protected Context context;
	private SurfaceHolder holder;
	protected ProtocolCommunication protocol;
	protected MultiGameActivity activity;
	protected Vibrator vibrator;
	private Display display;
	
	protected Spacecraft spacecraft;
	protected List<IDraw> drawables;
	private Paint blackScreen;
	
	public EngineGameClient(Context context, MultiGameActivity activity, Vibrator vibrator, Display display, String name) {
		this.context = context;
		this.activity = activity;
		this.vibrator = vibrator;
		this.display = display;

		this.spacecraft = new Spacecraft(name);
		
		this.init();
	}
	
	@Override
	public void init() {
		this.protocol = new ProtocolCommunication();
		this.drawables = new ArrayList<IDraw>();
		this.spacecraft.initClient();
		
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
		this.spacecraft.updateSensor();
		
		activity.sendsToServerClientSpacecraft(protocol.protocolSendToServerClientSpacecraft(this.spacecraft));
		
		
	}
	
	@Override
	public void drawGame(String[] message){
		Log.d("DRAW", "GAME CLIENT");
		drawables.clear();
		
		Log.d("DRAW", "drawGame"+message);

		this.drawables = this.protocol.protocolReceiveToServerStatusGame(this.context, message);

		Canvas canvas = null;
		try{
			
			if(holder != null){

				canvas = holder.lockCanvas(null);
				if(canvas != null){
					canvas.drawRect(0,0,canvas.getWidth(), canvas.getHeight(), this.blackScreen);
					for (IDraw object : drawables) {
						 Log.d("DRAW", "object");
						object.draw(canvas);
					}
				}
			//synchronized (canvas) {
			
				
				//engine.draw(canvas);
			}
			//}
		}finally{
			if(canvas != null){
				holder.unlockCanvasAndPost(canvas);
			}
		}
		
		updateClient();
		
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





	

}
