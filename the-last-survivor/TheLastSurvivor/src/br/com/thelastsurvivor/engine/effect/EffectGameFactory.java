package br.com.thelastsurvivor.engine.effect;

import android.content.Context;
import br.com.thelastsurvivor.engine.util.IEffect;
import br.com.thelastsurvivor.util.Vector2D;

public abstract class EffectGameFactory {

	
	public static IEffect newEffect(TypeEffect type,Context context, Vector2D position){
		switch(type){
		
		case spacecraft:
			return new EffectSpacecraft(context, position, type);	
			
		case shoot:
			return new EffectShoot(context, position, type);
		
		case asteroid:
			return new EffectAsteroid(context, position, type);

		}
		return null;
	}

}
