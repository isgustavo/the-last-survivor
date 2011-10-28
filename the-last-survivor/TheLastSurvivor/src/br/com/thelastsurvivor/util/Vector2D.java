package br.com.thelastsurvivor.util;

import java.io.Serializable;

public class Vector2D implements Serializable {
	
	private Float x;
	private Float y;
	
	public Vector2D(){
		this.x = 0f;
		this.y = 0f;
		
	}
	
	public Vector2D(Integer x, Integer y){
		this.x = x.floatValue();
		this.y = y.floatValue();
		
	}
	
	public Vector2D(Float x, Float y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(String x, String y){
		this.x = Float.parseFloat(x);
		this.y = Float.parseFloat(y);
		
	}
	
	public Vector2D(Vector2D vetor){
		this.x = new Float(vetor.getX().floatValue());
		this.y = new Float(vetor.getY().floatValue());
	}
	
	public Integer getX() {
		return x.intValue();
	}
	public Float getFloatX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x.floatValue();
	}
	
	public void setFloatX(Float x) {
		this.x = x;
	}

	public void addX(Integer x){
		this.x += x.floatValue();
	}
	public void addFloatX(Float x){
		this.x += x;
	}
	
	public Integer getY() {
		return y.intValue();
	}
	public Float getFloatY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y.floatValue();
	}
	public void setFloatY(Float y) {
		this.y = y;
	}

	public void addY(Integer y){
		this.y += y.floatValue();
	}
	public void addFloatY(Float y){
		this.y += y;
	}
	
	
}

