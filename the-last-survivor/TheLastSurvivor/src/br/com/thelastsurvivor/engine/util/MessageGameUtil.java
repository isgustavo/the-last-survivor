package br.com.thelastsurvivor.engine.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class MessageGameUtil implements IDraw{

	private Paint paint;
	private String text;
	private Integer position;
	private Integer alfa;
	private boolean isAlive;
	public Integer color = Color.argb(255, 255, 255, 255);
	protected Typeface font; 

	public MessageGameUtil(Paint paint, String text,Integer position, Integer alfa) {
		this.paint = paint;
		this.text = text;
		this.position = position;
		this.alfa = 0;
		this.isAlive = true;
	}
	
	public MessageGameUtil(Context context, String text,Integer position, Integer alfa) {
		this.text = text;
		this.position = position;
		this.alfa = alfa;
	
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.WHITE);
		this.paint.setTextSize(10);
		
		this.font = Typeface.createFromAsset(context.getAssets(),"fonts/FT2FONT.TTF");
		
		this.paint.setTypeface(this.font);
		this.paint.setAlpha(alfa);
	}
	
	
	@Override
	public void draw(Canvas c) {
		
   	 	switch (position) {
		case 1:
			c.drawText(text, 10, 240, getPaint());
		break;
		case 2:
			c.drawText(getText(), 10, 250, getPaint());
		break;
		case 3:
			c.drawText(getText(), 10, 260,getPaint());
		break;
		
		}
	}

	void drawFont(Canvas c, MessageGameUtil message){

   	 	
        

   }
	
	public Paint getPaint() {
		return paint;
	}
	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getAlfa() {
		return alfa;
	}

	public void setAlfa(Integer alfa) {
		this.alfa = alfa;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	
	
	
}
