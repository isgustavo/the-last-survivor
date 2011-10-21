package br.com.thelastsurvivor.engine.simpleplayergame.weapon;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import br.com.thelastsurvivor.engine.simpleplayergame.Orientation;
import br.com.thelastsurvivor.engine.simpleplayergame.powerup.PowerUp;
import br.com.thelastsurvivor.engine.util.IDrawBehavior;
import br.com.thelastsurvivor.util.Vector2D;

public abstract class ShootFactory {
	
	public static List<IDrawBehavior> newShoot(Context context, Vector2D position, Double angle, Bitmap spacecraft){
	
	    List<IDrawBehavior> shoot = new ArrayList<IDrawBehavior>();
	    Vector2D temp = new Vector2D();
	    Vector2D temp2 = new Vector2D();
	    
	    switch(PowerUp.POWER_UP){
	    
	    case 0:
			Log.d("0", "shoot");
			Orientation.getNewPosition(angle, position);
			
			position.addX(spacecraft.getWidth()/2);
	     	position.addY(spacecraft.getHeight()/2);
	     	
	     	shoot.add(new SimpleShoot(context, position, angle, spacecraft));
	     	
			return shoot;
		
		case 1:
			Log.d("1", "shoot");
			
			Orientation.getNewPositionShootRight(angle, position, spacecraft);
			
			//position.addX(position.getX());
	     	//position.addY(position.getY());
	     	
	     	shoot.add(new SimpleShoot(context, position, angle, spacecraft));
			//temp = new Vector2D(position.getX(), position.getY());
			//Vector2D temp2 = new Vector2D();
			//Orientation.getNewPosition(angle, position);
			
			//temp.addX();
			//position.addY(spacecraft.getHeight()/2);
			
			//Orientation.getNewPosition(angle, position);
			
			//shoot.add(new SimpleShoot(context, position, angle, spacecraft));
	     	
			
			//Log.d("1", ".."+position.getX()+".."+position.getY());
			
			//temp.addX(spacecraft.getWidth());
			//temp.addY(spacecraft.getHeight()/2);
		       
			//Orientation.getNewPosition(angle, temp);
			
			
			//shoot.add(new SimpleShoot(context, temp, angle, spacecraft));

			return shoot;
		
		case 2:
			Log.d("1", "shoot");
			temp = new Vector2D(position.getX(), position.getY());
			temp2 = new Vector2D(position.getX(), position.getY());
			//Vector2D temp2 = new Vector2D();
			//Orientation.getNewPosition(angle, position);
			
			//temp.addX();
			position.addY(spacecraft.getHeight()/2);
			
			Orientation.getNewPosition(angle, position);
			
			shoot.add(new SimpleShoot(context, position, angle, spacecraft));
	     	
			
			//Log.d("1", ".."+position.getX()+".."+position.getY());
			
			temp.addX(spacecraft.getWidth());
			temp.addY(spacecraft.getHeight()/2);
		       
			Orientation.getNewPosition(angle, temp);
			
			
			shoot.add(new SimpleShoot(context, temp, angle, spacecraft));
			
			Orientation.getNewPosition(angle, temp2);
			
			temp2.addX(spacecraft.getWidth()/2);
			temp2.addY((spacecraft.getHeight()/2)+10);
	     	
	     	shoot.add(new SimpleShoot(context, temp2, angle, spacecraft));

			return shoot;

			
	    default:
	    	
	    	Log.d("d", "shoot"+PowerUp.POWER_UP);
	    	Orientation.getNewPosition(angle, position);
			
			position.addX(spacecraft.getWidth()/2);
	     	position.addY(spacecraft.getHeight()/2);
	     	
	     	shoot.add(new SimpleShoot(context, position, angle, spacecraft));
	     	
			return shoot;
		}
	
	}
}
