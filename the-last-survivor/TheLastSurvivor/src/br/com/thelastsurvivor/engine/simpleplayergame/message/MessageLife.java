package br.com.thelastsurvivor.engine.simpleplayergame.message;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MessageLife extends MessageGame{
	
	private Paint paint;
	
	public MessageLife(Context context, String text, Integer position, Integer time){
		super(context, text, position, time);
		
		init();
	}
	
	@Override
	public void init() {
		super.init();
		
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.parseColor("#00889C"));
		this.paint.setTextSize(18);
		
	
		//.font = Typeface.createFromAsset(this.context.getAssets(),"fonts/FT2FONT.TTF");
		
		this.paint.setTypeface(this.font);
		
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
		
		String[] message = text.split("/");
 		
		//super.paint.setAlpha(this.alpha);
   	 	this.paint.setAlpha(this.alpha);
   	 	
   	 	
   	 	switch (getPosition()) {
		case 1:
			c.drawText(message[0], 10, 245, this.paint);
			c.drawText(message[1], 80, 240, this.paint);
		break;
		case 2:
			c.drawText(message[0], 10, 255, this.paint);
			c.drawText(message[1], 80, 250, this.paint);
		break;
		case 3:
			c.drawText(message[0], 10, 260, this.paint);
			c.drawText(message[1], 80, 260, this.paint);
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

	

}

