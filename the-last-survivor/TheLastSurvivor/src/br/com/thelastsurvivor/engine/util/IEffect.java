package br.com.thelastsurvivor.engine.util;

import br.com.thelastsurvivor.util.Vector2D;

public interface IEffect extends IInitUpdateDraw{
	
	public Integer getStartTime();
	public Integer getTypeEffect();
	
	public void setAlive(Boolean alive);
	public Vector2D getPosition();
	public boolean isAlive();

}
