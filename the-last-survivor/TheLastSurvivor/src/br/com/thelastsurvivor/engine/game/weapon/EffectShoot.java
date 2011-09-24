package br.com.thelastsurvivor.engine.game.weapon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import br.com.thelastsurvivor.R;
import br.com.thelastsurvivor.engine.IDrawBehavior;
import br.com.thelastsurvivor.util.Vector2D;

public class EffectShoot implements IDrawBehavior, IWeaponBehavior {

	private Context context;
	private Vector2D positionShoot;
	
	private Bitmap image;
	
	
	private Bitmap resizedBitmap;
	private Drawable drawableImage;
	private Integer sizeWidth;
	private Integer sizeHeight;
	
	
	private Matrix matrix;
	private Boolean isAlive;
	
	private Paint paint;
	private Integer alpha;
	public Integer color = Color.argb(255, 255, 255, 255);
	
	public EffectShoot(Context context, Vector2D position){
		this.context = context;
		this.positionShoot = position;
		this.alpha = 0;

		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.WHITE);
		
		this.image = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.shoot_effect_image);
		
		this.sizeHeight = image.getHeight();
		this.sizeWidth = image.getWidth();
		
		isAlive = true;
		
		this.matrix = new Matrix();
		
		
	}
	
	
	
	@Override
	public void init() {
		
		
	}

	@Override
	public void update() {
		
		Integer alpha = this.color >>> 24;
		alpha -= 12; //randomSizedimension(0,10).intValue();
		if (alpha <= 0) {	
			this.setAlive(false);
		}else{
			this.color = (this.color & 0x00ffffff) + (alpha << 24);		// set the new alpha
			//paint.setAlpha(alpha);
			this.alpha = alpha;
		}
		
		this.paint.setAlpha(this.alpha);
		
	}

	@Override
	public void draw(Canvas c) {
		
		
		
		this.matrix.setRotate(0);
		
    	
    	
		c.drawBitmap(Bitmap.createBitmap(this.image, 0, 0,
		        this.image.getWidth(), this.image.getHeight(), this.matrix, true),
		        this.positionShoot.getX()-(this.sizeWidth/2) , this.positionShoot.getY(), this.paint);
		
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
	
	
	
}
