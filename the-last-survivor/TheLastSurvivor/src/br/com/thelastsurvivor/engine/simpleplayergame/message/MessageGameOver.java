package br.com.thelastsurvivor.engine.simpleplayergame.message;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class MessageGameOver {

	
	protected Paint paint;
	protected Typeface font;
	protected String text;
	protected Integer position;
	
	public Integer color = Color.argb(255, 255, 255, 255);
	
	public MessageGameOver(Context context, String text, String color){
		
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.parseColor(color));
		this.paint.setTextSize(25);
		
		this.text = text;
		
		this.font = Typeface.createFromAsset(context.getAssets(),"fonts/FT2FONT.TTF");
		
		this.paint.setTypeface(this.font);
		
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public Typeface getFont() {
		return font;
	}

	public void setFont(Typeface font) {
		this.font = font;
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

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}
	
	
	
	
}
