package br.com.thelastsurvivor.view;

import android.graphics.Canvas;

public interface IAnimationBehavior {

	
	public void init();
	public void update();
	public void draw(Canvas c);
	
}
