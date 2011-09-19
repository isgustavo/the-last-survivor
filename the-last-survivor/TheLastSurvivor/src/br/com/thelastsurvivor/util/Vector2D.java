package br.com.thelastsurvivor.util;

public class Vector2D {
	
	private Integer x;
	private Integer y;
	
	public Vector2D(){
		this.x = 0;
		this.y = 0;
		
	}
	
	public Vector2D(Integer x, Integer y){
		this.x = x;
		this.y = y;
		
	}
	
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public void addX(Integer x){
		this.x += x;
	}
	
	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public void addY(Integer y){
		this.y += y;
	}
	
	
}

