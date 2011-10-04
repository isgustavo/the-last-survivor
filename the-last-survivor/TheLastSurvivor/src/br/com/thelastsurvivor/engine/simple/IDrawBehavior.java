package br.com.thelastsurvivor.engine.simple;

import android.graphics.Canvas;
import br.com.thelastsurvivor.util.Vector2D;

public interface IDrawBehavior{

	public void init();
	public void update();
	public void draw(Canvas c);

	public boolean isAlive();
	
	public Integer getSizeWidth();
	public Integer getSizeHeight();
	
	public Vector2D getPosition();
	void setAlive(boolean alive);
	
	public Integer getLife() ;
	public Integer getTypeImage() ;
	public Double getAngle();
	public Integer getRoute() ;
	public Integer getPower();
}
