package br.com.thelastsurvivor.engine;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import br.com.thelastsurvivor.engine.game.Spacecraft;

public abstract class EngineGame{
	
	
	//private IDrawControllable spacecraft;
	private List<INotDrawControllable> drawableList;
	

	public EngineGame() {
		init();
	}


	public void init() {
		this.drawableList = new ArrayList<INotDrawControllable>();
		
	}

	
	public void update(){
		
	}
	
	public void draw(Canvas c) {
		
		//spacecraft.draw(c);
	}
	
	public List<INotDrawControllable> getDrawableList() {
		return drawableList;
	}


	public void setDrawableList(List<INotDrawControllable> drawableList) {
		this.drawableList = drawableList;
	}

	
	
	
	
}
