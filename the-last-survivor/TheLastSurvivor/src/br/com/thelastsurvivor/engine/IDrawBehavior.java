package br.com.thelastsurvivor.engine;

import android.graphics.Canvas;

public interface IDrawBehavior{

	
	public void init();
	public void update();
	public void draw(Canvas c);

}
