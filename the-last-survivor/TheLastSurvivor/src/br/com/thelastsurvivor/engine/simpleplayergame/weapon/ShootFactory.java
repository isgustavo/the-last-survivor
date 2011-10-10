package br.com.thelastsurvivor.engine.simpleplayergame.weapon;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import br.com.thelastsurvivor.engine.simple.IDrawBehavior;
import br.com.thelastsurvivor.engine.simple.Orientation;
import br.com.thelastsurvivor.engine.simpleplayergame.powerup.PowerUp;
import br.com.thelastsurvivor.util.Vector2D;

public abstract class ShootFactory {
	
	public static List<IDrawBehavior> newShoot(Context context, Vector2D position, Double angle, Bitmap spacecraft){
	
	    List<IDrawBehavior> shoot = new ArrayList<IDrawBehavior>();
		
	    switch(PowerUp.POWER_UP){
		
	    case 0:
			Log.d("0", "shoot");
			Orientation.getNewPosition(angle, position);
			
			position.addX(spacecraft.getWidth()/2);
	     	position.addY(spacecraft.getHeight()/2);
	     	
	     	shoot.add(new SimpleShoot(context, position, angle));
	     	
			return shoot;
		
		case 1:
			Log.d("1", "shoot");
			Vector2D temp = new Vector2D(position.getX(), position.getY());
			//Vector2D temp2 = new Vector2D();
			Orientation.getNewPosition(angle, position);
			
			//position.addX();
			//position.addY(spacecraft.getHeight()/2);
			shoot.add(new SimpleShoot(context, position, angle));
	     	
			
			Log.d("1", ".."+position.getX()+".."+position.getY());
			Orientation.getNewPosition(angle, temp);
			
			temp.addX(spacecraft.getWidth());
			temp.addY(spacecraft.getHeight()/2);
			
			shoot.add(new SimpleShoot(context, temp, angle));

			return shoot;
		
			
	    default:
	    	
	    	Log.d("d", "shoot"+PowerUp.POWER_UP);
	    	Orientation.getNewPosition(angle, position);
			
			position.addX(spacecraft.getWidth()/2);
	     	position.addY(spacecraft.getHeight()/2);
	     	
	     	shoot.add(new SimpleShoot(context, position, angle));
	     	
			return shoot;
		}
	
	}
}
