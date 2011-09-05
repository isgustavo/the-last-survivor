package br.com.thelastsurvivor.engine;

import br.com.thelastsurvivor.util.Vector2D;

public class CameraGame {

	
	private Integer beginningSizeWidth;
	private Integer finalSizeWidth;
	
	private Integer beginningSizeHeight;
	private Integer finalSizeHeight;
	
	
	public CameraGame(int beginningSizeWidth, int finalSizeWidth, int beginningSizeHeight, int finalSizeHeight) {
		
		this.beginningSizeWidth = beginningSizeWidth;
		this.finalSizeWidth = finalSizeWidth;
		
		this.beginningSizeHeight = beginningSizeHeight;
		this.finalSizeHeight = finalSizeHeight;
	}
	
	
	public Integer getBeginningSizeWidth() {
		return beginningSizeWidth;
	}
	public Integer getRelativeBeginningSizeWidth(){
		return beginningSizeWidth - 80;
	}
	
	public void setBeginningSizeWidth(Integer beginningSizeWidth) {
		this.beginningSizeWidth = beginningSizeWidth;
	}
	
	public void addBeginningSizeWidth(Integer beginningSizeWidth){
		this.beginningSizeWidth += beginningSizeWidth;
	}
	
	
	public Integer getFinalSizeWidth() {
		return finalSizeWidth ;
	}
	
	public Integer getRelativeFinalSizeWidth() {
		return finalSizeWidth + 80 ;
	}
	
	public void setFinalSizeWidth(Integer finalSizeWidth) {
		this.finalSizeWidth = finalSizeWidth;
	}
	
	public void addFinalSizeWidth(Integer finalSizeWidth){
		this.finalSizeWidth += finalSizeWidth;
	}
	
	public Integer getBeginningSizeHeight() {
		return beginningSizeHeight;
	}
	
	public Integer getRelativeBeginningSizeHeight() {
		return beginningSizeHeight - 80;
	}
	
	public void setBeginningSizeHeight(Integer beginningSizeHeight) {
		this.beginningSizeHeight = beginningSizeHeight;
	}
	
	public void addBeginningSizeHeight(Integer beginningSizeHeight){
		this.beginningSizeHeight += beginningSizeHeight;
	}
	
	
	public Integer getFinalSizeHeight() {
		return finalSizeHeight;
	}
	
	public Integer getRelativeFinalSizeHeight() {
		return finalSizeHeight + 80;
	}
	
	public void setFinalSizeHeight(Integer finalSizeHeight) {
		this.finalSizeHeight = finalSizeHeight;
	}
	
	public void addFinalSizeHeight(Integer finalSizeHeight){
		this.finalSizeHeight += finalSizeHeight;
	}
	
	
}
