package br.com.thelastsurvivor.engine;

import android.graphics.Canvas;
import br.com.thelastsurvivor.util.Vector2D;

public interface IDrawBehavior{

	public void init();
	public void update();
	public void draw(Canvas c);

	public boolean isAlive();
	public Vector2D getPosition();
	
}
