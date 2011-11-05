package br.com.thelastsurvivor.engine.effect;

import android.content.Context;
import android.view.Display;
import br.com.thelastsurvivor.engine.util.IEffect;
import br.com.thelastsurvivor.util.Vector2D;

public abstract class EffectGameFactory {

	
	public static IEffect newEffect(TypeEffect type,Context context, Vector2D position, Display display){
		switch(type){
		
		case spacecraft:
			return new EffectSpacecraft(context, position, type, display);	
			
		case shoot:
			return new EffectShoot(context, position, type, display);
		
		case asteroid:
			return new EffectAsteroid(context, position, type, display);

		}
		return null;
	}

}
