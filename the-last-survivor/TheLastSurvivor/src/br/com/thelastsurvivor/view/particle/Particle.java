package br.com.thelastsurvivor.view.particle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import br.com.thelastsurvivor.engine.util.EOrientation;
import br.com.thelastsurvivor.util.Vector2D;

public class Particle {

	
	public Boolean isAlive;
	
	public static final int MAX_DIMENSION = 5;	
	public static final int MAX_SPEED = 6;	
	
	protected Vector2D position;
	
	protected Double widht;		
	protected Double height;		
	
	protected Integer color;			
	protected Paint paint;	
	
	public Particle(){
		
		
		
		this.init();
	}
	
	public void init(){
		
		this.isAlive = true;
		
		this.position = new Vector2D(randomSizedimension(0,800).intValue(),
				randomSizedimension(0,800).intValue());
		
		this.widht = randomSizedimension(1, MAX_DIMENSION);
		this.height = this.widht;
		
		this.color = Color.argb(255, 255,255,
						randomSizedimension(255,200).intValue());
		this.paint = new Paint(this.color);
		
	}
	
	public void update(EOrientation orientation){
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
	
	public void update() {
		if (this.isAlive) {
			//this.position.addX(1);
			//this.position.addY(randomSizedimension(50,200).intValue());
			
			// extract alpha 
			Integer alpha = this.color >>> 24;

			alpha -= randomSizedimension(0,4).intValue();								// fade by 5
			if (alpha <= 0) {						// if reached transparency kill the particle
				this.isAlive = false;
			} else {
				this.color = (this.color & 0x00ffffff) + (alpha << 24);		// set the new alpha
				this.paint.setAlpha(alpha);
				//this.age++;						// increase the age of the particle
//				this.widht *= 1.05;
//				this.height *= 1.05;
			}
			//if (this.age >= this.lifetime) {	// reached the end if its life
			//	this.state = STATE_DEAD;
			//}
			
			// http://lab.polygonal.de/2007/05/10/bitwise-gems-fast-integer-math/
			//32bit
//			var color:uint = 0xff336699;
//			var a:uint = color >>> 24;
//			var r:uint = color >>> 16 & 0xFF;
//			var g:uint = color >>>  8 & 0xFF;
//			var b:uint = color & 0xFF;
			
		}
	}
	
	
	public void draw(Canvas canvas) {
//		paint.setARGB(255, 128, 255, 50);
		paint.setColor(this.color);
		canvas.drawRect((float) this.position.getX(), (float) this.position.getY(), 
				(float) (this.position.getX() + this.widht),(float) (this.position.getY() + this.height), paint);
		
	}
	

	
	public static Double randomSizedimension(Integer min, Integer max) {
		return (min + Math.random() * (max - min + 1));
	}

	public Boolean isAlive() {
		return isAlive;
	}
	
	
	
}
