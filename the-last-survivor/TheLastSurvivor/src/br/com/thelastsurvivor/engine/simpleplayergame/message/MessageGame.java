package br.com.thelastsurvivor.engine.simpleplayergame.message;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import br.com.thelastsurvivor.engine.util.IInitUpdateDraw;

public class MessageGame implements IInitUpdateDraw{
	
	protected Context context;
	
	protected Paint paint;
	protected Typeface font;
	protected String text;
	protected Integer position;
	protected Integer alpha;
	protected boolean isAlive;
	public Integer color = Color.argb(255, 255, 255, 255);
	
	public MessageGame(Context context, String text, Integer position, Integer time, String color){
		this.context = context;
		this.position = position;
		this.alpha = time;
		this.text = text;
		
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.parseColor(color));
		this.paint.setTextSize(14);
		
		this.font = Typeface.createFromAsset(this.context.getAssets(),"fonts/FT2FONT.TTF");
		
		this.paint.setTypeface(this.font);
		
		
	}
	
	public MessageGame(Context context, String text, Integer position, Integer time){
		this.context = context;
		this.position = position;
		this.alpha = time;
		this.text = text;
		
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.WHITE);
		this.paint.setTextSize(14);
		
		this.font = Typeface.createFromAsset(this.context.getAssets(),"fonts/FT2FONT.TTF");
		
		this.paint.setTypeface(this.font);
		
	}
	
	@Override
	public void init() {

		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.WHITE);
		this.paint.setTextSize(14);

	}
	
	@Override
	public void update() {
		Integer alpha = color >>> 24;
		alpha -= randomSizedimension(0,4).intValue();
		if (alpha <= 0) {	
			this.isAlive = false;
		}else{
			color = (color & 0x00ffffff) + (alpha << 24);		// set the new alpha
			this.alpha = alpha;
		}	
	}

	@Override
	public void draw(Canvas c) {
 		
   	 	this.paint.setAlpha(this.alpha);
   	 	
   	 	switch (getPosition()) {
		case 1:
			c.drawText(text, 10, 235, paint);
			
		break;
		case 2:
			c.drawText(text, 10, 245, paint);
			
		break;
		case 3:
			c.drawText(text, 10, 260, paint);
			
		break;
		
		}
		
	}

	public static Double randomSizedimension(Integer min, Integer max) {
		return (min + Math.random() * (max - min + 1));
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public String getText() {
		return text;
	}

	public Integer getAlpha() {
		return alpha;
	}

	

}
