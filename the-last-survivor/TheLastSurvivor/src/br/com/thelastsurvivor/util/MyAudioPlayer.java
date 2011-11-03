package br.com.thelastsurvivor.util;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

public class MyAudioPlayer implements OnCompletionListener{

	private static final int NOVO = 0;
	private static final int INICIADO = 1;
	private static final int PAUSADO = 2;
	private static final int PARADO = 3;
	
	private int statusAtual;
	private MediaPlayer player;
	private String mp3;
	
	public MyAudioPlayer(Activity act, int audio) {
		statusAtual = NOVO;
		player = MediaPlayer.create(act, audio);
		player.setOnCompletionListener(this);
	}
	
	public void start() {
//		Log.i("", "Inicio da música: " + mp3);
		try {
			// Seguindo o ciclo de vida do MediaPlayer
			switch (statusAtual) {
				case INICIADO:
					player.stop();
				case PARADO:
					player.reset();
				case NOVO:
//					player.setDataSource(mp3);
//					player.prepare();
				case PAUSADO:
					player.start();
					break;
			}
		} catch (Exception e) {
			Log.e("", e.getMessage(), e);
		}
	}
	
	public void pause() {
		player.pause();
		statusAtual = PAUSADO;
	}
	
	public void stop() {
		if(player != null){
			player.stop();
			statusAtual = PARADO;
		}
		
		
	}
	
	public void fechar() {
		stop();
		if(player != null){
			player.release();
			player = null;
		}
		
	}
	
	public void onCompletion(MediaPlayer mp) {
		Log.i("", "Fim da música: " + mp3);
		statusAtual = PARADO;
	}

	public int getStatusAtual() {
		return statusAtual;
	}
	
	
	
}
