package br.com.thelastsurvivor.engine.game.weapon;

import br.com.thelastsurvivor.engine.simple.IDrawBehavior;

public interface IWeaponBehavior extends IDrawBehavior{
	
	public Integer getPower();
	public boolean isAlive();
}
