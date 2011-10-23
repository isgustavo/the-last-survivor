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
	private Integer powerUp;
	private Spacecraft spacecraft;
	private List<Asteroid> asteroids;
	private List<PowerUp> powerUps;
	
	public Game(Integer player, final Date date, final Long runTime, Integer powerUp,
			Spacecraft spacecraft, List<Asteroid> asteroids,
			List<PowerUp> powerUps) {
		
		this.player = player;
		this.date = date;
		this.runTime = runTime;
		this.powerUp = powerUp;
		this.spacecraft = spacecraft;
		this.asteroids = asteroids;
		this.powerUps = powerUps;
	}
	
	public Game(Integer id, Integer player, final Date date, final Long runTime,Integer powerUp, Spacecraft spacecraft) {
		this.id = id;
		this.player = player;
		this.date = date;
		this.runTime = runTime;
		this.powerUp = powerUp;
		this.spacecraft = spacecraft;

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
	
	public Integer getPowerUp() {
		return powerUp;
	}

	public void setPowerUp(Integer powerUp) {
		this.powerUp = powerUp;
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
	
	
}
