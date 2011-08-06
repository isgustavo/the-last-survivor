package br.com.thelastsurvivor.engine.simpleplayergame;

import android.graphics.Canvas;
import br.com.thelastsurvivor.engine.EngineGame;
import br.com.thelastsurvivor.engine.IDrawControllable;
import br.com.thelastsurvivor.engine.game.Spacecraft;

public class SimplePlayerMode extends EngineGame {
	
	private IDrawControllable spacecraft;
	
	
	public SimplePlayerMode(){
		super();
		init();
	}
	
	
	@Override
	public void init() {
		super.init();
		
		this.spacecraft = new Spacecraft();
		
	}
	
	@Override
	public void update(){
		spacecraft.update();
		
		super.update();
	}
	
	@Override
	public void draw(Canvas c) {
		spacecraft.draw(c);
		
		super.draw(c);
	}


	public IDrawControllable getSpacecraft() {
		return spacecraft;
	}


	public void setSpacecraft(IDrawControllable spacecraft) {
		this.spacecraft = spacecraft;
	}
	
	
	

}
