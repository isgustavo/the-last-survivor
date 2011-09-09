package br.com.thelastsurvivor.engine.game.asteroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.IDrawBehavior;
import br.com.thelastsurvivor.engine.util.EOrientation;
import br.com.thelastsurvivor.util.Vector2D;

public class Asteroid implements IDrawBehavior{

	private Context context;
	
	private Drawable drawableImage;
	private Integer typeImage;
	
	private Vector2D position;
	private Integer route;
	private Integer speed = 1;
	private Integer MAX_SPEED = 6;
	
	
	public Asteroid(Context context, Display display){
		this.context = context;
		
		
		init();
	}
	
	@Override
	public void init() {
		
		
		this.position = new Vector2D(200,200);
		
		this.drawableImage = ramdomImageAteroid();
		this.route = (int) (Math.random()*10);
			
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
	public void update(EOrientation orientation) {
		switch (orientation.getOrientation()) {
		case 1:
			
			this.position.addY(MAX_SPEED);
		break;

		case 2:
			
			this.position.addX(-MAX_SPEED);
			this.position.addY(MAX_SPEED);
			
		break;
		
		case 3:
			
			this.position.addX(-MAX_SPEED);
			
		break;	
		
		case 4:
			
			this.position.addX(-MAX_SPEED);
			this.position.addY(-MAX_SPEED);
			
		break;
		
		case 5:
			
			this.position.addY(-MAX_SPEED);
		break;
		
		case 6:
			
			this.position.addX(MAX_SPEED);
			this.position.addY(-MAX_SPEED);
		break;
		
		
		case 7:
			
			this.position.addX(MAX_SPEED);
		break;
		
		
		case 8:
			
			this.position.addX(MAX_SPEED);
			this.position.addY(MAX_SPEED);
		break;
		default:
			break;
		}
		
		this.update();
	}
		

	
	@Override
	public void update() {

		this.ramdomRoute();
		
		//this.position.addX(camera.getX());
		//this.position.addY(camera.getY());
		
	}

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
