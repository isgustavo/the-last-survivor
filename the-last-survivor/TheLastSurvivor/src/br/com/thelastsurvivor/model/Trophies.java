package br.com.thelastsurvivor.model;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class Trophies implements Serializable{
	
	private String name;
	private String objective;
	private boolean isAchieved;
	private Drawable image;
	private Integer status;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public boolean isAchieved() {
		return isAchieved;
	}
	public Drawable getImage() {
		return image;
	}
	public void setImage(Drawable image) {
		this.image = image;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	

}
