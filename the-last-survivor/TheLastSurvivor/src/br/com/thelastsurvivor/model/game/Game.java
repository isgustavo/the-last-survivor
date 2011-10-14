package br.com.thelastsurvivor.model.game;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Game implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer player;
	private Date date;
	private Long runTime;
	private Spacecraft spacecraft;
	private List<Asteroid> asteroids;
	private List<PowerUp> powerUps;
	private List<Effect> effects;
	
	public Game(Integer player, Date date, Long runTime,
			Spacecraft spacecraft, List<Asteroid> asteroids,
			List<PowerUp> powerUps, List<Effect> effects) {
		
		this.player = player;
		this.date = date;
		this.runTime = runTime;
		this.spacecraft = spacecraft;
		this.asteroids = asteroids;
		this.powerUps = powerUps;
		this.effects = effects;
	}
	
	public Game(Integer id, Integer player, Date date, Long runTime) {
		this.id = id;
		this.player = player;
		this.date = date;
		this.runTime = runTime;

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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getRunTime() {
		return runTime;
	}
	public void setRunTime(Long runTime) {
		this.runTime = runTime;
	}
	public Spacecraft getSpacecraft() {
		return spacecraft;
	}
	public void setSpacecraft(Spacecraft spacecraft) {
		this.spacecraft = spacecraft;
	}
	public List<Asteroid> getAsteroids() {
		return asteroids;
	}
	public void setAsteroids(List<Asteroid> asteroids) {
		this.asteroids = asteroids;
	}
	public List<PowerUp> getPowerUps() {
		return powerUps;
	}
	public void setPowerUps(List<PowerUp> powerUps) {
		this.powerUps = powerUps;
	}
	public List<Effect> getEffects() {
		return effects;
	}
	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}
	
	
	
	
}
