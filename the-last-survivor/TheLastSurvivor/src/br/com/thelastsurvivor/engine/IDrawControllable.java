package br.com.thelastsurvivor.engine;

import android.graphics.Canvas;


public interface IDrawControllable{
	
	
	public void init();
	public void update();
	public void draw(Canvas c);
	public void updateOrientation(Float orientationX, Float orientationY);
}