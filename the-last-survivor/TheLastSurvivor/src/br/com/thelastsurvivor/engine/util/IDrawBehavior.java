package br.com.thelastsurvivor.engine.util;

import br.com.thelastsurvivor.util.Vector2D;

public interface IDrawBehavior extends IInitUpdateDraw{

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
	public Integer getColor();
}
