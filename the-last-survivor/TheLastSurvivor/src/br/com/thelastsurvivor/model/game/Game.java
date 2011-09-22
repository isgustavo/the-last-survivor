package br.com.thelastsurvivor.model.game;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Game implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer player;
	private Date date;
	private Integer runTime;
	private Integer points;
	private Spacecraft spacecraft;
	private List<Asteroid> asteroids;
	
	
	public Game(Integer id, Integer player, Date date, Integer runTime,
			Integer points, Spacecraft spacecraft, List<Asteroid> asteroids) {
		this.id = id;
		this.player = player;
		this.date = date;
		this.runTime = runTime;
		this.points = points;
		this.spacecraft = spacecraft;
		this.asteroids = asteroids;
	}
	
	public Game(Date date, Integer runTime,
			Integer points, Spacecraft spacecraft, List<Asteroid> asteroids) {
		this.date = date;
		this.runTime = runTime;
		this.points = points;
		this.spacecraft = spacecraft;
		this.asteroids = asteroids;
	}
	
	public Game(Integer id, Integer player, Date date, Integer runTime,
			Integer points) {
		this.id = id;
		this.player = player;
		this.date = date;
		this.runTime = runTime;
		this.points = points;
	
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
	public Integer getRunTime() {
		return runTime;
	}
	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}
	
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
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
	
	
	
}
