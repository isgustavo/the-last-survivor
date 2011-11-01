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
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;
import br.com.thelastsurvivor.engine.effect.EffectGameFactory;
import br.com.thelastsurvivor.engine.effect.EffectShoot;
import br.com.thelastsurvivor.engine.effect.TypeEffect;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.engine.multiplayergame.asteroid.Asteroid;
import br.com.thelastsurvivor.engine.multiplayergame.protocol.ProtocolCommunication;
import br.com.thelastsurvivor.engine.simpleplayergame.message.MessageGame;
import br.com.thelastsurvivor.engine.simpleplayergame.message.MessageGameOver;
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
	
	protected static final Integer POINTS = 10;
	
	protected Integer pointsTeamRed;
	protected Integer pointsTeamBlue;
	protected Integer pointsTeamYellow;
	protected Integer pointsTeamGreen;
	
	protected Spacecraft spacecraft;
	protected List<Spacecraft> spacecrafts;
	protected List<Spacecraft> spacecraftsDrawables;
	protected List<Spacecraft> spacecraftsDead;
	
	protected List<IEffect> shootsEffect;
	
	protected static Vector2D camera;
	
	protected Explosion explosion;
	
	protected Paint paint;
	protected Typeface font; 
	
	protected MessageGameOver message;
	protected List<MessageGame> messages;
	protected List<MessageGame> messagesDrawables;
	
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
		
		this.explosion = new Explosion(200);
		
		this.pointsTeamRed = 0;
		this.pointsTeamBlue = 0;
		this.pointsTeamYellow = 0;
		this.pointsTeamGreen = 0;
		
		protocol = new ProtocolCommunication();
		
		this.camera = new Vector2D(display.getWidth(), display.getHeight());
		
		this.spacecraftsDrawables = new ArrayList<Spacecraft>();
		this.spacecraftsDead = new ArrayList<Spacecraft>();
		
		this.messages = new ArrayList<MessageGame>();
		this.messagesDrawables = new ArrayList<MessageGame>();
		
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
		
		if(!this.spacecraft.getIsDead()){
			this.spacecraft.update();
			verificationNewSpacecraftPositionScreen();
			verificationCollisionShoot();
		}
		
		
		updateEffectShoots();
		
		for (MessageGame message : this.messages) {
			message.update();
		}

		for (Spacecraft spacecraft : this.spacecrafts) {
			spacecraft.updateClient();
			verificationNewSpacecraftPositionScreen(spacecraft);
			verificationCollisionShoot(spacecraft);
		}
		
		if (this.explosion != null && this.explosion.isAlive) {
			this.explosion.update(this.getSpacecraft());
		}
		
		
		this.spacecraftsDrawables.addAll(this.spacecrafts);

		activity.sendToClientStatusGame(protocol.protocolSendToClientsStatusGame(
				spacecraft.getIsDead() ? null : spacecraft, 
				spacecraftsDrawables, messages, shootsEffect));
		
		
		
		List<Spacecraft> spacecraftALive = new ArrayList<Spacecraft>();
		for(Spacecraft spacecraft : spacecrafts){
			if(spacecraft.getLife() < 0){
				
				spacecraftsDead.add(spacecraft);
				this.addMessage(new MessageGame(context, spacecraft.getName()+" IS DEAD!", 3, 1000, "#FF0000"));
				activity.setToClientDead(spacecraft.getName());
			
			}else{
				spacecraftALive.add(spacecraft);
			}
		}
		
		spacecrafts.clear();
		spacecrafts.addAll(spacecraftALive);
		
		if(this.spacecraft.getLife() < 0){
			this.addMessage(new MessageGame(context, this.spacecraft.getName()+" IS DEAD!", 3, 1000, "#FF0000"));
			this.spacecraft.setIsDead(true);
		}
		
		
		if(spacecrafts.size() == 0){
			
			//activity.setEndGame(protocol.protocolSentToClientsEndGame(pointsTeamRed,
			//		pointsTeamBlue, pointsTeamYellow, pointsTeamGreen, spacecraft, spacecrafts));
			
		}else{
			if(spacecrafts.size() == 1 && this.spacecraft.getIsDead()){
				
			//	activity.setEndGame(protocol.protocolSentToClientsEndGame(pointsTeamRed,
			//			pointsTeamBlue, pointsTeamYellow, pointsTeamGreen, spacecraft, spacecrafts));
				
				
			}
		}
		

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
	
		for (IDrawBehavior shoot : this.getSpacecraft().getShootsDrawables()) {
			for(Spacecraft spacecraft : this.spacecrafts){
				
				if(shoot.getPosition().getX()+(shoot.getSizeWidth()-10) > spacecraft.getPosition().getX() &&
							shoot.getPosition().getX() < spacecraft.getPosition().getX()+(spacecraft.getSizeWidth()-10)&&
							shoot.getPosition().getY()+(shoot.getSizeHeight()-10) > spacecraft.getPosition().getY() &&
							shoot.getPosition().getY() < spacecraft.getPosition().getY()+(spacecraft.getSizeHeight()-10)){
					
					
					if(this.spacecraft.getColor() != spacecraft.getColor()){
						getTeamPoint(this.spacecraft);
						spacecraft.addLife(-10);
					
					}

					shoot.setAlive(false);
					
					this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.shoot, this.context, shoot.getPosition()));
					
					break;
					}
				}
			}
		}
		
		//this.asteroidsDrawables.addAll(collisionAsteroid);

	
	

	
	public void verificationCollisionShoot(Spacecraft spacecraftShoot){
		
		
		for (IDrawBehavior shoot : spacecraftShoot.getShootsDrawables()) {
			for(Spacecraft spacecraft : this.spacecrafts){
				
				if(!spacecraftShoot.equals(spacecraft)){
					if(shoot.getPosition().getX()+(shoot.getSizeWidth()-10) > spacecraft.getPosition().getX() &&
							shoot.getPosition().getX() < spacecraft.getPosition().getX()+(spacecraft.getSizeWidth()-10)&&
							shoot.getPosition().getY()+(shoot.getSizeHeight()-10) > spacecraft.getPosition().getY() &&
							shoot.getPosition().getY() < spacecraft.getPosition().getY()+(spacecraft.getSizeHeight()-10)){
						
					shoot.setAlive(false);
					
					if(spacecraftShoot.getColor() != spacecraft.getColor()){
						
						getTeamPoint(spacecraftShoot);
						
						spacecraft.addLife(-10);

					}
					
					
					this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.shoot, this.context, shoot.getPosition()));
					break;
					}
				}
			}
			
			
			if(!this.spacecraft.getIsDead()){
				if(shoot.getPosition().getX()+(shoot.getSizeWidth()-10) > this.spacecraft.getPosition().getX() &&
						shoot.getPosition().getX() < this.spacecraft.getPosition().getX()+(this.spacecraft.getSizeWidth()-10)&&
						shoot.getPosition().getY()+(shoot.getSizeHeight()-10) > this.spacecraft.getPosition().getY() &&
						shoot.getPosition().getY() < this.spacecraft.getPosition().getY()+(this.spacecraft.getSizeHeight()-10)){
					
					
					shoot.setAlive(false);
				
					if(spacecraftShoot.getColor() != this.spacecraft.getColor()){
					
						getTeamPoint(spacecraftShoot);
					
						this.spacecraft.addLife(-10);
						//this.addMessage(new MessageGame(context, values, 3, 1000, "#FF0000"));
						
					}
				
				
					this.shootsEffect.add(EffectGameFactory.newEffect(TypeEffect.shoot, this.context, shoot.getPosition()));

				
					this.vibrator.vibrate(100);
				}
			
			}
			
		}
	}
	
	public void getTeamPoint(Spacecraft spacecraft){
		
		String values;
		
		switch (spacecraft.getColor()) {
		case 1:
			addPointsTeamRed(POINTS);
			values = context.getString(R.string.point_team_game)+" "+pointsTeamRed+" pt";
			this.addMessage(new MessageGame(context, values, 3, 1000, "#FF0000"));
		break;
		
		case 2:
			addPointsTeamBlue(POINTS);
			values = context.getString(R.string.point_team_game)+" "+pointsTeamBlue+" pt";
			this.addMessage(new MessageGame(context, values, 3, 1000, "#000066"));
		break;
			
		case 3:
			addPointsTeamYellow(POINTS);
			values = context.getString(R.string.point_team_game)+" "+pointsTeamYellow+" pt";
			this.addMessage(new MessageGame(context, values, 3, 1000, "#FFFF00"));
		break;
			
		case 4:
			addPointsTeamGreen(POINTS);
			values = context.getString(R.string.point_team_game)+" "+pointsTeamGreen+" pt";
			this.addMessage(new MessageGame(context, values, 3, 1000, "#006633"));
		break;

		default:
			break;
		}
		
		
		
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
		
		Log.d("NEWSIZE","."+newListMessage.size());
		
		this.messages.add(newMessage);
		this.messages.addAll(newListMessage);
	}	
	
	public void verificationNewSpacecraftPositionScreen(){
		if(-5 > this.spacecraft.getPosition().getY()){
			this.spacecraft.getPosition().setY(MultiGameActivity.DISPLAY_HEIGHT);
		}else if(this.spacecraft.getPosition().getY() > MultiGameActivity.DISPLAY_HEIGHT+5){
			this.spacecraft.getPosition().setY(0);
		}
	
		if(-5 > this.spacecraft.getPosition().getX()){
			this.spacecraft.getPosition().setX(MultiGameActivity.DISPLAY_WIDHT);
		}else if(this.spacecraft.getPosition().getX() > MultiGameActivity.DISPLAY_WIDHT+5){
			this.spacecraft.getPosition().setX(0);
		}
	}
	
	public void verificationNewSpacecraftPositionScreen(Spacecraft spacecraft){
		if(-5 > spacecraft.getPosition().getY()){
			spacecraft.getPosition().setY(MultiGameActivity.DISPLAY_HEIGHT);
		}else if(spacecraft.getPosition().getY() > MultiGameActivity.DISPLAY_HEIGHT+5){
			spacecraft.getPosition().setY(0);
		}
	
		if(-5 > spacecraft.getPosition().getX()){
			spacecraft.getPosition().setX(MultiGameActivity.DISPLAY_WIDHT);
		}else if(spacecraft.getPosition().getX() > MultiGameActivity.DISPLAY_WIDHT+5){
			spacecraft.getPosition().setX(0);
		}
	}
	
	private void currentTime(){
		this.startTime += finish - start;
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
		
		if(!this.spacecraft.getIsDead()){
			this.spacecraft.draw(c);
		}else{
			if(message == null){
				
				message = new MessageGameOver(context, "YOU DEAD!", "#006633");
			}
			
			c.drawText(message.getText(), 150, 100, message.getPaint());
		}
		
		for (Spacecraft spacecraft : this.spacecraftsDrawables) {
			spacecraft.draw(c);
		}
		
		for (IEffect effect : this.shootsEffect) {
			effect.draw(c);
		}
		
		for (MessageGame message : this.messages) {
			
			message.draw(c);
			
		}
		
		if (explosion != null) {
 			explosion.draw(c);
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

	public void addPointsTeamRed(Integer pointsTeamRed) {
		this.pointsTeamRed += pointsTeamRed;
	}

	public void addPointsTeamBlue(Integer pointsTeamBlue) {
		this.pointsTeamBlue += pointsTeamBlue;
	}

	public void addPointsTeamYellow(Integer pointsTeamYellow) {
		this.pointsTeamYellow += pointsTeamYellow;
	}

	public void addPointsTeamGreen(Integer pointsTeamGreen) {
		this.pointsTeamGreen += pointsTeamGreen;
	}

	
	
}