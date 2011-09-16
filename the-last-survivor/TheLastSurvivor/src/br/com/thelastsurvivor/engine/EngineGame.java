package br.com.thelastsurvivor.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import br.com.thelastsurvivor.engine.game.asteroid.Asteroid;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.util.Vector2D;
import br.com.thelastsurvivor.view.particle.Explosion;

public abstract class EngineGame{

	protected Context context;
	protected Vibrator vibrator;
	private Display display;
	
	protected Spacecraft spacecraft;
	
	protected CameraGame camera;
	protected Explosion explosion;
	
	private static int SCREEN_SIZE_HEIGHT = 800;
	private static int SCREEN_SIZE_WIDTH = 800;
			
	private static int SCREEN_SIZE_HEIGHT_UP;
	private static int SCREEN_SIZE_WIDTH_RIGHT;
	private static int SCREEN_SIZE_HEIGHT_DOWN;
	private static int SCREEN_SIZE_WIDTH_LEFT;
	
	public List<IDrawBehavior> updateList;
	private List<IDrawBehavior> drawableList;

	public EngineGame(Context context, Vibrator vibrator, Display display) {
		this.context = context;
		this.vibrator = vibrator;
		this.display = display;
		
		this.init();
	}


	public void init(){
		
		this.camera = new CameraGame(200,display.getWidth(), 200, display.getHeight());
		this.explosion = new Explosion(200);
		
	/*	SCREEN_SIZE_HEIGHT_UP = ( display.getHeight() - ((display.getHeight()*95)/100));
		SCREEN_SIZE_WIDTH_RIGHT = (( display.getWidth()*90)/100);
		SCREEN_SIZE_HEIGHT_DOWN = (( display.getHeight()*95)/100);
		SCREEN_SIZE_WIDTH_LEFT = ( display.getWidth() - (( display.getWidth()*99)/100));
	 */	
		this.updateList = new ArrayList<IDrawBehavior>();
		this.drawableList = new ArrayList<IDrawBehavior>();

	}
	
	

	public void update(){
		
		if (this.explosion != null && this.explosion.isAlive) {
			this.explosion.update(this.getSpacecraft());
		}
	
		Vector2D newPositionCamera = new Vector2D(0,0);
		
		if(this.spacecraft.getUp()){
			
			Orientation.getNewPosition(this.spacecraft.getAngle(), newPositionCamera);
			
			
		}else if(this.spacecraft.getDown()){
			Orientation.getNewPosition(this.spacecraft.getAngle(), newPositionCamera);
		}
		
		
		this.spacecraft.scoll(newPositionCamera);
		
		this.drawableList.clear();
		
		for (IDrawBehavior object : this.updateList) {
			
			object.update();
			
			if(this.isDrawable(object.getPosition())){
				this.drawableList.add(object);
			}
			
		}
		
		if(this.isDrawable(this.spacecraft.getPosition())){
			this.drawableList.add(this.spacecraft);
		}
		
		
	}
	
	public void draw(Canvas c) {

		for (IDrawBehavior object : drawableList) {

			object.draw(c);
		}
		
		if (explosion != null) {
 			explosion.draw(c);
 		}

	}

	
	protected boolean isDrawable(Vector2D position){
		
		
		
		if(this.camera.getRelativeBeginningSizeWidth() < position.getX() &&
				position.getX() < this.camera.getRelativeFinalSizeWidth()){
			return true;
		}else if(this.camera.getRelativeBeginningSizeHeight() < position.getY() &&
				position.getY() < this.camera.getRelativeFinalSizeHeight()){
			return true;
		}
		
		return false;
	}
	
	public Display getDisplay() {
		return display;
	}


	public List<IDrawBehavior> getDrawableList() {
		return drawableList;
	}


	public void setDrawableList(List<IDrawBehavior> drawableList) {
		this.drawableList = drawableList;
	}


	public Context getContext() {
		return context;
	}
	
	public static int getSCREEN_SIZE_HEIGHT_UP() {
		return SCREEN_SIZE_HEIGHT_UP;
	}

	public static int getSCREEN_SIZE_WIDTH_RIGHT() {
		return SCREEN_SIZE_WIDTH_RIGHT;
	}

	public static int getSCREEN_SIZE_HEIGHT_DOWN() {
		return SCREEN_SIZE_HEIGHT_DOWN;
	}

	public static int getSCREEN_SIZE_WIDTH_LEFT() {
		return SCREEN_SIZE_WIDTH_LEFT;
	}


	public static int getSCREEN_SIZE_HEIGHT() {
		return SCREEN_SIZE_HEIGHT;
	}


	public static void setSCREEN_SIZE_HEIGHT(int sCREEN_SIZE_HEIGHT) {
		SCREEN_SIZE_HEIGHT = sCREEN_SIZE_HEIGHT;
	}


	public static int getSCREEN_SIZE_WIDTH() {
		return SCREEN_SIZE_WIDTH;
	}


	public static void setSCREEN_SIZE_WIDTH(int sCREEN_SIZE_WIDTH) {
		SCREEN_SIZE_WIDTH = sCREEN_SIZE_WIDTH;
	}


	public Spacecraft getSpacecraft() {
		return spacecraft;
	}


	public CameraGame getCamera() {
		return camera;
	}
	
	
	
	
}
