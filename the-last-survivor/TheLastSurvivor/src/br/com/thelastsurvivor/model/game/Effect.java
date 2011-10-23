package br.com.thelastsurvivor.model.game;

import java.io.Serializable;

import br.com.thelastsurvivor.util.Vector2D;

public class Effect  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer game;
	private Vector2D position;
	private Integer time;
	private Integer type;

	public Effect(Vector2D position, Integer time, Integer type) {
		this.position = position;
		this.time = time;
		this.type = type;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGame() {
		return game;
	}
	public void setGame(Integer game) {
		this.game = game;
	}
	public Vector2D getPosition() {
		return position;
	}
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	

}
