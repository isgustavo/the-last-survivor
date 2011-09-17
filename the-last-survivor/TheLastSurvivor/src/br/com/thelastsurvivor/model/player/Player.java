package br.com.thelastsurvivor.model.player;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nickname;

	public Player(String nickname) {
		this.nickname = nickname;
	}
	
	public Player(Integer id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
