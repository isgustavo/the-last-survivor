package br.com.thelastsurvivor.engine;

import java.util.ArrayList;
import java.util.List;

import br.com.thelastsurvivor.util.Vector2D;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;

public abstract class EngineGame{

	protected Context context;
	protected Vibrator vibrator;
	private Display display;
	
	private static int SCREEN_SIZE_HEIGHT_UP;
	private static int SCREEN_SIZE_WIDTH_RIGHT;
	private static int SCREEN_SIZE_HEIGHT_DOWN;
	private static int SCREEN_SIZE_WIDTH_LEFT;
	
	protected Vector2D camera;
	
	private List<IDrawBehavior> drawableList;

	

	public EngineGame(Context context, Vibrator vibrator, Display display) {
		this.context = context;
		this.vibrator = vibrator;
		this.display = display;
		
		Log.d("iEngineGamet","iEngineGame");
		this.camera = new Vector2D(0, 0);
		
		
		Log.d("cameraY","CAMERAY");
		
		SCREEN_SIZE_HEIGHT_UP = ( display.getHeight() - ((display.getHeight()*98)/100));
		SCREEN_SIZE_WIDTH_RIGHT = (( display.getWidth()*88)/100);
		SCREEN_SIZE_HEIGHT_DOWN = (( display.getHeight()*83)/100);
		SCREEN_SIZE_WIDTH_LEFT = ( display.getWidth() - (( display.getWidth()*98)/100));
		
		this.drawableList = new ArrayList<IDrawBehavior>();
		
		init();
	}


	public void init() {
		Log.d("initEngineGame","initEngineGame");
		
		this.camera = new Vector2D(0, 0);
		
		
		Log.d("cameraY","CAMERAY");
		
		SCREEN_SIZE_HEIGHT_UP = ( display.getHeight() - ((display.getHeight()*98)/100));
		SCREEN_SIZE_WIDTH_RIGHT = (( display.getWidth()*88)/100);
		SCREEN_SIZE_HEIGHT_DOWN = (( display.getHeight()*83)/100);
		SCREEN_SIZE_WIDTH_LEFT = ( display.getWidth() - (( display.getWidth()*98)/100));
		
		this.drawableList = new ArrayList<IDrawBehavior>();
		
		
	}

	
	public void update(){
		
	}
	
	public void draw(Canvas c) {
		
		//spacecraft.draw(c);
	}

	public Display getDisplay() {
		return display;
	}

	

	public Vector2D getCamera() {
		return camera;
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
	
	
	
	
}
