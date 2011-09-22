package br.com.thelastsurvivor.model.game;

import java.io.Serializable;
import java.util.List;

import br.com.thelastsurvivor.util.Vector2D;

public class Spacecraft implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer game;
	private Vector2D postion;
	private Integer life;
	private Double angle;
	private List<Shoot> shoots;
	

	public Spacecraft(Integer id, Integer game, Vector2D postion,Integer life, Double angle, List<Shoot> shoots) {
	
		this.id = id;
		this.game = game;
		this.postion = postion;
		this.life = life;
		this.angle = angle;
		this.shoots.addAll(shoots);
	}
	
	public Spacecraft(Vector2D postion,Integer life, Double angle, List<Shoot> shoots) {
		
		this.postion = postion;
		this.life = life;
		this.angle = angle;
		this.shoots.addAll(shoots);
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
	public Vector2D getPostion() {
		return postion;
	}
	public void setPostion(Vector2D postion) {
		this.postion = postion;
	}

	public Double getAngle() {
		return angle;
	}

	public void setAngle(Double angle) {
		this.angle = angle;
	}

	public Integer getLife() {
		return life;
	}

	public void setLife(Integer life) {
		this.life = life;
	}
	
	
	
	
}
