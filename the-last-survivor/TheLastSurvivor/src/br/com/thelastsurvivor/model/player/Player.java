package br.com.thelastsurvivor.model.player;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String identifier;
	private String lgTwitter;

	public Player(String identifier, String lgTwitter) {
		this.identifier = identifier;
		this.lgTwitter = lgTwitter;
	}
	
	public Player(Integer id, String identifier, String lgTwitter) {
		this.id = id;
		this.identifier = identifier;
		this.lgTwitter = lgTwitter;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getLgTwitter() {
		return lgTwitter;
	}

	public void setLgTwitter(String lgTwitter) {
		this.lgTwitter = lgTwitter;
	}

}
