package br.com.thelastsurvivor.engine.util;

import android.graphics.Color;
import android.graphics.Paint;

public class MessageGameUtil {

	private Paint paint;
	private String text;
	private Integer position;
	private Integer alfa;
	private boolean isAlive;
	public Integer color = Color.argb(255, 255, 255, 255);

	public MessageGameUtil(Paint paint, String text,Integer position, Integer alfa) {
		this.paint = paint;
		this.text = text;
		this.position = position;
		this.alfa = 0;
		this.isAlive = true;
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
