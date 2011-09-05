package br.com.thelastsurvivor.engine.util;

public enum EOrientation {

	
	up(1),
	up_right(2),
	right(3),
	down_right(4),
	down(5),
	down_left(6),
	left(7),
	up_left(8);
	
	private Integer orientation;
	
	EOrientation(Integer orientation){
		this.orientation = orientation;
	}

	public Integer getOrientation() {
		return orientation;
	}

	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}
	
	
	
}
