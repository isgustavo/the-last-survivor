package br.com.thelastsurvivor.engine.simpleplayergame.powerup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.util.IDrawBehavior;
import br.com.thelastsurvivor.util.Vector2D;

public class PowerUp implements IDrawBehavior{

	public static Integer POWER_UP = 1;
	
	private Context context;
	private Vector2D position;
	
	private Bitmap image;
	
	private Integer sizeWidth;
	private Integer sizeHeight;
	
	private Integer route;
	private Boolean isAlive;
	private Integer speed = 1;
	
	public PowerUp(Context context, Vector2D position){
		this.context = context;
		this.position = position;
		
		init();
	}
	
	
	@Override
	public void init() {
	
		this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.up_image);
		
		this.sizeHeight = image.getHeight();
		this.sizeWidth = image.getWidth();
		
		isAlive = true;
		
		this.route = (int) (Math.random()*4);
		
	}

	@Override
	public void update() {
		this.ramdomRoute();
		
	}

	
	private void ramdomRoute() {
		switch(route){
		case 0:
			this.position.addX(speed); 
			this.position.addY(speed);
		break;
		case 1:
			this.position.addX(speed); 
			this.position.addY(-speed);
		break;
		case 2:
			this.position.addX(-speed); 
			this.position.addY(speed);
		break;
		case 3:
			this.position.addX(-speed); 
			this.position.addY(-speed);
		break; 
		}
		
	}
	
	@Override
	public void draw(Canvas c) {
		c.drawBitmap(Bitmap.createBitmap(this.image),
		        this.position.getX()-(this.sizeWidth/2) , this.position.getY(), null);
		
	}

	@Override
	public boolean isAlive() {
		return this.isAlive;
	}

	@Override
	public Integer getSizeWidth() {
		return this.sizeWidth;
	}

	@Override
	public Integer getSizeHeight() {
		return this.sizeHeight;
	}

	@Override
	public Vector2D getPosition() {
		return position;
	}

	@Override
	public void setAlive(boolean alive) {
		this.isAlive = alive;
	}

	@Override
	public Integer getLife() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getTypeImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getAngle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getRoute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getPower() {
		// TODO Auto-generated method stub
		return null;
	}

}
