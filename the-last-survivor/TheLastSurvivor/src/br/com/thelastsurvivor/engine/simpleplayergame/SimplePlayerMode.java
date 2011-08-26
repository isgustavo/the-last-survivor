package br.com.thelastsurvivor.engine.simpleplayergame;

import android.content.Context;
import android.graphics.Canvas;
import br.com.thelastsurvivor.engine.EngineGame;
import br.com.thelastsurvivor.engine.game.Spacecraft;

public class SimplePlayerMode extends EngineGame {
	
	private Spacecraft spacecraft;
	
	public SimplePlayerMode(Context context){
		super(context);
		
		init();
	}
	
	
	@Override
	public void init() {
		
		this.spacecraft = new Spacecraft(this.getContext());
		
	}
	
	@Override
	public void update(){
		
		
		spacecraft.update();
		
		super.update();
	}
	
	@Override
	public void draw(Canvas c) {
		
		this.spacecraft.draw(c);

		super.draw(c);
	}


	public Spacecraft getSpacecraft() {
		return spacecraft;
	}


}
