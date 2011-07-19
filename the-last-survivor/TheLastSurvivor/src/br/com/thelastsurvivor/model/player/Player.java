package br.com.thelastsurvivor.model.player;

import java.io.Serializable;

public class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	
	private String identifier;
	private String lgTwitter;
	
	
	
	public Player(String identifier, String lgTwitter){
		this.setIdentifier(identifier);
		this.setLgTwitter(lgTwitter);
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
