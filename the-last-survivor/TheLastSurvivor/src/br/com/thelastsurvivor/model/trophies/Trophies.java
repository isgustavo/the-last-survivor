package br.com.thelastsurvivor.model.trophies;

import java.io.Serializable;
import java.util.Date;

public class Trophies implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	private Date dateAchieved;
	
	
	public Trophies(Integer id, Date date) {
		this.id = id;
		this.dateAchieved = date;
	}
	
	
	public Trophies(int id) {
		this.id = id;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDateAchieved() {
		return dateAchieved;
	}
	public void setDateAchieved(Date dateAchieved) {
		this.dateAchieved = dateAchieved;
	}

	
	
	
}
