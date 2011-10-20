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
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.game.weapon.IWeaponBehavior;
import br.com.thelastsurvivor.engine.simple.IDrawBehavior;
import br.com.thelastsurvivor.engine.util.IDraw;
import br.com.thelastsurvivor.util.Vector2D;

public class EffectEndGame implements IDraw, IDrawBehavior, IWeaponBehavior {

	private Context context;
	private Vector2D positionShoot;
	
	private Bitmap image;
	
	private Bitmap image1;
	private Bitmap image2;
	private Bitmap image3;
	private Bitmap image4;
	private Bitmap image5;
	private Bitmap image6;
	private Bitmap image7;
	private Bitmap image8;
	
	private Bitmap resizedBitmap;
	private Drawable drawableImage;
	private Integer sizeWidth;
	private Integer sizeHeight;
	
	
	private Matrix matrix;
	private Boolean isAlive;
	
	private Paint paint;
	private Integer alpha;
	public Integer color = Color.argb(255, 255, 255, 255);
	
	private Integer startTime;
	
	public EffectEndGame(Context context, Vector2D position){
		this.context = context;
		this.positionShoot = position;
		this.alpha = 0;

		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.WHITE);
		
		this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.shoot_effect_image);
		
		this.image1 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_1_image);
		this.image2 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_2_image);
		this.image3 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_3_image);
		this.image4 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_4_image);
		this.image5 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_9_image);
		this.image6 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_11_image);
		this.image7 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_10_image);
		this.image8 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_8_image);
		
		this.startTime = 0;
		
		this.sizeHeight = image.getHeight();
		this.sizeWidth = image.getWidth();
		
		
		
		isAlive = true;
		
		this.matrix = new Matrix();
		
		
	}
	
	public EffectEndGame(Context context, Vector2D position, Integer alfa){
		this.context = context;
		this.positionShoot = position;
		this.alpha = alfa;

		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.WHITE);
		
		this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.shoot_effect_image);
		
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
		}
	}

	@Override
	public void draw(Canvas c) {
		
		if(x < 2){
			c.drawBitmap(Bitmap.createBitmap(this.image5, 0, 0,
			        this.sizeWidth, this.sizeHeight, this.matrix, true),
			        this.positionShoot.getX()-(this.sizeWidth/2) , this.positionShoot.getY(), this.paint);
		}else if(x < 3){
			c.drawBitmap(Bitmap.createBitmap(this.image6, 0, 0,
			        this.sizeWidth, this.sizeHeight, this.matrix, true),
			        this.positionShoot.getX()-(this.sizeWidth/2) , this.positionShoot.getY(), this.paint);
		}else if(x < 4){
			c.drawBitmap(Bitmap.createBitmap(this.image7, 0, 0,
			        this.sizeWidth, this.sizeHeight, this.matrix, true),
			        this.positionShoot.getX()-(this.sizeWidth/2) , this.positionShoot.getY(), this.paint);
		}else if(x == 5){
			c.drawBitmap(Bitmap.createBitmap(this.image8, 0, 0,
			        this.sizeWidth, this.sizeHeight, this.matrix, true),
			        this.positionShoot.getX()-(this.sizeWidth/2) , this.positionShoot.getY(), this.paint);
		}else if(x == 6){
			c.drawBitmap(Bitmap.createBitmap(this.image4, 0, 0,
			        this.sizeWidth, this.sizeHeight, this.matrix, true),
			        this.positionShoot.getX()-(this.sizeWidth/2) , this.positionShoot.getY(), this.paint);
		}else if(x == 7){
			c.drawBitmap(Bitmap.createBitmap(this.image3, 0, 0,
			        this.sizeWidth, this.sizeHeight, this.matrix, true),
			        this.positionShoot.getX()-(this.sizeWidth/2) , this.positionShoot.getY(), this.paint);
		}else if(x == 8){
			c.drawBitmap(Bitmap.createBitmap(this.image2, 0, 0,
			        this.sizeWidth, this.sizeHeight, this.matrix, true),
			        this.positionShoot.getX()-(this.sizeWidth/2) , this.positionShoot.getY(), this.paint);
		}else if(x == 9){
			c.drawBitmap(Bitmap.createBitmap(this.image1, 0, 0,
			        this.sizeWidth, this.sizeHeight, this.matrix, true),
			        this.positionShoot.getX()-(this.sizeWidth/2) , this.positionShoot.getY(), this.paint);
		}
		
	}

	@Override
	public boolean isAlive() {
		return this.isAlive;
	}

	@Override
	public Integer getSizeWidth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getSizeHeight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector2D getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAlive(boolean alive) {
		// TODO Auto-generated method stub
		
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
	public static Double randomSizedimension(Integer min, Integer max) {
		return (min + Math.random() * (max - min + 1));
	}



	public Integer getAlpha() {
		return alpha;
	}
	
	
	
}
