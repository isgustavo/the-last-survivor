package br.com.thelastsurvivor.engine;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface IDrawControllable {
	
	public void init();
	public void update();
	public void updateOrientation(float orientationX, float orientationY);
	public void draw(Canvas c);
	
	public Paint getcPaint() ;

	public void setcPaint(Paint cPaint);
	
/*	public Integer getPosX();
	public void setPosX(Integer posX);

	public Integer getPosY();
	public void setPosY(Integer posY);*/
}
