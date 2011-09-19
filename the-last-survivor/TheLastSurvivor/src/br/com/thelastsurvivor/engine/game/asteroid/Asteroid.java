package br.com.thelastsurvivor.engine.game.asteroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.EngineGame;
import br.com.thelastsurvivor.engine.IDrawBehavior;
import br.com.thelastsurvivor.engine.Orientation;
import br.com.thelastsurvivor.engine.game.spacecraft.Spacecraft;
import br.com.thelastsurvivor.util.Vector2D;

public class Asteroid implements IDrawBehavior{

	private Context context;
	
	private Drawable drawableImage;
	private Integer typeImage;
	
	private Vector2D position;
	private Integer route;
	private Integer speed = 1;
	private Integer MAX_SPEED = 6;
	
	Spacecraft spacecraft;
	
	
	public Asteroid(Context context, Display display){
		this.context = context;
		
		
		init();
	}
	
	public Asteroid(Context context, Spacecraft spacecraft){
		this.context = context;
		this.spacecraft = spacecraft;
		
		init();
	}
	
	@Override
	public void init() {
		
		
		
		this.position = ramdonOrigin();
		
		this.drawableImage = ramdomImageAteroid();
		this.route = (int) (Math.random()*10);
			
	}
	
	private Vector2D ramdonOrigin(){
		
		
		int origin = (int) (Math.random()*2);
		if(origin == 1){
			int side = (int)(Math.random()*2);
			int position = (int)(Math.random()*EngineGame.getCamera().getY());
			if(side == 1){
				return new Vector2D(-40,position);
			}else{
				return new Vector2D(EngineGame.getCamera().getX(),position);
			}
		}else{
			int side = (int)(Math.random()*2);
			int position = (int)(Math.random()*EngineGame.getCamera().getX());
			if(side == 1){
				return new Vector2D(position,-40);
			}else{
				return new Vector2D(EngineGame.getCamera().getY(),position);
			}
		}
	}

	private void ramdomRoute() {
		switch(route){
		case 0:
			this.position.addX(speed); 
			this.position.addY(-speed);
		break;
		case 1:
			this.position.addX(speed); 
			this.position.addY(speed);
		break;
		case 2:
			this.position.addX(-speed); 
			this.position.addY(speed);
		break;
		case 3:
			this.position.addX(-speed); 
			this.position.addY(-speed);
		break;
		case 4:
			this.position.addX(-speed); 
			this.position.addY(-speed);
		break;
		case 5:
			this.position.addX(-speed); 
			this.position.addY(-speed);
		break;
		case 6:
			this.position.addX(-speed); 
			this.position.addY(-speed);
		break;
		case 7:
			this.position.addX(-speed); 
			this.position.addY(-speed);
		break;
		case 8:
			this.position.addX(-speed); 
			this.position.addY(-speed);
		break;
		case 9:
			this.position.addX(-speed); 
			this.position.addY(-speed);
		break;
		case 10:
			this.position.addX(-speed); 
			this.position.addY(-speed);
		break;
		}
		
	}

	private Drawable ramdomImageAteroid() {
		typeImage = (int) (Math.random()*5);
		switch(typeImage){
		case 0:
			return this.context.getResources().getDrawable(R.drawable.asteroids_1_image); 
		
		case 1:
			return this.context.getResources().getDrawable(R.drawable.asteroids_2_image); 
		
		case 2:
			return this.context.getResources().getDrawable(R.drawable.asteroids_3_image); 
		
		case 3:
			return this.context.getResources().getDrawable(R.drawable.asteroids_4_image); 
		
		case 4:
			return this.context.getResources().getDrawable(R.drawable.asteroids_5_image); 
		
		case 5:
			return this.context.getResources().getDrawable(R.drawable.asteroids_6_image); 
		
		default: 
			return null;

		}		
	}

	@Override
	public boolean isAlive() {
		if(-40 >= position.getX() &&
				position.getX() >= EngineGame.getCamera().getX()+40){
			return false;
		}else if(-40 >= position.getY() &&
				position.getY() >= EngineGame.getCamera().getY()+40){
			return false;
		}
		return true;
	}

	

	@Override
	public void update() {

		this.ramdomRoute();
		
		
		if (spacecraft.getUp()) {
			 if(spacecraft.getAngle() >= 270 || spacecraft.getAngle() <= 90){
				  
				   Double invertAngle;
				   
				   if(spacecraft.getAngle() >= 270){
					   invertAngle = (360 - spacecraft.getAngle()) + 90;
					   
				   }else{
					   invertAngle = spacecraft.getAngle()+180;
				   }
				   
				   Orientation.getNewPosition(invertAngle, this.position); 
				   
			   }else{
				   Orientation.getNewPosition(spacecraft.getAngle(), this.position);
			   }
		}
		} 
		//this.position.addX(camera.getX());
		//this.position.addY(camera.getY());
		
	

	@Override
	public void draw(Canvas c) {
		
		switch(typeImage){
		case 0:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
		    		this.position.getX()+15, 
		    			this.position.getY()+12); 
		break;
		case 1:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
		    		this.position.getX()+26, 
		    			this.position.getY()+23);
		break;
		case 2:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
		    		this.position.getX()+13, 
		    			this.position.getY()+15);
		break;
		case 3:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
		    		this.position.getX()+49, 
		    			this.position.getY()+47);
		break;
		case 4:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
		    		this.position.getX()+62, 
		    			this.position.getY()+56);
		break;
		
		case 5:
			this.drawableImage.setBounds(this.position.getX(), this.position.getY(),  
		    		this.position.getX()+49, 
		    			this.position.getY()+43);
		break;

		}		
		
		
		
		
		//Log.d("orieX",""+this.position.getX());
		//Log.d("orieY",""+this.position.getY());
	    
	    
	    this.drawableImage.draw(c);
		
	}

	public Vector2D getPosition() {
		return position;
	}

	
	
	
	
}
