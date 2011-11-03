package br.com.thelastsurvivor.model.rank;

public class Result {

	
	private String id;
	private Integer points;
	

	public Result(String id, Integer points) {
		this.id = id;
		this.points = points;
	}

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	
	
	
}
