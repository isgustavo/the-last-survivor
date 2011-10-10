package br.com.thelastsurvivor.engine.audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class AudioGame implements OnCompletionListener{

	public static final int NOT_REPEATS = 0;
	public static final int REPEATS = 1; 
	
	private Integer type;
	private MediaPlayer player;
	
	public AudioGame(Context context, Integer audio, Integer type) {
		player = MediaPlayer.create(context, audio);
		player.setOnCompletionListener(AudioGame.this);
		
		this.type = type; 
	}
	
	public void start() {
		try {
			player.start();
		} catch (Exception e) {}
	}
	
	public void stop() {
		player.stop();
		
		player.release();
		player = null;
	}
	
	public void onCompletion(MediaPlayer mp) {
		if(type == NOT_REPEATS){
			this.stop();
		}else{
			this.start();
		}
	}
	
}

