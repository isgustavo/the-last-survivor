package br.com.thelastsurvivor.model.trophies;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class Trophies implements Serializable{
	
	private String name;
	private String objective;
	private Boolean isAchieved;
	private Drawable image;

	
	
	
	
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
	public Boolean isAchieved() {
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
