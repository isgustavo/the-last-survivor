package br.com.thelastsurvivor.model.game;

import java.io.Serializable;

import br.com.thelastsurvivor.util.Vector2D;

public class Shoot implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer player;
	private Vector2D position;
	private Double angle;
	
	public Shoot(Vector2D position, Double angle) {
		this.position = position;
		this.angle = angle;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPlayer() {
		return player;
	}
	public void setPlayer(Integer player) {
		this.player = player;
	}
	public Vector2D getPosition() {
		return position;
	}
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	public Double getAngle() {
		return angle;
	}
	public void setAngle(Double angle) {
		this.angle = angle;
	}


	
}
