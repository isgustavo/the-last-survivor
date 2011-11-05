package br.com.thelastsurvivor.engine.effect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.activity.game.multiplayermode.MultiGameActivity;
import br.com.thelastsurvivor.engine.util.IEffect;
import br.com.thelastsurvivor.util.Vector2D;

public class EffectAsteroid implements IEffect {

	private Context context;
	private Vector2D positionShoot;
	private TypeEffect type;
	
	private Bitmap image;
	
	private static Bitmap image1;
	private static Bitmap image2;
	private static Bitmap image3;
	private static Bitmap image4;
	private static Bitmap image5;
	private static Bitmap image6;
	private static Bitmap image7;
	private static Bitmap image8;
	
	private Bitmap resizedBitmap;
	private Drawable drawableImage;
	private Integer sizeWidth;
	private Integer sizeHeight;

	private Display display;
	
	private Matrix matrix;
	private Boolean isAlive;
	
	private Paint paint;
	private Integer alpha;
	public Integer color = Color.argb(255, 255, 255, 255);
	
	private Integer startTime;
	
	protected EffectAsteroid(Context context, Vector2D position, TypeEffect type, Display display){
		this.context = context;
		this.positionShoot = position;
		this.type = type;
		this.alpha = 0;
		
		this.display = display;
		
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.WHITE);

		
		if(image1 == null)
			this.image1 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_1_image);
		
		if(image2 == null)
			this.image2 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_2_image);
		
		if(image3 == null)
			this.image3 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_3_image);
		
		if(image4 == null)
			this.image4 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_4_image);
		
		if(image5 == null)
			this.image5 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_9_image);
		
		if(image6 == null)
			this.image6 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_11_image);
		
		if(image7 == null)
			this.image7 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_10_image);
		
		if(image8 == null)
			this.image8 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_8_image);
		
		this.startTime = 0;
		
		
		isAlive = true;
		
		this.matrix = new Matrix();
		
		
	}
	
	public EffectAsteroid(Context context, Vector2D position, Integer alfa){
		this.context = context;
		this.positionShoot = position;
		this.alpha = alfa;

		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.WHITE);
		
		this.sizeHeight = image.getHeight();
		this.sizeWidth = image.getWidth();
		
		isAlive = true;
		
		this.matrix = new Matrix();
		
		this.paint.setAlpha(this.alpha);
	}
	
	
	
	@Override
	public void init() {
		
		
	}
	
	private void currentTime(){
		long millis = System.currentTimeMillis() - this.startTime;
	    
		startTime = (int) (millis / 1000);
	    
		//this.startTime = (seconds / 60);
		//Log.d("TIME","."+this.startTime);
	}

	Integer x = 0;
	@Override
	public void update() {
		x++;
		if(x < 2){
			this.drawableImage = new BitmapDrawable(this.image5);
			this.sizeHeight = image5.getHeight();
			this.sizeWidth = image5.getWidth();
		}else if(x < 3){
			this.drawableImage = new BitmapDrawable(this.image6);
			this.sizeHeight = image6.getHeight();
			this.sizeWidth = image6.getWidth();
		}else if(x < 4){
			this.drawableImage = new BitmapDrawable(this.image7);
			this.sizeHeight = image7.getHeight();
			this.sizeWidth = image7.getWidth();
		}else if(x == 5){
			this.drawableImage = new BitmapDrawable(this.image8);
			this.sizeHeight = image8.getHeight();
			this.sizeWidth = image8.getWidth();
		}else if(x == 6){
			this.drawableImage = new BitmapDrawable(this.image4);
			this.sizeHeight = image4.getHeight();
			this.sizeWidth = image4.getWidth();
		}else if(x == 7){
			this.drawableImage = new BitmapDrawable(this.image3);
			this.sizeHeight = image3.getHeight();
			this.sizeWidth = image3.getWidth();
		}else if(x == 8){
			this.drawableImage = new BitmapDrawable(this.image2);
			this.sizeHeight = image2.getHeight();
			this.sizeWidth = image2.getWidth();
		}else if(x == 9){
			this.drawableImage = new BitmapDrawable(this.image1);
			this.sizeHeight = image1.getHeight();
			this.sizeWidth = image1.getWidth();
		}else{
			this.setAlive(false);
		}
		

	}

	

	@Override
	public void draw(Canvas c) {
		
		if(display.getWidth() > MultiGameActivity.DISPLAY_WIDHT){
			if(x < 2){
				c.drawBitmap(Bitmap.createBitmap(this.image5, 0, 0,
						this.image5.getWidth(), this.image5.getHeight(), this.matrix, true),
				        (this.positionShoot.getX()-
				        (this.image5.getWidth()/2))*1.5f , 
				        (this.positionShoot.getY())*1.5f, 
				        this.paint);
			}else if(x < 3){
				c.drawBitmap(Bitmap.createBitmap(this.image6, 0, 0,
						this.image6.getWidth(), this.image6.getHeight(), this.matrix, true),
				        (this.positionShoot.getX()-(this.image6.getWidth()/2))*1.5f , 
				        (this.positionShoot.getY())*1.5f, this.paint);
			}else if(x < 4){
				c.drawBitmap(Bitmap.createBitmap(this.image7, 0, 0,
						this.image7.getWidth(), this.image7.getHeight(), this.matrix, true),
				        (this.positionShoot.getX()-(this.image7.getWidth()/2))*1.5f , 
				        (this.positionShoot.getY())*1.5f, this.paint);
			}else if(x == 5){
				c.drawBitmap(Bitmap.createBitmap(this.image8, 0, 0,
						this.image8.getWidth(), this.image8.getHeight(), this.matrix, true),
				        (this.positionShoot.getX()-(this.image8.getWidth()/2))*1.5f , 
				        (this.positionShoot.getY())*1.5f, this.paint);
			}else if(x == 6){
				c.drawBitmap(Bitmap.createBitmap(this.image4, 0, 0,
						this.image4.getWidth(), this.image4.getHeight(), this.matrix, true),
				        (this.positionShoot.getX()-(this.image4.getWidth()/2))*1.5f , 
				        (this.positionShoot.getY())*1.5f, this.paint);
			}else if(x == 7){
				c.drawBitmap(Bitmap.createBitmap(this.image3, 0, 0,
						this.image3.getWidth(), this.image3.getHeight(), this.matrix, true),
				        (this.positionShoot.getX()-(this.image3.getWidth()/2))*1.5f , 
				        (this.positionShoot.getY())*1.5f, this.paint);
			}else if(x == 8){
				c.drawBitmap(Bitmap.createBitmap(this.image2, 0, 0,
						this.image2.getWidth(), this.image2.getHeight(), this.matrix, true),
				        (this.positionShoot.getX()-(this.image2.getWidth()/2))*1.5f , 
				        (this.positionShoot.getY())*1.5f, this.paint);
			}else if(x == 9){
				c.drawBitmap(Bitmap.createBitmap(this.image1, 0, 0,
						this.image1.getWidth(), this.image1.getHeight(), this.matrix, true),
				        (this.positionShoot.getX()-(this.image1.getWidth()/2))*1.5f , 
				        (this.positionShoot.getY())*1.5f, this.paint);
			}
		}else{
			if(x < 2){
				c.drawBitmap(Bitmap.createBitmap(this.image5, 0, 0,
						this.image5.getWidth(), this.image5.getHeight(), this.matrix, true),
				        this.positionShoot.getX()-
				        (this.image5.getWidth()/2) , 
				        this.positionShoot.getY(), 
				        this.paint);
			}else if(x < 3){
				c.drawBitmap(Bitmap.createBitmap(this.image6, 0, 0,
						this.image6.getWidth(), this.image6.getHeight(), this.matrix, true),
				        this.positionShoot.getX()-(this.image6.getWidth()/2) , this.positionShoot.getY(), this.paint);
			}else if(x < 4){
				c.drawBitmap(Bitmap.createBitmap(this.image7, 0, 0,
						this.image7.getWidth(), this.image7.getHeight(), this.matrix, true),
				        this.positionShoot.getX()-(this.image7.getWidth()/2) , this.positionShoot.getY(), this.paint);
			}else if(x == 5){
				c.drawBitmap(Bitmap.createBitmap(this.image8, 0, 0,
						this.image8.getWidth(), this.image8.getHeight(), this.matrix, true),
				        this.positionShoot.getX()-(this.image8.getWidth()/2) , this.positionShoot.getY(), this.paint);
			}else if(x == 6){
				c.drawBitmap(Bitmap.createBitmap(this.image4, 0, 0,
						this.image4.getWidth(), this.image4.getHeight(), this.matrix, true),
				        this.positionShoot.getX()-(this.image4.getWidth()/2) , this.positionShoot.getY(), this.paint);
			}else if(x == 7){
				c.drawBitmap(Bitmap.createBitmap(this.image3, 0, 0,
						this.image3.getWidth(), this.image3.getHeight(), this.matrix, true),
				        this.positionShoot.getX()-(this.image3.getWidth()/2) , this.positionShoot.getY(), this.paint);
			}else if(x == 8){
				c.drawBitmap(Bitmap.createBitmap(this.image2, 0, 0,
						this.image2.getWidth(), this.image2.getHeight(), this.matrix, true),
				        this.positionShoot.getX()-(this.image2.getWidth()/2) , this.positionShoot.getY(), this.paint);
			}else if(x == 9){
				c.drawBitmap(Bitmap.createBitmap(this.image1, 0, 0,
						this.image1.getWidth(), this.image1.getHeight(), this.matrix, true),
				        this.positionShoot.getX()-(this.image1.getWidth()/2) , this.positionShoot.getY(), this.paint);
			}
		}
		
		
		
		
	/*	
		this.matrix.setRotate(0);
		
    	
    	
		c.drawBitmap(Bitmap.createBitmap(this.image, 0, 0,
		        this.image.getWidth(), this.image.getHeight(), this.matrix, true),
		        this.positionShoot.getX()-(this.sizeWidth/2) , this.positionShoot.getY(), this.paint);
		*/
	}

	@Override
	public boolean isAlive() {
		return isAlive;
	}
	@Override
	public void setAlive(Boolean alive) {
		this.isAlive = alive;
		
	}
	
	@Override
	public Vector2D getPosition() {
		return this.positionShoot;
	}

	@Override
	public Integer getStartTime() {
		return this.startTime;
	}

	@Override
	public Integer getTypeEffect() {
		return Integer.parseInt(type.type);
	}

	
	
	public static Double randomSizedimension(Integer min, Integer max) {
		return (min + Math.random() * (max - min + 1));
	}


	@Override
	public Integer getAlpha() {
		return alpha;
	}

	
	
	
	
}
