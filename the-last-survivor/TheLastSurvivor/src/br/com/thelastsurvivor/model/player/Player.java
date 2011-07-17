package br.com.thelastsurvivor.model.player;

import java.io.Serializable;

import br.com.thelastsurvivor.provider.TheLastSurvivorProvider;

import android.net.Uri;
import android.provider.BaseColumns;

public class Player implements Serializable, BaseColumns{
	
	private static final long serialVersionUID = 1L;
	
	private static final Uri CONTENT_URI = Uri.parse("content://"
			+ TheLastSurvivorProvider.CONTENT_URI+ "/player");
	
	private static final String CONTENT_TYPE = "vnd.android.cursor.dir/"
			+TheLastSurvivorProvider.CONTENT_URI;
	
	private static final String NAME_TABLE ="player";
	private static final String ID = "id";
	private static final String IDENTIFIER_PLAYER = "identifier";
	private static final String LGTWITTER = "lg_twitter"; 
	
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


	public static Uri getContentUri() {
		return CONTENT_URI;
	}


	public static String getContentType() {
		return CONTENT_TYPE;
	}


	public static String getNameTable(){
		return NAME_TABLE;
	}
	
	public static String getId() {
		return ID;
	}

	public static String getIdentifierPlayer(){
		return IDENTIFIER_PLAYER;
	}

	public static String getLgtwitter() {
		return LGTWITTER;
	}
	
	

}
