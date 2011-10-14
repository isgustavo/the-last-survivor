package br.com.thelastsurvivor.model.game;

import java.io.Serializable;

import br.com.thelastsurvivor.util.Vector2D;

public class PowerUp implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer game;
	private Vector2D position;
	private Integer route;

	
	public PowerUp(Vector2D position, Integer route) {
		this.position = position;
		this.route = route;
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

	public Integer getRoute() {
		return route;
	}

	public void setRoute(Integer route) {
		this.route = route;
	}
	
	

}
