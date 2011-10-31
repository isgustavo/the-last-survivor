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
import br.com.thelastsurvivor.engine.util.IEffect;
import br.com.thelastsurvivor.util.Vector2D;

public class EffectShoot implements IEffect {

	private Context context;
	private Vector2D positionShoot;
	private TypeEffect type;
	
	private Bitmap image;
	
	private Bitmap image1;
	private Bitmap image2;
	private Bitmap image3;
	private Bitmap image4;
	
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
	
	protected EffectShoot(Context context, Vector2D position, TypeEffect type){
		this.context = context;
		this.positionShoot = position;
		this.type = type;
		this.alpha = 0;

		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.WHITE);
		
		
		
		this.image1 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_1_image);
		this.image2 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_2_image);
		this.image3 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_3_image);
		this.image4 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_4_image);
		
		this.startTime = 0;
		
		isAlive = true;
		
		this.matrix = new Matrix();
		
		
	}
	
	public EffectShoot(Context context, Vector2D position, Integer time){
		this.context = context;
		this.positionShoot = position;
		this.alpha = 0;

		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.WHITE);
		
		
		
		this.image1 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_1_image);
		this.image2 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_2_image);
		this.image3 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_3_image);
		this.image4 = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.effect_4_image);
		
		this.startTime = time;
		
		isAlive = true;
		
		this.matrix = new Matrix();
	}
	
	
	
	@Override
	public void init() {
		
		
	}
	
	@Override
	public void update() {
		startTime++;
		if(startTime < 2){
			this.drawableImage = new BitmapDrawable(this.image4);
			this.sizeHeight = image4.getHeight();
			this.sizeWidth = image4.getWidth();
		}else if(startTime < 3){
			this.drawableImage = new BitmapDrawable(this.image3);
			this.sizeHeight = image3.getHeight();
			this.sizeWidth = image3.getWidth();
		}else if(startTime < 4){
			this.drawableImage = new BitmapDrawable(this.image2);
			this.sizeHeight = image2.getHeight();
			this.sizeWidth = image2.getWidth();
		}else if(startTime == 5){
			this.drawableImage = new BitmapDrawable(this.image1);
			this.sizeHeight = image1.getHeight();
			this.sizeWidth = image1.getWidth();
		}else{
			this.setAlive(false);
		}

	}

	@Override
	public void draw(Canvas c) {
		
		if(startTime < 2){
			c.drawBitmap(Bitmap.createBitmap(this.image4, 0, 0,
			        this.image4.getWidth(), this.image4.getHeight(), this.matrix, true),
			        this.positionShoot.getX()-(this.image4.getWidth()/2) , this.positionShoot.getY(), this.paint);
		}else if(startTime < 3){
			c.drawBitmap(Bitmap.createBitmap(this.image3, 0, 0,
					this.image3.getWidth(), this.image3.getHeight(), this.matrix, true),
			        this.positionShoot.getX()-(this.image4.getWidth()/2) , this.positionShoot.getY(), this.paint);
		}else if(startTime < 4){
			c.drawBitmap(Bitmap.createBitmap(this.image2, 0, 0,
					this.image2.getWidth(), this.image2.getHeight(), this.matrix, true),
			        this.positionShoot.getX()-(this.image4.getWidth()/2) , this.positionShoot.getY(), this.paint);
		}else if(startTime == 5){
			c.drawBitmap(Bitmap.createBitmap(this.image1, 0, 0,
					this.image1.getWidth(), this.image1.getHeight(), this.matrix, true),
			        this.positionShoot.getX()-(this.image4.getWidth()/2) , this.positionShoot.getY(), this.paint);
		}
		
		
	}

	@Override
	public boolean isAlive() {
		return this.isAlive;
	}

	
	
	public Integer getStartTime() {
		return startTime;
	}

	@Override
	public Vector2D getPosition() {
		return this.positionShoot;
	}


	public static Double randomSizedimension(Integer min, Integer max) {
		return (min + Math.random() * (max - min + 1));
	}



	public Integer getAlpha() {
		return alpha;
	}

	@Override
	public Integer getTypeEffect() {
		return Integer.parseInt(type.type);
	}

	@Override
	public void setAlive(Boolean alive) {
		this.isAlive = alive;
		
	}
	
	
	
}
