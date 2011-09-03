package br.com.thelastsurvivor.engine;

public class ScreenSize {

	
	private static Integer height;
	private static Integer widht;
	

	public ScreenSize(Integer height, Integer widht){
		this.height = height;
		this.widht = widht;
		
	}
	
	public static Integer getHeight() {
		return height;
	}
	public static Integer getWidht() {
		return widht;
	}
	
	
	
}
