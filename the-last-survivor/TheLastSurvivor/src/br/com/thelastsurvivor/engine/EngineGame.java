package br.com.thelastsurvivor.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;

public abstract class EngineGame{
	
	private List<IDrawBehavior> drawableList;

	protected Context context;
	protected Vibrator vibrator;

	public EngineGame(Context context, Vibrator vibrator) {
		this.context = context;
		this.vibrator = vibrator;
		
		
		init();
	}


	public void init() {
		
		this.drawableList = new ArrayList<IDrawBehavior>();

	}

	
	public void update(){
		
	}
	
	public void draw(Canvas c) {
		
		//spacecraft.draw(c);
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
	
}
