package br.com.thelastsurvivor.engine.simpleplayergame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.util.Log;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.EngineGame;
import br.com.thelastsurvivor.engine.game.Spacecraft;
import br.com.thelastsurvivor.util.Vector2D;

public class SimplePlayerMode extends EngineGame {
	
	private Vector2D camera;
	
	private Drawable image;
	
	private Spacecraft spacecraft;
	
	
	public SimplePlayerMode(Context context, Vibrator vibrator) {
		super(context, vibrator);
		
		init();
	}


	@Override
	public void init() {
		
		this.camera = new Vector2D(0,0);
		
		image = this.context.getResources().getDrawable(R.drawable.spacecraft_image);
		
		this.spacecraft = new Spacecraft(this.getContext());
		
	}
	
	@Override
	public void update(){
		
		switch (spacecraft.getOrientation().getOrientation()) {
		case 1:
			
			if(spacecraft.getPosition().getY() < 60){
				if(camera.getY() < 250){
					camera.addY(+6);
				}else if(camera.getY() > 250 && camera.getY() < 300){
					
					//this.vibrator.vibrate(1);
					
					camera.addY(+1);
				}
			}
			
		break;
		
		
		case 5:
			
			if(spacecraft.getPosition().getY() > 360){
				if(camera.getY() > -250){
					camera.addY(-6);
				}else if(camera.getY() < -250 && camera.getY() > -300){
					
					//this.vibrator.vibrate(1);
					
					camera.addY(-1);
				}
			}
			
		break;
	}
/*	
	case 2:
		this.position.setX(this.position.getX()+43);
		this.position.setY(this.position.getY());
	break;

	case 3:
		this.position.setX(this.position.getX()+40);
		this.position.setY(this.position.getY()+20);
	break;

	case 4:
		this.position.setX(this.position.getX()+40);
		this.position.setY(this.position.getY()+40);
	break;
		
	case 5:
		this.position.setX(this.position.getX()+19);
		this.position.setY(this.position.getY()+40);
	break;
		
	case 6:
		this.position.setX(this.position.getX());
		this.position.setY(this.position.getY()+40);
	break;
		
	case 7:
		this.position.setX(this.position.getX()-19);
		this.position.setY(this.position.getY()+20);
	break;
		
	default:
	break;
	}
		
		
		*/

		spacecraft.update();
		
		super.update();
	}
	
	@Override
	public void draw(Canvas c) {
		
		this.spacecraft.draw(c);
	    
	    c.translate(camera.getX(), camera.getY());
	    
	    
	    Log.d("CAMERA", ""+camera.getX());
	    Log.d("CAMERA", ""+camera.getY());
	    
	    this.image.setBounds(40, -270, 40+44, -270+48);
	
	    this.image.draw(c);
	    
	    
	    
	    Log.d("direcao", ""+camera.getY());
		super.draw(c);
		
		
	
	}


	public Spacecraft getSpacecraft() {
		return spacecraft;
	}


}
