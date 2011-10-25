package br.com.thelastsurvivor.model.rank;

import java.io.Serializable;
import java.util.Date;

public class Rank implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final int SIMPLE = 1;
	public static final int MULTI = 2;
	
	private Integer id;
	private String identifier;
	private Integer point;
	private Date date;
	private Integer type;
	 
	
	public Rank(String identifier, Integer point, Date date, Integer type) {

		this.identifier = identifier;
		this.point = point;
		this.date = date;
		this.type = type;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	

	
}
