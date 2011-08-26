package br.com.thelastsurvivor.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;

public abstract class EngineGame{
	
	private List<IDrawBehavior> drawableList;

	protected Context context;
	

	public EngineGame(Context context) {
		this.context = context;
		
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
